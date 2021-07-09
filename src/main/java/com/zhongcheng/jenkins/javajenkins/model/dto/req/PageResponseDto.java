package com.zhongcheng.jenkins.javajenkins.model.dto.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PageResponseDto<T> {
    @NotNull(message = "当前第几页不能为空")
    private Integer page;
    @NotNull(message = "总页数不能为空")
    private Integer size;
    private List<T> filter;
}
