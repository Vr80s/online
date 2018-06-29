package com.xczhihui.medical.doctor.service.impl;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.service.IMedicalDoctorSolrService;
import com.xczhihui.medical.doctor.vo.DoctorQueryVo;
import com.xczhihui.medical.doctor.vo.MedicalDoctorSolrVO;

import test.BaseJunit4Test;

/**
 * 医师入驻测试类
 */
public class MedicalDoctorServiceImplTest extends BaseJunit4Test {

    @Autowired
    private IMedicalDoctorSolrService service;

    @Test
    public void testSolrInit(){
    }

    @Test
    public void testSolrQuery() throws IOException, SolrServerException {
        Page page = new Page(1,10);
        DoctorQueryVo dqv = new DoctorQueryVo();
        dqv.setQueryKey("会针灸的于心");
        Page<MedicalDoctorSolrVO> medicalDoctorSolrVOPage = service.selectDoctorListBySolr(page, dqv);
    }

    @Test
    public void testSolrDelete() throws IOException, SolrServerException {
        service.deleteDoctorsSolrDataById("14c2192523c5438f9a10d17994a1c6a3");
    }

    @Test
    public void testSolrInitById() throws IOException, SolrServerException {
        service.initDoctorsSolrDataById("14c2192523c5438f9a10d17994a1c6a3");
    }
}