package com.jim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * ${api-url} 引入application.yml的api-url
 */
@Controller
@RequestMapping("/api")
public class ApiController {

    @RequestMapping("/getPage")
    public ModelAndView getPage(ModelAndView modelAndView,String pageName){
        modelAndView.setViewName(pageName);
        return modelAndView;
    }
}
