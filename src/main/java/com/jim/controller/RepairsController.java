package com.jim.controller;


import com.jim.service.RepairsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RestController
@RequestMapping("/repairs")
public class RepairsController {

    @Resource
    private RepairsService repairsService;

    @GetMapping("/statistics")
    public ModelAndView toRepairStatistics(ModelAndView modelAndView){
        modelAndView.addObject("types",repairsService.getTypes());
        modelAndView.setViewName("repair-manage/repair-statistics");
        return modelAndView;
    }
}
