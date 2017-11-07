package com.xczhihui.bxg.online.web.controller;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.web.service.NotesService;
import com.xczhihui.bxg.online.web.vo.NotesCommentVo;
import com.xczhihui.bxg.online.web.vo.NotesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 笔记模块控制层
 * @Author Fudong.Sun【】
 * @Date 2016/12/13 10:46
 */
@RestController
@RequestMapping(value = "/notes")
public class NotesController {
    @Autowired
    NotesService notesService;

    /**
     * 保存或修改笔记
     * @param request
     * @param notes
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    public ResponseObject saveOrUpdateNotes(HttpServletRequest request, NotesVo notes){
        notesService.saveOrUpdateNotes(request,notes);
        return ResponseObject.newSuccessResponseObject("成功！");
    }
    /**
     * 我的笔记列表、全部笔记列表
     * @param request
     * @param type 是否我的笔记(0：全部，1：我的笔记，2：我的收藏)
     * @return
     */
    @RequestMapping(value = "/findNotes")
    public ResponseObject findNotes(HttpServletRequest request, String videoId, Integer type, Integer pageNumber, Integer pageSize){
        return ResponseObject.newSuccessResponseObject(notesService.findNotes(request,videoId,type,pageNumber,pageSize));
    }
    /**
     * 删除笔记
     * @param request
     * @param notes_id 笔记id
     * @return
     */
    @RequestMapping(value = "/deleteNotes",method = RequestMethod.POST)
    public ResponseObject deleteNotes(HttpServletRequest request,String notes_id) {
        notesService.deleteNotes(request,notes_id);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }
    /**
     * 点赞、取消点赞
     * @param request
     * @param notes_id 笔记id
     * @return
     */
    @RequestMapping(value = "/updatePraise",method = RequestMethod.POST)
    public ResponseObject updatePraise(HttpServletRequest request, String notes_id) {
        return ResponseObject.newSuccessResponseObject(notesService.updatePraise(request, notes_id));
    }
    /**
     * 收藏、取消收藏
     * @param request
     * @param notes_id 笔记id
     * @return
     */
    @RequestMapping(value = "/updateCollect",method = RequestMethod.POST)
    public ResponseObject updateCollect(HttpServletRequest request,String notes_id) {
        return ResponseObject.newSuccessResponseObject(notesService.updateCollect(request,notes_id));
    }

    /**
     * 根据笔记id查询笔记的评价/回复
     * @param notes_id 笔记id
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/findComments")
    public ResponseObject findComments(HttpServletRequest request, String notes_id,Integer pageNumber,Integer pageSize) {
        return ResponseObject.newSuccessResponseObject(notesService.findComments(request,notes_id,pageNumber,pageSize));
    }

    /**
     * 保存评论/回复
     * @param request
     * @param nc
     * @return
     */
    @RequestMapping(value = "/saveComment",method = RequestMethod.POST)
    public ResponseObject saveComment(HttpServletRequest request,NotesCommentVo nc) {
        notesService.saveComment(request,nc);
        return ResponseObject.newSuccessResponseObject("保存成功！");
    }
    /**
     * 删除评论/回复
     * @param request
     * @param commentId 评论/回复id
     * @return
     */
    @RequestMapping(value = "/deleteComment",method = RequestMethod.POST)
    public ResponseObject deleteComment(HttpServletRequest request,String commentId) {
        notesService.deleteComment(request,commentId);
        return ResponseObject.newSuccessResponseObject("删除成功");
    }
}
