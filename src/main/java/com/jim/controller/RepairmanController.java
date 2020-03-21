package com.jim.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.enums.ResponseCode;
import com.jim.base.result.Results;
import com.jim.model.Repairman;
import com.jim.service.RepairmanService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/repairman")
public class RepairmanController {

    @Resource
    private RepairmanService repairmanService;

    @GetMapping("/irepairman")
    public ModelAndView repairmanList(ModelAndView modelAndView){
        modelAndView.setViewName("repairman/irepairman");
        return modelAndView;
    }

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


    /**
     * 添加维修人员
     * @param repairman
     * @return
     */
    @PostMapping("/add")
    public Results addRepairs(Repairman repairman){

        if(repairman.equals(null)){
            return Results.failure(ResponseCode.OBJECT_IS_NULL.getCode(),ResponseCode.OBJECT_IS_NULL.getMessage());
        }

        return repairmanService.addRepairman(repairman);
    }

    @GetMapping("/edit")
    public ModelAndView toEditRepairman(ModelAndView modelAndView,Integer id){
        Repairman repairman = repairmanService.getRepairmanById(id);
        modelAndView.addObject("repairman",repairman);
        modelAndView.setViewName("repairman/repairman-edit");
         return modelAndView;
    }


    @PostMapping("/edit")
    public Results editRepairman(Repairman repairman){
        if(repairman.equals(null)){
            return Results.failure(ResponseCode.OBJECT_IS_NULL.getCode(),ResponseCode.OBJECT_IS_NULL.getMessage());
        }
        return  repairmanService.updateRepairmanByModel(repairman);
    }

}
