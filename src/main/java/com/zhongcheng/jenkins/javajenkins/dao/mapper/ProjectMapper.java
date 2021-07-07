package com.zhongcheng.jenkins.javajenkins.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Project;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectMapper extends BaseMapper<Project> {
}
