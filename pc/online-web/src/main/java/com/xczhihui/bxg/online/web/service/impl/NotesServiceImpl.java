package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.dao.NotesDao;
import com.xczhihui.bxg.online.web.service.NotesService;
import com.xczhihui.bxg.online.web.vo.NotesCommentVo;
import com.xczhihui.bxg.online.web.vo.NotesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 笔记模块接口实现代码
 * @Author Fudong.Sun【】
 * @Date 2016/12/13 11:02
 */
@Service
public class NotesServiceImpl implements NotesService{
    @Autowired
    NotesDao notesDao;

    @Override
    public void saveOrUpdateNotes(HttpServletRequest request, NotesVo notes) {
        if(notes.getId()==null){
            //获取当前登录用户信息
            OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id",user.getId());
            paramMap.put("courseId",notes.getCourse_id());
            List<Map<String, Object>> note = notesDao.getNamedParameterJdbcTemplate().queryForList("SELECT agc.grade_id FROM oe_apply oa LEFT JOIN `apply_r_grade_course` agc on oa.id=agc.apply_id where oa.user_id =:id and agc.course_id =:courseId",paramMap);
            if(note!=null && note.size()>0) {
                notes.setGrade_id((Integer) note.get(0).get("grade_id"));
            }
            notes.setCreate_person(user.getLoginName());
            notes.setUser_id(user.getId());
            notes.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            notesDao.saveNotes(notes);
        }else{
            notesDao.updateNotes(notes.getId(),notes.getContent(),notes.getIs_share());
        }
    }

    @Override
    public Page<NotesVo> findNotes(HttpServletRequest request, String videoId,Integer type, Integer pageNumber, Integer pageSize) {
        //获取当前登录用户信息
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        String userId = "";
        if(user!=null){
            userId=user.getId();
        }
        return notesDao.findNotes(type,videoId,userId,pageNumber,pageSize);
    }

    @Override
    public void deleteNotes(HttpServletRequest request, String notes_id) {
        //获取当前登录用户信息
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        notesDao.deleteNotes(request,user,notes_id);
    }

    @Override
    public Map<String,Object> updatePraise(HttpServletRequest request,String notes_id) {
        //获取当前登录用户信息
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        return notesDao.praise(notes_id,user);
    }

    @Override
    public boolean updateCollect(HttpServletRequest request,String notes_id) {
        //获取当前登录用户信息
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        return notesDao.updateCollect(notes_id,user);
    }

    @Override
    public Page<NotesCommentVo> findComments(HttpServletRequest request,String notes_id, Integer pageNumber, Integer pageSize) {
        //获取当前登录用户信息
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        String userId = "";
        if(user!=null){
            userId=user.getId();
        }
        return notesDao.findComments(userId,notes_id,pageNumber,pageSize);
    }

    @Override
    public void saveComment(HttpServletRequest request,NotesCommentVo nv) {
        //获取当前登录用户信息
        OnlineUser u = (OnlineUser) UserLoginUtil.getLoginUser(request);
        nv.setCreate_person(u.getLoginName());
        nv.setCreate_head_img(u.getSmallHeadPhoto());
        nv.setCreate_nick_name(u.getName());
        nv.setUser_id(u.getId());
        notesDao.saveComment(nv);
    }

    @Override
    public void deleteComment(HttpServletRequest request, String comment_id) {
        //获取当前登录用户信息
        OnlineUser user = (OnlineUser) UserLoginUtil.getLoginUser(request);
        notesDao.deleteComment(request,user,comment_id);
    }
}
