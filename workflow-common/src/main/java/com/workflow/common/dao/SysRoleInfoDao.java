package com.workflow.common.dao;

import com.workflow.common.entity.SysRoleInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@CacheConfig(cacheNames = "sysRoleInfos")
public interface SysRoleInfoDao extends JpaRepository<SysRoleInfo,Integer>{

    SysRoleInfo findOne(Integer integer);

    @Cacheable
    List<SysRoleInfo> findAll();

}
