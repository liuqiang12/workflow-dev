package com.workflow.rest.service;

import com.workflow.common.dto.SocketMessage;
import com.workflow.rest.controller.WebSocketController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private RedisService<Integer> redisService;

//    @Autowired
//    private UserService userService;

    @Autowired
    private WebSocketController webSocketController;


    @Value("${REDIS_USERID_KEY}")
    private String REDIS_USERID_KEY;

    /**
     * 广播
     * @param message
     */
    public void sentToAll(SocketMessage message){
        webSocketController.template.convertAndSend("/topic/all",message);
    }

    /**
     * 单播
     * @param id
     */
    public void sendToOne(Integer id){
        boolean islogin = redisService.setHasValue(REDIS_USERID_KEY, id);
        //判断用户是否已经登录
//        boolean islogin = userService.loginId.contains(id);
        if (islogin){
            //查询未查看的通知
           /* long count = notificationService.getNotificationCount(id);*/
            SocketMessage message = SocketMessage.build((int) 0);
            webSocketController.template.convertAndSendToUser(id+"","/message",message);
        }

    }
}
