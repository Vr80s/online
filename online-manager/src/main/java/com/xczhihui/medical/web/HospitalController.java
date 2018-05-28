package com.xczhihui.medical.web;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.medical.enums.MedicalExceptionEnum;
import com.xczhihui.medical.service.HospitalService;
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

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.MedicalHospital;
import com.xczhihui.medical.exception.MedicalException;
import com.xczhihui.utils.Group;

/**
 * 医馆管理控制层实现类
 *
 * @author yxd
 */

@Controller
@RequestMapping("medical/hospital")
public class HospitalController extends AbstractController {
	protected final static String CLOUD_CLASS_PATH_PREFIX = "/medical/";
	@Autowired
	private HospitalService hospitalService;

	@Value("${web.url}")
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

	/**
	 * 跳转到医馆详情页
	 *
	 * @param hospitalId
	 *            医馆id
	 */
	@RequestMapping(value = "info/{hospitalId}")
	public String info(HttpServletRequest request,
			@PathVariable String hospitalId) {

		MedicalHospital hospital = hospitalService
				.findMedicalHospitalById(hospitalId);
		request.setAttribute("hospital", hospital);

		return CLOUD_CLASS_PATH_PREFIX + "/hospitalInfo";
	}

	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo medicalHospitals(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		MedicalHospital searchVo = new MedicalHospital();
		Group medicalHospitalName = groups.findByName("search_courseName");
		Group medicalHospitalStatus = groups.findByName("search_status");
		Group medicalHospitalAuthentication = groups.findByName("search_authentication");
		Group medicalHospitalScore = groups.findByName("search_score");
		if (medicalHospitalName != null) {
			searchVo.setName(medicalHospitalName.getPropertyValue1().toString());
		}
		if (medicalHospitalStatus != null) {
			searchVo.setStatusnum(Integer.valueOf(medicalHospitalStatus
					.getPropertyValue1().toString()));
			if (searchVo.getStatusnum() == 1) {
				searchVo.setStatus(true);
			} else {
				searchVo.setStatus(false);
			}
		}
		if (medicalHospitalAuthentication != null) {
			searchVo.setAuthenticationNum(Integer.valueOf(medicalHospitalAuthentication.getPropertyValue1().toString()));
			if (searchVo.getAuthenticationNum() == 1) {
				searchVo.setAuthentication(true);
			} else {
				searchVo.setAuthentication(false);
			}
		}
		if (medicalHospitalScore != null) {
			searchVo.setScore(Double.valueOf(medicalHospitalScore.getPropertyValue1().toString()));
		}
		Page<MedicalHospital> page = hospitalService.findMedicalHospitalPage(
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
	 * @param medicalHospital
	 * @return
	 */
	// //@RequiresPermissions("RealClass:menu:MedicalHospital")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject add(MedicalHospital medicalHospital) {
		ResponseObject responseObj = new ResponseObject();
		List<MedicalHospital> entitys = hospitalService
				.findByName(medicalHospital.getName());
		for (MedicalHospital entity : entitys) {
			if (!entity.getDeleted()) {
				responseObj.setSuccess(false);
				responseObj.setErrorMessage("医馆名称已存在！");
				return responseObj;
			}
		}

		try {
			hospitalService.addMedicalHospital(medicalHospital);
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
	@RequestMapping(value = "findMedicalHospitalById", method = RequestMethod.GET)
	@ResponseBody
	public MedicalHospital findMedicalHospitalById(String id) {
		return hospitalService.findMedicalHospitalById(id);
	}

	/**
	 * 编辑
	 *
	 * @param medicalHospital
	 * @return
	 */
	@RequestMapping(value = "updateMedicalHospitalById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateMedicalHospitalById(
			MedicalHospital medicalHospital) {
		ResponseObject responseObj = new ResponseObject();
		List<MedicalHospital> entitys = hospitalService
				.findByName(medicalHospital.getName());
		for (MedicalHospital entity : entitys) {
			if (!entity.getDeleted()
					&& !entity.getId().equals(medicalHospital.getId())) {
				responseObj.setSuccess(false);
				responseObj.setErrorMessage("医馆名称已存在！");
				return responseObj;
			}
		}

		try {
			MedicalHospital old = hospitalService
					.findMedicalHospitalById(medicalHospital.getId());

			if (StringUtils.isNotBlank(old.getSourceId())) {
				throw new MedicalException(MedicalExceptionEnum.MUST_NOT_HANDLE);
			}

			old.setName(medicalHospital.getName());
			old.setLal(medicalHospital.getLal());
			old.setTel(medicalHospital.getTel());
			old.setEmail(medicalHospital.getEmail());
			old.setPostCode(medicalHospital.getPostCode());
			old.setProvince(medicalHospital.getProvince());
			old.setCity(medicalHospital.getCity());
			old.setDetailedAddress(medicalHospital.getDetailedAddress());
			old.setDescription(medicalHospital.getDescription());
			old.setScore(medicalHospital.getScore());
			old.setAuthentication(medicalHospital.isAuthentication());
			hospitalService.updateMedicalHospital(old);
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
		hospitalService.updateStatus(id);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}

	@RequestMapping(value = "deletes", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deletes(String ids) throws InvocationTargetException,
			IllegalAccessException {
		ResponseObject responseObject = new ResponseObject();
		if (ids != null) {
			String[] idsArr = ids.split(",");
			hospitalService.deletes(idsArr);
		}
		responseObject.setSuccess(true);
		responseObject.setErrorMessage("删除成功!");
		return responseObject;
	}

	@RequestMapping(value = "getHospitalDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject getMedicalHospitalDetail(String medicalHospitalId) {
		return ResponseObject.newSuccessResponseObject(hospitalService
				.getMedicalHospitalDetail(medicalHospitalId));
	}

	/**
	 * 修改图片
	 *
	 * @return
	 */
	@RequestMapping(value = "updateMedicalHospitalDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateMedicalHospitalDetail(String medicalHospitalId,
			String picture1, String picture2, String picture3, String picture4,
			String picture5, String picture6, String picture7, String picture8,
			String picture9) {
		hospitalService.updateMedicalHospitalDetail(medicalHospitalId,
				picture1, picture2, picture3, picture4, picture5, picture6,
				picture7, picture8, picture9);
		return ResponseObject.newSuccessResponseObject("修改成功！");
	}

	@RequestMapping(value = "hospitalDetail")
	public String hospitalDetail(HttpServletRequest request) {
		request.setAttribute("weburl", weburl);
		return CLOUD_CLASS_PATH_PREFIX + "/hospitalDetail";
	}

	@RequestMapping(value = "updateRec")
	@ResponseBody
	public ResponseObject updateRec(String ids, int isRec) {
		ResponseObject responseObject = new ResponseObject();
		if (ids != null) {
			String[] _ids = ids.split(",");
			if (hospitalService.updateRec(_ids, isRec)) {
				responseObject.setSuccess(true);
				responseObject.setErrorMessage("操作成功!");
			} else {
				responseObject.setSuccess(false);
				responseObject.setErrorMessage("最多设置十个推荐医馆!");
			}
		}
		return responseObject;
	}

	/**
	 * 医馆推荐列表
	 * 
	 * @param tableVo
	 * @return
	 */
	@RequestMapping(value = "recList")
	@ResponseBody
	public TableVo recList(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		MedicalHospital searchVo = new MedicalHospital();
		Group medicalHospitalName = groups.findByName("search_courseName");
		Group medicalHospitalStatus = groups.findByName("search_status");
		if (medicalHospitalName != null) {
			searchVo.setName(medicalHospitalName.getPropertyValue1().toString());
		}
		if (medicalHospitalStatus != null) {
			searchVo.setStatusnum(Integer.valueOf(medicalHospitalStatus
					.getPropertyValue1().toString()));
			if (searchVo.getStatusnum() == 1) {
				searchVo.setStatus(true);
			} else {
				searchVo.setStatus(false);
			}
		}
		Page<MedicalHospital> page = hospitalService
				.findRecMedicalHospitalPage(searchVo, currentPage, pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;

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
		hospitalService.updateSortUpRec(id);
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
		hospitalService.updateSortDownRec(id);
		responseObj.setSuccess(true);
		return responseObj;
	}

	/**
	 * 添加、修改招聘信息
	 *
	 * @return
	 */
	@RequestMapping(value = "toRecruit")
	public String toRecruit(HttpServletRequest request, String hospitalId) {
		request.setAttribute("hospitalId", hospitalId);
		return CLOUD_CLASS_PATH_PREFIX + "/recruit";
	}
}
