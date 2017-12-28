package com.workflow.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

/**
 * Created by mungai on 17/07/2017.
 */
@Configuration
@ConfigurationProperties("oracle")
public class OracleConfiguration {
    @NotNull
    String username;
    @NotNull
    String password;
    @NotNull
    String url;
}
