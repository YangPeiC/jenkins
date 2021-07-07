package com.zhongcheng.jenkins.javajenkins.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhongcheng.jenkins.javajenkins.common.exception.BaseException;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Pages;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Response;
import com.zhongcheng.jenkins.javajenkins.dao.entity.User;
import com.zhongcheng.jenkins.javajenkins.model.CommonResult;
import com.zhongcheng.jenkins.javajenkins.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

import static com.zhongcheng.jenkins.javajenkins.model.ErrorEnum.DetailNotExists;
import static com.zhongcheng.jenkins.javajenkins.model.ErrorEnum.FAIL;

@RestController
public class UserController {

    @Resource
    private UserService userService;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @RequestMapping(value = "/api/admin/user/create",method = RequestMethod.POST)
    public CommonResult<User> create (@RequestBody User user) {
        String newPwd = "{bcrypt}" + passwordEncoder.encode(user.getPasswd());
        user.setPasswd(newPwd);
        boolean result = userService.save(user);
        if (!result) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(user);
    }

    @RequestMapping(value = "/api/admin/user/update",method = RequestMethod.POST)
    public CommonResult<Long> update (@RequestBody User user) {
        String newPwd = "{bcrypt}" + passwordEncoder.encode(user.getPasswd());
        user.setPasswd(newPwd);
        boolean result = userService.updateById(user);
        if (!result) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(user.getId());
    }

    @RequestMapping(value = "/api/admin/user/detail",method = RequestMethod.POST)
    public CommonResult<User> detail (@RequestBody Map<String, String> map) {
        String userId = map.get("id");
        User result = userService.getById(userId);
        if (result == null) {
            throw new BaseException(DetailNotExists);
        }
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/api/admin/user/remove",method = RequestMethod.POST)
    public CommonResult<String> remove (@RequestBody Map<String, String> map) {
        String userId = map.get("id");
        boolean result = userService.removeById(userId);
        if (!result) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(userId);
    }

    @RequestMapping(value = "/api/admin/user/search",method = RequestMethod.POST)
        public CommonResult<Pages<User>> index (@RequestBody Response<Map<String, String>> res) {
        //获取前台发送过来的数据
        Common<User> common = new Common<>();
        IPage<User> page = common.getPage(res);
        Pages<User> result = userService.findList(page, res);
        if (result == null) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(result);
    }
}
