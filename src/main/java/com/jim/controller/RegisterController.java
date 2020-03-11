package com.jim.controller;

import com.jim.base.result.ResponseCode;
import com.jim.base.result.Results;
import com.jim.dto.LoginDTO;
import com.jim.model.Student;
import com.jim.service.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RegisterController {

    @Resource
    private StudentService studentService;

    @PostMapping("/register")
    public Results toRegister(LoginDTO register){
        if(register==null || register.getSole()==null || register.getSole() == 0){
            return Results.failure(ResponseCode.OBJECT_IS_NULL.getCode(),ResponseCode.OBJECT_IS_NULL.getMessage());
        }
        System.out.println(register);
        if(register.getSole() == 1){
            Student studentBySno = studentService.findStudentBySno(register.getId().toString());
            System.out.println(studentBySno);
            if(studentBySno == null){
                return Results.ok("弹出注册学生页面");
            }else {
                return Results.failure(ResponseCode.SNO_REPEAT.getCode(),ResponseCode.SNO_REPEAT.getMessage());
            }
        }

        return Results.failure();
    }
}
