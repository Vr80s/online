package com.xczhihui.bxg.online.manager.common.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.user.center.bean.Token;
import com.xczhihui.user.center.web.utils.UCCookieUtil;
/**
 * 百度富文本编辑器（ueditor）
 * @author Haicheng Jiang
 *
 */
@Controller
@RequestMapping("/link/word")
public class LinkAddressController {
	
	@Autowired
	private AttachmentCenterService service;
	/**
	 * 下载链接操作文档
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "upload")
	public void upload(HttpServletRequest request,HttpServletResponse res,
			@RequestParam("file") MultipartFile file) throws Exception {
		
		System.out.println("request.getServletContext().getRealPath"+request.getServletContext().getRealPath("/template/"));
	    ///WEB-INF/template
		 //上传文件路径
		String filePath = request.getServletContext().getRealPath("/WEB-INF/template");
		//如果文件不为空，写入上传路径
        if(!file.isEmpty()) {
        	//删除原来的
        	deleteFile(filePath+File.separator+ "链接地址添加文档.docx");
            //上传文件路径
            //String path = request.getServletContext().getRealPath("/template/");
            //上传文件名
            String filename = file.getOriginalFilename();
            filename = "链接地址添加文档.docx";
            File filepath = new File(filePath,filename);
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) { 
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            file.transferTo(new File(filePath + File.separator + filename));
            //return ResponseObject.newSuccessResponseObject("上传成功");
        } else {
        	//return ResponseObject.newErrorResponseObject("上传失败");
        }
        res.sendRedirect("http://localhost:28080/home#/operate/mobileBanner/index");
	}
	
	@RequestMapping(value="/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request,
            @RequestParam("filename") String filename, Model model)throws Exception {
    	
    
       //String topicname=new String(filename.getBytes("ISO-8859-1"),"UTF-8"); 
       System.out.println("filename:"+filename);
       //下载文件路径
       String path = request.getServletContext().getRealPath("/WEB-INF/template");
       System.out.println("下载文件路径"+path);
       File file = new File(path + File.separator + filename);
       HttpHeaders headers = new HttpHeaders();  
       //下载显示的文件名，解决中文名称乱码问题  
       String downloadFielName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
       //通知浏览器以attachment（下载方式）打开图片
       headers.setContentDispositionFormData("attachment", downloadFielName); 
       //application/octet-stream ： 二进制流数据（最常见的文件下载）。
       headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
       return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
               headers, HttpStatus.CREATED);  
    }
	
    
    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
    
}
