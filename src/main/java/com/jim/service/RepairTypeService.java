package com.jim.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jim.base.result.ResponseCode;
import com.jim.base.result.Results;
import com.jim.mapper.RepairTypeMapper;
import com.jim.model.RepairType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class RepairTypeService  {
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

    /**
     * 添加报修类型
     * @param repairType
     * @return
     */
    public Results addRepairType(RepairType repairType) {
        if(repairTypeMapper.selectById(repairType.getId())!=null){
            return Results.failure(ResponseCode.REPAIR_TYPE_REPEAT.getCode(),ResponseCode.REPAIR_TYPE_REPEAT.getMessage());
        }
        repairType.setCtime(System.currentTimeMillis());
        repairType.setUtime(System.currentTimeMillis());
        int insert = repairTypeMapper.insert(repairType);
        if(insert>0){
            return  Results.ok();
        }
        return Results.failure();
    }

    /**
     * 通过id查找报修类型
     * @param id
     * @return
     */
    public RepairType findRepairTypeById(String id) {
        return repairTypeMapper.selectById(id);
    }

    /**
     * 编辑报修类型
     * @param repairType
     * @return
     */
    public Results editRepairType(RepairType repairType) {
        repairType.setUtime(System.currentTimeMillis());
        if(repairTypeMapper.updateById(repairType)>0){
            return Results.ok();
        }
        return Results.failure();
    }
}
