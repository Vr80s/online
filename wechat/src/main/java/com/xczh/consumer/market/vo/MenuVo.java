package com.xczh.consumer.market.vo;/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/18 1:09
 */

import java.io.Serializable;

/**
 * @author liutao
 * @create 2017-09-18 1:09
 **/
public class MenuVo implements Serializable{

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
