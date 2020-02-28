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
  private Integer typeId;
  private String content;
  private Long dormitory;  //宿舍号
  private Integer repairmanId; //维修员id
  private Integer state;
  private Long ctime;
  private Long utime;

}
