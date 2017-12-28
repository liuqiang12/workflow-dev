package com.workflow.design;

import com.workflow.design.server.QuarkChatServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
@EnableCaching//缓存支持
public class DesignApplication  implements CommandLineRunner {
    @Autowired
    private QuarkChatServer server;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        InputStream in = DesignApplication.class.getClassLoader().getResourceAsStream("design.properties");
        properties.load(in);
        SpringApplication app = new SpringApplication(DesignApplication.class);
        app.setDefaultProperties(properties);
        app.run(args);
    }

    @Bean
    public QuarkChatServer quarkChatServer(){
        return new QuarkChatServer();
    }

    @Override
    public void run(String... strings) throws Exception {
        server.start();
    }
}
