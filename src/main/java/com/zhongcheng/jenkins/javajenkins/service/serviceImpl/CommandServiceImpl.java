package com.zhongcheng.jenkins.javajenkins.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Command;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Pages;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Response;
import com.zhongcheng.jenkins.javajenkins.dao.mapper.CommandMapper;
import com.zhongcheng.jenkins.javajenkins.service.CommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Service
public class CommandServiceImpl extends ServiceImpl<CommandMapper, Command> implements CommandService {
    @Resource
    private CommandService commandService;
    final Logger logger = LoggerFactory.getLogger(getClass());

    public Pages<Command> findList (IPage<Command> page, Response<Map<String, String>> res) {
        Common<Command> common= new Common<>();
        QueryWrapper<Command> wrapper = common.pageFilter(res);
        IPage<Command> commandPage = commandService.page(page, wrapper); //有条件分页
        List<Command> rows = commandPage.getRecords();
        int count = rows.size();
        return new Pages<>(count, rows);
    }

    /**
     * sse输出结果
     */
    private static final HashMap<Long, SseEmitter> SSE_EMITTER_MAP = new HashMap<>(16);
    public SseEmitter run(String cid, Long id) {
        Common.setSseEmitterMap(SSE_EMITTER_MAP, id);
        SseEmitter sseEmitter = SSE_EMITTER_MAP.get(id);
        Command command = commandService.getById(cid);
        String cmd = command.getContent();
        new Thread(() -> {
            try {
                String sys = Common.getOsCmd();
                Runtime runtime = Runtime.getRuntime();
                logger.info("commandFormat:" + sys + Common.commandFormat(cmd));
                Process child = runtime.exec(sys + Common.commandFormat(cmd));
                int code = child.waitFor();
                Common.send(child.getInputStream(), "stdout", sseEmitter);
                Common.send(child.getErrorStream(),"stderr", sseEmitter);
                sseEmitter.send(SseEmitter.event().name("close").data(Common.format("close:" + code)));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            sseEmitter.complete();
        }).start();
        return sseEmitter;
    }

}
