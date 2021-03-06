package com.xczhihui.headline.web;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.controller.AbstractController;
import com.xczhihui.headline.service.ArticleTypeService;
import com.xczhihui.headline.vo.ArticleTypeVo;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

/**
 * 博学社文章类型管理控制层代码
 *
 * @Author Fudong.Sun【】
 * @Date 2017/1/9 10:55
 */
@Controller
@RequestMapping("headline/articletype")
public class ArticleTypeController extends AbstractController {
    private final static String HEADLINE_PATH_PREFIX = "/headline/";

    @Autowired
    ArticleTypeService service;

    @RequestMapping(value = "index")
    public String index() {
        return HEADLINE_PATH_PREFIX + "/articletype";
    }

    /**
     * 查询文章分类列表数据
     *
     * @param tableVo
     * @return
     */
    @RequestMapping(value = "types")
    @ResponseBody
    public TableVo articleTypes(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);

        ArticleTypeVo typeVo = new ArticleTypeVo();
        Group sortType = groups.findByName("sortType");
        if (sortType != null) {
            typeVo.setSortType(Integer.parseInt(sortType.getPropertyValue1()
                    .toString()));
        }
        Page<ArticleTypeVo> page = service.findArticleTypePage(typeVo,
                currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    /**
     * 添加文章分类
     *
     * @param typeVo
     * @return
     */
    @RequestMapping(value = "addType", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addType(ArticleTypeVo typeVo) {
        service.saveType(typeVo);
        return ResponseObject.newSuccessResponseObject("添加成功！");
    }

    /**
     * 根据id查询修改文章分类
     *
     * @param typeVo
     * @return
     */
    @RequestMapping(value = "updateTypeById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateTypeById(ArticleTypeVo typeVo) {
        service.updateTypeById(typeVo);
        return ResponseObject.newSuccessResponseObject("修改成功！");
    }

    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException,
            IllegalAccessException {
        ResponseObject responseObject = new ResponseObject();
        Set<String> idSet = new HashSet<>();
        if (ids != null) {
            String[] idsArry = ids.split(",");
            idSet.addAll(Arrays.asList(idsArry));
            service.deletes(idSet);
        }
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("删除成功!");
        return responseObject;
    }

    /**
     * 修改状态(禁用or启用)
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(String id) {
        service.updateStatus(id);
        return ResponseObject.newSuccessResponseObject("操作成功！");
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
        service.updateSortUp(id);
        return ResponseObject.newSuccessResponseObject("上移成功！");
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
        service.updateSortDown(id);
        return ResponseObject.newSuccessResponseObject("下移成功！");
    }
}
