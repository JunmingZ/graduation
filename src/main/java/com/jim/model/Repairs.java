package com.jim.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 报修表
 */
@Data
public class Repairs {
  @TableId(type = IdType.AUTO)
  private Long id;
  private Long sno;
  private Integer type;
  private String content;
  private Long dormitory;  //宿舍号
  private Integer repairId;
  private Integer state;
  private Long handling;
  private Long ctime;

}
