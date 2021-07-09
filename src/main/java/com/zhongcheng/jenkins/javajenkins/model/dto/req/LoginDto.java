package com.zhongcheng.jenkins.javajenkins.model.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {
    @NotBlank(message = "用户名不能为空")
    private String user;
    @NotBlank(message = "密码不能为空")
    private String pwd;
}
