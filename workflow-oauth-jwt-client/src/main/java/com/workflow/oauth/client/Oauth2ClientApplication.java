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
public class Oauth2ClientApplication {

	public static void main(String[] args) throws IOException {
		Properties properties = new Properties();
		InputStream in = Oauth2ClientApplication.class.getClassLoader().getResourceAsStream("oauth-jwt-client.properties");
		properties.load(in);
		SpringApplication app = new SpringApplication(Oauth2ClientApplication.class);
		app.setDefaultProperties(properties);
		app.run(args);
	}
}
