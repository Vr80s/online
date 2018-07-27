package com.xczhihui.medical.hospital.service;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.hospital.vo.MedicalHospitalSolrVO;

/**
 * 医馆搜索业务类
 */
public interface IMedicalHospitalSolrService {

    /**
     * Description：将所有医医馆数据初始化到solr中
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin
     * @Date: 2018/6/22 0022 上午 10:04
     **/
    void initHospitalsSolrData() throws IOException, SolrServerException;

    /**
     * Description：初始化（或更新）某一条数据
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin
     * @Date: 2018/6/22 0022 上午 10:05
     **/
    void initHospitalsSolrDataById(String HospitalId) throws IOException, SolrServerException;

    /**
     * Description：根据医馆id删除某条医师数据
     * （医师被删除或禁用时调用该方法）
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin
     * @Date: 2018/6/22 0022 上午 10:06
     **/
    void deleteHospitalsSolrDataById(String HospitalId) throws IOException, SolrServerException;

    List<MedicalHospitalSolrVO> selectHospitals4Solr();

    MedicalHospitalSolrVO selectHospital4SolrById(String id);

    Page<MedicalHospitalSolrVO> selectHospitalListBySolr(Page page, String name, String field) throws IOException, SolrServerException;
}
