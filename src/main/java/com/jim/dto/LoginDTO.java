package com.jim.dto;

import lombok.Data;

@Data
public class LoginDTO<T> {
    private T id;
    private T password;
    private Integer sole;
    private String url;
}
