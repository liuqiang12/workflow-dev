package com.workflow.oauth.jwt;

import com.workflow.oauth.jwt.service.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching//缓存支持
public class OauthApplication {
    // 查询用户使用
    @Autowired
    SysUserInfoService userInfoService;
    /**
     * 嵌入式容器
     * 控制错误界面跳转
     * server.port
     * @return
     */
   /* @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

            container.addErrorPages(error401Page, error404Page, error500Page);
        });
    }*/

    public static void main(String[] args) throws IOException {
        //更改properties配置文件名称,避免依赖冲突
        Properties properties = new Properties();
        InputStream in = OauthApplication.class.getClassLoader().getResourceAsStream("oauth-jwt-server.properties");
        properties.load(in);
        SpringApplication app = new SpringApplication(OauthApplication.class);
        app.setDefaultProperties(properties);
        app.run(args);
//        SpringApplication.run(CommonApplication.class, args);
    }
    /*@Autowired
    public void init(){
        try {
            SysUserInfo sysUserInfo = new SysUserInfo();
            sysUserInfo.setUsername("liuqiang10");
            sysUserInfo.setPassword("111111");
            sysUserInfo.setEmail("784248173@qq.com");
            sysUserInfo.setEnable(1);
            Set<SysRoleInfo> roles = new HashSet<SysRoleInfo>();
            SysRoleInfo sysRoleInfo1 = new SysRoleInfo();
            sysRoleInfo1.setName("系统管理员010");
            sysRoleInfo1.setKey("ROLE_USER");
            sysRoleInfo1.setSelected(1);

            roles.add(sysRoleInfo1);
            sysUserInfo.setRoles(roles);

            userInfoService.save(sysUserInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}