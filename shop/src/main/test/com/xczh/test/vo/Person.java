/**  
* <p>Title: Person.java</p>  
* <p>Description: </p>  
* @author yangxuan@ixincheng.com  
* @date 2018年9月17日 
*/  
package com.xczh.test.vo;

import java.util.Date;

/**
* @ClassName: Person
* @Description: TODO(这里用一句话描述这个类的作用)
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年9月17日
*
*/

public class Person {

	private Integer age;
	private boolean sex;
	private Date birthDay;
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public boolean getSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	
	
}
