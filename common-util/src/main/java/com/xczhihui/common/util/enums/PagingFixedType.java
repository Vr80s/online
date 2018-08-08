package com.xczhihui.common.util.enums;

/**
 * 
* @ClassName: PagingFixedType
* @Description: 展示的数据,分页数据
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年8月8日
*
 */
public enum PagingFixedType {


    /************  客户端分页展示类别   *****************/
	

    RECOMMENDATION_PAGETYPE_UP(1, "推荐->精品课程、最新课程-->固定展示6个", 6),
    RECOMMENDATION_PAGETYPE_DOWN(2, "推荐->分类课程-->固定展示4个", 4),

    REAL_PAGETYPE_UP(3, "线下课->全国课程-->固定展示6个", 6),
    REAL_PAGETYPE_DOWN(4, "线下课->其他城市-->固定展示4个", 4),

    LIVE_PAGETYPE_UP(5, "直播课->正在直播-->固定展示12个", 12),
    LIVE_PAGETYPE_DOWN(6, "直播课->其他状态-->固定展示4个", 4),

    LISTEN(7, "听课-->固定展示4个", 12),

    /************  PC客户端分页展示类别   *****************/

    PC_RECOMMENDATION(8, "PC端-推荐-->固定展示3个", 3),

    PC_REAL_PAGETYPE_UP(9, "PC端-->线下课->全国课程-->固定展示12个", 12),
    PC_REAL_PAGETYPE_DOWN(10, "PC端-->线下课->其他城市-->固定展示6个", 6),

    PC_LIVE_PAGETYPE(11, "PC端-->直播课--》固定展示3个", 3),

    PC_LISTEN(12, "PC端-->固定展示12个", 12),

    PC_INDEX(13, "PC端-->首页 ---》固定展示3个", 3),

    /************  线下课  *****************/
    
    OFFLINE_CITY_RECOMMEND(14, "学堂线下课城市推荐（推荐很多个）", 100),
    OFFLINE_CITY_CLASSIFY_RECOMMEND(15, "课程筛选列表，筛选条件展示的城市数", 5),
    OFFLINE_CITY_COURSE_RECOMMEND(16, "学堂线下课城市分类列表", 4);


    /**
     * 描述
     **/
    private int code;
    private String text;
    private Integer value;

    private PagingFixedType(int code, String text, Integer value) {
        this.text = text;
        this.code = code;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


}
