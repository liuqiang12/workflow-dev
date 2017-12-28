package com.workflow.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * OAuth2服务器端配置
 */
@Configuration
@EnableAuthorizationServer/*配置OAuth2.0 授权服务机制*/
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer auth) throws Exception{
        auth.authenticationManager(authenticationManager );
    }

    /**
     * ClientDetailsServiceConfigurer:客户端详情信息在这里进行初始化
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
        clients.inMemory()
                .withClient("web")
                .authorizedGrantTypes("password","client_credentials")
                .authorities("UI_CLIENT","UI_USER").scopes("read","write","trust")
                .resourceIds("auth2-resource").accessTokenValiditySeconds(3600)
        .secret("john");
    }

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束.
     * @param security
     */
    public void configure(AuthorizationServerSecurityConfigurer security){
        security.checkTokenAccess("isAuthenticated()");
    }
}
