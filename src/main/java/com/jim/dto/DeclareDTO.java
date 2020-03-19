package com.jim.dto;

import com.jim.model.RepairType;
import lombok.Data;

import java.util.List;

@Data
public class DeclareDTO {
    private Long sno;
    private Long dormitory;
    private List<RepairType> repairTypes;
}
