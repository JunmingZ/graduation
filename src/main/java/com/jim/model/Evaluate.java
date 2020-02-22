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
  private Long sno;
  private Integer evaluate;
  private Integer repairId;
  private Long ctime;


}
