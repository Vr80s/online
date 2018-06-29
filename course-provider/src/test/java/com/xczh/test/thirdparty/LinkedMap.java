package com.xczh.test.thirdparty;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/6/28 0028-下午 10:27<br>
 */
public class LinkedMap {
    public static void main(String[] args) {
        //LinkedHashMap 有序
        Map maps = new LinkedHashMap();
        maps.put("2", "李四");
        maps.put("3", "王五");
        maps.put("1", "张三");
        maps.put("4", "赵六");
        maps.put("2", "赵六2");
        System.out.println("LinkedHashMap(有序):");
//        maps.forEach(maps-> System.out.println(maps.));
        Iterator it = maps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entity = (Map.Entry) it.next();
            System.out.println("[ key = " + entity.getKey() +
                    ", value = " + entity.getValue() + " ]");
        }
        //HashMap 无序
        Map map = new HashMap();
        map.put("1", "张三");
        map.put("3", "王五");
        map.put("2", "李四");
        map.put("4", "赵六");
        it = null;
        System.out.println("HashMap(无序):");
        it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entity = (Map.Entry) it.next();
            System.out.println("[ key = " + entity.getKey() +
                    ", value = " + entity.getValue() + " ]");
        }
    }
}
