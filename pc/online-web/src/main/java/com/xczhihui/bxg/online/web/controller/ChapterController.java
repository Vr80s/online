package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 章节信息控制层
 *
 * @author 康荣彩
 * @create 2016-11-02 17:58
 */
@RestController
@RequestMapping(value = "online/chapter")
public class ChapterController {

    @Autowired
    private ChapterService   chapterService;


    /**
     * 获取相关课程下的所有章节信息
     * @param courseId 课程的id号
     */
    @RequestMapping(value = "/findChapterInfo",method= RequestMethod.GET)
    public ResponseObject findChapterInfo( Integer courseId,HttpServletRequest request){
    	
        return  ResponseObject.newSuccessResponseObject(chapterService.findChapterInfo(courseId,request));
    }


    /**
     * 获取章下面的节以及知识点(树形结构)
     * @param chapterId  章的id号
     * @return 章节信息集合
     */
    @RequestMapping(value = "/findChapterByChapterId",method= RequestMethod.GET)
    public ResponseObject findChapterByChapterId(String chapterId,Integer courseId,HttpServletRequest request) {
        return  ResponseObject.newSuccessResponseObject(chapterService.findChapterByChapterId(chapterId,courseId,request));
    }

    /**
     * 查找最后播放未完成的视频(点击继续播放调用)
     * @param courseId  课程ID号
     * @return 最后播放未完成的视频
     */
    @RequestMapping(value = "/findLastPayVideo",method= RequestMethod.GET)
    public ResponseObject findLastPayVideo (Integer courseId,HttpServletRequest request) {
        return  ResponseObject.newSuccessResponseObject(chapterService.findLastPayVideo(courseId, request));
    }
}
