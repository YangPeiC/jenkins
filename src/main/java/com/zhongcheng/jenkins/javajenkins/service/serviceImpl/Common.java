package com.zhongcheng.jenkins.javajenkins.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhongcheng.jenkins.javajenkins.common.exception.BaseException;
import com.zhongcheng.jenkins.javajenkins.model.ErrorEnum;
import com.zhongcheng.jenkins.javajenkins.model.dto.req.PageResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.*;
import java.util.*;

@Slf4j
public class Common<T> {

    /**
     * 设置分页查找的筛选条件
     */
    public QueryWrapper<T> pageFilter (PageResponseDto<Map<String, String>> obj) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        List<java.util.Map<String, String>> list = obj.getFilter();
        if (list.size() == 0) {
            return wrapper.orderByDesc("id");
        }
        HashMap<String, String> map = (HashMap<String, String>) list.get(0);
        String key = map.get("key");
        String op = map.get("op");
        String value = map.get("value");
        if (op.equals("=") || op.equals("==")) {
            wrapper.eq(key, value);
        }
        else if (op.equals("like")) {
            wrapper.like(key, value);
        }
        wrapper.orderByDesc("id");
        return  wrapper;
    }

    /**
     * 格式化执行命令
     */
    public static String commandFormat(String cmd) {
        String[] cmds = cmd.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String unit : cmds) {
            sb.append(" && ");
            sb.append(unit);
        }
        return sb.substring(4);
    }


    /**
     * 根据用户id构建sseMap
     */
    public static void setSseEmitterMap(HashMap<Long, SseEmitter> SSE_EMITTER_MAP, Long id) {
        //0L设置永不超时
        SseEmitter sseEmitter = new SseEmitter(0L);
        SSE_EMITTER_MAP.put(id, sseEmitter);
    }

    /**
     * 格式化输出格式
     */
    public static String format (String mes) {
        mes = mes.replaceAll("\r\n", "\\\\n");
        return "\"" + mes + "\"";
    }


    /**
     * 读取InputStream，读取255个字符，转化为String，sse输出内容
     */
    public static void send(InputStream input, String event, SseEmitter sse){
        Reader reader;
        try {
            reader = new InputStreamReader(input, "GBK");
        } catch (UnsupportedEncodingException e) {
            throw new BaseException(ErrorEnum.IO_ERROR);
        }
        while (true) {
            try {
                char [] buff = new char[255]; //缓存大小
                int length = reader.read(buff);
                sse.send(SseEmitter.event().name(event).data(format("" + String.valueOf(buff))));
                if (-1 == length) {
                    break;
                }
            } catch (IOException | NullPointerException e) {
                throw new BaseException(ErrorEnum.IO_ERROR);
            }
        }
    }

    /**
     * 判断操作系统
     */
    public static String getOsCmd() {
        Properties props = System.getProperties(); //获得系统属性集
        String osName = props.getProperty("os.name"); //操作系统名称
        log.info("osName:" + osName);
        if (osName.toLowerCase().contains("linux")) {
            return "/bin/sh -c ";

        } else if (osName.toLowerCase().contains("windows")) {
            return "cmd /c ";

        } else {
            throw new RuntimeException("服务器不是linux|windows操作系统");
        }
    }

}
