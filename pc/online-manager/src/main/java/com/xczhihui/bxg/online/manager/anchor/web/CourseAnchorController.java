package com.xczhihui.bxg.online.manager.anchor.web;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.api.po.CourseAnchor;
import com.xczhihui.bxg.online.common.domain.CourseApplyInfo;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.manager.anchor.service.AnchorService;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseApplyService;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 课程管理控制层实现类
 * @author yxd
 */

@Controller
@RequestMapping("anchor/courseAnchor")
public class CourseAnchorController extends AbstractController{

	protected final static String CLOUD_CLASS_PATH_PREFIX = "/anchor/";
	@Autowired
	private AnchorService anchorService;

    @Autowired
    private CourseApplyService courseApplyService;
    @Autowired
    private CourseService courseService;

	@RequestMapping(value = "index")
	public String index(HttpServletRequest request) {return CLOUD_CLASS_PATH_PREFIX + "/courseAnchor";}
	
	//@RequiresPermissions("cloudClass:menu:course")
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

          Group searchLecturer = groups.findByName("search_type");
          if (searchLecturer != null) {
        	  searchVo.setType(Integer.valueOf(searchLecturer.getPropertyValue1().toString()));
          }

          Page<CourseAnchor> page = anchorService.findCourseAnchorPage(searchVo, currentPage, pageSize);
          int total = page.getTotalCount();
          tableVo.setAaData(page.getItems());
          tableVo.setiTotalDisplayRecords(total);
          tableVo.setiTotalRecords(total);
          return tableVo;
		
	}

    @RequestMapping(value = "recList")
    @ResponseBody
    public TableVo recList(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);

        CourseAnchor searchVo=new CourseAnchor();
//
//        Group name = groups.findByName("search_name");
//        if (name != null) {
//            searchVo.setName(name.getPropertyValue1().toString());
//        }
//
//        Group searchLecturer = groups.findByName("search_type");
//        if (searchLecturer != null) {
//            searchVo.setType(Integer.valueOf(searchLecturer.getPropertyValue1().toString()));
//        }

        Page<CourseAnchor> page = anchorService.findCourseAnchorRecPage(searchVo, currentPage, pageSize);
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
    //@RequiresPermissions("cloudClass:menu:course")
    @RequestMapping(value = "findCourseAnchorById", method = RequestMethod.GET)
    @ResponseBody
    public CourseAnchor findCourseAnchorById(Integer id) {
        return anchorService.findCourseAnchorById(id);
    }

    /**
     * 编辑
     * @return
     */
    //@RequiresPermissions("cloudClass:menu:course")
    @RequestMapping(value = "updateCourseById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateCourseById (CourseAnchor courseAnchor){
        ResponseObject responseObj = new ResponseObject();

        anchorService.updateCourseAnchor(courseAnchor);
        responseObj.setSuccess(true);
        responseObj.setErrorMessage("修改成功");
        return responseObj;
    }

    /**
     * 更改权限
     * @return
     */
    //@RequiresPermissions("cloudClass:menu:course")
    @RequestMapping(value = "editPermissions")
    @ResponseBody
    public ResponseObject editPermissions (Integer id){
        ResponseObject responseObj = new ResponseObject();
        anchorService.updatePermissions(id);
        responseObj.setSuccess(true);
        responseObj.setErrorMessage("修改成功");
        return responseObj;
    }

    /**
     * Description：更新推荐状态
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/2/24 0024 下午 10:17
     **/
    @RequestMapping(value = "updateRec", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateRec(String ids,int isRec) {
        ResponseObject responseObject=new ResponseObject();
        if(ids!=null) {
            String[] idArr = ids.split(",");
            anchorService.updateRec(idArr,isRec);
        }
        responseObject.setSuccess(true);
        if(isRec==1){
            responseObject.setErrorMessage("主播推荐成功");
        }else{
            responseObject.setErrorMessage("取消推荐成功");
        }

        return responseObject;
    }

    /**
     * 上移
     * @param id
     * @return
     */
    @RequestMapping(value = "upMoveRec", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject upMoveRec(Integer id) {
        ResponseObject responseObj = new ResponseObject();
        anchorService.updateSortUpRec(id);
        responseObj.setSuccess(true);
        return responseObj;
    }

    /**
     * 下移
     * @param id
     * @return
     */
    @RequestMapping(value = "downMoveRec", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject downMoveRec(Integer id) {
        ResponseObject responseObj = new ResponseObject();
        anchorService.updateSortDownRec(id);
        responseObj.setSuccess(true);
        return responseObj;
    }


    @RequestMapping(value = "anchorCourse")
    public String anchorCourse(HttpServletRequest request,String userId,String anchorNname) {
        request.setAttribute("userId", userId);
        request.setAttribute("anchorNname", anchorNname);
        List<Menu> menuVos= courseService.getfirstMenus(null);
        request.setAttribute("menuVo", menuVos);
        return CLOUD_CLASS_PATH_PREFIX + "/anchorCourseList";
    }
    /**
     * Description：获取主播的课程列表
     * creed: Talk is cheap,show me the code
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/3/15 21:19
     **/
    @RequestMapping(value = "courseList")
    @ResponseBody
    public TableVo courseDetail(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);

        CourseApplyInfo courseApplyInfo =new CourseApplyInfo();
        //课程名查找
        Group courseName = groups.findByName("search_courseName");
        if (courseName != null) {
            courseApplyInfo.setTitle(courseName.getPropertyValue1().toString());
        }
        // 审核状态 0未通过 1通过 2未审核
        Group search_applyStatus = groups.findByName("search_applyStatus");
        if (search_applyStatus!=null && StringUtils.isNotBlank(search_applyStatus.getPropertyValue1().toString())) {
            courseApplyInfo.setApplyStatus(Integer.valueOf(search_applyStatus.getPropertyValue1().toString()));
        }
        // 课程类型  1.直播 2.点播 3.线下课
        Group search_courseForm = groups.findByName("search_courseForm");
        if (search_courseForm!=null && StringUtils.isNotBlank(search_courseForm.getPropertyValue1().toString())) {
            courseApplyInfo.setCourseForm(Integer.valueOf(search_courseForm.getPropertyValue1().toString()));
        }
        // 是否上架（所有、已上架、未上架）；禁用0，启用，1
        Group search_status = groups.findByName("search_status");
        if (search_status!=null && StringUtils.isNotBlank(search_status.getPropertyValue1().toString())) {
            courseApplyInfo.setStatus(Integer.valueOf(search_status.getPropertyValue1().toString()));
        }

        Group userId = groups.findByName("userId");
        if (userId != null) {
            courseApplyInfo.setUserId(userId.getPropertyValue1().toString());
        }
        Page<CourseApplyInfo> page = courseApplyService.findCoursePageByUserId(courseApplyInfo, currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    /**
     * Description：设置主播推荐值
     * creed: Talk is cheap,show me the code
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/3/17 15:05
     **/
    @RequestMapping(value = "updateRecommendSort", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateRecommendSort(Integer id,Integer recommendSort) {
        ResponseObject responseObject=new ResponseObject();
        courseApplyService.updateRecommendSort(id,recommendSort);
        responseObject.setSuccess(true);
        responseObject.setResultObject("修改成功!");
        return responseObject;
    }
}
