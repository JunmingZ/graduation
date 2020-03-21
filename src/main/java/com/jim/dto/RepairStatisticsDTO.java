package com.jim.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RepairStatisticsDTO {
    private List<Integer> init = new ArrayList<>();             // 待分配
    private List<Integer> wait = new ArrayList<>();             // 待处理
    private List<Integer> finish = new ArrayList<>();           //已处理
    private List<Integer> total = new ArrayList<>();            //总数
}
