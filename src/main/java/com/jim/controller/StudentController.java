package com.jim.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.result.PageTableRequest;
import com.jim.base.result.Results;
import com.jim.dto.StudentDto;
import com.jim.model.Student;
import com.jim.service.DormitoryService;
import com.jim.service.StudentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;
    @Resource
    private DormitoryService dormitoryService;
    /**
     * 查出相应数据返回前端
     * @param pageTableRequest 分页初始处理
     * @return
     */
    @GetMapping("/list")
    public Results studentList(PageTableRequest pageTableRequest,@RequestParam(required = false) String username){
        return studentService.getAllStudentsByPage(new Page(pageTableRequest.getPage(),pageTableRequest.getLimit()),username);
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


    @GetMapping("/edit")
    public ModelAndView editStudent(ModelAndView modelAndView,String sno){
        modelAndView.setViewName("student-manage/student-edit");
        StudentDto studentBySno = studentService.findStudentBySno(sno);
        modelAndView.addObject("studentDTO",studentBySno);
        return modelAndView;
    }

}
