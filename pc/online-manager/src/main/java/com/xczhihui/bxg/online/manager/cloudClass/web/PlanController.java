package com.xczhihui.bxg.online.manager.cloudClass.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.cloudClass.service.PlanService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.PlanVo;
import com.xczhihui.bxg.online.manager.utils.StringUtil;

@Controller
@RequestMapping(value = "/cloudClass/plan")
public class PlanController{
	
	@Autowired
	private PlanService planService;
	
	/**
     * 
     * @return
     */
    @RequestMapping(value = "/toAdd")
    public ModelAndView toAdd(HttpServletRequest request){
         ModelAndView mav=new ModelAndView("/cloudClass/plan");
         request.setAttribute("coursesMicro", planService.getMicroCourseList(Integer.parseInt(request.getParameter("courseId"))));//微课
         return mav;
    }

    /**
	 * 添加
	 * @param vo
	 * @return
	 */
    @RequiresPermissions("cloudClass:menu:grade")
	@RequestMapping(value = "/addPlan", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject addPlan(PlanVo planVo,HttpServletRequest request){
		List<PlanVo> list = planService.findPlanList(planVo);
		planVo.setCreatePerson(UserLoginUtil.getLoginUser(request).getLoginName());planVo.setCreatePerson(UserLoginUtil.getLoginUser(request).getLoginName());
		if(list.size() == 0){//如果已有就不再添加
			planService.addPlan(planVo);
			list = planService.findPlanList(planVo);
		}else if(list.size() > 0){//已有 就追加
			planService.addAppendPlan(planVo);
			list = planService.findPlanList(planVo);
		}
        return  ResponseObject.newSuccessResponseObject(list);
    }
    
    /**
     * 添加
     * @param vo
     * @return
     */
    @RequiresPermissions("cloudClass:menu:grade")
    @RequestMapping(value = "/addOneRestPlan", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject addOneRestPlan(PlanVo planVo,HttpServletRequest request){
    	ResponseObject responseObj = new ResponseObject();
		planVo.setCreatePerson(UserLoginUtil.getLoginUser(request).getLoginName());
		planService.addOneRestPlan(planVo);
		List<PlanVo> list =  planService.findPlanList(planVo);
		
    	responseObj.setSuccess(true);
    	responseObj.setErrorMessage("添加成功");
    	responseObj.setResultObject(list);
    	return responseObj;
    }
    
    /**
     * 导出
     * @param vo
     * @return
     * @throws IOException 
     */
    @RequiresPermissions("cloudClass:menu:grade")
    @RequestMapping(value = "/exportExcelPlan")
    public void exportExcelPlan(PlanVo planVo,HttpServletRequest request,HttpServletResponse res) throws IOException{

		List<Map<String,String>> list =  planService.exportExcelPlan(planVo);//获取到导出数据
		
		List<String> listId = new ArrayList<>();
		for(int i = 0;i<list.size();i++){
			listId.add(list.get(i).get("id"));
		}

		String titleName = planVo.getGradeName();
//		String fileName = System.getProperties().getProperty("java.io.tmpdir")+ "/schedule-" + titleName + ".xls";
//		String fileName = request.getServletContext().getRealPath("/")+"temp/plan-"+planVo.getGradeName()+".xls";
//		OutputStream out = new FileOutputStream(fileName);
		
		String title = titleName + " 学习计划";

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(title);

		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.ROYAL_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		style.setFillBackgroundColor(HSSFColor.ROYAL_BLUE.index);
		HSSFFont font = workbook.createFont();
		// font.setColor(HSSFColor.VIOLET.index);
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 11);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);
		
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font2 = workbook.createFont();
		// font.setColor(HSSFColor.VIOLET.index);
		font2.setFontName("宋体");
		font2.setFontHeightInPoints((short) 11);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		style2.setFont(font2);

		// 标题 begin

		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;

		// 日期列 begin
		cell = row.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue("日期");
		// 日期列 end

		// 星期 begin
		cell = row.createCell(1);
		cell.setCellStyle(style);
		cell.setCellValue("星期");
		// 星期 end

		// 天数 begin
		cell = row.createCell(2);
		cell.setCellStyle(style);
		cell.setCellValue("天数");
		// 天数 end

		// 知识点 begin
		cell = row.createCell(3);
		cell.setCellStyle(style);
		cell.setCellValue("知识点");
		// 知识点 end

		// 知识点时长 begin
		cell = row.createCell(4);
		cell.setCellStyle(style);
		cell.setCellValue("知识点时长");
		// 知识点时长 end

		// 标题 end
		String lastId = "";//用于计算是否开始新的一行
		// 记录 begin
		for (int i = 0; i < list.size(); i++)
		{
			Map map = (Map)list.get(i);
			String id = map.get("id").toString();
			String planDate = map.get("planDate").toString();
			String week = map.get("week").toString();
			String day = map.get("day").toString();
			String name = (map.get("name") == null ? "":map.get("name").toString());
			String videoTime = "休息".equals(day) && "".equals(name)? "":map.get("videoTime").toString();
			String sort = (map.get("sort") == null ? "":map.get("sort").toString());
			String chuanjiangHas = map.get("chuanjiangHas").toString();

			row = sheet.createRow(i + 1);
			
			if(!lastId.equals(id)){
				lastId = id;
				int cellRowNum= Collections.frequency(listId, id);//判断出ID出现的次数可以求出合并行数
				if( cellRowNum > 1)
				{
					sheet.addMergedRegion(new CellRangeAddress(i + 1, i + 1 + cellRowNum - 1,  0, 0));// 合并单元格  
					sheet.addMergedRegion(new CellRangeAddress(i + 1, i + 1 + cellRowNum - 1 , 1, 1));// 合并单元格  
					sheet.addMergedRegion(new CellRangeAddress(i + 1, i + 1 + cellRowNum - 1 , 2, 2));// 合并单元格
				}
			}
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue(planDate);

			cell = row.createCell(1);
			cell.setCellStyle(style2);
			cell.setCellValue(week);

			cell = row.createCell(2);
			cell.setCellStyle(style2);
			cell.setCellValue(day);
			
			cell = row.createCell(3);
			cell.setCellStyle(style2);
			cell.setCellValue(name);

			cell = row.createCell(4);
			cell.setCellStyle(style2);
			cell.setCellValue(videoTime);
			// sheet.addMergedRegion(new CellRangeAddress(i+2,i+2,2,6));

		}
		// 记录 end
//		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		// sheet.addMergedRegion(new CellRangeAddress(1,1,2,6));

		sheet.setColumnWidth(0, 3500);
		sheet.setColumnWidth(1, 2000);
		sheet.setColumnWidth(2, 2000);
		sheet.autoSizeColumn(3, true);
		sheet.autoSizeColumn(4, true);

		String codedfilename = null;
		String agent = request.getHeader("USER-AGENT");
		if (null != agent && -1 != agent.indexOf("MSIE") || null != agent
		        && -1 != agent.indexOf("Trident")) {// ie
			String name = URLEncoder.encode(title, "UTF8");
			codedfilename = name;
		} else if (null != agent && -1 != agent.indexOf("Mozilla")) {// firefox,chrome等
			codedfilename = new String(title.getBytes("UTF-8"),
			        "iso-8859-1");
		}
		
		res.setContentType("application/msexcel");
		res.setHeader("Content-Disposition","attachment;filename="+codedfilename+".xls"); 
		
		workbook.write(res.getOutputStream());
		workbook.close();
		
    }
    
    /**
     * 知识点相关
     * @param vo
     * @return
     */
    @RequiresPermissions("cloudClass:menu:grade")
    @RequestMapping(value = "/getGradePlanChapter", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject getGradePlanChapter(PlanVo planVo,HttpServletRequest request){
    	ResponseObject responseObj = new ResponseObject();
    	List list = planService.getGradePlanChapter(planVo);
    	responseObj.setSuccess(true);
    	responseObj.setResultObject(list);
    	return responseObj;
    }

	/**
	 * 编辑
	 * @param vo
	 * @return
	 */
    @RequiresPermissions("cloudClass:menu:grade")
	@RequestMapping(value = "updatePlanById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject updatePlanById (PlanVo planVo,HttpServletRequest request){
		ResponseObject responseObj = new ResponseObject();
		String[] microCourseIds = request.getParameterValues("microCourseIds");
		
		StringBuffer sb = new StringBuffer();
		for(int i = 0; microCourseIds !=null && i < microCourseIds.length; i++){
			if(i==microCourseIds.length - 1){
				sb. append(microCourseIds[i]);
			}else{
				sb. append(microCourseIds[i]+",");
			}
		}

		planVo.setMicroCourseIds(microCourseIds != null ? sb.toString():null);
		planVo.setCreatePerson(UserLoginUtil.getLoginUser(request).getLoginName());
		planService.updatePlan(planVo);
        responseObj.setSuccess(true);
		 return responseObj;
	}
    
    /**
     * 删除串讲
     * @param vo
     * @return
     */
    @RequiresPermissions("cloudClass:menu:grade")
    @RequestMapping(value = "deletePlanChuanJiangById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletePlanChuanJiangById (PlanVo planVo,HttpServletRequest request){
    	planService.deletePlanChuanJiangById(planVo);
    	return ResponseObject.newSuccessResponseObject(planService.findPlanList(planVo));
    }

	/**
	 * 批量逻辑删除
	 * @param Integer id
	 * @return
	 */
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids,String gradeId){
         ResponseObject responseObject=new ResponseObject();
         List<PlanVo> list = null;
         if(ids!=null) {
              String[] _ids = ids.split(",");
              planService.deletes(_ids);
              
              PlanVo planVo = new PlanVo();
              planVo.setGradeId(Integer.parseInt(gradeId));
              
              list = planService.findPlanList(planVo);
              responseObject.setSuccess(true);
              responseObject.setResultObject(list);
              responseObject.setErrorMessage("删除完成!");

         }else{
        	 responseObject.setSuccess(false);
             responseObject.setErrorMessage("请选择删除的数据！");
         }
         return responseObject;
    }
    
    /**
     * 获取到老师的下拉框
     * @param Integer id
     * @return
     */
    @RequestMapping(value = "getLecturers", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject getLecturers(Integer courseId){
    	ResponseObject responseObject=new ResponseObject();
    	responseObject.setSuccess(true);
    	responseObject.setResultObject(planService.getLecturers(courseId));
    	return responseObject;
    }

}
