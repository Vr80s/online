package com.xczhihui.medical.doctor.service.impl;

import java.util.Date;

import com.xczhihui.common.util.enums.HeadlineType;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorPostsMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorPosts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorWritingMapper;
import com.xczhihui.medical.doctor.mapper.MedicalWritingMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorWriting;
import com.xczhihui.medical.doctor.model.MedicalWriting;
import com.xczhihui.medical.doctor.service.IMedicalDoctorWritingService;
import com.xczhihui.medical.doctor.vo.MedicalWritingVO;
import com.xczhihui.medical.headline.mapper.OeBxsArticleMapper;
import com.xczhihui.medical.headline.model.OeBxsArticle;

/**
 * @author hejiwei
 */
@Service
public class MedicalDoctorWritingServiceImpl implements IMedicalDoctorWritingService {

    @Autowired
    private MedicalWritingMapper medicalWritingMapper;
    @Autowired
    private OeBxsArticleMapper oeBxsArticleMapper;
    @Autowired
    private MedicalDoctorWritingMapper medicalDoctorWritingMapper;
    @Autowired
    private MedicalDoctorPostsMapper medicalDoctorPostsMapper;

    @Override
    public Page<MedicalWritingVO> list(int page, int size, String userId) {
        Page<MedicalWritingVO> medicalWritingVOPage = new Page<>(page, size);
        medicalWritingVOPage.setRecords(medicalWritingMapper.listWritingByUserId(medicalWritingVOPage, userId));
        return medicalWritingVOPage;
    }

    @Override
    public Page<MedicalWritingVO> listPublic(int page, int size, String doctorId) {
        Page<MedicalWritingVO> medicalWritingVOPage = new Page<>(page, size);
        medicalWritingVOPage.setRecords(medicalWritingMapper.listWriting(medicalWritingVOPage, doctorId));
        return medicalWritingVOPage;
    }

    @Override
    public MedicalWritingVO get(String id) {
        return medicalWritingMapper.get(id);
    }

    @Override
    public boolean updateStatus(String doctorId, String id, boolean status) {
        if (medicalWritingMapper.updateStatus(doctorId, id, status) == 1) {
            MedicalWriting medicalWriting = medicalWritingMapper.selectById(id);
            if (medicalWriting == null) {
                throw new IllegalArgumentException("参数非法");
            }

            OeBxsArticle oeBxsArticle = oeBxsArticleMapper.selectById(medicalWriting.getArticleId());
            oeBxsArticle.setStatus(status ? 1 : 0);
            oeBxsArticleMapper.updateById(oeBxsArticle);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void save(String doctorId, MedicalWriting medicalWriting, String userId) {
        OeBxsArticle oeBxsArticle = new OeBxsArticle();
        oeBxsArticle.setTitle(medicalWriting.getTitle());
        oeBxsArticle.setImgPath(medicalWriting.getImgPath());
        oeBxsArticle.setContent(medicalWriting.getRemark());
        oeBxsArticle.setUserId(medicalWriting.getAuthor());
        oeBxsArticle.setCreateTime(new Date());
        oeBxsArticle.setDelete(false);
        oeBxsArticle.setStatus(medicalWriting.getStatus() ? 1 : 0);
        oeBxsArticle.setUserCreated(true);
        oeBxsArticle.setCreatePerson(userId);
        oeBxsArticle.setSort(0);
        oeBxsArticle.setTypeId(HeadlineType.ZZ.getCode());
        oeBxsArticleMapper.insert(oeBxsArticle);
        Integer articleId = oeBxsArticle.getId();

        if(oeBxsArticle.getStatus() == 1){
            //更新动态
            MedicalDoctorPosts mdp = new MedicalDoctorPosts();
            mdp.setType(4);
            mdp.setDoctorId(doctorId);
            mdp.setArticleId(articleId);
            mdp.setArticleContent(oeBxsArticle.getContent());
            mdp.setArticleImgPath(oeBxsArticle.getImgPath());
            mdp.setArticleTitle(oeBxsArticle.getTitle());
            medicalDoctorPostsMapper.addMedicalDoctorPosts(mdp);
        }
        medicalWriting.setArticleId(articleId);
        medicalWriting.setId(CodeUtil.getRandomUUID());
        medicalWritingMapper.insert(medicalWriting);

        MedicalDoctorWriting medicalDoctorWriting = new MedicalDoctorWriting();
        medicalDoctorWriting.setId(CodeUtil.getRandomUUID());
        medicalDoctorWriting.setCreateTime(new Date());
        medicalDoctorWriting.setDoctorId(doctorId);
        medicalDoctorWriting.setWritingId(medicalWriting.getId());
        medicalDoctorWritingMapper.insert(medicalDoctorWriting);
    }

    @Override
    public boolean update(String id, String doctorId, MedicalWriting medicalWriting) {
        MedicalWriting oldMedicalWriting = medicalWritingMapper.selectById(id);
        if (oldMedicalWriting == null) {
            throw new IllegalArgumentException("著作未找到");
        }
        oldMedicalWriting.setRemark(medicalWriting.getRemark());
        oldMedicalWriting.setTitle(medicalWriting.getTitle());
        oldMedicalWriting.setImgPath(medicalWriting.getImgPath());
        oldMedicalWriting.setBuyLink(medicalWriting.getBuyLink());
        oldMedicalWriting.setUpdatePerson(medicalWriting.getCreatePerson());
        oldMedicalWriting.setUpdateTime(new Date());
        oldMedicalWriting.setAuthor(medicalWriting.getAuthor());
        medicalWritingMapper.updateById(oldMedicalWriting);

        OeBxsArticle oldOeBxsArticle = oeBxsArticleMapper.selectById(oldMedicalWriting.getArticleId());
        oldOeBxsArticle.setTitle(medicalWriting.getTitle());
        oldOeBxsArticle.setContent(medicalWriting.getRemark());
        oldOeBxsArticle.setImgPath(medicalWriting.getImgPath());
        oldOeBxsArticle.setUserId(medicalWriting.getAuthor());
        oeBxsArticleMapper.updateById(oldOeBxsArticle);
        return true;
    }

    @Override
    public void delete(String id, String doctorId) {
        MedicalWriting medicalWriting = medicalWritingMapper.selectById(id);
        if (medicalWriting == null) {
            throw new IllegalArgumentException("参数非法");
        }
        medicalWriting.setDeleted(true);
        medicalWritingMapper.updateById(medicalWriting);

        OeBxsArticle oeBxsArticle = oeBxsArticleMapper.selectById(medicalWriting.getArticleId());
        oeBxsArticle.setDelete(true);
        oeBxsArticleMapper.updateById(oeBxsArticle);
    }

    @Override
    public MedicalWriting findByArticleId(int articleId) {
        return medicalWritingMapper.findByArticleId(articleId);
    }
}
