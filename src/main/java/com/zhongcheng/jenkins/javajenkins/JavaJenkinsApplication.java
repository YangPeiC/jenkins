package com.zhongcheng.jenkins.javajenkins;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.zhongcheng.jenkins.javajenkins.dao.mapper")
@SpringBootApplication
//@ServletComponentScan
//@EnableWebSecurity()   //开启security
public class JavaJenkinsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaJenkinsApplication.class, args);
    }

}
