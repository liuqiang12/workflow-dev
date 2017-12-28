package com.workflow.oauth.service;

import com.workflow.common.base.BaseService;
import com.workflow.common.entity.SysPermission;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SysPermissionService extends BaseService<SysPermission>{

    /**
     * 根据用户id获取用户所有权限
     * @param id
     * @return
     */
    List<SysPermission> loadUserPermission(Integer id);

    /**
     * 根据用户id，type获取用户权限
     * @param id
     * @param type
     * @return
     */
    List<SysPermission> loadUserPermissionByType(Integer id,Integer type);

    /**
     * 根据角色ID查询用户权限
     * @param id
     * @return
     */
    List<SysPermission> findPermissionsAndSelected(Integer id);

    /**
     * 翻页查询
     * @param pageNo
     * @param length
     * @return
     */
    Page<SysPermission> findByPage(int pageNo, int length);


}
