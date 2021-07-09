package com.zhongcheng.jenkins.javajenkins.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongcheng.jenkins.javajenkins.common.exception.BaseException;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Command;
import com.zhongcheng.jenkins.javajenkins.dao.mapper.CommandMapper;
import com.zhongcheng.jenkins.javajenkins.model.ErrorEnum;
import com.zhongcheng.jenkins.javajenkins.service.RunProjectService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.*;
import java.util.*;

/**
 * 1.实现根据不同操作系统执行脚本命令
 * 2.获取每条命令的输出
 * 3.使用sse流式输出
 */
@Service
public class RunProjectServiceImpl extends ServiceImpl<CommandMapper, Command> implements RunProjectService {
    private static final HashMap<Long, SseEmitter> SSE_EMITTER_MAP = new HashMap<>(16);
    public SseEmitter run(String cmd, Long id) {
        Common.setSseEmitterMap(SSE_EMITTER_MAP, id);
        SseEmitter sseEmitter = SSE_EMITTER_MAP.get(id);
        new Thread(() -> {
            try {
                String sys = Common.getOsCmd();
                Runtime runtime = Runtime.getRuntime();
                Process child = runtime.exec(sys + Common.commandFormat(cmd));
                int code = child.waitFor();
                Common.send(child.getInputStream(), "stdout", sseEmitter);
                Common.send(child.getErrorStream(),"stderr", sseEmitter);
                sseEmitter.send(SseEmitter.event().name("close").data(Common.format("close:" + code)));
            } catch (IOException | InterruptedException e) {
                throw new BaseException(ErrorEnum.IO_ERROR);
            }
            sseEmitter.complete();
        }).start();
        return sseEmitter;
    }
}
