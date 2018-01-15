package com.xczhihui.bxg.online.manager.homework.service.impl;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.cloudClass.vo.GradeDetailVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.GradeVo;
import com.xczhihui.bxg.online.manager.homework.dao.HomeworkDao;
import com.xczhihui.bxg.online.manager.homework.service.HomeworkService;
import com.xczhihui.bxg.online.manager.homework.vo.*;
import com.xczhihui.bxg.online.manager.utils.ArithUtil;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 作业管理模块业务实现类
 * @Author Fudong.Sun【】
 * @Date 2017/2/28 9:29
 */
@Service
public class HomeworServiceImpl extends OnlineBaseServiceImpl implements HomeworkService {

    @Autowired
    private HomeworkDao homeworkDao;

    @Autowired
    private AttachmentCenterService attachmentCenterService;

    /**
     * 获取全部职业课班级信息
     * @param
     * @return
     */
    @Override
    public Page<GradeVo> findGradeList(GradeDetailVo gradeVo, Integer pageNumber, Integer pageSize) {
        Map<String,Object> paramMap= new HashMap<>();
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        StringBuilder sql=new StringBuilder() ;
        sql.append(" select * from(");
        sql.append("select concat(concat(concat(concat(concat(om.name,'-'),st.name),'-'),tm.name),oc.grade_name) as courseName,og.name as name,og.student_amount as studentAmount,oc.grade_name as courseNameTmp,om.name as menuName,st.name as scoreTypeName,tm.name as teachMethodName,ifnull(og.student_count,0) as student_count,\n" );
        sql.append("(select group_concat(ol.name) from oe_lecturer ol where role_type=1 and is_delete=0 and ol.id in (select lecturer_id from course_r_lecturer where course_id=og.course_id and is_delete=0 ))role_type1,\n" );
        sql.append("(select group_concat(ol.name) from oe_lecturer ol where role_type=2 and is_delete=0 and ol.id in (select lecturer_id from course_r_lecturer where course_id=og.course_id and is_delete=0 ))role_type2,\n" );
        sql.append("(select group_concat(ol.name) from oe_lecturer ol where role_type=3 and is_delete=0 and ol.id in (select lecturer_id from course_r_lecturer where course_id=og.course_id and is_delete=0 ))role_type3,\n" );
        sql.append("(select count(1) from oe_exam_class_paper where class_id=og.id) paperCount,");
        sql.append("og.create_time as createTime,og.curriculum_time as curriculumTime,og.stop_time as stopTime,og.status as status,og.id as id,case WHEN og.curriculum_time>SYSDATE() THEN  0 WHEN og.curriculum_time<SYSDATE() THEN  1 END  as otcStatus,\n");
        sql.append("oc.id as courseId,oc.class_Template as classTemplate,og.sort as sort,om.id as menuId,oc.course_type_id,oc.courseType,st.id as scoreTypeId,tm.id as teachMethodId,og.work_day_sum workDaySum,og.rest_day_sum restDaySum,og.qqno,og.default_student_count \n");
        sql.append("from oe_grade og " );						//职业课程
        sql.append("join oe_course oc on oc.id=og.course_id and oc.course_type = 0 join oe_menu om on om.id=oc.menu_id\n" );
        sql.append("join score_type st on st.id=oc.course_type_id left join teach_method tm on tm.id=oc.courseType\n" );
        sql.append(" where 1=1 and og.is_delete=0 and oc.is_delete=0 and st.is_delete=0 and tm.is_delete=0  ");
        sql.append(") ds where 1=1 ");

        if(StringUtils.isNotEmpty(gradeVo.getName()))
        {
            sql.append(" and ds.name like :courseName ");
            paramMap.put("courseName","%"+gradeVo.getName()+"%");
        }
        if(gradeVo.getCourse_id()!=null&&gradeVo.getCourse_id()!=-1)
        {
            sql.append(" and ds.courseId=:courseId");
            paramMap.put("courseId",gradeVo.getCourse_id());
        }
        sql.append(" order by ds.createTime desc");
        Page<GradeVo>  page = dao.findPageBySQL(sql.toString(),paramMap,GradeVo.class, pageNumber, pageSize);
        return page;
    }

    @Override
    public Page<ClassPaperVo> findPaperList(ClassPaperVo searchVo, Integer pageNumber, Integer pageSize) {
        Map<String,Object> paramMap= new HashMap<>();
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        StringBuilder sql=new StringBuilder() ;
        sql.append("select ocp.id,ocp.`status`,ocp.start_time,ocp.end_time,ocp.class_id,ocp.paper_id,");
        sql.append(" oep.difficulty,oep.paper_name paperName,");
        sql.append(" (select count(1) from oe_exam_user_paper where class_paper_id=ocp.id and `status`>=1) as sendCount,");
        sql.append(" (select count(1) from oe_exam_user_paper where class_paper_id=ocp.id) as totalCount,");
        sql.append(" (select round(AVG(score),1) from oe_exam_user_paper where class_paper_id=ocp.id and status!=0) as averageScore,");
        sql.append(" (select count(1) from oe_exam_user_paper where class_paper_id=ocp.id and `status`=1) as unReadOver");
        sql.append(" from oe_exam_class_paper ocp");
        sql.append(" LEFT JOIN oe_exam_paper oep on oep.id=ocp.paper_id");
        sql.append(" where 1=1");
        if(StringUtils.isNotEmpty(searchVo.getDifficulty()))
        {
            sql.append(" and oep.difficulty = :difficulty ");
            paramMap.put("difficulty",searchVo.getDifficulty());
        }
        if(searchVo.getStatus()!=null && searchVo.getStatus()!=-1)
        {
            sql.append(" and ocp.status=:status");
            paramMap.put("status",searchVo.getStatus());
        }
        if(StringUtils.isNotEmpty(searchVo.getPaperName()))
        {
            sql.append(" and oep.paper_name like :paperName ");
            paramMap.put("paperName","%"+searchVo.getPaperName()+"%");
        }
        if(searchVo.getStart_time()!=null){
            sql.append(" and ocp.create_time >= :startTime ");
            paramMap.put("startTime",searchVo.getStart_time());
        }
        if(searchVo.getStart_time()!=null){
            sql.append(" and ocp.create_time <= :endTime ");
            paramMap.put("endTime",searchVo.getStart_time());
        }
        if(StringUtils.isNotEmpty(searchVo.getClass_id()))
        {
            sql.append(" and ocp.class_id = :classId ");
            paramMap.put("classId",searchVo.getClass_id());
        }
        sql.append(" order by ocp.create_time desc");
        Page<ClassPaperVo>  page = dao.findPageBySQL(sql.toString(),paramMap,ClassPaperVo.class, pageNumber, pageSize);
        return page;
    }

    @Override
    public Page<PaperVo> queryHomeworkList(PaperVo searchVo, Integer pageNumber, Integer pageSize) {
        Map<String,Object> paramMap= new HashMap<>();
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        StringBuilder sql=new StringBuilder() ;
        sql.append("select oep.id,oep.`score`,oep.duration,oep.create_time,oep.use_sum as useSum,");
        sql.append(" oep.difficulty as difficult,oep.paper_name paperName");
        sql.append(" from oe_exam_paper oep");
        sql.append(" where 1=1");
        if(StringUtils.isNotEmpty(searchVo.getDifficult()))
        {
            sql.append(" and oep.difficulty = :difficulty ");
            paramMap.put("difficulty",searchVo.getDifficult());
        }
        if(StringUtils.isNotEmpty(searchVo.getPaperName()))
        {
            sql.append(" and oep.paper_name like :paperName ");
            paramMap.put("paperName","%"+searchVo.getPaperName()+"%");
        }
        if(StringUtils.isNotEmpty(searchVo.getCourse_id()))
        {
            sql.append(" and oep.course_id = :courseId ");
            paramMap.put("courseId",searchVo.getCourse_id());
        }
        sql.append(" order by oep.create_time desc");
        Page<PaperVo>  page = dao.findPageBySQL(sql.toString(),paramMap,PaperVo.class, pageNumber, pageSize);
        return page;
    }

    @Override
    public Page<UserPaperVo> queryStudentList(UserPaperVo searchVo, Integer pageNumber, Integer pageSize) {
        Map<String,Object> paramMap= new HashMap<>();
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        StringBuilder sql=new StringBuilder() ;
        sql.append("select oup.id,oup.user_id,round(oup.score,1) score,oup.submit_time,oup.`status`,");
        sql.append(" u.`name` as studentName,");
        sql.append(" (select agc.student_number from oe_apply oa LEFT JOIN apply_r_grade_course agc on oa.id=agc.apply_id where oa.user_id=oup.user_id and agc.grade_id=:classId) as student_number,");
        paramMap.put("classId",searchVo.getClassId());
        sql.append(" CASE oup.`status` when 0 THEN 0 ELSE SEC_TO_TIME((oup.submit_time-oup.start_time)) END as expendTime");
        sql.append(" from oe_exam_user_paper oup");
        sql.append(" LEFT JOIN oe_user u on u.id=oup.user_id");
        sql.append(" where 1=1");
        if(StringUtils.isNotEmpty(searchVo.getClass_paper_id()))
        {
            sql.append(" and oup.class_paper_id = :classPaperId ");
            paramMap.put("classPaperId",searchVo.getClass_paper_id());
        }
        if(searchVo.getStatus()!=null && searchVo.getStatus()!=-1)
        {
            sql.append(" and oup.status=:status");
            paramMap.put("status",searchVo.getStatus());
        }
        if(StringUtils.isNotEmpty(searchVo.getStudentName()))
        {
            sql.append(" and u.`name` like :studentName ");
            paramMap.put("studentName","%"+searchVo.getStudentName()+"%");
        }
        Object sortName = searchVo.getSortName();
        String sortType = searchVo.getSortType();
        if ("id".equals(sortName)) {
            sql.append(" order by oup.submit_time desc");
        }else{
            sql.append(" order by " + sortName + " " + sortType + " ");
        }
        Page<UserPaperVo>  page = dao.findPageBySQL(sql.toString(),paramMap,UserPaperVo.class, pageNumber, pageSize);
        return page;
    }

    @Override
    public void addHomework(ClassPaperVo classPaper) {
        if(classPaper.getId()==null) {//添加
            MapSqlParameterSource params = new MapSqlParameterSource();
            String sql = "insert into oe_exam_class_paper (id,class_id,course_id,paper_id,status,start_time,end_time,create_time) values (:id,:class_id,:course_id,:paper_id,:status,:start_time,:end_time,now())";
            params.addValue("id", UUID.randomUUID().toString().replace("-", ""));
            params.addValue("class_id", classPaper.getClass_id());
            params.addValue("course_id", classPaper.getCourse_id());
            params.addValue("paper_id", classPaper.getPaper_id());
            params.addValue("status", 0);
            params.addValue("start_time", classPaper.getStart_time());
            params.addValue("end_time", classPaper.getEnd_time());
            dao.getNamedParameterJdbcTemplate().update(sql, params);
            //增加试卷的使用次数
            sql = "update oe_exam_paper set use_sum=use_sum+1 where id=:paper_id";
            dao.getNamedParameterJdbcTemplate().update(sql, params);
        }else{//修改
            MapSqlParameterSource params = new MapSqlParameterSource();
            String sql = "update oe_exam_class_paper set paper_id=:paper_id,start_time =:start_time,end_time =:end_time where id=:id";
            params.addValue("id", classPaper.getId());
            params.addValue("paper_id", classPaper.getPaper_id());
            params.addValue("start_time", classPaper.getStart_time());
            params.addValue("end_time", classPaper.getEnd_time());
            dao.getNamedParameterJdbcTemplate().update(sql, params);
        }
    }

    @Override
    public ResponseObject savePublishHomework(String id) {
        Map<String,Object> classPaper = homeworkDao.findClassPaperById(id);
        String classPaperId = (String) classPaper.get("id");
        String paperId = (String) classPaper.get("paper_id");
        if (null != classPaper) {
            if (classPaper.get("end_time") != null) {
                Date nowTime = new Date();
                long diffLong = (((Date)classPaper.get("end_time")).getTime() - nowTime.getTime());
                if (diffLong < 0) {
                    return ResponseObject.newSuccessResponseObject("截止时间已过，请修改。");
                }
            }
            if (classPaper.get("status") != null && classPaper.get("status").toString().equals('1')) {
                return ResponseObject.newSuccessResponseObject("作业已发布");
            }
            // 向发布作业表插入每一个学员的作业数据
            List<Map<String,Object>> users = homeworkDao.findClassUsers((String) classPaper.get("class_id"));
            MapSqlParameterSource params = new MapSqlParameterSource();
            for (Map<String, Object> user : users) {
                //1，写入用户试卷信息表数据
                String userPaperId = UUID.randomUUID().toString().replace("-", "");
                String sql = "insert into oe_exam_user_paper (id,user_id,class_paper_id,status) values" +
                        " ('"+userPaperId+"','"+user.get("id")+"','"+classPaperId+"',0)";
                dao.getNamedParameterJdbcTemplate().update(sql,params);
                //2，写入用户的试题信息表数据
                sql = "insert into oe_exam_user_question (id,user_paper_id,question_type,question_head,options,options_picture,attachment_url,difficulty,answer," +
                        "solution,question_score) select replace(uuid(),'-',''),'" +userPaperId+
                        "',epq.question_type,epq.question_head,epq.options,epq.options_picture,epq.attachment_url,epq.difficulty,epq.answer,epq.solution,epq.question_score"+
                        " from oe_exam_paper_question epq left join oe_exam_paper ep on epq.paper_id=ep.id where ep.id='"+paperId+"'";
                dao.getNamedParameterJdbcTemplate().update(sql,params);
            }
            //3，修改班级试卷的发布状态
            String sql = "update oe_exam_class_paper set status=1 where id=:id";
            params.addValue("id",id);
            dao.getNamedParameterJdbcTemplate().update(sql,params);
        }
        return ResponseObject.newSuccessResponseObject("发布成功");
    }

    @Override
    public void deleteHomework(String id) {
        String sql = "delete from oe_exam_class_paper where id=?";
        dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,id);
    }


    @Override
    public String saveUserResult(String userPaperIds){
         homeworkDao.saveUserResult(userPaperIds);
         return "操作成功";
    }

    @Override
    public Map<Integer, ReadOverPaperQuestionTypeInfoVo> getQuestionsByPaperId(String userPaperId,String sign) throws Exception {
        Map<Integer,ReadOverPaperQuestionTypeInfoVo> questionTypeMap = new HashMap<>();

        for(int type=0;type<7;type++) {
            ReadOverPaperQuestionTypeInfoVo typeVo = dealQuestionType(userPaperId, type,sign);
            questionTypeMap.put(type,typeVo);
        }
        return questionTypeMap;
    }

    @Override
    public void saveReadOverQuestion(String questionId, Double totleScore, Double score) {
        int isCorrect = 0;
        if(totleScore!=0) {
            double rate = ArithUtil.div(score, totleScore, 2);
            if (rate >= 0.60d) {// 试题得分达到60%则此题做对了
                isCorrect = 2;//对
            } else {
                isCorrect = 1;//错
            }
        }
        String sql = "update oe_exam_user_question set user_score=?,read_over=1,rightOrWrong=? where id=?";
        dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,score,isCorrect,questionId);
    }

    @Override
    public ResponseObject judgeReadOverAllQuestions(String userPaperId) {
        String sql = "select count(1) from oe_exam_user_question where user_paper_id=? and read_over=0 and question_type in(3,4,5,6)";
        int count = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql,Integer.class,userPaperId);
        if(count>0){
            return ResponseObject.newSuccessResponseObject(false);
        }else{
            return ResponseObject.newSuccessResponseObject(true);
        }
    }

    @Override
    public void saveReadOverStudentPaper(String userPaperId, String comment) {
        //1,查询试卷整体分数
        String sql = "select sum(user_score) from oe_exam_user_question where user_paper_id=?";
        Double totalScore = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql,Double.class,userPaperId);
        //2，保存试卷分数，评语，修改为已批阅状态
        sql = "update oe_exam_user_paper set score=:score,comment=:comment,status=2 where id=:id and status in(1,2)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",userPaperId);
        params.addValue("comment",comment);
        params.addValue("score",totalScore);
        dao.getNamedParameterJdbcTemplate().update(sql,params);
    }

    @Override
    public ResponseObject judgeHasNextUnReadOverPaper(String classPaperId,String userPaperId) {
        String sql = "select count(1) from oe_exam_user_paper where class_paper_id=? and status=1 and id !=?";
        int count = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql,Integer.class,classPaperId,userPaperId);
        if(count>0){
            return ResponseObject.newSuccessResponseObject(true);
        }else{
            return ResponseObject.newSuccessResponseObject(false);
        }
    }

    @Override
    public String findNextUnReadOverPaper(String classPaperId,String userPaperId) {
        String sql = "select id,score from oe_exam_user_paper where class_paper_id=:classPaperId and status=1 order by submit_time desc limit 0,1";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("classPaperId",classPaperId);
        List<Map<String,Object>> list = dao.getNamedParameterJdbcTemplate().queryForList(sql,params);
        return list.size()>0? (String) list.get(0).get("id") : null;
    }

    @Override
    public String findCommentById(String userPaperId) {
        String sql = "select id,comment from oe_exam_user_paper where id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",userPaperId);
        List<Map<String,Object>> list = dao.getNamedParameterJdbcTemplate().queryForList(sql,params);
        return list.size()>0? (String) list.get(0).get("comment") : "";
    }

    @Override
    public void saveClassReadOverStatus(String classPaperId) {
        String sql = "select count(1) from oe_exam_user_paper where class_paper_id=? and status=1";
        int count = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql,Integer.class,classPaperId);
        if(count==0){//班级下没有未批阅的试卷，修改班级试卷表状态为已批阅
            sql = "update oe_exam_class_paper set status=2 where id=?";
            dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,classPaperId);
        }
    }

    @Override
    public void saveClassPublishStatus(String classPaperId) {
        String sql = "select count(1) from oe_exam_user_paper where class_paper_id=? and status=2";
        int count = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql,Integer.class,classPaperId);
        if(count==0){//班级下没有未发布成绩的试卷，修改班级试卷表状态为已完成
            sql = "update oe_exam_class_paper set status=3 where id=?";
            dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,classPaperId);
        }
    }

    /**
     * 组装各种题型
     * @param userPaperId
     * @param type
     */
    private ReadOverPaperQuestionTypeInfoVo dealQuestionType(String userPaperId, Integer type,String sign) throws Exception {
        String opstr = "ABCDEFGH";
        ReadOverPaperQuestionTypeInfoVo typeVo = new ReadOverPaperQuestionTypeInfoVo();
        List<UserPaperQuestionVo> questionType;
        if(sign.equalsIgnoreCase("user")) {
            questionType = homeworkDao.findUserPaperQuestionsByUserPaperId(userPaperId, type);
        }else{
            questionType = homeworkDao.findPaperQuestionsByPaperId(userPaperId,type);
        }
        typeVo.setQuestionTypeNumber(type);
        Double totalScore = 0d;
        Double userScore = 0d;
        Integer correctQuestions = 0;
        Integer errorQuestions = 0;
        Gson g = new GsonBuilder().create();
        for (UserPaperQuestionVo userPaperQuestionVo : questionType) {
            if(type == 5 || type == 6) {
                //处理附件名称
                String attachment_url = userPaperQuestionVo.getAttachment_url();
                if (!StringUtils.isEmpty(attachment_url)) {
                    String attachmentName = attachment_url.substring(attachment_url.lastIndexOf("/") + 1, attachment_url.length());
                    Attachment att = attachmentCenterService.getAttachmentByFileName(attachmentName);
                    if(att!=null) {
                        String fileName = att.getOrgFileName();
                        userPaperQuestionVo.setOrgFileName(fileName);
                    }
                }
                String ans_attachment_url = userPaperQuestionVo.getAnswer_attachment_url();
                if (!StringUtils.isEmpty(ans_attachment_url)) {
                    String attachmentName = ans_attachment_url.substring(ans_attachment_url.lastIndexOf("/") + 1, ans_attachment_url.length());
                    Attachment att = attachmentCenterService.getAttachmentByFileName(attachmentName);
                    if(att!=null) {
                        String fileName = att.getOrgFileName();
                        userPaperQuestionVo.setUsrFileName(fileName);
                    }
                }
            }
            if(userPaperQuestionVo.getRightOrWrong()!=null) {
                if (userPaperQuestionVo.getRightOrWrong() == 1) {//错误题
                    errorQuestions++;
                } else if (userPaperQuestionVo.getRightOrWrong() == 2) {//对的题
                    correctQuestions++;
                }
            }

            totalScore += userPaperQuestionVo.getQuestion_score();
            if(userPaperQuestionVo.getUser_score()!=null) {
                userScore += userPaperQuestionVo.getUser_score();
            }

            if(type==0){//单选题处理
                //选项解析
                List<Map<String,Object>> oplists = new ArrayList<>();
                String ops = userPaperQuestionVo.getOptions();//选项
                String pis = userPaperQuestionVo.getOptions_picture();//选项图片
                if(!StringUtils.isEmpty(ops)) {
                    List<String> lst = g.fromJson(ops, List.class);
                    for (int i=0;i<lst.size();i++) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("key",opstr.charAt(i));
                        map.put("value",lst.get(i));
                        if(!StringUtils.isEmpty(pis)) {
                            List<String> pst = g.fromJson(pis, List.class);
                            map.put("pic",pst.get(i));
                        }
                        oplists.add(map);
                    }
                    userPaperQuestionVo.setOptionList(oplists);
                }

                //答案解析
                String ans = userPaperQuestionVo.getAnswer();
                if(!StringUtils.isEmpty(ans)) {
                    ans = String.valueOf(opstr.charAt(Integer.parseInt(ans)));
                    userPaperQuestionVo.setAnswer(ans);
                }
                String uans = userPaperQuestionVo.getUser_answer();
                if(!StringUtils.isEmpty(uans)) {
                    uans = String.valueOf(opstr.charAt(Integer.parseInt(uans)));
                    userPaperQuestionVo.setUser_answer(uans);
                }
            }else if(type==1){//多选题处理
                //选项解析
                List<Map<String,Object>> oplists = new ArrayList<>();
                String ops = userPaperQuestionVo.getOptions();//选项
                String pis = userPaperQuestionVo.getOptions_picture();//选项图片
                if(!StringUtils.isEmpty(ops)) {
                    List<String> lst = g.fromJson(ops, List.class);
                    for (int i=0;i<lst.size();i++) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("key",opstr.charAt(i));
                        map.put("value",lst.get(i));
                        if(!StringUtils.isEmpty(pis)) {
                            List<String> pst = g.fromJson(pis, List.class);
                            map.put("pic",pst.get(i));
                        }
                        oplists.add(map);
                    }
                    userPaperQuestionVo.setOptionList(oplists);
                }
                //答案解析
                String ans = userPaperQuestionVo.getAnswer();
                if(!StringUtils.isEmpty(ans)) {
                    List<String> lst = g.fromJson(ans, List.class);
                    String anstr = "";
                    for (String s : lst) {
                        anstr += opstr.charAt(Integer.parseInt(s));
                    }
                    userPaperQuestionVo.setAnswer(anstr);
                }
                String uans = userPaperQuestionVo.getUser_answer();
                if(!StringUtils.isEmpty(uans)) {
                    List<String> lst = g.fromJson(uans, List.class);
                    String anstr = "";
                    for (String s : lst) {
                        anstr += opstr.charAt(Integer.parseInt(s));
                    }
                    userPaperQuestionVo.setUser_answer(anstr);
                }
            }else if (type == 3) {//填空题数据处理
                //标准答案解析
                String as = userPaperQuestionVo.getAnswer();
                if (!StringUtils.isEmpty(as)){
                    String asstr = "";
                    as = as.replace("\"^\"","\",\"");
                    List<String> lst = g.fromJson(as,List.class);
                    if (lst.size() > 0) {
                        for (String s : lst) {
                            asstr += (s+" ");
                        }
                    }
                    userPaperQuestionVo.setAnswer(asstr);
                }
                //学员答案解析
                String stu = userPaperQuestionVo.getUser_answer();
                if (!StringUtils.isEmpty(stu)){
                    String asstr = "";
                    stu = stu.replace("\"^\"","\",\"");
                    List<String> lst = g.fromJson(stu,List.class);
                    if (lst.size() > 0) {
                        for (String s : lst) {
                            asstr += (s+" ");
                        }
                    }
                    userPaperQuestionVo.setUser_answer(asstr);
                }
            }
        }
        typeVo.setQuestionInfoVos(questionType);
        typeVo.setTotleScore(totalScore);
        typeVo.setScore(userScore);
        typeVo.setErrorQuestions(errorQuestions);
        typeVo.setCorrectQuestions(correctQuestions);
        return typeVo;
    }
}
