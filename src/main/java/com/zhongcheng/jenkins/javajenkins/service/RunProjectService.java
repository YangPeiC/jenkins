package com.zhongcheng.jenkins.javajenkins.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Command;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


public interface RunProjectService extends IService<Command> {
    SseEmitter run(String cmd, Long id);
}
