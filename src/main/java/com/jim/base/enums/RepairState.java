package com.jim.base.enums;


public enum RepairState {
    INIT(1,"待分配"),
    WAIT(2,"待处理"),
    FINISH(3,"已处理")
    ;

    private Integer code;

    private String state;

    RepairState(Integer code, String state){
        this.code = code;
        this.state = state;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static String getMessage(String name) {
        for (RepairState item : RepairState.values()) {
            if (item.name().equals(name)) {
                return item.state;
            }
        }
        return null;
    }

    public static Integer getCode(String name) {
        for (RepairState item : RepairState.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }
}
