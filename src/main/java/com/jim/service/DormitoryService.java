package com.jim.service;

import com.jim.mapper.DormitoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DormitoryService {
    @Resource
    private DormitoryMapper dormitoryMapper;


}
