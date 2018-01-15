package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.PaperVo;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 用户作业底层业务类
 * @author Rongcai.Kang
 *
 */
@Repository
public class UserPaperDao extends SimpleHibernateDao {

    /**
     * 获取用户当前作业
     * @param paperStatus 0:作业 1:试卷
     * @param pageNumber 当前也码
     * @param pageSize 每页条数
     * @param user  当前登录用户
     * @return
     */
    public Page<PaperVo>  findMyCurrentPage(Integer paperStatus,Integer pageNumber, Integer pageSize,OnlineUser user){
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 10 : pageSize;
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", user.getId());
        paramMap.put("paperStatus", paperStatus);
        String sql = "select up.id, p.paper_name,cp.end_time,if(now()>=cp.start_time,1,0) status,cp.start_time," +
                     "(select count(*) from oe_exam_user_question where user_paper_id=up.id and user_answer is not null ) as finishSum," +
                     "(select count(*) from oe_exam_user_question where user_paper_id=up.id ) as amount" +
                     " from oe_exam_user_paper up,oe_exam_class_paper cp,oe_exam_paper p " +
                     " where cp.id = up.class_paper_id and cp.paper_id=p.id and up.submit_time is null  and p.type=:paperStatus and up.user_id=:userId  order by cp.end_time desc ";
        return this.findPageBySQL(sql, paramMap, PaperVo.class, pageNumber, pageSize);
    }


    /**
     * 获取用户历史作业
     * @param paperStatus 0:作业 1:试卷
     * @param pageNumber 当前也码
     * @param pageSize 每页条数
     * @param user  当前登录用户
     * @return
     */
    public Page<PaperVo>  findMyHistoryPage(Integer paperStatus,String startTime,String endTime, Integer pageNumber, Integer pageSize, OnlineUser user){
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 10 : pageSize;
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId", user.getId());
        paramMap.put("paperStatus", paperStatus);
        paramMap.put("startTime",startTime);
        paramMap.put("endTime",endTime);
        String sTime="";//开始时间拼接语句
        String eTime=""; //结束时间拼接语句
        if(!StringUtils.isNullOrEmpty(startTime)){
            sTime=" and  cp.start_time >= :startTime";
        }
        if (!StringUtils.isNullOrEmpty(endTime)){
            eTime=" and DATE_SUB(cp.end_time, INTERVAL 1 day)  <=:endTime";
        }
        String sql = " select up.id, p.paper_name,cp.end_time,up.status,cp.start_time,if(up.status=3,up.score,0) score  " +
                     " from oe_exam_user_paper up,oe_exam_class_paper cp,oe_exam_paper p  where cp.id = up.class_paper_id " +
                     " and cp.paper_id=p.id and up.submit_time is not null "+sTime + eTime +"  and p.type=:paperStatus and up.user_id=:userId  order by up.submit_time desc  ";
        return  this.findPageBySQL(sql, paramMap, PaperVo.class, pageNumber, pageSize);
    }


    /**
     * 试卷基本信息
     * @param paperId 试卷id
     * @return
     */
    public List<Map<String,Object>>  findPaperBaseInfo(String paperId,OnlineUser user){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("paperId", paperId);
        paramMap.put("userId",user.getId());
        String  sql = "select p.course_id, p.paper_name,if(up.status=0,cp.end_time,up.submit_time) end_time,up.score,now() currentTime," +
                      "if(up.status=3,up.comment,null) comment,p.score zscore,up.`status`,"+
                      " (select count(*) from oe_exam_user_question where user_paper_id = up.id) as questionNum" +
                      " from oe_exam_user_paper up,oe_exam_class_paper cp,oe_exam_paper p " +
                      " where cp.id = up.class_paper_id and cp.paper_id=p.id  and up.id=:paperId and up.user_id=:userId  and p.type=0 ";
        return this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);

    }

    /**
     * 获取我的未完成作业试卷页面试题信息
     * @param paperId  试卷id
     * @return
     */
    public List<Map<String,Object>>  getMyPaper(String paperId,OnlineUser user){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("paperId", paperId);
        paramMap.put("userId",user.getId());
        String sql =" select q.id, q.question_head,q.options,q.question_type,q.question_score,q.options_picture,q.attachment_url,ifnull(q.user_answer,'') user_answer,q.answer_attachment_url from  oe_exam_user_question  q,oe_exam_user_paper up" +
                    " where q.user_paper_id=up.id and up.user_id=:userId  and   q.user_paper_id=:paperId  order by q.question_type asc ";
        return this.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
    }

    /**
     * 当用户第一次进行写作业时，记录用户第一次进入卷子的时间
     * @param paperId
     * @param user
     */
    public  void  updateStartTimeAboutPaper(String paperId,OnlineUser user){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("paperId", paperId);
        paramMap.put("userId",user.getId());
        //用户第一次进入卷子要记录进来的时间
        String sql="update oe_exam_user_paper set start_time=now()  where id=:paperId and user_id=:userId and start_time is null ";
        this.getNamedParameterJdbcTemplate().update(sql,paramMap);

    }
    /**
     * 保存我的回答 康荣彩
     * @param
     */
    public void updateQuestionById(String questionId,String answer, String attachment){
        String sql = "";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("question_id",questionId);
        paramMap.put("answer",answer);
        paramMap.put("attachment",attachment);
        //获得当前题目
        sql = "select * from oe_exam_user_question where id=:question_id ";
        List<Map<String, Object>> questions = this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
        Map<String, Object> question = questions.size() > 0  ? questions.get(0) : null;

        if(question != null){
            Set questionType= new HashSet<>(); //只存放客观题
            questionType.add(0);
            questionType.add(1);
            questionType.add(2);

            String attachmentSql=StringUtils.isNullOrEmpty(attachment) ? "" : ",answer_attachment_url=:attachment";
            String readOverSql=questionType.contains(question.get("question_type")) && ((answer!=null &&  !"".equals(answer)))  ?  ",read_over=1" : ",read_over=0";
            //更新结果
            int score = 0;
            int isRight=1; //答错
            if( (answer==null || "".equals(answer)) && StringUtils.isNullOrEmpty(attachment)){
                isRight=0; //未答
            }else{
                if (questionType.contains(question.get("question_type")) && question.get("answer").toString().equals(answer)) {
                    score = Integer.valueOf(question.get("question_score").toString());
                    isRight=2;  //答对
                }
            }
            sql ="update oe_exam_user_question set rightOrWrong="+isRight+", user_score="+score+",user_answer=:answer"+attachmentSql+readOverSql+" where id=:question_id";
            this.getNamedParameterJdbcTemplate().update(sql, paramMap);
        }
    }

    /**
     * 提交作业  康荣彩
     * @param paperId  试卷id
     */
    public void savePaper(String paperId,OnlineUser user){
        String sql = "";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("paperId",paperId);
        paramMap.put("userId",user.getId());
        //获得当前题目
        sql = "update oe_exam_user_paper set status=1,submit_time=now()  where status=0 and id =:paperId  and user_id=:userId";
        this.getNamedParameterJdbcTemplate().update(sql,paramMap);
    }


    /**
     * 查看我的作业或成绩
     * @param paperId  试卷id
     * @return
     */
    public List<Map<String,Object>>  findMyPaper(String paperId,OnlineUser user){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("paperId", paperId);
        paramMap.put("userId",user.getId());

        String sql ="select  q.question_head,q.options,q.question_type,q.question_score,q.options_picture,q.attachment_url,q.user_answer,q.solution,q.answer,q.answer_attachment_url  from " +
                    " oe_exam_user_question  q,oe_exam_user_paper up  where q.user_paper_id=up.id and up.user_id=:userId and  q.user_paper_id=:paperId  order by q.question_type asc ";
        return this.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
    }

    /**
     * 查看我作业成绩
     * @param paperId 试卷id
     * @return
     */
    public List<Map<String,Object>> findMyScore(String paperId,OnlineUser user){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("paperId", paperId);
        paramMap.put("userId",user.getId());
        String sql="select ut.question_type,ut.right_sum,ut.wrong_sum,ut.undo_sum,ut.score from oe_exam_user_total ut,oe_exam_user_paper up  where ut.user_paper_id = up.id and  ut.user_paper_id=:paperId and up.user_id=:userId  order by ut.question_type asc";
        return  this.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
    }

}
