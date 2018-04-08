package com.xczhihui.course.dao;

import java.util.HashMap;
import java.util.Map;

import com.xczhihui.course.vo.ApplyVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Apply;
import com.xczhihui.common.dao.HibernateDao;

/**
 * 云课堂课程管理DAO
 *
 * @author yxd
 */
@Repository("applyDao")
public class ApplyDao extends HibernateDao<Apply> {
    public Page<ApplyVo> findApplyPage(ApplyVo vo, int pageNumber, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("gradeId", vo.getGradeId());
        StringBuilder sql = new StringBuilder();
        sql.append(" select * from ( ");
        sql.append("  select oa.real_name as realName,oa.mobile,oa.email,oa.qq,oe.login_name as loginName , ");
        sql.append(" (select s.name from school s where oa.school_id=s.id) as schoolName, ");
        sql.append(" (select sv.name from system_variate sv where sv.value=oa.education_id and sv.parent_id='c4d7e0efwerwtsf234f94de432e342d' and sv.is_delete=0 ) as educationName, ");
        sql.append(" (select sv.name from system_variate sv where sv.value=oa.major_id and sv.parent_id =(select id from system_variate where value = 'major')) as majorName, ");
        sql.append(" (select max(is_payment) from apply_r_grade_course argc where argc.grade_id=:gradeId and argc.apply_id=oa.id and argc.is_delete=0 ) as isPayment,  ");
        sql.append(" (select id from apply_r_grade_course argc where argc.grade_id=:gradeId and argc.apply_id=oa.id  and argc.is_delete=0) as id,  ");
        sql.append("(select ifnull(cost,0) from apply_r_grade_course argc where argc.grade_id=:gradeId and argc.apply_id=oa.id  and argc.is_delete=0) as cost, ");
        sql.append(" (select student_number from apply_r_grade_course argc where argc.grade_id=:gradeId and argc.apply_id=oa.id ) as studentNumber,oa.id as applyId   ");
        sql.append("  from oe_apply oa left join oe_user oe on oa.user_id=oe.id where oa.id in (select apply_id from apply_r_grade_course where grade_id=:gradeId and is_delete=0) and oa.is_delete=0 ");
        sql.append(" ) ds where 1=1 ");
        if (StringUtils.isNotEmpty(vo.getRealName())) {
            sql.append(" and (ds.realName like :realName or ds.mobile like :mobile or ds.studentNumber like :studentNumber or ds.loginName like :loginName )");
            paramMap.put("realName", "%" + vo.getRealName() + "%");
            paramMap.put("mobile", "%" + vo.getRealName() + "%");
            paramMap.put("studentNumber", "%" + vo.getRealName() + "%");
            paramMap.put("loginName", "%" + vo.getRealName() + "%");
        }
//        if (StringUtils.isNotEmpty(vo.getIsPayment()) && !"-1".equals(vo.getIsPayment())) {
//            if ("0".equals(vo.getIsPayment())) {
//                vo.setIsPayment("2");
//            }
//            sql.append(" and ds.isPayment=:isPayment");
//            paramMap.put("isPayment", vo.getIsPayment());
//        }
        return this.findPageBySQL(sql.toString(), paramMap, ApplyVo.class, pageNumber, pageSize);
    }

    public void deleteStudents(String[] _ids) {
    	
        if (_ids.length > 0) {
            StringBuilder sql = new StringBuilder(" delete from apply_r_grade_course  where  id IN (");
            
       //     StringBuilder findByIdSql = new StringBuilder(" select apply_id as id from apply_r_grade_course  where  id IN (");
            
          //  Map<String, Object> paramMap =new  HashMap<String, Object>();
            for (int i = 0; i < _ids.length; i++) {
                if (i != 0){
                	sql.append(",");
                	//findByIdSql.append(",");
                }
                    
                sql.append("'" + _ids[i] + "'");
                
                
              //  findByIdSql.append("'" + _ids[i] + "'");
                
            }
            sql.append(")");
       /*     findByIdSql.append(")");
            List<Apply>  temps= this.findEntitiesByJdbc(Apply.class, findByIdSql.toString(), paramMap);
            for(Apply apply :temps){
            	Apply applyById = this.findOneEntitiyByProperty(Apply.class, "id", apply.getId());
            	OnlineUser OnlineById = this.findOneEntitiyByProperty(OnlineUser.class, "id",applyById.getUserId());
            	OnlineById.setIsApply(false);
            	this.update(OnlineById);
         	   this.delete(apply.getId(), entityClass);
            }*/
            this.getNamedParameterJdbcTemplate().update(sql.toString(), new HashMap<String, Object>());
           
        }
    }

    public void changePayment(ApplyVo vo)
    {
        String sql=" update apply_r_grade_course set ";
    }
}
