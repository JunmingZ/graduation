package com.jim.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 评价
 */
@Data
public class Evaluate {
  @TableId(type = IdType.AUTO)
  private Long id;
  private Long repairsId;    // 报修id
  private Integer star;      //评价等级
  private String  content;   //评价内容
  private Long ctime;


}
