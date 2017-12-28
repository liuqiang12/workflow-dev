package com.workflow.oauth.controller;

import com.workflow.common.base.BaseController;
import com.workflow.common.dto.PageResult;
import com.workflow.common.dto.Result;
import com.workflow.common.entity.SysPermission;
import com.workflow.oauth.service.SysPermissionService;
import com.workflow.oauth.shiro.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@RestController
@RequestMapping("/permissions")
public class SysPermissionController extends BaseController {
    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ShiroService shiroService;

    @PostMapping("/loadMenu")
    public List<SysPermission> loadMenu(){
        Integer userid = (Integer) SecurityUtils.getSubject().getSession().getAttribute("AdminSessionId");
        List<SysPermission> list = permissionService.loadUserPermissionByType(userid,1);
        return list;
    }

    @PostMapping("/PermissionWithSelected")
    public Result PermissionWithSelected(Integer roleId){
        Result result = restProcessor(() -> {
            List<SysPermission> data = permissionService.findPermissionsAndSelected(roleId);
            return Result.ok(data);
        });

        return result;
    }

    @GetMapping
    public PageResult getAll(String draw,
                             @RequestParam(required = false, defaultValue = "1") int start,
                             @RequestParam(required = false, defaultValue = "10") int length){
        int pageNo = start/length;
        Page<SysPermission> page = permissionService.findByPage(pageNo, length);
        PageResult<List<SysPermission>> result = new PageResult<>(
                draw,
                page.getTotalElements(),
                page.getTotalElements(),
                page.getContent());

        return result;
    }

    @PostMapping("/add")
    public Result add(SysPermission permission) {
        Result result = restProcessor(() -> {
            permissionService.save(permission);
            //更新权限
            shiroService.updatePermission();
            return Result.ok();
        });
        return result;
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam(value = "id[]") SysPermission[] id){
        Result result = restProcessor(() -> {
            permissionService.deleteInBatch(Arrays.asList(id));
            //更新权限
            shiroService.updatePermission();
            return Result.ok();
        });
        return result;
    }

}
