package com.jim.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.Results;
import com.jim.service.EvaluateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
