package com.xczhihui.medical.doctor.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.xczhihui.common.solr.utils.HanyuPinyinHelper;
import com.xczhihui.common.solr.utils.SolrConstant;
import com.xczhihui.common.solr.utils.SolrPages;
import com.xczhihui.common.solr.utils.SolrUtils;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorMapper;
import com.xczhihui.medical.doctor.service.IMedicalDoctorSolrService;
import com.xczhihui.medical.doctor.vo.DoctorQueryVo;
import com.xczhihui.medical.doctor.vo.MedicalDoctorSolrVO;
import com.xczhihui.medical.field.vo.MedicalFieldVO;

/**
 * ClassName: MedicalDoctorBusinessServiceImpl.java <br>
 * Description:医师业务接口类 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 下午 11:24 2017/12/9 0009<br>
 */
@Service
public class MedicalDoctorSolrServiceImpl implements IMedicalDoctorSolrService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MedicalDoctorMapper medicalDoctorMapper;

    @Value("${solr.url}")
    private String url;
    @Value("${solr.heiht.pre}")
    private String pre;
    @Value("${solr.heiht.post}")
    private String post;
    @Value("${solr.core}")
    private String core;

    private SolrUtils solrUtils;

    @PostConstruct
    public void initDoctorsSolr() throws IOException, SolrServerException {
        solrUtils = new SolrUtils(url,core,pre,post);
//        this.initDoctorsSolrData();
    }

    @Override
    public void initDoctorsSolrData() throws IOException, SolrServerException {
        List<MedicalDoctorSolrVO> medicalDoctorSolrVOS = selectDoctors4Solr();
        solrUtils.init(medicalDoctorSolrVOS);
        logger.warn("医师数据初始化完成，共{}条",medicalDoctorSolrVOS.size());
    }

    @Override
    public void initDoctorsSolrDataById(String doctorId) throws IOException, SolrServerException {
        MedicalDoctorSolrVO medicalDoctorSolrVO = selectDoctor4SolrById(doctorId);
        solrUtils.addBean(medicalDoctorSolrVO);
        logger.warn("医师数据更新:{}",medicalDoctorSolrVO.toString());
    }

    @Override
    public void deleteDoctorsSolrDataById(String doctorId) throws IOException, SolrServerException {
        solrUtils.deleteById(doctorId);
        logger.warn("医师数据删除:{}",doctorId);
    }

    @Override
    public List<MedicalDoctorSolrVO> selectDoctors4Solr() {
        List<MedicalDoctorSolrVO> medicalDoctorSolrVOs = medicalDoctorMapper.selectDoctorList4Solr(null);
        medicalDoctorSolrVOs.forEach(medicalDoctorSolrVO -> handleMedicalDoctorSolrVO(medicalDoctorSolrVO));
        return medicalDoctorSolrVOs;
    }

    @Override
    public MedicalDoctorSolrVO selectDoctor4SolrById(String id) {
        List<MedicalDoctorSolrVO> medicalDoctorSolrVOs = medicalDoctorMapper.selectDoctorList4Solr(id);
        MedicalDoctorSolrVO medicalDoctorSolrVO = medicalDoctorSolrVOs.get(0);
        handleMedicalDoctorSolrVO(medicalDoctorSolrVO);
        return medicalDoctorSolrVO;
    }

    /**
     * Description：处理医师数据
     * creed: Talk is cheap,show me the code
     * @author name：yuxin
     * @Date: 2018/6/22 0022 上午 10:17
     **/
    private void handleMedicalDoctorSolrVO(MedicalDoctorSolrVO medicalDoctorSolrVO){
        medicalDoctorSolrVO.setNamePinyin(HanyuPinyinHelper.toHanyuPinyin(medicalDoctorSolrVO.getName()));

        if(medicalDoctorSolrVO.getRecommendSort()==null){
            medicalDoctorSolrVO.setRecommendSort(-Integer.MAX_VALUE);
        }

        List<MedicalFieldVO> medicalFields = medicalDoctorMapper.selectMedicalFieldsByDoctorId(medicalDoctorSolrVO.getId());
        List<String> departmentName = new ArrayList<>();
        List<String> departmentId = new ArrayList<>();
        medicalFields.forEach(medicalFieldVO -> {
            departmentName.add(medicalFieldVO.getName());
            departmentId.add(medicalFieldVO.getId());
        });
        medicalDoctorSolrVO.setDepartmentId(departmentId);
        medicalDoctorSolrVO.setDepartmentName(departmentName);
    }

    @Override
    public Page<MedicalDoctorSolrVO> selectDoctorListBySolr(Page page, DoctorQueryVo dqv) throws IOException, SolrServerException {
        String searchStr = getSearchStr(dqv);
        searchStr = searchStr.equals("") ? "*:*" : searchStr;
        Map<String, SolrQuery.ORDER> sortedMap = new HashMap<>();
        String sort;
        if(dqv.getSortType()!=null && dqv.getSortType().equals(2)){
            sort = "focusCount";
        }else{
            sort = "recommendSort";
        }
        sortedMap.put("score", SolrQuery.ORDER.desc);
        sortedMap.put(sort, SolrQuery.ORDER.desc);

        SolrPages doctors = solrUtils.getByPage(searchStr, page.getCurrent(), page.getSize(), MedicalDoctorSolrVO.class, sortedMap);
        page.setTotal(doctors.getTotal());
        page.setRecords(doctors.getList());
        return page;
    }

    private static String getSearchStr(DoctorQueryVo dqv){
        StringBuilder searchKeyWordStr = new StringBuilder();
        StringBuilder query = new StringBuilder();
        String queryKey = dqv.getQueryKey();
        if(StringUtils.isNotBlank(queryKey)){
            searchKeyWordStr.append("(");
            searchKeyWordStr.append("name:"+ queryKey + SolrConstant.OR);
            searchKeyWordStr.append("departmentName:"+ queryKey + SolrConstant.OR);
            searchKeyWordStr.append("title:"+ queryKey + SolrConstant.OR);
            searchKeyWordStr.append("description:"+ queryKey + SolrConstant.OR);
            searchKeyWordStr.append("hospitalName:"+ queryKey + SolrConstant.OR);
            searchKeyWordStr.append("province:"+ queryKey + SolrConstant.OR);
            searchKeyWordStr.append("city:"+ queryKey + SolrConstant.OR);
            searchKeyWordStr.append("detailedAddress:"+ queryKey);
            searchKeyWordStr.append(")");
            query.append(searchKeyWordStr);
        }
        String searchDepartmentKey;
        if(StringUtils.isNotBlank(dqv.getDepartmentId())){
            searchDepartmentKey = "departmentId:"+dqv.getDepartmentId();
            if(query.length()>0){
                query.append(SolrConstant.AND);
            }
            query.append(searchDepartmentKey);
        }
        String searchTypeKey;
        if(StringUtils.isNotBlank(dqv.getType())){
            searchTypeKey = "type:"+dqv.getType();
            if(query.length()>0){
                query.append(SolrConstant.AND);
            }
            query.append(searchTypeKey);
        }
        return query.toString();
    }

}
