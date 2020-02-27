package com.jim.dto;


import lombok.Data;

@Data
public class RepairStatisticsDTO<T> {
    public Integer type;
    public Integer untreated;        //未处理数
    public Integer pending;          //待处理数
    public Integer finish;
    public Integer total;            //总数
}
