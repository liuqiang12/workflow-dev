package com.workflow.common.entity.system;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 流程引擎的用户
 */
@Entity
@Table(name = "SYS_USER_INFO")
public class SysUserInfo implements Serializable{

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true,nullable = false,name = "USER_NAME")
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;


    //是否可以使用,默认为１
    @Column(nullable = false)
    private Integer enable = 1;
    // 头像
    private String icon ="http://127.0.0.1/images/upload/default.png";
    //注册邮箱
    @Column(nullable = false)
    private String email;
    //最后一次登录时间
    private Date lastLoginTime;
    //创建时间
    @Column(name = "CREATE_TIME")
    private Long createTime = new Date().getTime();
    //Default user is initial when create database, do not delete
    @Column(name = "DEFAULT_USER")
    private Integer defaultUser = 0;
    /**
     * 所属用户:枚举类型  Privilege 多个以逗号隔开
     */
    @Column(name = "PRIVILEGE")
    private String privilege;

    @JsonIgnore
    @JoinTable(name = "SYS_USER_LN_ROLE",
            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<SysRoleInfo> roles = new HashSet<SysRoleInfo>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Set<SysRoleInfo> getRoles() {
        return roles;
    }

    public void setRoles(Set<SysRoleInfo> roles) {
        this.roles = roles;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public SysUserInfo() {
    }

    public SysUserInfo(String username, String password, Integer enable, String icon, String email) {
        this.username = username;
        this.password = password;
        this.enable = enable;
        this.icon = icon;
        this.email = email;
    }

    public SysUserInfo email(String email) {
        this.email = email;
        return this;
    }


    public SysUserInfo username(String username) {
        this.username = username;
        return this;
    }


    public Date lastLoginTime() {
        return lastLoginTime;
    }

    public SysUserInfo lastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    public SysUserInfo createTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    public SysUserInfo password(String password) {
        this.password = password;
        return this;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer isDefaultUser() {
        return defaultUser;
    }

    public void setDefaultUser(Integer defaultUser) {
        this.defaultUser = defaultUser;
    }

    public Integer getDefaultUser() {
        return defaultUser;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
                "Id=" + id +
                ", userName='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enable=" + enable+ '\'' +
                ", icon=" + icon+ '\'' +
                ", email=" + email;
    }
}
