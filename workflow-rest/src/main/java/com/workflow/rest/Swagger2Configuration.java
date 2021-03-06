package com.workflow.rest;

import com.workflow.rest.controller.OfficeWord4Download;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-06T20:25:53.736+08:00")
@Configuration
@EnableSwagger2
/*@EnableWebMvc*/
public class Swagger2Configuration {
    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.workflow.rest.controller";
    public static final String VERSION = "1.0.0";
    /*
        扫描注解的配置，即你的API接口位置
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(OfficeWord4Download.class) //忽略“/list”接口参数
                .directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class);
    }

    /**
     * 这个可要可不要
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("工作流API")
                .description("实现：自定义流程、自定义表单、oauth2.0权限，HTML5 win8效果")
                .license("刘强")
                .licenseUrl("784248173@qq.com")
                .termsOfServiceUrl("")
                .version(VERSION)
                .contact("刘强 邮箱 :784248173")
                .build();
    }


}
