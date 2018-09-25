package com.xczh.test.vo;

import java.util.Date;

public class User extends Person{
	
    private int id;
    private String name;
    private Group group;
   
    
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
    public Group getGroup() {
        return group;
    }
    public void setGroup(Group group) {
        this.group = group;
    }
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", group=" + group + "]";
	}
    
    
}