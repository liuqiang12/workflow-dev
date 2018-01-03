package com.workflow.oauth.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching//缓存支持
public class OauthJwtClientApplication {

	public static void main(String[] args) throws IOException {
		Properties properties = new Properties();
		InputStream in = OauthJwtClientApplication.class.getClassLoader().getResourceAsStream("oauth-jwt-client.properties");
		properties.load(in);
		SpringApplication app = new SpringApplication(OauthJwtClientApplication.class);
		app.setDefaultProperties(properties);
		app.run(args);
	}
}
