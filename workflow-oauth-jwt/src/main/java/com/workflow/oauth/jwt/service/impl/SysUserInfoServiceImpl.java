package com.workflow.oauth.jwt.service.impl;

import com.workflow.common.base.BaseServiceImpl;
import com.workflow.common.dao.SysUserInfoDao;
import com.workflow.common.entity.SysRoleInfo;
import com.workflow.common.entity.SysUserInfo;
import com.workflow.oauth.jwt.service.SysRoleInfoService;
import com.workflow.oauth.jwt.utils.PasswordHelper;
import com.workflow.oauth.jwt.service.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

import static java.util.stream.Collectors.toSet;


@Service
@Transactional
public class SysUserInfoServiceImpl extends BaseServiceImpl<SysUserInfoDao, SysUserInfo> implements SysUserInfoService {

    @Autowired
    private SysRoleInfoService roleService;

    @Override
    public SysUserInfo findByUsername(String userName) {
        return repository.findByUsername(userName);
    }

    @Override
    public Page<SysUserInfo> findByPage(SysUserInfo userInfo, int pageNo, int length) {
        PageRequest pageable = new PageRequest(pageNo, length);

        Specification<SysUserInfo> specification = new Specification<SysUserInfo>() {

            @Override
            public Predicate toPredicate(Root<SysUserInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Integer> $id = root.get("id");
                Path<String> $username = root.get("username");
                Path<Integer> $enable = root.get("enable");

                ArrayList<Predicate> list = new ArrayList<>();
                if (userInfo.getId() != null) list.add(criteriaBuilder.equal($id, userInfo.getId()));
                if (userInfo.getEnable() != null) list.add(criteriaBuilder.equal($enable, userInfo.getEnable()));
                if (userInfo.getUsername() != null)
                    list.add(criteriaBuilder.like($username, "%" + userInfo.getUsername() + "%"));

                Predicate predicate = criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
                return predicate;
            }
        };

        Page<SysUserInfo> page = repository.findAll(specification, pageable);

        return page;
    }

    @Override
    public void saveUser(SysUserInfo entity) {
        entity.setEnable(1);
        PasswordHelper passwordHelper = new PasswordHelper();
        passwordHelper.encryptPassword(entity);
        save(entity);
    }

    @Override
    public void saveUserRoles(Integer uid, Integer[] roles) {
        SysUserInfo adminUser = findOne(uid);
        if (roles == null) {
            adminUser.setRoles(new HashSet<>());
        } else {
            Set<SysRoleInfo> roleSet = roleService.findAll(Arrays.asList(roles)).stream().collect(toSet());
            adminUser.setRoles(roleSet);
        }
        save(adminUser);
    }

    @Override
    public void saveUserEnable(Integer[] ids) {

        List<SysUserInfo> all = findAll(Arrays.asList(ids));
        for (SysUserInfo user : all) {
            if (user.getEnable() == 1) {
                user.setEnable(0);
            } else {
                user.setEnable(1);
            }
        }
        save(all);
    }
}
