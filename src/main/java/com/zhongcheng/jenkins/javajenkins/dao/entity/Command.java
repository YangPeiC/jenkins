package com.zhongcheng.jenkins.javajenkins.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@AllArgsConstructor
@TableName("Command")
@EqualsAndHashCode(callSuper=true)
public class Command extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private String instruction;
    private String content;
}
