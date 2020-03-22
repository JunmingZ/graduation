package com.jim.dto;

import lombok.Data;

@Data
public class RepairsDTO {
    private Long id;
    private Long sno;
    private String  type;
    private String content;
    private Long dormitory;  //宿舍号
    private String repairman; //维修员名
    private Integer state;
    private Integer star;   // 星级
    private String  evaluation;  // 评价
    private Long evaluationId;
    private Long ctime;
    private Long utime;
}
