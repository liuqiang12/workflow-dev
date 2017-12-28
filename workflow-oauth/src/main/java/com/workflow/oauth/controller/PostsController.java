package com.workflow.oauth.controller;

import com.workflow.oauth.service.PostsService;
import com.workflow.oauth.service.UserService;
import com.workflow.common.base.BaseController;
import com.workflow.common.dto.PageResult;
import com.workflow.common.dto.Result;
import com.workflow.common.entity.Posts;
import com.workflow.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/posts")
@RestController
public class PostsController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostsService postsService;

    @GetMapping
    public PageResult getAll(Posts posts, Integer uid, String draw,
                             @RequestParam(required = false, defaultValue = "1") int start,
                             @RequestParam(required = false, defaultValue = "10") int length) {
        int pageNo = start / length;
        if (uid != null) {
            User user = userService.findOne(uid);
            posts.setUser(user);
        }
        Page<Posts> page = postsService.findByPage(posts, pageNo, length);
        PageResult<List<Posts>> result = new PageResult<>(
                draw,
                page.getTotalElements(),
                page.getTotalElements(),
                page.getContent());
        return result;
    }

    @PostMapping("/saveTop")
    public Result saveTop(@RequestParam(value = "id[]") Integer[] id) {
        Result result = restProcessor(() -> {
            postsService.changeTop(id);
            return Result.ok();
        });
        return result;
    }

    @PostMapping("/saveGood")
    public Result saveGood(@RequestParam(value = "id[]") Integer[] id) {
        Result result = restProcessor(() -> {
            postsService.changeGood(id);
            return Result.ok();
        });
        return result;
    }

    @PostMapping("/delete")
    public Result deletePosts(@RequestParam(value = "id[]") Posts[] id){
        Result result = restProcessor(() -> {
            postsService.deleteInBatch(Arrays.asList(id));
            return Result.ok();
        });
        return result;
    }
}
