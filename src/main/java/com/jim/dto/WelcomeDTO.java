package com.jim.dto;

import lombok.Data;

@Data
public class WelcomeDTO {
    private Integer studentCount;  // 学生数
    private Integer dormitoryCount; // 宿舍数
    private Integer repairsCount;  // 报修数
    private Integer repairmanCount;  // 报修人数
    private Integer adminCount;    // 管理员人数
    private Integer evaluateCount;  // 评价数
}
