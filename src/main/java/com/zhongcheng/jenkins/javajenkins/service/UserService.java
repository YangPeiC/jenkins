package com.zhongcheng.jenkins.javajenkins.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Pages;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Response;
import com.zhongcheng.jenkins.javajenkins.dao.entity.User;

import java.util.Map;

public interface UserService extends IService<User> {
    Pages<User> findList(IPage<User> page, Response<Map<String, String>> res);
}
