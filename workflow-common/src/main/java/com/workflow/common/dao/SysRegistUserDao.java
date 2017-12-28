package com.workflow.common.dao;

import com.workflow.common.entity.SysRegistUser;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@CacheConfig(cacheNames = "sysRegistUsers")
public interface SysRegistUserDao extends JpaRepository<SysRegistUser,Integer> ,JpaSpecificationExecutor {

    SysRegistUser findByUserName(String userName);

    SysRegistUser findByEmail(String email);

    @Query(value = "select u.id, u.user_name , u.icon from workflow_user u where DATE_SUB(CURDATE(), INTERVAL 30 DAY) <=DATE(u.init_time) ORDER BY u.id DESC limit 12" ,nativeQuery = true)
    List<Object> findNewUser();

}
