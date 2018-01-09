package com.xczhihui.bxg.online.web.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

/**
 * Created by admin on 2016/7/27.
 */
public class CourseLecturVo extends OnlineBaseVo {

    /**
     *课程ID
     */
    private int id;
    /**
     *课程名
     */
    private String gradeName;

    /**
     * 课程小图
     */
    private String smallImgPath;


    /**
     * 讲师名
     */
    private String  name;

    /**
     * 已学习人数
     */
    private Integer learnd_count;

    /**
     *授课类型
     */
    private String courseType;

    /**
     *课程当前价格
     */
    private String currentPrice;

    /**
     *课程原价
     */
    private String originalCost;

    private String address;

    /**
     *课程时长
     */
    private String courseLength;

    /**
     *是否免费 true:免费  false:不免费
     */
    private boolean isFree;

    /**
     *当前页码
     */
    private Integer pageNumber;

    /**
     *每页总条数
     */
    private Integer pageSize;

    /**
     *总页数
     */
    private Integer totalPageCount;

    /**
     * 课程跳转是否展示课程介绍页 0:不展示  1:展示
     */
    private Integer description_show;

    /**
     * 推荐课程小尺寸图片
     */
    private String  recImgPath;
    
    private String  type;
    
    private String  direct_id;

    private String  userLecturerId;

    private String  coursePwd;

    private String  multimediaType;

    private String  isRecommend;

    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private Date endTime;

    public String getMultimediaType() {
		return multimediaType;
	}

	public void setMultimediaType(String multimediaType) {
		this.multimediaType = multimediaType;
	}

	public String getCoursePwd() {
		return coursePwd;
	}

	public void setCoursePwd(String coursePwd) {
		this.coursePwd = coursePwd;
	}

	public String getUserLecturerId() {
		return userLecturerId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setUserLecturerId(String userLecturerId) {
		this.userLecturerId = userLecturerId;
	}

	public String getDirect_id() {
		return direct_id;
	}

	public void setDirect_id(String direct_id) {
		this.direct_id = direct_id;
	}

	public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getDescription_show() {
        return description_show;
    }

    public void setDescription_show(Integer description_show) {
        this.description_show = description_show;
    }

    public String getSmallImgPath() {
        return smallImgPath;
    }

    public void setSmallImgPath(String smallImgPath) {
        this.smallImgPath = smallImgPath;
    }

    public String getName() {

        return name==null ? "暂无讲师" : name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Integer getLearnd_count() {
        return learnd_count;
    }

    public void setLearnd_count(Integer learnd_count) {
        this.learnd_count = learnd_count;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(String originalCost) {
        this.originalCost = originalCost;
    }

    public String getCourseLength() {
        return courseLength;
    }

    public void setCourseLength(String courseLength) {
        this.courseLength = courseLength;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
    }

    public String getCourseType() {
        return courseType;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(Integer totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public String getRecImgPath() {
        return recImgPath;
    }

    public void setRecImgPath(String recImgPath) {
        this.recImgPath = recImgPath;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }
}
