package com.jim.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.Results;
import com.jim.mapper.RepairmanMapper;
import com.jim.model.Repairman;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RepairmanService {

    @Resource
    private RepairmanMapper repairmanMapper;

    /**
     * 获取维修人列表
     * @param page
     * @param id
     * @return
     */
    public Results getAllRepairmanByPage(Page page, String id) {
        QueryWrapper<Repairman> wrapper = new QueryWrapper<>();
        IPage iPage = null;
        if(!StringUtils.isEmpty(id)){
            //模糊查询
            wrapper.like("id",id);
        }
        iPage = repairmanMapper.selectPage(page,wrapper);
        return Results.success((int) iPage.getTotal(),iPage.getRecords());
    }

    /**
     * 单纯获取维修人列表
     * @return
     */
    public List getAllRepairman() {
        return repairmanMapper.selectList(null);
    }
}
