package com.twinkling.stream.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.xczhihui.attachment.center.server.service.AttachmentService;
import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.FileUtil;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.twinkling.stream.config.Configurations;
import com.twinkling.stream.util.IoUtil;

/**
 * File reserved servlet, mainly reading the request parameter and its file
 * part, stored it.
 */
public class StreamServlet extends HttpServlet {
	private static final long serialVersionUID = -8619685235661387895L;
	/** when the has increased to 10kb, then flush it to the hard-disk. */
	static final int BUFFER_LENGTH = 10240;
	static final String START_FIELD = "start";
	public static final String CONTENT_RANGE_HEADER = "content-range";
	
	@Value("${attachment.path}")
	private String rootPath = "";
	@Value("${picture.path}")
	private String picturePath = "";

	@Autowired
	private AttachmentService attachmentService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}
	
	/**
	 * Lookup where's the position of this file?
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doOptions(req, resp);

		final String token = req.getParameter(TokenServlet.TOKEN_FIELD);
		final String size = req.getParameter(TokenServlet.FILE_SIZE_FIELD);
		final String fileName = req.getParameter(TokenServlet.FILE_NAME_FIELD);
		
		String projectName = req.getParameter("projectName");
		//1==图片，2==附件，3其他=错误
		String fileType = req.getParameter("fileType");
		String base_dir = "2".equals(fileType) ? rootPath : picturePath;
		final PrintWriter writer = resp.getWriter();
		
		/** TODO: validate your token. */
		
		JSONObject json = new JSONObject();
		long start = 0;
		boolean success = true;
		String message = "";
		try {
			File f =  IoUtil.getTokenedFile(token,base_dir,projectName);
			start = f.length();
			/** file size is 0 bytes. */
			if (token.endsWith("_0") && "0".equals(size) && 0 == start){
				f.renameTo(IoUtil.getFile(fileName,base_dir,projectName));
			}

		} catch (FileNotFoundException fne) {
			message = "Error: " + fne.getMessage();
			success = false;
		} finally {
			try {
				if (success) {
                    json.put(START_FIELD, start);
                }
				json.put(TokenServlet.SUCCESS, success);
				json.put(TokenServlet.MESSAGE, message);
			} catch (JSONException e) {}
			writer.write(json.toString());
			IoUtil.close(writer);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doOptions(req, resp);
		final String token = req.getParameter(TokenServlet.TOKEN_FIELD);
		final String fileName = req.getParameter(TokenServlet.FILE_NAME_FIELD);
		String projectName = req.getParameter("projectName");
		String fileType = req.getParameter("fileType");//1==图片，2==附件，3其他=错误
		String base_dir = "2".equals(fileType) ? rootPath : picturePath;
		String userId = req.getParameter("userId");
		Range range = IoUtil.parseRange(req);
		
		OutputStream out = null;
		InputStream content = null;
		final PrintWriter writer = resp.getWriter();
		
		/** TODO: validate your token. */
		
		JSONObject json = new JSONObject();
		long start = 0;
		boolean success = true;
		String message = "";
		File f =  IoUtil.getTokenedFile(token,base_dir,projectName);
		try {
			if (f.length() != range.getFrom()) {
				/** drop this uploaded data */
				throw new StreamException(StreamException.ERROR_FILE_RANGE_START);
			}
			
			out = new FileOutputStream(f, true);
			content = req.getInputStream();
			int read = 0;
			final byte[] bytes = new byte[BUFFER_LENGTH];
			while ((read = content.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

			start = f.length();
		} catch (StreamException se) {
			success = StreamException.ERROR_FILE_RANGE_START == se.getCode();
			message = "Code: " + se.getCode();
		} catch (FileNotFoundException fne) {
			message = "Code: " + StreamException.ERROR_FILE_NOT_EXIST;
			success = false;
		} catch (IOException io) {
			message = "IO Error: " + io.getMessage();
			success = false;
		} finally {
			IoUtil.close(out);
			IoUtil.close(content);
			Attachment a = null;
			/** rename the file */
			if (range.getSize() == start) {
				try {
					//获取磁盘实际存储的文件名
					String ext = StringUtils.getFilenameExtension(fileName);
					String storeName = FileUtil.getUUIDFileName(ext);
					// 先删除
					IoUtil.getFile(storeName,base_dir,projectName).delete();
					//hash存储的文件名称
					
					Files.move(f.toPath(), f.toPath().resolveSibling(storeName));
					 
					AttachmentType type = AttachmentType.valueOf(projectName.toUpperCase());
					String path = type.getName() + "/" + FileUtil.getYearMonthDayHourPath();
					
					a = attachmentService.saveAttachmentOnUpload(fileType , userId, type, fileName, storeName, path, range.getSize());
		
					/** if `STREAM_DELETE_FINISH`, then delete it. */
//					if (Configurations.isDeleteFinished()) {
//						IoUtil.getFile(fileName).delete();
//					}
				} catch (IOException e) {
					e.printStackTrace();
					success = false;
					message = "Rename file error: " + e.getMessage();
				}
				
			}
			try {
				if (success) {
                    json.put(START_FIELD, start);
                }
				if("".equals(message)){
					json.put(TokenServlet.MESSAGE, a ==null ? "":a.getId());
				}else{
					json.put(TokenServlet.MESSAGE, message);
				}
				json.put(TokenServlet.SUCCESS, success);
			} catch (JSONException e) {}
			
			writer.write(json.toString());
			IoUtil.close(writer);
		}
	}
	
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Range,Content-Type");
		resp.setHeader("Access-Control-Allow-Origin", Configurations.getCrossOrigins());
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	}

	@Override
	public void destroy() {
		super.destroy();
	}
	
}
