package com.jim.controller;


import com.jim.base.enums.ResponseCode;
import com.jim.base.result.Results;
import com.jim.config.UserToken;
import com.jim.dto.LoginDTO;
import com.jim.model.Student;
import com.jim.service.AdminService;
import com.jim.service.RepairmanService;
import com.jim.service.StudentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    /**
     * 登出交由shiro
     * @param model
     * @return
     */
    //@GetMapping("/logout")
    //public String toLogout(Model model){
    //    Subject subject = SecurityUtils.getSubject();
    //    System.out.println("退出");
    //    subject.logout();
    //    model.addAttribute("msg","安全退出！");
    //    return "login";
    //}


    /**
     * 进入登录页面
     * @return
     */
    @GetMapping("/login")
    public String toLogin(){
        return "/login";
    }

    /**
     * 未授权
     * @return
     */
    @GetMapping("/noAuth")
    public String toNoAuth(){
        return "noAuth";
    }

    @PostMapping("/login")
    @ResponseBody
    public Results login(LoginDTO login){
        if(login==null || login.getSole()==null ){
            return Results.failure(ResponseCode.OBJECT_IS_NULL);
        }

        Subject subject = SecurityUtils.getSubject();
        // 3. 执行登录方法
        try {
            subject.login(new UserToken(login.getId(),login.getPassword(),login.getSole()));
            //确定角色
            switch (login.getSole()){
                case "Student":
                    return studentService.checkStudent(subject);  //学生
                case "Repairman":
                    return repairmanService.checkRepairman(subject);    //维修员
                case "Admin":
                    return adminService.checkAdmin(subject);  // 管理员
            }
        }catch (Exception e){
            return Results.failure(ResponseCode.LOGIN_ACCPASS_NOT_FOUND);
        }

        return Results.failure();
    }
}
