package com.workflow.oauth.controller;

import com.workflow.oauth.service.ReplyService;
import com.workflow.oauth.service.UserService;
import com.workflow.common.base.BaseController;
import com.workflow.common.dto.PageResult;
import com.workflow.common.dto.Result;
import com.workflow.common.entity.Reply;
import com.workflow.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/replys")
@RestController
public class ReplyController extends BaseController {


    @Autowired
    private UserService userService;

    @Autowired
    private ReplyService replyService;

    @GetMapping
    public PageResult getAll(Reply reply, Integer uid, String draw,
                             @RequestParam(required = false, defaultValue = "1") int start,
                             @RequestParam(required = false, defaultValue = "10") int length) {
        int pageNo = start / length;
        if (uid!=null){
            User user = userService.findOne(uid);
            reply.setUser(user);
        }

        Page<Reply> page = replyService.findByPage(reply, pageNo, length);
        PageResult<List<Reply>> result = new PageResult<>(
                draw,
                page.getTotalElements(),
                page.getTotalElements(),
                page.getContent());
        return result;
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam(value = "id[]") Reply[] id){
        Result result = restProcessor(() -> {
            replyService.deleteInBatch(Arrays.asList(id));
            return Result.ok();
        });
        return result;
    }

}
