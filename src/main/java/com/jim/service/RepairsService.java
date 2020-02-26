package com.jim.service;

import com.jim.mapper.RepairTypeMapper;
import com.jim.model.RepairType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RepairsService {
    @Resource
    private RepairTypeMapper repairTypeMapper;

    public List<RepairType> getTypes() {
        return repairTypeMapper.selectList(null);
    }
}
