package com.zhongcheng.jenkins.javajenkins.common.config;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.service.Contact;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Slf4j
public class SwaggerConfig {
    /**
     * 添加摘要信息(Docket)
     * .groupName("XXXX")配置这个Docket的组名
     */
    @Bean
    public Docket docket() {
        log.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        return new Docket(DocumentationType.SWAGGER_2).groupName("组名:")
                .apiInfo(new ApiInfoBuilder()
                        .title("标题:此处是配置UI界面显示的标题信息")
                        .description("描述：这里配置的是UI界面显示的对这个接口文档的描述信息")
                        //new Contact()  第一个参数是创建者,第二个是连接地址(可以不配),第三个参数是邮箱(可以不配)
                        .contact(new Contact("流-沙", "https://www.cnblogs.com/liusha-1/", "2387831285@qq.com"))
                        .version("版本号:1.0")
                        .build())
                .select()
                //扫描Api接口的包监听是哪一个路径的
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
