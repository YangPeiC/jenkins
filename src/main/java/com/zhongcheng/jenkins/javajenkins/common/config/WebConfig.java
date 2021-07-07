//package com.zhongcheng.jenkins.javajenkins.common.config;
//
//import com.zhongcheng.jenkins.javajenkins.common.interceptor.AuthInterceptor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    final Logger logger = LoggerFactory.getLogger(getClass());
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //注册TestInterceptor拦截器
//        logger.info("load LoginInterceptor ...");
//        InterceptorRegistration registration = registry.addInterceptor(new AuthInterceptor());
//        logger.info("registration:",registration);
//        registration.addPathPatterns("/**");                      //所有路径都被拦截
//        registration.excludePathPatterns(                         //添加不拦截路径
//                "/api/admin/login",            //登录
//                "/**/*.html",            //html静态资源
//                "/**/*.js",              //js静态资源
//                "/**/*.css",             //css静态资源
//                "/**/*.woff",
//                "/**/*.ttf"
//        );
//    }
//}
