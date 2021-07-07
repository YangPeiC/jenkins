package com.zhongcheng.jenkins.javajenkins.dao.entity;

import lombok.Data;

import java.util.List;

@Data
public class Response<T> {
    private Integer page;
    private Integer size;
    private List<T> filter;
}
