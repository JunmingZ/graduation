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
       Student student = new Student();
       student.setSno(4L);
        Dormitory dormitory = new Dormitory();
        dormitory.setId(4L);

       System.out.println(student.getSno().equals(dormitory.getId()));
    }

}
