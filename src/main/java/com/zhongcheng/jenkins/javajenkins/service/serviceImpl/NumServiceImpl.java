package com.zhongcheng.jenkins.javajenkins.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongcheng.jenkins.javajenkins.dao.entity.Num;
import com.zhongcheng.jenkins.javajenkins.dao.mapper.NumMapper;
import com.zhongcheng.jenkins.javajenkins.service.NumService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class NumServiceImpl extends ServiceImpl<NumMapper, Num> implements NumService {
    @Resource
    private NumService numService;
    public List<Integer> search () {
        QueryWrapper<Num> qw = new QueryWrapper<>();
        qw.select("id", "num");
        List<Num> result =  numService.list(qw);
        List<Num> res =  result.subList(result.size()-7,result.size());
        ArrayList<Integer> l = new ArrayList<>(res.size());
        for(Num n : res) {
            l.add(n.getNum());
        }
        return l;
    }
}
