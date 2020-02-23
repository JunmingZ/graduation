package com.jim.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.ResponseCode;
import com.jim.base.result.Results;
import com.jim.mapper.DormitoryMapper;
import com.jim.model.Dormitory;
import com.jim.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 宿舍业务层
 */
@Service
public class DormitoryService {
    @Resource
    private DormitoryMapper dormitoryMapper;

    /**
     * 兼搜索的分页列表
     * @param page
     * @param id
     * @return
     */
    public Results getAllDormitoryByPage(Page page, String id) {
        QueryWrapper<Dormitory> wrapper = new QueryWrapper<>();
        IPage iPage = null;
        if(!StringUtils.isEmpty(id)){
            //模糊查询
            wrapper.like("id",id);
        }
        iPage = dormitoryMapper.selectPage(page,wrapper);
        return Results.success((int) iPage.getTotal(),iPage.getRecords());
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    public Results deleteDormitoryById(String id) {
        if(dormitoryMapper.deleteById(id)>0){
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
    public Results deleteDormitoryByIds(String ids) {
        if(StringUtils.isEmpty(ids)){
            return Results.failure();
        }
        String[] split = ids.split(",");
        int flag=0;
        for (String s : split) {
            flag = dormitoryMapper.deleteById(s);
        }
        if(flag==0){
            return Results.failure();
        }
        return Results.ok();
    }

    /**
     * 添加宿舍
     * @param dormitory
     * @return
     */
    public Results addDormitory(Dormitory dormitory) {
        if(dormitoryMapper.selectById(dormitory.getId())!=null){
            return Results.failure(ResponseCode.Dormitory_REPEAT.getCode(),ResponseCode.Dormitory_REPEAT.getMessage());
        }
        dormitory.setCtime(System.currentTimeMillis());
        dormitory.setUtime(System.currentTimeMillis());
        int insert = dormitoryMapper.insert(dormitory);
        if(insert>0){
            return  Results.ok();
        }
        return Results.failure();
    }
}
