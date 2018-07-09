package com.xczhihui.medical.web;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xczhihui.bxg.online.common.domain.MedicalDoctor;
import com.xczhihui.bxg.online.common.domain.MedicalEnrollmentRegulations;
import com.xczhihui.bxg.online.common.domain.MedicalEntryInformation;
import com.xczhihui.common.util.DateUtil;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.medical.service.DoctorService;
import com.xczhihui.medical.service.MedicalEnrollmentRegulationsService;
import com.xczhihui.medical.service.MedicalEntryInformationService;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

/**
 * @ClassName: ApprenticeController
 * @Description: 师承
 * @Author: wangyishuai
 * @email: 15210815880@163.com
 * @CreateDate: 2018/5/21 18:09
 **/
@Controller
@RequestMapping(value = "/apprentice")
public class ApprenticeController {
    protected final static String CLOUD_CLASS_PATH_PREFIX = "/medical/";
    @Autowired
    private MedicalEntryInformationService medicalEntryInformationService;
    @Autowired
    private MedicalEnrollmentRegulationsService medicalEnrollmentRegulationsService;
    @Autowired
    private DoctorService doctorService;

    @RequestMapping(value = "/entryInformation/index")
    public String entryInformationIndex(HttpServletRequest request) {
        List<MedicalEnrollmentRegulations> list = medicalEnrollmentRegulationsService.getAllMedicalEntryInformationList();
        request.setAttribute("MedicalEnrollmentRegulations", list);
        return "/medical/entryInformation";
    }

    @RequestMapping(value = "/enrollmentRegulations/index")
    public ModelAndView enrollmentRegulationsIndex() {
        ModelAndView mav = new ModelAndView("/medical/enrollmentRegulations");
        return mav;
    }

    //师承列表
    @RequestMapping(value = "enrollmentRegulationsList")
    @ResponseBody
    public TableVo MedicalEnrollmentRegulations(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);

        MedicalEnrollmentRegulations searchVo = new MedicalEnrollmentRegulations();

        Page<MedicalEnrollmentRegulations> page = medicalEnrollmentRegulationsService.findEnrollmentRegulationsPage(
                searchVo, currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;

    }

    //报名列表
    @RequestMapping(value = "entryInformationList")
    @ResponseBody
    public TableVo MedicalEntryInformation(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);

        MedicalEntryInformation searchVo = new MedicalEntryInformation();
        Group search_title = groups.findByName("search_title");
        if (search_title != null) {
            String str = search_title.getPropertyValue1().toString();
            if (!str.equals("-1")) {
                searchVo.setMerId(Integer.parseInt(str));
            }
        }
        Page<MedicalEntryInformation> page = medicalEntryInformationService.findEntryInformationPage(
                searchVo, currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;

    }

    //查看详情-招生简章
    @RequestMapping(value = "enrollmentRegulationsDetail")
    public String enrollmentRegulationsIndex(HttpServletRequest request, Integer id) {
        MedicalEnrollmentRegulations searchEntity = medicalEnrollmentRegulationsService.enrollmentRegulationsDetail(id);
        List<MedicalDoctor> mdList = doctorService.getAllMedicalDoctorList();
        request.setAttribute("MedicalEnrollmentRegulations", searchEntity);
        request.setAttribute("doctorList", mdList);
        return CLOUD_CLASS_PATH_PREFIX + "/enrollmentRegulationsDetail";
    }

    //修改跳转-招生简章
    @RequestMapping(value = "toEdit")
    public String toEdit(HttpServletRequest request, Integer id) {
        MedicalEnrollmentRegulations searchEntity = medicalEnrollmentRegulationsService.enrollmentRegulationsDetail(id);
        List<MedicalDoctor> mdList = doctorService.getAllMedicalDoctorList();
        request.setAttribute("MedicalEnrollmentRegulations", searchEntity);
        request.setAttribute("doctorList", mdList);
        return CLOUD_CLASS_PATH_PREFIX + "/enrollmentRegulationsEdit";
    }

    //添加跳转-招生简章
    @RequestMapping(value = "toAdd")
    public String toAdd(HttpServletRequest request) {
        List<MedicalDoctor> mdList = doctorService.getAllMedicalDoctorList();
        request.setAttribute("doctorList", mdList);
        return CLOUD_CLASS_PATH_PREFIX + "/enrollmentRegulationsAdd";
    }

    //更新师承
    @RequestMapping(value = "updateEnrollmentRegulations")
    @ResponseBody
    public ResponseObject updateEnrollmentRegulations(MedicalEnrollmentRegulations medicalEnrollmentRegulations) {
        ResponseObject responseObject = new ResponseObject();

        medicalEnrollmentRegulations.setUpdateTime(new Date());
        medicalEnrollmentRegulations.setUpdator(ManagerUserUtil.getId());
        medicalEnrollmentRegulations.setCreateTime(DateUtil.parseDate(medicalEnrollmentRegulations.getCreateTimeStr(), "yyyy-MM-dd hh:mm:ss"));
        medicalEnrollmentRegulations.setStartTime(DateUtil.parseDate(medicalEnrollmentRegulations.getStartTimeStr(), "yyyy-MM-dd hh:mm:ss"));
        medicalEnrollmentRegulations.setEndTime(DateUtil.parseDate(medicalEnrollmentRegulations.getEndTimeStr(), "yyyy-MM-dd hh:mm:ss"));
        medicalEnrollmentRegulations.setDeadline(DateUtil.parseDate(medicalEnrollmentRegulations.getDeadlineStr(), "yyyy-MM-dd hh:mm:ss"));
        medicalEnrollmentRegulations.setStatus(true);
        medicalEnrollmentRegulationsService.update(medicalEnrollmentRegulations);
        responseObject.setSuccess(true);
        responseObject.setResultObject("修改完成!");
        return responseObject;
    }

    /**
     * 添加-招生简章
     *
     * @param medicalEnrollmentRegulations
     * @return
     */
    @RequestMapping(value = "addEnrollmentRegulations", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject add(HttpServletRequest request, MedicalEnrollmentRegulations medicalEnrollmentRegulations) {

        medicalEnrollmentRegulations.setCreateTime(new Date());
        medicalEnrollmentRegulations.setStartTime(DateUtil.parseDate(medicalEnrollmentRegulations.getStartTimeStr(), "yyyy-MM-dd hh:mm:ss"));
        medicalEnrollmentRegulations.setEndTime(DateUtil.parseDate(medicalEnrollmentRegulations.getEndTimeStr(), "yyyy-MM-dd hh:mm:ss"));
        medicalEnrollmentRegulations.setDeadline(DateUtil.parseDate(medicalEnrollmentRegulations.getDeadlineStr(), "yyyy-MM-dd hh:mm:ss"));
        medicalEnrollmentRegulations.setCreator(ManagerUserUtil.getId());
        medicalEnrollmentRegulationsService.save(medicalEnrollmentRegulations);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    //查看详情-报名
    @RequestMapping(value = "entryInformationDetail")
    public String entryInformationDetail(HttpServletRequest request, Integer id) {
        MedicalEntryInformation searchEntity = medicalEntryInformationService.entryInformationDetail(id);
        request.setAttribute("MedicalEntryInformation", searchEntity);
        return CLOUD_CLASS_PATH_PREFIX + "/entryInformationDetail";
    }

    //修改跳转-报名
    @RequestMapping(value = "entryInformationEdit")
    public String entryInformationEdit(HttpServletRequest request, Integer id) {
        MedicalEntryInformation searchEntity = medicalEntryInformationService.entryInformationDetail(id);
        request.setAttribute("MedicalEntryInformation", searchEntity);
        return CLOUD_CLASS_PATH_PREFIX + "/entryInformationEdit";
    }

    //更新报名
    @RequestMapping(value = "updateEntryInformation")
    @ResponseBody
    public ResponseObject updateEntryInformation(MedicalEntryInformation medicalEntryInformation) {
        ResponseObject responseObject = new ResponseObject();
        medicalEntryInformation.setCreateTime(DateUtil.parseDate(medicalEntryInformation.getCreateTimeStr(), "yyyy-MM-dd hh:mm:ss"));
        medicalEntryInformationService.update(medicalEntryInformation);
        responseObject.setSuccess(true);
        responseObject.setResultObject("修改完成!");
        return responseObject;
    }

    /**
     * 简章 --修改状态(禁用or启用)
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(Integer id) {
        medicalEnrollmentRegulationsService.updateStatus(id);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    //是否为徒弟
    @RequestMapping(value = "updateIsApprentice")
    @ResponseBody
    public ResponseObject updateIsApprentice(MedicalEntryInformation medicalEntryInformation) {
        ResponseObject responseObject = new ResponseObject();
        medicalEntryInformationService.updateIsApprentice(medicalEntryInformation.getId(), medicalEntryInformation.getApprentice());
        responseObject.setSuccess(true);
        responseObject.setResultObject("修改完成!");
        return responseObject;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/export")
    public String download(HttpServletRequest request, HttpServletResponse response, Integer merId) {
        List<MedicalEntryInformation> meilist = medicalEntryInformationService.getAllMedicalEntryInformationList(merId);
        response.reset(); // 清除buffer缓存
        // 指定下载的文件名
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        response.setHeader("Content-Disposition", "attachment;filename=enrollList"+ LocalDateTime.now().format(df) + ".xlsx");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        XSSFWorkbook workbook = null;
        try {
            workbook = exportContacts(meilist);
            OutputStream output;
            try {
                output = response.getOutputStream();
                BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
                bufferedOutPut.flush();
                workbook.write(bufferedOutPut);
                bufferedOutPut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public XSSFWorkbook exportContacts(List<MedicalEntryInformation> valueList)
            throws IllegalArgumentException {
        XSSFWorkbook xssfWorkbook = null;
        String sheetName = "报名列表";
        xssfWorkbook = createExcelFile(valueList, sheetName);
        return xssfWorkbook;
    }

    public XSSFWorkbook createExcelFile(List<MedicalEntryInformation> valueList, String sheetName)
            throws IllegalArgumentException {
        // 创建新的Excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 在Excel工作簿中建一工作表，其名为缺省值, 也可以指定Sheet名称
        XSSFSheet sheet = workbook.createSheet(sheetName);
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("姓名");
        cell = row.createCell(1);
        cell.setCellValue("手机");
        cell = row.createCell(2);
        cell.setCellValue("年龄");
        cell = row.createCell(3);
        cell.setCellValue("性别");
        cell = row.createCell(4);
        cell.setCellValue("籍贯");
        cell = row.createCell(5);
        cell.setCellValue("学历");
        cell = row.createCell(6);
        cell.setCellValue("报名时间");
        cell = row.createCell(7);
        cell.setCellValue("学习经历");
        cell = row.createCell(8);
        cell.setCellValue("行医经历");
        cell = row.createCell(9);
        cell.setCellValue("学中医目的");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (int i = 0; i < valueList.size(); i++) {
            row = sheet.createRow((int) i + 1);
            MedicalEntryInformation mei = valueList.get(i);
            if (mei.getSex() == 1) {
                mei.setSexStr("男");
            } else if (mei.getSex() == 0) {
                mei.setSexStr("女");
            } else {
                mei.setSexStr("未知");
            }
            if (mei.getEducation() != null) {
                if (mei.getEducation() == 1) {
                    mei.setEducationStr("小学");
                } else if (mei.getEducation() == 2) {
                    mei.setEducationStr("初中");
                } else if (mei.getEducation() == 3) {
                    mei.setEducationStr("高中");
                } else if (mei.getEducation() == 4) {
                    mei.setEducationStr("大专");
                } else if (mei.getEducation() == 5) {
                    mei.setEducationStr("本科");
                } else if (mei.getEducation() == 6) {
                    mei.setEducationStr("研究生");
                } else if (mei.getEducation() == 7) {
                    mei.setEducationStr("博士生");
                } else if (mei.getEducation() == 8) {
                    mei.setEducationStr("博士后");
                } else {
                    mei.setEducationStr("无");
                }
            } else {
                mei.setEducationStr("无");
            }

            String dataStr = sdf.format(mei.getDeadline());
            mei.setDeadlineStr(dataStr);
            cell = row.createCell((short) 0);//创建单元格  先设置样式、编码，然后再置值。
            cell.setCellValue(mei.getName());
            cell = row.createCell((short) 1);
            cell.setCellValue(mei.getTel());
            cell = row.createCell((short) 2);
            cell.setCellValue(mei.getAge());
            cell = row.createCell((short) 3);
            cell.setCellValue(mei.getSexStr());
            cell = row.createCell((short) 4);
            cell.setCellValue(mei.getNativePlace());
            cell = row.createCell((short) 5);
            cell.setCellValue(mei.getEducationStr());
            cell = row.createCell((short) 6);
            cell.setCellValue(sdf.format(mei.getCreateTime()));
            cell = row.createCell((short) 7);
            cell.setCellValue(mei.getEducationExperience());
            cell = row.createCell((short) 8);
            cell.setCellValue(mei.getMedicalExperience());
            cell = row.createCell((short) 9);
            cell.setCellValue(mei.getGoal());
        }
        return workbook;
    }


}