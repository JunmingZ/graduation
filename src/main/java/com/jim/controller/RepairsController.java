package com.jim.controller;


import com.alibaba.fastjson.JSON;
import com.jim.service.RepairsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/repairs")
public class RepairsController {

    @Resource
    private RepairsService repairsService;

    @GetMapping("/statistics")
    public ModelAndView toRepairStatistics(ModelAndView modelAndView){
        Map map = repairsService.getRepairStatisticsDTO();
        modelAndView.addObject("types",map.get("types"));
        modelAndView.addObject("RepairStatistics",map.get("RepairStatistics"));
        modelAndView.setViewName("repair-manage/repair-statistics");
        return modelAndView;
    }
}
