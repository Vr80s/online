package com.xczhihui.bxg.online.web.body.doctor;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xczhihui.common.util.enums.AppointmentStatus;
import com.xczhihui.medical.doctor.model.Treatment;

/**
 * @author hejiwei
 */
public class TreatmentBody {

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    @NotNull
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date startTime;

    @NotNull
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date endTime;

    public Treatment build(String doctorId, String userId) {
        Treatment treatment = new Treatment();
        treatment.setCreatePerson(userId);
        treatment.setCreateTime(new Date());
        treatment.setDate(this.date);
        treatment.setStartTime(startTime);
        treatment.setEndTime(endTime);
        treatment.setDoctorId(doctorId);
        treatment.setDeleted(false);
        treatment.setStatus(AppointmentStatus.ORIGIN.getVal());
        return treatment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
