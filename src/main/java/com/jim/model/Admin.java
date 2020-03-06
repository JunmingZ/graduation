package com.jim.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Admin {
  @TableId(type = IdType.AUTO)
  private Integer id;
  private String name;
  private String  phone;
  private Integer sex;
  private String password;
  private Long ctime;
  private Long utime;
}
