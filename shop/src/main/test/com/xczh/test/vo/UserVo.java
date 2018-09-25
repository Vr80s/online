/**  
* <p>Title: UserVo.java</p>  
* <p>Description: </p>  
* @author yangxuan@ixincheng.com  
* @date 2018年9月17日 
*/
package com.xczh.test.vo;

import java.util.Date;

/**
 * @ClassName: UserVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author yangxuan
 * @email yangxuan@ixincheng.com
 * @date 2018年9月17日
 *
 */

public class UserVo {

	private int id;
	private String name;
	private GroupVo groupVo;
	private Integer age;
    private boolean sex;
    private Date birthDay;	
	
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



	
	public GroupVo getGroupVo() {
		return groupVo;
	}

	public void setGroupVo(GroupVo groupVo) {
		this.groupVo = groupVo;
	}

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
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", group=" + groupVo + ", age=" + age + ", sex=" + sex
				+ ", birthDay=" + birthDay + "]";
	}
	
	
	
}
