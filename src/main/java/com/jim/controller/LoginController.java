package com.jim.controller;


import com.jim.base.result.ResponseCode;
import com.jim.base.result.Results;
import com.jim.dto.LoginDTO;
import com.jim.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class LoginController {

    @Resource
    private AdminService adminService;

    @GetMapping("/login")
    public String toLogin(){
        return "/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Results login(LoginDTO login){
        if(login==null){
            return Results.failure(ResponseCode.OBJECT_IS_NULL.getCode(),ResponseCode.OBJECT_IS_NULL.getMessage());
        }

        if(login.getSole().equals(3)){
                return adminService.checkAdmin(login);
        }

        return Results.failure();
    }
}
