package com.workflow.oauth.controller;

import com.workflow.oauth.service.LabelService;
import com.workflow.common.base.BaseController;
import com.workflow.common.dto.PageResult;
import com.workflow.common.dto.Result;
import com.workflow.common.entity.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/labels")
@RestController
public class LabelController extends BaseController {

    @Autowired
    private LabelService labelService;

    @GetMapping
    public PageResult getAll(String draw,
                             @RequestParam(required = false, defaultValue = "1") int start,
                             @RequestParam(required = false, defaultValue = "10") int length) {

        int pageNo = start / length;
        Page<Label> page = labelService.findByPage(pageNo, length);
        PageResult<List<Label>> result = new PageResult<>(draw,
                page.getTotalElements(),
                page.getTotalElements(),
                page.getContent());

        return result;
    }

    @PostMapping("/delete")
    public Result deleteLabels(@RequestParam(value = "id[]") Label[] id) {
        Result result = restProcessor(() -> {
            labelService.deleteInBatch(Arrays.asList(id));
            return Result.ok();
        });
        return result;
    }

    @PostMapping("/add")
    public Result addLabels(Label label){
        Result result = restProcessor(() -> {
            labelService.save(label);
            return Result.ok();
        });

        return result;
    }

}
