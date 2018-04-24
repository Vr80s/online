package com.xczhihui.headline.web;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.headline.service.BxsTagService;
import com.xczhihui.headline.vo.TagVo;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

/**
 * 博学社控制层实现类
 *
 * @author wgw
 */
@Controller
@RequestMapping("headline/tag")
public class BxsTagController {

    @Autowired
    BxsTagService tagService;

    @RequestMapping(value = "/tagIndex", method = RequestMethod.GET)
    public ModelAndView student(HttpServletRequest request, String page,
                                String menuName, String menuId) {
        return new ModelAndView("headline/tag");
    }

    /**
     * 获取标签列表信息
     *
     * @return
     */
    @RequestMapping(value = "/findTagList")
    @ResponseBody
    public TableVo findTagList(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);
        Group name = groups.findByName("nameSearch");
        Group sortType = groups.findByName("sortType");
        Group monthSort = groups.findByName("monthSort");

        TagVo searchVo = new TagVo();

        if (name != null) {
            searchVo.setName(name.getPropertyValue1().toString());
        }
        if (sortType != null
                && !"undefined".equals(sortType.getPropertyValue1())) {
            searchVo.setSortType(Integer.parseInt(sortType.getPropertyValue1()
                    .toString()));
        }
        if (monthSort != null
                && !"undefined".equals(monthSort.getPropertyValue1())) {
            searchVo.setLatelyNum(Integer.parseInt(monthSort
                    .getPropertyValue1().toString()));
        }

        Page<TagVo> page = tagService.findPage(searchVo, currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;

    }

    /**
     * 添加
     *
     * @param tagVo
     * @return
     */
    @RequestMapping(value = "addTag", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject add(TagVo tagVo) {
        ResponseObject responseObj = new ResponseObject();
        String message = tagService.addTag(tagVo);
        if ("新增成功！".equals(message)) {
            responseObj.setSuccess(true);
            responseObj.setErrorMessage(message);
        } else {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage(message);
        }
        return responseObj;
    }

    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException,
            IllegalAccessException {
        ResponseObject responseObject = new ResponseObject();
        if (ids != null) {
            String[] _ids = ids.split(",");
            tagService.deletes(_ids);
        }
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("删除成功！");
        return responseObject;
    }

    /**
     * 禁用or启用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(String id) {
        ResponseObject responseObj = new ResponseObject();
        try {
            tagService.updateStatus(id);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("操作成功");
        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
        return responseObj;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject update(TagVo tagVo) {
        ResponseObject responseObject = new ResponseObject();
        tagService.updateTag(tagVo);
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("修改成功!");
        return responseObject;
    }

}
