package com.xczhihui.bxg.online.manager.medical.web;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.MedicalHospitalApply;
import com.xczhihui.bxg.online.manager.medical.service.HospitalApplyService;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 医馆入驻管理控制层
 * @author zhuwenbao
 */

@Controller
@RequestMapping("medical/hospital/apply")
public class HospitalApplyController extends AbstractController{

	@Autowired
	private HospitalApplyService hospitalApplyService;

	protected final static String CLOUD_CLASS_PATH_PREFIX = "/medical/";

	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		return CLOUD_CLASS_PATH_PREFIX + "/hospitalApply";
	}
	
	@RequestMapping(value = "/{applyId}")
	public String MedicalDoctorDetail(HttpServletRequest request, @PathVariable String applyId) {

		MedicalHospitalApply medicalHospitalApply = hospitalApplyService.findById(applyId);

		request.setAttribute("medicalHospitalApply", medicalHospitalApply);

		return CLOUD_CLASS_PATH_PREFIX + "/hospitalApplyDetail";
	}

	/**
	 * 获取医师入驻申请列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public TableVo list(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize;

		MedicalHospitalApply searchVo = new MedicalHospitalApply();
		Page<MedicalHospitalApply> page = hospitalApplyService.list(searchVo, currentPage, pageSize);

		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;
		
	}

	/**
	 * 更新医师入驻申请状态
	 */
	@RequestMapping(value = "/updateStatus")
	@ResponseBody
	public ResponseObject updateStatus(MedicalHospitalApply hospitalApply) {

		hospitalApplyService.updateStatus(hospitalApply);

		ResponseObject responseObj = new ResponseObject();
		responseObj.setSuccess(true);
		responseObj.setErrorMessage("修改成功");
		return responseObj;
	}
}
