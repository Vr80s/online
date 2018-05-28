package com.xczhihui.common.util.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: LiveStatus
 * @Description: 付费状态
 * @Author: wangyishuai
 * @email: 15210815880@163.com
 * @CreateDate: 2018/5/10 15:10
 **/
public enum PayStatus {
    FREE(1,"免费"),
    PAY(0,"付费");

    private int id;
    private String name;
    PayStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public static List<Map<String, Object>> getPayStatusList(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (PayStatus e : PayStatus.values()) {
            Map m = new HashMap();
            m.put("id",e.getId());
            m.put("name",e.getName());
            list.add(m);
        }
        return list;
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
