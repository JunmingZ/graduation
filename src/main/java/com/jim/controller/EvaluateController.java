package com.jim.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.enums.ResponseCode;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.Results;
import com.jim.dto.RepairsDTO;
import com.jim.model.Evaluate;
import com.jim.service.EvaluateService;
import org.springframework.util.StringUtils;
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
     * 添加评价
     * @param evaluate
     * @param repairId
     * @return
     */
    @PostMapping("/add")
    public Results addEvaluation(Evaluate evaluate, Long repairId){
        if(evaluate==null){
            return Results.failure(ResponseCode.OBJECT_IS_NULL);
        }
        if(repairId==null || repairId == 0){
            return Results.failure(ResponseCode.EVALUATE_OF_REPAIRID_NULL);
        }

        return evaluateService.addEvaluation(evaluate,repairId);
    }

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
        RepairsDTO evaluateInfo = evaluateService.getEvaluateInfo(id);
        modelAndView.addObject("evaluateInfo",evaluateInfo);
        modelAndView.setViewName("evaluation/evaluation-info");
        return modelAndView;
    }

}
