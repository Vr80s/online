package com.xczhihui.bxg.online.manager.medical.web;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.api.po.MedicalHospital;
import com.xczhihui.bxg.online.manager.medical.service.HospitalService;
import com.xczhihui.bxg.online.manager.user.service.OnlineUserService;
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
 * 医馆管理控制层实现类
 * @author yxd
 */

@Controller
@RequestMapping("medical/hospital")
public class HospitalController extends AbstractController{
	protected final static String CLOUD_CLASS_PATH_PREFIX = "/medical/";
	@Autowired
	private HospitalService hospitalService;
	
	@Value("${online.web.url:http://www.ixincheng.com}")
	private String weburl;


	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		return CLOUD_CLASS_PATH_PREFIX + "/hospital";
	}
	
	@RequestMapping(value = "MedicalHospitalDetail")
	public String MedicalHospitalDetail(HttpServletRequest request) {
		request.setAttribute("weburl", weburl);
		return CLOUD_CLASS_PATH_PREFIX + "/hospitalDetail";
	}

	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo MedicalHospitals(TableVo tableVo) {
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
          Page<MedicalHospital> page = hospitalService.findMedicalHospitalPage(searchVo, currentPage, pageSize);
          int total = page.getTotalCount();
          tableVo.setAaData(page.getItems());
          tableVo.setiTotalDisplayRecords(total);
          tableVo.setiTotalRecords(total);
          return tableVo;
		
	}
	
	/**
	 * 添加
	 * @param medicalHospital
	 * @return
	 */
//	@RequiresPermissions("RealClass:menu:MedicalHospital")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	 public ResponseObject add(MedicalHospital medicalHospital){
		ResponseObject responseObj = new ResponseObject();
		List<MedicalHospital> entitys= hospitalService.findByName(medicalHospital.getName());
		for(MedicalHospital entity:entitys){
			if(!entity.getDeleted()){
				 responseObj.setSuccess(false);
		         responseObj.setErrorMessage("医馆名称已存在！");
		         return responseObj;
			}
		}

		try{
			hospitalService.addMedicalHospital(medicalHospital);
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
	@RequestMapping(value = "findMedicalHospitalById", method = RequestMethod.GET)
	@ResponseBody
	  public MedicalHospital findMedicalHospitalById(String id) {
		return 	hospitalService.findMedicalHospitalById(id);
	}
	
	/**
	 * 编辑
	 * @param medicalHospital
	 * @return
	 */
	@RequestMapping(value = "updateMedicalHospitalById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateMedicalHospitalById (MedicalHospital medicalHospital){
		ResponseObject responseObj = new ResponseObject();
		List<MedicalHospital> entitys= hospitalService.findByName(medicalHospital.getName());
		for(MedicalHospital entity: entitys){
			if(!entity.getDeleted()&&!entity.getId().equals(medicalHospital.getId())){
				 responseObj.setSuccess(false);
		         responseObj.setErrorMessage("医馆名称已存在！");
		         return responseObj;
			}
		}

		 try{
			 	MedicalHospital old = hospitalService.findMedicalHospitalById(medicalHospital.getId());
			 	old.setName(medicalHospital.getName());
			 	old.setLal(medicalHospital.getLal());
			 	old.setTel(medicalHospital.getTel());
			 	old.setEmail(medicalHospital.getEmail());
			 	old.setPostCode(medicalHospital.getPostCode());
			 	old.setProvince(medicalHospital.getProvince());
			 	old.setCity(medicalHospital.getCity());
			 	old.setDetailedAddress(medicalHospital.getDetailedAddress());
			 	old.setDescription(medicalHospital.getDescription());
			 	hospitalService.updateMedicalHospital(old);
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
//
//	@RequestMapping(value = "uploadImg", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseObject uploadImg(String content){
//		String str = content.split("base64,")[1];
//		byte[] b = org.apache.commons.codec.binary.Base64.decodeBase64(str);
//		Attachment a = att.addAttachment(UserHolder.getCurrentUser().getId(), AttachmentType.ONLINE, "1.png", b, "image/png", null);
//		if (a.getError() != 0) {
//			return ResponseObject.newErrorResponseObject("上传失败！");
//		}
//		return ResponseObject.newSuccessResponseObject(a);
//	}


	@RequestMapping(value = "hospitalDetail")
	public String courseDetail(HttpServletRequest request) {
//		List<Menu> menuVos= courseService.getfirstMenus(null);
//		request.setAttribute("menuVo", menuVos);
//
//		//在列表初始化时查找出课程类别
//		List<ScoreType> scoreTypeVos = courseService.getScoreType();
//		request.setAttribute("scoreTypeVo", scoreTypeVos);
//
//		//在列表初始化时查找出授课方式
//		List<TeachMethod> teachMethodVos= courseService.getTeachMethod();
//		request.setAttribute("teachMethodVo", teachMethodVos);
//
//		List<LecturerVo> Lecturers = courseService.getLecturers();
//		request.setAttribute("lecturerVo", Lecturers);

		request.setAttribute("weburl", weburl);
		return CLOUD_CLASS_PATH_PREFIX + "/hospitalDetail";
	}
}
