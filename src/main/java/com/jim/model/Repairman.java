package com.jim.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 *
 */
@Data
public class Repairman {
  @TableId(type = IdType.AUTO)
  private Integer id;
  private String name;
  private Long phone;
  private String password;
  private Integer flag;  // 1 在职  2 离职
  private Long ctime;
  private Long utime;



}
