package com.zhongcheng.jenkins.javajenkins.model;

public enum ResultCode {
    SUCCESS(0, "操作成功"),
    FAILED(501, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getErrCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }
}
