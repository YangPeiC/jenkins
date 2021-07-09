package com.zhongcheng.jenkins.javajenkins.model;

public enum ErrorEnum {

    SessionError(-2, "请先登录！"),
    INVALID_USER(-3,"无效的用户"),
    INVALID_VERIFY_CODE(501, "账号或者密码错误"),
    FAIL(-1,"发生错误了！"),
    OK(0,"ok！"),
    DetailNotExists(4, "该详细信息不存在"),
    IO_ERROR(8, "IO出错了"),

    // 用户系统
    USER_PHONE_EXISTS(101, "电话号码已存在"),
    USER_NOT_EXISTS(102, "该用户不存在"),

    //项目系统
    PROJECT_NOT_EXISTS(201,"该项目不存在");


    private String message;
    private Integer err_code;

    ErrorEnum(Integer err_code, String message) {
        this.message = message;
        this.err_code = err_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErr_code() {
        return err_code;
    }

    public void setErr_code(Integer err_code) {
        this.err_code = err_code;
    }
}
