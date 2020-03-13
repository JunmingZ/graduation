package com.jim.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 学生
 */
@Data
public class Student extends Model<Student>  {
  @TableId
  private Long sno;
  private String name;
  private Integer sex;
  private Integer phone;
  private String major;
  private String home;
  private String password;
  private Long dormitory;
  private Integer flag;  // 1. 通过  2. 待审核
  private Long ctime;
  private Long utime;
}
