package com.xczhihui.medical.service.impl;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.medical.doctor.service.IMedicalDoctorSolrService;
import com.xczhihui.medical.service.DoctorSolrService;

/**
 * Description：医师solr
 * creed: Talk is cheap,show me the code
 * @author name：yuxin
 * @Date: 2018/6/22 0022 上午 11:16
 **/
@Service
public class DoctorSolrServiceImpl implements DoctorSolrService {

    @Autowired
    private IMedicalDoctorSolrService medicalDoctorSolrService;

    @Override
    public void initDoctorsSolrData() throws IOException, SolrServerException {
        medicalDoctorSolrService.initDoctorsSolrData();
    }


}
