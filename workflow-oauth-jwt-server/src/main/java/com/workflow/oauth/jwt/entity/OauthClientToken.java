package com.workflow.oauth.jwt.entity;

import com.workflow.common.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Arrays;

/**
 * OAuth2.0
 */
@Entity
@javax.persistence.Table(name = "OAUTH_CLIENT_TOKEN")
public class OauthClientToken implements Serializable {
    private static final long serialVersionUID = -6947822646185526939L;

    /* 主键信息 */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 主键,必须唯一,不能为空.
     用于唯一标识每一个客户端(client); 在注册时必须填写(也可由服务端自动生成).
     对于不同的grant_type,该字段都是必须的. 在实际应用中的另一个名称叫appKey,与client_id是同一个概念.
     */
    @Column(name = "CLIENT_ID")
    private String clientId;
    /**
     * 该字段的值是将access_token的值通过MD5加密后存储的.
     */
    @Column(name = "TOKEN_ID")
    private String tokenId;
    /**
     * 存储将OAuth2AccessToken.java对象序列化后的二进制数据, 是真实的AccessToken的数据值
     */
    @Column(name="token", columnDefinition="BLOB")
    private byte[] token;

    /**
     * 该字段具有唯一性, 其值是根据当前的username(如果有),client_id与scope通过MD5加密生成的. 具体实现请参考DefaultAuthenticationKeyGenerator.java类
     */
    @Column(unique = true,name = "AUTHENTICATION_ID")
    private String authenticationId;
    /**
     * 登录时的用户名, 若客户端没有用户名(如grant_type="client_credentials"),则该值等于client_id
     */
    @Column(name = "USERNAME")
    private String username;
    /**
     * 存储将OAuth2Authentication.java对象序列化后的二进制数据
     */
    @Column(name="AUTHENTICATION", columnDefinition="BLOB")
    private byte[] authentication;
    /**
     * 该字段的值是将refresh_token的值通过MD5加密后存储的.
     */
    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "create_time")
    private Integer createTime = DateUtils.now().getSecond();

    public OauthClientToken() {
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OauthClientToken{");
        sb.append("id=").append(id);
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", tokenId='").append(tokenId).append('\'');
        sb.append(", token='").append(Arrays.toString(token)).append('\'');
        sb.append(", authenticationId='").append(authenticationId).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", authentication='").append(Arrays.toString(authentication)).append('\'');
        sb.append(", refreshToken='").append(refreshToken).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
}