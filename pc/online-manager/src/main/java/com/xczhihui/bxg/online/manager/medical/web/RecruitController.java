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
import java.util.Date;

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
	public TableVo MedicalHospitalRecruits(TableVo tableVo) {
	      int pageSize = tableVo.getiDisplayLength();
          int index = tableVo.getiDisplayStart();
          int currentPage = index / pageSize + 1;
          String params = tableVo.getsSearch();
          Groups groups = Tools.filterGroup(params);
          
          MedicalHospitalRecruit searchVo=new MedicalHospitalRecruit();
//          Group MedicalHospitalName = groups.findByName("search_courseName");
		  Group MedicalHospitalId = groups.findByName("hospitalId");
          Group MedicalHospitalRecruitName = groups.findByName("search_recruitName");
          Group medicalHospitalStatus = groups.findByName("search_status");
//          if (MedicalHospitalName != null) {
//        	  searchVo.setName(MedicalHospitalName.getPropertyValue1().toString());
//          }
          if (MedicalHospitalId != null) {
        	  searchVo.setHospitalId(MedicalHospitalId.getPropertyValue1().toString());
          }
          if (MedicalHospitalRecruitName != null) {
        	  searchVo.setPosition(MedicalHospitalRecruitName.getPropertyValue1().toString());
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
	 * @param medicalHospitalRecruit
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
			 	old.setYears(medicalHospitalRecruit.getYears());
			 	old.setUpdateTime(new Date());
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
	@RequestMapping(value = "updateRecruitStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateRecruitStatus(String id){
		hospitalService.updateRecruitStatus(id);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}

	/**
	 * 逻辑删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteMedicalHospitalRecruitById", method = RequestMethod.POST)
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
              hospitalService.deletesRecruit(_ids);
         }
         responseObject.setSuccess(true);
         responseObject.setErrorMessage("删除成功!");
         return responseObject;
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
	 * 上移
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "upMove", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject upMove(String id) {
		ResponseObject responseObj = new ResponseObject();
		hospitalService.updateSortUp(id);
		responseObj.setSuccess(true);
		return responseObj;
	}

	/**
	 * 下移
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "downMove", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject downMove(String id) {
		ResponseObject responseObj = new ResponseObject();
		hospitalService.updateSortDown(id);
		responseObj.setSuccess(true);
		return responseObj;
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
