package com.zhongcheng.jenkins.javajenkins.service.serviceImpl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongcheng.jenkins.javajenkins.common.exception.BaseException;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Pages;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Project;
import com.zhongcheng.jenkins.javajenkins.dao.mapper.ProjectMapper;
import com.zhongcheng.jenkins.javajenkins.model.ErrorEnum;
import com.zhongcheng.jenkins.javajenkins.model.dto.req.PageResponseDto;
import com.zhongcheng.jenkins.javajenkins.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    @Resource
    private ProjectService projectService;

    public Pages<Project> findList(IPage<Project> page, PageResponseDto<Map<String, String>> res) {
        Common<Project> common = new Common<>();
        QueryWrapper<Project> wrapper = common.pageFilter(res);
        IPage<Project> projectPage = projectService.page(page, wrapper);
        List<Project> rows = projectPage.getRecords();
        Long count = projectPage.getTotal();
        return new Pages<>(count, rows);
    }

    public ExcelWriter export() {
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        List<Project> list = projectService.list(queryWrapper);
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);
        return writer;
    }

    /*
    读取文件数据，并把数据存入数据库
     */
    public Boolean imports(HttpServletRequest request, MultipartFile file) {
        List<Project> list = new ArrayList<>();
        ByteArrayInputStream inputStream;
        try {
            inputStream = new ByteArrayInputStream(file.getBytes());
        } catch (IOException e) {
            throw new BaseException(ErrorEnum.IO_ERROR);
        }
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Map<String, Object>> lists = reader.readAll();
        Long creatorId = (Long) request.getSession().getAttribute("userId");
        for (Map<String, Object> maps : lists) {
            Project project = new Project();
            project.setName((String) maps.get("项目名称"));
            project.setCreatorId(creatorId);
            list.add(project);
        }
        return projectService.saveBatch(list);
    }

    public ExcelWriter download() {
        File path;
        String pathStr;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
            pathStr = path.getAbsolutePath();
        } catch (FileNotFoundException e) {
            throw new BaseException(ErrorEnum.IO_ERROR);
        }
        String replacePath = "\\src\\main\\resources\\static\\excel\\模板.xlsx";
        String realPath = pathStr.replace("\\target\\classes", replacePath);  //获取文件相对地址
        ExcelReader reader = ExcelUtil.getReader(realPath);
        List<List<Object>> list = reader.read();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);
        return writer;
    }
}
