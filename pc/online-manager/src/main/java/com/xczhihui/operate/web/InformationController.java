package com.xczhihui.operate.web;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.operate.service.InformationService;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.common.support.domain.SystemVariate;
import com.xczhihui.bxg.common.support.service.SystemVariateService;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.operate.vo.InformationVo;
import com.xczhihui.operate.vo.TypeVo;

@Controller
@RequestMapping(value = "/operate/information")
public class InformationController {

    @Autowired
    private InformationService informationService;

    @Autowired
    SystemVariateService systemVariateService;

    /**
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/operate/information");
        List<SystemVariate> informationTypes = systemVariateService.getSystemVariatesByParentValue("informationType");
        request.setAttribute("informationTypes", informationTypes);
        return mav;
    }

    //@RequiresPermissions("operate:menu:information")
    @RequestMapping(value = "/findinformationList", method = RequestMethod.POST)
    @ResponseBody
    public TableVo findQuestionList(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
//         Groups groups = Tools.filterGroup(params);
        Group nameSearch = groups.findByName("nameSearch");
        Group informationTypeSearch = groups.findByName("informationTypeSearch");
        Group statusSearch = groups.findByName("statusSearch");
//         Group startTimeGroup = groups.findByName("startTime");
//         Group stopTimeGroup = groups.findByName("stopTime");
//         
        InformationVo searchVo = new InformationVo();
        if (nameSearch != null) {
            searchVo.setName(nameSearch.getPropertyValue1().toString());
        }
        if (informationTypeSearch != null) {
            searchVo.setInformationtype(informationTypeSearch.getPropertyValue1().toString());
        }
        if (statusSearch != null) {
            searchVo.setStatus(Integer.parseInt(statusSearch.getPropertyValue1().toString()));
        }
//         if(startTimeGroup!=null){
//              searchVo.setStartTime(DateUtil.parseDate(startTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
//         }
//         if(stopTimeGroup!=null){
//        	 searchVo.setStopTime(DateUtil.parseDate(stopTimeGroup.getPropertyValue1().toString(),"yyyy-MM-dd"));
//         }
        Page<InformationVo> page = informationService.findInformationPage(searchVo, currentPage, pageSize);

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
    //@RequiresPermissions("operate:menu:information")
    @RequestMapping(value = "addInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject add(InformationVo info) {
        ResponseObject responseObj = new ResponseObject();
        info.setDelete(false);
        info.setCreateTime(new Date());
        info.setCreatePerson(ManagerUserUtil.getUsername());
        try {
            informationService.addInfo(info);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("新增成功");
        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("新增失败");
            e.printStackTrace();
        }
        return responseObj;
    }

    /**
     * 编辑
     *
     * @param vo
     * @return
     */
    //@RequiresPermissions("course:menu:course")
    @RequestMapping(value = "updateInfoById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateCourseById(InformationVo info) {
        ResponseObject responseObj = new ResponseObject();
        informationService.updateInfo(info);
        responseObj.setSuccess(true);
        responseObj.setErrorMessage("修改成功！");
        return responseObj;
    }


    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException, IllegalAccessException {
        ResponseObject responseObject = new ResponseObject();
        if (ids != null) {
            String[] _ids = ids.split(",");
            informationService.deletes(_ids);
        }
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("删除完成!");
        return responseObject;
    }

    /**
     * 修改状态(禁用or启用)
     *
     * @param Integer id
     * @return
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(Integer id) {
        ResponseObject responseObject = new ResponseObject();
        String message = informationService.updateStatus(id);
        responseObject.setSuccess(true);
        if ("success".equals(message)) {
            responseObject.setErrorMessage("操作成功!");
        } else {
            responseObject.setErrorMessage(message);
        }

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
    public ResponseObject upMove(Integer id) {
        ResponseObject responseObj = new ResponseObject();
        informationService.updateSortUp(id);
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
        informationService.updateSortDown(id);
        responseObj.setSuccess(true);
        return responseObj;
    }

    /**
     * 获得分类列表
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getTypes", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject getTypes(Integer id) {
        return ResponseObject.newSuccessResponseObject
                (systemVariateService.getSystemVariatesByParentValue("informationType"));
    }

    /**
     * 修改分类
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "updateTypes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateTypes(TypeVo vo) {
        informationService.updateTypes(vo);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }
}
