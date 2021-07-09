package com.zhongcheng.jenkins.javajenkins.service;

import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Pages;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Project;
import com.zhongcheng.jenkins.javajenkins.model.dto.req.PageResponseDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ProjectService extends IService<Project> {
    Pages<Project> findList(IPage<Project> page, PageResponseDto<Map<String, String>> res);
    ExcelWriter export();
    Boolean imports(HttpServletRequest request, MultipartFile file);
    ExcelWriter download();
}
