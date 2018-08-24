package com.xczhihui.medical.doctor.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.Treatment;
import com.xczhihui.medical.doctor.model.TreatmentAppointmentInfo;
import com.xczhihui.medical.doctor.vo.TreatmentVO;

import java.util.List;
import java.util.Map;

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

    /**
     * 校验预约状态
     *
     * @param id        id
     * @param accountId accountId
     * @return
     */
    int checkAppointment(int id, String accountId);

    /**
     * <p>Title: selectById</p>
     * <p>Description: </p>
     *
     * @param id
     * @return
     */
    TreatmentAppointmentInfo selectById(int id);

    /**
     * 删除诊疗预约信息
     *
     * @param infoId infoId
     */
    void deleteAppointmentInfo(int infoId);

    /**
     * 更新诊疗直播状态
     *
     * @param id     id
     * @param status status
     * @return
     */
    Map<String, Object> updateTreatmentStartStatus(int id, int status);

    /**
     * 分页获取医师远程诊疗
     *
     * @param doctorId doctorId
     * @return
     */
    List<TreatmentVO> listByDoctorId(String doctorId);

    /**
     * 分页获取用户的诊疗预约
     *
     * @param userId userId
     * @return
     */
    List<TreatmentVO> listByUserId(String userId);

    /**
     * 查询出时间已过期的预约
     *
     * @return
     */
    List<Treatment> selectUpcomingExpire();

    /**
     * 将课程id更新至诊疗数据中
     *
     * @param id       id
     * @param courseId courseId
     */
    void updateTreatmentCourseId(int id, int courseId);

    /**
     * 标记为过期
     *
     * @param treatment treatment
     */
    boolean updateExpired(Treatment treatment);

    Treatment selectTreatmentById(int id);

    /**
     * 更新诊疗状态变更
     *
     * @param treatment treatment
     * @param info info
     */
    void updateStatusChange(Treatment treatment, TreatmentAppointmentInfo info);

    /**
     * 获取正在直播中的互动房间内的用户列表
     *
     * @param  inavId
     */
    String inavUserList(String inavId) throws Exception;
}
