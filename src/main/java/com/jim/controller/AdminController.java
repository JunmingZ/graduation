package com.jim.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.enums.ResponseCode;
import com.jim.base.result.Results;
import com.jim.model.Admin;
import com.jim.service.AdminService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    public AdminService adminService;

    /**
     * 进入欢迎页
     * @param modelAndView
     * @return
     */
    @GetMapping("/welcome")
    public ModelAndView toWelcome(ModelAndView modelAndView){
        modelAndView.setViewName("admin/welcome");
        return modelAndView;
    }


    @GetMapping("/list")
    public Results getAdminList(PageTableRequest pageTableRequest, @RequestParam(required = false) String id){
        return adminService.getAllAdminListByPage(new Page(pageTableRequest.getPage(),pageTableRequest.getLimit()),id);
    }


    /**
     * 通过id单个删除
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Results deleteAdminById(String id){
        return adminService.deleteAdminById(id);
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public Results deleteAdminByIds(String ids){
        return adminService.deleteAdminByIds(ids);
    }


    /**
     * 添加管理员
     * @param admin
     * @return
     */
    @PostMapping("/add")
    public Results addStudent(Admin admin){
        if(admin == null){
            return Results.failure(ResponseCode.OBJECT_IS_NULL.getCode(),ResponseCode.OBJECT_IS_NULL.getMessage());
        }
        return adminService.addAdmin(admin);
    }

    /**
     * 进入编辑页面
     * @param modelAndView
     * @param id
     * @return
     */
    @GetMapping("/edit")
    public ModelAndView toEditAdmin(ModelAndView modelAndView, String id){
        modelAndView.setViewName("admin/admin-edit");
        Admin admin = adminService.findAdminById(id);
        modelAndView.addObject("admin",admin);
        return modelAndView;
    }

    /**
     * 编辑宿舍
     * @param admin 宿舍
     * @return
     */
    @PostMapping("/edit")
    public Results editDormitory(Admin admin){
        if(admin == null){
            return Results.failure(ResponseCode.OBJECT_IS_NULL.getCode(),ResponseCode.OBJECT_IS_NULL.getMessage());
        }
        return adminService.editAdmin(admin);
    }

    /**
     * 查看宿舍信息
     * @param id 宿舍号
     * @return
     */
    //@GetMapping("/info")
    //public ModelAndView dormitoryInfo(ModelAndView modelAndView,String id){
    //    List<Student> dormitoryInfo = dormitoryService.findDormitoryInfo(id);
    //    modelAndView.addObject("dormitoryInfo",dormitoryInfo);
    //    modelAndView.setViewName("dormitory-manage/dormitory-info");
    //    return modelAndView;
    //}

}
