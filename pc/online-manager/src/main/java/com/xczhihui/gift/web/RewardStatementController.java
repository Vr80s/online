package com.xczhihui.gift.web;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.gift.service.RewardStatementService;
import com.xczhihui.gift.vo.RewardStatementVo;
import com.xczhihui.utils.Group;

/**
 * ClassName: RewardStatementController.java <br>
 * Description: 打赏流水<br>
 * Create by: name：yuxin <br>
 * email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月16日<br>
 */
@Controller
@RequestMapping("rewardStatement")
public class RewardStatementController extends AbstractController {
	protected final static String GIFT_PATH_PREFIX = "/gift";
	@Autowired
	private RewardStatementService rewardStatementService;
	@Autowired()
	private AttachmentCenterService att;
	@Value("${web.url}")
	private String weburl;

	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {

		// List<LecturerVo> lecturers = courseService.getLecturers();
		// request.setAttribute("lecturerVo", lecturers);

		return GIFT_PATH_PREFIX + "/rewardStatement";
	}

	// @RequiresPermissions("gift:menu:rewardStatement")
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo gifts(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		RewardStatementVo searchVo = new RewardStatementVo();
		Group startTimeGroup = groups.findByName("startTime");
		Group stopTimeGroup = groups.findByName("stopTime");
		Group searchTypeGroup = groups.findByName("searchType");
		Group searchConditionGroup = groups.findByName("searchCondition");

		if (startTimeGroup != null) {
			searchVo.setStartTime(DateUtil.parseDate(startTimeGroup
					.getPropertyValue1().toString(), "yyyy-MM-dd"));
		}

		if (stopTimeGroup != null) {
			searchVo.setStopTime(DateUtil.parseDate(stopTimeGroup
					.getPropertyValue1().toString(), "yyyy-MM-dd"));
		}

		if (searchTypeGroup != null) {
			searchVo.setSearchType(Integer.valueOf(searchTypeGroup
					.getPropertyValue1().toString()));
		}
		if (searchConditionGroup != null) {
			searchVo.setSearchCondition(searchConditionGroup
					.getPropertyValue1().toString());
		}
		Page<RewardStatementVo> page = rewardStatementService.findRewardPage(
				searchVo, currentPage, pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;

	}

}
