package com.xczhihui.bxg.online.manager.operate.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.ask.vo.AccuseVo;
import com.xczhihui.bxg.online.manager.operate.vo.MobileBannerVo;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class MobileBannerDao extends SimpleHibernateDao {

	public Page<MobileBannerVo> findMobileBannerPage(MobileBannerVo mobileBannerVo, Integer pageNumber, Integer pageSize){
		   Map<String,Object> paramMap=new HashMap<String,Object>();
		   StringBuilder sql=new StringBuilder(" SELECT " +
											   "	t.id, " +
											   "	t.name, " +
											   "	t.url, " +
											   "	t.click_sum, " +
											   "	t.create_person, " +
											   "	t.create_time, " +
											   "	t.`status`, " +
											   "	t.seq, " +
											   "	t.img_path, " +
											   "	t2.name createPersonName " +
											   " FROM " +
											   "	oe_course_mobile_banner t, " +
											   "	user t2 " +
											   " where t.create_person = t2.login_name ");
		   
		   if(mobileBannerVo.getName() != null && !"".equals(mobileBannerVo.getName())){
			   sql.append(" and t.name like :name ");
			   paramMap.put("name", "%" + mobileBannerVo.getName() + "%");
		   }
		   
		   if(mobileBannerVo.getStatus() != null && !"".equals(mobileBannerVo.getStatus())){
			   sql.append(" and t.status = :status ");
			   paramMap.put("status", mobileBannerVo.getStatus());
		   }
		   
		   if(mobileBannerVo.getBannerType() != null && !"".equals(mobileBannerVo.getBannerType())){
			   sql.append(" and t.banner_type = :bannerType ");
			   paramMap.put("bannerType", mobileBannerVo.getBannerType());
		   }
		   sql.append(" order by t.status desc,t.seq desc ");
		   
		   System.out.println("mobile:"+sql.toString());
		   
		   Page<MobileBannerVo> ms = this.findPageBySQL(sql.toString(), paramMap, MobileBannerVo.class, pageNumber, pageSize);
      	   return ms;
	}
}

