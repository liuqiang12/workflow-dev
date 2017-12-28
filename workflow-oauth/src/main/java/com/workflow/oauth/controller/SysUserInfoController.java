package com.workflow.oauth.controller;

import com.workflow.common.base.BaseController;
import com.workflow.common.dto.PageResult;
import com.workflow.common.dto.Result;
import com.workflow.common.entity.SysUserInfo;
import com.workflow.oauth.service.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admins")
public class SysUserInfoController extends BaseController {

    @Autowired
    private SysUserInfoService userInfoService;

    /**
     * 翻页获取管理员
     *
     * @param SysUserInfo
     * @param draw:请求次数
     * @param start
     * @param length
     * @return
     */
    @GetMapping
    public PageResult getAll(SysUserInfo SysUserInfo, String draw,
                             @RequestParam(required = false, defaultValue = "1") int start,
                             @RequestParam(required = false, defaultValue = "10") int length) {
        int pageNo = start / length;
        Page<SysUserInfo> page = userInfoService.findByPage(SysUserInfo, pageNo, length);
        PageResult<List<SysUserInfo>> result = new PageResult<>(
                draw,
                page.getTotalElements(),
                page.getTotalElements(),
                page.getContent());
        return result;
    }

    @PostMapping("/add")
    public Result addAdmin(SysUserInfo SysUserInfo) {

        Result result = restProcessor(() -> {
            if (userInfoService.findByUserName(SysUserInfo.getUserName()) != null)
                return Result.error("用户名重复");
            userInfoService.saveUser(SysUserInfo);
            return Result.ok();
        });

        return result;
    }

    @PostMapping("/delete")
    public Result deleteAdmin(@RequestParam(value = "id[]") SysUserInfo[] id) {

        Result result = restProcessor(() -> {
            List<SysUserInfo> collect = Arrays.asList(id);
            userInfoService.deleteInBatch(collect);
            return Result.ok();
        });
        return result;
    }


    @PostMapping("/saveAdminRoles")
    public Result saveAdminRoles(Integer uid, Integer[] id) {

        Result result = restProcessor(() -> {
            userInfoService.saveUserRoles(uid, id);
            return Result.ok();
        });
        return result;
    }

    @PostMapping("/saveAdminEnable")
    public Result saveAdminEnable(@RequestParam(value = "id[]") Integer[] id) {
        Result result = restProcessor(() -> {
            userInfoService.saveUserEnable(id);
            return Result.ok();
        });
        return result;
    }

}
