package com.workflow.oauth.controller;

import com.workflow.oauth.service.AdminUserService;
import com.workflow.common.base.BaseController;
import com.workflow.common.dto.PageResult;
import com.workflow.common.dto.Result;
import com.workflow.common.entity.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminUserController extends BaseController {

    @Autowired
    private AdminUserService adminUserService;

    /**
     * 翻页获取管理员
     *
     * @param adminUser
     * @param draw:请求次数
     * @param start
     * @param length
     * @return
     */
    @GetMapping
    public PageResult getAll(AdminUser adminUser, String draw,
                             @RequestParam(required = false, defaultValue = "1") int start,
                             @RequestParam(required = false, defaultValue = "10") int length) {
        int pageNo = start / length;
        Page<AdminUser> page = adminUserService.findByPage(adminUser, pageNo, length);
        PageResult<List<AdminUser>> result = new PageResult<>(
                draw,
                page.getTotalElements(),
                page.getTotalElements(),
                page.getContent());
        return result;
    }

    @PostMapping("/add")
    public Result addAdmin(AdminUser adminUser) {

        Result result = restProcessor(() -> {
            if (adminUserService.findByUserName(adminUser.getUsername()) != null)
                return Result.error("用户名重复");
            adminUserService.saveAdmin(adminUser);
            return Result.ok();
        });

        return result;
    }

    @PostMapping("/delete")
    public Result deleteAdmin(@RequestParam(value = "id[]") AdminUser[] id) {

        Result result = restProcessor(() -> {
            List<AdminUser> collect = Arrays.asList(id);
            adminUserService.deleteInBatch(collect);
            return Result.ok();
        });
        return result;
    }


    @PostMapping("/saveAdminRoles")
    public Result saveAdminRoles(Integer uid, Integer[] id) {

        Result result = restProcessor(() -> {
            adminUserService.saveAdminRoles(uid, id);
            return Result.ok();
        });
        return result;
    }

    @PostMapping("/saveAdminEnable")
    public Result saveAdminEnable(@RequestParam(value = "id[]") Integer[] id) {
        Result result = restProcessor(() -> {
            adminUserService.saveAdminEnable(id);
            return Result.ok();
        });
        return result;
    }

}
