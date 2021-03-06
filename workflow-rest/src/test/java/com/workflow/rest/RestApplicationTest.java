package com.workflow.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * user.setUsername("DELL");
 * user.setPassword("root");
 */
@RunWith(SpringRunner.class)
@TestPropertySource(locations = {"classpath:rest.properties"})
@SpringBootTest
public class RestApplicationTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Value("${REDIS_USERID_KEY}")
    private String REDIS_USERID_KEY;
    @Test
    public void testDataSource() {
//        userService.updateUserPassword("2a8e48c8-9d09-4ef4-892c-b3436070297c","12345678","123456");
//        redisService.cacheSet(REDIS_USERID_KEY,8);
//        redisService.cacheSet(REDIS_USERID_KEY,10);
//        redisService.deleteSet(REDIS_USERID_KEY,8);
//            System.out.println(redisService.setHasValue(REDIS_USERID_KEY,5));
//        postsDao.findAll();
    }

}
