package com.workflow.oauth.jwt.service;

import com.workflow.common.base.BaseService;
import com.workflow.common.entity.system.SysUserInfo;
import org.springframework.data.domain.Page;


public interface SysUserInfoService extends BaseService<SysUserInfo>{

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    SysUserInfo findByUsername(String username);

    /**
     * 根据翻页信息获取用户列表
     * @param adminUser
     * @param pageNo
     * @param length
     * @return
     */
    Page<SysUserInfo> findByPage(SysUserInfo adminUser, int pageNo, int length);

    /**
     * 保存用户
     * @param entity
     * @return
     */
    void saveUser(SysUserInfo entity);

    /**
     * 保存用户的角色
     * @param uid
     * @param roles
     * @return
     */
    void saveUserRoles(Integer uid,Integer[] roles);

    /**
     * 开启/禁用用户
     * @param ids
     */
    void saveUserEnable(Integer[] ids);

}
