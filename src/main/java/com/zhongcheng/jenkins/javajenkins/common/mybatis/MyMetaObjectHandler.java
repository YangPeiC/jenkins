package com.zhongcheng.jenkins.javajenkins.common.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(org.apache.ibatis.reflection.MetaObject metaObject) {
        setFieldValByName("createdAt", new Date(), metaObject);
        setFieldValByName("updatedAt", new Date(), metaObject);
    }
    @Override
    public void updateFill(org.apache.ibatis.reflection.MetaObject metaObject) {
        setFieldValByName("updatedAt", new Date(), metaObject);
    }
}
