package com.workflow.oauth.client.controller;

import com.workflow.oauth.client.dto.AccessTokenDto;
import com.workflow.oauth.client.dto.AuthAccessTokenDto;
import com.workflow.oauth.client.dto.AuthCallbackDto;
import com.workflow.oauth.client.dto.AuthorizationCodeDto;
import com.workflow.oauth.client.service.Oauth2Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 客户端授权跳转控制器
 * Handle 'authorization_code'  type actions
 */
@Controller
public class AuthorizationCodeController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationCodeController.class);

    @Value("${user-authorization-uri}")
    private String userAuthorizationUri;


    @Value("${application-host}")
    private String host;


    @Value("${unityUserInfoUri}")
    private String unityUserInfoUri;

    @Autowired
    private Oauth2Service oauth2Service;

    /*
    *Entrance:   step-1
    **/
    @RequestMapping(value = "authorization_code", method = RequestMethod.GET)
    public String authorizationCode(Model model) {
        model.addAttribute("userAuthorizationUri", userAuthorizationUri);
        model.addAttribute("host", host);
        model.addAttribute("unityUserInfoUri", unityUserInfoUri);
        model.addAttribute("state", UUID.randomUUID().toString());
        return "authorization_code";
    }

    /**
    *Save state firstly
    *Redirect to oauth-server login page:   step-2
    **/
    @RequestMapping(value = "authorization_code", method = RequestMethod.POST)
    public String submitAuthorizationCode(AuthorizationCodeDto codeDto, HttpServletRequest request,Model model) throws Exception {
        System.out.println("模拟属性");
        codeDto.setUserAuthorizationUri(userAuthorizationUri);
        codeDto.setClientId("unity-client");
        codeDto.setResponseType("code");
        codeDto.setState(UUID.randomUUID().toString());
        codeDto.setRedirectUri("http://localhost:8081/authorization_code_callback");
        codeDto.setScope("read");
        /**
         {{userAuthorizationUri}}?response_type={{responseType}}&scope={{scope}}&client_id={{clientId}}&redirect_uri={{redirectUri}}&state={{state}}
         */
        //save stats  firstly
        WebUtils.saveState(request, codeDto.getState());

        //final String fullUri = codeDto.getFullUri();
        final String fullUri = "http://localhost:8080/oauth/authorize?response_type=code&scope=read&client_id=normal-app&redirect_uri=http%3A%2F%2Flocalhost%3A8081%2F%2Fauthorization_code_callback&state="+codeDto.getState();
        LOG.debug("Redirect to Oauth-Server URL: {}", fullUri);
        return "redirect:" + fullUri;
    }


    /*
   *Oauth callback (redirectUri):   step-3
   *
   *Handle 'code', go to 'access_token' ,validation oauth-server response data
   *
   *authorization_code_callback
   **/
    @RequestMapping(value = "authorization_code_callback")
    public String authorizationCodeCallback(AuthCallbackDto callbackDto, HttpServletRequest request, Model model) throws Exception {

        if (callbackDto.error()) {
            //Server response error
            model.addAttribute("message", callbackDto.getError_description());
            model.addAttribute("error", callbackDto.getError());
            return "redirect:oauth_error";
        } else if (correctState(callbackDto, request)) {
            //Go to retrieve access_token form
            AuthAccessTokenDto accessTokenDto = oauth2Service.createAuthAccessTokenDto(callbackDto);
            model.addAttribute("accessTokenDto", accessTokenDto);
            model.addAttribute("host", host);
            return "auth/code_access_token";
        } else {
            //illegal state
            model.addAttribute("message", "Illegal \"state\": " + callbackDto.getState());
            model.addAttribute("error", "Invalid state");
            return "redirect:oauth_error";
        }

    }


    /**
     *Use HttpClient to get access_token :   step-4
     *<p/>
     *Then, 'authorization_code' flow is finished,  use 'access_token'  visit resources now
     *
     *@param tokenDto AuthAccessTokenDto
     *@param model    Model
     *@return View
     *@throws Exception
     */
    @RequestMapping(value = "code_access_token", method = RequestMethod.POST)
    public String codeAccessToken(AuthAccessTokenDto tokenDto, Model model) throws Exception {
        final AccessTokenDto accessTokenDto = oauth2Service.retrieveAccessTokenDto(tokenDto);
        if (accessTokenDto.error()) {
            model.addAttribute("message", accessTokenDto.getErrorDescription());
            model.addAttribute("error", accessTokenDto.getError());
            return "oauth_error";
        } else {
            model.addAttribute("accessTokenDto", accessTokenDto);
            model.addAttribute("unityUserInfoUri", unityUserInfoUri);
            return "access_token_result";
        }
    }


    /**
     *Check the state is correct or not after redirect from Oauth Server.
     */
    private boolean correctState(AuthCallbackDto callbackDto, HttpServletRequest request) {
        final String state = callbackDto.getState();
        return WebUtils.validateState(request, state);
    }
}
