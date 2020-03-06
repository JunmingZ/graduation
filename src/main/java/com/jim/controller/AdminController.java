package com.jim.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.Results;
import com.jim.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    public AdminService adminService;

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


    ///**
    // * 批量删除
    // * @param ids
    // * @return
    // */
    //@PostMapping("/delete")
    //public Results deleteDormitoryByIds(String ids){
    //    return dormitoryService.deleteDormitoryByIds(ids);
    //}
    //
    //
    ///**
    // * 添加宿舍
    // * @param dormitory
    // * @return
    // */
    //@PostMapping("/add")
    //public Results addStudent(Dormitory dormitory){
    //    return dormitoryService.addDormitory(dormitory);
    //}
    //
    ///**
    // * 进入编辑页面
    // * @param modelAndView
    // * @param id
    // * @return
    // */
    //@GetMapping("/edit")
    //public ModelAndView toEditDormitory(ModelAndView modelAndView, String id){
    //    modelAndView.setViewName("dormitory-manage/dormitory-edit");
    //    Dormitory dormitory = dormitoryService.findDormitoryById(id);
    //    modelAndView.addObject("dormitory",dormitory);
    //    return modelAndView;
    //}
    //
    ///**
    // * 编辑宿舍
    // * @param dormitory 宿舍
    // * @return
    // */
    //@PostMapping("/edit")
    //public Results editDormitory(Dormitory dormitory){
    //    return dormitoryService.editDormitory(dormitory);
    //}
    //
    ///**
    // * 查看宿舍信息
    // * @param id 宿舍号
    // * @return
    // */
    //@GetMapping("/info")
    //public ModelAndView dormitoryInfo(ModelAndView modelAndView,String id){
    //    List<Student> dormitoryInfo = dormitoryService.findDormitoryInfo(id);
    //    modelAndView.addObject("dormitoryInfo",dormitoryInfo);
    //    modelAndView.setViewName("dormitory-manage/dormitory-info");
    //    return modelAndView;
    //}

}
