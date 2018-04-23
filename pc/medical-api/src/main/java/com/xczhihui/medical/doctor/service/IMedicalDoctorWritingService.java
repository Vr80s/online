package com.xczhihui.medical.doctor.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.MedicalWriting;
import com.xczhihui.medical.doctor.vo.MedicalWritingVO;

/**
 * 著作管理服务类
 *
 * @author hejiwei
 */
public interface IMedicalDoctorWritingService {

    /**
     * 医师的著作列表
     *
     * @param page     页码
     * @param size     每页数量
     * @param doctorId 医师id
     * @return 列表数据
     */
    Page<MedicalWritingVO> list(int page, int size, String doctorId);

    /**
     * 著作列表
     *
     * @param page     页码
     * @param size     每页数量
     * @param doctorId 医师id
     * @return 列表数据
     */
    Page<MedicalWritingVO> listPublic(int page, int size, String doctorId);

    /**
     * 获取著作数据
     *
     * @param id id
     * @return 著作数据
     */
    MedicalWritingVO get(String id);

    /**
     * 更新著作状态
     *
     * @param doctorId 医师id
     * @param id       id
     * @param status   状态
     * @return 是否成功
     */
    boolean updateStatus(String doctorId, String id, boolean status);

    /**
     * 新增著作
     *
     * @param doctorId       医师id
     * @param medicalWriting 著作
     * @param userId         用户id
     */
    void save(String doctorId, MedicalWriting medicalWriting, String userId);

    /**
     * 更新著作
     *
     * @param id             id
     * @param doctorId       医师id
     * @param medicalWriting 著作
     * @return 是否更新成功
     */
    boolean update(String id, String doctorId, MedicalWriting medicalWriting);

    /**
     * 删除
     *
     * @param id       id
     * @param doctorId 医师id
     */
    void delete(String id, String doctorId);
}
