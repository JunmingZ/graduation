package com.jim;

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
        List<Student> students = student.selectAll();
        students.stream().forEach(System.out::println);
    }

}
