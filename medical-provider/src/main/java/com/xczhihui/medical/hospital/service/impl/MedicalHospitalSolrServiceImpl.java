package com.xczhihui.medical.hospital.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.solr.utils.SolrConstant;
import com.xczhihui.common.solr.utils.SolrPages;
import com.xczhihui.common.solr.utils.SolrUtils;
import com.xczhihui.medical.field.vo.MedicalFieldVO;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalMapper;
import com.xczhihui.medical.hospital.service.IMedicalHospitalSolrService;
import com.xczhihui.medical.hospital.vo.MedicalHospitalSolrVO;
import com.xczhihui.utils.HtmlUtil;

/**
 * ClassName: MedicalHospitalBusinessServiceImpl.java <br>
 * Description:医馆业务接口类 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 下午 11:24 2017/12/9 0009<br>
 */
@Service
public class MedicalHospitalSolrServiceImpl implements IMedicalHospitalSolrService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MedicalHospitalMapper medicalHospitalMapper;

    @Value("${solr.url}")
    private String url;
    @Value("${solr.heiht.pre}")
    private String pre;
    @Value("${solr.heiht.post}")
    private String post;
    @Value("${solr.hospital.core}")
    private String core;

    private SolrUtils solrUtils;

    private static String getSearchStr(String queryKey,String field) {
        StringBuilder searchKeyWordStr = new StringBuilder();
        StringBuilder query = new StringBuilder();
        if (StringUtils.isNotBlank(queryKey)) {
            searchKeyWordStr.append("(");
            searchKeyWordStr.append("name:" + queryKey + SolrConstant.OR);
            searchKeyWordStr.append("description:" + queryKey + SolrConstant.OR);
            searchKeyWordStr.append("fieldTextList:" + queryKey + SolrConstant.OR);
            searchKeyWordStr.append("province:" + queryKey + SolrConstant.OR);
            searchKeyWordStr.append("city:" + queryKey + SolrConstant.OR);
            searchKeyWordStr.append("detailedAddress:" + queryKey);
            searchKeyWordStr.append(")");
            query.append(searchKeyWordStr);
        }
        String searchDepartmentKey;
        if (StringUtils.isNotBlank(field)) {
            searchDepartmentKey = "fieldIdList:" + field;
            if (query.length() > 0) {
                query.append(SolrConstant.AND);
            }
            query.append(searchDepartmentKey);
        }
        return query.toString();
    }

    @PostConstruct
    public void initHospitalsSolr() throws IOException, SolrServerException {
//        solrUtils = new SolrUtils(url, core, pre, post);
//        this.initHospitalsSolrData();
    }

    @Override
    public void initHospitalsSolrData() throws IOException, SolrServerException {
        List<MedicalHospitalSolrVO> medicalHospitalSolrVOS = selectHospitals4Solr();
        solrUtils.init(medicalHospitalSolrVOS);
        logger.warn("医馆数据初始化完成，共{}条", medicalHospitalSolrVOS.size());
    }

    @Override
    public void initHospitalsSolrDataById(String hospitalId) throws IOException, SolrServerException {
        if (StringUtils.isNotBlank(hospitalId)) {
            MedicalHospitalSolrVO medicalHospitalSolrVO = selectHospital4SolrById(hospitalId);
            if(medicalHospitalSolrVO != null){
                solrUtils.addBean(medicalHospitalSolrVO);
                logger.warn("医馆数据更新:{}", medicalHospitalSolrVO.toString());
            }else{
                deleteHospitalsSolrDataById(hospitalId);
            }
        }
    }

    @Override
    public void deleteHospitalsSolrDataById(String HospitalId) throws IOException, SolrServerException {
        solrUtils.deleteById(HospitalId);
        logger.warn("医馆数据删除:{}", HospitalId);
    }

    @Override
    public List<MedicalHospitalSolrVO> selectHospitals4Solr() {
        List<MedicalHospitalSolrVO> medicalHospitalSolrVOs = medicalHospitalMapper.selectHospitalList4Solr(null);
        medicalHospitalSolrVOs.forEach(medicalHospitalSolrVO -> handleMedicalHospitalSolrVO(medicalHospitalSolrVO));
        return medicalHospitalSolrVOs;
    }

    @Override
    public MedicalHospitalSolrVO selectHospital4SolrById(String id) {
        List<MedicalHospitalSolrVO> medicalHospitalSolrVOs = medicalHospitalMapper.selectHospitalList4Solr(id);
        MedicalHospitalSolrVO medicalHospitalSolrVO = medicalHospitalSolrVOs.get(0);
        handleMedicalHospitalSolrVO(medicalHospitalSolrVO);
        return medicalHospitalSolrVO;
    }

    /**
     * Description：处理医馆数据
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin
     * @Date: 2018/6/22 0022 上午 10:17
     **/
    private void handleMedicalHospitalSolrVO(MedicalHospitalSolrVO medicalHospitalSolrVO) {
        List<String> medicalHospitalPictureVOList = medicalHospitalMapper.selectPictureListByHospital(medicalHospitalSolrVO.getId());
        medicalHospitalSolrVO.setMedicalHospitalPictures(medicalHospitalPictureVOList);
        List<MedicalFieldVO> fieldList = medicalHospitalMapper.selectMedicalFieldsByHospitalId(medicalHospitalSolrVO.getId());

        List<String> fieldIdList = new ArrayList<>();
        List<String> fieldTextList = new ArrayList<>();
        fieldList.forEach(field->{
            fieldIdList.add(field.getId());
            fieldTextList.add(field.getName());
        });
        medicalHospitalSolrVO.setFieldIdList(fieldIdList);
        medicalHospitalSolrVO.setFieldTextList(fieldTextList);

        medicalHospitalSolrVO.setDescription(HtmlUtil.getTextFromHtml(medicalHospitalSolrVO.getDescription()));
    }

    @Override
    public Page<MedicalHospitalSolrVO> selectHospitalListBySolr(Page page, String name,String field) throws IOException, SolrServerException {
        String searchStr = getSearchStr(name,field);
        searchStr = searchStr.equals("") ? "*:*" : searchStr;
        Map<String, SolrQuery.ORDER> sortedMap = new LinkedHashMap<>();

        sortedMap.put("score", SolrQuery.ORDER.desc);
        sortedMap.put("recommendSort", SolrQuery.ORDER.desc);
        sortedMap.put("mhscore", SolrQuery.ORDER.desc);
        sortedMap.put("authentication", SolrQuery.ORDER.desc);
        sortedMap.put("createTime", SolrQuery.ORDER.desc);

        SolrPages Hospitals = solrUtils.getByPage(searchStr, page.getCurrent(), page.getSize(), MedicalHospitalSolrVO.class, sortedMap);
        page.setTotal(Hospitals.getTotal());
        page.setRecords(Hospitals.getList());
        return page;
    }

}
