package com.zhongcheng.jenkins.javajenkins.model.dto.req;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddAndUpdateProjectDto {
    private Long id;
    private Long creatorId;
    @NotBlank(message = "项目名不为空")
    private String name;
}
