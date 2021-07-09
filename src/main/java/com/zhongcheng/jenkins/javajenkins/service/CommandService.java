package com.zhongcheng.jenkins.javajenkins.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Command;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Pages;
import com.zhongcheng.jenkins.javajenkins.model.dto.req.PageResponseDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.Map;

public interface CommandService extends IService<Command> {
    Pages<Command> findList(IPage<Command> page, PageResponseDto<Map<String, String>> res);
    SseEmitter run(String cid, Long id);
}
