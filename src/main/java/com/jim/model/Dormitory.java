package com.jim.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 宿舍
 */
@Data
public class Dormitory {
  private Long id;  //宿舍号
  private String description; //描述
  private Long ctime;
  private Long utime;
}
