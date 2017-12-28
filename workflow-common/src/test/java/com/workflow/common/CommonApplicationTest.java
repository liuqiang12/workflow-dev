package com.workflow.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@EnableCaching//缓存支持
@SpringBootTest
public class CommonApplicationTest {

    /*@Autowired
    private DataSource dataSource;*/

    /*@Autowired
    private com.workflow.common.dao.UserDao UserDao;*/

    @Test
    public void TestDataSource(){
//        long count = notificationDao.getNotificationCount(72);
//        System.out.println(count);
//        List<Notification> list = notificationDao.getByTouser(UserDao.findOne(2));
//        System.out.println(list);
//        list.forEach(t->{
//            System.out.println(t.getPosts().getTitle());
//        });
    }
}
