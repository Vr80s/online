package com.xczhihui.medical.doctor.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xczhihui.medical.doctor.mapper.MedicalDoctorPostsMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorReportMapper;
import com.xczhihui.medical.doctor.mapper.MedicalSpecialColumnMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorReport;
import com.xczhihui.medical.doctor.model.MedicalDoctorSpecialColumn;
import com.xczhihui.medical.doctor.service.IMedicalDoctorArticleService;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import com.xczhihui.medical.headline.mapper.OeBxsArticleMapper;
import com.xczhihui.medical.headline.model.OeBxsArticle;

/**
 * @author hejiwei
 */
@Service
public class MedicalDoctorArticleServiceImpl implements IMedicalDoctorArticleService {

    @Autowired
    private OeBxsArticleMapper oeBxsArticleMapper;
    @Autowired
    private MedicalDoctorReportMapper medicalDoctorReportMapper;
    @Autowired
    private MedicalSpecialColumnMapper medicalSpecialColumnMapper;
    @Autowired
    private MedicalDoctorPostsMapper medicalDoctorPostsMapper;

    @Override
    public Page<OeBxsArticleVO> listSpecialColumn(int page, int size, String doctorId, String keyQuery, String type) {
        Page<OeBxsArticleVO> oeBxsArticleVOPage = new Page<>(page, size);
        return oeBxsArticleVOPage.setRecords(oeBxsArticleMapper.listSpecialColumn(oeBxsArticleVOPage, doctorId, keyQuery, type));
    }

    @Override
    public void saveSpecialColumn(String doctorId, OeBxsArticle oeBxsArticle) {
        oeBxsArticleMapper.insert(oeBxsArticle);
        if(oeBxsArticle.getStatus() == 1){
            //更新动态
            addDoctorPosts(doctorId, oeBxsArticle);
        }
        MedicalDoctorSpecialColumn medicalDoctorSpecialColumn = new MedicalDoctorSpecialColumn();
        medicalDoctorSpecialColumn.setArticleId(String.valueOf(oeBxsArticle.getId()));
        medicalDoctorSpecialColumn.setCreateTime(new Date());
        medicalDoctorSpecialColumn.setDoctorId(doctorId);
        medicalDoctorSpecialColumn.setId(CodeUtil.getRandomUUID());
        medicalSpecialColumnMapper.insert(medicalDoctorSpecialColumn);
    }

    @Override
    public boolean updateSpecialColumn(String doctorId, OeBxsArticle oeBxsArticle, String id) {
        OeBxsArticle oldOeBxsArticle = oeBxsArticle.selectById(id);
        if (oldOeBxsArticle == null || oldOeBxsArticle.getDelete()) {
            return false;
        }
        oldOeBxsArticle.setUpdateTime(new Date());
        oldOeBxsArticle.setTitle(oeBxsArticle.getTitle());
        oldOeBxsArticle.setContent(oeBxsArticle.getContent());
        oldOeBxsArticle.setImgPath(oeBxsArticle.getImgPath());
        oldOeBxsArticle.setUserId(oeBxsArticle.getUserId());
        oeBxsArticleMapper.updateById(oldOeBxsArticle);
        return true;
    }

    @Override
    public OeBxsArticleVO getSpecialColumn(String id) {
        OeBxsArticleVO report = oeBxsArticleMapper.getSpecialColumnById(id);
        if (report == null) {
            throw new IllegalArgumentException("专栏已删除");
        }
        return report;
    }

    @Override
    public boolean updateSpecialColumnStatus(String id, int status) {
        if (status != 0 && status != 1) {
            throw new IllegalArgumentException("非法status值");
        }
        OeBxsArticle oeBxsArticle = oeBxsArticleMapper.selectById(id);
        if (oeBxsArticle != null && !oeBxsArticle.getDelete()) {
            oeBxsArticle.setStatus(status);
            oeBxsArticleMapper.updateById(oeBxsArticle);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSpecialColumnById(String id) {
        OeBxsArticle oeBxsArticle = oeBxsArticleMapper.selectById(id);
        if (oeBxsArticle != null && !oeBxsArticle.getDelete()) {
            oeBxsArticle.setDelete(true);
            oeBxsArticleMapper.updateById(oeBxsArticle);
            return true;
        }
        return false;
    }

    @Override
    public Page<OeBxsArticleVO> listReport(int page, int size, String doctorId, String keyQuery) {
        Page<OeBxsArticleVO> oeBxsArticleVOPage = new Page<>(page, size);
        oeBxsArticleVOPage.setRecords(oeBxsArticleMapper.listReport(oeBxsArticleVOPage, doctorId, keyQuery));
        return oeBxsArticleVOPage;
    }

    @Override
    public void saveReport(String doctorId, OeBxsArticle oeBxsArticle) {
        oeBxsArticleMapper.insert(oeBxsArticle);
        if(oeBxsArticle.getStatus() == 1){
            //更新动态
            addDoctorPosts(doctorId, oeBxsArticle);
        }
        MedicalDoctorReport medicalDoctorReport = new MedicalDoctorReport();
        medicalDoctorReport.setArticleId(String.valueOf(oeBxsArticle.getId()));
        medicalDoctorReport.setCreateTime(new Date());
        medicalDoctorReport.setDoctorId(doctorId);
        medicalDoctorReport.setId(CodeUtil.getRandomUUID());
        medicalDoctorReportMapper.insert(medicalDoctorReport);

    }


    @Override
    public boolean updateReport(String doctorId, OeBxsArticle oeBxsArticle, String id) {
        OeBxsArticle oldOeBxsArticle = oeBxsArticle.selectById(id);
        if (oldOeBxsArticle == null || oldOeBxsArticle.getDelete()) {
            return false;
        }
        oldOeBxsArticle.setUpdateTime(new Date());
        oldOeBxsArticle.setTitle(oeBxsArticle.getTitle());
        oldOeBxsArticle.setContent(oeBxsArticle.getContent());
        oldOeBxsArticle.setImgPath(oeBxsArticle.getImgPath());
        oldOeBxsArticle.setUrl(oeBxsArticle.getUrl());
        oldOeBxsArticle.setUserId(oeBxsArticle.getUserId());
        oeBxsArticleMapper.updateById(oldOeBxsArticle);

        MedicalDoctorReport medicalDoctorReport = medicalDoctorReportMapper.findByArticleIdAndDoctorId(id, doctorId);
        if (medicalDoctorReport != null) {
            medicalDoctorReportMapper.updateById(medicalDoctorReport);
        }
        return true;
    }

    @Override
    public OeBxsArticleVO getReport(String id) {
        OeBxsArticleVO report = oeBxsArticleMapper.getReportById(id);
        if (report == null) {
            throw new IllegalArgumentException("报道数据已被删除");
        }
        return report;
    }

    @Override
    public boolean updateReportStatus(String id, int status) {
        if (status != 0 && status != 1) {
            throw new IllegalArgumentException("非法status值");
        }
        OeBxsArticle oeBxsArticle = oeBxsArticleMapper.selectById(id);

        oeBxsArticle.setStatus(status);
        oeBxsArticleMapper.updateById(oeBxsArticle);
        return true;
    }

    @Override
    public boolean deleteReportById(String id) {
        OeBxsArticle oeBxsArticle = oeBxsArticleMapper.selectById(id);
        if (oeBxsArticle != null && !oeBxsArticle.getDelete()) {
            oeBxsArticle.setDelete(true);
            oeBxsArticleMapper.updateById(oeBxsArticle);
            return true;
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> listReportDoctor(int size) {
        return oeBxsArticleMapper.listReportDoctor(size);
    }

    @Override
    public List<Map<String, Object>> listWritingAuthor(int size) {
        return oeBxsArticleMapper.listWritingAuthor(size);
    }

    @Override
    public List<Map<String, Object>> listHotSpecialColumnAuthor(int size) {
        return oeBxsArticleMapper.listSpecialColumnAuthor(size);
    }

    @Override
    public List<Map<String, Object>> listWritingAuthorByArticleId(int articleId) {
        return oeBxsArticleMapper.listWritingAuthorByArticleId(articleId);
    }

    @Override
    public List<Map<String, Object>> listReportDoctorByArticleId(int articleId) {
        return oeBxsArticleMapper.listReportDoctorByArticleId(articleId);
    }

    @Override
    public List<Map<String, Object>> listHotSpecialColumnAuthorByArticleId(int articleId) {
        return oeBxsArticleMapper.listSpecialColumnAuthorByArticleId(articleId);
    }

    @Override
    public Page<OeBxsArticleVO> listPublicArticle(int page, String typeId) {
        Page<OeBxsArticleVO> oeBxsArticleVOPage = new Page<>(page, 10);
        return oeBxsArticleVOPage.setRecords(oeBxsArticleMapper.listPublicArticle(oeBxsArticleVOPage, typeId));
    }

    @Override
    public Page<OeBxsArticleVO> listPublicWritings(int page, int size) {
        Page<OeBxsArticleVO> oeBxsArticleVOPage = new Page<>(page, size);
        return oeBxsArticleVOPage.setRecords(oeBxsArticleMapper.listPublicWritings(oeBxsArticleVOPage));
    }

    @Override
    public List<Map<String, Object>> listSpecialAuthorContent(int size) {
        return oeBxsArticleMapper.listSpecialAuthorContent(size);
    }

    @Override
    public Page<OeBxsArticleVO> list(String type, String userId, String keyword) {
        //TODO 前端不分页,这里写死分页参数
        Page<OeBxsArticleVO> page = new Page<>(1, 1000);
        return page.setRecords(oeBxsArticleMapper.list(type, userId, keyword, page));
    }

    @Override
    public OeBxsArticleVO get(int id) {
        return oeBxsArticleMapper.get(id);
    }

    @Override
    public OeBxsArticleVO getSimpleInfo(int id) {
        return oeBxsArticleMapper.getSimpleInfo(id);
    }


/**
 * Description：添加专栏/报道时同步动态
 * creed: Talk is cheap,show me the code
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/7/4 15:13
 **/
    private void addDoctorPosts(String doctorId, OeBxsArticle oeBxsArticle) {
        MedicalDoctorPosts mdp = new MedicalDoctorPosts();
        mdp.setType(4);
        mdp.setDoctorId(doctorId);
        mdp.setContent(oeBxsArticle.getContent());
        mdp.setArticleId(oeBxsArticle.getId());
        mdp.setArticleContent(oeBxsArticle.getContent());
        mdp.setArticleImgPath(oeBxsArticle.getImgPath());
        mdp.setArticleTitle(oeBxsArticle.getTitle());
        medicalDoctorPostsMapper.addMedicalDoctorPosts(mdp);
    }


}


