package com.workflow.portal.service.impl;

import com.workflow.portal.entity.Result;
import com.workflow.portal.entity.User;
import com.workflow.portal.service.UserService;
import com.workflow.portal.utils.HttpClientUtils;
import com.workflow.portal.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Value("${api_getUserByToken}")
    private String api_getUserByToken;

    @Override
    public User getUserByApi(String token) {
        String s = HttpClientUtils.doGet(api_getUserByToken + token);
        Result Result = JsonUtils.jsonToResult(s, User.class);
        User data= (User) Result.getData();
        return data;
    }
}
