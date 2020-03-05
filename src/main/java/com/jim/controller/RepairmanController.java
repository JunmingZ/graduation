package com.jim.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.ResponseCode;
import com.jim.base.result.Results;
import com.jim.service.RepairmanService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 更新维修人在职状态
     * @param flag
     * @return
     */
    @PostMapping("/flag")
    public Results repairmanUpdateFlag(String id,Integer flag){
        if(StringUtils.isEmpty(flag)||StringUtils.isEmpty(id)){
            return Results.failure(ResponseCode.REPAIRMAN_FLAG_NULL.getCode(),ResponseCode.REPAIRMAN_FLAG_NULL.getMessage());
        }

        return repairmanService.updateFlag(id,flag);
    }


}
