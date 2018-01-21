package com.workflow.oauth.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.HashMap;
import java.util.Map;

/**
 * OAuth2认证授权服务端
 */
@Configuration
@EnableAuthorizationServer/*配置OAuth2.0 授权服务机制*/
public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Value("${resource.id:spring-boot-application}") // 默认值spring-boot-application
    private String resourceId;

    @Value("${access_token.validity_period:360}") // 默认值3600
    int accessTokenValiditySeconds = 360;

    @Value("${access_token.validity_period:3600}")
    int refreshTokenValiditySeconds  = 3600;// 30 days 2592000


    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * 配置授权类型
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception{
        endpoints.authenticationManager(this.authenticationManager);//认证管理器 当你选择了资源所有者密码（password）授权类型的时候，请设置这个属性注入一个 AuthenticationManager 对象。
        endpoints.accessTokenConverter(accessTokenConverter());//
        endpoints.tokenStore(tokenStore());
        //授权界面
        endpoints.pathMapping("/oauth/confirm_access", "/extenal/oauth/confirm_access");
    }
    /**
     * token converter
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        /**
         * 资源服务器也需要一个解码的Token令牌的类 JwtAccessTokenConverter
         */
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
            /***
             * 重写增强token方法,用于自定义一些token返回的信息
             */
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                String userName = authentication.getUserAuthentication().getName();
                User user = (User) authentication.getUserAuthentication().getPrincipal();// 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
                /** 自定义一些token[令牌]属性 ***/
                final Map<String, Object> additionalInformation = new HashMap<>();
                additionalInformation.put("userName", userName);
                additionalInformation.put("roles", user.getAuthorities());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
                return enhancedToken;
            }

        };
        accessTokenConverter.setSigningKey("123");// 测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
        return accessTokenConverter;
    }
    /**
     * token store
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        /*
            JSON Web Token（JWT）
            令牌相关的数据进行编码
         */

        TokenStore tokenStore = new JwtTokenStore(accessTokenConverter());
        return tokenStore;
    }

    /**
     * ClientDetailsServiceConfigurer:客户端详情信息在这里进行初始化
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()// 使用in-memory存储 即 ."内存存储"
                //常规web
                .withClient("normal-app")//客户端ID
                .authorizedGrantTypes("authorization_code", "implicit")//该client允许的授权类型：authorization_code：授权码模式
                .authorities("ROLE_CLIENT")//授权给客户端的权限
                .scopes("read", "write")//授权用户的操作权限
                .resourceIds(resourceId)
                .accessTokenValiditySeconds(accessTokenValiditySeconds)//token有效期为120秒
                .refreshTokenValiditySeconds(refreshTokenValiditySeconds)// 30 days
                .and()

                .withClient("trusted-app")//客户端ID
                .authorizedGrantTypes("client_credentials", "password")
                .authorities("ROLE_TRUSTED_CLIENT")
                .scopes("read", "write")//授权用户的操作权限
                .resourceIds(resourceId)
                .accessTokenValiditySeconds(accessTokenValiditySeconds)//token有效期为120秒
                .refreshTokenValiditySeconds(refreshTokenValiditySeconds) // 30 days
                .secret("secret");// （需要值得信任的客户端）客户端安全码，如果有的话。client_secret

    }

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束.
     * @param security
     */
    public void configure(AuthorizationServerSecurityConfigurer security){
        //security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')");
        security.checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
    }
}
