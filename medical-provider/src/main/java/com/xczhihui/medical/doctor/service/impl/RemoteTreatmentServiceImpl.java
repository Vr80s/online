package com.xczhihui.medical.doctor.service.impl;

import static com.xczhihui.common.util.enums.TreatmentInfoApplyStatus.APPLY_PASSED;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.util.DateUtil;
import com.xczhihui.common.util.SmsUtil;
import com.xczhihui.common.util.enums.AppointmentStatus;
import com.xczhihui.common.util.enums.IndexAppointmentStatus;
import com.xczhihui.common.util.enums.TreatmentInfoApplyStatus;
import com.xczhihui.common.util.redis.key.RedisCacheKey;
import com.xczhihui.medical.anchor.mapper.CourseApplyInfoMapper;
import com.xczhihui.medical.doctor.mapper.RemoteTreatmentAppointmentInfoMapper;
import com.xczhihui.medical.doctor.mapper.RemoteTreatmentMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
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

    private static final int START_TREATMENT_MINUTE = 10;
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
    @Autowired
    private CacheService cacheService;
    @Autowired
    private CourseApplyInfoMapper courseApplyInfoMapper;


    @Override
    public void save(Treatment treatment) {
        synchronized (LOCK) {
            Integer cnt = remoteTreatmentMapper.countRepeatByDate(treatment.getDate(), treatment.getStartTime(), treatment.getEndTime(), null, treatment.getDoctorId());
            if (cnt > 0) {
                throw new MedicalException("预约时间重合, 请重新选择时间");
            }
            treatment.setTreatmentStartTime(getTreatmentTime(treatment.getDate(), treatment.getStartTime()));
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
            waitUpdateTreatment.setTreatmentStartTime(getTreatmentTime(treatment.getDate(), treatment.getStartTime()));
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
            int status = waitUpdateTreatment.getStatus();
            if (status != AppointmentStatus.ORIGIN.getVal() && status != AppointmentStatus.WAIT_APPLY.getVal() && status != AppointmentStatus.EXPIRED.getVal()) {
                throw new MedicalException("该状态下不能被删除");
            }
            waitUpdateTreatment.setDeleted(true);
            remoteTreatmentMapper.updateById(waitUpdateTreatment);
            Integer infoId = waitUpdateTreatment.getInfoId();
            if (infoId != null) {
                TreatmentAppointmentInfo treatmentAppointmentInfo = remoteTreatmentAppointmentInfoMapper.selectById(infoId);
                if (treatmentAppointmentInfo != null) {
                    treatmentAppointmentInfo.setStatus(TreatmentInfoApplyStatus.APPLY_NOT_PASSED.getVal());
                    remoteTreatmentAppointmentInfoMapper.updateById(treatmentAppointmentInfo);
                }
            }
            Integer courseId = waitUpdateTreatment.getCourseId();
            if (courseId != null) {
                updateCourseStatus(courseId, 0);
            }
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
            MedicalDoctorAccount medicalDoctorAccount = medicalDoctorBusinessService.getByDoctorId(treatment.getDoctorId());
            if (medicalDoctorAccount != null && medicalDoctorAccount.getAccountId().equals(treatmentAppointmentInfo.getUserId())) {
                throw new MedicalException("抱歉，您不可以预约自己的诊疗");
            }
            treatmentAppointmentInfo.setDate(treatment.getDate());
            treatmentAppointmentInfo.setStartTime(treatment.getStartTime());
            treatmentAppointmentInfo.setEndTime(treatment.getEndTime());
            treatmentAppointmentInfo.setStatus(TreatmentInfoApplyStatus.WAIT_DOCTOR_APPLY.getVal());
            treatmentAppointmentInfo.setTreatmentStartTime(getTreatmentTime(treatment.getDate(), treatment.getStartTime()));
            remoteTreatmentAppointmentInfoMapper.insert(treatmentAppointmentInfo);
            treatment.setInfoId(treatmentAppointmentInfo.getId());
            treatment.setStatus(AppointmentStatus.WAIT_APPLY.getVal());
            updateStatusChange(treatment, treatmentAppointmentInfo);
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
            TreatmentAppointmentInfo treatmentAppointmentInfo = remoteTreatmentAppointmentInfoMapper.selectById(infoId);
            if (status) {
                treatmentAppointmentInfo.setStatus(APPLY_PASSED.getVal());
                treatment.setStatus(AppointmentStatus.WAIT_START.getVal());
            } else {
                treatmentAppointmentInfo.setStatus(TreatmentInfoApplyStatus.APPLY_NOT_PASSED.getVal());
                treatment.setStatus(AppointmentStatus.ORIGIN.getVal());
                treatment.setInfoId(null);
            }
            remoteTreatmentMapper.updateAllColumnById(treatment);
            remoteTreatmentAppointmentInfoMapper.updateById(treatmentAppointmentInfo);
            updateStatusChange(treatment, treatmentAppointmentInfo);

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
            if (treatment.getStatus() != AppointmentStatus.WAIT_START.getVal()) {
                throw new MedicalException("当前状态不支持取消");
            }
            updateCourseStatus(treatment.getCourseId(), 0);

            Integer infoId = treatment.getInfoId();
            TreatmentAppointmentInfo treatmentAppointmentInfo = remoteTreatmentAppointmentInfoMapper.selectById(infoId);
            //更新为用户的预约信息为未通过
            treatmentAppointmentInfo.setStatus(TreatmentInfoApplyStatus.APPLY_NOT_PASSED.getVal());
            remoteTreatmentAppointmentInfoMapper.updateById(treatmentAppointmentInfo);
            updateStatusChange(treatment, treatmentAppointmentInfo);
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
        //是否开启开始远程诊疗点击状态
        SimpleDateFormat yearMonthDayDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hourMinuteFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTimeStr = hourMinuteFormat.format(treatmentVO.getStartTime());
        String s = yearMonthDayDateFormat.format(treatmentVO.getDate()) + " " + startTimeStr;
        try {
            Date startDate = dateFormat.parse(s);
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.MINUTE, 10);//10分钟后的时间
            Date newDate = nowTime.getTime();
            
//            if (startDate.getTime() <= newDate.getTime() && treatmentVO.getStatus() == 2) {
//                treatmentVO.setStart(true);
//            } else {
//                treatmentVO.setStart(false);
//            }
            
            
          if (treatmentVO.getStatus() == 2) {
	          treatmentVO.setStart(true);
	      } else {
	          treatmentVO.setStart(false);
	      }
            
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    @Override
    public int checkAppointment(int id, String accountId) {
        Treatment treatment = remoteTreatmentMapper.selectById(id);
        if (treatment == null) {
            throw new MedicalException("参数错误");
        }
        if (treatment.getStatus() != AppointmentStatus.ORIGIN.getVal()) {
            return 1;
        }
        if (checkRepeatAppoint(id, accountId)) {
            return 2;
        }
        return 0;
    }

    @Override
    public void deleteAppointmentInfo(int infoId) {
        TreatmentAppointmentInfo treatmentAppointmentInfo = remoteTreatmentAppointmentInfoMapper.selectById(infoId);
        if (treatmentAppointmentInfo == null) {
            throw new MedicalException("预约id参数错误");
        }
        if (treatmentAppointmentInfo.getStatus() != TreatmentInfoApplyStatus.APPLY_NOT_PASSED.getVal() && treatmentAppointmentInfo.getStatus() != TreatmentInfoApplyStatus.EXPIRED.getVal()) {
            throw new MedicalException("仅审核不通过与已过期的数据可删除");
        }
        treatmentAppointmentInfo.setDeleted(true);
        remoteTreatmentAppointmentInfoMapper.updateById(treatmentAppointmentInfo);
    }

    @Override
    public Map<String, Object> updateTreatmentStartStatus(int infoId, int status) {
        Map<String, Object> result = new HashMap<>();
        synchronized (LOCK) {
            TreatmentAppointmentInfo treatmentAppointmentInfo = remoteTreatmentAppointmentInfoMapper.selectById(infoId);
            if (treatmentAppointmentInfo == null) {
                throw new MedicalException("对应infoId数据找不到");
            }
            Treatment treatment = remoteTreatmentMapper.selectById(treatmentAppointmentInfo.getTreatmentId());
            if (treatment == null || (treatment.getDeleted() != null && treatment.getDeleted())) {
                throw new MedicalException("诊疗id参数错误");
            }
            Integer treatmentStatus = treatment.getStatus();
            if (treatmentStatus == AppointmentStatus.EXPIRED.getVal()) {
                throw new MedicalException("该诊疗直播已过期");
            }
            if (treatmentStatus == AppointmentStatus.FINISHED.getVal()) {
                throw new MedicalException("该诊疗直播已结束");
            }
            if (status != AppointmentStatus.STARTED.getVal() && status != AppointmentStatus.FINISHED.getVal()) {
                throw new MedicalException("status 参数错误");
            }
            //开始诊疗
            if (treatmentStatus == AppointmentStatus.WAIT_START.getVal() && status == AppointmentStatus.STARTED.getVal()) {
                treatment.setStatus(status);
                updateStatusChange(treatment, treatmentAppointmentInfo);
                //继续诊疗
            } else if (treatmentStatus == AppointmentStatus.STARTED.getVal() && status == AppointmentStatus.STARTED.getVal()) {
                //结束诊疗
            } else if (treatmentStatus == AppointmentStatus.STARTED.getVal() && status == AppointmentStatus.FINISHED.getVal()) {
                treatmentAppointmentInfo.setStatus(TreatmentInfoApplyStatus.FINISHED.getVal());
                treatment.setStatus(status);
                updateStatusChange(treatment, treatmentAppointmentInfo);
            } else {
                throw new MedicalException("参数错误");
            }
            remoteTreatmentMapper.updateById(treatment);
            remoteTreatmentAppointmentInfoMapper.updateById(treatmentAppointmentInfo);
            return result;
        }
    }

    @Override
    public List<TreatmentVO> listByDoctorId(String doctorId, int page, int size) {
        List<TreatmentVO> treatmentVOS = remoteTreatmentMapper.selectTreatmentByDoctorId(doctorId, new Page<>(page, size));
        treatmentVOS.forEach(treatmentVO -> {
            treatmentVO.setTreatmentTime(getTreatmentTime(treatmentVO.getDate(), treatmentVO.getStartTime()));
            handleDate(treatmentVO);
        });
        if (page == 1) {
            cacheService.delete(RedisCacheKey.DOCTOR_TREATMENT_STATUS_CNT_KEY + doctorId);
        }
        return treatmentVOS;
    }

    @Override
    public List<TreatmentVO> listByUserId(String userId, int page, int size) {
        List<TreatmentVO> treatmentVOS = remoteTreatmentMapper.selectTreatmentByUserId(userId, new Page<>(page, size));
        treatmentVOS.forEach(treatmentVO -> {
            treatmentVO.setTreatmentTime(getTreatmentTime(treatmentVO.getDate(), treatmentVO.getStartTime()));
            handleDate(treatmentVO);
        });
        if (page == 1) {
            cacheService.delete(RedisCacheKey.USER_TREATMENT_STATUS_CNT_KEY + userId);
        }
        return treatmentVOS;
    }

    @Override
    public List<Treatment> selectUpcomingExpire() {
        return remoteTreatmentMapper.selectUpcomingExpire();
    }

    @Override
    public void updateTreatmentCourseId(int id, int courseId) {
        Treatment treatment = remoteTreatmentMapper.selectById(id);
        if (treatment != null) {
            treatment.setCourseId(courseId);
        }
        remoteTreatmentMapper.updateById(treatment);
    }

    private void handleDate(TreatmentVO treatmentVO) {
        SimpleDateFormat yearMonthDayDateFormat = new SimpleDateFormat("yyyy年M月d日");
        SimpleDateFormat hourMinuteFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat monthDayDateFormat = new SimpleDateFormat("M月d日");
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

    @Override
    public TreatmentAppointmentInfo selectById(int id) {
        return remoteTreatmentAppointmentInfoMapper.selectById(id);
    }

    private Date getTreatmentTime(Date date, Date startTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    private boolean isCanStartLive(Date treatmentTime) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        calendar.setTime(treatmentTime);
        if (calendar.get(Calendar.YEAR) == year) {
            if (calendar.get(Calendar.DAY_OF_YEAR) == dayOfYear) {
                if (calendar.get(Calendar.HOUR_OF_DAY) == hour) {
                    int appointmentMinute = calendar.get(Calendar.MINUTE);
                    if (appointmentMinute - minute <= START_TREATMENT_MINUTE) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isExpired(Date date, Date endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);
        int endTimeHour = calendar.get(Calendar.HOUR_OF_DAY);
        int endTimeMinute = calendar.get(Calendar.MINUTE);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, endTimeHour);
        calendar.set(Calendar.MINUTE, endTimeMinute);

        Date treatmentEndTime = calendar.getTime();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, -30);
        return calendar.getTime().after(treatmentEndTime);
    }

    @Override
    public boolean updateExpired(Treatment treatment) {
        if (isExpired(treatment.getDate(), treatment.getEndTime())) {
            treatment.setStatus(AppointmentStatus.EXPIRED.getVal());
            remoteTreatmentMapper.updateById(treatment);
            Integer infoId = treatment.getInfoId();
            TreatmentAppointmentInfo treatmentAppointmentInfo = null;
            if (infoId != null) {
                treatmentAppointmentInfo = remoteTreatmentAppointmentInfoMapper.selectById(infoId);
                remoteTreatmentAppointmentInfoMapper.updateRemoteTreatmentAppointmentInfoExpired(infoId);
            }
            updateStatusChange(treatment, treatmentAppointmentInfo);
            return true;
        }
        return false;
    }

    @Override
    public Treatment selectTreatmentById(int id) {
        return remoteTreatmentMapper.selectById(id);
    }

    @Override
    public void updateStatusChange(Treatment treatment, TreatmentAppointmentInfo info) {
        if (info != null) {
            cacheService.sadd(RedisCacheKey.USER_TREATMENT_STATUS_CNT_KEY + info.getUserId(), String.valueOf(info.getId()));
        }
        if (treatment != null) {
            cacheService.sadd(RedisCacheKey.DOCTOR_TREATMENT_STATUS_CNT_KEY + treatment.getDoctorId(), String.valueOf(treatment.getId()));
        }
    }

    @Override
    public void updateCourseStatus(int id, int status) {
        if (status == 0) {
            //取消远程诊疗后，禁用这个课程
            remoteTreatmentMapper.updateCourseStatusForDisable(id);
            /*
             * 取消远程诊疗后，逻辑删除课程审核信息表中的数据
             */
            courseApplyInfoMapper.deleteCourseApplyByCouserId(id);
            deleteMessage(id);
        }
    }

    @Override
    public void updateTreatmentStartTime() {
        List<Treatment> treatments = remoteTreatmentMapper.listAll();
        List<TreatmentAppointmentInfo> treatmentAppointmentInfos = remoteTreatmentMapper.listAllAppointmentInfo();
        for (Treatment treatment : treatments) {
            treatment.setTreatmentStartTime(getTreatmentTime(treatment.getDate(), treatment.getStartTime()));
            remoteTreatmentMapper.updateById(treatment);
        }
        for (TreatmentAppointmentInfo treatmentAppointmentInfo : treatmentAppointmentInfos) {
            treatmentAppointmentInfo.setTreatmentStartTime(getTreatmentTime(treatmentAppointmentInfo.getDate(), treatmentAppointmentInfo.getStartTime()));
            remoteTreatmentAppointmentInfoMapper.updateById(treatmentAppointmentInfo);
        }
    }

    private void deleteMessage(int courseId) {
        cacheService.delete(RedisCacheKey.OFFLINE_COURSE_REMIND_KEY + RedisCacheKey.REDIS_SPLIT_CHAR + courseId);
        cacheService.delete(RedisCacheKey.LIVE_COURSE_REMIND_KEY + RedisCacheKey.REDIS_SPLIT_CHAR + courseId);
        cacheService.delete(RedisCacheKey.COLLECTION_COURSE_REMIND_KEY + RedisCacheKey.REDIS_SPLIT_CHAR + courseId);
    }
}
