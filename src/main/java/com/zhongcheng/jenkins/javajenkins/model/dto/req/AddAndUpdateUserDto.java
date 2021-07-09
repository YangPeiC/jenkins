package com.zhongcheng.jenkins.javajenkins.model.dto.req;


import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddAndUpdateUserDto {
    private Long id;
    @NotBlank(message = "用户名不能为空")
    private String account;
    @NotBlank(message = "密码不能为空")
    private String passwd;
}
