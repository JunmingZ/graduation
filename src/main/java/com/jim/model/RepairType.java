package com.jim.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 报修类型
 */
@Data
public class RepairType {
  @TableId(type = IdType.AUTO)
  private Integer id;
  private String type;
  private Long ctime;
  private Long utime;


}
