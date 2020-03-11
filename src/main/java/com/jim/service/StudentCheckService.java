package com.jim.service;

import com.jim.mapper.StudentCheckMapper;
import com.jim.model.StudentCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional   //加入事务
public class StudentCheckService {
    @Autowired
    private StudentCheckMapper studentCheckMapper;

    public StudentCheck studentCheckById(String id){
        return studentCheckMapper.selectById(id);
    }
}
