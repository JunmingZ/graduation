package com.jim.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.Results;
import com.jim.model.Dormitory;
import com.jim.model.RepairType;
import com.jim.service.RepairTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/repairType")
public class RepairTypeController {

    @Resource
    private RepairTypeService repairTypeService;

    /**
     * 查出相应数据返回前端
     * @param pageTableRequest 分页初始处理
     * @return
     */
    @GetMapping("/list")
    public Results studentList(PageTableRequest pageTableRequest, @RequestParam(required = false) String type){
        return repairTypeService.getAllRepairTypeByPage(new Page(pageTableRequest.getPage(),pageTableRequest.getLimit()),type);
    }


    /**
     * 添加报修类型
     * @param repairType
     * @return
     */
    @PostMapping("/add")
    public Results addStudent(RepairType repairType){
        return repairTypeService.addRepairType(repairType);
    }
}
