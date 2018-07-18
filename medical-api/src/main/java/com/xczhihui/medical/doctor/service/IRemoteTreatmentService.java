package com.xczhihui.medical.doctor.service;

import java.util.List;

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
     * @param doctorId doctorId
     * @return
     */
    List<TreatmentVO> listAppointment(String doctorId, boolean onlyUnAppointment);
}
