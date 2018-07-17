package com.xczhihui.medical.doctor.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.common.util.DateUtil;
import com.xczhihui.common.util.enums.AppointmentStatus;
import com.xczhihui.medical.doctor.mapper.RemoteTreatmentAppointmentInfoMapper;
import com.xczhihui.medical.doctor.mapper.RemoteTreatmentMapper;
import com.xczhihui.medical.doctor.model.Treatment;
import com.xczhihui.medical.doctor.model.TreatmentAppointmentInfo;
import com.xczhihui.medical.doctor.service.IRemoteTreatmentService;
import com.xczhihui.medical.doctor.vo.TreatmentVO;
import com.xczhihui.medical.exception.MedicalException;

/**
 * @author hejiwei
 */
@Service
public class RemoteTreatmentServiceImpl implements IRemoteTreatmentService {

    private static final Object LOCK = new Object();

    @Autowired
    private RemoteTreatmentMapper remoteTreatmentMapper;
    @Autowired
    private RemoteTreatmentAppointmentInfoMapper remoteTreatmentAppointmentInfoMapper;

    @Override
    public void save(Treatment treatment) {
        synchronized (LOCK) {
            Integer cnt = remoteTreatmentMapper.countRepeatByDate(treatment.getDate(), treatment.getStartTime(), treatment.getEndTime(), null, treatment.getDoctorId());
            if (cnt > 0) {
                throw new MedicalException("预约时间重合, 请重新选择时间");
            }
            remoteTreatmentMapper.insert(treatment);
        }
    }

    @Override
    public void update(Treatment treatment, Integer id) {
        synchronized (LOCK) {
            Treatment waitUpdateTreatment = remoteTreatmentMapper.selectById(id);
            if (waitUpdateTreatment == null || (waitUpdateTreatment.getDeleted() != null && waitUpdateTreatment.getDeleted())) {
                throw new MedicalException("预约时间已被删除");
            }
            if (waitUpdateTreatment.getStatus() != AppointmentStatus.ORIGIN.getVal()) {
                throw new MedicalException("当前状态不可再编辑");
            }
            Integer cnt = remoteTreatmentMapper.countRepeatByDate(treatment.getDate(), treatment.getStartTime(), treatment.getEndTime(), waitUpdateTreatment.getId(), treatment.getDoctorId());
            if (cnt > 0) {
                throw new MedicalException("预约时间重合, 请重新选择时间");
            }
            waitUpdateTreatment.setEndTime(treatment.getEndTime());
            waitUpdateTreatment.setStartTime(treatment.getStartTime());
            waitUpdateTreatment.setDate(treatment.getDate());
            remoteTreatmentMapper.updateById(waitUpdateTreatment);
        }
    }

    @Override
    public void delete(Integer id, String userId) {
        synchronized (LOCK) {
            Treatment waitUpdateTreatment = remoteTreatmentMapper.selectById(id);
            if (waitUpdateTreatment == null || (waitUpdateTreatment.getDeleted() != null && waitUpdateTreatment.getDeleted())) {
                throw new MedicalException("预约时间已被删除");
            }
            if (waitUpdateTreatment.getStatus() == AppointmentStatus.APPOINTMENT_SUCCESS.getVal()) {
                throw new MedicalException("已预约成功, 不能被删除");
            }
            waitUpdateTreatment.setDeleted(true);
            remoteTreatmentMapper.updateById(waitUpdateTreatment);
        }
    }

    @Override
    public int saveAppointmentInfo(TreatmentAppointmentInfo treatmentAppointmentInfo) {
        synchronized (LOCK) {
            Integer treatmentId = treatmentAppointmentInfo.getTreatmentId();
            Treatment treatment = remoteTreatmentMapper.selectById(treatmentId);
            if (treatment == null || treatment.getDeleted()) {
                throw new MedicalException("该诊疗时间段已被删除");
            }
            if (treatment.getStatus() != AppointmentStatus.ORIGIN.getVal()) {
                return 0;
            }
            remoteTreatmentAppointmentInfoMapper.insert(treatmentAppointmentInfo);
            treatment.setInfoId(treatmentAppointmentInfo.getId());
            treatment.setStatus(AppointmentStatus.WAIT_APPLY.getVal());
            remoteTreatmentMapper.updateById(treatment);
        }
        return 1;//throw new MedicalException("");
    }

    @Override
    public List<TreatmentVO> listAppointment(String doctorId, boolean onlyUnAppointment) {
        List<TreatmentVO> treatmentVOS = remoteTreatmentMapper.listByDoctorId(doctorId, onlyUnAppointment);
        SimpleDateFormat yearMonthDayDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat hourMinuteFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat monthDayDateFormat = new SimpleDateFormat("MM月dd日");
        treatmentVOS.forEach(treatmentVO -> {
            Date date = treatmentVO.getDate();
            Date startTime = treatmentVO.getStartTime();
            Date endTime = treatmentVO.getEndTime();
            if (date != null && startTime != null && endTime != null) {
                String startTimeStr = hourMinuteFormat.format(startTime);
                String endTimeStr = hourMinuteFormat.format(endTime);
                treatmentVO.setDateText(yearMonthDayDateFormat.format(date) + " " + startTimeStr + "-" + endTimeStr);
                treatmentVO.setIndexDateText((DateUtil.isCurrentYear(date) ? monthDayDateFormat.format(date) : yearMonthDayDateFormat.format(date)) + " " + DateUtil.getDayOfWeek(date) + " " + startTimeStr + "-" + endTimeStr);
            }
            treatmentVO.setAppointed(treatmentVO.getStatus() != AppointmentStatus.ORIGIN.getVal());
        });
        return treatmentVOS;
    }
}
