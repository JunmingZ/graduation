package com.jim.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.enums.ResponseCode;
import com.jim.base.result.Results;
import com.jim.mapper.RepairTypeMapper;
import com.jim.mapper.RepairsMapper;
import com.jim.model.RepairType;
import com.jim.model.Repairs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional   //加入事务
public class RepairTypeService  {
    @Resource
    private RepairTypeMapper repairTypeMapper;
    @Resource
    private RepairsMapper repairsMapper;
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
            return  Results.success();
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
            return Results.success();
        }
        return Results.failure();
    }

    /**
     * 通过id单个删除
     * @param id
     * @return
     */
    public Results deleteRepairTypeById(String id) {
        // 1。判断是否在使用，若是在使用，不许删除
        QueryWrapper<Repairs> wrapper = new QueryWrapper<>();
        wrapper.eq("type_id",id);
        Integer flag = 0;
        Integer count = repairsMapper.selectCount(wrapper);

        // 报修表存在不给删除
        if(count>0){
            return Results.failure();
        }else {
            flag = repairTypeMapper.deleteById(id);
        }
        if(flag>0){
            return Results.success();
        }else {
            return Results.failure();
        }


    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    public Results deleteRepairTypeByIds(String ids) {
        if(StringUtils.isEmpty(ids)){
            return Results.failure();
        }
        Integer flag = 0;
        String[] split = ids.split(",");
        for (String id:split){
            QueryWrapper<Repairs> wrapper = new QueryWrapper<>();
            wrapper.eq("type_id",id);
            Integer count = repairsMapper.selectCount(wrapper);
            if(count>0){
                // 在使用
                return Results.failure();
            }else {
                flag = repairTypeMapper.deleteById(id);
            }
        }
        if(flag>0){
            return Results.success();
        }else {
            return Results.failure();
        }
    }

    /**
     * 单纯获取集合
     * @return
     */
    public List<RepairType> getAllRepairType() {
        return repairTypeMapper.selectList(null);
    }

}
