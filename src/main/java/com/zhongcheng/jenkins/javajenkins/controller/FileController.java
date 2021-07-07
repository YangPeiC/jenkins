package com.zhongcheng.jenkins.javajenkins.controller;


import com.zhongcheng.jenkins.javajenkins.model.CommonResult;
import com.zhongcheng.jenkins.javajenkins.service.FileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
public class FileController {
    @Resource
    private FileService fileService;

    @RequestMapping(value = "/api/admin/upload/upload_file", method = RequestMethod.POST)
    public CommonResult<HashMap<String ,String>> upload(MultipartFile file) {
        HashMap<String ,String> map = fileService.upload(file);
        if (map == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(map);
    }
}
