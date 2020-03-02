package com.jim.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.ResponseCode;
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
import java.util.*;

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


    /**
     * 统计页面
     * @return
     */
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
            // 4.2 已处理
            QueryWrapper<Repairs> wrapper3 = new QueryWrapper<>();
            wrapper3.eq("state",2).eq("type_id",id);;
            repairStatisticsDTO.setFinish(repairsMapper.selectCount(wrapper3));
            // 4.3 总记录数
            QueryWrapper<Repairs> wrapper4 = new QueryWrapper<>();
            wrapper4.eq("type_id",id);
            repairStatisticsDTO.setTotal(repairsMapper.selectCount(wrapper4));


            list.add(repairStatisticsDTO);

        }


        map.put("RepairStatistics", list);
        map.put("types",repairTypes);

        return map;
    }

    /**
     * 获取所有
     * @param page
     * @param sno
     * @return
     */
    public Results getAllRepairsByPage(Page page, String sno,Integer state) {
        QueryWrapper<Repairs> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        IPage iPage = null;
        if(!StringUtils.isEmpty(sno)){
            //模糊查询
            wrapper.like("sno",sno);
        }
        if(!StringUtils.isEmpty(state)){
            wrapper.eq("state",state);
        }
        iPage = repairsMapper.selectPage(page,wrapper);
        return Results.success((int) iPage.getTotal(),iPage.getRecords());  //getRecords()是获取记录

    }

    /**
     * 分配任务
     * @param type  报修类型
     * @param repairman  维修人id
     * @return
     */
    public Results taskAllocation(Integer type, Integer repairman) {
        QueryWrapper<Repairs> wrapper = new QueryWrapper<>();
        wrapper.eq("type_id",type);
        Repairs repairs = new Repairs();
        repairs.setRepairmanId(repairman);
        int update = repairsMapper.update(repairs, wrapper);
        if(update>0){
            return Results.ok();
        }
        return Results.failure();
    }

    /**
     * 通过id找报修条目
     * @param id
     * @return
     */
    public Repairs findRepairById(String id) {
        return repairsMapper.selectById(id);
    }

    /**
     * 更新报修条目
     * @param repairs
     * @return
     */
    public Results updateRepairsByEntity(Repairs repairs) {
        repairs.setUtime(System.currentTimeMillis());
        int i = repairsMapper.updateById(repairs);
        if(i>0){
            return Results.ok();
        }
        return Results.failure();
    }

    /**
     * 通过id单个删除
     * @param id
     * @return
     */
    public Results deleteRepairsById(String id) {
        if(repairsMapper.deleteById(id)>0){
            return Results.ok();
        }
        return Results.failure();
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public Results deleteRepairsByIds(String ids) {
        if(StringUtils.isEmpty(ids)){
            return Results.failure();
        }
        String[] split = ids.split(",");

        if(repairsMapper.deleteBatchIds(Arrays.asList(split))==0){
            return Results.failure();
        }
        return Results.ok();
    }

    /**
     * 添加报修任务
     * @param repairs
     * @return
     */
    public Results addRepairs(Repairs repairs) {
        int insert =0;
        repairs.setCtime(System.currentTimeMillis());
        repairs.setUtime(System.currentTimeMillis());
        if(repairs.getId() == null){
            insert = repairsMapper.insert(repairs);
        }else {
            // 异常插入
            return  Results.failure(ResponseCode.INSERT_EXCEPTION.getCode(),ResponseCode.INSERT_EXCEPTION.getMessage());
        }
        if(insert>0){
            return Results.ok();
        }else {
            return Results.failure();
        }
    }
}
