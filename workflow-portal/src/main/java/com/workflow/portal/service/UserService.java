package com.workflow.portal.service;

import com.workflow.portal.entity.User;

public interface UserService {

    User getUserByApi(String token);

    
}
