package com.workflow.oauth.jwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 *资源服务配置
 * 必须使用 ResourceServerConfigurer 这个配置对象来进行配置
 */
@Configuration
@EnableResourceServer/*授权服务机制*/
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/api/home","/api/user/register","/oauth/token").permitAll()
                .anyRequest()
                    .authenticated();

    }
}
