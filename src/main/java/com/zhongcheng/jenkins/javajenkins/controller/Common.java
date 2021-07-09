package com.zhongcheng.jenkins.javajenkins.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhongcheng.jenkins.javajenkins.model.dto.req.PageResponseDto;

import java.util.Map;


public class Common<T> {
    /**
     * 读取页面总数和当前页面
     */
    public IPage<T> getPage(PageResponseDto<Map<String, String>> res) {
        Integer pageNo = res.getPage();
        Integer pageSize = res.getSize();
        return new Page<>(pageNo, pageSize);
    }
}
