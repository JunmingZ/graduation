package com.jim.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.enums.ResponseCode;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.Results;
import com.jim.dto.WelcomeDTO;
import com.jim.model.Admin;
import com.jim.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @Resource
    private StudentService studentService;

    @Resource
    private DormitoryService dormitoryService;

    @Resource
    private RepairmanService repairmanService;

    @Resource
    private EvaluateService evaluateService;

    @Resource
    private RepairsService repairsService;
    /**
     * 进入欢迎页
     * @param modelAndView
     * @return
     */
    @GetMapping("/welcome/{id}")
    public ModelAndView toWelcome(ModelAndView modelAndView,@PathVariable String id){
        System.out.println("AdminController toWelcome():"+id);
        Admin admin = adminService.findAdminById(id);
        // 管理员的名字
        modelAndView.addObject("admin_name",admin.getName());
        WelcomeDTO welcome = new WelcomeDTO();
        // 统计审核学生数
        welcome.setStudentCount(studentService.getStudentsCount());
        // 统计宿舍数
        welcome.setDormitoryCount(dormitoryService.getDormitorysCount());
        // 统计报修数
        welcome.setRepairsCount(repairsService.getRepairsCount());
        // 统计在职维修人员数
        welcome.setRepairmanCount(repairmanService.getRepairmanCount());
        // 统计评价数
        welcome.setEvaluateCount(evaluateService.getEvaluateCount());
        // 统计在职人员数
        welcome.setAdminCount(adminService.getAdminCount());
        modelAndView.addObject("welcomeDTO",welcome);
        modelAndView.setViewName("admin/welcome");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView index(ModelAndView modelAndView,@PathVariable("id")String  id){
        System.out.println("AdminController  index() :"+id);
        // 获取当前登录对象,判断是否是登录对象
        Subject subject = SecurityUtils.getSubject();
        Admin admin = (Admin) subject.getPrincipal();
        if(!admin.getId().toString().equals(id)){
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }
        // 存入请求域
        modelAndView.addObject("admin",admin);

        modelAndView.setViewName("index");
        return modelAndView;
    }




    @GetMapping("/list")
    public Results getAdminList(PageTableRequest pageTableRequest, @RequestParam(required = false) String id){
        return adminService.getAllAdminListByPage(new Page(pageTableRequest.getPage(),pageTableRequest.getLimit()),id);
    }


    /**
     * 通过id单个删除
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Results deleteAdminById(String id){
        return adminService.deleteAdminById(id);
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public Results deleteAdminByIds(String ids){
        return adminService.deleteAdminByIds(ids);
    }


    /**
     * 添加管理员
     * @param admin
     * @return
     */
    @PostMapping("/add")
    public Results addStudent(Admin admin){
        if(admin == null){
            return Results.failure(ResponseCode.OBJECT_IS_NULL.getCode(),ResponseCode.OBJECT_IS_NULL.getMessage());
        }
        return adminService.addAdmin(admin);
    }

    /**
     * 进入编辑页面
     * @param modelAndView
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public ModelAndView toEditAdmin(ModelAndView modelAndView, @PathVariable String id){
        modelAndView.setViewName("admin/admin-edit");
        Admin admin = adminService.findAdminById(id);
        modelAndView.addObject("admin",admin);
        return modelAndView;
    }

    /**
     * 编辑宿舍
     * @param admin 宿舍
     * @return
     */
    @PostMapping("/edit")
    public Results editDormitory(Admin admin){
        if(admin == null){
            return Results.failure(ResponseCode.OBJECT_IS_NULL.getCode(),ResponseCode.OBJECT_IS_NULL.getMessage());
        }
        return adminService.editAdmin(admin);
    }

    /**
     * 查看宿舍信息
     * @param id 宿舍号
     * @return
     */
    //@GetMapping("/info")
    //public ModelAndView dormitoryInfo(ModelAndView modelAndView,String id){
    //    List<Student> dormitoryInfo = dormitoryService.findDormitoryInfo(id);
    //    modelAndView.addObject("dormitoryInfo",dormitoryInfo);
    //    modelAndView.setViewName("dormitory-manage/dormitory-info");
    //    return modelAndView;
    //}

}
