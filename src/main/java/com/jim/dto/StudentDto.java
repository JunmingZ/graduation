package com.jim.dto;

import com.jim.model.Student;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudentDto extends Student {
    public List<Integer> bedNums = new ArrayList<>();
}
