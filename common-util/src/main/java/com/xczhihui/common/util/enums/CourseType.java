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
public enum CourseType {
	
	AUDIO_LIVE(6, "语音直播"),
	LIVE(3, "视频直播"),
    VIDEO(1, "视频点播"),
    OFFLINE(4, "线下课程"),
    AUDIO(2, "音频课程");
   
    //APPRENTICE(5, "师承直播")

    private int id;
    private String name;

    CourseType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Map<String, Object>> getCourseType() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (CourseType e : CourseType.values()) {
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
