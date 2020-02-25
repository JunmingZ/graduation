package com.jim.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jim.model.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
