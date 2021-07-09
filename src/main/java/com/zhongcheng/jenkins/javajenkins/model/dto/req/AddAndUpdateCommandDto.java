package com.zhongcheng.jenkins.javajenkins.model.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddAndUpdateCommandDto {
    private Long id;
    private Long projectId;
    @NotBlank(message = "介绍不能为空")
    private String instruction;
    @NotBlank(message = "命令不能为空")
    private String content;
}
