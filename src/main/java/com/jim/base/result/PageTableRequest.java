package com.jim.base.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageTableRequest implements Serializable {

    private Integer page;  //当前页
    private Integer limit;  // 显示条数数目
    private Integer offset; // 起始位置

    public void countOffset(){
        if(null == this.page || null == this.limit){
            this.offset = 0;
            return;
        }
        this.offset = (this.page - 1) * limit;
    }

}
