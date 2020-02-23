package com.jim.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.Results;
import com.jim.dto.StudentDto;
import com.jim.mapper.StudentMapper;
import com.jim.model.Dormitory;
import com.jim.model.Student;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional   //加入事务
public class StudentService {
     @Resource
     private StudentMapper studentMapper;
    /**
     * 分页获取学生列表
     * @param page
     * @param username
     * @return 返回总记录数和集合
     */
    public Results getAllStudentsByPage(Page page, String username){
        Student student = new Student();
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        IPage iPage = null;

        if(!StringUtils.isEmpty(username)){
            //模糊查询
            wrapper.like("name",username);
        }
        iPage = student.selectPage(page, wrapper);

        return Results.success((int) iPage.getTotal(),iPage.getRecords());
    }

    public Results deleteStudentById(String sno) {
        if(studentMapper.deleteById(sno)>0){
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
    public Results deleteStudentByIds(String ids) {
        if(StringUtils.isEmpty(ids)){
            return Results.failure();
        }
        String[] split = ids.split(",");
        int flag=0;
        for (String s : split) {
            flag = studentMapper.deleteById(s);
        }
        if(flag==0){
            return Results.failure();
        }
        return Results.ok();
    }

    public StudentDto findStudentBySno(String sno) {
        Student student = studentMapper.selectById(sno);
        StudentDto studentDto = new StudentDto();
        BeanUtils.copyProperties(student,studentDto);
        Dormitory dormitory = new Dormitory();
        Integer bedNums = dormitory.selectById(student.getDormitory()).getBedNum();//获取床数
        // 加入集合给前端
        for(int i = 1; i<= bedNums; i++){
            studentDto.getBedNums().add(i);
        }
        return studentDto;
    }

    public Results editStudent(Student student) {
        student.setUtime(System.currentTimeMillis());
        int i = studentMapper.updateById(student);
        if(i>0){
            return Results.ok();
        }
        return Results.failure();

    }
}
