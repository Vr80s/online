package com.xczhihui.bxg.online.manager.headline.web;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.manager.headline.service.ArticleService;
import com.xczhihui.bxg.online.manager.headline.vo.ArticleTypeVo;
import com.xczhihui.bxg.online.manager.headline.vo.ArticleVo;
import com.xczhihui.bxg.online.manager.headline.vo.TagVo;
import com.xczhihui.bxg.online.manager.support.shiro.ManagerUserUtil;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description：专栏
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/4/8 0008 上午 10:47
 **/
@Controller
@RequestMapping("headline/column")
public class ColumnController extends AbstractController{
	protected final static String headline_PATH_PREFIX = "/headline/";
	
	@Autowired
	private ArticleService articleService;

	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		List<ArticleTypeVo> articleTypes=articleService.getArticleTypes();
	 	List<TagVo> tags= articleService.getTags();
	 	request.setAttribute("articleTypes", articleTypes);
	 	request.setAttribute("tags", tags);
		return headline_PATH_PREFIX + "/columnList";
	}

	
}
