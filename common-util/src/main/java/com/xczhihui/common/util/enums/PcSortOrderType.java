package com.xczhihui.common.util.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * pc端的排序方式
 * @author yangxuan
 */
public enum PcSortOrderType {
	
	
	
	SYNTHESIZE(1,"综合排序"),
	LATEST(2,"最新排序"),
	HITS(3,"人气排序"),
	PRICE_DOWN(4,"价格降序"),
    PRICE_UP(5,"价格升序");

    private int id;
    private String name;
    
    
    PcSortOrderType(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public static List<Map<String, Object>> getPayStatusList(){
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (PcSortOrderType e : PcSortOrderType.values()) {
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
