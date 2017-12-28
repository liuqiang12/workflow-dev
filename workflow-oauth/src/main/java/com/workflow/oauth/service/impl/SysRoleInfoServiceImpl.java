package com.workflow.oauth.service.impl;

import com.workflow.common.base.BaseServiceImpl;
import com.workflow.common.dao.SysRoleInfoDao;
import com.workflow.common.dto.Result;
import com.workflow.common.entity.SysPermission;
import com.workflow.common.entity.SysRoleInfo;
import com.workflow.oauth.service.SysPermissionService;
import com.workflow.oauth.service.SysRoleInfoService;
import com.workflow.oauth.service.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
@Service
@Transactional
public class SysRoleInfoServiceImpl extends BaseServiceImpl<SysRoleInfoDao, SysRoleInfo> implements SysRoleInfoService {

    @Autowired
    private SysUserInfoService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public Result findRolesAndSelected(Integer id) {
        Set<SysRoleInfo> userRole = userService.findOne(id).getRoles();
        List<SysRoleInfo> roles = findAll();
        for (SysRoleInfo r: roles) {
            if (userRole.contains(r)) r.setSelected(1);
        }

        return Result.ok(roles);
    }

    @Override
    public Page<SysRoleInfo> findByPage(int pageNo, int length) {
        PageRequest pageRequest = new PageRequest(pageNo, length);
        Page<SysRoleInfo> page = repository.findAll(pageRequest);
        return page;
    }

    @Override
    public void saveRolePermission(Integer roleid, SysPermission[] pers) {
        SysRoleInfo role = findOne(roleid);
        if (pers==null){
            role.setPermissions(new HashSet<>());
        }
        else {
            role.setPermissions(Stream.of(pers).collect(toSet()));
        }
        save(role);
    }
}
