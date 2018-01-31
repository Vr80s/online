package com.xczhihui.bxg.online.manager.gift.web;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.manager.gift.service.GiftService;
import com.xczhihui.bxg.online.manager.gift.service.GiftStatementService;
import com.xczhihui.bxg.online.manager.gift.vo.GiftStatementVo;
import com.xczhihui.bxg.online.manager.gift.vo.GiftVo;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

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
	@Value("${online.web.url:http://www.ixincheng.com}")
	private String weburl;

	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {

		// List<LecturerVo> lecturers = courseService.getLecturers();
		// request.setAttribute("lecturerVo", lecturers);

		return GIFT_PATH_PREFIX + "/giftStatement";
	}

	//@RequiresPermissions("gift:menu:giftStatement")
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
		
		if (startTimeGroup != null) {
			searchVo.setStartTime(DateUtil.parseDate(startTimeGroup
					.getPropertyValue1().toString(), "yyyy-MM-dd"));
		}

		if (stopTimeGroup != null) {
			searchVo.setStopTime(DateUtil.parseDate(stopTimeGroup
					.getPropertyValue1().toString(), "yyyy-MM-dd"));
		}
		

        if(searchTypeGroup!=null){
       	 searchVo.setSearchType(Integer.valueOf(searchTypeGroup.getPropertyValue1().toString()));
        }
        if(searchConditionGroup!=null){
       	 searchVo.setSearchCondition(searchConditionGroup.getPropertyValue1().toString());
        }
		Page<GiftStatementVo> page = giftStatementService.findGiftPage(searchVo, currentPage,pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;

	}

}
