package com.xczhihui.bxg.online.web.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.api.dto.ProgressDto;
import com.xczhihui.bxg.online.api.po.ExamineProgressVo;
import com.xczhihui.bxg.online.api.po.LiveExamineInfo;
import com.xczhihui.bxg.online.api.po.LiveExamineInfoVo;
import com.xczhihui.bxg.online.api.service.LiveExamineInfoService;


/**
 * @author liutao
 * @create 2017-09-08 14:22
 **/
@Service
public class LiveExamineInfoServiceImpl implements LiveExamineInfoService {

    @Autowired
    private SimpleHibernateDao simpleHibernateDao;
    
	@Value("${rate}")
	private Integer rate;
	
	@Value("${gift.im.room.postfix}")
	private String postfix;

    @Override
    public String add(LiveExamineInfo liveExamineInfo){


        Map<String,Object> param=new HashMap<>();
        String id=UUID.randomUUID().toString().replaceAll("-","");
        param.put("id",id);
        param.put("create_time",new Date());
        param.put("title",liveExamineInfo.getTitle());
        param.put("logo",liveExamineInfo.getLogo());
        param.put("content",liveExamineInfo.getContent());
        param.put("type",liveExamineInfo.getType());
        param.put("see_mode",liveExamineInfo.getSeeMode());
        param.put("start_time",liveExamineInfo.getStartTime());
        param.put("when_long",liveExamineInfo.getWhenLong());
        param.put("user_id",liveExamineInfo.getUserId());
        param.put("examine_status",0);
        param.put("is_free",liveExamineInfo.getIsFree());
        param.put("password",liveExamineInfo.getPassword());
        param.put("price",liveExamineInfo.getPrice());       
        //param.put("panda_coin",CountUtils.div(liveExamineInfo.getPrice(),rate,2));//熊猫币
        param.put("is_push_notice",liveExamineInfo.getPushNotice());
        param.put("is_send_phone_message",liveExamineInfo.getSendPhoneMessage());
 
        /*
         * 审核状态
         */
        liveExamineInfo.setExamineStatus("0");
        /**
         * 直播结束时间
         */
    	Long l= Long.parseLong(liveExamineInfo.getWhenLong()) +  liveExamineInfo.getStartTime().getTime();
		Date dend = new Date(l);
		param.put("end_time",dend);
        
        simpleHibernateDao.getNamedParameterJdbcTemplate().update("INSERT INTO live_examine_info ( id,create_time,title, logo,content, type, see_mode," +
                        " start_time, when_long, user_id, examine_status,is_free,password,price,is_push_notice,is_send_phone_message,end_time) VALUES (:id,:create_time,:title, :logo,:content, :type, :see_mode,:start_time, :when_long, :user_id,:examine_status,:is_free,:password,:price,:is_push_notice,:is_send_phone_message,:end_time)",param);
        return id;


    }

    @Override
    public List<LiveExamineInfoVo> liseByExamineStatus(String userId, String examineStatus, int pageNumber, int pageSize) {

            StringBuilder sb=new StringBuilder("SELECT c.id courseId,"
            		+ " c.live_status liveStatus,lei.create_time createTime,om.name type,"
            		+ "lei.examine_status examineStatus, lei.id, lei.logo, lei.title, "
            		+ "lei.see_mode seeMode, lei.price, lei.start_time startTime,"
            		+ "lei.end_time endTime, "
            		+ " if(c.direct_id is not null,(select start_time from oe_live_time_record where live_id = c.direct_id order by record_count limit 1),null) as tureTime,  "
            		+ "c.direct_id directId, IFNULL(( SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = c.id ), 0 ) + IFNULL(default_student_count, 0) learndCount "
            		+ "FROM live_examine_info lei  "
            		+ "left JOIN  oe_course c ON (lei.id = c.examine_id)"
            		+ "inner join oe_menu om on (om.id=lei.type)   WHERE lei.user_id = :userId  and  lei.is_delete = 0 ");
            if("0".equals(examineStatus)){ //待直播
                sb.append(" and lei.examine_status = 1  AND c.live_status != 3  and  c.is_delete=0 and c.status = 1 ");  
            }else if("2".equals(examineStatus)){ //直播完成
                sb.append(" AND c.live_status = 3  and  c.is_delete=0 and c.status = 1 ");
            }else if("1".equals(examineStatus)){ //审核中
                sb.append(" AND (lei.examine_status = 0 or lei.examine_status = 2)  ");
            }
            sb.append(" ORDER BY lei.start_time");
            Map<String,Object> param=new HashMap<>();
            param.put("userId",userId);
            
            
            //courseLecturVo.setImRoomId(courseLecturVo.getId()+postfix);
            List<LiveExamineInfoVo>  list= simpleHibernateDao.findPageBySQL(sb.toString(),param,LiveExamineInfoVo.class,pageNumber,pageSize).getItems();
            for (LiveExamineInfoVo liveExamineInfoVo : list) {
            	System.out.println(liveExamineInfoVo.getCourseId()+postfix);
            	liveExamineInfoVo.setImRoomId(liveExamineInfoVo.getCourseId()+postfix);
			}
            return  list;
    }

    @Override
    public int appealCount(String examineId) {

        String sql="select count(appeal_time) ct FROM live_appeal  where examine_id= ? ";
        return simpleHibernateDao.queryForInt(sql,examineId);

    }

    @Override
    public LiveExamineInfoVo get(String examineId) {

//    	 /**
//    	  * 查询下是审核
//    	  */
//    	 String sql = "select cid,name,code from ht_location where level = 2 and code = :code ";
//		 Map<String,Object> paramMap = new HashMap<String,Object>();
//		 paramMap.put("code",code);
//		 return dao.getNamedParameterJdbcTemplate().queryForMap(sql,paramMap);
    	
        String sql1="SELECT lei.content, lei.create_time createTime,"
        		+ " om.name type,lei.examine_status examineStatus, "
        		+ " lei.id, lei.logo, lei.title, lei.see_mode seeMode,"
        		+ " lei.price, lei.start_time startTime,lei.end_time endTime  FROM "
        		+ " live_examine_info lei  left JOIN "
        		+ "  oe_menu om on (om.id=lei.type) WHERE lei.id=:examineId   ";
        
        Map<String,Object> param1=new HashMap<>();
        param1.put("examineId",examineId);
        LiveExamineInfoVo liveExamineInfoVo =  simpleHibernateDao.getNamedParameterJdbcTemplate().queryForObject(sql1,param1,new BeanPropertyRowMapper<LiveExamineInfoVo>(LiveExamineInfoVo.class));
        return liveExamineInfoVo;
    }

    @Override
    public List<ProgressDto> appealList(String examineId) {

        String sql="select reviewer_time reviewerTime,against_reason againstReason,appeal_time appealTime from live_appeal la where examine_id=:examineId  order by reviewer_time  ";

        Map<String,Object> param=new HashMap<>();
        param.put("examineId",examineId);
        return simpleHibernateDao.getNamedParameterJdbcTemplate().query(sql,param,new BeanPropertyRowMapper<ProgressDto>(ProgressDto.class));
    }

    public Date addTwoSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, 2);
        return calendar.getTime();
    }
    @Override
    public ExamineProgressVo examineDetails(String examineId) {
        ExamineProgressVo examineProgressVo=new ExamineProgressVo();
       LiveExamineInfoVo liveExamineInfoVo= get(examineId);
        examineProgressVo.setLiveExamineInfoVo(liveExamineInfoVo);
        List<ProgressDto> dtoList=appealList(examineId);
        //if(dtoList.size()>0){
            List<ExamineProgressVo.Progress> progressList=new ArrayList<>();
            examineProgressVo.setProgressList(progressList);

            ExamineProgressVo.Progress progress1=new ExamineProgressVo().new Progress();
            progress1.setExaminTime(liveExamineInfoVo.getCreateTime());
            progress1.setName("您的直播申请已提交，我们将在24小时内完成审核");
            progressList.add(progress1);

            ExamineProgressVo.Progress progress2=new ExamineProgressVo().new Progress();
            progress2.setExaminTime(addTwoSecond(liveExamineInfoVo.getCreateTime()));
            progress2.setName("您提交的直播申请已在审核中，请等待");
            progressList.add(progress2);
            if(dtoList.size()>0)
            for (ProgressDto progressDto:dtoList){
                ExamineProgressVo.Progress progress3=new ExamineProgressVo().new Progress();
                if(progressDto.getAppealTime()!=null){
                    ExamineProgressVo.Progress progress4=new ExamineProgressVo().new Progress();
                    progress4.setName("您的直播申请被拒绝，原因:"+progressDto.getAgainstReason());
                    progress4.setExaminTime(progressDto.getReviewerTime());
                    if(appealCount(examineId)<1){
                        progress4.setShowAppeal(true);
                    }
                    progressList.add(progress4);



                    progress3.setName("您提起的申诉已在审核中，请等待");
                    progress3.setExaminTime(addTwoSecond(progressDto.getAppealTime()));
                    progressList.add(progress3);
                    continue;
                }else{
                progress3.setName("您的直播申请被拒绝，原因:"+progressDto.getAgainstReason());
                progress3.setExaminTime(progressDto.getReviewerTime());
                if(appealCount(examineId)<1){
                    progress3.setShowAppeal(true);
                }
                }
                progressList.add(progress3);
        }
        return examineProgressVo;
    }

    @Override
    public void appeal(String examineId,String content) {
            String sql="update live_appeal set appeal_time=now(),appeal_reason=:content where examine_id=:examineId ";

            Map<String,Object> param=new HashMap<>();
            param.put("content",content);
            param.put("examineId",examineId);
            simpleHibernateDao.getNamedParameterJdbcTemplate().update(sql,param);
            String sql2="update live_examine_info set examine_status=0 where id=:examineId ";
            Map<String,Object> param2=new HashMap<>();
            param2.put("examineId",examineId);
            simpleHibernateDao.getNamedParameterJdbcTemplate().update(sql2,param2);
    }

    @Override
    public int getPreLiveCount(String userId) {

        String sql="SELECT count(*) FROM live_examine_info lei "
        		+ "LEFT JOIN oe_course c ON (lei.id = c.examine_id) "
        		+ "INNER JOIN oe_menu om ON (om.id = lei.type) "
        		+ "  WHERE lei.user_id =:userId AND lei.examine_status = 1 AND c.live_status != 3 and c.is_delete=0 and c.status = 1  and lei.is_delete = 0 ";
        Map<String,Object> param=new HashMap<>();
        param.put("userId",userId);
        return simpleHibernateDao.getNamedParameterJdbcTemplate().queryForObject(sql,param,Integer.class);
    }
}
