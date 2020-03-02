package com.jim.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.Results;
import com.jim.mapper.EvaluateMapper;
import com.jim.model.Dormitory;
import com.jim.model.Evaluate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 报修评价业务层
 */
@Service
public class EvaluateService {

    @Resource
    private EvaluateMapper evaluateMapper;


    public Results getEvaluateListByPage(Page page, String id) {
        QueryWrapper<Evaluate> wrapper = new QueryWrapper<>();
        IPage iPage = null;
        if(!StringUtils.isEmpty(id)){
            //模糊查询
            wrapper.like("id",id);
        }
        iPage = evaluateMapper.selectPage(page,wrapper);
        return Results.success((int) iPage.getTotal(),iPage.getRecords());
    }
}
