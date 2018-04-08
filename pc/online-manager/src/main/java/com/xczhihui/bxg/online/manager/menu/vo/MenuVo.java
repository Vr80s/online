package com.xczhihui.bxg.online.manager.menu.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * 菜单web端调用的结果封装类
 * @author Rongcai Kang
 */
public class MenuVo extends OnlineBaseVo {
    private String id;
    /**
     * 菜单名
     */
    private String name;
    /**
     * 包含班级
     */
    private String courseName;
    /**
     * 菜单编号
     */
    private String number;

    /**
     * 菜单类型
     */
    private Integer type;

    /**
     * 菜单排序
     */
    private Integer sort;

    /**
     * 课程数
     */
    private Integer courseCount;

    private Integer status;

    /**
     * 菜单名
     */
    private String menuName;
    
    

    @JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
    private Date createTime;
    
    /**
     * 备注
     */
    private String remark;
    
    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
     * 子菜单
     */
    private String childMenuNames;

    private String orderCourseCount; //课程排序

    private Date time_start;

    private Date time_end;

    private List<Menu> sencodMenu;

    private Integer parentId;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getOrderCourseCount() {
        return orderCourseCount;
    }

    public void setOrderCourseCount(String orderCourseCount) {
        this.orderCourseCount = orderCourseCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<Menu> getSencodMenu() {
        return sencodMenu;
    }

    public void setSencodMenu(List<Menu> sencodMenu) {
        this.sencodMenu = sencodMenu;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public Integer getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getChildMenuNames() {
        return childMenuNames;
    }

    public void setChildMenuNames(String childMenuNames) {
        this.childMenuNames = childMenuNames;
    }

    public Date getTime_end() {
        return time_end;
    }

    public void setTime_end(Date time_end) {
        this.time_end = time_end;
    }

    public Date getTime_start() {
        return time_start;
    }

    public void setTime_start(Date time_start) {
        this.time_start = time_start;
    }

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
    
    
}
