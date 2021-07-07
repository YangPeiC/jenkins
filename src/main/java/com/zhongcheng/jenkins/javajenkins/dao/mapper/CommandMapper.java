package com.zhongcheng.jenkins.javajenkins.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Command;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommandMapper extends BaseMapper<Command> {
}
