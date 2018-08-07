package com.xczhihui.medical.department.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.enums.DoctorType1;
import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorDepartmentService;
import com.xczhihui.medical.doctor.vo.DoctorQueryVo;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;

import test.BaseJunit4Test;

/**
 * 科室测试类
 */
public class MedicalDepartmentServiceImplTest extends BaseJunit4Test {

    private static Logger logger = LoggerFactory.getLogger(MedicalDepartmentServiceImplTest.class);
    @Autowired
    private IMedicalDoctorDepartmentService service;


    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;

    /**
     * 获取医师所在的科室
     */
    @Test
    public void testGetDoctorDepartment() {
        List<MedicalDepartment> result = service.selectByUserId("402880e860c4ebe30160c51302660000");
        result.forEach(doctorDepartment -> logger.info("-------------------" + doctorDepartment.toString()));
    }


    /**
     * 获取医师所在的科室
     */
    @Test
    public void testGetDoctorDepartment1() {

        DoctorQueryVo dqv = new DoctorQueryVo();
        dqv.setQueryKey("康复科");
        dqv.setDepartmentId("170b3b46a2af42d1b99646c85807d62d");
        dqv.bulid();
        Page<MedicalDoctorVO> doctors = medicalDoctorBusinessService.
                selectDoctorListByQueryKey(new Page<MedicalDoctorVO>(10, 1), dqv);

        logger.info("{}", doctors.getTotal());
    }


    /**
     * 获取医师所在的科室
     */
    @Test
    public void testGetDoctorDepartment2() {


        logger.info(" start：" + System.currentTimeMillis());

        Page<MedicalDoctorVO> page = new Page<>();
        page.setCurrent(1);
        page.setSize(3);

        Map<String, Object> mapAll = new HashMap<String, Object>();

        Map<String, Object> map = new HashMap<String, Object>();
        Page<MedicalDoctorVO> doctors0 = medicalDoctorBusinessService.selectDoctorPage(page, null, null, null, null, null);
        if (doctors0 != null && doctors0.getSize() > 0) {
            map.put("code", 0);
            map.put("text", "热门中医");
            map.put("doctors", doctors0.getRecords());
            mapAll.put("HOT", map);
        }
        /**
         * 循环枚举进行查询
         */
        List<Map> listMap = DoctorType1.getDoctorTypeList();
        for (int i = 0; i < listMap.size(); i++) {
            Map maps = listMap.get(i);
            Integer code = (Integer) maps.get("code");
            String text = (String) maps.get("value");

            Page<MedicalDoctorVO> doctors = medicalDoctorBusinessService.selectDoctorPage(page,
                    code, null, null, null, null);
            if (doctors != null && doctors.getSize() > 0) {
                Map<String, Object> mapDoctors = new HashMap<String, Object>();

                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("code", code);
                map1.put("text", text);
                map1.put("doctors", doctors.getRecords());
                if (code != null && code.equals(1)) {
                    mapAll.put("MQNZY", map1);
                } else if (code != null && code.equals(2)) {
                    mapAll.put("MLZY", map1);
                } else if (code != null && code.equals(3)) {
                    mapAll.put("SSMZZY", map1);
                } else if (code != null && code.equals(4)) {
                    mapAll.put("GYDS", map1);
                } else if (code != null && code.equals(5)) {
                    mapAll.put("GZY", map1);
                }
            }
        }

        logger.info(" end：" + System.currentTimeMillis());

    }

}