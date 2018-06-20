package com.xczh.consumer.market.controller.doctor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.DoctorSortOrderType;
import com.xczhihui.common.util.enums.DoctorType;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.department.service.IMedicalDepartmentService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorArticleService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorWritingService;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;

/**
 * Description：医师页面
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/3/26 0026 上午 11:59
 **/
@RestController
@RequestMapping(value = "/xczh/doctors")
public class DoctorController{

    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private IMedicalDepartmentService medicalDepartmentService;

    /**
     * 医师分类页面
     * @return
     */
    @RequestMapping("category")
    public ResponseObject category() {
    	

        Page<MedicalDoctorVO> page = new Page<>();
        page.setCurrent(1);
        page.setSize(3);
        
        Map<String,Object> mapAll = new HashMap<String,Object>();
        
        
        Map<String,Object> map = new HashMap<String,Object>();
        Page<MedicalDoctorVO> doctors0 = medicalDoctorBusinessService.selectDoctorPage(page, null, null, null, null, null);
       
        if(doctors0!=null && doctors0.getSize()>0) {
	    	  map.put("type", "");
	          map.put("text", "热门中医");
	          map.put("doctors", doctors0.getRecords());
        }
      
        List<Map> listMap = DoctorType.getDoctorTypeList();
        
        for (int i = 0; i < listMap.size(); i++) {
        	Map  maps = listMap.get(i);
        	Integer code = (Integer) maps.get("code");
        	
        	Page<MedicalDoctorVO> doctors = medicalDoctorBusinessService.selectDoctorPage(page, 
        			 code, null, null, null, null);
        	if(doctors!=null && doctors.getSize() >0) {
        		
        	}
        	
		}
        
        
        Map<String,Object> map1 = new HashMap<String,Object>();
        Page<MedicalDoctorVO> doctors1 = medicalDoctorBusinessService.selectDoctorPage(page, 
        		DoctorType.MQNZY.getCode(), null, null, null, null);
        map1.put("code", DoctorType.MQNZY.getCode());
        map1.put("text", DoctorType.MQNZY.getText());
        map1.put("doctors", doctors1.getRecords());
        
        
        Map<String,Object> map2 = new HashMap<String,Object>();
        Page<MedicalDoctorVO> doctors2 = medicalDoctorBusinessService.selectDoctorPage(page, DoctorType.MLZY.getCode(), null, null, null, null);
        map2.put("code", DoctorType.MLZY.getCode());
        map2.put("text",  DoctorType.MLZY.getText());
        map2.put("doctors", doctors2.getRecords());
        
        
        Map<String,Object> map3 = new HashMap<String,Object>();
        Page<MedicalDoctorVO> doctors3 = medicalDoctorBusinessService.selectDoctorPage(page, DoctorType.SSMZZY.getCode(), null, null, null, null);
        map3.put("code", DoctorType.SSMZZY.getCode());
        map3.put("text", DoctorType.SSMZZY.getText());
        map3.put("doctors", doctors3.getRecords());
        
        
        Map<String,Object> map4 = new HashMap<String,Object>();
        Page<MedicalDoctorVO> doctors4 = medicalDoctorBusinessService.selectDoctorPage(page, DoctorType.GYDS.getCode(), null, null, null, null);
        map4.put("code", DoctorType.GYDS.getCode());
        map4.put("text", DoctorType.GYDS.getText());
        map4.put("doctors", doctors4.getRecords());
        
        Map<String,Object> map5 = new HashMap<String,Object>();
        Page<MedicalDoctorVO> doctors5 = medicalDoctorBusinessService.selectDoctorPage(page, DoctorType.GZY.getCode(), null, null, null, null);
        map5.put("code", DoctorType.GZY.getCode());
        map5.put("text", DoctorType.GZY.getText());
        map5.put("doctors", doctors5.getRecords());
        
        mapAll.put("HOT", map);
        mapAll.put("MQNZY", map1);
        mapAll.put("SSMZZY", map2);
        mapAll.put("GYDS", map3);
        mapAll.put("GZY", map4);
        
        return ResponseObject.newSuccessResponseObject(mapAll);
    }


    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list")
    public ResponseObject list(@RequestParam(value = "page", required = false) 
    		Integer current, Integer size, Integer type, 
    	    String hospitalId, String name, 
    	    String field, String departmentId) {
        current = current == null ? 1 : current;
        size = size == null ? 10 : size;

        Page<MedicalDoctorVO> doctors = medicalDoctorBusinessService.selectDoctorPage(new Page(current, size), type, hospitalId, name, field, departmentId);
        return ResponseObject.newSuccessResponseObject(doctors.getRecords());
    }
    /**
	 * 分类
	 */
	@RequestMapping("screen")
	public ResponseObject schoolClass(HttpServletRequest req,
									  HttpServletResponse res)
			throws Exception {

        Map<String,Object> mapAll = new HashMap<String,Object>();
		//名医类型
		List<Map> getDoctorTypeList = DoctorType.getDoctorTypeList();
		mapAll.put("doctorTypes", getDoctorTypeList);
		//科室
		Page page = new Page(0, Integer.MAX_VALUE);
        Page<MedicalDepartment> departments = medicalDepartmentService.page(page);
        mapAll.put("departments", departments.getRecords());
        //智能排序
        mapAll.put("sortTypes", DoctorSortOrderType.getDoctorSortOrderTypeList());
		return ResponseObject.newSuccessResponseObject(mapAll);
	}
}
