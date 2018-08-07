package com.xczhihui.medical.doctor.service.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.medical.doctor.mapper.DoctorTypeMapper;
import com.xczhihui.medical.doctor.model.DoctorType;
import com.xczhihui.medical.doctor.service.IDoctorTypeService;

/**
 * ClassName: DoctorTypeServiceImpl.java <br>
 * Description:医师分类接口类 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 下午 11:24 2017/12/9 0009<br>
 */
@Service
public class DoctorTypeServiceImpl extends ServiceImpl<DoctorTypeMapper, DoctorType> implements IDoctorTypeService {
	
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DoctorTypeMapper  doctorTypeMapper;
    
	@Override
	public List<Map<String, Object>> getDoctorTypeTitleList() {
		return doctorTypeMapper.getDoctorTypeTitleList();
	}

	@Override
	public String getDoctorTypeTitleById(String id) {
		return doctorTypeMapper.getDoctorTypeTitleById(id);
	}

	@Override
	public List<DoctorType> getDoctorTypeList() {
		
		return doctorTypeMapper.getDoctorTypeList();
	}

   
}
