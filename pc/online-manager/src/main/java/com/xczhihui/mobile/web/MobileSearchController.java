package com.xczhihui.mobile.web;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.mobile.service.MobileSearchService;
import com.xczhihui.mobile.vo.MobileSearchVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.service.UserService;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * 移动端搜索控制层实现类
 * 
 * @author Rongcai Kang
 */
@RestController()
@RequestMapping(value = "/mobile/search")
public class MobileSearchController {

	@Autowired
	private MobileSearchService service;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("mobile/mobileSearch");
		return mav;
	}

	/**
	 * 我们在增加线下课时，然后把这个城市同步到那边。我们在那边
	 */

	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo list(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);
		Group searchType = groups.findByName("search_type");
		MobileSearchVo m = new MobileSearchVo();
		if (searchType != null) {
			m.setSearchType(Integer.parseInt(searchType.getPropertyValue1()
					.toString()));
		}

		Page<MobileSearchVo> page = service.findMobileSearchPage(m,
				currentPage, pageSize);

		if (page.getItems().size() > 0) {
			for (MobileSearchVo vo : page.getItems()) {
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

	/**
	 * 添加数据
	 * 
	 * @param mobileSearchVo
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject add(MobileSearchVo mobileSearchVo)
			throws InvocationTargetException, IllegalAccessException {
		ResponseObject responseObj = new ResponseObject();

		if (mobileSearchVo.getName() == null) {
			throw new IllegalArgumentException("请输入搜索名称");
		}
		MobileSearchVo existsEntity = service.findMobileSearchByNameAndByType(
				mobileSearchVo.getName(), mobileSearchVo.getSearchType());
		if (existsEntity != null && existsEntity.isDelete() != true) {
			throw new IllegalArgumentException("已经存在");
		} else if (existsEntity != null && existsEntity.isDelete() == true) {
			existsEntity.setStatus(0);
			existsEntity.setDelete(false);
			service.update(existsEntity);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("新增搜索关键字成功");
			return responseObj;

		}
		mobileSearchVo.setCreatePerson(ManagerUserUtil.getName());
		mobileSearchVo.setCreateTime(new Date());
		mobileSearchVo.setSeq(service.getMaxSort() + 1);
		mobileSearchVo.setStatus(0);
		try {
			service.save(mobileSearchVo);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("新增搜索关键字成功");
		} catch (Exception e) {
			responseObj.setSuccess(false);
			responseObj.setErrorMessage("新增搜索关键字失败");
		}
		return responseObj;
	}

	/**
	 * 更新数据
	 * 
	 * @param mobileSearchVo
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject update(MobileSearchVo mobileSearchVo)
			throws InvocationTargetException, IllegalAccessException {
		ResponseObject responseObject = new ResponseObject();

		if (mobileSearchVo == null || mobileSearchVo.getName() == null) {
			throw new IllegalArgumentException("请输入必填项");
		}
		MobileSearchVo existsEntity = service.findMobileSearchByNameAndByType(
				mobileSearchVo.getName(), mobileSearchVo.getSearchType());
		if (existsEntity != null && existsEntity.isDelete() != true) {
			throw new IllegalArgumentException("已经存在");
		}
		MobileSearchVo ms = service.findById(mobileSearchVo.getId().toString());
		ms.setName(mobileSearchVo.getName());
		service.update(ms);
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
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateStatus(String id) {
		ResponseObject responseObj = new ResponseObject();
		try {
			service.updateStatus(id);
			responseObj.setSuccess(true);
			responseObj.setResultObject("操作成功");
		} catch (Exception e) {
			responseObj.setSuccess(false);
			responseObj.setErrorMessage("操作失败");
		}
		return responseObj;
	}

	/**
	 * 上移
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "upMove", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject upMove(Integer id) {
		ResponseObject responseObj = new ResponseObject();
		service.updateSortUp(id);
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
	public ResponseObject downMove(Integer id) {
		ResponseObject responseObj = new ResponseObject();
		service.updateSortDown(id);
		responseObj.setSuccess(true);
		return responseObj;
	}

}
