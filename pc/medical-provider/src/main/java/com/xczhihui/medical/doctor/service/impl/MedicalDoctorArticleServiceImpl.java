package com.xczhihui.medical.doctor.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.common.util.CodeUtil;
import com.xczhihui.bxg.common.util.enums.HeadlineType;
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

    @Override
    public Page<OeBxsArticleVO> listSpecialColumn(int page, String doctorId) {
        Page<OeBxsArticleVO> oeBxsArticleVOPage = new Page<>(page, 10);
        return oeBxsArticleVOPage.setRecords(oeBxsArticleMapper.listSpecialColumn(oeBxsArticleVOPage, doctorId));
    }

    @Override
    public void saveSpecialColumn(String doctorId, OeBxsArticle oeBxsArticle) {
        oeBxsArticleMapper.insert(oeBxsArticle);

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
        OeBxsArticleVO report = oeBxsArticleMapper.getReportById(id);
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
    public List<Map<String, Object>> listHotSpecialColumnAuthor(String doctorId) {
        return oeBxsArticleMapper.listSpecialColumnAuthorByDoctorId(doctorId);
    }

    @Override
    public Page<OeBxsArticleVO> listPublicSpecialColumn(int page, String doctorId) {
        Page<OeBxsArticleVO> oeBxsArticleVOPage = new Page<>(page, 10);
        oeBxsArticleVOPage.setRecords(oeBxsArticleMapper.getSpecialColumns(oeBxsArticleVOPage, doctorId));
        return oeBxsArticleVOPage;
    }

    @Override
    public Page<OeBxsArticleVO> listReport(int page, String doctorId) {
        Page<OeBxsArticleVO> oeBxsArticleVOPage = new Page<>(page, 10);
        oeBxsArticleVOPage.setRecords(oeBxsArticleMapper.listReport(oeBxsArticleVOPage, doctorId));
        return oeBxsArticleVOPage;
    }

    @Override
    public void saveReport(String doctorId, OeBxsArticle oeBxsArticle) {
        oeBxsArticleMapper.insert(oeBxsArticle);

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
    public List<Map<String, Object>> listReportDoctorByArticleId(int id) {
        return oeBxsArticleMapper.listReportDoctorByArticleId(id);
    }

    @Override
    public List<Map<String, Object>> listSpecialColumnAuthorByArticleId(int id) {
        return oeBxsArticleMapper.listSpecialColumnAuthorByArticleId(id);
    }

    @Override
    public List<Map<String, Object>> listReportDoctor(String doctorId) {
        return oeBxsArticleMapper.listReportDoctorByDoctorId(doctorId);
    }

    @Override
    public Page<OeBxsArticleVO> listPublicReport(int page, String doctorId) {
        Page<OeBxsArticleVO> oeBxsArticleVOPage = new Page<>(page, 10);
        return oeBxsArticleVOPage.setRecords(oeBxsArticleMapper.getNewsReportsByPage(oeBxsArticleVOPage, doctorId, HeadlineType.MYBD.getCode()));
    }

    @Override
    public Page<OeBxsArticleVO> listPublicWriting(int page, String doctorId) {
        Page<OeBxsArticleVO> oeBxsArticleVOPage = new Page<>(page, 10);
        return oeBxsArticleVOPage.setRecords(oeBxsArticleMapper.listPublicWriting(oeBxsArticleVOPage, doctorId));
    }
}
