package com.jim.dto;

import lombok.Data;

@Data
public class RepairsDTO {
    private Long id;
    private Long sno;
    private String  type;
    private String content;
    private Long dormitory;  //宿舍号
    private String repairman; //维修员id
    private Integer state;
    private Long ctime;
    private Long utime;
}
