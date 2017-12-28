package com.workflow.oauth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * user.setUsername("DELL");
 * user.setPassword("root");
 */
@RunWith(SpringRunner.class)
@TestPropertySource(locations = {"classpath:oauth.properties"})
@SpringBootTest
public class OauthApplicationTest {
/*
    @Autowired
    DataSource dataSource;*/




    @Test
    public void testRole(){
        /*adminUserService.saveAdminEnable(new Integer[]{11,12,13});*/
    }
}
