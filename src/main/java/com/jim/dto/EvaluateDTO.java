package com.jim.dto;

import com.jim.model.Evaluate;
import lombok.Data;

@Data
public class EvaluateDTO extends Evaluate {
    private Long sno;
    private Integer repairmanId;
}
