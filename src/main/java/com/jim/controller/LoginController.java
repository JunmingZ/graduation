package com.jim.controller;


import com.jim.base.enums.ResponseCode;
import com.jim.base.result.Results;
import com.jim.dto.LoginDTO;
import com.jim.service.AdminService;
import com.jim.service.RepairmanService;
import com.jim.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Resource
    private AdminService adminService;

    @Resource
    private StudentService studentService;

    @Resource
    private RepairmanService repairmanService;

    @GetMapping("/login")
    public String toLogin(){
        return "/login";
    }



    @PostMapping("/login")
    @ResponseBody
    public Results login(LoginDTO login,HttpSession session){
        if(login==null || login.getSole()==null || login.getSole() == 0){
            return Results.failure(ResponseCode.OBJECT_IS_NULL.getCode(),ResponseCode.OBJECT_IS_NULL.getMessage());
        }
        //确定角色
        switch (login.getSole()){
            case 1:
                return studentService.checkStudent(login,session);  //学生
            case 2:
                return repairmanService.checkRepairman(login,session);    //维修员
            case 3:
                return adminService.checkAdmin(login,session);  // 管理员
        }

        return Results.failure();
    }
}
