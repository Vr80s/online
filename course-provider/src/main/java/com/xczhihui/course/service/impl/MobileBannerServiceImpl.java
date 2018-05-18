package com.xczhihui.course.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.course.mapper.MobileBannerMapper;
import com.xczhihui.course.model.MobileBanner;
import com.xczhihui.course.model.OfflineCity;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.MenuVo;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class MobileBannerServiceImpl extends ServiceImpl<MobileBannerMapper,MobileBanner> implements IMobileBannerService {

	@Autowired
	private MobileBannerMapper iMobileBannerMapper;
	
	@Override
	public List<MobileBanner> selectMobileBannerPage(Integer type) {
		List<MobileBanner> records = iMobileBannerMapper.selectMobileBannerPage(type);
		return   records;
	}
	@Override
	public void addClickNum(String id) {
		   iMobileBannerMapper.addClickNum(id);
	}
	@Override
	public List<Map<String,Object>> recommendCourseList(List<MenuVo> menuList,Integer pageSizeUp,Integer pageSizeDown) {
		
		List<CourseLecturVo> listAll = iMobileBannerMapper.recommendCourseList(menuList,pageSizeUp,pageSizeDown);
		
		List<Map<String,Object>> mapCourseList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> mapTj = new HashMap<String, Object>();
		Map<String,Object> mapNw = new HashMap<String, Object>();
		List<CourseLecturVo> listTj = new ArrayList<CourseLecturVo>();
		List<CourseLecturVo> listNw = new ArrayList<CourseLecturVo>();

		for (CourseLecturVo courseLecturVo : listAll) {
			if("精品课程".equals(courseLecturVo.getNote())){
				listTj.add(courseLecturVo);
			}
			if("最新课程".equals(courseLecturVo.getNote())){
				listNw.add(courseLecturVo);
			}
		}

		if(listTj.size()>0){
			mapTj.put("menuType","goodCourse");
			mapTj.put("title","精品课程");
			mapTj.put("courseList",listTj);
			mapCourseList.add(mapTj);
		}
		if(listNw.size()>0){
			mapNw.put("menuType","newCourse");
			mapNw.put("title","最新课程");
			mapNw.put("courseList",listNw);
			mapCourseList.add(mapNw);
		}

		//定义好这
		for (MenuVo menuVo : menuList) {
			Map<String,Object> mapMenu = new HashMap<String, Object>();
			List<CourseLecturVo> listMenu = new ArrayList<CourseLecturVo>();
			for (CourseLecturVo courseLecturVo : listAll) {
				if(menuVo.getName().equals(courseLecturVo.getNote())){
					listMenu.add(courseLecturVo);
				}
			}
			if(listMenu.size()>0){
				mapMenu.put("menuType", menuVo.getId());
				mapMenu.put("title", menuVo.getName());
				mapMenu.put("courseList", listMenu);
				mapCourseList.add(mapMenu);
			}
		}
		return mapCourseList;
	}

	@Override
	public List<Map<String, Object>> recommendCourseList(List<MenuVo> menuList, Integer pageSize) {
		return recommendCourseList(menuList,pageSize,pageSize);
	}
	@Override
	public List<Map<String, Object>> realCourseList(List<OfflineCity> cityList, Integer pageSizeUp,
			Integer pageSizeDown) {

		List<CourseLecturVo> list = iMobileBannerMapper.realCourseList(cityList,pageSizeUp,pageSizeDown);
		
		List<Map<String,Object>> mapCourseList = new ArrayList<Map<String,Object>>();
		Map<String,Object> mapTj = new HashMap<String, Object>();
		List<CourseLecturVo> listqg = new ArrayList<CourseLecturVo>();
		if(list!=null){
			for (CourseLecturVo courseLecturVo : list) {
				if("全国课程".equals(courseLecturVo.getNote())){
					listqg.add(courseLecturVo);
				}
			}
		}
		if(listqg.size()>0){
			mapTj.put("title","全国课程");
			mapTj.put("courseList",listqg);
			mapCourseList.add(mapTj);
		}
		for (OfflineCity oc : cityList) {
			Map<String,Object> mapMenu = new HashMap<String, Object>();
			List<CourseLecturVo> listMenu = new ArrayList<CourseLecturVo>();
			if(list!=null){
				for (CourseLecturVo courseLecturVo : list) {
					if(oc.getCityName().equals(courseLecturVo.getNote())){
						listMenu.add(courseLecturVo);
					}
				}
			}
			if(listMenu.size()>0){
				mapMenu.put("title", oc.getCityName());
				mapMenu.put("courseList", listMenu);
				mapCourseList.add(mapMenu);
			}
		}
		return mapCourseList;
	}
	@Override
	public List<Map<String, Object>> liveCourseList(Integer pageSizeUp, Integer pageSizeDown) {
		
		List<CourseLecturVo> list = iMobileBannerMapper.liveCourseList(pageSizeUp,pageSizeDown);
		
		//直播 中的课程
		List<Map<String,Object>> mapCourseList = new ArrayList<Map<String,Object>>();
		Map<String,Object> mapTj = new HashMap<String, Object>();
		Map<String,Object> mapNw = new HashMap<String, Object>();
		Map<String,Object> mapZz = new HashMap<String, Object>();
		Map<String,Object> mapHf = new HashMap<String, Object>();
		
		List<CourseLecturVo> listTj = new ArrayList<CourseLecturVo>();
		List<CourseLecturVo> listNw = new ArrayList<CourseLecturVo>();
		List<CourseLecturVo> listZz = new ArrayList<CourseLecturVo>();
		List<CourseLecturVo> listHf = new ArrayList<CourseLecturVo>();
		for (CourseLecturVo courseLecturVo : list) {
			if("正在直播".equals(courseLecturVo.getNote())){
				listTj.add(courseLecturVo);
			}
			if("即将直播".equals(courseLecturVo.getNote())){
				listNw.add(courseLecturVo);
			}
			if("直播课程".equals(courseLecturVo.getNote())){
				listZz.add(courseLecturVo);
			}
			if("精彩直播回放".equals(courseLecturVo.getNote())){
				listHf.add(courseLecturVo);
			}
		}
		if(listTj.size()>0){    
			mapTj.put("title","正在直播");
			mapTj.put("lineState","1");
			mapTj.put("courseList",listTj);
			mapCourseList.add(mapTj);
		}
		if(listNw.size()>0){
			mapNw.put("title","即将直播");
			mapNw.put("lineState","2");
			mapNw.put("courseList",listNw);
			mapCourseList.add(mapNw);
		}
		if(listZz.size()>0){
			mapZz.put("title","直播课程");
			mapZz.put("lineState","1234");
			mapZz.put("courseList",listZz);
			mapCourseList.add(mapZz);
		}
		if(listHf.size()>0){
			mapHf.put("title","精彩直播回放");
			mapHf.put("lineState","3");
			mapHf.put("courseList",listHf);
			mapCourseList.add(mapHf);
		}
		return mapCourseList;
	}
	@Override
	public List<Map<String, Object>> liveCourseList(Integer pageSize) {
		return liveCourseList(pageSize,pageSize);
	}
}
