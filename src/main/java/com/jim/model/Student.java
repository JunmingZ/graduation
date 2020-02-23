package com.jim.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 学生
 */
@Data
public class Student extends Model<Student>  {
  @TableId(type = IdType.AUTO)
  private Long sno;
  private String name;
  private Integer sex;
  private Integer phone;
  private String major;
  private String home;
  private String password;
  private Long dormitory;
  private Long ctime;
  private Long utime;
}
