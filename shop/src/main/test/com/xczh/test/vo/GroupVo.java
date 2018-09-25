package com.xczh.test.vo;
import java.util.ArrayList;
import java.util.List;

public class GroupVo{
    private int id;
    private String name;

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
	@Override
	public String toString() {
		return "GroupVo [id=" + id + ", name=" + name + "]";
	}
    
    
}