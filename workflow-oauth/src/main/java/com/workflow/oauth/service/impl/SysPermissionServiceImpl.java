package com.workflow.oauth.service.impl;

import com.workflow.common.base.BaseServiceImpl;
import com.workflow.common.dao.SysPermissionDao;
import com.workflow.common.dao.SysUserInfoDao;
import com.workflow.common.entity.SysPermission;
import com.workflow.common.entity.SysUserInfo;
import com.workflow.oauth.service.SysPermissionService;
import com.workflow.oauth.service.SysRoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermissionDao, SysPermission> implements SysPermissionService {
    @Autowired
    private SysRoleInfoService roleService;

    @Autowired
    private SysUserInfoDao adminUserDao;


    @Override
    public List<SysPermission> loadUserPermission(Integer id) {
        List<SysPermission> perlist = new ArrayList<SysPermission>();
        SysUserInfo user = adminUserDao.findOne(id);
        if (user.getRoles().size() > 0) {
            user.getRoles().stream()
                    .filter(role -> role.getPermissions().size() > 0)
                    .forEach(role -> {
                        perlist.addAll(role.getPermissions().stream().filter(p -> p.getParentId() > 0).collect(Collectors.toList()));
                    });
        }
        return perlist;
    }

    @Override
    public List<SysPermission> loadUserPermissionByType(Integer id, Integer type) {
        List<SysPermission> perlist = new ArrayList<>();
        SysUserInfo user = adminUserDao.findOne(id);
        if (user.getRoles().size() > 0) {
            user.getRoles().stream()
                    .filter(role -> role.getPermissions().size() > 0)
                    .forEach(role -> {
                        perlist.addAll(role.getPermissions().stream().filter(p ->p.getParentId() > 0 && p.getType() == type)
                                .sorted(Comparator.comparing(SysPermission::getSort))
                                .collect(Collectors.toList()));
                    });
        }

        return perlist;
    }

    @Override
    public List<SysPermission> findPermissionsAndSelected(Integer id) {
        Set<SysPermission> permissions = roleService.findOne(id).getPermissions();
        List<SysPermission> all = repository.findAll();
        for (SysPermission p: all) {
            if (permissions.contains(p)) p.setChecked("true");
            else p.setChecked("false");
        }
        return all;
    }

    @Override
    public Page<SysPermission> findByPage(int pageNo, int length) {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "sort");
        Sort sort = new Sort(order);
        PageRequest pageRequest = new PageRequest(pageNo, length,sort);
        Page<SysPermission> page = repository.findAll(pageRequest);
        return page;
    }

}
