package com.xczhihui.common.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xczhihui.common.service.CommonService;
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

import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.utils.TimeUtil;

/**
 * 
 * 下载上传word: ClassName: LinkAddressController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2018年2月6日<br>
 */
@Controller
@RequestMapping("/link/word")
public class LinkAddressController {

	@Autowired
	private AttachmentCenterService service;

	@Autowired
	private CommonService commonService;

	protected final static String MOBILE_PATH_PREFIX = "/mobile/";

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request) {

		String filePath = request.getServletContext().getRealPath(
				"/WEB-INF/template");
		filePath += "/excel_uplaod_record";
		File dir = new File(filePath);
		if (!dir.getParentFile().exists()) {
			dir.getParentFile().mkdirs();
		} else {
			String[] names = dir.list();
			List<Map<String, String>> listMapName = new ArrayList<Map<String, String>>();
			for (String string : names) {
				Map<String, String> map = new HashMap<String, String>();
				// listName.add("/excel_uplaod_record/"+string);
				map.put("path", "/excel_uplaod_record/" + string);
				map.put("name", string);
				listMapName.add(map);
			}
			request.setAttribute("record", listMapName);
		}
		return MOBILE_PATH_PREFIX + "/link";
	}

	/**
	 * 下载链接操作文档
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "upload")
	@ResponseBody
	public Map<String, Object> upload(HttpServletRequest request,
			HttpServletResponse res, @RequestParam("file") MultipartFile file)
			throws Exception {

		// excel 保留最近10次的上传记录

		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("request.getServletContext().getRealPath"
				+ request.getServletContext().getRealPath("/template/"));
		// /WEB-INF/template
		// 上传文件路径
		String filePath = request.getServletContext().getRealPath(
				"/WEB-INF/template");
		// 如果文件不为空，写入上传路径
		if (!file.isEmpty()) {
			// 上传文件名
			String filename = file.getOriginalFilename();
			// 删除原来的
			deleteFile(filePath + File.separator + "链接地址添加文档.docx");
			filename = "链接地址添加文档.docx";

			File filepath = new File(filePath, filename);
			// 判断路径是否存在，如果不存在就创建一个
			if (!filepath.getParentFile().exists()) {
				filepath.getParentFile().mkdirs();
			}
			// 将上传文件保存到一个目标文件当中
			file.transferTo(new File(filePath + File.separator + filename));

			map.put("error", "0");
			return map;
		} else {
			map.put("error", "1");
			map.put("error", "上传失败");
			return map;
		}
		// res.sendRedirect("http://localhost:28080/home#/operate/mobileBanner/index");
	}

	@RequestMapping(value = "/download")
	public ResponseEntity<byte[]> download(HttpServletRequest request,
			@RequestParam("filename") String filename, Model model)
			throws Exception {

		System.out.println("filename:" + filename);
		// 下载文件路径
		String path = request.getServletContext().getRealPath(
				"/WEB-INF/template");
		System.out.println("下载文件路径" + path);
		File file = new File(path + File.separator + filename);
		HttpHeaders headers = new HttpHeaders();
		// 下载显示的文件名，解决中文名称乱码问题
		String downloadFielName = new String(filename.getBytes("UTF-8"),
				"iso-8859-1");
		// 通知浏览器以attachment（下载方式）打开图片
		headers.setContentDispositionFormData("attachment", downloadFielName);
		// application/octet-stream ： 二进制流数据（最常见的文件下载）。
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

	/**
	 * 
	 * Description：导入表引库到excel
	 * 
	 * @param file
	 * @param menuId
	 * @return
	 * @return Object
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 */
	@ResponseBody
	@RequestMapping("/importExcel")
	public Map<String, Object> importExcel(HttpServletRequest request,
			@RequestParam("file") MultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 上传文件路径
			String filePath = request.getServletContext().getRealPath(
					"/WEB-INF/template");
			if (!file.isEmpty()) {

				MultipartFile file_recoad = file;

				map = commonService.updateImportExcel(file.getInputStream(),
						file.getOriginalFilename());
				String mapErrorInfo = (String) map.get("excel_error");
				if ("更新成功".equals(mapErrorInfo)) { // 后台数据存储成功了
					// 上传文件名
					String filename = file.getOriginalFilename();
					if (filename.contains(".xls")) { // 这个文件
						// 删除原来的
						deleteFile(filePath + File.separator + "常见问题模板.xls");
						filename = "常见问题模板.xls";
					} else if (filename.contains(".xlsx")) {
						// 删除原来的
						deleteFile(filePath + File.separator + "常见问题模板.xlsx");
						filename = "常见问题模板.xlsx";
					}
					/**
					 * 创建文件
					 */
					File filepath = new File(filePath, filename);
					// 判断路径是否存在，如果不存在就创建一个
					if (!filepath.getParentFile().exists()) {
						filepath.getParentFile().mkdirs();
					}
					// 将上传文件保存到一个目标文件当中
					file.transferTo(new File(filePath + File.separator
							+ filename));

					// 另外将文件保存到 excel_uplaod_record 历史记录中
					String fileDate = TimeUtil.formatDateUnderline(new Date());
					fileDate += "_" + filename;

					System.out
							.println(filePath + File.separator
									+ "excel_uplaod_record" + File.separator
									+ fileDate);
					File record = new File(filePath + File.separator
							+ "excel_uplaod_record" + File.separator + fileDate);
					// 将上传文件保存到一个目标文件当中
					// file_recoad.transferTo(new File(filePath+File.separator
					// +"excel_uplaod_record"+ File.separator + fileDate));
					File exit = new File(filePath + File.separator + filename);

					if (exit.getParentFile().exists()) {
						Files.copy(exit.toPath(), record.toPath());
					}
					map.put("error", "0");
				} else {
					map.put("error", "1");
				}
			} else {
				map.put("error", "1");
				map.put("excel_error", "文件类型获取为null");
			}
			return map;
		} catch (IOException e) {
			e.printStackTrace();
			map.put("error", "1");
			return map;
		}
	}
}
