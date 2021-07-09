package com.zhongcheng.jenkins.javajenkins.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongcheng.jenkins.javajenkins.common.exception.InvalidUser;
import com.zhongcheng.jenkins.javajenkins.common.exception.InvalidVerifyCode;
import com.zhongcheng.jenkins.javajenkins.dao.entity.User;
import com.zhongcheng.jenkins.javajenkins.dao.mapper.UserMapper;
import com.zhongcheng.jenkins.javajenkins.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Slf4j
@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements LoginService {
    @Resource
    private UserMapper userMapper;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    /**
     * dest: 已加密的密码； src：前端输入密码
     *根据前缀判断密码类型，｛noop｝和｛bcrypt｝
     * ｛noop｝：去前缀，判断密码是否正确
     * ｛bcrypt｝：去前缀，判断加密后的密码是否正确
     */
    public boolean isTruePasswd(String dest, String src) {
        String pwd = src+"";
        if (dest.charAt(0) != '{') {  //无前缀跳过
            return true;
        }
        String key = dest.substring(0, dest.indexOf('}')+1); //获取前缀
        dest = dest.substring(key.length()); //去除前缀
        //添加判断Map
        HashMap<String, Boolean> handler = new HashMap<>();
        handler.put("{noop}", !pwd.equals(dest));
        handler.put("{bcrypt}", !passwordEncoder.matches(pwd, dest));
        return handler.get(key);  //对应类型密码是否正确
    }
    public User login(Object account, Object passwd) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("account","admin");
        User user = userMapper.selectOne(qw);
        if (user == null) {
            throw new InvalidUser("无效的用户");
        }
        else if (isTruePasswd(user.getPasswd(), (String) passwd)) {
            throw new InvalidVerifyCode();
        }
        return user;
    }
    public User updatePwd(Long id, String oldPwd ,String passwd) {
        User user = userMapper.selectById(id);

        if (isTruePasswd(user.getPasswd(), oldPwd)){
            throw new InvalidVerifyCode();
        }
        String newPwd = "{bcrypt}"+passwordEncoder.encode(passwd);
        user.setPasswd(newPwd);
        int result = userMapper.updateById(user); //更新密码
        if (result == 0) {
            throw new InvalidVerifyCode("修改密码失败");
        }
        return userMapper.selectById(user);
    }


}
