package com.xczhihui.medical.web;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.common.domain.MedicalHospitalRecruit;
import com.xczhihui.medical.service.HospitalRecruitService;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

/**
 * 简历管理控制层实现类
 *
 * @author yxd
 */

@Controller
@RequestMapping("medical/recruit")
public class RecruitController extends AbstractController {
    protected final static String MEDICAL_PATH_PREFIX = "/medical/";
    @Autowired
    private HospitalRecruitService hospitalRecruitService;

    @Value("${online.web.url}")
    private String weburl;

    @RequestMapping(value = "list")
    @ResponseBody
    public TableVo medicalHospitalRecruits(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);

        MedicalHospitalRecruit searchVo = new MedicalHospitalRecruit();
        Group medicalHospitalId = groups.findByName("hospitalId");
        Group medicalHospitalRecruitName = groups
                .findByName("search_recruitName");
        Group medicalHospitalStatus = groups.findByName("search_status");
        if (medicalHospitalId != null) {
            searchVo.setHospitalId(medicalHospitalId.getPropertyValue1()
                    .toString());
        }
        if (medicalHospitalRecruitName != null) {
            searchVo.setPosition(medicalHospitalRecruitName.getPropertyValue1()
                    .toString());
        }
        if (medicalHospitalStatus != null) {
            searchVo.setStatusnum(Integer.valueOf(medicalHospitalStatus
                    .getPropertyValue1().toString()));
            if (searchVo.getStatusnum() == 1) {
                searchVo.setStatus(true);
            } else {
                searchVo.setStatus(false);
            }
        }
        Page<MedicalHospitalRecruit> page = hospitalRecruitService
                .findMedicalHospitalRecruitPage(searchVo, currentPage, pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;

    }

    /**
     * 添加
     *
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject add(MedicalHospitalRecruit medicalHospitalRecruit) {
        ResponseObject responseObj = new ResponseObject();
        try {
            hospitalRecruitService
                    .addMedicalHospitalRecruit(medicalHospitalRecruit);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("新增成功");

        } catch (Exception e) {
            e.printStackTrace();
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("新增失败");
        }
        return responseObj;
    }

    /**
     * 查看
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "findMedicalHospitalRecruitById", method = RequestMethod.GET)
    @ResponseBody
    public MedicalHospitalRecruit findMedicalHospitalRecruitById(String id) {
        return hospitalRecruitService.findMedicalHospitalRecruitById(id);
    }

    /**
     * 编辑
     *
     * @param medicalHospitalRecruit
     * @return
     */
    @RequestMapping(value = "updateMedicalHospitalRecruitById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateMedicalHospitalRecruitById(
            MedicalHospitalRecruit medicalHospitalRecruit) {
        ResponseObject responseObj = new ResponseObject();
        try {
            hospitalRecruitService
                    .updateMedicalHospitalRecruit(medicalHospitalRecruit);
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
    @RequestMapping(value = "updateRecruitStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateRecruitStatus(String id) {
        hospitalRecruitService.updateRecruitStatus(id);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException,
            IllegalAccessException {
        ResponseObject responseObject = new ResponseObject();
        if (ids != null) {
            String[] idsArr = ids.split(",");
            hospitalRecruitService.deleteByIds(idsArr);
        }
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("删除成功!");
        return responseObject;
    }

    /**
     * 上移
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "upMove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject upMove(String id) {
        ResponseObject responseObj = new ResponseObject();
        hospitalRecruitService.updateSortUp(id);
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
    public ResponseObject downMove(String id) {
        ResponseObject responseObj = new ResponseObject();
        hospitalRecruitService.updateSortDown(id);
        responseObj.setSuccess(true);
        return responseObj;
    }

    /**
     * 添加、修改招聘信息
     *
     * @return
     */
    @RequestMapping(value = "toRecruit")
    public String toRecruit(HttpServletRequest request) {
        return MEDICAL_PATH_PREFIX + "/recruit";
    }
}
