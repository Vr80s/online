package com.xczhihui.bxg.online.manager.cloudClass.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.cloudClass.service.EssenceRecommendService;
import com.xczhihui.bxg.online.manager.cloudClass.service.PublicCourseService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

/**
 * 精品课程管理控制层实现类
 * @author yxd
 */

@Controller
@RequestMapping("essencerecommend/course")
public class EssenceRecommendController {
	protected final static String PUBLIC_CLASS_PATH_PREFIX = "/cloudClass/";
	@Autowired
	private PublicCourseService publicCourseService;
	@Autowired
	private CourseService courseService;



	@Autowired
	private EssenceRecommendService ssenceRecommenedService;
	
	
	@Value("${online.web.publiccloud.courseType}")
	private String courseType;
	@Value("${online.web.publiccloud.courseTypeId}")
	private String courseTypeId;
	
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {

		List<Menu> menuVos= courseService.getfirstMenus(null);
		request.setAttribute("menuVo", menuVos);
		
		return PUBLIC_CLASS_PATH_PREFIX + "/essenceRcommend";
	
	}
	
	@RequiresPermissions("cloudClass:menu:essenceRecommend")
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo courses(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);

		
        CourseVo searchVo=new CourseVo();
        //课程名查找
//        Group courseName = groups.findByName("search_courseName");
//        if (courseName != null) {
//      	  searchVo.setCourseName(courseName.getPropertyValue1().toString());
//        }

		//查询学科
        Group menuId = groups.findByName("menu_id");
        if (menuId != null) {
      	  searchVo.setMenuId(Integer.valueOf(menuId.getPropertyValue1().toString()));
        }
        
		//是否为精品推荐   
        Group essenceSort = groups.findByName("essence_sort");
        if (essenceSort != null) {
      	  searchVo.setEssenceSort(Integer.valueOf(essenceSort.getPropertyValue1().toString()));
        }
        
        
		Page<CourseVo> page = ssenceRecommenedService.findCoursePage(searchVo,
				currentPage, pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;
		
	}
	/**
	 * 精品推荐
	 * @param ids
	 * @param isRec
	 * @return
	 */
	@RequestMapping(value = "updateEssenceRec", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateCityRec(String ids,int isRec) {
		ResponseObject responseObject=new ResponseObject();
		if(ids!=null) {
			String[] _ids = ids.split(",");
			if(ssenceRecommenedService.updateEssenceRec(_ids,isRec))
			{
				responseObject.setSuccess(true);
				responseObject.setErrorMessage("操作成功!");
			}else{
				responseObject.setSuccess(false);
				responseObject.setErrorMessage("推荐失败!");
			}
		}
		return responseObject;
	}

	/**
     * 上移
     * @param id
     * @return
     */
    @RequestMapping(value = "upMove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject upMove(Integer id) {
         ResponseObject responseObj = new ResponseObject();
		ssenceRecommenedService.updateSortUp(id);
         responseObj.setSuccess(true);
         return responseObj;
    }
    
	/**
     * 下移
     * @param id
     * @return
     */
    @RequestMapping(value = "downMove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject downMove(Integer id) {
         ResponseObject responseObj = new ResponseObject();
		ssenceRecommenedService.updateSortDown(id);
         responseObj.setSuccess(true);
         return responseObj;
    }
    

}
