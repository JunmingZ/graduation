package com.jim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jim.model.Dormitory;
import com.jim.model.Repairman;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RepairmanMapper extends BaseMapper<Repairman> {
    void getRepairmanNameById(Long id);
}
