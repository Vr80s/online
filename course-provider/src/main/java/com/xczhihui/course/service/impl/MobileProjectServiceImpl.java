package com.xczhihui.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.util.enums.CourseType;
import com.xczhihui.course.mapper.MobileProjectMapper;
import com.xczhihui.course.model.MobileProject;
import com.xczhihui.course.service.IMobileProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class MobileProjectServiceImpl extends ServiceImpl<MobileProjectMapper,MobileProject> implements IMobileProjectService {

	@Autowired
	private MobileProjectMapper iMobileProjectMapper;
	
	@Override
    public Page<MobileProject> selectMobileProjectPage(Page<MobileProject> page, Integer type) {
		List<MobileProject> records = iMobileProjectMapper.selectMobileProjectPage(page,type);
		return   page.setRecords(records);
	}

	@Override
	public List<Map<String, Object>> getCourseType() {
		List<Map<String, Object>>  list  = CourseType.getCourseType();
		return list;
	}
}
