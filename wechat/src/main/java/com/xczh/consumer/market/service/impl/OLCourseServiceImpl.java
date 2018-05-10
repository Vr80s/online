package com.xczh.consumer.market.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.dao.OLCourseMapper;
import com.xczh.consumer.market.service.OLCourseServiceI;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.MenuVo;
import com.xczhihui.course.model.OfflineCity;
import com.xczhihui.course.service.IOfflineCityService;

@Service
public class OLCourseServiceImpl implements OLCourseServiceI {

	@Autowired
	private OLCourseMapper wxcpCourseDao;
	
	@Autowired
	private IOfflineCityService offlineCityService;
	

	
	/***
	 * 直播搜索页面的接口调整
	 * @param
	 * @param
	 * @return
	 * @throws SQLException
	 */
	@Override
    public List<CourseLecturVo> recommendCourseList( List<MenuVo> listmv) throws SQLException{
		
		
		//学习人数、当前价格、课程类型、课程图片、讲师名、课程名字
		
		StringBuffer all = new StringBuffer("");
		all.append(" ( select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,oc.city as city,"
				+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,");
		all.append(" if(oc.live_status=1,DATE_FORMAT(oc.start_time,'%H:%i'),");
		all.append("if(oc.live_status=2,if(oc.start_time <= DATE_ADD(DATE_ADD(str_to_date(DATE_FORMAT(NOW(),'%Y-%m-%d'),'%Y-%m-%d %H:%i:%s'),INTERVAL 1 DAY),INTERVAL -1 SECOND) ,");
		all.append("DATE_FORMAT(oc.start_time,'%H:%i'),DATE_FORMAT(oc.start_time,'%m.%d')),DATE_FORMAT(oc.start_time,'%m.%d') )) as startDateStr,");
		all.append(" if(oc.type =3,4,IF(oc.type =1,3,if(oc.multimedia_type=1,1,2))) as type, ");    		//课程类型
		all.append(" if(oc.live_status = 2,if(DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now()<oc.start_time,4,");
		all.append(" if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now()<oc.start_time,5,oc.live_status)),oc.live_status) as  lineState ,");
		all.append(" if(oc.is_free =0,0,1) as watchState, ");	
		all.append(" oc.collection as collection, ");
		all.append(" if(oc.sort_update_time< now(),0,oc.recommend_sort) recommendSort, ");

		all.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
				+ "+IFNULL(oc.default_student_count, 0) learndCount,");								//学习人数
		all.append(" oc.start_time as startTime, ");
		all.append(" '精品课程' as note ");

		all.append(" from oe_course oc ,oe_user ou ");
		all.append(" where  oc.user_lecturer_id = ou.id and oc.is_delete=0 and oc.status=1 order by recommendSort desc,oc.release_time desc  limit 0,6)");


		all.append("  union all ");
		
		all.append(" ( select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,oc.city as city,"
				+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,");
		all.append(" if(oc.live_status=1,DATE_FORMAT(oc.start_time,'%H:%i'),");
		all.append("if(oc.live_status=2,if(oc.start_time <= DATE_ADD(DATE_ADD(str_to_date(DATE_FORMAT(NOW(),'%Y-%m-%d'),'%Y-%m-%d %H:%i:%s'),INTERVAL 1 DAY),INTERVAL -1 SECOND) ,");
		all.append("DATE_FORMAT(oc.start_time,'%H:%i'),DATE_FORMAT(oc.start_time,'%m.%d')),DATE_FORMAT(oc.start_time,'%m.%d') )) as startDateStr,");
		all.append(" if(oc.type =3,4,IF(oc.type =1,3,if(oc.multimedia_type=1,1,2))) as type, ");    		//课程类型
		all.append(" if(oc.live_status = 2,if(DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now()<oc.start_time,4,");
		all.append(" if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now()<oc.start_time,5,oc.live_status)),oc.live_status) as  lineState ,");
		all.append(" if(oc.is_free =0,0,1) as watchState, ");
		all.append(" oc.collection as collection, ");
		all.append(" if(oc.sort_update_time< now(),0,oc.recommend_sort) recommendSort, ");
		
		
		all.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
				+ "+IFNULL(oc.default_student_count, 0) learndCount,");								//学习人数
		all.append(" oc.start_time as startTime, ");
		all.append(" '最新课程' as note ");
		
		all.append(" from oe_course oc ,oe_user ou ");
		all.append(" where  oc.user_lecturer_id = ou.id and  oc.is_delete=0 and oc.status=1  order by  oc.release_time desc limit 0,6)");
		
		
		all.append("  union all ");
		
		
		int i = 0;
		for (MenuVo menuVo : listmv) {
			i++;
			all.append(" ( select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,oc.city as city,"
					+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,");
			all.append(" if(oc.live_status=1,DATE_FORMAT(oc.start_time,'%H:%i'),");
			all.append("if(oc.live_status=2,if(oc.start_time <= DATE_ADD(DATE_ADD(str_to_date(DATE_FORMAT(NOW(),'%Y-%m-%d'),'%Y-%m-%d %H:%i:%s'),INTERVAL 1 DAY),INTERVAL -1 SECOND) ,");
			all.append("DATE_FORMAT(oc.start_time,'%H:%i'),DATE_FORMAT(oc.start_time,'%m.%d')),DATE_FORMAT(oc.start_time,'%m.%d') )) as startDateStr,");
			all.append(" if(oc.type =3,4,IF(oc.type =1,3,if(oc.multimedia_type=1,1,2))) as type, ");    		//课程类型
			all.append(" if(oc.live_status = 2,if(DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now()<oc.start_time,4,");
			all.append(" if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now()<oc.start_time,5,oc.live_status)),oc.live_status) as  lineState ,");
			all.append(" if(oc.is_free =0,0,1) as watchState, ");
			all.append(" oc.collection as collection, ");
			all.append(" if(oc.sort_update_time< now(),0,oc.recommend_sort) recommendSort, ");
			
			all.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
					+ "+IFNULL(oc.default_student_count, 0) learndCount,");								//学习人数
			all.append(" oc.start_time as startTime, ");
			all.append(" om.name as note ");
			all.append(" from oe_course oc, oe_menu om  ,oe_user ou ");
			all.append(" where  oc.user_lecturer_id = ou.id and om.id = oc.menu_id	and oc.is_delete=0 and oc.status=1 ");
			all.append(" and om.id  = "+menuVo.getId());
			all.append("  order by recommendSort desc,oc.release_time desc limit 0,4 ) ");
			
			if(i < listmv.size()){
				all.append("  union all ");
			}
		}
		System.out.println(all.toString());
		
		return wxcpCourseDao.query(JdbcUtil.getCurrentConnection(),all.toString(),new BeanListHandler<>(CourseLecturVo.class));
	}

	@Override
	public List<CourseLecturVo> queryAllCourse(String menuType,Integer lineState,
			Integer courseType, String isFree,String city, String queryKey,
			Integer pageNumber, Integer pageSize) throws SQLException {

	    pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 100000 : pageSize;
        StringBuffer  commonSql =new StringBuffer();
        //如果为模糊查询，排序规则为，课程名>分类>讲师名>课程,讲师简介
        if(org.apache.commons.lang.StringUtils.isNotBlank(queryKey)){
			commonSql.append("select * from ((");
		}
		commonSql.append(" select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,"
				+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,");
        commonSql.append(" if(oc.live_status=1,DATE_FORMAT(oc.start_time,'%H:%i'),");
		commonSql.append("if(oc.live_status=2,if(oc.start_time <= DATE_ADD(DATE_ADD(str_to_date(DATE_FORMAT(NOW(),'%Y-%m-%d'),'%Y-%m-%d %H:%i:%s'),INTERVAL 1 DAY),INTERVAL -1 SECOND) ,");
        commonSql.append("DATE_FORMAT(oc.start_time,'%H:%i'),DATE_FORMAT(oc.start_time,'%m.%d')),DATE_FORMAT(oc.start_time,'%m.%d') )) as startDateStr,");
        commonSql.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
				+ "+IFNULL(oc.default_student_count, 0) learndCount, ");
		commonSql.append(" if(oc.is_free =0,0,1) as watchState, ");//是否免费
		commonSql.append(" oc.collection as collection, ");
		commonSql.append(" if(oc.live_status = 2,if((DATE_SUB(now(),INTERVAL 30 MINUTE) < oc.start_time and now() > oc.start_time ) or ");
		commonSql.append(" (DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now() < oc.start_time ),4, ");
		commonSql.append(" if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now() < oc.start_time,5,oc.live_status)),oc.live_status) as lineState,");
		commonSql.append(" oc.city as city, ");//是否免费
		commonSql.append(" 1 as querySort, ");//排序
		commonSql.append(" oc.release_time, ");//上架/下架时间
		commonSql.append(" if(oc.sort_update_time< now(),0,oc.recommend_sort) recommendSort, ");//推荐值
		//课程类型     音频、视频、直播、线下培训班   1 2 3 4
		commonSql.append(" if(oc.type =3,4,IF(oc.type = 1,3,if(oc.multimedia_type=1,1,2))) as type, ");
		commonSql.append(" oc.create_time,oc.is_recommend,oc.start_time as startTime,");
		commonSql.append(" ABS(timestampdiff(second,current_timestamp,oc.start_time)) as recent ");
		commonSql.append(" from oe_course oc,oe_menu as om ,oe_user ou ");
		commonSql.append(" where  oc.user_lecturer_id = ou.id and om.id = oc.menu_id  and "
				+ " oc.is_delete=0 and oc.status = 1   ");
		if(org.apache.commons.lang.StringUtils.isNotBlank(city)){
			if(city.equals("其他")){
				Page<OfflineCity> OfflineCityPage = new Page<>();
				OfflineCityPage.setCurrent(1);
				OfflineCityPage.setSize(5);
				List<OfflineCity> oclist = offlineCityService.selectOfflineCityPage(OfflineCityPage).getRecords();
				String citylist = " (";
				for(OfflineCity c : oclist){
					citylist+="'"+c.getCityName()+"',";
				}
				citylist = citylist.substring(0,citylist.length()-1);
				citylist+=") ";
				commonSql.append(" and oc.type =3 ");
				commonSql.append(" and oc.city not in "+citylist+"");
			}else if(city.equals("全国课程")){
				commonSql.append(" and oc.type =3 ");
			}else{
				commonSql.append(" and oc.city= '"+city+"'");
				commonSql.append(" and oc.type =3 ");
			}
		}

		if(org.apache.commons.lang.StringUtils.isNotBlank(isFree)){
			commonSql.append(" and oc.is_free = '"+isFree+"'");
		}

		/**
		 * 直播中的状态 4:直播课程
		 */
		if(lineState!=null&&lineState!=1234){
			commonSql.append(" and oc.live_status = '"+lineState+"'");
		}
		if(lineState!=null&&lineState==1234){
			commonSql.append(" and oc.type = 1 ");
		}
		/**
		 * 目前检索的是讲师名和课程id
		 */
		if(org.apache.commons.lang.StringUtils.isNotBlank(queryKey)){
			commonSql.append(" and ");
			commonSql.append(" oc.grade_name like '%"+ queryKey + "%' ");
		}
		if(courseType!=null) {
			if (courseType == 1 || courseType == 2) { //视频或者音频
				commonSql.append(" and oc.multimedia_type = '" + courseType + "'");  //多媒体类型1视频2音频
			} else if (courseType == 3 || courseType == 4) { //直播  或者线下课程
				commonSql.append(" and " + (courseType == 3 ? " oc.type=1 " : " oc.type =3 "));
			}
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(menuType)&&
				!menuType.equals("goodCourse")&&!menuType.equals("newCourse")){
			commonSql.append(" AND oc.menu_id = '"+menuType+"' ");
		}
		//判断是否有模糊查询
		if(org.apache.commons.lang.StringUtils.isBlank(queryKey)){
			if(org.apache.commons.lang.StringUtils.isNotBlank(menuType)){
				//精品课程排序
				if(menuType.equals("goodCourse")){
					commonSql.append("  order by  recommendSort desc ");
					//最新课程排序
				}else if(menuType.equals("newCourse")){
					commonSql.append(" order by  oc.release_time desc ");
				}else{
					commonSql.append("  order by  recommendSort desc,oc.release_time desc ");
				}
			}else if(org.apache.commons.lang.StringUtils.isBlank(menuType)&&courseType!=null&&courseType==3){
				commonSql.append("  order by  recommendSort desc,recent ");
			}else if(org.apache.commons.lang.StringUtils.isBlank(menuType)&&courseType!=null&&courseType==4){
				commonSql.append("  order by  recommendSort desc,oc.start_time desc ");
			}else{
				commonSql.append("  order by  recommendSort desc,oc.release_time desc ");
			}
		}else{
			commonSql.append(") ");
			commonSql.append(" union ");

			commonSql.append(" (select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,"
					+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,");
			commonSql.append(" if(oc.live_status=1,DATE_FORMAT(oc.start_time,'%H:%i'),");
			commonSql.append("if(oc.live_status=2,if(oc.start_time <= DATE_ADD(DATE_ADD(str_to_date(DATE_FORMAT(NOW(),'%Y-%m-%d'),'%Y-%m-%d %H:%i:%s'),INTERVAL 1 DAY),INTERVAL -1 SECOND) ,");
			commonSql.append("DATE_FORMAT(oc.start_time,'%H:%i'),DATE_FORMAT(oc.start_time,'%m.%d')),DATE_FORMAT(oc.start_time,'%m.%d') )) as startDateStr,");
			commonSql.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
					+ "+IFNULL(oc.default_student_count, 0) learndCount, ");
			commonSql.append(" if(oc.is_free =0,0,1) as watchState, ");//是否免费
			commonSql.append(" oc.collection as collection, ");
			commonSql.append(" if(oc.live_status = 2,if((DATE_SUB(now(),INTERVAL 30 MINUTE) < oc.start_time and now() > oc.start_time ) or ");
			commonSql.append(" (DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now() < oc.start_time ),4, ");
			commonSql.append(" if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now() < oc.start_time,5,oc.live_status)),oc.live_status) as lineState,");
			commonSql.append(" oc.city as city, ");//是否免费
			commonSql.append(" 2 as querySort, ");//排序
			commonSql.append(" oc.release_time, ");//上架/下架时间
			commonSql.append(" if(oc.sort_update_time< now(),0,oc.recommend_sort) recommendSort, ");//推荐值
			//课程类型     音频、视频、直播、线下培训班   1 2 3 4
			commonSql.append(" if(oc.type =3,4,IF(oc.type = 1,3,if(oc.multimedia_type=1,1,2))) as type, ");
			commonSql.append(" oc.create_time,oc.is_recommend,oc.start_time as startTime,");
			commonSql.append(" ABS(timestampdiff(second,current_timestamp,oc.start_time)) as recent ");

			commonSql.append(" from oe_course oc,oe_menu as om ,oe_user ou ");
			commonSql.append(" where  oc.user_lecturer_id = ou.id and om.id = oc.menu_id  and "
					+ " oc.is_delete=0 and oc.status = 1   ");
			if(org.apache.commons.lang.StringUtils.isNotBlank(city)){
				if(city.equals("其他")){
					Page<OfflineCity> OfflineCityPage = new Page<>();
					OfflineCityPage.setCurrent(1);
					OfflineCityPage.setSize(5);
					List<OfflineCity> oclist = offlineCityService.selectOfflineCityPage(OfflineCityPage).getRecords();
					String citylist = " (";
					for(OfflineCity c : oclist){
						citylist+="'"+c.getCityName()+"',";
					}
					citylist = citylist.substring(0,citylist.length()-1);
					citylist+=") ";
					commonSql.append(" and oc.type =3 ");
					commonSql.append(" and oc.city not in "+citylist+"");
				}else if(city.equals("全国课程")){
					commonSql.append(" and oc.type =3 ");
				}else{
					commonSql.append(" and oc.city= '"+city+"'");
					commonSql.append(" and oc.type =3 ");
				}
			}

			if(org.apache.commons.lang.StringUtils.isNotBlank(isFree)){
				commonSql.append(" and oc.is_free = '"+isFree+"'");
			}

			/**
			 * 直播中的状态 4:直播课程
			 */
            if(lineState!=null&&lineState!=1234){
                commonSql.append(" and oc.live_status = '"+lineState+"'");
            }
            if(lineState!=null&&lineState==1234){
                commonSql.append(" and oc.type = 1 ");
            }
			/**
			 * 目前检索的是讲师名和课程id
			 */
			if(org.apache.commons.lang.StringUtils.isNotBlank(queryKey)){
				commonSql.append(" and ");
				commonSql.append(" oc.menu_id in (select id from oe_menu  where name like '%"+ queryKey + "%')  ");
			}
			if(courseType!=null) {
				if (courseType == 1 || courseType == 2) { //视频或者音频
					commonSql.append(" and oc.multimedia_type = '" + courseType + "'");  //多媒体类型1视频2音频
				} else if (courseType == 3 || courseType == 4) { //直播  或者线下课程
					commonSql.append(" and " + (courseType == 3 ? " oc.type=1 " : " oc.type =3 "));
				}
			}
			if(org.apache.commons.lang.StringUtils.isNotBlank(menuType)&&
					!menuType.equals("goodCourse")&&!menuType.equals("newCourse")){
				commonSql.append(" AND oc.menu_id = '"+menuType+"' ");
			}
			commonSql.append(") ");

			commonSql.append(" union ");

			commonSql.append(" (select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,"
					+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,");
			commonSql.append(" if(oc.live_status=1,DATE_FORMAT(oc.start_time,'%H:%i'),");
			commonSql.append("if(oc.live_status=2,if(oc.start_time <= DATE_ADD(DATE_ADD(str_to_date(DATE_FORMAT(NOW(),'%Y-%m-%d'),'%Y-%m-%d %H:%i:%s'),INTERVAL 1 DAY),INTERVAL -1 SECOND) ,");
			commonSql.append("DATE_FORMAT(oc.start_time,'%H:%i'),DATE_FORMAT(oc.start_time,'%m.%d')),DATE_FORMAT(oc.start_time,'%m.%d') )) as startDateStr,");
			commonSql.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
					+ "+IFNULL(oc.default_student_count, 0) learndCount, ");
			commonSql.append(" if(oc.is_free =0,0,1) as watchState, ");//是否免费
			commonSql.append(" oc.collection as collection, ");
			commonSql.append(" if(oc.live_status = 2,if((DATE_SUB(now(),INTERVAL 30 MINUTE) < oc.start_time and now() > oc.start_time ) or ");
			commonSql.append(" (DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now() < oc.start_time ),4, ");
			commonSql.append(" if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now() < oc.start_time,5,oc.live_status)),oc.live_status) as lineState,");
			commonSql.append(" oc.city as city, ");//是否免费
			commonSql.append(" 3 as querySort, ");//排序
			commonSql.append(" oc.release_time, ");//上架/下架时间
			commonSql.append(" if(oc.sort_update_time< now(),0,oc.recommend_sort) recommendSort, ");//推荐值
			//课程类型     音频、视频、直播、线下培训班   1 2 3 4
			commonSql.append(" if(oc.type =3,4,IF(oc.type = 1,3,if(oc.multimedia_type=1,1,2))) as type, ");
			commonSql.append(" oc.create_time,oc.is_recommend,oc.start_time as startTime,");
			commonSql.append(" ABS(timestampdiff(second,current_timestamp,oc.start_time)) as recent ");

			commonSql.append(" from oe_course oc,oe_menu as om ,oe_user ou ");
			commonSql.append(" where  oc.user_lecturer_id = ou.id and om.id = oc.menu_id  and "
					+ " oc.is_delete=0 and oc.status = 1   ");
			if(org.apache.commons.lang.StringUtils.isNotBlank(city)){
				if(city.equals("其他")){
					Page<OfflineCity> OfflineCityPage = new Page<>();
					OfflineCityPage.setCurrent(1);
					OfflineCityPage.setSize(5);
					List<OfflineCity> oclist = offlineCityService.selectOfflineCityPage(OfflineCityPage).getRecords();
					String citylist = " (";
					for(OfflineCity c : oclist){
						citylist+="'"+c.getCityName()+"',";
					}
					citylist = citylist.substring(0,citylist.length()-1);
					citylist+=") ";
					commonSql.append(" and oc.type =3 ");
					commonSql.append(" and oc.city not in "+citylist+"");
				}else if(city.equals("全国课程")){
					commonSql.append(" and oc.type =3 ");
				}else{
					commonSql.append(" and oc.city= '"+city+"'");
					commonSql.append(" and oc.type =3 ");
				}
			}

			if(org.apache.commons.lang.StringUtils.isNotBlank(isFree)){
				commonSql.append(" and oc.is_free = '"+isFree+"'");
			}

			/**
			 * 直播中的状态 4:直播课程
			 */
            if(lineState!=null&&lineState!=1234){
                commonSql.append(" and oc.live_status = '"+lineState+"'");
            }
            if(lineState!=null&&lineState==1234){
                commonSql.append(" and oc.type = 1 ");
            }
			/**
			 * 目前检索的是讲师名和课程id
			 */
			if(org.apache.commons.lang.StringUtils.isNotBlank(queryKey)){
				commonSql.append(" and ");
				commonSql.append(" oc.lecturer like '%"+ queryKey + "%' ");
			}
			if(courseType!=null) {
				if (courseType == 1 || courseType == 2) { //视频或者音频
					commonSql.append(" and oc.multimedia_type = '" + courseType + "'");  //多媒体类型1视频2音频
				} else if (courseType == 3 || courseType == 4) { //直播  或者线下课程
					commonSql.append(" and " + (courseType == 3 ? " oc.type=1 " : " oc.type =3 "));
				}
			}
			if(org.apache.commons.lang.StringUtils.isNotBlank(menuType)&&
					!menuType.equals("goodCourse")&&!menuType.equals("newCourse")){
				commonSql.append(" AND oc.menu_id = '"+menuType+"' ");
			}

			commonSql.append(") ");

			commonSql.append(" union ");

			commonSql.append(" (select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,"
					+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,");
			commonSql.append(" if(oc.live_status=1,DATE_FORMAT(oc.start_time,'%H:%i'),");
			commonSql.append("if(oc.live_status=2,if(oc.start_time <= DATE_ADD(DATE_ADD(str_to_date(DATE_FORMAT(NOW(),'%Y-%m-%d'),'%Y-%m-%d %H:%i:%s'),INTERVAL 1 DAY),INTERVAL -1 SECOND) ,");
			commonSql.append("DATE_FORMAT(oc.start_time,'%H:%i'),DATE_FORMAT(oc.start_time,'%m.%d')),DATE_FORMAT(oc.start_time,'%m.%d') )) as startDateStr,");
			commonSql.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
					+ "+IFNULL(oc.default_student_count, 0) learndCount, ");
			commonSql.append(" if(oc.is_free =0,0,1) as watchState, ");//是否免费
			commonSql.append(" oc.collection as collection, ");
			commonSql.append(" if(oc.live_status = 2,if((DATE_SUB(now(),INTERVAL 30 MINUTE) < oc.start_time and now() > oc.start_time ) or ");
			commonSql.append(" (DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now() < oc.start_time ),4, ");
			commonSql.append(" if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now() < oc.start_time,5,oc.live_status)),oc.live_status) as lineState,");
			commonSql.append(" oc.city as city, ");//是否免费
			commonSql.append(" 2 as querySort, ");//排序
			commonSql.append(" oc.release_time, ");//上架/下架时间
			commonSql.append(" if(oc.sort_update_time< now(),0,oc.recommend_sort) recommendSort, ");//推荐值
			//课程类型     音频、视频、直播、线下培训班   1 2 3 4
			commonSql.append(" if(oc.type =3,4,IF(oc.type = 1,3,if(oc.multimedia_type=1,1,2))) as type, ");
			commonSql.append(" oc.create_time,oc.is_recommend,oc.start_time as startTime,");
			commonSql.append(" ABS(timestampdiff(second,current_timestamp,oc.start_time)) as recent ");

			commonSql.append(" from oe_course oc,oe_menu as om ,oe_user ou ");
			commonSql.append(" where  oc.user_lecturer_id = ou.id and om.id = oc.menu_id  and "
					+ " oc.is_delete=0 and oc.status = 1   ");
			if(org.apache.commons.lang.StringUtils.isNotBlank(city)){
				if(city.equals("其他")){
					Page<OfflineCity> OfflineCityPage = new Page<>();
					OfflineCityPage.setCurrent(1);
					OfflineCityPage.setSize(5);
					List<OfflineCity> oclist = offlineCityService.selectOfflineCityPage(OfflineCityPage).getRecords();
					String citylist = " (";
					for(OfflineCity c : oclist){
						citylist+="'"+c.getCityName()+"',";
					}
					citylist = citylist.substring(0,citylist.length()-1);
					citylist+=") ";
					commonSql.append(" and oc.type =3 ");
					commonSql.append(" and oc.city not in "+citylist+"");
				}else if(city.equals("全国课程")){
					commonSql.append(" and oc.type =3 ");
				}else{
					commonSql.append(" and oc.city= '"+city+"'");
					commonSql.append(" and oc.type =3 ");
				}
			}

			if(org.apache.commons.lang.StringUtils.isNotBlank(isFree)){
				commonSql.append(" and oc.is_free = '"+isFree+"'");
			}

			/**
			 * 直播中的状态 4:直播课程
			 */
			if(lineState!=null&&lineState!=1234){
				commonSql.append(" and oc.live_status = '"+lineState+"'");
			}
			if(lineState!=null&&lineState==1234){
				commonSql.append(" and oc.type = 1 ");
			}
			/**
			 * 目前检索的是讲师名和课程id
			 */
			if(org.apache.commons.lang.StringUtils.isNotBlank(queryKey)){
				commonSql.append(" and ");
				commonSql.append(" oc.city like '%"+ queryKey + "%' ");
			}
			if(courseType!=null) {
				if (courseType == 1 || courseType == 2) { //视频或者音频
					commonSql.append(" and oc.multimedia_type = '" + courseType + "'");  //多媒体类型1视频2音频
				} else if (courseType == 3 || courseType == 4) { //直播  或者线下课程
					commonSql.append(" and " + (courseType == 3 ? " oc.type=1 " : " oc.type =3 "));
				}
			}
			if(org.apache.commons.lang.StringUtils.isNotBlank(menuType)&&
					!menuType.equals("goodCourse")&&!menuType.equals("newCourse")){
				commonSql.append(" AND oc.menu_id = '"+menuType+"' ");
			}
			commonSql.append(") ");

			commonSql.append(" union ");

			commonSql.append(" (select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,"
					+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,");
			commonSql.append(" if(oc.live_status=1,DATE_FORMAT(oc.start_time,'%H:%i'),");
			commonSql.append("if(oc.live_status=2,if(oc.start_time <= DATE_ADD(DATE_ADD(str_to_date(DATE_FORMAT(NOW(),'%Y-%m-%d'),'%Y-%m-%d %H:%i:%s'),INTERVAL 1 DAY),INTERVAL -1 SECOND) ,");
			commonSql.append("DATE_FORMAT(oc.start_time,'%H:%i'),DATE_FORMAT(oc.start_time,'%m.%d')),DATE_FORMAT(oc.start_time,'%m.%d') )) as startDateStr,");
			commonSql.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
					+ "+IFNULL(oc.default_student_count, 0) learndCount, ");
			commonSql.append(" if(oc.is_free =0,0,1) as watchState, ");//是否免费
			commonSql.append(" oc.collection as collection, ");
			commonSql.append(" if(oc.live_status = 2,if((DATE_SUB(now(),INTERVAL 30 MINUTE) < oc.start_time and now() > oc.start_time ) or ");
			commonSql.append(" (DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now() < oc.start_time ),4, ");
			commonSql.append(" if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now() < oc.start_time,5,oc.live_status)),oc.live_status) as lineState,");
			commonSql.append(" oc.city as city, ");//是否免费
			commonSql.append(" 4 as querySort, ");//排序
			commonSql.append(" oc.release_time, ");//上架/下架时间
			commonSql.append(" if(oc.sort_update_time< now(),0,oc.recommend_sort) recommendSort, ");//推荐值
			//课程类型     音频、视频、直播、线下培训班   1 2 3 4
			commonSql.append(" if(oc.type =3,4,IF(oc.type = 1,3,if(oc.multimedia_type=1,1,2))) as type, ");
			commonSql.append(" oc.create_time,oc.is_recommend,oc.start_time as startTime,");
			commonSql.append(" ABS(timestampdiff(second,current_timestamp,oc.start_time)) as recent ");

			commonSql.append(" from oe_course oc,oe_menu as om ,oe_user ou ");
			commonSql.append(" where  oc.user_lecturer_id = ou.id and om.id = oc.menu_id  and "
					+ " oc.is_delete=0 and oc.status = 1   ");
			if(org.apache.commons.lang.StringUtils.isNotBlank(city)){
				if(city.equals("其他")){
					Page<OfflineCity> OfflineCityPage = new Page<>();
					OfflineCityPage.setCurrent(1);
					OfflineCityPage.setSize(5);
					List<OfflineCity> oclist = offlineCityService.selectOfflineCityPage(OfflineCityPage).getRecords();
					String citylist = " (";
					for(OfflineCity c : oclist){
						citylist+="'"+c.getCityName()+"',";
					}
					citylist = citylist.substring(0,citylist.length()-1);
					citylist+=") ";
					commonSql.append(" and oc.type =3 ");
					commonSql.append(" and oc.city not in "+citylist+"");
				}else if(city.equals("全国课程")){
					commonSql.append(" and oc.type =3 ");
				}else{
					commonSql.append(" and oc.city= '"+city+"'");
					commonSql.append(" and oc.type =3 ");
				}
			}

			if(org.apache.commons.lang.StringUtils.isNotBlank(isFree)){
				commonSql.append(" and oc.is_free = '"+isFree+"'");
			}

			/**
			 * 直播中的状态 4:直播课程
			 */
            if(lineState!=null&&lineState!=1234){
                commonSql.append(" and oc.live_status = '"+lineState+"'");
            }
            if(lineState!=null&&lineState==1234){
                commonSql.append(" and oc.type = 1 ");
            }
			/**
			 * 目前检索的是讲师名和课程id
			 */
			if(org.apache.commons.lang.StringUtils.isNotBlank(queryKey)){
				commonSql.append(" and (");
				commonSql.append(" oc.description like '%"+ queryKey + "%' ");
				commonSql.append(" or ");
				commonSql.append(" oc.course_detail like '%"+ queryKey + "%' ");
				commonSql.append(" or ");
				commonSql.append(" oc.lecturer_description like '%"+ queryKey + "%') ");
			}
			if(courseType!=null) {
				if (courseType == 1 || courseType == 2) { //视频或者音频
					commonSql.append(" and oc.multimedia_type = '" + courseType + "'");  //多媒体类型1视频2音频
				} else if (courseType == 3 || courseType == 4) { //直播  或者线下课程
					commonSql.append(" and " + (courseType == 3 ? " oc.type=1 " : " oc.type =3 "));
				}
			}
			if(org.apache.commons.lang.StringUtils.isNotBlank(menuType)&&
					!menuType.equals("goodCourse")&&!menuType.equals("newCourse")){
				commonSql.append(" AND oc.menu_id = '"+menuType+"' ");
			}
			commonSql.append(")) ooc");

			commonSql.append("  group by ooc.id order by  ooc.querySort asc,recommendSort desc,ooc.release_time desc ");

		}

    	System.out.println("commonSql:"+commonSql.toString());
        return wxcpCourseDao.queryPage(JdbcUtil.getCurrentConnection(),commonSql.toString(),
        		pageNumber,pageSize,CourseLecturVo.class);
	}

	@Override
	public List<CourseLecturVo> offLineClassList(List<OfflineCity> cityList) throws SQLException {
		String strsql="(select  o.id,o.grade_name as gradeName,o.current_price*10 as currentPrice,4 as type,"
				+" o.smallimg_path as smallImgPath,o.lecturer as name,o.address as address,"
				+" o.city as city,DATE_FORMAT(o.start_time,'%m.%d') as startDateStr,"
				+"IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = o.id),0) + IFNULL(o.default_student_count, 0) learndCount,"
				+"if(date_sub(date_format(o.start_time,'%Y%m%d'),INTERVAL 1 DAY)>=date_format(now(),'%Y-%m-%d'),1,0) as cutoff," //是否截止
				+" o.collection as collection,"
				+" o.is_free as watchState,"
				+ " if(o.sort_update_time< now(),0,o.recommend_sort) recommendSort, "
				+" o.start_time as startTime,"
				+"'全国课程' as note "
				+" from oe_course o "
				+" WHERE o.is_delete=0 and o.status=1 and o.type = 3   "
				+" ORDER BY recommendSort desc,o.start_time DESC LIMIT 0,6) ";

		if(cityList.size()>0){
			strsql+= " union all ";
		}
		int i = 0;
		for (OfflineCity offlineCity : cityList) {
				i++;
			strsql+="(select  o.id,o.grade_name as gradeName,o.current_price*10 as currentPrice,4 as type,"
					+" o.smallimg_path as smallImgPath,o.lecturer as name,o.address as address,"
					+" o.city as city,DATE_FORMAT(o.start_time,'%m.%d') as startDateStr,"
					+"IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = o.id),0) + IFNULL(o.default_student_count, 0) learndCount,"
					+"if(date_sub(date_format(o.start_time,'%Y%m%d'),INTERVAL 1 DAY)>=date_format(now(),'%Y-%m-%d'),1,0) as cutoff," //是否截止
					+" o.collection as collection,"
					+" o.is_free as watchState,"
					+ " if(o.sort_update_time< now(),0,o.recommend_sort) recommendSort, "
					+" o.start_time as startTime,"
					+" o.city as note "
					+" from oe_course o "
					+" WHERE o.is_delete=0 and o.status=1 and o.type = 3  and o.city = '"+offlineCity.getCityName()+"' "
					+" ORDER BY recommendSort desc,o.start_time DESC LIMIT 0,4)";

				if(i < cityList.size()){
					strsql+= " union all ";
				}
		}
		return wxcpCourseDao.query(JdbcUtil.getCurrentConnection(),strsql,new BeanListHandler<>(CourseLecturVo.class));
	}
}
