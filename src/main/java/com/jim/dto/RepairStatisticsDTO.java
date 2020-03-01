package com.jim.dto;


import lombok.Data;

@Data
public class RepairStatisticsDTO<T> {
    public Integer type;
    public Integer untreated;        //未处理数
    public Integer finish;           //已处理
    public Integer total;            //总数
}
