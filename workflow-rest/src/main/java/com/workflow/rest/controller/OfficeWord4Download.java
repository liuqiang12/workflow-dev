package com.workflow.rest.controller;


import com.workflow.rest.utils.WordUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 测试下载word文档模板
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-06T20:25:53.736+08:00")
@Api(value = "office-word操作接口",description = "常见的导入导出")
@RestController
@RequestMapping("/officedownLoad")
public class OfficeWord4Download {
	private static final long serialVersionUID = 1L;
    @ApiOperation(value = "word导出接口", notes = "利用freemarker导出word模板:需要的参数可以通过form表单传递[word是固定的格式]")
    @PostMapping("/export2word/{name}/{sex}")
    public void upload(@PathVariable("name") String name,@PathVariable("sex") String sex,HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration<String> paramNames = req.getParameterNames();
        // 通过循环将表单参数放入键值对映射中
        while(paramNames.hasMoreElements()) {
            String key = paramNames.nextElement();
            String value = req.getParameter(key);
            //验空，如果有未填写的内容会报错，所以检测出来强制修改为“无”
            if (value.equals("")||value==null) {
                value="无";
            }
            map.put(key, value);
        }
        map.put("name",name);
        map.put("sex",sex);
        File file = null;
        InputStream in = null;
        ServletOutputStream out = null;
        try {
            // 调用工具类WordGenerator的createDoc方法生成Word文档
            file = WordUtil.createDoc(map, "word");
            in = new FileInputStream(file);

            resp.setCharacterEncoding("utf-8");
            resp.setContentType("application/msword");
            // 设置浏览器以下载的方式处理该文件默认名为resume.doc
            resp.addHeader("Content-Disposition", "attachment;filename=word.doc");

            out = resp.getOutputStream();
            byte[] buffer = new byte[1024];  // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while((bytesToRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } finally {
            if(in != null) in.close();
            if(out != null) out.close();
            if(file != null) file.delete(); // 删除临时文件
        }
    }

    @ApiOperation(value = "导出word列表信息", notes = "利用freemarker导出word模板:需要的参数可以通过form表单传递[word是固定的格式]")
    @PostMapping("/export2_listGrid_word/{name}/{sex}")
    public void export2_listGrid_word(@PathVariable("name") String name,@PathVariable("sex") String sex,HttpServletResponse resp)  throws ServletException, IOException {
        Map<String,Object> map = new HashMap<String,Object>();
        /**
         * 设置参数信息
         */
        List<Map<String,Object>> paramList = new ArrayList<Map<String,Object>>();
        for(int i = 0; i < 20 ; i++){
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("xhIndex",(i+1));
            paramList.add(param);
        }
        map.put("listRecord",paramList);
        map.put("sex",sex);
        map.put("name",name);
        File file = null;
        InputStream in = null;
        ServletOutputStream out = null;
        try {
            // 调用工具类WordGenerator的createDoc方法生成Word文档
            file = WordUtil.createDoc(map, "listGrid");
            in = new FileInputStream(file);

            resp.setCharacterEncoding("utf-8");
            resp.setContentType("application/msword");
            // 设置浏览器以下载的方式处理该文件默认名为resume.doc
            resp.addHeader("Content-Disposition", "attachment;filename=word.doc");

            out = resp.getOutputStream();
            byte[] buffer = new byte[1024];  // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while((bytesToRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } finally {
            if(in != null) in.close();
            if(out != null) out.close();
            if(file != null) file.delete(); // 删除临时文件
        }
    }
}
