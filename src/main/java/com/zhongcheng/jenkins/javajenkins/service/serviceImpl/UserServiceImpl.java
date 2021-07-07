package com.zhongcheng.jenkins.javajenkins.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Pages;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Response;
import com.zhongcheng.jenkins.javajenkins.dao.entity.User;
import com.zhongcheng.jenkins.javajenkins.dao.mapper.UserMapper;
import com.zhongcheng.jenkins.javajenkins.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 实现User类的基本功能
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserService userService;

    /**
     * 分页查询
     */
    public Pages<User> findList (IPage<User> page, Response<Map<String, String>> res) {
        Common<User> common= new Common<>();
        QueryWrapper<User> wrapper = common.pageFilter(res);
        IPage<User> userIPage = userService.page(page, wrapper);
        List<User> rows = userIPage.getRecords();
        long count = userIPage.getTotal();
        return new Pages<>(count, rows);
    }
}
