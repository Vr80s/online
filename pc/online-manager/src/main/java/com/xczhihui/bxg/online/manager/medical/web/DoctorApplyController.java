package com.xczhihui.bxg.online.manager.medical.web;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorApply;
import com.xczhihui.bxg.online.manager.medical.service.DoctorApplyService;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 医师管理控制层实现类
 * @author yxd
 */

@Controller
@RequestMapping("medical/doctor/apply")
public class DoctorApplyController extends AbstractController{

	@Autowired
	private DoctorApplyService doctorApplyService;

	protected final static String CLOUD_CLASS_PATH_PREFIX = "/medical/";

	@Value("${online.web.url:http://www.ixincheng.com}")
	private String weburl;


	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		return CLOUD_CLASS_PATH_PREFIX + "/doctor/apply";
	}
	
	@RequestMapping(value = "MedicalDoctorAppllyDetail")
	public String MedicalDoctorDetail(HttpServletRequest request) {
		request.setAttribute("weburl", weburl);
		return CLOUD_CLASS_PATH_PREFIX + "/doctorApplyDetail";
	}

	/**
	 * 获取医师入驻申请列表
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo list(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
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
}
