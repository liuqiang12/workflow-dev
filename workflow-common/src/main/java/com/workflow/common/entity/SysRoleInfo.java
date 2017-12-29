package com.workflow.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Entity
@Table(name = "SYS_ROLE_INFO")
public class SysRoleInfo implements Serializable{

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true,nullable = false)
    private String name;
    //角色key唯一标准。权限使用
    @Column(unique = true,nullable = false,name = "KEY_")
    private String key;
    //角色描述
    private String description;

    //是否持有角色标志
    @Transient
    private Integer selected;


    //角色与用户的关联关系
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<SysUserInfo> users = new HashSet<SysUserInfo>();

    //角色与权限的关联关系
    @JsonIgnore
    @JoinTable(name = "sys_role_ln_perm",
            joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permissions_id",referencedColumnName = "id")})
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<SysPermission> permissions = new HashSet<SysPermission>();
    //角色与菜单的关联关系
    @JsonIgnore
    @JoinTable(name = "sys_role_ln_menu",
            joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id",referencedColumnName = "id")})
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<SysMenuInfo> menus = new HashSet<SysMenuInfo>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SysUserInfo> getUsers() {
        return users;
    }

    public void setUsers(Set<SysUserInfo> users) {
        this.users = users;
    }

    public Set<SysPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<SysPermission> permissions) {
        this.permissions = permissions;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public Set<SysMenuInfo> getMenus() {
        return menus;
    }

    public void setMenus(Set<SysMenuInfo> menus) {
        this.menus = menus;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", selected=" + selected +
                '}';
    }
}
