package com.xczhihui.bxg.online.web.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.web.dao.ChapterDao;
import com.xczhihui.bxg.online.web.service.ChapterService;
import com.xczhihui.bxg.online.web.vo.ChapterPointVo;
import com.xczhihui.bxg.online.web.vo.ChapterVo;

/**
 * 视频章节业务层实现类
 *
 * @author 康荣彩
 * @create 2016-11-02 19:21
 */
@Service
public class ChapterServiceImpl  extends OnlineBaseServiceImpl implements ChapterService{

    @Autowired
    private ChapterDao chapterDao;


    /**
     * 获取第一级菜单(章节信息)
     * @param courseId  课程id
     * @return 章节信息集合
     */
    public List<ChapterVo> findChapterInfo(Integer courseId, HttpServletRequest request) {
        return chapterDao.findChapterInfo(courseId, request);
    }

    /**
     * 获取章下面的节以及知识点(树形结构)
     * @param chapterId  章的id号
     * @return 章节信息集合
     */
    public  List<Map<String, Object>>  findChapterByChapterId(String chapterId ,Integer courseId,HttpServletRequest request) {
         return  chapterDao.findChapterByChapterId(chapterId, courseId, request);
    }

    /**
     * 查找最后播放未完成的视频(点击继续播放调用)
     * @param courseId  课程ID号
     * @return 最后播放未完成的视频
     */
    public ChapterPointVo  findLastPayVideo (Integer courseId,HttpServletRequest request) {
        return  chapterDao.findLastPayVideo( courseId, request);
    }

	@Override
	public Object findChapterInfo(Integer courseId, String userId) {
		return chapterDao.findChapterInfo(courseId, userId);
	}

	@Override
	public Object findChapterByChapterId(String chapterId, Integer courseId, String userId) {
		return  chapterDao.findChapterByChapterId(chapterId, courseId, userId);
	}
}
