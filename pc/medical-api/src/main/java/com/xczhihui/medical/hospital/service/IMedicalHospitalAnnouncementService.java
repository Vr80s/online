package com.xczhihui.medical.hospital.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.hospital.model.MedicalHospitalAnnouncement;
import com.xczhihui.medical.hospital.vo.MedicalHospitalAnnouncementVO;

/**
 * 医馆公告管理接口
 *
 * @author hejiwei
 */
public interface IMedicalHospitalAnnouncementService {

    /**
     * 医馆公告列表
     *
     * @param page       分页参数
     * @param hospitalId 医馆id
     * @return 公告分页列表
     */
    Page<MedicalHospitalAnnouncementVO> list(int page, String hospitalId);

    /**
     * 新增医馆公告
     *
     * @param announcement 公告
     */
    void save(MedicalHospitalAnnouncement announcement);

    /**
     * 逻辑删除公告
     *
     * @param id         id
     * @param hospitalId 医馆id
     * @return 是否删除成功
     */
    boolean deleteById(String id, String hospitalId);

    /**
     * 获取公告数据
     *
     * @param id id
     * @return 公告数据
     */
    MedicalHospitalAnnouncement get(String id);
}
