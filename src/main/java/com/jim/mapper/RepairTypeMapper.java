package com.jim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jim.model.Dormitory;
import com.jim.model.RepairType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RepairTypeMapper extends BaseMapper<RepairType> {
}
