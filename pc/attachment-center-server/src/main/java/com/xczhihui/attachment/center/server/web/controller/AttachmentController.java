package com.xczhihui.attachment.center.server.web.controller;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.xczhihui.attachment.center.server.bean.Attachment1;
import com.xczhihui.attachment.center.server.service.AttachmentService;
import com.xczhihui.attachment.center.server.utils.ZIPUtil;
import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.FileUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 附件中心
 * @author liuxiaodong,jianghaicheng
 */
@Controller
@RequestMapping("attachment")
public class AttachmentController {
	
	@Autowired
	private AttachmentService attachmentService;
	
	@Value("${picture.path:/data/picture/}")
	private String picturePath = "/data/picture/";
	
	
	/**
	 *  保存完整的附件实体信息，包括id,在双元285内网附件改造上传的时添加，用于接收内网传过来的保存后的完整附件信息
	 * @param attachment
	 * @param request
	 */
	@RequestMapping(value = "saveFullAttachmentData")
	@ResponseBody
	public void saveFullAttachmentData(String id,String createPerson, String createTime, String businessType, 
			String fileName, String filePath, String  fileSize ,String fileType ,String orgFileName ) {
		Attachment1 attachment = new Attachment1();
		attachment.setId(id);
		attachment.setCreatePerson(createPerson);
		attachment.setCreateTime(DateUtil.parseDate(createTime,null));
		attachment.setBusinessType(Integer.valueOf(businessType));
		attachment.setFileName(fileName);
		attachment.setFilePath(filePath);
		attachment.setFileSize(Integer.valueOf(fileSize));
		attachment.setFileType(fileType);
		attachment.setOrgFileName(orgFileName);
		attachment.setDelete(false);
		attachmentService.saveFullAttachment(attachment);
		System.out.println(attachment.getId());
	}
	
	/**
	 * 附件中心文件上传总接口 姜海成
	 * @param createUserId 当前登录用户id
	 * @param fileType 1图片，2附件
	 * @param projectName dual双元，univ院校，online在线，kcenter知识中心，必传
	 * @param attachment 文件域名称，固定为：<input type="file" name="attachment">
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "upload")
	@ResponseBody
	public Attachment upload(@RequestParam(value = "createUserId", required = false) String createUserId,
			@RequestParam(value = "fileType", required = true) String fileType,
			@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "attachment", required = true) MultipartFile attachment,
			HttpServletRequest request ) {
		try {
			//上传图片，返回图片的访问网址：
			if ("1".equals(fileType)) {
				return this.uploadImage(projectName, attachment);
				//上传附件，返回附件id等信息：
			} else if("2".equals(fileType)){
				return this.uploadFile(createUserId,projectName, attachment);
			} else {
				return new Attachment(1,"请传入fileType参数，1图片，2附件");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Attachment(1,"服务器错误！"+e.getMessage());
		}
	}
	
	/**
	 * 下载附件 姜海成
	 * @param aid 附件id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "download")
	public String downloadFile(@RequestParam(value = "aid", required = true) String aid,
			HttpServletRequest request, HttpServletResponse response) {
		OutputStream out = null;
		try {
			Attachment att = attachmentService.getAttachment(aid);
			String fileName = att.getOrgFileName();
			// 下载乱码解决
			String agent = request.getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("MSIE") == -1) {// !IE
				fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} else { // IE
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
			response.setHeader("Content-Disposition", "attachment; filename=\"" +  fileName+"\"");
			response.setContentType("multipart/form-data");
			out = response.getOutputStream();
			byte[] b = attachmentService.getFileData(att);
			if(b==null){
				return null;
			}
			response.addIntHeader("Content-Length", b.length);
			out.write(b);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 获得json格式的附件信息
	 * @param aid
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "getAttachmentJson")
	public void getAttachmentJson(String aid,HttpServletResponse response) throws Exception{
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		if (!StringUtils.hasText(aid)) {
			response.getWriter().write(gson.toJson(new Attachment(1,"附件ID不能为空！")));
			return;
		}
		
		Attachment att = attachmentService.getAttachment(aid);
		if (att == null) {
			att = new Attachment(1,"查无此附件：\""+aid+"\"！");
		}
		
		//防止中文乱码
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setContentType("text/html;charset=UTF-8"); 
		response.getWriter().write(gson.toJson(att));
	}
	
	/**
	 * 上传文件 姜海成
	 * @param userId
	 * @param type
	 * @param attachmentFile
	 * @return
	 * @throws Exception
	 */
	public Attachment uploadFile(String userId,String type,MultipartFile attachmentFile) throws Exception{
		AttachmentType atype = AttachmentType.valueOf(type.toUpperCase());
		Attachment att = this.attachmentService.addAttachment(userId, atype, attachmentFile.getOriginalFilename(),attachmentFile.getBytes());
		return att;
	}
	/**
	 * 上传图片 姜海成
	 * @param type
	 * @param attachmentFile
	 * @return
	 * @throws Exception
	 */
	public Attachment uploadImage(String type,MultipartFile attachmentFile) throws Exception{
		Attachment attachment = attachmentService.addImage(AttachmentType.valueOf(type.toUpperCase()), attachmentFile.getOriginalFilename(),attachmentFile.getBytes());
		return attachment;
	}
	/**
	 * 按id删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteById")
	@ResponseBody
	public Attachment deleteById(String id) throws Exception{
		return attachmentService.deleteAttachment(id);
	}
	/**
	 * 按文件名删除
	 * @param fileName
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteByFileName")
	@ResponseBody
	public Attachment deleteByFileName(String fileName,String type) throws Exception{
		return attachmentService.deleteAttachment(fileName, AttachmentType.valueOf(type));
	}
	

	
	/***
	 * 下载学生作业附件
	 * @param paramString json格式:[{"name":"学生姓名","stNo":"学号","qitems":[{"aid":"附件ID","qnum":"题号","qtype":"题型"}]}]
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "downloadAttFiles")
	public void downloadAttachmentFiles(String paramString,HttpServletRequest request, HttpServletResponse response){
		OutputStream resout = null;
		if(!"".equals(paramString) && paramString!=null){
			String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
			JSONArray jarr = JSONArray.fromObject(paramString);
			if(jarr!=null){
				try{
					String rootpath = attachmentService.getAttachmentRootPath();
					String attpath = rootpath + "作业附件-"+today;
					for(int i=0;i<jarr.size();i++){
						JSONObject ji = jarr.getJSONObject(i);
//						JSONObject stu = ji.getJSONObject("stu");
						String name = ji.getString("name");
						String stno = ji.getString("stNo");
						String path = attpath +"/"+name+"-"+stno;
						File fdir = new File(path);
						if(!fdir.exists()){
							fdir.mkdirs();
						}
						JSONArray att = ji.getJSONArray("qitems");
						if(att!=null){
							for(int k=0;k<att.size();k++){
								JSONObject attobj = att.getJSONObject(k);
								String type = attobj.getString("qtype");
								String qnum = attobj.getString("qnum");
								String attid = attobj.getString("aid");
								Attachment attchment = attachmentService.getAttachment(attid);
								if(attchment!=null){
									File f = new File(rootpath+attchment.getFilePath()+"/"+attchment.getFileName());
									String n = f.getName();
									String oname = attchment.getOrgFileName();
									String exten ="";
									if("".equals(n) || n==null){
										if("".equals(oname) || oname==null){
											exten = "."+attchment.getFileType();
										}else{
											String s0 = n.substring(oname.lastIndexOf(".")+1,oname.length());
											if(s0==null || "".equals(s0)){
												exten = oname.substring(oname.lastIndexOf("."));
											}else{
												exten = "."+attchment.getFileType();
											}
										}
									}else{
										String s = n.substring(n.lastIndexOf(".")+1,n.length());
										if(s==null || "".equals(s)){
											exten = "."+attchment.getFileType();
										}else{
											exten = n.substring(n.lastIndexOf("."));
										}
									}
									File nfile = new File(path+"/"+type+"第"+qnum+"题"+exten);
									ZIPUtil.copyFile(f,nfile);
								}
							}
						}
					}
					String zipname = "作业附件-"+today+".zip";
					ZIPUtil.compressedFile(attpath,rootpath);
					
					String fileName = "";
					// 下载乱码解决
					String agent = request.getHeader("USER-AGENT");
					if (agent != null && agent.indexOf("MSIE") == -1) {// !IE
						fileName = new String(zipname.getBytes("UTF-8"), "iso-8859-1");
					} else { // IE
						fileName = URLEncoder.encode(zipname, "UTF-8");
					}
					response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName+"\"");
					response.setContentType("multipart/form-data");
					resout = response.getOutputStream();
					File zipfile = new File(rootpath+zipname);
					byte[] b = FileUtil.readFromFile(zipfile);
					response.addIntHeader("Content-Length", b.length);
					resout.write(b);
					resout.flush();
					File filedir = new File(attpath);
					ZIPUtil.deleteFile(filedir);
					ZIPUtil.deleteFile(zipfile);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					try {
						if(resout!=null){
							resout.close();
						}
					} catch (Exception e2) {
					}
				}
			}
		}
	}
	/**
	 * 根据文件名获得一个附件信息
	 * @param fileName 附件中心文件名
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getAttachmentByFileName")
	@ResponseBody
	public Attachment getAttachmentByFileName(String fileName) throws Exception{
		return attachmentService.getAttachmentByFileName(fileName);
	}
	
	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	
}
