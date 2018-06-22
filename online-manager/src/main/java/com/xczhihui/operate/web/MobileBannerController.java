package com.xczhihui.operate.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.anchor.service.AnchorService;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.enums.CourseStatus;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.course.vo.CourseVo;
import com.xczhihui.course.vo.MenuVo;
import com.xczhihui.menu.service.CommonMenuService;
import com.xczhihui.operate.service.MobileBannerService;
import com.xczhihui.operate.vo.MobileBannerVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

@Controller
@RequestMapping(value = "/operate/mobileBanner")
public class MobileBannerController {

    @Autowired
    private MobileBannerService mobileBannerService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private AnchorService anchorService;
    @Autowired
    private CommonMenuService commonMenuService;

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/operate/mobileBanner");
        List<MenuVo> menus = commonMenuService.list();
        List<CourseVo> courses = new ArrayList<>();
        if (!menus.isEmpty()) {
            courses = courseService.listByMenuId(menus.get(0).getId());
        }
        mav.addObject("courses", courses);
        mav.addObject("anchors", anchorService.list(1));
        mav.addObject("menus", menus);
        return mav;
    }

    // @RequiresPermissions("mobile:menu:banner")
    @RequestMapping(value = "/findMobileBannerList", method = RequestMethod.POST)
    @ResponseBody
    public TableVo findMobileBannerList(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group statusGroup = groups.findByName("search_status");
        Group nameGroup = groups.findByName("search_name");

        MobileBannerVo searchVo = new MobileBannerVo();

        if (statusGroup != null) {
            searchVo.setStatus(Integer.parseInt(statusGroup.getPropertyValue1()
                    .toString()));
        }

        if (nameGroup != null) {
            searchVo.setName(nameGroup.getPropertyValue1().toString());
        }

        Group bannerType = groups.findByName("banner_type");
        if (bannerType != null) {
            searchVo.setBannerType(Integer.parseInt(bannerType
                    .getPropertyValue1().toString()));
        }

        Page<MobileBannerVo> page = mobileBannerService.findMobileBannerPage(
                searchVo, currentPage, pageSize);

        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    /**
     * 添加
     *
     * @param vo
     * @return
     */
    // @RequiresPermissions("mobile:menu:banner")
    @RequestMapping(value = "/addMobileBanner", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addMobileBanner(MobileBannerVo mobileBannerVo,
                                          HttpServletRequest request) {
        mobileBannerVo.setCreatePerson(ManagerUserUtil.getUsername());
        handleMobileBannerVo(mobileBannerVo);
        mobileBannerService.addMobileBanner(mobileBannerVo);
        return ResponseObject.newSuccessResponseObject("新增成功!");
    }

    /**
     * 编辑
     *
     * @param vo
     * @return
     */
    // @RequiresPermissions("mobile:menu:banner")
    @RequestMapping(value = "updateMobileBannerById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateMobileBannerById(MobileBannerVo mobileBannerVo) {
        handleMobileBannerVo(mobileBannerVo);
        mobileBannerService.updateMobileBanner(mobileBannerVo);
        return ResponseObject.newSuccessResponseObject("修改成功!");
    }

    /**
     * 修改状态(禁用or启用)
     *
     * @param Integer id
     * @return
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(MobileBannerVo mobileBannerVo) {
        mobileBannerService.updateStatus(mobileBannerVo);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    /**
     * 批量逻辑删除
     *
     * @param Integer id
     * @return
     */
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) {
        ResponseObject responseObject = new ResponseObject();
        if (ids != null) {
            String[] _ids = ids.split(",");
            mobileBannerService.deletes(_ids);
        }
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("删除完成!");
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
    public ResponseObject upMove(String id) {
        ResponseObject responseObj = new ResponseObject();
        mobileBannerService.updateSortUp(id);
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
    public ResponseObject downMove(String id) {
        ResponseObject responseObj = new ResponseObject();
        mobileBannerService.updateSortDown(id);
        responseObj.setSuccess(true);
        return responseObj;
    }

    @RequestMapping(value = "clean", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject cleanData() {
        mobileBannerService.updateOldData();
        return ResponseObject.newSuccessResponseObject();
    }

    //TODO 兼容老版本处理 不考虑兼容老版本后可删除
    private MobileBannerVo handleMobileBannerVo(MobileBannerVo mobileBannerVo) {
        Integer linkType = mobileBannerVo.getLinkType();
        if (linkType != null) {
            String linkParam = mobileBannerVo.getLinkParam();
            if (linkParam != null) {
                if (linkType.equals(3)) {
                    mobileBannerVo.setUrl("course_id=" + linkParam);
                    //主讲人
                } else if (linkType.equals(4)) {
                    mobileBannerVo.setUrl("userLecturerId=" + linkParam);
                } else if (linkType.equals(5)) {
                    mobileBannerVo.setUrl(linkParam);
                }
            }
        }
        return mobileBannerVo;
    }
}
