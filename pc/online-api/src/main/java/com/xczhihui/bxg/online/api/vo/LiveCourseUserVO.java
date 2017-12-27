package com.xczhihui.bxg.online.api.vo;

import java.io.Serializable;

/** 
 * ClassName: ReceivedGift.java <br>
 * Description: 直播课程报名用户<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月16日<br>
 */
public class LiveCourseUserVO implements Serializable{

	private String loginName;
	private String createTime;
	private String name;
	private String actualPay;

    public String getActualPay() {
        return actualPay;
    }

    public void setActualPay(String actualPay) {
        this.actualPay = actualPay;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
