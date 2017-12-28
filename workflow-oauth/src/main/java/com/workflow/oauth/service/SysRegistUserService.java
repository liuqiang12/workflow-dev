package com.workflow.oauth.service;

import com.workflow.common.base.BaseService;
import com.workflow.common.entity.SysRegistUser;
import org.springframework.data.domain.Page;

public interface SysRegistUserService  extends BaseService<SysRegistUser> {

    /**
     * 翻页获取用户列表
     * @param user
     * @param pageNo
     * @param length
     * @return
     */
    Page<SysRegistUser> findByPage(SysRegistUser user, int pageNo, int length);

    /**
     * 恢复/封禁用户
     * @param id
     */
    void saveUserEnable(Integer[] id);
}
