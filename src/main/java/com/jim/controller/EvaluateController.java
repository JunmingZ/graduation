package com.jim.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.Results;
import com.jim.model.Evaluate;
import com.jim.service.EvaluateService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/evaluate")
public class EvaluateController {
    @Resource
    private EvaluateService evaluateService;

    /**
     * 获取评价列表
     * @param pageTableRequest
     * @param id
     * @return
     */
    @GetMapping("/list")
    public Results getEvaluateList(PageTableRequest pageTableRequest, @RequestParam(required = false) String id){
        return evaluateService.getEvaluateListByPage(new Page(pageTableRequest.getPage(),pageTableRequest.getLimit()),id);
    }


    /**
     * 通过id单个删除
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Results deleteEvaluateById(String id){
        return evaluateService.deleteEvaluateById(id);
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public Results deleteEvaluateByIds(String ids){
        return evaluateService.deleteEvaluateByIds(ids);
    }


    /**
     * 查看报修评价的详细信息
     * @param id
     * @param modelAndView
     * @return
     */
    @GetMapping("/info")
    public ModelAndView toEvaluateInfo(String id,ModelAndView modelAndView){
        Map evaluateInfo = evaluateService.getEvaluateInfo(id);
        modelAndView.addAllObjects(evaluateInfo);
        modelAndView.setViewName("evaluation/evaluation-info");
        return modelAndView;
    }

}
