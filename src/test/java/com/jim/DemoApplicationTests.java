package com.jim;

import com.jim.model.Dormitory;
import com.jim.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
       String a= "11111";
       String b = "11111";

       System.out.println(a==b);
    }

}
