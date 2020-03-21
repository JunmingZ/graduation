package com.jim.dto;


import lombok.Data;

@Data
public class RepairStatisticsDTO<T> {
    private Integer type;
    private Integer init;             // 待分配
    private Integer wait;             // 待处理
    private Integer finish;           //已处理
    private Integer total;            //总数
}
