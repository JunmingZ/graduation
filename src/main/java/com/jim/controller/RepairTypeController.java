package com.jim.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.Results;
import com.jim.model.Dormitory;
import com.jim.model.RepairType;
import com.jim.service.RepairTypeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/repairType")
public class RepairTypeController {

    @Resource
    private RepairTypeService repairTypeService;

    @GetMapping("/types")
    public List getRepairTypeOnlyNames(){
        return repairTypeService. getAllRepairType();
    }





    /**
     * 查出相应数据返回前端
     * @param pageTableRequest 分页初始处理
     * @return
     */
    @GetMapping("/list")
    public Results RepairTypeList(PageTableRequest pageTableRequest, @RequestParam(required = false) String type){
        return repairTypeService.getAllRepairTypeByPage(new Page(pageTableRequest.getPage(),pageTableRequest.getLimit()),type);
    }


    /**
     * 添加报修类型
     * @param repairType
     * @return
     */
    @PostMapping("/add")
    public Results addRepairType(RepairType repairType){
        return repairTypeService.addRepairType(repairType);
    }


    /**
     * 进入编辑页面
     * @param modelAndView
     * @param id
     * @return
     */
    @GetMapping("/edit")
    public ModelAndView toEditRepairType(ModelAndView modelAndView, String id){
        modelAndView.setViewName("repair-manage/repair-type-edit");
        RepairType repairType = repairTypeService.findRepairTypeById(id);
        modelAndView.addObject("repairType",repairType);
        return modelAndView;
    }

    /**
     * 编辑报修类型
     * @param repairType 宿舍
     * @return
     */
    @PostMapping("/edit")
    public Results editRepairType(RepairType repairType){
        return repairTypeService.editRepairType(repairType);
    }


    /**
     * 通过id单个删除
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Results deleteRepairTypeById(String id){
        return repairTypeService.deleteRepairTypeById(id);
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public Results deleteRepairTypeByIds(String ids){
        return repairTypeService.deleteRepairTypeByIds(ids);
    }
}
