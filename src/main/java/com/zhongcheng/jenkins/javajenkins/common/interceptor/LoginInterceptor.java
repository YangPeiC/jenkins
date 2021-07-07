package com.zhongcheng.jenkins.javajenkins.common.interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginInterceptor implements WebMvcConfigurer {
    final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册Interceptor拦截器
        logger.info("load LoginInterceptor ...");
        InterceptorRegistration registration = registry.addInterceptor(new AuthInterceptor());
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(
                "/api/admin/login",            //登录
                "/**/*.html",                  //html静态资源
                "/**/*.js",                    //js静态资源
                "/**/*.css",                   //css静态资源
                "/**/*.woff",
                "/**/*.ttf"
        );
    }
}
