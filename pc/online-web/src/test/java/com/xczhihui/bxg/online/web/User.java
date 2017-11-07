package com.xczhihui.bxg.online.web;

import java.util.Date;

public class User {
    private String name;
    private String address;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static void main(String[] args) throws InterruptedException {
        String timestr = new Date().getTime()/1000+"";
        System.out.println(timestr);
        Thread.sleep(1000);
        timestr = new Date().getTime()/1000+"";
        System.out.println(timestr);
        Thread.sleep(1000);
        timestr = new Date().getTime()/1000+"";
        System.out.println(timestr);
        Thread.sleep(1000);
        timestr = new Date().getTime()/1000+"";
        System.out.println(timestr);
    }
}
