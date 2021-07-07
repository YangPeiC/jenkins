package com.zhongcheng.jenkins.javajenkins.common.exception;

import com.zhongcheng.jenkins.javajenkins.model.ErrorEnum;

public class BaseException extends RuntimeException {
    private final ErrorEnum err;
    private final String msg;
    public BaseException(ErrorEnum err) {
        this(err, err.getMessage());
    }

    public BaseException(ErrorEnum err, String msg) {
        super(msg);
        this.err = err;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return String.format("%s %s(%d)", this.getClass().toString(), this.msg, this.err.getErr_code());
    }

    public Integer getErrCode() {
        return err.getErr_code();
    }
}
