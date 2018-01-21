package com.workflow.oauth.jwt.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by DELL on 2018/1/21.
 */
@Controller
@EnableAutoConfiguration
public class OAuth2RestController {
    @RequestMapping("/")
    public String index(Model model) {
        return "login";
    }

    @RequestMapping("/loginError")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping("/session_expired")
    public String sessionExpired(Model model) {
        model.addAttribute("loginError", true);
        return "session_expired";
    }
}
