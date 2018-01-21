package com.workflow.oauth.jwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
/*@EnableWebSecurity
@EnableAutoConfiguration*/
public class MainMenuController {

	
	@RequestMapping("/init")
	public String mainMenu(HttpServletRequest request, Principal principal, ModelAndView model) throws Exception  {
	//public String mainMenu(HttpServletRequest request ,Principal principal) {
		String loginId = principal.getName();
    	return "init";
	}
}	
