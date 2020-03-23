package com.jim.dto;

import lombok.Data;
import org.omg.PortableInterceptor.INACTIVE;

@Data
public class RepairsDTO {
    private Long id;
    private Long sno;
    private Long dormitory;  //宿舍号
    private String  type;
    private String content;
    private Integer repairmanId;
    private String repairman; //维修员名
    private Integer state;
    private Integer star;   // 星级
    private String  evaluation;  // 评价
    private Long evaluationId;
    private Long ctime;
    private Long utime;
}
