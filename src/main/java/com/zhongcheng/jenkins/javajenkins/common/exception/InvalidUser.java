package com.zhongcheng.jenkins.javajenkins.common.exception;

import com.zhongcheng.jenkins.javajenkins.model.ErrorEnum;

public class InvalidUser extends BaseException {
    public InvalidUser() {
        super(ErrorEnum.INVALID_USER);
    }
    public InvalidUser(String msg) {
        super(ErrorEnum.INVALID_USER, msg);
    }
}
