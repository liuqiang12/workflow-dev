package com.workflow.oauth.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.authorizeRequests().antMatchers("/").permitAll();*/
        /*http.csrf().disable();
        http.headers().frameOptions().disable();*/
        http.authorizeRequests()
                .antMatchers("/css/**","/font-awesome/**", "/js/**","/img/**","favicon.png").permitAll()
                .antMatchers("/", "/login").permitAll()
                .and()
                .formLogin()/* 都是请求的后台地址 */
                .loginPage("/login")
                .defaultSuccessUrl("/init")
                .failureUrl("/loginError")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .csrf()/*禁用csrf网络攻击:跨站域请求伪造*/
                .disable();
        http.headers().frameOptions().disable();
    }
}
