package com.zhongcheng.jenkins.javajenkins.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Num;

import java.util.List;

public interface NumService extends IService<Num> {
    List<Integer> search();
}
