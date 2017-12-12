package com.xczhihui.bxg.online.manager.medical.web;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.MedicalHospital;
import com.xczhihui.bxg.online.common.domain.MedicalHospitalRecruit;
import com.xczhihui.bxg.online.manager.medical.service.HospitalService;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 简历管理控制层实现类
 * @author yxd
 */

@Controller
@RequestMapping("medical/recruit")
public class RecruitController extends AbstractController{
	protected final static String CLOUD_CLASS_PATH_PREFIX = "/medical/";
	@Autowired
	private HospitalService hospitalService;
	
	@Value("${online.web.url:http://www.ixincheng.com}")
	private String weburl;

	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo MedicalHospitals(TableVo tableVo) {
	      int pageSize = tableVo.getiDisplayLength();
          int index = tableVo.getiDisplayStart();
          int currentPage = index / pageSize + 1;
          String params = tableVo.getsSearch();
          Groups groups = Tools.filterGroup(params);
          
          MedicalHospitalRecruit searchVo=new MedicalHospitalRecruit();
//          Group MedicalHospitalName = groups.findByName("search_courseName");
          Group MedicalHospitalId = groups.findByName("hospitalId");
          Group medicalHospitalStatus = groups.findByName("search_status");
//          if (MedicalHospitalName != null) {
//        	  searchVo.setName(MedicalHospitalName.getPropertyValue1().toString());
//          }
          if (MedicalHospitalId != null) {
        	  searchVo.setHospitalId(MedicalHospitalId.getPropertyValue1().toString());
          }
          if (medicalHospitalStatus != null) {
			  searchVo.setStatusnum(Integer.valueOf(medicalHospitalStatus.getPropertyValue1().toString()));
			  if(searchVo.getStatusnum() == 1){
					searchVo.setStatus(true);
				}else{
					searchVo.setStatus(false);
				}
          }
          Page<MedicalHospitalRecruit> page = hospitalService.findMedicalHospitalRecruitPage(searchVo, currentPage, pageSize);
          int total = page.getTotalCount();
          tableVo.setAaData(page.getItems());
          tableVo.setiTotalDisplayRecords(total);
          tableVo.setiTotalRecords(total);
          return tableVo;
		
	}
	
	/**
	 * 添加
	 * @return
	 */
//	@RequiresPermissions("RealClass:menu:MedicalHospital")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	 public ResponseObject add(MedicalHospitalRecruit medicalHospitalRecruit){
		ResponseObject responseObj = new ResponseObject();
		try{
			hospitalService.addMedicalHospitalRecruit(medicalHospitalRecruit);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("新增成功");
            
       }catch(Exception e){
    	   	e.printStackTrace();
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("新增失败");
       }
        return responseObj;
    }
	
	/**
	 * 查看
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "findMedicalHospitalRecruitById", method = RequestMethod.GET)
	@ResponseBody
	  public MedicalHospitalRecruit findMedicalHospitalRecruitById(String id) {
		return 	hospitalService.findMedicalHospitalRecruitById(id);
	}
	
	/**
	 * 编辑
	 * @param medicalHospital
	 * @return
	 */
	@RequestMapping(value = "updateMedicalHospitalRecruitById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateMedicalHospitalRecruitById (MedicalHospitalRecruit medicalHospitalRecruit){
		ResponseObject responseObj = new ResponseObject();
		 try{
			 MedicalHospitalRecruit old = hospitalService.findMedicalHospitalRecruitById(medicalHospitalRecruit.getId());
			 	old.setPosition(medicalHospitalRecruit.getPosition());
			 	old.setJobRequirements(medicalHospitalRecruit.getJobRequirements());
			 	old.setPostDuties(medicalHospitalRecruit.getPostDuties());
			 	old.setUpdateTime(medicalHospitalRecruit.getUpdateTime());
			 	hospitalService.updateMedicalHospitalRecruit(old);
	            responseObj.setSuccess(true);
	            responseObj.setErrorMessage("修改成功");
	       }catch(Exception e){
	            responseObj.setSuccess(false);
	            responseObj.setErrorMessage("修改失败");
	            e.printStackTrace();
	       }
	        return responseObj;
	}
	
	/**
	 * 修改状态(禁用or启用)
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(String id){
		hospitalService.updateStatus(id);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}

	/**
	 * 逻辑删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteMedicalHospitalById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deleteMedicalHospitalById(String id){
		hospitalService.deleteMedicalHospitalById(id);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}

    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException, IllegalAccessException {
         ResponseObject responseObject=new ResponseObject();
         if(ids!=null) {
              String[] _ids = ids.split(",");
              hospitalService.deletes(_ids);
         }
         responseObject.setSuccess(true);
         responseObject.setErrorMessage("删除成功!");
         return responseObject;
    }

    @RequestMapping(value = "getHospitalDetail", method = RequestMethod.POST)
	@ResponseBody
	 public ResponseObject getMedicalHospitalDetail(String medicalHospitalId){
    	return ResponseObject.newSuccessResponseObject(hospitalService.getMedicalHospitalDetail(medicalHospitalId));
    }
    
    /**
     * 添加课程详情
     * @return
     */
	@RequestMapping(value = "updateMedicalHospitalDetail", method = RequestMethod.POST)
	@ResponseBody
	 public ResponseObject updateMedicalHospitalDetail(String medicalHospitalId, String picture1, String picture2, String picture3, String picture4, String picture5){
		hospitalService.updateMedicalHospitalDetail(medicalHospitalId, picture1, picture2, picture3, picture4, picture5);
        return ResponseObject.newSuccessResponseObject("修改成功！");
    }


	@RequestMapping(value = "hospitalDetail")
	public String hospitalDetail(HttpServletRequest request) {

		request.setAttribute("weburl", weburl);
		return CLOUD_CLASS_PATH_PREFIX + "/hospitalDetail";
	}

	@RequestMapping(value = "updateRec")
	@ResponseBody
	public ResponseObject updateRec(String ids,int isRec) {
		ResponseObject responseObject=new ResponseObject();
		if(ids!=null) {
			String[] _ids = ids.split(",");
			if(hospitalService.updateRec(_ids,isRec))
			{
				responseObject.setSuccess(true);
				responseObject.setErrorMessage("操作成功!");
			}else{
				responseObject.setSuccess(false);
				responseObject.setErrorMessage("最多设置十个推荐医馆!");
			}
		}
		return responseObject;
	}


	@RequestMapping(value = "recList")
	@ResponseBody
	public TableVo recList(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		MedicalHospital searchVo=new MedicalHospital();
		Group MedicalHospitalName = groups.findByName("search_courseName");
		Group medicalHospitalStatus = groups.findByName("search_status");
//          searchVo.setOnlineMedicalHospital(1);
		if (MedicalHospitalName != null) {
			searchVo.setName(MedicalHospitalName.getPropertyValue1().toString());
		}
		if (medicalHospitalStatus != null) {
			searchVo.setStatusnum(Integer.valueOf(medicalHospitalStatus.getPropertyValue1().toString()));
			if(searchVo.getStatusnum() == 1){
				searchVo.setStatus(true);
			}else{
				searchVo.setStatus(false);
			}
		}
		Page<MedicalHospital> page = hospitalService.findRecMedicalHospitalPage(searchVo, currentPage, pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;

	}

	/**
	 * 推荐上移
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "upMoveRec", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject upMoveRec(String id) {
		ResponseObject responseObj = new ResponseObject();
		hospitalService.updateSortUpRec(id);
		responseObj.setSuccess(true);
		return responseObj;
	}

	/**
	 * 推荐下移
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "downMoveRec", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject downMoveRec(String id) {
		ResponseObject responseObj = new ResponseObject();
		hospitalService.updateSortDownRec(id);
		responseObj.setSuccess(true);
		return responseObj;
	}

	/**
	 * 添加、修改招聘信息
	 * @return
	 */
	@RequestMapping(value = "toRecruit")
	public String toRecruit(HttpServletRequest request) {
		return CLOUD_CLASS_PATH_PREFIX + "/recruit";
	}
}
