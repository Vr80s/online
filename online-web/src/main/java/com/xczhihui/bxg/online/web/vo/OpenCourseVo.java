package com.xczhihui.bxg.online.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rongcai Kang on 2016/7/27.
 *
 *
 */
public class OpenCourseVo {

    private Integer id;

    /**
     * 学科id号
     */
    private  Integer menu_id;

    /**
     *课程名称
     */
    private String  courseName;

    /**
     * 课程小图
     */
    private String smallimg_path;

    /**
     * 直播开始时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm", timezone = "GMT+8")
    private Timestamp start_time;

    /**
     * 直播结束时间
     */
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Timestamp end_time;

    /**
     * 格式化直播开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm", timezone = "GMT+8")
    private Timestamp formatStartTime;

    /**
     * 主讲老师
     */
    private String teacherName;

    /**
     * 直播状态 1:直播中 2:未开始
     */
    private Integer broadcastState;

    /**
     * 直播间ID
     */
    private String direct_id;

    /**
     * 外部链接地址
     */
    private String external_links;

    private String coursePwd;

    /**
     * 直播方式
     */
    private  Integer direct_seeding;

    /**
     * 鲜花数
     */
    private Integer flowers_number;

    /**
     * 讲师头像
     */
    private  String  head_img;
    private  String  description;

    /**
     * 最高在线人数
     */
    private  Integer highest_number_line;

    /**
     * 格式化直播开始时间  例如格式：19:20
     */
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Timestamp hourTime;

    private String showFormatDateString;

    /**
     * 星期几所代表的数值 1:星期一，以此类推  0:星期日
     */
    private  Integer day;

    private boolean isFree;

    /**
     * 报名人数
     */
    private Integer learnd_count;
    
    private int isSubscribe;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(int isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public String getCoursePwd() {
		return coursePwd;
	}

	public void setCoursePwd(String coursePwd) {
		this.coursePwd = coursePwd;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSmallimg_path() {
        return smallimg_path;
    }

    public void setSmallimg_path(String smallimg_path) {
        this.smallimg_path = smallimg_path;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getFormatStartTime() {
        return formatStartTime;
    }

    public void setFormatStartTime(Timestamp formatStartTime) {
        this.formatStartTime = formatStartTime;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Integer getBroadcastState() {
        return broadcastState;
    }

    public void setBroadcastState(Integer broadcastState) {
        this.broadcastState = broadcastState;
    }

    public String getDirect_id() {
        return direct_id;
    }

    public void setDirect_id(String direct_id) {
        this.direct_id = direct_id;
    }

    public String getExternal_links() {
        return external_links;
    }

    public void setExternal_links(String external_links) {
        this.external_links = external_links;
    }

    public Integer getDirect_seeding() {
        return direct_seeding;
    }

    public void setDirect_seeding(Integer direct_seeding) {
        this.direct_seeding = direct_seeding;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public Integer getFlowers_number() {
        return flowers_number;
    }

    public void setFlowers_number(Integer flowers_number) {
        this.flowers_number = flowers_number;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public Integer getHighest_number_line() {
        return highest_number_line;
    }

    public void setHighest_number_line(Integer highest_number_line) {
        this.highest_number_line = highest_number_line;
    }

    public Timestamp getHourTime() {
        return hourTime;
    }

    public void setHourTime(Timestamp hourTime) {
        this.hourTime = hourTime;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }

    public Integer getLearnd_count() {
        return learnd_count;
    }

    public void setLearnd_count(Integer learnd_count) {
        this.learnd_count = learnd_count;
    }

    public String getShowFormatDateString() {

        Date startTime = null;
        try {
            startTime=new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(formatStartTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            int c=startTime.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
            if(c==0){
              return "今天 "+new SimpleDateFormat("HH:mm").format(formatStartTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date d=new Date();
        d.setDate((d.getDate()+1));

        try {
            int c=startTime.compareTo(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(d)));
            if(c==0){
                return "明天 "+new SimpleDateFormat("HH:mm").format(formatStartTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(formatStartTime);
    }

    public void setShowFormatDateString(String showFormatDateString) {
        this.showFormatDateString = showFormatDateString;
    }
}
