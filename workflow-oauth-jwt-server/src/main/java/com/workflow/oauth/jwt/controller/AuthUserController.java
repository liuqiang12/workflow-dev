package com.workflow.oauth.jwt.controller;

import com.workflow.common.entity.SysUserInfo;
import com.workflow.oauth.jwt.service.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by DELL on 2018/1/3.
 */
@RestController
@RequestMapping("/resources")
public class AuthUserController {
    @Autowired
    private SysUserInfoService userInfoService;//用户服务层

    /**
     *
     * @param username
     * @return
     */
    @PreAuthorize("hasRole('ROLE_API_USER')")
    @RequestMapping(value = "/getAuthUserInfoByMobile", method = RequestMethod.GET)
    public @ResponseBody
    SysUserInfo getAuthUserInfoByMobile(@RequestParam(name = "username",required = true)String username) {
        SysUserInfo vo = userInfoService.findByUsername(username);
        return vo;
    }


    /**
     * 需要用户角色权限
     * @return
     */
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value="/user", method=RequestMethod.GET)
    public String helloUser() {
        return "hello user1111";
    }
    /**
     * 需要管理角色权限
     *
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/admin", method=RequestMethod.GET)
    public String helloAdmin() {
        return "hello admin";
    }
    /**
     * 需要客户端权限
     *
     * @return
     */
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @RequestMapping(value="/client", method=RequestMethod.GET)
    public String helloClient() {
        return "hello user authenticated by normal client";
    }
    /**
     * 需要受信任的客户端权限
     *
     * @return
     */
    @PreAuthorize("hasRole('ROLE_TRUSTED_CLIENT')")
    @RequestMapping(value="/trusted_client", method=RequestMethod.GET)
    public String helloTrustedClient() {
        return "hello user authenticated by trusted client";
    }

    @RequestMapping(value="principal", method=RequestMethod.GET)
    public Object getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal;
    }

    @RequestMapping(value="roles", method=RequestMethod.GET)
    public Object getRoles() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
