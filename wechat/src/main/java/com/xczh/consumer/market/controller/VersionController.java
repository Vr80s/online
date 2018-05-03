package com.xczh.consumer.market.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.service.VersionService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.VersionCompareUtil;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.VersionInfoVo;
import com.xczhihui.online.api.vo.LiveExamineInfo;

/**
 * @author liutao
 * @create 2017-09-07 20:58
 **/
@Controller
@RequestMapping("/bxg/version")
public class VersionController {


    @Autowired
    private VersionService versionService;
    
    @Autowired
	private OLAttachmentCenterService service;
	
	@Value("${returnOpenidUri}")
	private String returnOpenidUri;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(VersionController.class);

    @RequestMapping("checkUpdate")
    @ResponseBody
    public ResponseObject checkUpdate(HttpServletRequest req, 
    		HttpServletResponse res,
    		Map<String, String> params) throws Exception{


        String userVersion=req.getParameter("version");

        if(StringUtils.isBlank(userVersion)){
            return ResponseObject.newErrorResponseObject("参数[version]不能为空");
        }

        VersionInfoVo newVer=versionService.getNewVersion();
        VersionInfoVo defaultNoUpdateResult=new VersionInfoVo();
        defaultNoUpdateResult.setIsUpdate(false);
        if(newVer==null){
            return ResponseObject.newSuccessResponseObject(defaultNoUpdateResult);
        }
        LOGGER.info("version:"+userVersion);
        LOGGER.info("newVer.getVersion():"+newVer.getVersion());
        //对比版本号
        String newVersion=newVer.getVersion();
        int diff = VersionCompareUtil.compareVersion(newVersion, userVersion);
        if (diff <= 0) {
            return ResponseObject.newSuccessResponseObject(defaultNoUpdateResult);
        }
        newVer.setIsUpdate(true);
        
        return ResponseObject.newSuccessResponseObject(newVer);
    }

    @RequestMapping("addTipOff")
	public void addTipOff(HttpServletRequest req,
								  HttpServletResponse res, LiveExamineInfo liveExamineInfo,
								  @RequestParam MultipartFile [] files) throws IOException, SQLException, ServletException{
    	
    /*	ConfigUtil cfg = new ConfigUtil(req.getSession());
		String returnCodeUri = cfg.getConfig("returnCodeUri");*/
    	List<String> list;
    	String courseId = req.getParameter("courseId");
    	String label = req.getParameter("label");
    	String token = req.getParameter("token");
		try {
			String content = req.getParameter("content");
	    	CourseLecturVo cv =  null;
			list = uploadFileList(files,req);
			String imgStrs = "";
	    	int i = 0;
	    	for (String string : list) {
	    		if(i==list.size()){
	    			imgStrs+=string;
	    		}else{
	    			imgStrs+=string+",";
	    		}
	    		i++;
			}
	    	String teacherId =1+"";
	    	versionService.insertTipOff(content,courseId,label,teacherId,"",imgStrs);
	    	String str =  returnOpenidUri + "/xcviews/html/complaint_details.html?label="+label+"&falg=1";
	    	LOGGER.info(str);
	    	res.sendRedirect("/xcviews/html/complaint_details.html?label="+label+"&falg=1");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return ResponseObject.newErrorResponseObject("举报失败");
			res.sendRedirect("/xcviews/html/complaint_details.html?label="+label+"&falg=2");
		}
    	
	}
    
    /**
     * @param multipartFiles
     * @param request
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public  List<String> uploadFileList(MultipartFile multipartFiles[], HttpServletRequest request) 
    		throws IllegalStateException, IOException{
    	
    	String projectName="other";
		String fileType="1"; //图片类型了
        List<String> newFilePaths=new ArrayList<>();
        try {
            for(MultipartFile file:multipartFiles){
            	/**
            	 * 上传到文件服务器，并且返回一个路径
            	 */
            	String headImgPath = service.upload(null,projectName, file.getOriginalFilename(),file.getContentType(),
        				             file.getBytes(),fileType,null);

        		LOGGER.info("文件路径——path:"+headImgPath);
            	
            	newFilePaths.add(headImgPath);
            }
        }catch (IOException e){
            e.getMessage();
        }
        return newFilePaths;
    }


}
