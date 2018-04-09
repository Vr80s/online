package com.xczhihui.anchor.web;

import com.xczhihui.anchor.service.AnchorService;
import com.xczhihui.anchor.vo.AnchorIncomeVO;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.CourseAnchor;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 课程管理控制层实现类
 * @author yxd
 */

@Controller
@RequestMapping("anchor/courseAnchorIncome")
public class CourseAnchorIncomeController extends AbstractController{

	protected final static String CLOUD_CLASS_PATH_PREFIX = "/anchor/";
	@Autowired
	private AnchorService anchorService;

	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {
		return CLOUD_CLASS_PATH_PREFIX + "/courseAnchorIncome";
	}
	
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo courses(TableVo tableVo) {
	      int pageSize = tableVo.getiDisplayLength();
          int index = tableVo.getiDisplayStart();
          int currentPage = index / pageSize + 1;
          String params = tableVo.getsSearch();
          Groups groups = Tools.filterGroup(params);
          
          CourseAnchor searchVo=new CourseAnchor();

          Group name = groups.findByName("search_name");
          if (name != null) {
        	  searchVo.setName(name.getPropertyValue1().toString());
          }

          Group order = groups.findByName("search_type");
          if (order != null) {
        	  searchVo.setRemark(order.getPropertyValue1().toString());
          }

          Page<AnchorIncomeVO> page = anchorService.findCourseAnchorIncomePage(searchVo, currentPage, pageSize);
          int total = page.getTotalCount();
          tableVo.setAaData(page.getItems());
          tableVo.setiTotalDisplayRecords(total);
          tableVo.setiTotalRecords(total);
          return tableVo;
		
	}

    /**
     * 查看
     * @param id
     * @return
     */
    @RequestMapping(value = "findCourseAnchorById", method = RequestMethod.GET)
    @ResponseBody
    public CourseAnchor findCourseAnchorById(Integer id) {
        return anchorService.findCourseAnchorById(id);
    }

}
