package com.xczhihui.bxg.online.web.service.impl;/**
 * Created by admin on 2016/8/31.
 */

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Apply;
import com.xczhihui.bxg.online.common.domain.ApplyGradeCourse;
import com.xczhihui.bxg.online.common.domain.Grade;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.dao.ApplyDao;
import com.xczhihui.bxg.online.web.dao.ApplyGradeCourseDao;
import com.xczhihui.bxg.online.web.dao.CourseDao;
import com.xczhihui.bxg.online.web.service.ApplyService;
import com.xczhihui.bxg.online.web.service.GradeService;
import com.xczhihui.bxg.online.web.vo.CourseApplyVo;

/**
 * 学员信息业务层接口实现类
 *
 * @author 康荣彩
 * @create 2016-08-31 12:56
 */
@Service
public class ApplyServiceImpl extends OnlineBaseServiceImpl implements ApplyService {


    @Autowired
    private ApplyDao  applyDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private ApplyGradeCourseDao applyGradeCourseDao;
    
    @Value("${online.web.url}")
	private String weburl;
    
    /**
     *  判断当前登陆用户是否已经报名了
     *  报名成功，不向学员表添加数据(apply表),只要学员班级课程中间表添加数据
     *  未报名,向学员表添加数据,并向学员班级课程表中添加一条数据
     *
     */
    public String saveApply(OnlineUser onlineUser, Integer courseId, Integer gradeId) {
        //判断当前用户是否报名
        boolean  applyStatus= onlineUser.isApply();
        //获取用户要报名的课程
        CourseApplyVo course= courseDao.getCourseApplyByCourseId(courseId);
        //如果课程是免费的,那么支付状态就是免费;否则是已付款
        String status=course.isFree() ? "0" : "2";

        //查询一下当前用户的学员信息
        Apply apply=applyDao.findByUserId(onlineUser.getId());
        //生成学员号
        String studentNumber = applyDao.createStudentNumber(courseId, gradeId);
        if(apply==null)
        {
            if(!applyStatus) { //未报名
               apply=new Apply();
               apply.setStudentNumber(studentNumber);
               apply.setCreateTime(new Date());
               apply.setCreatePerson(onlineUser.getName());
               apply.setDelete(false);
               //向学员表里面插入一条数据
               apply.setUserId(onlineUser.getId());
               applyDao.save(apply);

           }
        }
        if(apply != null){
            ApplyGradeCourse agc= applyGradeCourseDao.findInfoByID(onlineUser.getId(), courseId, gradeId);
            if(agc == null ){
                //向中间表插入数据
                agc = new ApplyGradeCourse();
                agc.setApplyId(apply.getId());
                agc.setStudentNumber(studentNumber);
                agc.setCourseId(courseId);
                agc.setGradeId(gradeId);
                agc.setIsPayment(status);
                agc.setDelete(false);
                agc.setCreateTime(new Date());
                agc.setCost(Double.valueOf(course.getCurrentPrice()));
                agc.setCreatePerson(onlineUser.getName());
                applyGradeCourseDao.save(agc);
            }
        }

        //当课程是付费课程时，将班级中的剩余席位改变
        if (status.equals("2")){
            Grade grade=gradeService.findGradeById(gradeId);
            if(grade != null){
                int count =0;
                if(grade.getStudentCount()==null){
                    count=1;
                }else{
                    count=grade.getStudentCount()+1;
                }
                grade.setStudentCount(count);
                gradeService.update(grade);
            }
        }
        //修改用户报名状态
        return "报名成功!";
    }
	@Override
	public synchronized boolean updateUserOrcheckUser(String realName, String IDNumber,String lot_no,OnlineUser user) throws Exception {
		return false;
	}

    /**
     * 将用户修改为老学员
     */
    public void updateUserToOldUser(OnlineUser user,String real_name, String id_card_no,String lot_no ){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("user_id",user.getId());
        paramMap.put("id_card_no",id_card_no);
        paramMap.put("real_name",real_name);
        paramMap.put("login_name",user.getLoginName());
        paramMap.put("old_user_class_name",user.getOldUserClassName());
        paramMap.put("old_user_subject_id",user.getOldUserSubjectId());
        //查看用户课程报名信息是否已存在
        String sql="select is_old_user from oe_apply  where  user_id=:user_id";
        List< Map<String, Object>> applys= applyDao.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
        if(applys.size() <= 0 ){ //添加报名信息
            paramMap.put("id",UUID.randomUUID().toString().replace("-", ""));
            paramMap.put("create_time",new Date());
            paramMap.put("is_delete",0);
            paramMap.put("create_person",user.getLoginName());
            if(user.getLoginName().indexOf("@") !=-1 ){
                paramMap.put("email",user.getLoginName());
            }else{
                paramMap.put("mobile",user.getLoginName());
            }
            paramMap.put("real_name",real_name);
            paramMap.put("is_old_user",1);
             sql=" insert into oe_apply (id,user_id,create_time,is_delete,create_person,real_name,id_card_no,"
             		+ "is_old_user,old_user_subject_id,old_user_class_name) " +
                 " values (:id,:user_id,:create_time,:is_delete,:create_person,:real_name,:id_card_no,"
                 	+ ":is_old_user,:old_user_subject_id,:old_user_class_name)";
             applyDao.getNamedParameterJdbcTemplate().update(sql,paramMap);
        }else{ //修改用户报名信息
            String appendSql="";
            if(user.getLoginName().indexOf("@") !=-1 ){
                appendSql=",email=:login_name ";
            }else{
                appendSql=",mobile=:login_name ";
            }
            sql = "update oe_apply  set is_old_user=1,id_card_no=:id_card_no,real_name=:real_name,"
            		+ "old_user_subject_id=:old_user_subject_id,old_user_class_name=:old_user_class_name"
            			+appendSql+"  where user_id=:user_id";
            if(applys.get(0).get("is_old_user").toString().equals("0")) {
                applyDao.getNamedParameterJdbcTemplate().update(sql,paramMap);
            }
        }
        
        //发送优惠码
        List<Map<String, Object>> acts = applyDao.getNamedParameterJdbcTemplate().getJdbcOperations()
        		.queryForList("select t1.* from oe_fcode_activity t1,oe_fcode_lot t2 where t1.lot_no=t2.lot_no "
        				+ " and t2.lot_no=? and t1.start_time < now() and t2.expiry_time > now() ",lot_no);
        //有活动
        if (acts.size() > 0) {
        	String fcode = null;
        	Map<String, Object> act = acts.get(0);
        	String subjectIds = act.get("subject_ids").toString();
        	if (Arrays.asList(subjectIds.split(",")).contains(user.getOldUserSubjectId())) {
        		if (Integer.valueOf(act.get("auto").toString()) == 1) {
        			String id = UUID.randomUUID().toString().replace("-", "");
        			String code = UUID.randomUUID().toString().replace("-", "");
        			applyDao.getNamedParameterJdbcTemplate().getJdbcOperations()
        			.update("insert into oe_fcode (id,lot_no,fcode,`status`) "
        					+ " values ('"+id+"','"+lot_no+"','"+code+"',1)");
        			applyDao.getNamedParameterJdbcTemplate().getJdbcOperations()
        			.update("update oe_fcode_activity set fcode_sum=(fcode_sum+1) where lot_no='"+lot_no+"' ");
        			fcode = code;
        		} else {
        			List<Map<String, Object>> cos = applyDao.getNamedParameterJdbcTemplate().getJdbcOperations()
        					.queryForList("select * from oe_fcode o where o.lot_no=? and o.`status`=0",lot_no);
        			if (cos.size() > 0) {
        				fcode = cos.get(0).get("fcode").toString();
        				applyDao.getNamedParameterJdbcTemplate().getJdbcOperations()
            			.update("update oe_fcode set `status`=1 where fcode=?",fcode);
					}
        		}
        		String msg_id = UUID.randomUUID().toString().replaceAll("-", ""); //最新消息的id
				String msg_link = weburl+"/web/html/myStudyCenter.html?location=fcode";
				String content = "亲爱的老学员，快去加QQ群549557985，赠你1个课程兑换码"+fcode+"，<a href=\"javascript:void(0)\" onclick=\"on_click_msg(\\'" + msg_id + "\\',\\'" + msg_link + "\\');\"><p>立即兑换>></p></a>";
				sql = "insert into oe_message (id,user_id,context,type,status,create_person ) values "
		                   + "('"+msg_id+"','"+user.getId()+"','"+content+"',1,1,'"+user.getId()+"')";
		        applyDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql);
			}
		}
    }
    
    /*
    public static void main(String[] args) {
    	//从双元验证
		Map<String, String> map = new HashMap<String, String>();
		map.put("realName", "姚文江");
		map.put("IDNumber", "372328199307190013");
		map.put("loginName", "18234078021");
		map.put("unixTime", System.currentTimeMillis()/1000 + "");
		String sign = CodeUtil.calSignature(map, "dc3aa06f3b1c4857a75609bd3cc321fd");
		
		String url = "http://tlias.ixincheng.com/remote/student/jdugeIfExists";
		url+="?realName=姚文江&IDNumber=372328199307190013&loginName=18234078021&unixTime="+map.get("unixTime")
		+"&sign="+sign;
		
		String msg = HttpUtil.sendGetRequest(url);
		Gson gson = new GsonBuilder().create();
		Map<String, Object> msgmap = gson.fromJson(msg, Map.class);
		if (Boolean.valueOf(msgmap.get("success").toString())) {
			Map<String, Object> r = (Map<String, Object>)msgmap.get("resultObject");
			System.out.println(r.get("className").toString());
			System.out.println(r.get("subjectId").toString());
		}
	}
	*/
    
    @Override
    public Apply getUserApply(String userId){
    	Apply apply=applyDao.findByUserId(userId);
    	return apply;
    }
    
	/** 
	 * Description：保存用户信息
	 * @param apply
	 * @param onlineUser
	 * @return
	 * @return String
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public void saveApply(Apply apply, OnlineUser onlineUser) {
		// 查询一下当前用户的学员信息
		Apply a = applyDao.findByUserId(onlineUser.getId());
		if (a == null) {
			apply.setCreateTime(new Date());
			apply.setCreatePerson(onlineUser.getName());
			apply.setDelete(false);
			// 向学员表里面插入一条数据
			apply.setUserId(onlineUser.getId());
			apply.setIsOldUser(0);
			applyDao.save(apply);
		}else{
			a.setIsOldUser(0);
			a.setSex(apply.getSex());
			a.setIsFirst(apply.getIsFirst());
			if(apply.getRealName()!=null && !apply.getRealName().equals("")){
				a.setRealName(apply.getRealName());
			}
			if(apply.getMobile()!=null && !apply.getMobile().equals("")){
				a.setMobile(apply.getMobile());
			}
			if(apply.getIdCardNo()!=null && !apply.getIdCardNo().equals("")){
				a.setIdCardNo(apply.getIdCardNo());
			}
			if(apply.getWechatNo()!=null && !apply.getWechatNo().equals("")){
				a.setWechatNo(apply.getWechatNo());
			}
			if(apply.getEmail()!=null && !apply.getEmail().equals("")){
				a.setEmail(apply.getEmail());
			}
			if(apply.getQq()!=null && !apply.getQq().equals("")){
				a.setQq(apply.getQq());
			}
			if(apply.getReferee()!=null && !apply.getReferee().equals("")){
				a.setReferee(apply.getReferee());
			}
			if(apply.getOccupation()!=null && !apply.getOccupation().equals("")){
				a.setOccupation(apply.getOccupation());
			}
			applyDao.save(a);
		}
	}
}
