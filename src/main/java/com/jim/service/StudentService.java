package com.jim.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jim.base.enums.ResponseCode;
import com.jim.base.result.Results;
import com.jim.config.UserToken;
import com.jim.dto.LoginDTO;
import com.jim.mapper.DormitoryMapper;
import com.jim.mapper.EvaluateMapper;
import com.jim.mapper.RepairsMapper;
import com.jim.mapper.StudentMapper;
import com.jim.model.Evaluate;
import com.jim.model.Repairs;
import com.jim.model.Student;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class StudentService {
     @Resource
     private StudentMapper studentMapper;

     @Resource
     private DormitoryMapper dormitoryMapper;

     @Resource
     private RepairsMapper repairsMapper;

     @Resource
     private EvaluateMapper evaluateMapper;



    /**
     * 分页获取学生列表
     * @param page
     * @param username  搜索条件
     * @return 返回总记录数和集合
     */
    public Results getAllStudentsByPage(Page page, String username, Integer flag){
        Student student = new Student();
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        IPage iPage = null;

        // 增加查询学生姓名的条件
        if(!StringUtils.isEmpty(username)){
            // 模糊查询
            wrapper.like("name",username).or().like("sno",username);
        }
        // 增加审核查询的条件
        if(flag!=null){
            // 审核状态
            wrapper.eq("flag",flag);
        }
        // 根据状态降序
        wrapper.orderByDesc("flag");
        iPage = student.selectPage(page, wrapper);

        //getTotal 总记录数  getRecords 信息集
        return Results.success((int) iPage.getTotal(),iPage.getRecords());
    }

    /**
     * 通过id删除学生
     * @param sno
     * @return
     */
    public Results deleteStudentById(String sno) {
        // 找出要删除报修的报修id
        QueryWrapper<Repairs> wrapper = new QueryWrapper<>();
        wrapper.eq("sno",sno);
        List<Repairs> repairs = repairsMapper.selectList(wrapper);

        // 删除评价
        for(Repairs r :repairs ){
            QueryWrapper<Evaluate> evaWrapper = new QueryWrapper<>();
            evaWrapper.eq("repairs_id",r.getId());
            evaluateMapper.delete(evaWrapper);      //删除对应的评价
            repairsMapper.deleteById(r.getId());  // 删除对应的报修任务
        }
        // 删除学生
        if(studentMapper.deleteById(sno)>0){
            return Results.success();
        }else {
            return Results.failure();
        }

    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public Results deleteStudentByIds(String ids) {
        if(StringUtils.isEmpty(ids)){
            return Results.failure();
        }
        String[] split = ids.split(",");
        int flag=0;
        for (String s : split) {
            flag = studentMapper.deleteById(s);
        }
        if(flag==0){
            return Results.failure();
        }
        return Results.success();
    }

    /**
     * 通过学号查找学生
     * @param sno
     * @return
     */
    public Student findStudentBySno(String sno) {
        return studentMapper.selectById(sno);
    }

    /**
     * 编辑学生
     * @param student
     * @return
     */
    public Results editStudent(Student student) {
        // 检查宿舍号是否存在
        // 检查宿舍是否存在
        if(dormitoryMapper.selectById(student.getDormitory())==null){
            return Results.failure(ResponseCode.DORMITORY_NOT_EXIST);
        }
        student.setUtime(System.currentTimeMillis());
        int i = studentMapper.updateById(student);
        if(i>0){
            return Results.success();
        }
        return Results.failure();

    }


    /**
     * 插入学生
     * @param student
     * @return
     */
    public Results addStudent(Student student) {

        if(studentMapper.selectById(student.getSno())!=null){
            return Results.failure(ResponseCode.SNO_REPEAT.getCode(),ResponseCode.SNO_REPEAT.getMessage());
        }
        // 检查宿舍是否存在
        if(dormitoryMapper.selectById(student.getDormitory())==null){
            return Results.failure(ResponseCode.DORMITORY_NOT_EXIST);
        }

        student.setCtime(System.currentTimeMillis());
        student.setUtime(System.currentTimeMillis());
        int insert = studentMapper.insert(student);
        if(insert>0){
            return  Results.success();
        }
        return Results.failure();
    }

    /**
     * 验证学生登录
     * @param subject
     * @return
     */
    public Results checkStudent(Subject subject ) {

        Student student = (Student)subject.getPrincipal();
        return Results.success("student/istudent/"+student.getSno());

        //QueryWrapper<Student> wrapper = new QueryWrapper<>();
        //wrapper.eq("sno",login.getId()).eq("password",login.getPassword()).eq("flag",1);
        //Student student = studentMapper.selectOne(wrapper);
        //if(student!=null){
        //    return Results.ok("student/istudent/"+student.getSno());
        //}
        //return Results.failure(ResponseCode.LOGIN_ACCPASS_NOT_FOUND.getCode(),ResponseCode.LOGIN_ACCPASS_NOT_FOUND.getMessage());
    }

    /**
     * 设置状态
     * @param id
     * @return
     */
    public Results setFlagById(String id) {
        Student student = new Student();
        student.setSno(Long.valueOf(id));
        student.setFlag(1);
        int i = studentMapper.updateById(student);
        if(i>0){
            return Results.success();
        }
        return Results.failure();
    }

    /**
     * 批量修改审核状态
     * @param ids
     * @return
     */
    public Results setFlagByIds(String ids) {
        String[] split = ids.split(",");
        int i =0;
        for (String id : split) {
            Student student = new Student();
            student.setSno(Long.valueOf(id));
            student.setFlag(1);
            i = studentMapper.updateById(student);
        }
        if(i>0){
            return Results.success();
        }
        return Results.failure();
    }

    /**
     * 统计在职学生数
     * @return
     */
    public Integer getStudentsCount() {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("flag",1);
        return studentMapper.selectCount(wrapper);
    }
}
