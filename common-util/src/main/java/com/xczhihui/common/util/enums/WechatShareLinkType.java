package com.xczhihui.common.util.enums;

/**
 * banner图类型
 * <p>
 * ClassName: ThirdPartyType.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月2日<br>
 */
public enum WechatShareLinkType {


    INDEX_PAGE("/xcview/html/physician/index.html", "首页"),

    EVPI("/xcview/html/evpi.html", "完善信息"),

    BING_PHOME("/xcview/html/evpi.html", "绑定手机号页面"),

    
    // shareBack=1  --> 分享返回。  分享页面涉及到的，点击手机返回，关闭公众号回到聊天

    /*************    课程的分享页面  开始       ***************/

    SCHOOL_AUDIO("/xcview/html/school_audio.html?shareBack=1&course_id=", "视频音频展示页"),
    SCHOOL_PLAY("/xcview/html/school_play.html?shareBack=1&course_id=", "直播展示页"),
    SCHOOL_CLASS("/xcview/html/school_class.html?shareBack=1&course_id=", "线下课展示页"),

    LIVE_AUDIO("/xcview/html/live_audio.html?shareBack=1&my_study=", "视频/音频"),
    LIVE_PLAY("/xcview/html/live_play.html?shareBack=1&my_study=", "直播"),
    LIVE_CLASS("/xcview/html/live_class.html?shareBack=1&my_study=", "线下课"),
    LIVE_SELECT_ALBUM("/xcview/html/live_select_album.html?shareBack=1&course_id=", "专辑播放页"),


    /*************    课程的分享页面  结束       ***************/

    UNSHELVE("/xcview/html/unshelve.html", "课程下架页面"),
    LIVE_PERSONAL("/xcview/html/live_personal.html?shareBack=1&userLecturerId=", "主播"),
    APPRENTICE("/xcview/html/apprentice/inherited_introduction.html?shareBack=1&merId=", "师承"),

    DOCDOT_SHARE("/xcview/html/physician/physicians_page.html?shareBack=1&doctor=", "医师页面"),

    //这里文章、医案、著作的统一了一个页面了
    //ACTICLE_SHARE("/xcview/html/physician/article.html?shareBack=1&articleId=", "文章"),
    MEDICAL_CASES("/xcview/html/physician/consilia.html?shareBack=1&consiliaId=", "医案");
    /**
     * 描述
     **/
    private String link;
    private String text;

    private WechatShareLinkType(String link, String text) {
        this.link = link;
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
