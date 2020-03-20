package com.jim.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.Results;
import com.jim.model.Repairs;
import com.jim.model.Student;
import com.jim.service.RepairsService;
import com.jim.service.StudentService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;


    @Resource
    private RepairsService repairsService;

    @PostMapping("/repairCount")
    public  Results getRepairsCountBySno(String sno, @RequestParam(required = false) String content){
        return repairsService.selectRepairsCountBySno(sno,content);
    }


    /**
     * 获取学生报修列表
     * @param pageTableRequest
     * @param sno
     * @param content
     * @return
     */
    @PostMapping("/repair")
    public Results getRepairsBySnoPage(PageTableRequest pageTableRequest
                                        , String sno
                                        , @RequestParam(required = false) String content){
        return  repairsService.selectRepairsBySnoPage(pageTableRequest, sno,content);
    }

    /**
     * 更新审核状态
     * @param id
     * @return
     */
    @GetMapping("/setFlag")
    public Results changeFlagById(String id){
        if(StringUtils.isEmpty(id)){
            return Results.failure();
        }
        return studentService.setFlagById(id);
    }

    /**
     * 批量修改审核状态
     * @param ids
     * @return
     */
    @PostMapping("/setFlag")
    public Results changeFlagByIds(String ids){
        if(StringUtils.isEmpty(ids)){
            return Results.failure();
        }
        return studentService.setFlagByIds(ids);
    }

    /**
     * 访问学生页面
     * @param modelAndView
     * @return
     */
    @GetMapping("/istudent/{id}")
    public ModelAndView toIstudent(ModelAndView modelAndView
                                    , @PathVariable("id")String  id
                                    , HttpServletRequest request){
        //获取学生信息
        Student studentBySno = studentService.findStudentBySno(id);
        // 1. 存入域
        //request.getSession().setAttribute("object", studentBySno);
        modelAndView.addObject("student",studentBySno);
        //获取该学生报修总数
        Integer count = repairsService.selectRepairCountBySno(id);
        modelAndView.addObject("count",count);

        modelAndView.setViewName("student-manage/istudent");
        return modelAndView;
    }
    /**
     * 查出相应数据返回前端
     * @param pageTableRequest 分页初始处理
     * @return
     */
    @GetMapping("/list")
    public Results studentList(PageTableRequest pageTableRequest
                        ,@RequestParam(required = false) String username
                        ,@RequestParam(required = false) Integer flag){
        return studentService.getAllStudentsByPage(new Page(pageTableRequest.getPage(),pageTableRequest.getLimit()),username,flag);
    }

    /**
     * 通过id单个删除
     * @param sno
     * @return
     */
    @GetMapping("/delete")
    public Results deleteStudentById(String sno){
        return studentService.deleteStudentById(sno);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public Results deleteStudentByIds(String ids){
       return studentService.deleteStudentByIds(ids);
    }

    /**
     * 进入编辑页面
     * @param modelAndView
     * @param sno
     * @return
     */
    @GetMapping("/edit/{sno}")
    public ModelAndView toEditStudent(ModelAndView modelAndView,@PathVariable String sno){
        modelAndView.setViewName("student-manage/student-edit");
        Student studentBySno = studentService.findStudentBySno(sno);
        modelAndView.addObject("studentDTO",studentBySno);
        return modelAndView;
    }

    /**
     * 编辑学生
     * @param student
     * @return
     */
    @PostMapping("/edit")
    public Results editStudent(Student student){
        return studentService.editStudent(student);
    }

    /**
     * 添加学生
     * @param student
     * @return
     */
    @PostMapping("/add")
    public Results addStudent(Student student){
        return studentService.addStudent(student);
    }

}
