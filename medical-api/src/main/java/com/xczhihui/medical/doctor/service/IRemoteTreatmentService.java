package com.xczhihui.medical.doctor.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.Treatment;
import com.xczhihui.medical.doctor.model.TreatmentAppointmentInfo;
import com.xczhihui.medical.doctor.vo.TreatmentVO;

/**
 * 远程诊疗服务
 *
 * @author hejiwei
 */
public interface IRemoteTreatmentService {

    /**
     * 新增远程诊疗
     *
     * @param treatment treatment
     */
    void save(Treatment treatment);

    /**
     * 更新远程诊疗
     *
     * @param treatment treatment
     * @param id        id
     */
    void update(Treatment treatment, Integer id);

    /**
     * 删除远程诊疗
     *
     * @param id     id
     * @param userId userId
     */
    void delete(Integer id, String userId);

    /**
     * 保存预约信息
     *
     * @param treatmentAppointmentInfo 预约信息
     * @return
     */
    int saveAppointmentInfo(TreatmentAppointmentInfo treatmentAppointmentInfo);

    /**
     * 查询医师诊疗
     *
     * @param doctorId          doctorId
     * @param onlyUnAppointment onlyUnAppointment
     * @param accountId         accountId
     * @return
     */
    List<TreatmentVO> listAppointment(String doctorId, boolean onlyUnAppointment, String accountId);

    /**
     * 更新审核预约的状态
     *
     * @param id     id
     * @param status status
     */
    void updateStatus(Integer id, boolean status);

    /**
     * 取消预约
     *
     * @param id id
     */
    void updateAppointmentForCancel(Integer id);

    /**
     * 远程诊疗列表
     *
     * @param doctorId doctorId
     * @param page     page
     * @param size     size
     * @return
     */
    Page<TreatmentVO> list(String doctorId, int page, int size);

    /**
     * 获取预约信息
     *
     * @param id id
     * @return
     */
    TreatmentVO getInfo(int id);

    /**
     * 校验是否预约了相同时间段的预约
     *
     * @param id        id
     * @param accountId accountId
     * @return
     */
    boolean checkRepeatAppoint(int id, String accountId);
}
