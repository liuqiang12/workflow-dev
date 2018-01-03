package com.workflow.common.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
