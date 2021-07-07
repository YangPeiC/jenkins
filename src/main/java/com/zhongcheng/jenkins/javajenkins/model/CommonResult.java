package com.zhongcheng.jenkins.javajenkins.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonResult<T> {
    private Integer err_code;
    private String msg;
    private T data;

    public CommonResult() {
    }

    public CommonResult(Integer err_code, String msg, T data) {
        this.err_code = err_code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getErr_code() {
        return err_code;
    }

    public void setErr_code(Integer err_code) {
        this.err_code = err_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getErrCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<>(ResultCode.SUCCESS.getErrCode(), message, data);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed(Integer errorCode, String message, T data) {
        return new CommonResult<>(errorCode, message, data);
    }
    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed(Integer errorCode, String message) {
        return new CommonResult<>(errorCode, message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<>(ResultCode.FAILED.getErrCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED.getErrCode(), ResultCode.FAILED.getMessage());
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED.getErrCode(), ResultCode.VALIDATE_FAILED.getMessage());
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<>(ResultCode.VALIDATE_FAILED.getErrCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getErrCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<>(ResultCode.FORBIDDEN.getErrCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }
}
