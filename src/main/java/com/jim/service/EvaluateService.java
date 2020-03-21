package com.jim.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.enums.ResponseCode;
import com.jim.base.result.Results;
import com.jim.dto.EvaluateDTO;
import com.jim.mapper.EvaluateMapper;
import com.jim.mapper.RepairsMapper;
import com.jim.model.Evaluate;
import com.jim.model.Repairs;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

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
            Repairs repairs = repairsMapper.selectOne(evaluateId);  //通过evaluation_id查找报修任务表的相关信息
            BeanUtils.copyProperties(record,evaluateDTO);
            if(repairs!=null){
                evaluateDTO.setSno(repairs.getSno());
                evaluateDTO.setRepairmanId(repairs.getRepairmanId());
            }
            list.add(evaluateDTO);
        }

        return Results.success((int) iPage.getTotal(),list);
    }
    /**
     * 通过id单个删除
     * @param id
     * @return
     */
    public Results deleteEvaluateById(String id) {
        if(StringUtils.isEmpty(id)){
            return Results.failure(ResponseCode.DELETE_ID_IS_NULL.getCode(),ResponseCode.DELETE_ID_IS_NULL.getMessage());
        }
        if(evaluateMapper.deleteById(id)>0){
            QueryWrapper<Repairs> wrapper = new QueryWrapper<>();
            wrapper.eq("evaluation_id",id);
            Repairs repairs = new Repairs();
            repairs.setEvaluationId(0L);
            repairsMapper.update(repairs,wrapper);
            return Results.ok();
        }else {
            return Results.failure();
        }
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    public Results deleteEvaluateByIds(String ids) {
        if(StringUtils.isEmpty(ids)){
            return Results.failure(ResponseCode.DELETE_ID_IS_NULL.getCode(),ResponseCode.DELETE_ID_IS_NULL.getMessage());
        }
        String[] split = ids.split(",");

        for (String id : split) {
            QueryWrapper<Repairs> wrapper = new QueryWrapper<>();
            wrapper.eq("evaluation_id", id);
            Repairs repairs = new Repairs();
            repairs.setEvaluationId(0L);
            repairsMapper.update(repairs, wrapper);
            return null;
        }

        if(evaluateMapper.deleteBatchIds(Arrays.asList(split))==0){
            return Results.failure();
        }
        return Results.ok();
    }


    /**
     * 获取评价信息
     * @param id
     * @return
     */
    public Map getEvaluateInfo(String id) {
        Evaluate evaluate = evaluateMapper.selectById(id);

        QueryWrapper<Repairs> wrapper = new QueryWrapper();
        wrapper.eq("evaluation_id",id);
        Repairs repairs = repairsMapper.selectOne(wrapper);

        Map map = new HashMap();
        map.put("evaluate",evaluate);
        map.put("repairs",repairs);
        return map;
    }
}
