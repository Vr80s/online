package com.xczhihui.medical.web;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.MedicalDoctor;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorAuthenticationInformation;
import com.xczhihui.bxg.online.common.domain.MedicalHospital;
import com.xczhihui.medical.enums.MedicalExceptionEnum;
import com.xczhihui.medical.exception.MedicalException;
import com.xczhihui.medical.service.DoctorService;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 医师管理控制层实现类
 * 
 * @author yxd
 */

@Controller
@RequestMapping("medical/doctor")
public class DoctorController extends AbstractController {
	protected final static String CLOUD_CLASS_PATH_PREFIX = "/medical/";
	@Autowired
	private DoctorService doctorService;

	@Value("${web.url}")
	private String weburl;

	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		return CLOUD_CLASS_PATH_PREFIX + "/doctor";
	}

	@RequestMapping(value = "MedicalDoctorDetail")
	public String MedicalDoctorDetail(HttpServletRequest request) {
		request.setAttribute("weburl", weburl);
		return CLOUD_CLASS_PATH_PREFIX + "/doctorDetail";
	}

	/**
	 * 跳转到医师详情页
	 * 
	 * @param doctorId
	 *            医师id
	 */
	@RequestMapping(value = "info/{doctorId}")
	public String index(HttpServletRequest request,
			@PathVariable String doctorId) {

		MedicalDoctor doctor = doctorService.findMedicalDoctorById(doctorId);
		request.setAttribute("doctor", doctor);

		return CLOUD_CLASS_PATH_PREFIX + "/doctorInfo";
	}

	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo MedicalDoctors(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		MedicalDoctor searchVo = new MedicalDoctor();
		Group MedicalDoctorName = groups.findByName("search_courseName");
		Group medicalDoctorStatus = groups.findByName("search_status");
		Group medicalDoctorType = groups.findByName("search_type");
		if (MedicalDoctorName != null) {
			searchVo.setName(MedicalDoctorName.getPropertyValue1().toString());
		}
		if (medicalDoctorType != null) {
			searchVo.setType(medicalDoctorType.getPropertyValue1().toString());
		}
		if (medicalDoctorStatus != null) {
			searchVo.setStatusnum(Integer.valueOf(medicalDoctorStatus
					.getPropertyValue1().toString()));
			if (searchVo.getStatusnum() == 1) {
				searchVo.setStatus(true);
			} else {
				searchVo.setStatus(false);
			}
		}
		Page<MedicalDoctor> page = doctorService.findMedicalDoctorPage(
				searchVo, currentPage, pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;

	}

	/**
	 * 添加
	 * 
	 * @param medicalDoctor
	 * @return
	 */
	// //@RequiresPermissions("RealClass:menu:MedicalDoctor")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject add(MedicalDoctor medicalDoctor) {
		ResponseObject responseObj = new ResponseObject();
		// List<MedicalDoctor> entitys=
		// doctorService.findByName(medicalDoctor.getName());
		// for(MedicalDoctor entity:entitys){
		// if(!entity.getDeleted()){
		// responseObj.setSuccess(false);
		// responseObj.setErrorMessage("医师名称已存在！");
		// return responseObj;
		// }
		// }

		try {
			doctorService.addMedicalDoctor(medicalDoctor);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("新增成功");

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setSuccess(false);
			responseObj.setErrorMessage("新增失败");
		}
		return responseObj;
	}

	/**
	 * 查看
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "findMedicalDoctorById", method = RequestMethod.GET)
	@ResponseBody
	public MedicalDoctor findMedicalDoctorById(String id) {
		return doctorService.findMedicalDoctorById(id);
	}

	/**
	 * 编辑
	 * 
	 * @param medicalDoctor
	 * @return
	 */
	@RequestMapping(value = "updateMedicalDoctorById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateMedicalDoctorById(MedicalDoctor medicalDoctor) {
		ResponseObject responseObj = new ResponseObject();
		// List<MedicalDoctor> entitys=
		// doctorService.findByName(medicalDoctor.getName());
		// for(MedicalDoctor entity: entitys){
		// if(!entity.getDeleted()&&!entity.getId().equals(medicalDoctor.getId())){
		// responseObj.setSuccess(false);
		// responseObj.setErrorMessage("医师名称已存在！");
		// return responseObj;
		// }
		// }

		try {
			MedicalDoctor old = doctorService
					.findMedicalDoctorById(medicalDoctor.getId());

			// 如果是用户认证成功的医师 管理员可以修改用户的医师类型
			if (StringUtils.isNotBlank(old.getSourceId())
					|| StringUtils.isNotBlank(old.getCreatePerson())) {
				if (StringUtils.isNotBlank(medicalDoctor.getType())) {
					old.setType(medicalDoctor.getType());
					doctorService.updateMedicalDoctor(old);
					responseObj.setSuccess(true);
					responseObj.setErrorMessage("修改成功");
					return responseObj;
				}
				throw new MedicalException(MedicalExceptionEnum.MUST_NOT_HANDLE);
			}

			old.setName(medicalDoctor.getName());
			old.setTel(medicalDoctor.getTel());
			old.setTitle(medicalDoctor.getTitle());
			old.setType(medicalDoctor.getType());
			old.setProvince(medicalDoctor.getProvince());
			old.setCity(medicalDoctor.getCity());
			old.setWorkTime(medicalDoctor.getWorkTime());
			old.setDetailedAddress(medicalDoctor.getDetailedAddress());
			old.setDescription(medicalDoctor.getDescription());
			doctorService.updateMedicalDoctor(old);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("修改成功");
		} catch (MedicalException e) {
			responseObj.setSuccess(false);
			responseObj.setErrorMessage(MedicalExceptionEnum.MUST_NOT_HANDLE
					.getMsg());
		} catch (Exception e) {
			responseObj.setSuccess(false);
			responseObj.setErrorMessage("修改失败");
			e.printStackTrace();
		}
		return responseObj;
	}

	/**
	 * 修改状态(禁用or启用)
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateStatus(String id) {
		doctorService.updateStatus(id);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}

	/**
	 * 逻辑删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteMedicalDoctorById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deleteMedicalDoctorById(String id) {
		doctorService.deleteMedicalDoctorById(id);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}

	@RequestMapping(value = "deletes", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deletes(String ids) throws InvocationTargetException,
			IllegalAccessException {
		ResponseObject responseObject = new ResponseObject();
		if (ids != null) {
			String[] _ids = ids.split(",");
			doctorService.deletes(_ids);
		}
		responseObject.setSuccess(true);
		responseObject.setErrorMessage("删除成功!");
		return responseObject;
	}

	@RequestMapping(value = "getDoctorDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getMedicalDoctorDetail(String medicalDoctorId) {
		return ResponseObject.newSuccessResponseObject(doctorService
				.getMedicalDoctorDetail(medicalDoctorId));
	}

	/**
	 * 添加或者修改身份验证的信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "updateMedicalDoctorDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateMedicalDoctorDetail(String medicalDoctorId,
			String authenticationInformationId, String picture1,
			String picture2, String picture3, String picture4, String picture5,
			String picture6) {

		doctorService.updateMedicalDoctorDetail(medicalDoctorId,
				authenticationInformationId, picture1, picture2, picture3,
				picture4, picture5, picture6);
		return ResponseObject.newSuccessResponseObject("修改成功！");
	}

	@RequestMapping(value = "doctorDetail")
	public String doctorDetail(HttpServletRequest request) {

		request.setAttribute("weburl", weburl);

		String doctorId = request.getParameter("doctorId");
		String mdaiId = request.getParameter("mdaiId");

		System.out.println("doctorId:" + doctorId + ",mdaiId:" + mdaiId);
		/*
		 * 这里需要传递两个参数 一个是医师id，一个是医师基本信息id
		 */
		request.setAttribute("doctorId", doctorId);
		request.setAttribute("mdaiId", mdaiId);

		return CLOUD_CLASS_PATH_PREFIX + "/doctorDetail";
	}

	@RequestMapping(value = "mdaiDetail")
	@ResponseBody
	public ResponseObject mdaiDetail(String authenticationInformationId) {

		MedicalDoctorAuthenticationInformation mdai = doctorService
				.mdaiDetail(authenticationInformationId);

		return ResponseObject.newSuccessResponseObject(mdai);
	}

	/**
	 * 医师医馆关联接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "updateMedicalHospitalDoctor", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateMedicalHospitalDoctor(String doctorId,
			String hospitalId) {
		// if(hospitalId==null || "".equals(hospitalId)){
		// return ResponseObject.newErrorResponseObject("必须选择一个医馆才能保存！");
		// }
		doctorService.updateMedicalHospitalDoctorDetail(doctorId, hospitalId);
		return ResponseObject.newSuccessResponseObject("修改成功！");
	}

	/**
	 * 获取所有医馆接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "getMedicalHospital")
	@ResponseBody
	public List<MedicalHospital> getMedicalHospital(String id) {

		List<MedicalHospital> medicalHospitals = doctorService
				.getMedicalHospital(id);
		return medicalHospitals;
	}

	@RequestMapping(value = "recList")
	@ResponseBody
	public TableVo recList(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		MedicalDoctor searchVo = new MedicalDoctor();
		Group MedicalDoctorName = groups.findByName("search_courseName");
		Group medicalDoctorStatus = groups.findByName("search_status");
		// searchVo.setOnlineMedicalHospital(1);
		if (MedicalDoctorName != null) {
			searchVo.setName(MedicalDoctorName.getPropertyValue1().toString());
		}
		if (medicalDoctorStatus != null) {
			searchVo.setStatusnum(Integer.valueOf(medicalDoctorStatus
					.getPropertyValue1().toString()));
			if (searchVo.getStatusnum() == 1) {
				searchVo.setStatus(true);
			} else {
				searchVo.setStatus(false);
			}
		}
		Page<MedicalDoctor> page = doctorService.findRecMedicalDoctorPage(
				searchVo, currentPage, pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;

	}

	@RequestMapping(value = "updateRec")
	@ResponseBody
	public ResponseObject updateRec(String ids, int isRec) {
		ResponseObject responseObject = new ResponseObject();
		if (ids != null) {
			String[] _ids = ids.split(",");
			if (doctorService.updateRec(_ids, isRec) == 1000) {
				responseObject.setSuccess(true);
				responseObject.setErrorMessage("操作成功!");
			} else if (doctorService.updateRec(_ids, isRec) == 1001) {
				responseObject.setSuccess(false);
				responseObject.setErrorMessage("最多设置十个推荐医师!");
			} else if (doctorService.updateRec(_ids, isRec) == 1002) {
				responseObject.setSuccess(false);
				responseObject.setErrorMessage("你所选中的被推荐人已是被推荐!");
			}
		}
		return responseObject;
	}

	/**
	 * 推荐上移
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "upMoveRec", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject upMoveRec(String id) {
		ResponseObject responseObj = new ResponseObject();
		doctorService.updateSortUpRec(id);
		responseObj.setSuccess(true);
		return responseObj;
	}

	/**
	 * 推荐下移
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "downMoveRec", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject downMoveRec(String id) {
		ResponseObject responseObj = new ResponseObject();
		doctorService.updateSortDownRec(id);
		responseObj.setSuccess(true);
		return responseObj;
	}

	@RequestMapping(value = "allListForArticle")
	@ResponseBody
	public List<MedicalDoctor> allListForArticle(String articleId) {

		List<MedicalDoctor> allField = doctorService.findAllDoctor(articleId);

		return allField;
	}

	@RequestMapping(value = "addDoctorAuthorArticle", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addDoctorAuthorArticle(String id, String[] doctorId) {
		ResponseObject responseObject = new ResponseObject();
		doctorService.addDoctorAuthorArticle(id, doctorId);
		responseObject.setSuccess(true);
		responseObject.setResultObject("医师作者配置成功！");
		return responseObject;
	}

	@RequestMapping(value = "allListForReport")
	@ResponseBody
	public List<MedicalDoctor> allListForReport(String articleId) {

		List<MedicalDoctor> allField = doctorService
				.allListForReport(articleId);

		return allField;
	}

	@RequestMapping(value = "addDoctorReport", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addDoctorReport(String id, String[] doctorIds) {
		ResponseObject responseObject = new ResponseObject();
		doctorService.addDoctorReport(id, doctorIds);
		responseObject.setSuccess(true);
		responseObject.setResultObject("报道-医师关联成功！");
		return responseObject;
	}

	/**
	 * 获取医师的科室列表
	 */
	@RequestMapping(value = "listDepartmemt")
	@ResponseBody
	public List<MedicalDoctor> listDepartmemt(String writingsId) {
		List<MedicalDoctor> medicalHospitals = doctorService
				.getMedicalDoctor(writingsId);
		return medicalHospitals;
	}

	/**
	 * 获取所有医师 名字 的 接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "getMedicalDoctor")
	@ResponseBody
	public List<MedicalDoctor> getMedicalDoctor(String writingsId) {
		List<MedicalDoctor> medicalHospitals = doctorService
				.getMedicalDoctor(writingsId);
		return medicalHospitals;
	}
}
