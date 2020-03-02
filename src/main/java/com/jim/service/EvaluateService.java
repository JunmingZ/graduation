package com.jim.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.Results;
import com.jim.dto.EvaluateDTO;
import com.jim.mapper.EvaluateMapper;
import com.jim.mapper.RepairsMapper;
import com.jim.model.Evaluate;
import com.jim.model.Repairs;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 报修评价业务层
 */
@Service
public class EvaluateService {

    @Resource
    private EvaluateMapper evaluateMapper;

    @Resource
    private RepairsMapper repairsMapper;
    /**
     * 获取评价列表
     * @param page
     * @param id
     * @return
     */
    public Results getEvaluateListByPage(Page page, String id) {
        QueryWrapper<Evaluate> wrapper = new QueryWrapper<>();
        IPage iPage = null;
        if(!StringUtils.isEmpty(id)){
            //模糊查询
            wrapper.like("id",id);
        }
        iPage = evaluateMapper.selectPage(page,wrapper);
        List<Evaluate> records = iPage.getRecords();
        List<EvaluateDTO> list  = new ArrayList<>();
        for (Evaluate record : records) {
            EvaluateDTO evaluateDTO = new EvaluateDTO();
            QueryWrapper<Repairs> evaluateId = new QueryWrapper<>();
            evaluateId.eq("evaluation_id",record.getId());
            Repairs repairs = repairsMapper.selectOne(evaluateId);
            BeanUtils.copyProperties(record,evaluateDTO);
            evaluateDTO.setSno(repairs.getSno());
            evaluateDTO.setRepairmanId(repairs.getRepairmanId());
            list.add(evaluateDTO);
        }

        return Results.success((int) iPage.getTotal(),list);
    }
}
