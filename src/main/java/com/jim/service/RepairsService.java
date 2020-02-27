package com.jim.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jim.dto.RepairStatisticsDTO;
import com.jim.mapper.RepairTypeMapper;
import com.jim.mapper.RepairsMapper;
import com.jim.model.RepairType;
import com.jim.model.Repairs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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


    public Map getRepairStatisticsDTO() {

        List list = new ArrayList();
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
        map.put("RepairStatistics",list);
        map.put("types",repairTypes);

        return map;
    }
}
