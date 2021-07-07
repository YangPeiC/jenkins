package com.zhongcheng.jenkins.javajenkins.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@TableName("User")
@EqualsAndHashCode(callSuper=true)
public class User extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String account;
    private String passwd;
}

