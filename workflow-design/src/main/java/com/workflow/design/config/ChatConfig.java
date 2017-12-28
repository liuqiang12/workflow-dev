package com.workflow.design.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.workflow.common","com.workflow.design"})
public class ChatConfig {
}
