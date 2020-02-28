package com.jim.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.Results;
import com.jim.service.RepairmanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/repairman")
public class RepairmanController {

    @Resource
    private RepairmanService repairmanService;


    @GetMapping("/repairmans")
    public List repairmanList(){
        return repairmanService.getAllRepairman();
    }

    @GetMapping("/list")
    public Results repairmanList(PageTableRequest pageTableRequest, @RequestParam(required = false) String id){
        return repairmanService.getAllRepairmanByPage(new Page(pageTableRequest.getPage(),pageTableRequest.getLimit()),id);
    }

}
