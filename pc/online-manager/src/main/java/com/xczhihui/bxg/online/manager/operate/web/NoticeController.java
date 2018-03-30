package com.xczhihui.bxg.online.manager.operate.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.manager.operate.service.NoticeService;
import com.xczhihui.bxg.online.manager.operate.vo.NoticeVo;
import com.xczhihui.bxg.online.manager.support.shiro.ManagerUserUtil;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

@Controller
@RequestMapping(value = "/operate/notice")
public class NoticeController{
	
	@Autowired
	private NoticeService noticeService;
	
	/**
     * 
     * @return
     */
    @RequestMapping(value = "/index")
    public ModelAndView index(){
         ModelAndView mav=new ModelAndView("/operate/notice");
         return mav;
    }

	//@RequiresPermissions("operate:menu:notice")
    @RequestMapping(value = "/findNoticeList", method = RequestMethod.POST)
    @ResponseBody
    public TableVo findNoticeList(TableVo tableVo) {
         int pageSize = tableVo.getiDisplayLength();
         int index = tableVo.getiDisplayStart();
         int currentPage = index / pageSize + 1;
         String params = tableVo.getsSearch();
         Groups groups = Tools.filterGroup(params);
         Group noticeContentGroup = groups.findByName("search_noticeContent");
         Group statusGroup = groups.findByName("search_status");

         NoticeVo searchVo=new NoticeVo();
         if(noticeContentGroup!=null){
              searchVo.setNoticeContent(noticeContentGroup.getPropertyValue1().toString());
         }
         if(statusGroup!=null){
        	 searchVo.setStatus(Integer.parseInt(statusGroup.getPropertyValue1().toString()));
         }
         
         Page<NoticeVo> page = noticeService.findNoticePage(searchVo, currentPage, pageSize);

         int total = page.getTotalCount();
         tableVo.setAaData(page.getItems());
         tableVo.setiTotalDisplayRecords(total);
         tableVo.setiTotalRecords(total);
         return tableVo;
    }
    
    /**
	 * 添加
	 * @param vo
	 * @return
	 */
	//@RequiresPermissions("operate:menu:notice")
	@RequestMapping(value = "/addNotice", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addNotice(NoticeVo noticeVo,HttpServletRequest request){
		noticeVo.setCreatePerson(ManagerUserUtil.getUsername());
		noticeService.addNotice(noticeVo);
        return ResponseObject.newSuccessResponseObject("新增成功!");
    }

	/**
	 * 编辑
	 * @param vo
	 * @return
	 */
	//@RequiresPermissions("operate:menu:notice")
	@RequestMapping(value = "updateNoticeById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateNoticeById (NoticeVo noticeVo){
		noticeService.updateNotice(noticeVo);
		return ResponseObject.newSuccessResponseObject("修改成功!");
	}

	/**
	 * 修改状态(禁用or启用)
	 * @param Integer id
	 * @return
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(NoticeVo noticeVo){
		noticeService.updateStatus(noticeVo);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}
	
	/**
	 * 批量逻辑删除
	 * @param Integer id
	 * @return
	 */
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids){
         if(ids!=null) {
              String[] _ids = ids.split(",");
              noticeService.deletes(_ids);
         }
         ResponseObject responseObject = ResponseObject.newSuccessResponseObject("删除成功！");
         responseObject.setErrorMessage("删除成功");
         return responseObject;
    }
}
