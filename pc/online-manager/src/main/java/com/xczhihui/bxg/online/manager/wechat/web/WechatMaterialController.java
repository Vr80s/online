package com.xczhihui.bxg.online.manager.wechat.web;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.WechatMaterial;
import com.xczhihui.bxg.online.manager.support.shiro.ManagerUserUtil;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;
import com.xczhihui.bxg.online.manager.wechat.service.WechatMaterialService;
import com.xczhihui.user.center.utils.HttpUtil;

/**
 * 课程管理控制层实现类
 * 
 * @author yxd
 */

@Controller
@RequestMapping("wechat/menu")
public class WechatMaterialController extends AbstractController {
	
	protected final static String WechatMaterial_PATH_PREFIX = "/wechat/";
	@Autowired
	private WechatMaterialService WechatMaterialService;
	@Autowired
	private AttachmentCenterService att;
	@Value("${online.web.url:http://www.ixincheng.com}")
	private String weburl;

	
	@RequestMapping(value = "material")
	public String index(HttpServletRequest request) {

		System.out.println("[=[===========");
		return WechatMaterial_PATH_PREFIX + "/materialList";
	}
	@RequestMapping(value = "list")
	@ResponseBody
	public TableVo WechatMaterials(TableVo tableVo) {
		int pageSize = tableVo.getiDisplayLength();
		int index = tableVo.getiDisplayStart();
		int currentPage = index / pageSize + 1;
		String params = tableVo.getsSearch();
		Groups groups = Tools.filterGroup(params);
		WechatMaterial searchVo = new WechatMaterial();
		Page<WechatMaterial> page = WechatMaterialService.findWechatMaterialPage(searchVo, currentPage,
				pageSize);
		int total = page.getTotalCount();
		tableVo.setAaData(page.getItems());
		tableVo.setiTotalDisplayRecords(total);
		tableVo.setiTotalRecords(total);
		return tableVo;

	}

	/**
	 * 添加
	 * @param MaterialVo
	 * @return
	 */
	//@RequiresPermissions("WechatMaterial:menu")
	@RequestMapping(value = "addWechatMaterial", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject add(WechatMaterial wm) {
		
		ResponseObject responseObj = new ResponseObject();

	   /* String accessToken = new AccessTokenUtil().getAccessToken();  
	    String url_upload = String.format(MaterialInterface.upload_persistent_news_url, accessToken);  */
		
		
	    List<WechatMaterial> wechatMaterials = new ArrayList<WechatMaterial>();  
	    WechatMaterial wechatMaterial = new WechatMaterial();  
        wechatMaterial.setTitle("测试上传");  
        wechatMaterial.setThumbMediaId("6y0EBrCsG4Si29EjR7_uAA0zZmaUa37VIAAqNDbrjQE");
        wechatMaterial.setShowCoverPic(true);
        wechatMaterial.setDigest("摘要");  
        wechatMaterial.setContentSourceUrl("https://www.ixincheng.com");
        wechatMaterial.setContent("我看到一个东西哈哈！");  
        wechatMaterial.setAuthor("qiao");  
        
		
		String reqUrl ="https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=";
	    Map<String, Object> params = new HashMap<String, Object>();  
	    params.put("articles", wechatMaterials);  
	    
		//HttpUtil.doPostObject(reqUrl, params);
		

		
		return responseObj;
	}

	/**
	 * 查看
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "findMaterialById", method = RequestMethod.GET)
	@ResponseBody
	public WechatMaterial findWechatMaterialById(Integer id) {
		
		return WechatMaterialService.getWechatMaterialById(id);
	}

	/**
	 * 编辑
	 * 
	 * @param MaterialVo
	 * @return
	 */
	//@RequiresPermissions("WechatMaterial:menu")
	@RequestMapping(value = "updateWechatMaterialById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateWechatMaterialById(WechatMaterial WechatMaterial) {
		ResponseObject responseObj = new ResponseObject();

		try {
			//WechatMaterialService.updateWechatMaterial(WechatMaterial);
			responseObj.setSuccess(true);
			responseObj.setErrorMessage("修改成功");
		} catch (Exception e) {
			responseObj.setSuccess(false);
			responseObj.setErrorMessage("修改失败");
			e.printStackTrace();
		}
		return responseObj;
	}

	/**
	 * 修改状态(禁用or启用)
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateStatus(Integer id) {
		WechatMaterialService.updateStatus(id);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}

	/**
	 * 逻辑删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "deleteMaterialById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deleteMaterialById(Integer id) {
		WechatMaterialService.deleteWechatMaterialById(id);
		return ResponseObject.newSuccessResponseObject("操作成功！");
	}
	
	/**
	 * 设置分成
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateBrokerage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updateBrokerage(String ids,String brokerage) {
		WechatMaterialService.updateBrokerage(ids,brokerage);
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
	public ResponseObject upMove(Integer id) {
		ResponseObject responseObj = new ResponseObject();
		WechatMaterialService.updateSortUp(id);
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
		WechatMaterialService.updateSortDown(id);
		responseObj.setSuccess(true);
		return responseObj;
	}

	@RequestMapping(value = "deletes", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject deletes(String ids) throws InvocationTargetException,
			IllegalAccessException {
		ResponseObject responseObject = new ResponseObject();
		if (ids != null) {
			String[] _ids = ids.split(",");
			WechatMaterialService.deletes(_ids);
		}
		responseObject.setSuccess(true);
		responseObject.setErrorMessage("删除成功!");
		return responseObject;
	}

	@RequestMapping(value = "uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject uploadImg(String content) {
		String str = content.split("base64,")[1];
		byte[] b = org.apache.commons.codec.binary.Base64.decodeBase64(str);
		Attachment a = att.addAttachment(ManagerUserUtil.getId(),
				AttachmentType.ONLINE, "1.png", b, "image/png");
		if (a.getError() != 0) {
			return ResponseObject.newErrorResponseObject("上传失败！");
		}
		return ResponseObject.newSuccessResponseObject(a);
	}
}
