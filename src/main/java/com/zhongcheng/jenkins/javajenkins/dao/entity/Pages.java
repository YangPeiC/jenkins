package com.zhongcheng.jenkins.javajenkins.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor     //全参构造函数
public class Pages<T> {
    public Pages(int count, List<T> rows) {
        this((long)count, rows);
    }
    private Long count;
    private List<T>  rows;
}
