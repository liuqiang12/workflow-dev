package com.workflow.oauth.controller;

import com.workflow.common.base.BaseController;
import com.workflow.common.dto.PageResult;
import com.workflow.common.dto.Result;
import com.workflow.common.entity.SysUserInfo;
import com.workflow.oauth.service.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController{

    @Autowired
    private SysUserInfoService userService;

    @GetMapping
    public PageResult getAll(SysUserInfo user, String draw,
                             @RequestParam(required = false, defaultValue = "1") int start,
                             @RequestParam(required = false, defaultValue = "10") int length) {
        int pageNo = start / length;
        Page<SysUserInfo> page = userService.findByPage(user, pageNo, length);
        PageResult<List<SysUserInfo>> result = new PageResult<>(
                draw,
                page.getTotalElements(),
                page.getTotalElements(),
                page.getContent());
        return result;
    }

    @PostMapping("/saveUserEnable")
    public Result saveUserEnable(@RequestParam(value = "id[]") Integer[] id){
        Result result = restProcessor(() -> {
            userService.saveUserEnable(id);
            return Result.ok();
        });
        return result;
    }

}
