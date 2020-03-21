package com.jim.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.ResponseCode;
import com.jim.base.result.Results;
import com.jim.dto.DeclareDTO;
import com.jim.model.RepairType;
import com.jim.model.Repairman;
import com.jim.model.Repairs;
import com.jim.service.RepairTypeService;
import com.jim.service.RepairmanService;
import com.jim.service.RepairsService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/repairs")
public class RepairsController {

    @Resource
    private RepairsService repairsService;

    @Resource
    private RepairmanService repairmanService;
    
    @Resource
    private RepairTypeService repairTypeService;


    /**
     * 进入故障申报页面
     * @param modelAndView
     * @param sno
     * @return
     */
    @GetMapping("/declare/{sno}")
    public ModelAndView toDeclare(ModelAndView modelAndView
                                    ,@PathVariable("sno")String  sno){

        DeclareDTO declareDTO = repairsService.getDeclareDTOBySno(sno);
        modelAndView.addObject("declareDTO",declareDTO);
        modelAndView.setViewName("repair-manage/declare");
        return modelAndView;
    }



    /**
     * 进入统计页面
     * @param modelAndView
     * @return
     */
    @GetMapping("/statistics")
    public ModelAndView toRepairStatistics(ModelAndView modelAndView){
        Map map = repairsService.getRepairStatisticsDTO();
        modelAndView.addObject("types",map.get("types"));
        modelAndView.addObject("RepairStatistics",map.get("RepairStatistics"));
        modelAndView.setViewName("repair-manage/repair-statistics");
        return modelAndView;
    }


    /**
     * 搜索兼列表显示
     * @param pageTableRequest
     * @param sno
     * @param state
     * @return
     */
    @GetMapping("/list")
    public Results repairsList(PageTableRequest pageTableRequest
                                , @RequestParam(required = false) String sno
                                , @RequestParam(required = false) Integer state){
        //  getPage ：当前页   getLimit ：显示条数数目
        return repairsService.getAllRepairsByPage(new Page(pageTableRequest.getPage(),pageTableRequest.getLimit()),sno,state);
    }

    /**
     * 跳转到任务分配页面
     * @param modelAndView
     * @return
     */
    @GetMapping("/allocation")
    public ModelAndView toAllocationPage(ModelAndView modelAndView){
        //维修员列表
        List<Repairman> allRepairman = repairmanService.getAllRepairman();
        
        //报修类型
        List allRepairType = repairTypeService.getAllRepairType();


        modelAndView.addObject("allRepairman",allRepairman);
        modelAndView.addObject("allRepairType",allRepairType);

        modelAndView.setViewName("repair-manage/task-allocation");
        return modelAndView;
    }

    /**
     * 分配任务
     * @param type
     * @param type
     * @param repairman
     * @return
     */
    @PostMapping("/allocation")
    public Results taskAllocation(Integer type,Integer repairman){
        return repairsService.taskAllocation(type,repairman) ;
    }

    /**
     * 进入编辑页面 或者 添加页面
     * @param modelAndView
     * @param id
     * @return
     */
    @GetMapping({"/edit","/add"})
    public ModelAndView toEditRepairType(ModelAndView modelAndView, @RequestParam(required = false) String id){
        if(StringUtils.isEmpty(id)){
            modelAndView.setViewName("repair-manage/repair-task-add");
        }else {
            modelAndView.setViewName("repair-manage/repair-task-edit");
            Repairs repairs = repairsService.findRepairById(id);
            // 已处理的
            modelAndView.addObject("repairs",repairs);
        }

        List<RepairType> allRepairType = repairTypeService.getAllRepairType();
        List<Repairman> allRepairman = repairmanService.getAllRepairman();

        modelAndView.addObject("allRepairType",allRepairType);
        modelAndView.addObject("allRepairman",allRepairman);
        return modelAndView;
    }


    /**
     * 更新任务
     * @param repairs
     * @return
     */
    @PostMapping("/edit")
    public Results updateRepair(Repairs repairs){
        return repairsService.updateRepairsByEntity(repairs);
    }

    /**
     * 通过id单个删除
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Results deleteRepairsById(String id){
        return repairsService.deleteRepairsById(id);
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public Results deleteRepairsByIds(String ids){
        return repairsService.deleteRepairsByIds(ids);
    }


    /**
     * 添加报修任务
     * @param repairs
     * @return
     */
    @PostMapping("/add")
    public Results addRepairs(Repairs repairs){
        if(repairs.equals(null)){
            return Results.failure(ResponseCode.OBJECT_IS_NULL.getCode(),ResponseCode.OBJECT_IS_NULL.getMessage());
        }
        return repairsService.addRepairs(repairs);
    }

}
