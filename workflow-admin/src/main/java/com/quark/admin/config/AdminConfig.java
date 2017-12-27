package com.quark.admin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 扫描路径
 */
@Configuration
@ComponentScan(basePackages = "com.quark.common")
public class AdminConfig {
}
