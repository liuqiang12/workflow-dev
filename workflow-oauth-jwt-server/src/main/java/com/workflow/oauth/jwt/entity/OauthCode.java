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
@javax.persistence.Table(name = "OAUTH_CODE")
public class OauthCode implements Serializable {
    private static final long serialVersionUID = -6947822646185526939L;

    /* 主键信息 */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 存存储将AuthorizationRequestHolder.java对象序列化后的二进制数据.
     */
    @Column(name="AUTHENTICATION", columnDefinition="BLOB")
    private byte[] authentication;
    /**
     * 存储服务端系统生成的code的值(未加密).
     */
    @Column(name = "CODE")
    private String code;
    @Column(name = "create_time")
    private Integer createTime = DateUtils.now().getSecond();

    public OauthCode() {
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OauthCode{");
        sb.append("id=").append(id);
        sb.append(", authentication='").append(Arrays.toString(authentication)).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
}