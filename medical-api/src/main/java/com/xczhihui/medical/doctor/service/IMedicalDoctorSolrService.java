package com.xczhihui.medical.doctor.service;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.vo.DoctorQueryVo;
import com.xczhihui.medical.doctor.vo.MedicalDoctorSolrVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface IMedicalDoctorSolrService {

    /**
     * Description：将所有医师数据初始化到solr中
     * creed: Talk is cheap,show me the code
     * @author name：yuxin
     * @Date: 2018/6/22 0022 上午 10:04
     **/
    void initDoctorsSolrData() throws IOException, SolrServerException;

    /**
     * Description：初始化（或更新）某一条数据
     * creed: Talk is cheap,show me the code
     * @author name：yuxin
     * @Date: 2018/6/22 0022 上午 10:05
     **/
    void initDoctorsSolrDataById(String doctorId) throws IOException, SolrServerException;

    /**
     * Description：根据医师id删除某条医师数据
     *              （医师被删除或禁用时调用该方法）
     * creed: Talk is cheap,show me the code
     * @author name：yuxin
     * @Date: 2018/6/22 0022 上午 10:06
     **/
    void deleteDoctorsSolrDataById(String doctorId) throws IOException, SolrServerException;

    List<MedicalDoctorSolrVO> selectDoctors4Solr();

    MedicalDoctorSolrVO selectDoctor4SolrById(String id);

    Page<MedicalDoctorSolrVO> selectDoctorListBySolr(Page page, DoctorQueryVo dqv) throws IOException, SolrServerException;
}
