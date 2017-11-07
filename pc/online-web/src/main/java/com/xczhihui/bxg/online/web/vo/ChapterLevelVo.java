package com.xczhihui.bxg.online.web.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 章、节、知识点、视频级别菜单
 * @Author Fudong.Sun【】
 * @Date 2016/12/1 11:27
 */
public class ChapterLevelVo {
    private String id;
    private String name;
    private Boolean isLearn;
    private List<ChapterLevelVo> chapterSons = new ArrayList();
    private List<Map<String, Object>> videos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Map<String, Object>> getVideos() {
        return videos;
    }

    public void setVideos(List<Map<String, Object>> videos) {
        this.videos = videos;
    }

    public List<ChapterLevelVo> getChapterSons() {
        return chapterSons;
    }

    public void setChapterSons(List<ChapterLevelVo> chapterSons) {
        this.chapterSons = chapterSons;
    }

    public Boolean isLearn() {
        return isLearn==null ? false : isLearn;
    }

    public void setIsLearn(Boolean isLearn) {
        this.isLearn = isLearn;
    }
}
