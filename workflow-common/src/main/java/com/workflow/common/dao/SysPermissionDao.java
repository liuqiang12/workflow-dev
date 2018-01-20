package com.workflow.common.dao;

import com.workflow.common.entity.system.SysPermission;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@CacheConfig(cacheNames = "sysPermissions")
public interface SysPermissionDao extends JpaRepository<SysPermission,Integer> {


    SysPermission findOne(Integer integer);

    @Cacheable
    List<SysPermission> findAll();

}
