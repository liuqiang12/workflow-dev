package com.workflow.design.service.impl;

import com.workflow.design.service.ChatService;
import com.workflow.design.service.RedisService;
import com.workflow.common.base.BaseServiceImpl;
import com.workflow.common.dao.UserDao;
import com.workflow.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ChatServiceImpl extends BaseServiceImpl<UserDao,User> implements ChatService {

    @Autowired
    private RedisService<User> redisService;

    @Value("${REDIS_USER_KEY}")
    private String REDIS_USER_KEY;


    @Override
    public User getUserByToken(String token) {
        User user = redisService.getString(REDIS_USER_KEY + token);
        return user;
    }

    @Override
    public boolean authUser(Integer id) {
        User user = repository.findOne(id);
        return user.getEnable() == 1;
    }


}
