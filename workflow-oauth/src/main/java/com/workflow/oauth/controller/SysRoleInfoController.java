package com.workflow.oauth.controller;

import com.workflow.common.base.BaseController;
import com.workflow.common.dto.PageResult;
import com.workflow.common.dto.Result;
import com.workflow.common.entity.SysPermission;
import com.workflow.common.entity.SysRoleInfo;
import com.workflow.oauth.service.SysRoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class SysRoleInfoController extends BaseController {

    @Autowired
    private SysRoleInfoService roleService;

    @GetMapping
    public PageResult getAll(String draw,
                             @RequestParam(required = false, defaultValue = "1") int start,
                             @RequestParam(required = false, defaultValue = "10") int length) {
        int pageNo = start / length;
        Page<SysRoleInfo> page = roleService.findByPage(pageNo, length);
        PageResult<List<SysRoleInfo>> result = new PageResult<>(
                draw,
                page.getTotalElements(),
                page.getTotalElements(),
                page.getContent());

        return result;
    }

    @PostMapping("/rolesWithSelected")
    public Result rolesWithSelected(Integer uid) {
        Result result = roleService.findRolesAndSelected(uid);
        return result;
    }

    @PostMapping("/add")
    public Result add(SysRoleInfo role) {
        Result result = restProcessor(() -> {
            roleService.save(role);
            return Result.ok();
        });
        return result;
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam(value = "id[]") SysRoleInfo[] id){
        Result result = restProcessor(() -> {
            roleService.deleteInBatch(Arrays.asList(id));
            return Result.ok();
        });

        return result;
    }

    @PostMapping("/saveRolePermission")
    public Result saveRolePermission(Integer roleid, @RequestParam(value = "pers[]") SysPermission[] pers){

        Result result = restProcessor(() -> {
            roleService.saveRolePermission(roleid, pers);
            return Result.ok();
        });

        return result;
    }

}
