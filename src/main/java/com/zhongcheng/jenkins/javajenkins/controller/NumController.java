package com.zhongcheng.jenkins.javajenkins.controller;

import com.zhongcheng.jenkins.javajenkins.common.exception.BaseException;
import com.zhongcheng.jenkins.javajenkins.model.CommonResult;
import com.zhongcheng.jenkins.javajenkins.service.NumService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.zhongcheng.jenkins.javajenkins.model.ErrorEnum.FAIL;

@RestController
public class NumController {
    @Resource
    private NumService numService;
    @RequestMapping(value = "/api/admin/num/select", method = RequestMethod.POST)
    public CommonResult<List<Integer>> search(){
        List<Integer> result = numService.search();
        if (result == null) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(result);
    }
}
