package com.zhongcheng.jenkins.javajenkins.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhongcheng.jenkins.javajenkins.dao.entity.User;

public interface LoginService extends IService<User> {
    User updatePwd(Long id, String oldPwd ,String passwd);
    boolean isTruePasswd(String dest, String src);
    User login(Object account, Object passwd);
}
