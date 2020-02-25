package com.jim.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.Results;
import com.jim.mapper.RepairTypeMapper;
import com.jim.model.RepairType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class RepairTypeService {
    @Resource
    private RepairTypeMapper repairTypeMapper;
    /**
     * 按类型搜索
     * @param page
     * @param type
     * @return
     */
    public Results getAllRepairTypeByPage(Page page, String type) {
        QueryWrapper<RepairType> wrapper = new QueryWrapper<>();
        IPage iPage = null;
        if(!StringUtils.isEmpty(type)){
            //模糊查询
            wrapper.like("type",type);
        }
        iPage = repairTypeMapper.selectPage(page,wrapper);
        return Results.success((int) iPage.getTotal(),iPage.getRecords());
    }
}
