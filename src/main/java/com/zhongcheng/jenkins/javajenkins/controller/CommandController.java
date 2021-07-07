package com.zhongcheng.jenkins.javajenkins.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhongcheng.jenkins.javajenkins.common.exception.BaseException;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Command;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Pages;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Response;
import com.zhongcheng.jenkins.javajenkins.model.CommonResult;
import com.zhongcheng.jenkins.javajenkins.service.CommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.zhongcheng.jenkins.javajenkins.model.ErrorEnum.FAIL;

@RestController
public class CommandController {
    @Resource
    private CommandService commandService;
    final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/api/admin/command/create",method = RequestMethod.POST)
    public CommonResult<Object> create (@RequestBody Command command) {
        logger.info("result is ready ... ");
        boolean result = commandService.save(command);
        if (!result) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(command);
    }

    @RequestMapping(value = "/api/admin/command/update",method = RequestMethod.POST)
    public CommonResult<Object> update (@RequestBody Command command) {
        boolean result = commandService.updateById(command);
        if (!result) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(command.getId());
    }

    @RequestMapping(value = "/api/admin/command/detail",method = RequestMethod.POST)
    public CommonResult<Object> detail (@RequestBody Map<String, String> map) {
        String commandId = map.get("id");
        Command result = commandService.getById(commandId);
        if (result == null) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/api/admin/command/remove",method = RequestMethod.POST)
    public CommonResult<Object> remove (@RequestBody Map<String, String> map) {
        String commandId = map.get("id");
        boolean result = commandService.removeById(commandId);
        if (!result) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(commandId);
    }

    @RequestMapping(value = "/api/admin/command/search",method = RequestMethod.POST)
    public CommonResult<Object> index (@RequestBody Response<Map<String, String>> res) {
        //获取前台发送过来的数据
        Common<Command> common = new Common<>();
        IPage<Command> page = common.getPage(res);
        Pages<Command> result = commandService.findList(page, res);
        if (result == null) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/api/admin/command/run", method = RequestMethod.GET, produces = "text/event-stream")
    public SseEmitter run (@RequestParam("cid") String cid, HttpServletRequest request) {
        Long id = (Long) request.getAttribute("user");
        return commandService.run(cid, id);
    }
}
