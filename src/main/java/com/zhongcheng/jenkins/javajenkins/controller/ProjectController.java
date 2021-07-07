package com.zhongcheng.jenkins.javajenkins.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhongcheng.jenkins.javajenkins.common.exception.BaseException;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Pages;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Project;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Response;
import com.zhongcheng.jenkins.javajenkins.model.CommonResult;
import com.zhongcheng.jenkins.javajenkins.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.zhongcheng.jenkins.javajenkins.model.ErrorEnum.DetailNotExists;
import static com.zhongcheng.jenkins.javajenkins.model.ErrorEnum.FAIL;

@RestController
public class ProjectController extends BaseController {
    @Resource
    private ProjectService projectService;
    final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/api/admin/project/create",method = RequestMethod.POST)
    public CommonResult<Object> create (@RequestBody Project project, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        logger.info("userId:"+userId);
        project.setCreatorId(userId);
        boolean result = projectService.save(project);
        if (!result) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(project);
    }

    @RequestMapping(value = "/api/admin/project/update",method = RequestMethod.POST)
    public CommonResult<Object> update (@RequestBody Project project) {
        boolean result = projectService.updateById(project);
        if (!result) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(project.getId());
    }

    @RequestMapping(value = "/api/admin/project/detail",method = RequestMethod.POST)
    public CommonResult<Object> detail (@RequestBody Map<String, String> map) {
        String projectId = map.get("id");
        Project result = projectService.getById(projectId);
        if (result == null) {
            throw new BaseException(DetailNotExists);
        }
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/api/admin/project/remove",method = RequestMethod.POST)
    public CommonResult<Object> remove (@RequestBody Map<String, String> map) {
        String projectId = map.get("id");
        boolean result = projectService.removeById(projectId);
        if (!result) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(projectId);
    }

    @RequestMapping(value = "/api/admin/project/search",method = RequestMethod.POST)
    public CommonResult<Pages<Project>> index (@RequestBody Response<Map<String, String>> res) {
        //获取前台发送过来的数据
        Common<Project> common = new Common<>();
        IPage<Project> page = common.getPage(res);
        Pages<Project> result = projectService.findList(page, res);
        if (result == null) {
            throw new BaseException(FAIL);
        }
        return CommonResult.success(result);
    }

    @RequestMapping(value = "/api/admin/project/export", method = RequestMethod.POST)
    public CommonResult<ExcelWriter> export (HttpServletResponse response) throws IOException {
        ExcelWriter writer = projectService.export();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=test.xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
        return null;
    }

    @RequestMapping(value = "/api/admin/project/import", method = RequestMethod.POST)
    public CommonResult<String> imports (MultipartFile file, HttpServletRequest request) {
        Boolean result = projectService.imports(request, file);
        if (!result) {
            throw new BaseException(FAIL, "导入失败！");
        }
        return CommonResult.success("successful！");
    }

    @RequestMapping(value = "/api/admin/project/download", method = RequestMethod.POST)
    public CommonResult<ExcelWriter> download (HttpServletResponse response) throws IOException {
        ExcelWriter writer = projectService.download();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=test.xlsx");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
        return null;
    }
}
