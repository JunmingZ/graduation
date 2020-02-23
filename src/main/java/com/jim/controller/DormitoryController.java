package com.jim.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.Results;
import com.jim.model.Dormitory;
import com.jim.model.Student;
import com.jim.service.DormitoryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RestController
@RequestMapping("/dormitory")
public class DormitoryController {
    @Resource
    private DormitoryService dormitoryService;

    @GetMapping("/list")
    public Results toDormitoryList(PageTableRequest pageTableRequest, @RequestParam(required = false) String id){
        return dormitoryService.getAllDormitoryByPage(new Page(pageTableRequest.getPage(),pageTableRequest.getLimit()),id);
    }


    /**
     * 通过id单个删除
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Results deleteDormitoryById(String id){
        return dormitoryService.deleteDormitoryById(id);
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public Results deleteDormitoryByIds(String ids){
        return dormitoryService.deleteDormitoryByIds(ids);
    }


    /**
     * 添加宿舍
     * @param dormitory
     * @return
     */
    @PostMapping("/add")
    public Results addStudent(Dormitory dormitory){
        return dormitoryService.addDormitory(dormitory);
    }

    /**
     * 进入编辑页面
     * @param modelAndView
     * @param id
     * @return
     */
    @GetMapping("/edit")
    public ModelAndView toEditDormitory(ModelAndView modelAndView, String id){
        modelAndView.setViewName("dormitory-manage/dormitory-edit");
        Dormitory dormitory = dormitoryService.findDormitoryById(id);
        modelAndView.addObject("dormitory",dormitory);
        return modelAndView;
    }

    /**
     * 编辑宿舍
     * @param dormitory 宿舍
     * @return
     */
    @PostMapping("/edit")
    public Results editDormitory(Dormitory dormitory){
        return dormitoryService.editDormitory(dormitory);
    }

}
