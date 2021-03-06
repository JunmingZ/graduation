package com.jim.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.enums.ResponseCode;
import com.jim.base.result.Results;
import com.jim.dto.DeclareDTO;
import com.jim.dto.RepairStatisticsDTO;
import com.jim.dto.RepairsDTO;
import com.jim.mapper.*;
import com.jim.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    private StudentMapper studentMapper;

    @Resource
    private RepairmanMapper repairmanMapper;

    @Resource
    private RepairsMapper repairsMapper;


    @Resource
    private EvaluateMapper evaluateMapper;


    public List<RepairType> getTypes() {
        return repairTypeMapper.selectList(null);
    }


    /**
     * 报修统计页面
     * @return
     */
    public Map<String,RepairStatisticsDTO> getRepairStatisticsDTO() {


        Map map = new HashMap();
        // 1. 查出所有维修类型
        List<RepairType> repairTypes = repairTypeMapper.selectList(null);

        RepairStatisticsDTO repairStatisticsDTO = new RepairStatisticsDTO();

        // 2. 遍历每个维修类型,按类型分配
        for (RepairType repairType : repairTypes) {
            // 2.1 待分配
            QueryWrapper<Repairs> init = new QueryWrapper<>();
            init.eq("state",1).eq("type_id",repairType.getId());
            repairStatisticsDTO.getInit().add(repairsMapper.selectCount(init));
            // 2.2 待处理
            QueryWrapper<Repairs> wait = new QueryWrapper<>();
            wait.eq("state",2).eq("type_id",repairType.getId());
            repairStatisticsDTO.getWait().add(repairsMapper.selectCount(wait));
             // 2.3 已处理
            QueryWrapper<Repairs> finish = new QueryWrapper<>();
            finish.eq("state",3).eq("type_id",repairType.getId());
            repairStatisticsDTO.getFinish().add(repairsMapper.selectCount(finish));
            // 2.4 总数
            QueryWrapper<Repairs> total = new QueryWrapper<>();
            total.eq("type_id",repairType.getId());
            repairStatisticsDTO.getTotal().add(repairsMapper.selectCount(total));
        }

        ArrayList list = new ArrayList();
        list.add(repairStatisticsDTO.getInit());
        list.add(repairStatisticsDTO.getWait());
        list.add(repairStatisticsDTO.getFinish());
        list.add(repairStatisticsDTO.getTotal());


        map.put("repairStatisticsDTO",list);
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
        wrapper.orderByAsc("state").orderByDesc("ctime");

        IPage iPage = null;
        if(!StringUtils.isEmpty(sno)){
            //模糊查询
            wrapper.like("sno",sno);
        }
        if(!StringUtils.isEmpty(state)){
            wrapper.eq("state",state);
        }
        iPage = repairsMapper.selectPage(page,wrapper);
        List<Repairs> repairs = iPage.getRecords();
        List<RepairsDTO> repairsDTOS = new ArrayList<>();
        for (Repairs repair : repairs) {
            RepairsDTO repairsDTO = new RepairsDTO();
            BeanUtils.copyProperties(repair,repairsDTO);
            // 1 获取维修人员名称并存入
            Repairman repairman = repairmanMapper.selectById(repair.getRepairmanId());
            if(repairman==null){
                repairsDTO.setRepairman("未分配维修员");
            }else {
                repairsDTO.setRepairman(repairman.getName());
            }
            // 2. 获取维修类型名称并存入
            RepairType repairType = repairTypeMapper.selectById(repair.getTypeId());
            if(repairType == null){
                repairsDTO.setType("未分配类型");
            }else {
                repairsDTO.setType(repairType.getType());
            }
            repairsDTOS.add(repairsDTO);
        }
        return Results.success((int) iPage.getTotal(),repairsDTOS);  //getRecords()是获取记录

    }


    /**
     * 分配任务
     * @param repairmanId  维修人id
     * @return
     */
    public Results taskAllocation(Integer repairmanId,String ids) {
        String[] split = ids.split(",");
        int update = 0;
        // 1. 根据 报修类型 id 获取
        for (String id:split) {
            QueryWrapper<Repairs> wrapper = new QueryWrapper<>();
            wrapper.eq("id",id);
            Repairs repairs = new Repairs();
            // 2. 更新状状态为待处理
            repairs.setState(2);
            repairs.setRepairmanId(repairmanId);
            repairs.setUtime(System.currentTimeMillis());
            update = repairsMapper.update(repairs, wrapper);
        }

        if(update>0){
            return Results.success();
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
        if(repairs==null){
            return Results.failure(ResponseCode.OBJECT_IS_NULL);
        }
        repairs.setUtime(System.currentTimeMillis());

        int i = repairsMapper.updateById(repairs);
        if(i>0){
            return Results.success();
        }
        return Results.failure();
    }

    /**
     * 通过id单个删除
     * @param id
     * @return
     */
    public Results deleteRepairsById(String id) {
        // 1. 判断评价表是否存在
        Repairs repairs = repairsMapper.selectById(id);
        if(repairs == null){
            return Results.failure(ResponseCode.OBJECT_IS_NULL);
        }

        if(repairs.getEvaluationId()!=0){
            // 2. 存在,删除该评价
            if(evaluateMapper.deleteById(repairs.getEvaluationId())<0){
                // 3. 删除评价失败，
                return Results.failure(ResponseCode.DELETE_EVALUATE_IS_FAIL);
            }
        }
        // 4. 删除报修任务
        if(repairsMapper.deleteById(id)>0){
            return Results.success();
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

        for (String s : split) {
            Results results = this.deleteRepairsById(s);
            if(results.getCode()!=200){
                return Results.failure();
            }
        }
        return Results.success();
    }

    /**
     * 添加报修任务
     * @param repairs
     * @return
     */
    public Results addRepairs(Repairs repairs) {
        // 1. 检查学生是否存在
        Student student = studentMapper.selectById(repairs.getSno());
        if(student==null){
            return Results.failure(ResponseCode.SNO_NOT_EXIST);
        }else {
            // 2. 学生不为空判断宿舍是否存在
            if(student.getDormitory().equals(repairs.getDormitory())){
                int insert =0;
                repairs.setCtime(System.currentTimeMillis());
                repairs.setUtime(System.currentTimeMillis());
                repairs.setEvaluationId(0L);
                //repairs.setRepairmanId(0);
                // 3. 判断是否分配维修人员
                if(repairs.getRepairmanId()==null||repairs.getRepairmanId()==0){
                    repairs.setState(1);  // 待分配
                }else {
                    repairs.setState(2); //待处理
                }

                if(repairs.getId() == null){
                    insert = repairsMapper.insert(repairs);
                }else {
                    // 异常插入
                    return  Results.failure(ResponseCode.INSERT_EXCEPTION.getCode(),ResponseCode.INSERT_EXCEPTION.getMessage());
                }
                if(insert>0){
                    return Results.success();
                }else {
                    return Results.failure();
                }
            }else {
                return Results.failure(ResponseCode.DORMITORY_NOT_EXIST);
            }
        }


    }

    /**
     * 通过学号寻找维修信息
     * @param sno
     */
    public  List<Repairs> selectRepairBySno(String sno) {
        QueryWrapper<Repairs> wrapper = new QueryWrapper();
        wrapper.eq("sno",sno);
        List<Repairs> repairs = repairsMapper.selectList(wrapper);
        return repairs;
    }

    /**
     * 根据学号获取报修总数
     * @param id
     * @return
     */
    public Integer selectRepairCountBySno(String id) {
        QueryWrapper<Repairs> wrapper = new QueryWrapper<>();
        wrapper.eq("sno",id);
        Integer count = repairsMapper.selectCount(wrapper);
        return count;
    }

    /**
     * 获取学生报修列表
     * @param pageTableRequest
     * @param sno
     * @param content
     * @return
     */
    public Results selectRepairsBySnoPage(PageTableRequest pageTableRequest, String sno,String content) {
        Page page = new Page(pageTableRequest.getPage(),pageTableRequest.getLimit());
        QueryWrapper<Repairs> wrapper = new QueryWrapper<>();
        // 创建时间降序，维修状态升序
        wrapper.orderByAsc("state").orderByDesc("ctime");
        wrapper.eq("sno",sno);
        if(!StringUtils.isEmpty(content)){
            wrapper.like("content",content);
        }
        IPage iPage = repairsMapper.selectPage(page,wrapper);
        return Results.success(iPage.getRecords());
    }

    /**
     * 通过学生学号和内容获取统计
     * @param sno
     * @param content
     * @return
     */
    public Results selectRepairsCountBySno(String sno, String content) {
        QueryWrapper<Repairs> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("state"); //升序
        wrapper.eq("sno",sno);
        if(!StringUtils.isEmpty(content)){
            wrapper.like("content",content);
        }
        Integer count = repairsMapper.selectCount(wrapper);
        return Results.success(count);
    }


    /**
     * 获取故障申报DeclareDTO相关数据
     * @param sno
     * @return
     */
    public DeclareDTO getDeclareDTOBySno(String sno) {
        DeclareDTO declareDTO = new DeclareDTO();
        // 1. 获取学号 宿舍号
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.select("sno","dormitory").eq("sno",sno);
        Student student = studentMapper.selectOne(wrapper);
        BeanUtils.copyProperties(student,declareDTO);
        // 2. 获取报修类型集
        declareDTO.setRepairTypes( repairTypeMapper.selectList(null));
        return declareDTO;
    }


    /**
     * 获取报修信息
     * @param id
     * @return
     */
    public RepairsDTO getRepairsDTO(String id) {
        // 1. 先获取维修任务进行封装
        Repairs repairs = repairsMapper.selectById(id);
        RepairsDTO repairsDTO = new RepairsDTO();
        BeanUtils.copyProperties(repairs,repairsDTO);

        // 2. 获取维修类型
        if(repairs.getTypeId()!=0){
            RepairType repairType = repairTypeMapper.selectById(repairs.getTypeId());
            repairsDTO.setType(repairType.getType());
        }else {
            repairsDTO.setType("未分配类型");
        }

        // 3. 不是空时获取维修人名
        if(repairs.getRepairmanId()!=0){
            Repairman repairman = repairmanMapper.selectById(repairs.getRepairmanId());
            repairsDTO.setRepairman(repairman.getName());
        }else {
            repairsDTO.setRepairman("未分配维修员");
        }

        // 4. 不是空时获取评价
        if(repairs.getEvaluationId()!=0){
            Evaluate evaluate = evaluateMapper.selectById(repairs.getEvaluationId());
            repairsDTO.setEvaluationId(evaluate.getId());
            repairsDTO.setEvaluation(evaluate.getContent());
            repairsDTO.setStar(evaluate.getStar());
        }else {
            repairsDTO.setEvaluation("尚未评价");
            repairsDTO.setStar(0);
            repairsDTO.setEvaluationId(0L);
        }

        return repairsDTO;
    }

    /**
     * 通过维修员id获取该维修人统计数
     * @param id
     * @return
     */
    public Integer selectRepairCountByRepairmanId(Integer id) {
        QueryWrapper<Repairs> wrapper = new QueryWrapper<>();
        wrapper.eq("repairman_id",id);
        return repairsMapper.selectCount(wrapper);
    }

    /**
     * 关于该维修员所涉及获取分页信息和计数
     * @param pageTableRequest
     * @param repairmanId
     * @param content
     */
    public Results selectRepairsCountByRepairmanId(PageTableRequest pageTableRequest, Integer repairmanId, String content) {
        Page page = new Page(pageTableRequest.getPage(),pageTableRequest.getLimit());
        QueryWrapper<Repairs> wrapper = new QueryWrapper<>();
        // 根据维修状态升序，创建时间降序
        wrapper.orderByAsc("state").orderByDesc("ctime");
        wrapper.eq("repairman_id",repairmanId).notIn("state",1);
        if(!StringUtils.isEmpty(content)){
            //wrapper.like("dormitory",content);
            wrapper.and(q-> q.like("dormitory",content).or().like("content", content));
        }
        IPage iPage = repairsMapper.selectPage(page,wrapper);
        return Results.success((int) iPage.getTotal(),iPage.getRecords());
    }


    /**
     * 统计报修数
     * @return
     */
    public Integer getRepairsCount() {
        return repairsMapper.selectCount(null);
    }
}
