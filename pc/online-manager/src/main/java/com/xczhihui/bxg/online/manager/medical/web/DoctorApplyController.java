package com.xczhihui.bxg.online.manager.medical.web;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorApply;
import com.xczhihui.bxg.online.manager.medical.service.DoctorApplyService;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 医师入驻管理控制层
 * @author zhuwenbao
 */

@Controller
@RequestMapping("medical/doctor/apply")
public class DoctorApplyController extends AbstractController{

	@Autowired
	private DoctorApplyService doctorApplyService;

	protected final static String CLOUD_CLASS_PATH_PREFIX = "/medical/";

	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		return CLOUD_CLASS_PATH_PREFIX + "/doctorApply";
	}

	/**
	 * 获取医师入驻申请列表
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public TableVo list(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		MedicalDoctorApply searchVo = new MedicalDoctorApply();
		Page<MedicalDoctorApply> page = doctorApplyService.list(searchVo, currentPage, pageSize);

		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;
		
	}

	/**
	 * 更新医师入驻申请状态
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateStatus(MedicalDoctorApply doctorApply) {

		ResponseObject responseObj = new ResponseObject();

		doctorApplyService.updateStatus(doctorApply);

		responseObj.setSuccess(true);
		responseObj.setErrorMessage("修改成功");
		return responseObj;
	}

	@RequestMapping(value = "/{appId}")
	public String MedicalDoctorDetail(HttpServletRequest request, @PathVariable String appId) {

		MedicalDoctorApply medicalDoctorApply = doctorApplyService.findById(appId);
		request.setAttribute("medicalDoctorApply", medicalDoctorApply);

		return CLOUD_CLASS_PATH_PREFIX + "/doctorApplyDetail";
	}

	/**
	 * 兼容之前主播没有进行医师认证所缺少的数据
	 */
	@RequestMapping(value = "/after", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject afterApply(String userId) {

		ResponseObject responseObj = new ResponseObject();

		doctorApplyService.afterApply(userId);

		responseObj.setSuccess(true);
		responseObj.setErrorMessage("修改成功");
		return responseObj;
	}

	/**
	 * 兼容之前主播没有进行医师认证所缺少的数据
	 */
	@RequestMapping(value = "/afterApplyAll")
	@ResponseBody
	public ResponseObject afterApplyAll(String userId) {

		ResponseObject responseObj = new ResponseObject();

		doctorApplyService.afterApplyAll();

		responseObj.setSuccess(true);
		responseObj.setErrorMessage("修改成功");
		return responseObj;
	}


}
