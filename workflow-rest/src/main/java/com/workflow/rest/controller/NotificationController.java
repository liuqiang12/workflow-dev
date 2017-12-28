package com.workflow.rest.controller;

import com.workflow.common.base.BaseController;
import com.workflow.common.dto.Result;
import com.workflow.common.entity.Notification;
import com.workflow.common.entity.User;
import com.workflow.rest.service.NotificationService;
import com.workflow.rest.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("通知消息接口")
@RequestMapping("/notification")
@RestController
public class NotificationController extends BaseController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @ApiOperation("获取用户的通知消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",value = "用户的id",dataType ="int")
    })
    @GetMapping("/{uid}")
    public Result getAllNotification(@PathVariable("uid") Integer uid) {
        Result result = restProcessor(() -> {
            User user = userService.findOne(uid);
            if (user==null) return Result.warn("用户不存在！");
            List<Notification> list = notificationService.findByUser(user);
            return Result.ok(list);
        });
        return result;
    }

    @ApiOperation("删除用户的通知消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid",value = "用户的id",dataType ="int")
    })
    @DeleteMapping("/{uid}")
    public Result deleteAllNotification(@PathVariable("uid") Integer uid){
        Result result = restProcessor(() -> {
            User user = userService.findOne(uid);
            if (user == null) return Result.warn("用户不存在！");
            notificationService.deleteByUser(user);
            return Result.ok();
        });

        return result;
    }

}
