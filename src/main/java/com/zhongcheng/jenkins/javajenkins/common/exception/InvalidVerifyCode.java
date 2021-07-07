package com.zhongcheng.jenkins.javajenkins.common.exception;

import com.zhongcheng.jenkins.javajenkins.model.ErrorEnum;


public class InvalidVerifyCode extends BaseException {
    public InvalidVerifyCode() {
        super(ErrorEnum.INVALID_VERIFY_CODE);
    }
    public InvalidVerifyCode(String mes) {
        super(ErrorEnum.INVALID_VERIFY_CODE, mes);
    }
}
