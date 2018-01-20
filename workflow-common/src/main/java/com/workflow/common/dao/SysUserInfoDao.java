package com.workflow.common.dao;

import com.workflow.common.entity.system.SysUserInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@CacheConfig(cacheNames = "sysUserInfos")
public interface SysUserInfoDao extends JpaRepository<SysUserInfo,Integer>,JpaSpecificationExecutor {

    SysUserInfo findOne(Integer integer);

    @Cacheable
    List<SysUserInfo> findAll();

    SysUserInfo findByUsername(String userName);

    @Cacheable
    @Override
    List findAll(Specification specification, Sort sort);
}
