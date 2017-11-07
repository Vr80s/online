package com.xczhihui.bxg.online.web.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.bxg.online.web.vo.ChapterPointVo;
import com.xczhihui.bxg.online.web.vo.ChapterVo;

/**
 * 视频章节业务层接口累
 *
 * @author 康荣彩
 * @create 2016-11-02 19:21
 */
public interface ChapterService {

    /**
     * 获取第一级菜单(章节信息)
     * @param courseId  课程id
     * @param request 
     * @return 章节信息集合
     */
    public List<ChapterVo> findChapterInfo(Integer courseId, HttpServletRequest request);


    /**
     * 获取章下面的节以及知识点(树形结构)
     * @param chapterId  章的id号
     * @return 章节信息集合
     */
    public  List<Map<String, Object>> findChapterByChapterId(String chapterId,Integer courseId,HttpServletRequest request);

    /**
     * 查找最后播放未完成的视频(点击继续播放调用)
     * @param courseId  课程ID号
     * @return 最后播放未完成的视频
     */
    public ChapterPointVo  findLastPayVideo (Integer courseId,HttpServletRequest request);


	public Object findChapterInfo(Integer courseId, String userId);


	public Object findChapterByChapterId(String chapterId, Integer courseId, String userId);
}
