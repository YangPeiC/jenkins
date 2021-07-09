package com.zhongcheng.jenkins.javajenkins.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhongcheng.jenkins.javajenkins.common.exception.BaseException;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Command;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Pages;
import com.zhongcheng.jenkins.javajenkins.model.CommonResult;
import com.zhongcheng.jenkins.javajenkins.model.dto.req.AddAndUpdateCommandDto;
import com.zhongcheng.jenkins.javajenkins.model.dto.req.DeleteAndDetailDto;
import com.zhongcheng.jenkins.javajenkins.model.dto.req.PageResponseDto;
import com.zhongcheng.jenkins.javajenkins.service.CommandService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
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

    @RequestMapping(value = "/api/admin/command/create",method = RequestMethod.POST)
    public CommonResult<Command> create (@RequestBody @Validated AddAndUpdateCommandDto dto) {
        Command command = new Command();
        BeanUtils.copyProperties(dto, command);
        boolean result = commandService.save(command);
        if (!result) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(command);
    }

    @RequestMapping(value = "/api/admin/command/update",method = RequestMethod.POST)
    public CommonResult<Command> update (@RequestBody @Validated AddAndUpdateCommandDto dto) {
        Command command = new Command();
        BeanUtils.copyProperties(dto, command);
        boolean result = commandService.updateById(command);
        if (!result) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(command);
    }

    @RequestMapping(value = "/api/admin/command/detail",method = RequestMethod.POST)
    public CommonResult<Command> detail (@RequestBody @Validated DeleteAndDetailDto dto) {
        Command result = commandService.getById(dto.getId());
        if (result == null) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/api/admin/command/remove",method = RequestMethod.POST)
    public CommonResult<Long> remove (@RequestBody @Validated DeleteAndDetailDto dto) {
        boolean result = commandService.removeById(dto.getId());
        if (!result) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(dto.getId());
    }

    @RequestMapping(value = "/api/admin/command/search",method = RequestMethod.POST)
    public CommonResult<Pages<Command>> index (@RequestBody @Validated PageResponseDto<Map<String, String>> res) {
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
