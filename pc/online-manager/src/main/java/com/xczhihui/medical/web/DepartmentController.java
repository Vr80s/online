package com.xczhihui.medical.web;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.MedicalDepartment;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.medical.service.DepartmentService;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.service.UserService;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * 科室控制层实现类
 * 
 * @author Rongcai Kang
 */
@RestController()
@RequestMapping(value = "/medical/department")
public class DepartmentController {

	@Autowired
	private DepartmentService service;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("/medical/department");
		return mav;
	}

	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo list(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);
		Group createPersonGroup = groups.findByName("createPerson");
		Group nameGroup = groups.findByName("name");
		Group time_startGroup = groups.findByName("time_start");
		Group time_endGroup = groups.findByName("time_end");
		MedicalDepartment searchVo = new MedicalDepartment();
		if (createPersonGroup != null) {
			searchVo.setCreatePerson(createPersonGroup.getPropertyValue1()
					.toString());
		}
		if (nameGroup != null) {
			searchVo.setName(nameGroup.getPropertyValue1().toString());
		}
		Page<MedicalDepartment> page = service.findMenuPage(searchVo,
				currentPage, pageSize);
		if (page.getItems().size() > 0) {
			for (MedicalDepartment vo : page.getItems()) {
				User user = userService.getUserById(vo.getCreatePerson());
				if (user != null) {
					vo.setCreatePerson(user.getName());
				}
			}
		}
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;
	}

	@RequestMapping(value = "alllist")
	@ResponseBody
	public List<MedicalDepartment> alllist(String id) {

		List<MedicalDepartment> allField = service.findAllDepartment(id);

		return allField;
	}

	/**
	 * 添加数据
	 * 
	 * @param MedicalDepartment
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject add(MedicalDepartment MedicalDepartment)
			throws InvocationTargetException, IllegalAccessException {
		ResponseObject responseObj = new ResponseObject();
		MedicalDepartment entity = new MedicalDepartment();
		BeanUtils.copyProperties(entity, MedicalDepartment);
		entity.setName(MedicalDepartment.getName());
		entity.setRemark(MedicalDepartment.getRemark());
		if (entity.getName() == null) {
			throw new IllegalArgumentException("请输入科室名称");
		}
		MedicalDepartment existsEntity = service
				.findMedicalDepartmentByName(entity.getName());
		if (existsEntity != null && existsEntity.getDeleted() != true) {
			throw new IllegalArgumentException("已经存在");
		}
		entity.setCreatePerson(ManagerUserUtil.getName());
		entity.setCreateTime(new Date());
		// entity.setSort(service.getMaxSort()+1);
		// entity.setStatus(false);
		service.save(entity);
		responseObj.setSuccess(true);
		responseObj.setErrorMessage("新增科室成功");
		return responseObj;
	}

	/**
	 * 更新科室管理表
	 * 
	 * @param medicalDepartment
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject update(MedicalDepartment medicalDepartment)
			throws InvocationTargetException, IllegalAccessException {
		ResponseObject responseObject = new ResponseObject();
		MedicalDepartment entity = new MedicalDepartment();
		BeanUtils.copyProperties(entity, medicalDepartment);
		entity.setName(medicalDepartment.getName());
		if (medicalDepartment == null) {
			throw new IllegalArgumentException("不存在记录");
		}
		MedicalDepartment searchEntity = service.findById(medicalDepartment
				.getId());
		searchEntity.setName(medicalDepartment.getName());
		if (service.exists(searchEntity)) {
			throw new IllegalArgumentException("已经存在了");
		}
		MedicalDepartment me = service.findById(medicalDepartment.getId());
		me.setName(entity.getName());
		me.setRemark(entity.getRemark());
		service.update(me);
		responseObject.setSuccess(true);
		responseObject.setErrorMessage("修改完成!");
		return responseObject;
	}

	/**
	 * 删除数据
	 * 
	 * @param ids
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "deletes", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deletes(String ids) throws InvocationTargetException,
			IllegalAccessException {
		String msg = "";
		ResponseObject responseObject = new ResponseObject();
		if (ids != null) {
			String[] _ids = ids.split(",");
			msg = service.deletes(_ids);
		}
		responseObject.setSuccess(true);
		responseObject.setErrorMessage(msg);
		return responseObject;
	}

	/**
	 * 禁用or启用
	 * 
	 * @return
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateStatus(String id) {
		ResponseObject responseObj = new ResponseObject();
		try {
			service.updateStatus(id);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("操作成功");
		} catch (Exception e) {
			responseObj.setSuccess(false);
			responseObj.setErrorMessage("操作失败");
		}
		return responseObj;
	}

	@RequestMapping(value = "addDoctorDepartment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addDoctorDepartment(String id, String[] departmentId) {
		ResponseObject responseObject = new ResponseObject();
		service.addDoctorDepartment(id, departmentId);
		responseObject.setSuccess(true);
		responseObject.setResultObject("科室配置成功！");
		return responseObject;
	}

	/**
	 * 上移
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "upMove", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject upMoveRec(String id) {
		ResponseObject responseObj = new ResponseObject();
		service.updateSortUpRec(id);
		responseObj.setSuccess(true);
		return responseObj;
	}

	/**
	 * 下移
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "downMove", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject downMoveRec(String id) {
		ResponseObject responseObj = new ResponseObject();
		service.updateSortDownRec(id);
		responseObj.setSuccess(true);
		return responseObj;
	}

	/**
	 * Description：设置推荐值 creed: Talk is cheap,show me the code
	 * 
	 * @author name：wangyishuai <br>
	 *         email: wangyishuai@ixincheng.com
	 * @Date: 2018/3/9 14:13
	 **/
	@RequestMapping(value = "updateSort", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateRecommendSort(String id, Integer sort) {
		ResponseObject responseObject = new ResponseObject();
		service.updateSort(id, sort);
		responseObject.setSuccess(true);
		responseObject.setResultObject("修改成功!");
		return responseObject;
	}

}
