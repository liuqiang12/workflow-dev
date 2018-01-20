package com.workflow.oauth.jwt.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 扫描路径
 */
@Configuration
@ComponentScan(basePackages = "com.workflow.common")
@ComponentScan(basePackages = "com.workflow.oauth")
public class ComponentScanConfig {

}
