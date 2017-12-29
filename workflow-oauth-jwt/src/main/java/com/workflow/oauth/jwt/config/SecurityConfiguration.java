package com.workflow.oauth.jwt.config;

import com.workflow.common.entity.SysRoleInfo;
import com.workflow.common.entity.SysUserInfo;
import com.workflow.oauth.jwt.service.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity/*开启Spring Security的功能*/
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	// 查询用户使用
	@Autowired
	SysUserInfoService userInfoService;

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		// auth.inMemoryAuthentication()
		// .withUser("user").password("password").roles("USER")
		// .and()
		// .withUser("app_client").password("nopass").roles("USER")
		// .and()
		// .withUser("admin").password("password").roles("ADMIN");
		//配置用户来源于数据库
		auth.userDetailsService(userDetailsService());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//设置被保护的URL
		http.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS).permitAll()/*获取服务器支持的HTTP请求方法；AJAX进行跨域请求时的预检，需要向另外一个域名的资源发送一个HTTP OPTIONS请求头，用以判断实际发送的请求是否安全*/
				.anyRequest().authenticated()
				.and()
				.httpBasic()/* 认证通过弹出框输入密码验证 */
				.and()
				.csrf()/*禁用csrf网络攻击:跨站域请求伪造*/
				.disable();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
				// 通过用户名获取用户信息
				SysUserInfo userInfo = userInfoService.findByUserName(name);
				if (userInfo != null) {
					// 创建spring security安全用户:在内存中创建了用户
					/* 用户对应的角色数组 */
					SysRoleInfo[] roleinfos = userInfo.getRoles().toArray(new SysRoleInfo[]{});
					String[] roles = new String[roleinfos.length];
					for(int i = 0 ; i < roleinfos.length; i++){
						roles[i] = roleinfos[i].getKey();
					}
					User user = new User(
							userInfo.getUserName(),
							userInfo.getPassword(),
							AuthorityUtils.createAuthorityList(roles)
					);
					return user;
				} else {
					throw new UsernameNotFoundException("用户[" + name + "]不存在");
				}
			}
		};

	}
}
