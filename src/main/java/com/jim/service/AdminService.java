package com.jim.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.enums.ResponseCode;
import com.jim.base.result.Results;
import com.jim.dto.LoginDTO;
import com.jim.mapper.AdminMapper;
import com.jim.model.Admin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 宿舍业务层
 */
@Service
@Transactional   //加入事务
public class AdminService {

    @Resource
    private AdminMapper adminMapper;

    /**
     * 获取所有管理员
     * @param page
     * @param id
     * @return
     */
    public  Results getAllAdminListByPage(Page page, String id) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        IPage iPage = null;
        if(!StringUtils.isEmpty(id)){
            //模糊查询
            wrapper.like("id",id).or().like("name",id);
        }

        iPage = adminMapper.selectPage(page,wrapper);
        return Results.success((int) iPage.getTotal(),iPage.getRecords());
    }

    /**
     * 通过id单个删除
     * @param id
     * @return
     */
    public Results deleteAdminById(String id) {
        int i = adminMapper.deleteById(id);
        if(i>0){
            return Results.ok();
        }
        return Results.failure(ResponseCode.DELETE_FAIL.getCode(),ResponseCode.DELETE_FAIL.getMessage());
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public Results deleteAdminByIds(String ids) {
        if(StringUtils.isEmpty(ids)){
            return Results.failure(ResponseCode.DELETE_ID_IS_NULL.getCode(),ResponseCode.DELETE_ID_IS_NULL.getMessage());
        }
        String[] split = ids.split(",");

        if(adminMapper.deleteBatchIds(Arrays.asList(split))==0){
            return Results.failure(ResponseCode.DELETE_FAIL.getCode(),ResponseCode.DELETE_FAIL.getMessage());
        }
        return Results.ok();
    }

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    public Results addAdmin(Admin admin) {
        admin.setCtime(System.currentTimeMillis());
        admin.setUtime(System.currentTimeMillis());
        int insert = adminMapper.insert(admin);
        if(insert>0){
            return Results.ok();
        }
        return Results.failure(ResponseCode.INSERT_EXCEPTION.getCode(),ResponseCode.INSERT_EXCEPTION.getMessage());
    }

    /**
     * 根据id查找管理员
     * @param id
     * @return
     */
    public Admin findAdminById(String id) {
        return adminMapper.selectById(id);
    }

    /**
     * 编辑管理员
     * @param admin
     * @return
     */
    public Results editAdmin(Admin admin) {
        admin.setUtime(System.currentTimeMillis());
        int i = adminMapper.updateById(admin);
        if(i>0){
            return Results.ok();
        }
        return Results.failure(ResponseCode.UPDATE_FAIL.getCode(),ResponseCode.UPDATE_FAIL.getMessage());
    }

    /**
     * 检查
     * @param login
     */
    public Results  checkAdmin(LoginDTO<Admin> login) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("id",login.getId()).eq("password",login.getPassword());
        Integer integer = adminMapper.selectCount(wrapper);
        if(integer>0){
            return Results.ok("/");
        }
        return Results.failure(ResponseCode.LOGIN_ACCPASS_NOT_FOUND.getCode(),ResponseCode.LOGIN_ACCPASS_NOT_FOUND.getMessage());
    }
}
