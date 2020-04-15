package com.jim.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.enums.ResponseCode;
import com.jim.base.result.Results;
import com.jim.mapper.DormitoryMapper;
import com.jim.mapper.StudentMapper;
import com.jim.model.Dormitory;
import com.jim.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 宿舍业务层
 */
@Service
@Transactional   //加入事务
public class DormitoryService {
    @Resource
    private DormitoryMapper dormitoryMapper;

    @Resource
    private StudentMapper studentMapper;
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
     * 通过id删除,将更新学生的宿舍号为null
     * @param id
     * @return
     */
    public Results deleteDormitoryById(String id) {
        if(dormitoryMapper.deleteById(id)>0){
            QueryWrapper<Student> wrapper = new QueryWrapper<>();
            wrapper.eq("dormitory",id);
            Student student = new Student();
            student.setDormitory(0L);
            studentMapper.update(student,wrapper);
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

        if(dormitoryMapper.deleteBatchIds(Arrays.asList(split))==0){
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
            return Results.failure(ResponseCode.DORMITORY_REPEAT.getCode(),ResponseCode.DORMITORY_REPEAT.getMessage());
        }
        dormitory.setCtime(System.currentTimeMillis());
        dormitory.setUtime(System.currentTimeMillis());
        int insert = dormitoryMapper.insert(dormitory);
        if(insert>0){
            return  Results.ok();
        }
        return Results.failure();
    }

    /**
     * 通过id查找宿舍
     * @param id
     * @return
     */
    public Dormitory findDormitoryById(String id) {
        return dormitoryMapper.selectById(id);
    }

    /**
     * 编辑宿舍信息
     * @param dormitory
     * @return
     */
    public Results editDormitory(Dormitory dormitory) {
        dormitory.setUtime(System.currentTimeMillis());
        int i = dormitoryMapper.updateById(dormitory);
        if(i>0){
            return Results.ok();
        }
        return Results.failure();
    }

    /**
     * 查找宿舍信息，与之相关的学生
     * @param id
     */
    public List<Student> findDormitoryInfo(String id) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("dormitory",id);
        return studentMapper.selectList(wrapper);
    }

    /**
     * 获取宿舍数
     * @return
     */
    public Integer getDormitorysCount() {
        return dormitoryMapper.selectCount(null);
    }
}
