package com.xczhihui.medical.doctor.service;


import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xczhihui.medical.doctor.model.DoctorType;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface IDoctorTypeService extends IService<DoctorType> {

	
	List<DoctorType>  getDoctorTypeList();

	String  getDoctorTypeTitleById(String id);

	List<Map<String,Object>> getDoctorTypeTitleList();
}
