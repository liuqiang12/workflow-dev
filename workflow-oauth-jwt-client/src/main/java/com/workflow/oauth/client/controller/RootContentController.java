package com.workflow.oauth.client.controller;

import com.workflow.common.entity.system.SysUserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 顶部控制器:[登录、主界面等]
 */
@Controller
@EnableAutoConfiguration
public class RootContentController {
    @Value("${user-authorization-uri}")
    private String userAuthorizationUri;


    @Value("${application-host}")
    private String host;


    @Value("${unityUserInfoUri}")
    private String unityUserInfoUri;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("userAuthorizationUri", userAuthorizationUri);
        model.addAttribute("host", host);
        model.addAttribute("unityUserInfoUri", unityUserInfoUri);
        model.addAttribute("state", UUID.randomUUID().toString());

        return "login";
    }

    /**
     * 登录界面
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("userAuthorizationUri", userAuthorizationUri);
        model.addAttribute("host", host);
        model.addAttribute("unityUserInfoUri", unityUserInfoUri);
        model.addAttribute("state", UUID.randomUUID().toString());
        return "login";
    }
    /**
     * 用户登录
     * @param request
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request, SysUserInfo user, Model model) {
        if (StringUtils.isEmpty(user.getUsername())||StringUtils.isEmpty(user.getPassword())){
            request.setAttribute("msg","用户名或者密码不能为空!");
            return "login";
        }
        model.addAttribute("userAuthorizationUri", userAuthorizationUri);
        model.addAttribute("host", host);
        model.addAttribute("unityUserInfoUri", unityUserInfoUri);
        model.addAttribute("state", UUID.randomUUID().toString());

        return "init";
    }
}
