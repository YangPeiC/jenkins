package com.zhongcheng.jenkins.javajenkins.controller;

import com.zhongcheng.jenkins.javajenkins.dao.entity.User;
import com.zhongcheng.jenkins.javajenkins.model.CommonResult;
import com.zhongcheng.jenkins.javajenkins.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Map;

@RestController
public class LoginController {
    @Resource
    private LoginService loginServer;
    final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/api/admin/login",method = RequestMethod.POST)
    public CommonResult<User> login(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        User user = loginServer.login(map.get("user"), map.get("pwd"));
        request.getSession().setAttribute("type", "admin");
        request.getSession().setAttribute("userId", user.getId());
        logger.info("login successful!");
        return CommonResult.success(user);
    }
    @RequestMapping(value = "/api/admin/update_pwd",method = RequestMethod.POST)
    public CommonResult<User> updatePwd(@RequestBody Map<String, String> map, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("user");
        User result = loginServer.updatePwd(userId, map.get("oldPwd"), map.get("newPwd"));
        return CommonResult.success(result);
    }
}
