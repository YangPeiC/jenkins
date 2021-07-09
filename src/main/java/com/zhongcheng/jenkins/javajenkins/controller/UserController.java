package com.zhongcheng.jenkins.javajenkins.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhongcheng.jenkins.javajenkins.common.exception.BaseException;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Pages;
import com.zhongcheng.jenkins.javajenkins.dao.entity.User;
import com.zhongcheng.jenkins.javajenkins.model.CommonResult;
import com.zhongcheng.jenkins.javajenkins.model.dto.req.AddAndUpdateUserDto;
import com.zhongcheng.jenkins.javajenkins.model.dto.req.DeleteAndDetailDto;
import com.zhongcheng.jenkins.javajenkins.model.dto.req.PageResponseDto;
import com.zhongcheng.jenkins.javajenkins.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

import static com.zhongcheng.jenkins.javajenkins.model.ErrorEnum.DetailNotExists;
import static com.zhongcheng.jenkins.javajenkins.model.ErrorEnum.FAIL;

@Slf4j
@RestController
public class UserController {

    @Resource
    private UserService userService;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @RequestMapping(value = "/api/admin/user/create",method = RequestMethod.POST)
    public CommonResult<User> create (@RequestBody @Validated AddAndUpdateUserDto dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        String newPwd = "{bcrypt}" + passwordEncoder.encode(user.getPasswd());
        user.setPasswd(newPwd);

        boolean result = userService.save(user);
        if (!result) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(user);
    }

    @RequestMapping(value = "/api/admin/user/update",method = RequestMethod.POST)
    public CommonResult<Long> update (@RequestBody @Validated AddAndUpdateUserDto dto) {
        User user = new User();
        BeanUtils.copyProperties(dto,user);
        String newPwd = "{bcrypt}" + passwordEncoder.encode(user.getPasswd());
        user.setPasswd(newPwd);
        boolean result = userService.updateById(user);
        if (!result) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(user.getId());
    }

    @RequestMapping(value = "/api/admin/user/detail",method = RequestMethod.POST)
    public CommonResult<User> detail (@RequestBody @Validated DeleteAndDetailDto dto) {
        User result = userService.getById(dto.getId());
        if (result == null) {
            throw new BaseException(DetailNotExists);
        }
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/api/admin/user/remove",method = RequestMethod.POST)
    public CommonResult<String> remove (@RequestBody @Validated DeleteAndDetailDto dto) {
        boolean result = userService.removeById(dto.getId());
        if (!result) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success("successful");
    }

    @RequestMapping(value = "/api/admin/user/search",method = RequestMethod.POST)
        public CommonResult<Pages<User>> index (@RequestBody @Validated PageResponseDto<Map<String, String>> res) {
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
