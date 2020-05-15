package com.jim.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.enums.ResponseCode;
import com.jim.base.result.Results;
import com.jim.config.UserToken;
import com.jim.dto.LoginDTO;
import com.jim.mapper.RepairmanMapper;
import com.jim.model.Repairman;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class RepairmanService {

    @Resource
    private RepairmanMapper repairmanMapper;

    /**
     * 获取维修人列表
     * @param page
     * @param id
     * @return
     */
    public Results getAllRepairmanByPage(Page page, String id) {
        QueryWrapper<Repairman> wrapper = new QueryWrapper<>();
        IPage iPage = null;
        if(!StringUtils.isEmpty(id)){
            //模糊查询
            wrapper.like("id",id).or().like("name",id);
        }
        iPage = repairmanMapper.selectPage(page,wrapper);
        return Results.success((int) iPage.getTotal(),iPage.getRecords());
    }

    /**
     * 单纯获取维修人列表
     * @return
     */
    public List<Repairman> getAllRepairman() {
        QueryWrapper<Repairman> wrapper = new QueryWrapper();
        wrapper.eq("flag",1).select("id","name");
        return repairmanMapper.selectList(wrapper);
    }

    /**
     * 更新维修人在职状态
     * @param flag
     * @return
     */
    public Results updateFlag(String id,Integer flag) {
        QueryWrapper<Repairman> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        Repairman repairman = new Repairman();
        if (flag == 1) {
            repairman.setFlag(flag + 1);
        } else {
            repairman.setFlag(flag - 1);
        }

        int update = repairmanMapper.update(repairman, wrapper);

        if(update>0){
            return Results.success();
        }else {
            return Results.failure(ResponseCode.REPAIRMAN_UPDATE_FLAG.getCode(),ResponseCode.REPAIRMAN_UPDATE_FLAG.getMessage());
        }

    }

    /**
     * 添加维修人员
     * @param repairman
     * @return
     */
    public Results addRepairman(Repairman repairman) {
        if(repairman.getFlag()==null){
            repairman.setFlag(2);
        }
        //QueryWrapper<Repairman> wrapper = new QueryWrapper<>();
        repairman.setCtime(System.currentTimeMillis());
        repairman.setUtime(System.currentTimeMillis());
        int insert = repairmanMapper.insert(repairman);
        if(insert>0){
            return Results.success();
        }else {
            return Results.failure(ResponseCode.INSERT_EXCEPTION.getCode(),ResponseCode.INSERT_EXCEPTION.getMessage());
        }

    }

    /**
     * 通过id获取维修人员
     * @param id
     */
    public Repairman getRepairmanById(Integer  id) {
        Repairman repairman = repairmanMapper.selectById(id);
        return repairman;
    }

    /**
     * 更新维修人员
     * @param repairman
     */
    public Results updateRepairmanByModel(Repairman repairman) {
        if(repairman.getFlag()==null){
            repairman.setFlag(2);
        }
        repairman.setUtime(System.currentTimeMillis());
        int i = repairmanMapper.updateById(repairman);
        if(i>0){
            return Results.success();
        }else {
            return Results.failure(ResponseCode.UPDATE_FAIL.getCode(),ResponseCode.UPDATE_FAIL.getMessage());
        }
    }

    /**
     * 检查维修员账号密码
     * @param subject
     * @return
     */
    public Results checkRepairman(Subject subject) {

        Repairman repairman = (Repairman)subject.getPrincipal();
        return Results.success("repairman/irepairman/"+repairman.getId());


        //// 1. 检查账号是否存在
        //Repairman repairman = repairmanMapper.selectById(login.getId());
        //if(repairman==null){
        //    return Results.failure(ResponseCode.REPAIRMAN_NOT_EXIST);
        //}
        //// 2 检查是否在职
        //if(repairman.getFlag()!=1){
        //    return Results.failure(ResponseCode.REPAIRMAN_QUIT);
        //}
        //
        //if(login.getPassword().equals(repairman.getPassword())){
        //    return Results.success("repairman/irepairman/"+repairman.getId());
        //}
        //return Results.failure(ResponseCode.LOGIN_ACCPASS_NOT_FOUND);
    }

    /**
     * 统计在职人员个数
     * @return
     */
    public Integer getRepairmanCount() {
        QueryWrapper<Repairman> wrapper = new QueryWrapper<>();
        wrapper.eq("flag",1);
        return repairmanMapper.selectCount(wrapper);
    }
}
