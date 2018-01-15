package com.xczhihui.attachment.center.server.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.xczhihui.attachment.center.server.service.AttachmentService;
import com.xczhihui.attachment.center.server.utils.ConfigUtil;
import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.FileUtil;

/**
 * 
 */
public class UploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4902583334059940165L;
	public static final String UPLOAD_DIR = "/data/attachment/";

	@Value("${attachment.path:/data/attachment/}")
	private String rootPath = "/data/attachment/";

	@Value("${attachment.url:http://attachment-center.ixincheng.com}")
	private String attachmentUrl = "http://attachment-center.ixincheng.com";

	@Value("${picture.path:/data/picture/}")
	private String picturePath = "/data/picture/";

	@Autowired
	private AttachmentService attachmentService;

	@Override
    public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doOptions(request,response);
		int resumableChunkNumber = getResumableChunkNumber(request);
		ResumableInfo info = getResumableInfo(request);
		RandomAccessFile raf = new RandomAccessFile(info.resumableFilePath, "rw");

		// Seek to position
		raf.seek((resumableChunkNumber - 1) * (long) info.resumableChunkSize);

		// Save to file
		InputStream is = request.getInputStream();
		long readed = 0;
		long content_length = request.getContentLength();
		byte[] bytes = new byte[1024 * 1024 * 100];
		while (readed < content_length) {
			int r = is.read(bytes);
			if (r < 0) {
				break;
			}
			raf.write(bytes, 0, r);
			readed += r;
		}
		raf.close();

		// Mark as uploaded.
		info.uploadedChunks.add(new ResumableInfo.ResumableChunkNumber(resumableChunkNumber));
		String ext = StringUtils.getFilenameExtension(info.getResumableFilename());
		String storeName = FileUtil.getUUIDFileName(ext);
		// Check if all chunks  uploaded, and change  filename
		if (info.checkIfUploadFinished(storeName)) { 
			// String storeName = info.getStoreName();
			// String path =
			// AttachmentType.valueOf(info.getProjectName().toUpperCase()).getName()
			// + "/" + FileUtil.getYearMonthDayHourPath();

			Attachment a = attachmentService.saveAttachmentOnUpload(info.getFileType(), info.getCreateUserId(),
			        AttachmentType.valueOf(info.getProjectName().toUpperCase()), info.getResumableFilename(), storeName, info.getStorePath(),
			        info.getResumableTotalSize());
			ResumableInfoStorage.getInstance().remove(info);
			// 保存数据库
			response.getWriter().write("All finished." + a.getId());
		} else {
			response.getWriter().write("Upload");
		}
	}

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doOptions(request,response);
		int resumableChunkNumber = getResumableChunkNumber(request);

		ResumableInfo info = getResumableInfo(request);

		if (info.uploadedChunks.contains(new ResumableInfo.ResumableChunkNumber(resumableChunkNumber))) {
			response.getWriter().write("Uploaded."); // This Chunk has been Uploaded.
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private int getResumableChunkNumber(HttpServletRequest request) {
		return HttpUtils.toInt(request.getParameter("resumableChunkNumber"), -1);
	}

	private ResumableInfo getResumableInfo(HttpServletRequest request) throws ServletException {
		int resumableChunkSize = HttpUtils.toInt(request.getParameter("resumableChunkSize"), -1);
		long resumableTotalSize = HttpUtils.toLong(request.getParameter("resumableTotalSize"), -1);
		String resumableIdentifier = request.getParameter("resumableIdentifier");// 文件大小+文件名称
		String resumableFilename = request.getParameter("resumableFilename");
		String resumableRelativePath = request.getParameter("resumableRelativePath");
		String projectName = request.getParameter("projectName");
		String createUserId = request.getParameter("createUserId");
		String fileType = request.getParameter("fileType");

		String base_dir = "2".equals(fileType) ? rootPath : picturePath;

		AttachmentType type = AttachmentType.valueOf(projectName.toUpperCase());
		String path = type.getName() + "/" + FileUtil.getYearMonthDayHourPath();
		// String ext = StringUtils.getFilenameExtension(resumableFilename);
		// String name = FileUtil.getUUIDFileName(ext);

		// Here we add a ".temp" to every upload file to indicate NON-FINISHED
		new File(base_dir + path).mkdirs();
		String resumableFilePath = new File(base_dir + path, resumableFilename).getAbsolutePath() + ".temp";

		ResumableInfoStorage storage = ResumableInfoStorage.getInstance();

		ResumableInfo info = storage.get(resumableChunkSize, resumableTotalSize, resumableIdentifier, resumableFilename, resumableRelativePath,
		        resumableFilePath);
		if (!info.vaild()) {

			storage.remove(info);
			throw new ServletException("Invalid request params.");
		}
		info.setProjectName(projectName);
		info.setStorePath(path);
		info.setStoreName(resumableFilename);
		info.setCreateUserId(createUserId);
		info.setFileType(fileType);
		return info;
	}

	@Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)   throws ServletException, IOException {
		resp.setContentType("application/json");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Range,Content-Type");
		resp.setHeader("Access-Control-Allow-Origin", ConfigUtil.getValueByName("STREAM_CROSS_ORIGIN"));
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	}

	 @Override
     public void destroy()
	  {
	    super.destroy();
	  }
	 
}
