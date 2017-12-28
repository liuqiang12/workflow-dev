package com.workflow.rest.controller;

import com.workflow.common.base.BaseController;
import com.workflow.common.dto.Result;
import com.workflow.common.entity.Reply;
import com.workflow.common.entity.User;
import com.workflow.rest.service.ReplyService;
import com.workflow.rest.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "回复接口", description = "对帖子进行回复,点赞回复等服务")
@RestController
@RequestMapping("/reply")
public class ReplyController extends BaseController{

    @Autowired
    private WebSocketController webSocketController;

    @Autowired
    private UserService userService;

    @Autowired
    private ReplyService replyService;
    @ApiOperation("发布回复接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "回复内容", dataType = "String"),
            @ApiImplicitParam(name = "token", value = "用户令牌", dataType = "String"),
            @ApiImplicitParam(name = "postsId", value = "帖子ID", dataType = "Integer")
    })
    @PostMapping
    public Result CreateReply(Reply reply,Integer postsId,String token){
        Result result = restProcessor(() -> {
            if (token == null) return Result.warn("请先登录！");

            User userbytoken = userService.getUserByToken(token);
            if (userbytoken == null) return Result.warn("用户不存在,请先登录！");

            User user = userService.findOne(userbytoken.getId());
            if (user.getEnable() != 1) return Result.warn("用户处于封禁状态！");

            replyService.saveReply(reply, postsId, user);
            return Result.ok();
        });
        return result;
    }


}
