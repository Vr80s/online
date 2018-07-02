package com.xczhihui.common.util.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: LiveStatus
 * @Description: 直播状态
 * @Author: wangyishuai
 * @email: 15210815880@163.com
 * @CreateDate: 2018/5/10 15:10
 **/
public enum LiveStatus {
    LIVEING(1, "直播中"),
    NOT_LIVE(2, "未直播"),
    HIGHLIGHT(3, "精彩回放");
    private int id;
    private String name;

    LiveStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Map<String, Object>> getLiveStatusList() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (LiveStatus e : LiveStatus.values()) {
            Map m = new HashMap();
            m.put("id", e.getId());
            m.put("name", e.getName());
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
