package com.zhongcheng.jenkins.javajenkins.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("Num")
@EqualsAndHashCode(callSuper=true)
public class Num extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer num;
}
