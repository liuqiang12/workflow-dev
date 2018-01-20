package com.workflow.oauth.jwt.entity;

import com.workflow.common.utils.DateUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * OAuth2.0
 */
@Entity
@Table(name = "OAUTH_REFRESH_TOKEN")
public class OauthRefreshToken implements Serializable {
    private static final long serialVersionUID = -6947822646185526939L;

    /* 主键信息 */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 该该字段的值是将refresh_token的值通过MD5加密后存储的.
     */
    @Column(name = "TOKEN_ID")
    private String tokenId;
    /**
     * 存储将OAuth2RefreshToken.java对象序列化后的二进制数据
     */
    @Column(name="token", columnDefinition="BLOB")
    private byte[] token;
    /**
     * 存储将OAuth2Authentication.java对象序列化后的二进制数据
     */
    @Column(name="AUTHENTICATION", columnDefinition="BLOB")
    private byte[] authentication;

    @Column(name = "create_time")
    private Integer createTime = DateUtils.now().getSecond();

    public OauthRefreshToken() {
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OauthRefreshToken{");
        sb.append("id=").append(id);
        sb.append(", tokenId='").append(tokenId).append('\'');
        sb.append(", token='").append(Arrays.toString(token)).append('\'');
        sb.append(", authentication='").append(Arrays.toString(authentication)).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /*public Integer getId() {
        return id;
    }*/

    public String getTokenId() {
        return tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public Integer getCreateTime() {
        return createTime;
    }


}