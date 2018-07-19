package com.xczhihui.common.util.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: LiveStatus
 * @Description: 课程类型
 * @Author: wangyishuai
 * @email: 15210815880@163.com
 * @CreateDate: 2018/5/10 15:10
 **/
public enum RecordType {
    
    STUDY(1, "学习记录"),
    LOOK(5, "观看记录");
    
    private int id;
    private String name;

    RecordType(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
