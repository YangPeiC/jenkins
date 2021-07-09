package com.zhongcheng.jenkins.javajenkins.model.dto.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteAndDetailDto {
    @NotNull(message = "主键不能为空")
    private Long id;
}
