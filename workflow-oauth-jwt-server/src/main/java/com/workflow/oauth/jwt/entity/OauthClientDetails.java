package com.workflow.oauth.jwt.entity;

import com.workflow.common.utils.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 定义OAuth中的 Client, 也称 ClientDetails
 */
@Entity
@javax.persistence.Table(name = "OAUTH_CLIENT_DETAILS")
public class OauthClientDetails implements Serializable {
    private static final long serialVersionUID = -6947822646185526939L;

    /**
     * 主键,必须唯一,不能为空.
     用于唯一标识每一个客户端(client); 在注册时必须填写(也可由服务端自动生成).
     对于不同的grant_type,该字段都是必须的. 在实际应用中的另一个名称叫appKey,与client_id是同一个概念.
     */
    /* 主键信息 */
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "CLIENT_ID",unique = true,nullable = false)
    private String clientId;
    @Column(name = "resource_ids")
    private String resourceIds;
    @Column(name = "client_secret",nullable = false)
    private String clientSecret;

    /**
     * Available values: read,write
     */
    @Column(name = "scope")
    private String scope;
    /**
     * grant types include
     * "authorization_code", "password", "assertion", and "refresh_token".
     * Default value is "authorization_code,refresh_token".
     */
    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes = "authorization_code,refresh_token";
    /**
     * The re-direct URI(s) established during registration (optional, comma separated).
     */
    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectUri;
    /**
     * Authorities that are granted to the client (comma-separated). Distinct from the authorities
     * granted to the user on behalf of whom the client is acting.
     * <p/>
     */
    @Column(name = "authorities")
    private String authorities;
    /**
     * The access token validity period in seconds (optional).
     * If unspecified a global default will be applied by the token services.
     */
    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;

    /**
     * The refresh token validity period in seconds (optional).
     * If unspecified a global default will  be applied by the token services.
     */
    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;

    // optional
    @Column(name = "additional_information")
    private String additionalInformation;

    @Column(name = "create_time")
    private Integer createTime = DateUtils.now().getSecond();
    /**
     * 用于标识客户端是否已存档(即实现逻辑删除),默认值为'0'(即未存档).
     对该字段的具体使用请参考CustomJdbcClientDetailsService.java,在该类中,扩展了在查询client_details的SQL加上archived = 0条件 (扩展字段)
     */
    @Column(name = "ARCHIVED")
    private Integer archived = 0;

    /**
     * The client is trusted or not. If it is trust, will skip approve step
     * default false.
     * 设置客户端是否为受信任的,默认为'0'(即不受信任的,1为受信任的).
     该字段只适用于grant_type="authorization_code"的情况,当用户登录成功后,若该值为0,则会跳转到让用户Approve的页面让用户同意授权,
     若该字段为1,则在登录后不需要再让用户Approve同意授权(因为是受信任的).
     */
    @Column(name = "TRUSTED")
    private Integer trusted = 0;
    /**
     * Value is 'true' or 'false',  default 'false'
     * 设置用户是否自动Approval操作, 默认值为 'false', 可选值包括 'true','false', 'read','write'.
     该字段只适用于grant_type="authorization_code"的情况,当用户登录成功后,若该值为'true'或支持的scope值,则会跳过用户Approve的页面, 直接授权.
     该字段与 trusted 有类似的功能, 是 spring-security-oauth2 的 2.0 版本后添加的新属性.
     */
    @Column(name = "autoapprove")
    private Integer autoApprove = 0;

    public OauthClientDetails() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer autoApprove() {
        return autoApprove;
    }

    public OauthClientDetails autoApprove(Integer autoApprove) {
        this.autoApprove = autoApprove;
        return this;
    }

    public Integer trusted() {
        return trusted;
    }

    public Integer createTime() {
        return createTime;
    }

    public OauthClientDetails createTime(Integer createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer archived() {
        return archived;
    }

    public String clientId() {
        return clientId;
    }

    public String resourceIds() {
        return resourceIds;
    }

    public String clientSecret() {
        return clientSecret;
    }

    public String scope() {
        return scope;
    }

    public String authorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public String webServerRedirectUri() {
        return webServerRedirectUri;
    }

    public String authorities() {
        return authorities;
    }

    public Integer accessTokenValidity() {
        return accessTokenValidity;
    }

    public Integer refreshTokenValidity() {
        return refreshTokenValidity;
    }

    public String additionalInformation() {
        return additionalInformation;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("OauthClientDetails");
        sb.append("{id=").append(id);
        sb.append(", createTime=").append(createTime);
        sb.append(", archived=").append(archived);
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", resourceIds='").append(resourceIds).append('\'');
        sb.append(", clientSecret='").append(clientSecret).append('\'');
        sb.append(", scope='").append(scope).append('\'');
        sb.append(", authorizedGrantTypes='").append(authorizedGrantTypes).append('\'');
        sb.append(", webServerRedirectUri='").append(webServerRedirectUri).append('\'');
        sb.append(", authorities='").append(authorities).append('\'');
        sb.append(", accessTokenValidity=").append(accessTokenValidity);
        sb.append(", refreshTokenValidity=").append(refreshTokenValidity);
        sb.append(", additionalInformation='").append(additionalInformation).append('\'');
        sb.append(", trusted=").append(trusted);
        sb.append('}');
        return sb.toString();
    }

    public OauthClientDetails clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public OauthClientDetails clientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public OauthClientDetails resourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
        return this;
    }

    public OauthClientDetails authorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
        return this;
    }

    public OauthClientDetails scope(String scope) {
        this.scope = scope;
        return this;
    }

    public OauthClientDetails webServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
        return this;
    }

    public OauthClientDetails authorities(String authorities) {
        this.authorities = authorities;
        return this;
    }

    public OauthClientDetails accessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
        return this;
    }

    public OauthClientDetails refreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
        return this;
    }

    public OauthClientDetails trusted(Integer trusted) {
        this.trusted = trusted;
        return this;
    }

    public OauthClientDetails additionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    public OauthClientDetails archived(Integer archived) {
        this.archived = archived;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getArchived() {
        return archived;
    }

    public void setArchived(Integer archived) {
        this.archived = archived;
    }

    public Integer getTrusted() {
        return trusted;
    }

    public void setTrusted(Integer trusted) {
        this.trusted = trusted;
    }

    public Integer getAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(Integer autoApprove) {
        this.autoApprove = autoApprove;
    }
}