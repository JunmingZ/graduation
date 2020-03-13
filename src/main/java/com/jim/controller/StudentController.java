package com.jim.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.Results;
import com.jim.model.Student;
import com.jim.service.StudentService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    @GetMapping("/setFlag")
    public Results changeFlagById(String id){
        if(StringUtils.isEmpty(id)){
            return Results.failure();
        }

        return studentService.setFlagById(id);
    }


    /**
     * 访问学生页面
     * @param modelAndView
     * @return
     */
    @GetMapping("/istudent")
    public ModelAndView toIstudent(ModelAndView modelAndView){
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
    @GetMapping("/edit")
    public ModelAndView toEditStudent(ModelAndView modelAndView,String sno){
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
