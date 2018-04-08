package com.xczhihui.gift.web;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.gift.service.RewardService;
import com.xczhihui.gift.vo.RewardVo;
import com.xczhihui.utils.Group;

/**
 * 课程管理控制层实现类
 * 
 * @author yxd
 */

@Controller
@RequestMapping("reward")
public class RewardController extends AbstractController {
	protected final static String GIFT_PATH_PREFIX = "/gift/";
	@Autowired
	private RewardService rewardService;
	@Autowired
	private AttachmentCenterService att;
	@Value("${online.web.url:http://www.ixincheng.com}")
	private String weburl;

	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {

		// List<LecturerVo> lecturers = courseService.getLecturers();
		// request.setAttribute("lecturerVo", lecturers);

		return GIFT_PATH_PREFIX + "/reward";
	}

	//@RequiresPermissions("gift:menu:reward")
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo gifts(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		RewardVo searchVo = new RewardVo();

		Group status = groups.findByName("search_status");

		if (status != null) {
			searchVo.setStatus((boolean) status.getPropertyValue1());
		}
		Page<RewardVo> page = rewardService.findRewardPage(searchVo, currentPage,
				pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;

	}

	/**
	 * 查看
	 * 
	 * @param id
	 * @return
	 */
	//@RequiresPermissions("gift:menu:reward")
	@RequestMapping(value = "findRewardById", method = RequestMethod.GET)
	@ResponseBody
	public RewardVo findRewardById(Integer id) {
		return rewardService.getRewardById(id);
	}

	/**
	 * 编辑
	 * 
	 * @param courseVo
	 * @return
	 */
	//@RequiresPermissions("gift:menu:reward")
	@RequestMapping(value = "updateRewardById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateRewardById(RewardVo giftVo) {
		ResponseObject responseObj = new ResponseObject();

		try {
			rewardService.updateReward(giftVo);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("修改成功");
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
	public ResponseObject updateStatus(Integer id) {
		rewardService.updateStatus(id);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}
	
	/**
	 * 设置分成
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateBrokerage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateBrokerage(String ids,String brokerage) {
		rewardService.updateBrokerage(ids,brokerage);
		return ResponseObject.newSuccessResponseObject("操作成功！");
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
		rewardService.updateSortUp(id);
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
		rewardService.updateSortDown(id);
		responseObj.setSuccess(true);
		return responseObj;
	}
}
