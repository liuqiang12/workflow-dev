package com.workflow.rest.controller;

import com.workflow.common.base.BaseController;
import com.workflow.common.dto.Result;
import com.workflow.common.entity.Label;
import com.workflow.common.entity.Posts;
import com.workflow.common.entity.Reply;
import com.workflow.common.entity.User;
import com.workflow.rest.service.LabelService;
import com.workflow.rest.service.PostsService;
import com.workflow.rest.service.ReplyService;
import com.workflow.rest.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Api(value = "接口", description = "asdfadsfsaf")
@RestController
@RequestMapping("/posts")
public class PostsController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private PostsService postsService;

    @Autowired
    private ReplyService replyService;

    @ApiOperation("发帖接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "帖子内容", dataType = "String"),
            @ApiImplicitParam(name = "title", value = "帖子标题", dataType = "String"),
            @ApiImplicitParam(name = "token", value = "用户令牌", dataType = "String"),
            @ApiImplicitParam(name = "labelId", value = "标签ID", dataType = "Integer")
    })
    @PostMapping
    public Result CreatePosts(Posts posts, String token, Integer labelId) {
        Result result = restProcessor(() -> {

            if (token == null) return Result.warn("请先登录！");

            User userbytoken = userService.getUserByToken(token);
            if (userbytoken == null) return Result.warn("用户不存在,请先登录！");

            User user = userService.findOne(userbytoken.getId());
            if (user.getEnable() != 1) return Result.warn("用户处于封禁状态！");

            postsService.savePosts(posts, labelId, user);
            return Result.ok();
        });

        return result;
    }

    @ApiOperation("翻页查询帖子接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "search", value = "查询条件", dataType = "int"),
            @ApiImplicitParam(name = "type", value = "帖子类型[top : good : ]", dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "页码[从1开始]", dataType = "int"),
            @ApiImplicitParam(name = "length", value = "返回结果数量[默认20]", dataType = "int")
    })
    @GetMapping()
    public Result GetPosts(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "") String type,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "20") int length) {
        Result result = restProcessor(() -> {
            if (!type.equals("good") && !type.equals("top") && !type.equals(""))
                return Result.error("类型错误!");
            Page<Posts> page = postsService.getPostsByPage(type, search, pageNo - 1, length);
            return Result.ok(page.getContent(), page.getTotalElements(), page.getNumberOfElements());

        });

        return result;

    }


    @ApiOperation("翻页帖子详情与回复接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postsid", value = "帖子的id", dataType = "int"),
            @ApiImplicitParam(name = "pageNo", value = "页码[从1开始]", dataType = "int"),
            @ApiImplicitParam(name = "length", value = "返回结果数量[默认20]", dataType = "int")
    })
    @GetMapping("/detail/{postsid}")
    public Result GetPostsDetail(
            @PathVariable("postsid") Integer postsid,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "20") int length) {
        Result result = restProcessor(() -> {
            HashMap<String, Object> map = new HashMap<>();
            Posts posts = postsService.findOne(postsid);
            if (posts == null) return Result.error("帖子不存在");
            map.put("posts", posts);

            Page<Reply> page = replyService.getReplyByPage(postsid, pageNo - 1, length);
            map.put("replys", page.getContent());

            return Result.ok(map, page.getTotalElements(), page.getNumberOfElements());
        });
        return result;

    }

    @ApiOperation("根据labelId获取帖子接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "labelid", value = "标签的id", dataType = "int"),
            @ApiImplicitParam(name = "pageNo", value = "页码[从1开始]", dataType = "int"),
            @ApiImplicitParam(name = "length", value = "返回结果数量[默认20]", dataType = "int"),
    })
    @GetMapping("/label/{labelid}")
    public Result GetPostsByLabel(
            @PathVariable("labelid") Integer labelid,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "20") int length) {

        Result result = restProcessor(() -> {
            Label label = labelService.findOne(labelid);
            if (label == null) return Result.error("标签不存在");
            Page<Posts> page = postsService.getPostsByLabel(label, pageNo - 1, length);
            return Result.ok(page.getContent(), page.getTotalElements(), page.getNumberOfElements());

        });

        return result;

    }

}
