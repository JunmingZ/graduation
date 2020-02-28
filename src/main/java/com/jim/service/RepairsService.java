package com.jim.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.Results;
import com.jim.dto.RepairStatisticsDTO;
import com.jim.mapper.RepairTypeMapper;
import com.jim.mapper.RepairsMapper;
import com.jim.model.Dormitory;
import com.jim.model.RepairType;
import com.jim.model.Repairs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RepairsService {
    @Resource
    private RepairTypeMapper repairTypeMapper;

    @Resource
    private RepairsMapper repairsMapper;

    public List<RepairType> getTypes() {
        return repairTypeMapper.selectList(null);
    }


    public Map<String,List<RepairStatisticsDTO>> getRepairStatisticsDTO() {

        List<RepairStatisticsDTO> list = new ArrayList();
        Map map = new HashMap();
        // 1. 查出所有维修类型
        List<RepairType> repairTypes = repairTypeMapper.selectList(null);
        // 2. 存储每个类型的维修统计
        for (RepairType repairType : repairTypes) {
            RepairStatisticsDTO repairStatisticsDTO = new RepairStatisticsDTO();
            //3. 获取维修id
            Integer id = repairType.getId();
            repairStatisticsDTO.setType(id);
            // 4. 进行统计
            // 4.1 统计 未处理
            QueryWrapper<Repairs> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("state",1).eq("type_id",id);

            repairStatisticsDTO.setUntreated(repairsMapper.selectCount(wrapper1));

            QueryWrapper<Repairs> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("state",2).eq("type_id",id);;
            repairStatisticsDTO.setPending(repairsMapper.selectCount(wrapper2));

            QueryWrapper<Repairs> wrapper3 = new QueryWrapper<>();
            wrapper3.eq("state",3).eq("type_id",id);;
            repairStatisticsDTO.setFinish(repairsMapper.selectCount(wrapper3));

            QueryWrapper<Repairs> wrapper4 = new QueryWrapper<>();
            wrapper4.eq("type_id",id);
            repairStatisticsDTO.setTotal(repairsMapper.selectCount(wrapper4));


            list.add(repairStatisticsDTO);

        }


        map.put("RepairStatistics", list);
        map.put("types",repairTypes);

        return map;
    }

    public Results getAllRepairsByPage(Page page, String sno) {
        QueryWrapper<Repairs> wrapper = new QueryWrapper<>();
        IPage iPage = null;
        if(!StringUtils.isEmpty(sno)){
            //模糊查询
            wrapper.like("sno",sno);
        }
        iPage = repairsMapper.selectPage(page,wrapper);
        return Results.success((int) iPage.getTotal(),iPage.getRecords());  //getRecords()是获取记录

    }
}
