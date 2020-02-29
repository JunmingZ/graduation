package com.jim.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.Results;
import com.jim.service.RepairsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import java.util.Map;

@RestController
@RequestMapping("/repairs")
public class RepairsController {

    @Resource
    private RepairsService repairsService;

    /**
     * 进入统计页面
     * @param modelAndView
     * @return
     */
    @GetMapping("/statistics")
    public ModelAndView toRepairStatistics(ModelAndView modelAndView){
        Map map = repairsService.getRepairStatisticsDTO();
        modelAndView.addObject("types",map.get("types"));
        modelAndView.addObject("RepairStatistics",map.get("RepairStatistics"));
        modelAndView.setViewName("repair-manage/repair-statistics");
        return modelAndView;
    }



    @GetMapping("/list")
    public Results repairsList(PageTableRequest pageTableRequest
                                , @RequestParam(required = false) String sno
                                , @RequestParam(required = false) Integer state){
        //  getPage ：当前页   getLimit ：显示条数数目
        return repairsService.getAllRepairsByPage(new Page(pageTableRequest.getPage(),pageTableRequest.getLimit()),sno,state);
    }
}
