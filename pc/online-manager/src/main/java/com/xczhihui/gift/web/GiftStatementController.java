package com.xczhihui.gift.web;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.gift.vo.GiftStatementVo;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.common.util.DateUtil;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.web.controller.AbstractController;
import com.xczhihui.gift.service.GiftStatementService;

/**
 * ClassName: GiftStatementController.java <br>
 * Description: 礼物流水表<br>
 * Create by: name：yuxin <br>
 * email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月15日<br>
 */
@Controller
@RequestMapping("giftStatement")
public class GiftStatementController extends AbstractController {
	protected final static String GIFT_PATH_PREFIX = "/gift";
	@Autowired
	private GiftStatementService giftStatementService;
	@Autowired
	private AttachmentCenterService att;
	@Value("${web.url}")
	private String weburl;

	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		return GIFT_PATH_PREFIX + "/giftStatement";
	}

	// @RequiresPermissions("gift:menu:giftStatement")
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo gifts(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		GiftStatementVo searchVo = new GiftStatementVo();
		Group startTimeGroup = groups.findByName("startTime");
		Group stopTimeGroup = groups.findByName("stopTime");
		Group searchTypeGroup = groups.findByName("searchType");
		Group searchConditionGroup = groups.findByName("searchCondition");
		Group clientTypeGroup = groups.findByName("clientType");

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
		if (clientTypeGroup != null
				&& StringUtils.isNotBlank(clientTypeGroup.getPropertyValue1()
						.toString())) {
			searchVo.setClientType(Integer.parseInt(clientTypeGroup
					.getPropertyValue1().toString()));
		}
		Page<GiftStatementVo> page = giftStatementService.findGiftPage(
				searchVo, currentPage, pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;
	}
}
