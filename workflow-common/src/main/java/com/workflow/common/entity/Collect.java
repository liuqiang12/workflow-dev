package com.workflow.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.workflow.common.utils.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 收藏
 */
@Entity
@Table(name = "workflow_collect")
public class Collect implements Serializable{

    @Id
    @GeneratedValue
    private Integer id;

    //与帖子的关联关系：立即加载
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "posts_id")
    private Posts posts;

    //与用户的关联关系：立即加载
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private SysUserInfo user;

    //收藏时间
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private Date initTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }

    public SysUserInfo getUser() {
        return user;
    }

    public void setUser(SysUserInfo user) {
        this.user = user;
    }

    public Date getInitTime() {
        return initTime;
    }

    public void setInitTime(Date initTime) {
        this.initTime = initTime;
    }
}
