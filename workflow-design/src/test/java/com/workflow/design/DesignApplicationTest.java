package com.workflow.design;

import com.workflow.design.handler.UserAuthHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = {"classpath:design.properties"})
@SpringBootTest
public class DesignApplicationTest {


    @Autowired
    private UserAuthHandler handler;

    @Test
    public void testNumber() {
        byte a = 0x10;
       System.out.println(a);
    }

    @Test
    public void testService(){
//       handler.test();
    }

}
