package com.zhongcheng.jenkins.javajenkins.controller;

import com.zhongcheng.jenkins.javajenkins.service.RunProjectService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
public class RunProjectController {
    @Resource
    private RunProjectService runProjectService;

    @RequestMapping(value = "/api/admin/run_project/run", method = RequestMethod.GET)
    public SseEmitter runCmd (@RequestParam("command") String cmd, HttpServletRequest request) {
        Long id = (Long) request.getAttribute("user");
        return runProjectService.run(cmd, id);
    }
}
