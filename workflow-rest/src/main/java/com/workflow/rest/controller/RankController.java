package com.workflow.rest.controller;

import com.workflow.common.base.BaseController;
import com.workflow.common.dto.Result;
import com.workflow.rest.service.RankService;
import com.workflow.rest.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "排序接口", description = "测试")
@RestController
@RequestMapping("/rank")
public class RankController extends BaseController {
    @Autowired
    private RankService rankService;

    @Autowired
    private RedisService<List<Object>> redisService;

    @Value("${REDIS_RANK_POSTS}")
    private String REDIS_RANK_POSTS;

    @Value("${REDIS_RANK_USERS}")
    private String REDIS_RANK_USERS;

    @ApiOperation("获取一个月内热帖排行榜")
    @GetMapping("/topPosts")
    public Result getTotPosts() {
        Result result = restProcessor(() -> {
            List<Object> hot = redisService.getString(REDIS_RANK_POSTS);
            if (hot != null) {
                return Result.ok(hot);
            }
            hot = rankService.findPostsRank();
            redisService.cacheString(REDIS_RANK_POSTS, hot, 1);
            return Result.ok(hot);
        });
        return result;
    }

    @ApiOperation("获取一个月内新注册的用户")
    @GetMapping("/newUsers")
    public Result getNewUser() {
        Result result = restProcessor(() -> {
            List<Object> users = redisService.getString(REDIS_RANK_USERS);
            if (users != null) return Result.ok(users);

            users = rankService.findUserRank();
            redisService.cacheString(REDIS_RANK_USERS, users, 1);
            return Result.ok(users);
        });
        return result;
    }

}
