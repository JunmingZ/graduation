package com.jim.controller;

import com.jim.base.enums.ResponseCode;
import com.jim.base.result.Results;
import com.jim.dto.LoginDTO;
import com.jim.model.Student;
import com.jim.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 注册控制层
 */
@RestController
public class RegisterController {

    @Resource
    private StudentService studentService;

    @PostMapping("/register/add")
    public Results studentRegister(Student student){
        return studentService.addStudent(student);
    }

    /**
     * 去往学生注册页面
     * @param modelAndView
     * @return
     */
    @GetMapping("/register/student-register")
    public ModelAndView toStudentRegister(ModelAndView modelAndView){
        modelAndView.setViewName("register/student-register");
        return modelAndView;
    }


    /**
     * 进入注册页面
     * @param modelAndView
     * @return
     */
    @GetMapping("/register")
    public ModelAndView getPage(ModelAndView modelAndView){
        modelAndView.setViewName("register");
        return modelAndView;
    }


    /**
     * 返回学生注册页面网址
     * @param register
     * @return
     */
    @PostMapping("/register")
    public Results toRegister(LoginDTO register){
        // 校验注册用户
        if(register==null ){
            return Results.failure(ResponseCode.OBJECT_IS_NULL);
        }

        // 检查注册学生是否在主表已经存在
        Student studentBySno = studentService.findStudentBySno(register.getId().toString());

        if(studentBySno == null ){
            register.setUrl("/register/student-register");
            return Results.success(register);
        }else {
            return Results.failure(ResponseCode.SNO_REPEAT.getCode(),ResponseCode.SNO_REPEAT.getMessage());
        }
    }

}
