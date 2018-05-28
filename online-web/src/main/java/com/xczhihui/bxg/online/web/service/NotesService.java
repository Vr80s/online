package com.xczhihui.bxg.online.web.service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.web.vo.NotesCommentVo;
import com.xczhihui.bxg.online.web.vo.NotesVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 笔记模块业务层接口
 * @Author Fudong.Sun【】
 * @Date 2016/12/13 11:01
 */
public interface NotesService {
    /**
     * 保存或修改笔记
     * @param request
     * @param notes
     */
    public void saveOrUpdateNotes(HttpServletRequest request, NotesVo notes);
    /**
     * 我的笔记列表、全部笔记列表
     * @param request
     * @param type 是否我的笔记(0：全部，1：我的笔记，2：我的收藏)
     * @return
     */
    public Page<NotesVo> findNotes(HttpServletRequest request, String videoId,Integer type, Integer pageNumber, Integer pageSize);
    /**
     * 删除笔记
     * @param notes_id
     */
    public void deleteNotes(HttpServletRequest request, String notes_id);
    /**
     * 点赞、取消点赞
     */
    public Map<String,Object> updatePraise(HttpServletRequest request,String notes_id) ;
    /**
     * 笔记收藏、取消收藏
     * @param request
     * @param notes_id
     */
    public boolean updateCollect(HttpServletRequest request,String notes_id);
    /**
     * 查询评论/回复列表
     * @param notes_id
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<NotesCommentVo> findComments(HttpServletRequest request,String notes_id, Integer pageNumber, Integer pageSize);
    /**
     * 新增评论/回复
     * @param nv
     */
    public void saveComment(HttpServletRequest request,NotesCommentVo nv);
    /**
     * 删除评论/回复
     * @param comment_id
     */
    public void deleteComment(HttpServletRequest request, String comment_id);
}
