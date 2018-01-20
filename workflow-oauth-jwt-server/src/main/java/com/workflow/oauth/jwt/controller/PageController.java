package com.workflow.oauth.jwt.controller;

import com.workflow.common.entity.system.SysUserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PageController {

    /**
     * 登录界面
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
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
        return "init";
        /*Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());*/
       /* try {
           *//* subject.login(token);*//*
            return "redirect:/initPage";
        }catch (LockedAccountException lae) {
            token.clear();
            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
            return "login";
        } catch (AuthenticationException e) {
            token.clear();
            request.setAttribute("msg", "用户或密码不正确！");
            return "login";
            *//*return "init";*//*
        }*/
    }

    @RequestMapping("/initPage")
    public String InitPage(){
        return "init";
    }

    @RequestMapping("/adminsPage")
    public String AdminsPage(){
        return "oauth/admins";
    }

    @RequestMapping("/permissionsPage")
    public String PermissionPage(){
        return "permission/permissions";
    }

    @RequestMapping("/rolesPage")
    public String RolesPage(){
        return "role/roles";
    }

    @RequestMapping("/usersPage")
    public String UsersPage(){
        return "user/users";
    }

    @RequestMapping("/postsPage")
    public String PostsPage(){
        return "posts/posts";
    }

    @RequestMapping("/replysPage")
    public String ReplysPage(){
        return "reply/replys";
    }

    @RequestMapping("/labelsPage")
    public String LabelsPage(){
        return "label/labels";
    }
}
