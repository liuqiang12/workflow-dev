package com.workflow.oauth.service;

import com.workflow.common.base.BaseService;
import com.workflow.common.dto.Result;
import com.workflow.common.entity.SysPermission;
import com.workflow.common.entity.SysRoleInfo;
import org.springframework.data.domain.Page;

public interface SysRoleInfoService extends BaseService<SysRoleInfo> {

    /**
     * 根据用户id查询用户的角色
     *
     * @param id
     * @return
     */
    Result findRolesAndSelected(Integer id);

    /**
     * 翻页查询
     * @param pageNo
     * @param length
     * @return
     */
    Page<SysRoleInfo> findByPage(int pageNo, int length);

    /**
     * 保存角色的权限
     * @param roleid
     * @param pers
     */
    void saveRolePermission(Integer roleid, SysPermission[] pers);
}
