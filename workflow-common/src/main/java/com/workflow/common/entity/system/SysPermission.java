package com.workflow.common.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "SYS_PERMISSION_INFO")
public class SysPermission implements Serializable{

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    @Column(unique = true, name = "PER_URL")
    private String perUrl;

    //资源类型　　1：菜单　2：按钮
    private Integer type;

    //父权限
    @Column(name = "PARENT_ID")
    private Integer parentId;

    //排序
    private Integer sort;

    //是否选中
    @Transient
    private String checked;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    private Set<SysRoleInfo> roles = new HashSet<SysRoleInfo>();

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

    public String getPerUrl() {
        return perUrl;
    }

    public void setPerUrl(String perUrl) {
        this.perUrl = perUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Set<SysRoleInfo> getRoles() {
        return roles;
    }

    public void setRoles(Set<SysRoleInfo> roles) {
        this.roles = roles;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
