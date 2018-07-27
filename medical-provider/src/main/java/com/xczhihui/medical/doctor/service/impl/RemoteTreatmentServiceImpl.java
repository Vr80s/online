package com.xczhihui.medical.doctor.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.DateUtil;
import com.xczhihui.common.util.SmsUtil;
import com.xczhihui.common.util.enums.AppointmentStatus;
import com.xczhihui.common.util.enums.IndexAppointmentStatus;
import com.xczhihui.medical.doctor.mapper.RemoteTreatmentAppointmentInfoMapper;
import com.xczhihui.medical.doctor.mapper.RemoteTreatmentMapper;
import com.xczhihui.medical.doctor.model.Treatment;
import com.xczhihui.medical.doctor.model.TreatmentAppointmentInfo;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.service.IRemoteTreatmentService;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.doctor.vo.TreatmentVO;
import com.xczhihui.medical.enrol.mapper.MedicalEntryInformationMapper;
import com.xczhihui.medical.exception.MedicalException;

/**
 * @author hejiwei
 */
@Service
public class RemoteTreatmentServiceImpl implements IRemoteTreatmentService {

    private static final Object LOCK = new Object();

    @Value("${online.treatment.apply.success.sms.code}")
    private String treatmentApplySuccessCode;
    @Value("${online.treatment.apply.fail.sms.code}")
    private String treatmentApplyFailCode;
    @Value("${online.treatment.apply.cancel.sms.code}")
    private String treatmentApplyCancelCode;

    @Autowired
    private RemoteTreatmentMapper remoteTreatmentMapper;
    @Autowired
    private RemoteTreatmentAppointmentInfoMapper remoteTreatmentAppointmentInfoMapper;
    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;
    @Autowired
    private MedicalEntryInformationMapper medicalEntryInformationMapper;

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
//            if (checkRepeatAppoint(treatmentId, treatmentAppointmentInfo.getUserId())) {
//                throw new MedicalException("该日期您已经有预约申请，请选择其他日期进行申请");
//            }
            remoteTreatmentAppointmentInfoMapper.insert(treatmentAppointmentInfo);
            treatment.setInfoId(treatmentAppointmentInfo.getId());
            treatment.setStatus(AppointmentStatus.WAIT_APPLY.getVal());
            remoteTreatmentMapper.updateById(treatment);
        }
        return 1;
    }

    @Override
    public List<TreatmentVO> listAppointment(String doctorId, boolean onlyUnAppointment, String accountId) {
        List<TreatmentVO> treatmentVOS = remoteTreatmentMapper.listByDoctorId(doctorId, onlyUnAppointment);
        SimpleDateFormat yearMonthDayDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat hourMinuteFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat monthDayDateFormat = new SimpleDateFormat("MM月dd日");
        treatmentVOS.forEach(treatmentVO -> {
            handleDate(treatmentVO);
            Integer status = treatmentVO.getStatus();
            //已预约
            if (status != AppointmentStatus.ORIGIN.getVal()) {
                if (accountId == null || !treatmentVO.getUserId().equals(accountId)) {
                    treatmentVO.setAppointStatus(IndexAppointmentStatus.FULL.getVal());
                } else {
                    treatmentVO.setAppointStatus(IndexAppointmentStatus.MY_APPOINT.getVal());
                }
            } else {
                treatmentVO.setAppointStatus(IndexAppointmentStatus.ORIGIN.getVal());
            }
            treatmentVO.setAppointed(status != AppointmentStatus.ORIGIN.getVal());
        });
        return treatmentVOS;
    }

    @Override
    public void updateStatus(Integer id, boolean status) {
        synchronized (LOCK) {
            Treatment treatment = remoteTreatmentMapper.selectById(id);
            if (treatment == null || (treatment.getDeleted() != null && treatment.getDeleted())) {
                throw new MedicalException("预约时间已被删除");
            }
            Integer infoId = treatment.getInfoId();
            if (treatment.getStatus() != AppointmentStatus.WAIT_APPLY.getVal()) {
                throw new MedicalException("当前状态不支持审核");
            }
            if (status) {
                treatment.setStatus(AppointmentStatus.APPOINTMENT_SUCCESS.getVal());
            } else {
                treatment.setStatus(AppointmentStatus.ORIGIN.getVal());
                treatment.setInfoId(null);
            }
            remoteTreatmentMapper.updateAllColumnById(treatment);
            sendSms(treatment, status, infoId);
        }
    }

    public void sendSms(Treatment treatment, boolean status, int infoId) {
        SimpleDateFormat yearMonthDayFormat = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat hourMinuteFormat = new SimpleDateFormat("HH时mm分");
        TreatmentAppointmentInfo treatmentAppointmentInfo = remoteTreatmentAppointmentInfoMapper.selectById(infoId);
        MedicalDoctorVO medicalDoctorVO = medicalDoctorBusinessService.findSimpleById(treatment.getDoctorId());
        Map<String, String> smsParams = new HashMap<>(4);
        String smsCode;
        if (status) {
            smsParams.put("name1", medicalDoctorVO.getName());
            smsParams.put("name2", medicalDoctorVO.getName());
            smsParams.put("startTime", yearMonthDayFormat.format(treatment.getDate()) + hourMinuteFormat.format(treatment.getStartTime()));
            smsParams.put("endTime", hourMinuteFormat.format(treatment.getEndTime()));
            smsCode = treatmentApplySuccessCode;
        } else {
            smsParams.put("name", medicalDoctorVO.getName());
            smsParams.put("startTime", yearMonthDayFormat.format(treatment.getDate()) + hourMinuteFormat.format(treatment.getStartTime()));
            smsParams.put("endTime", hourMinuteFormat.format(treatment.getEndTime()));
            smsCode = treatmentApplyFailCode;
        }
        SmsUtil.sendSMS(smsCode, smsParams, treatmentAppointmentInfo.getTel());
    }

    @Override
    public void updateAppointmentForCancel(Integer id) {
        synchronized (LOCK) {
            Treatment treatment = remoteTreatmentMapper.selectById(id);
            if (treatment == null || (treatment.getDeleted() != null && treatment.getDeleted())) {
                throw new MedicalException("预约时间已被删除");
            }
            if (treatment.getStatus() != AppointmentStatus.APPOINTMENT_SUCCESS.getVal()) {
                throw new MedicalException("当前状态不支持取消");
            }
            Integer infoId = treatment.getInfoId();
            treatment.setStatus(AppointmentStatus.ORIGIN.getVal());
            treatment.setInfoId(null);
            remoteTreatmentMapper.updateAllColumnById(treatment);
            sendAppointmentCancelSms(infoId, treatment);
        }
    }

    private void sendAppointmentCancelSms(Integer infoId, Treatment treatment) {
        SimpleDateFormat yearMonthDayFormat = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat hourMinuteFormat = new SimpleDateFormat("HH时mm分");
        Map<String, String> smsParams = new HashMap<>(3);
        TreatmentAppointmentInfo treatmentAppointmentInfo = remoteTreatmentAppointmentInfoMapper.selectById(infoId);
        if (treatmentAppointmentInfo != null) {
            MedicalDoctorVO medicalDoctorVO = medicalDoctorBusinessService.findSimpleById(treatment.getDoctorId());
            if (medicalDoctorVO != null) {
                smsParams.put("name", medicalDoctorVO.getName());
                smsParams.put("startTime", yearMonthDayFormat.format(treatment.getDate()) + hourMinuteFormat.format(treatment.getStartTime()));
                smsParams.put("endTime", hourMinuteFormat.format(treatment.getEndTime()));
                SmsUtil.sendSMS(treatmentApplyCancelCode, smsParams, treatmentAppointmentInfo.getTel());
            }
        }
    }

    @Override
    public Page<TreatmentVO> list(String doctorId, int page, int size) {
        Page<TreatmentVO> treatmentVOPage = new Page<>(page, size);
        List<TreatmentVO> treatmentVOS = remoteTreatmentMapper.listPageByDoctorId(doctorId, treatmentVOPage);
        treatmentVOS.forEach(treatmentVO -> treatmentVO.setWeek(DateUtil.getDayOfWeek(treatmentVO.getDate())));
        return treatmentVOPage.setRecords(treatmentVOS);
    }

    @Override
    public TreatmentVO getInfo(int id) {
        TreatmentVO treatmentVO = remoteTreatmentMapper.findByInfoId(id);
        handleDate(treatmentVO);
        return treatmentVO;
    }

    @Override
    public boolean checkRepeatAppoint(int id, String accountId) {
        Treatment treatment = remoteTreatmentMapper.selectById(id);
        if (treatment == null) {
            throw new MedicalException("数据不存在");
        }
        Date startTime = treatment.getStartTime();
        Date endTime = treatment.getEndTime();
        Date date = treatment.getDate();
        Integer cnt = remoteTreatmentMapper.countUserAppointRepeatByDate(date, startTime, endTime, accountId);
        return cnt != null && cnt > 0;
    }

    private void handleDate(TreatmentVO treatmentVO) {
        SimpleDateFormat yearMonthDayDateFormat = new SimpleDateFormat("yyyy年M月dd日");
        SimpleDateFormat hourMinuteFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat monthDayDateFormat = new SimpleDateFormat("M月dd日");
        Date date = treatmentVO.getDate();
        Date startTime = treatmentVO.getStartTime();
        Date endTime = treatmentVO.getEndTime();
        if (date != null && startTime != null && endTime != null) {
            String startTimeStr = hourMinuteFormat.format(startTime);
            String endTimeStr = hourMinuteFormat.format(endTime);
            treatmentVO.setDateText(yearMonthDayDateFormat.format(date) + " " + startTimeStr + "-" + endTimeStr);
            treatmentVO.setIndexDateText((DateUtil.isCurrentYear(date) ? monthDayDateFormat.format(date) : yearMonthDayDateFormat.format(date))
                    + " " + DateUtil.getDayOfWeek(date) + " " + startTimeStr + "-" + endTimeStr);
        }
    }
}
