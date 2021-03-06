package com.jim.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.enums.ResponseCode;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.Results;
import com.jim.dto.RepairsDTO;
import com.jim.model.Repairman;
import com.jim.model.Repairs;
import com.jim.model.Student;
import com.jim.service.RepairmanService;
import com.jim.service.RepairsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RestController
@RequestMapping("/repairman")
public class RepairmanController {

    @Resource
    private RepairmanService repairmanService;

    @Resource
    private RepairsService repairsService;

    @GetMapping("/ack/{id}")
    public Results ackRepairInfoById(@PathVariable String id){
        if(StringUtils.isEmpty(id)){
            return Results.failure(ResponseCode.ID_IS_NULL);
        }
        Repairs repairs = new Repairs();
        repairs.setId(Long.valueOf(id));
        repairs.setState(3); // 设置为完成
        return repairsService.updateRepairsByEntity(repairs);
    }

    /**
     * 获取已完成的报修信息
     * @param id
     * @return
     */
    @GetMapping("/repairInfo/{id}")
    public ModelAndView toRepairmanRepairInfoById(ModelAndView modelAndView,@PathVariable String id){
        RepairsDTO repairsDTO = repairsService.getRepairsDTO(id);
        modelAndView.addObject("repairsDTO",repairsDTO);
        modelAndView.setViewName("repairman/repairman-repairs");
        return modelAndView;
    }


    /**
     * 关于该维修员所涉及获取分页信息
     * @param pageTableRequest
     * @param repairmanId
     * @param content
     */
    @PostMapping("/task")
    public Results getRepairsByRepairmanId(PageTableRequest pageTableRequest
                                            , Integer repairmanId
                                            , @RequestParam(required = false) String content){
        return  repairsService.selectRepairsCountByRepairmanId(pageTableRequest, repairmanId,content);
    }



    /**
     * 进入维修员个人界面
     * @param modelAndView
     * @return
     */
    @GetMapping("/irepairman/{id}")
    public ModelAndView toRepairman(ModelAndView modelAndView,@PathVariable Integer id){
        // 获取当前登录对象
        Subject subject = SecurityUtils.getSubject();
        Repairman login = (Repairman) subject.getPrincipal();
        if(!login.getId().equals(id)){
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        // 1. 获取登录人
        Repairman repairman = repairmanService.getRepairmanById(id);
        modelAndView.addObject("repairman",repairman);

        // 2. 获取该登录人的总数用于前端分页插件
        Integer count = repairsService.selectRepairCountByRepairmanId(id);
        modelAndView.addObject("count",count);
        modelAndView.setViewName("repairman/irepairman");
        return modelAndView;
    }

    /**
     * 获取在职维修员集
     * @return
     */
    //@GetMapping("/repairmans")
    //public List repairmanList(){
    //    return repairmanService.getAllRepairman();
    //}

    @GetMapping("/list")
    public Results repairmanList(PageTableRequest pageTableRequest, @RequestParam(required = false) String id){
        return repairmanService.getAllRepairmanByPage(new Page(pageTableRequest.getPage(),pageTableRequest.getLimit()),id);
    }

    /**
     * 更新维修人在职状态
     * @param flag
     * @return
     */
    @PostMapping("/flag")
    public Results repairmanUpdateFlag(String id,Integer flag){
        if(StringUtils.isEmpty(flag)||StringUtils.isEmpty(id)){
            return Results.failure(ResponseCode.REPAIRMAN_FLAG_NULL.getCode(),ResponseCode.REPAIRMAN_FLAG_NULL.getMessage());
        }
        return repairmanService.updateFlag(id,flag);
    }


    /**
     * 添加维修人员
     * @param repairman
     * @return
     */
    @PostMapping("/add")
    public Results addRepairs(Repairman repairman){

        if(repairman.equals(null)){
            return Results.failure(ResponseCode.OBJECT_IS_NULL.getCode(),ResponseCode.OBJECT_IS_NULL.getMessage());
        }

        return repairmanService.addRepairman(repairman);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView toEditRepairman(ModelAndView modelAndView,@PathVariable Integer id){
        Repairman repairman = repairmanService.getRepairmanById(id);
        modelAndView.addObject("repairman",repairman);
        modelAndView.setViewName("repairman/repairman-edit");
         return modelAndView;
    }


    @PostMapping("/edit")
    public Results editRepairman(Repairman repairman){
        if(repairman.equals(null)){
            return Results.failure(ResponseCode.OBJECT_IS_NULL.getCode(),ResponseCode.OBJECT_IS_NULL.getMessage());
        }
        return  repairmanService.updateRepairmanByModel(repairman);
    }

}
