package com.quark.rest.controller;

import com.quark.common.dto.UploadResult;
import com.quark.common.exception.ServiceProcessException;
import com.quark.rest.service.UserService;
import com.quark.rest.utils.FileUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * 文件上传的api
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-06T20:25:53.736+08:00")
@Api(value = "文件上传接口",description = "图片上传")
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UserService userService;
    @ApiOperation(value = "图片上传接口", notes = "图片上传接口说明，后期如果控制文件上传类型如doc或者ftp等都需要在界面控件中验证", response = UploadResult.class)
    @ApiResponses(
        value =
        { @ApiResponse(code = 200, message = "图片上传结果", response = UploadResult.class),
            @ApiResponse(code = 200, message = "返回错误信息", response = UploadResult.class)
        }
    )
    @PostMapping("/image")
    public UploadResult upload(@NotNull @ApiParam(value = "文件File对象", required = false)@RequestParam(value = "file", required = false)MultipartFile file) {
        UploadResult result = null;
        if (!file.isEmpty()) {
            try {
                String s = FileUtils.uploadFile(file);
              result = new UploadResult(0, new UploadResult.Data(s));
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                result = new UploadResult(1,"上传图片失败");
            }
            return result;
        }
        result = new UploadResult(1,"文件不存在");
        return result;
    }

    @ApiOperation("用户头像上传接口")
    @PostMapping("/usericon/{token}")
    public UploadResult iconUpload(@PathVariable("token") String token,@RequestParam("file") MultipartFile file){
        UploadResult result = null;
        if (!file.isEmpty()) {
            try {
                String icon = FileUtils.uploadFile(file);
                userService.updataUserIcon(token,icon);
                return new UploadResult(0, new UploadResult.Data(icon));

            } catch (IOException e) {
                e.printStackTrace();
                return new UploadResult(1,"上传图片失败");
            }catch (ServiceProcessException e1){
                e1.printStackTrace();
                return new UploadResult(1,e1.getMessage());
            }
        }
        return new UploadResult(1,"文件不存在");
    }



}
