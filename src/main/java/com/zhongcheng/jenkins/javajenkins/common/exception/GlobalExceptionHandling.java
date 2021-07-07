package com.zhongcheng.jenkins.javajenkins.common.exception;

import com.zhongcheng.jenkins.javajenkins.model.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandling<Object> {
    @ExceptionHandler
    public CommonResult<Object> handle(Exception ex) {
        log.info("loading globalException ......");
        if (ex instanceof BindException) {
            BindException be = (BindException) ex;
            return CommonResult.failed(be.getAllErrors().get(0).getDefaultMessage());
        } else if (ex instanceof MethodArgumentNotValidException) {
            return CommonResult.failed(
                    ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().get(0).getDefaultMessage()
            );
        } else if (ex instanceof BaseException) {
            return CommonResult.failed( ((BaseException) ex).getErrCode(), ex.getMessage(), null);
        }
        log.error("[BaseController Err-Print]",ex);
        return CommonResult.failed("["+ex.getLocalizedMessage()+"]");
    }
}
