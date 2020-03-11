package com.jim.controller;

import com.jim.base.result.ResponseCode;
import com.jim.base.result.Results;
import com.jim.dto.LoginDTO;
import com.jim.mapper.StudentCheckMapper;
import com.jim.model.Student;
import com.jim.model.StudentCheck;
import com.jim.service.StudentCheckService;
import com.jim.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RegisterController {

    @Resource
    private StudentService studentService;

    @Autowired
    private StudentCheckService studentCheckService;
    /**
     * 前往注册页面
     * @param register
     * @return
     */
    @PostMapping("/register")
    public Results toRegister(LoginDTO register){
        // 校验注册用户
        if(register==null || register.getSole()==null || register.getSole() == 0){
            return Results.failure(ResponseCode.OBJECT_IS_NULL.getCode(),ResponseCode.OBJECT_IS_NULL.getMessage());
        }
        // 学生
        if(register.getSole() == 1){
            // 检查注册学生是否在主表已经存在
            Student studentBySno = studentService.findStudentBySno(register.getId().toString());
            // 检查是否在附表存在
            StudentCheck studentCheck = studentCheckService.studentCheckById(register.getId().toString());
            if(studentBySno == null && studentCheck == null){
                System.out.println("提交注册页面地址");
                register.setUrl("/register/student-register");
                return Results.success(register);
            }else {
                return Results.failure(ResponseCode.SNO_REPEAT.getCode(),ResponseCode.SNO_REPEAT.getMessage());
            }
        }

        return Results.failure();
    }
}
