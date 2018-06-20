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

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.interceptor.IOSVersionInterceptor;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.DoctorSortOrderType;
import com.xczhihui.common.util.enums.DoctorType;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.medical.anchor.service.IAnchorInfoService;
import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.department.service.IMedicalDepartmentService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.vo.DoctorQueryVo;
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
    
    @Autowired
    private ICourseService courseService;
    
    @Autowired
    private IAnchorInfoService anchorInfoService;

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
	    	  map.put("type", 0);
	          map.put("text", "热门中医");
	          map.put("doctors", doctors0.getRecords());
	          mapAll.put("HOT", map);
        }
        /**
         * 循环枚举进行查询
         */
        List<Map> listMap = DoctorType.getDoctorTypeList();
        for (int i = 0; i < listMap.size(); i++) {
        	Map  maps = listMap.get(i);
        	Integer code = (Integer) maps.get("code");
        	String text =  (String) maps.get("value");
        	
        	Page<MedicalDoctorVO> doctors = medicalDoctorBusinessService.selectDoctorPage(page, 
        			 code, null, null, null, null);
        	if(doctors!=null && doctors.getSize() >0) {
        		Map<String,Object> mapDoctors = new HashMap<String,Object>();
        		
        		Map<String,Object> map1 = new HashMap<String,Object>();
    	        map1.put("code", code);
    	        map1.put("text", text);
    	        map1.put("doctors", doctors.getRecords());
    	        if(code != null && code.equals(1)) {
    	        	  mapAll.put("MQNZY", map1);
    	        }else if(code != null && code.equals(2)) {
    	        	  mapAll.put("MLZY", map1);
    	        }else if(code != null && code.equals(3)) {
    	        	  mapAll.put("SSMZZY", map1);
    	        }else if(code != null && code.equals(4)) {
    	        	  mapAll.put("GYDS", map1);
    	        }else if(code != null && code.equals(5)) {
  	        	      mapAll.put("GZY", map1);
    	        }  
        	}
		}
        return ResponseObject.newSuccessResponseObject(mapAll);
    }

    /**
     * 搜索列表接口
     * @param pageNumber
     * @param pageSize
     * @param dqv
     * @return
     */
	@RequestMapping(value = "list")
    public ResponseObject list(@RequestParam(value = "page", required = false) 
    		Integer pageNumber, Integer pageSize, 
    		DoctorQueryVo dqv) {
    	
		pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 10 : pageSize;
        
        Page<MedicalDoctorVO> doctors = medicalDoctorBusinessService.
        		selectDoctorListByQueryKey(new Page<MedicalDoctorVO>(pageNumber, pageSize),dqv);
       
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
		List<Map> getDoctorTypeList = DoctorType.getDoctorTypeListAddHot();
		mapAll.put("doctorTypes", getDoctorTypeList);
		//科室
		Page page = new Page(0, Integer.MAX_VALUE);
        Page<MedicalDepartment> departments = medicalDepartmentService.page(page);
        mapAll.put("departments", departments.getRecords());
        //智能排序
        mapAll.put("sortTypes", DoctorSortOrderType.getDoctorSortOrderTypeList());
		return ResponseObject.newSuccessResponseObject(mapAll);
	}
	
	
	/**
     * Description：医师主页    -- 课程列表
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("doctorCourse")
    public ResponseObject doctorCourseList(@RequestParam("userId") String userId,
                         Integer pageNumber,Integer pageSize,
                         @RequestParam(value = "type",required = false)Integer type) throws Exception {
    	
    	pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 10 : pageSize;
    	
        Page<CourseLecturVo> page = new Page<>();
        page.setCurrent(pageNumber);
        page.setSize(pageSize);
        try {
        	Page<CourseLecturVo> list = courseService.selectLecturerAllCourse
        			(page, userId,type,IOSVersionInterceptor.onlyThread.get());
            return ResponseObject.newSuccessResponseObject(list.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("网络开小差");
        }
    }
	
    /**
     * Description：医师 最近的一次直播
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("recentlyLive")
    public ResponseObject recentlyLive(@RequestParam("userId") String userId)
    		throws Exception {
    	
    	CourseLecturVo cv = courseService.selectLecturerRecentCourse(userId, 
    			 IOSVersionInterceptor.onlyThread.get());
    	return ResponseObject.newSuccessResponseObject(cv);    
    }
    
    
	/**
     * Description：医师 主播状态
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("doctorStatus")
    public ResponseObject doctorStatus(@RequestParam("doctorId") String doctorId)
    		throws Exception {
    	
    	return ResponseObject.newSuccessResponseObject(anchorInfoService.anchorPermissionStatusByDoctorId(doctorId));    
    }
}
