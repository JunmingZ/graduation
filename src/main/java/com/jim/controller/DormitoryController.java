package com.jim.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.Results;
import com.jim.service.DormitoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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



}
