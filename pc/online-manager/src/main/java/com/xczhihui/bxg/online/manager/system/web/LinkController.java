package com.xczhihui.bxg.online.manager.system.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.Otherlink;
import com.xczhihui.bxg.online.manager.support.shiro.ManagerUserUtil;
import com.xczhihui.bxg.online.manager.system.service.LinkService;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.PageVo;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

/**
 * 友情链接
 *
 * @author duanqh
 */
@Controller
@RequestMapping("link")
public class LinkController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LinkService service;

    /**
     * 跳转到页面
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        return "link/index";
    }


    /**
     * 点击菜单获取列表
     *
     * @param request
     * @param tableVo
     * @return
     */
    @RequestMapping(value = "initLink")
    @ResponseBody
    public TableVo loadLink(HttpServletRequest request, TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        PageVo page = null;
        try {
            Groups groups = Tools.filterGroup(params);
            groups.Add(new Groups("a.is_delete", "0"));
            groups.setOrderby("a.sort");
            groups.setOrder(true);
            page = new PageVo(pageSize, currentPage);
            service.findPageLink(groups, page);

            tableVo.setAaData(page.getItems());
            tableVo.setiTotalDisplayRecords(page.getTotalCount());
            tableVo.setiTotalRecords(page.getTotalCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableVo;
    }
    /**
     * 点击菜单获取列表
     * @param request
     * @param tableVo
     * @param menuId
     * @return
     */
    /*@RequestMapping(value = "initLink")
	@ResponseBody
	public TableVo loadLink(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int pageNumber = index / pageSize + 1;
		String params = tableVo.getsSearch();
		String urlOrcreatPer = "";
		String timeStart = null;
		String timeEnd = null;
		HashMap<String, Object> paramMap = null;
	
		try{
			Groups groups = Tools.filterGroup(params);
			
			for (Group g : groups.getGroupList()) {
				if ("urlOrcreatPer".equals(g.getPropertyName())) {
					urlOrcreatPer = g.getPropertyValue1().toString();
				}
				if ("timeStart".equals(g.getPropertyName())) {
					timeStart = g.getPropertyValue1().toString();
				
				}
				if ("timeEnd".equals(g.getPropertyName())) {
					timeEnd = g.getPropertyValue1().toString();
					
				}
			}
			paramMap = new HashMap<String,Object>();
			paramMap.put("urlOrcreatPer", urlOrcreatPer);
			paramMap.put("timeEnd", timeEnd);
			paramMap.put("timeStart", timeStart);
			
			Page<OtherLinkVo> page = service.findOtherLinkPage(paramMap, pageNumber, pageSize);
			tableVo.setAaData(page.getItems());
			tableVo.setiTotalDisplayRecords(page.getTotalCount());
			tableVo.setiTotalRecords(page.getTotalCount());
		}catch(Exception e){
			e.printStackTrace();
		}
		return tableVo;
	}*/

    /**
     * 添加
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "addLink", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addVideo(Otherlink vo) {
        ResponseObject responseObj = new ResponseObject();
        try {
            vo.setCreateTime(new Date());
            vo.setCreatePerson(ManagerUserUtil.getName());
            vo.setStatus("0");
            service.addLink(vo);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            this.logger.error(e.getMessage());
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
        return responseObj;
    }


    /**
     * 单条删除
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject del(HttpServletRequest request, Model model) {
        ResponseObject responseObj = new ResponseObject();
        try {
            String id = ServletRequestUtils.getRequiredStringParameter(request, "id");
            service.deleteById(Integer.valueOf(id));
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("操作成功");
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
        return responseObj;
    }

    /**
     * 多条删除
     *
     * @param request
     * @param model
     * @param ids
     * @return
     */
    @RequestMapping(value = "deleteBatch", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(HttpServletRequest request, Model model,
                                  String ids) {
        ResponseObject responseObj = new ResponseObject();
        try {
            String[] idArr = ids.split(",");
            service.deleteBatch(idArr);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("操作成功");
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
        return responseObj;
    }

    /**
     * 修改
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateLink(HttpServletRequest request, Model model, Otherlink olink) {
        ResponseObject responseObj = new ResponseObject();
        try {
            Otherlink link = service.getLinkById(olink.getId());
            link.setName(olink.getName());
            link.setUrl(olink.getUrl());
            link.setSort(olink.getSort());
            if (olink.getLogo() != null && !"".equals(olink.getLogo())) {
                link.setLogo(olink.getLogo());
            }
            service.update(link);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("操作成功");
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
        return responseObj;
    }

    /**
     * 修改状态
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "updateStatus")
    @ResponseBody
    public ResponseObject updateStatus(Integer id) {
        ResponseObject responseObj = new ResponseObject();
        try {
            service.updateStatus(id);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("操作成功");
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
        return responseObj;
    }

    /**
     * 获取排序列表
     *
     * @return
     */
    @RequestMapping(value = "sortlist")
    @ResponseBody
    public Object sortList() {
        return service.getSortlist();
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
        try {
            logger.info("上移任务开始：" + id);
            service.updateUpMove(id);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("操作成功");
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
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
        try {
            logger.info("下移任务开始：" + id);
            service.updateDownMove(id);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("操作成功");
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("操作失败");
        }
        return responseObj;
    }

    /**
     * 验证位置是否重复
     *
     * @return
     */
    @RequestMapping("verifySort")
    @ResponseBody
    public boolean verifySort(Integer sort, Integer id) {
        logger.info("验证位置是否重复开始：" + "[sort:" + sort + "," + "id:" + id + "]");
        return service.findSort(sort, id);
    }

}
