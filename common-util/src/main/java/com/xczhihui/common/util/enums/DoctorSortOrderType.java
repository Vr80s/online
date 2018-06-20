package com.xczhihui.common.util.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医师的 列表排序方式
 * @author yangxuan
 */
public enum DoctorSortOrderType {
	
	
	PRICE_DOWN(1,"推荐排序"),
    PRICE_UP(2,"人气最高");

    private int id;
    private String name;
    
    
    DoctorSortOrderType(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public static List<Map<String, Object>> getDoctorSortOrderTypeList(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (DoctorSortOrderType e : DoctorSortOrderType.values()) {
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
