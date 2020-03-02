package com.jim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jim.model.Repairs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RepairsMapper extends BaseMapper<Repairs> {

    //@Insert("INSERT into repairs (id,sno,dormitory,type_id,content,repairman_id,state,ctime,utime) VALUES (#{id},#{sno},#{typeId},#{content},#{dormitory},#{repairmanId},#{state},#{ctime},#{utime});")
    //int selfInsert(Repairs repairs);
}
