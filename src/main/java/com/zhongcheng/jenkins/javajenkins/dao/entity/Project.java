package com.zhongcheng.jenkins.javajenkins.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@TableName("Project")
@EqualsAndHashCode(callSuper=true)
public class Project extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long creatorId;
    private String name;
}
