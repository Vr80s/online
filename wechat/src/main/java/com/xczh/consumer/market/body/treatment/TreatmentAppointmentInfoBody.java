package com.xczh.consumer.market.body.treatment;

import java.util.Date;

import com.xczhihui.medical.doctor.model.TreatmentAppointmentInfo;

/**
 * 诊疗
 *
 * @author hejiwei
 */
public class TreatmentAppointmentInfoBody {

    private Integer treatmentId;

    private String question;

    private String tel;

    private String name;

    public TreatmentAppointmentInfo build(String userId) {
        TreatmentAppointmentInfo treatmentAppointmentInfo = new TreatmentAppointmentInfo();
        treatmentAppointmentInfo.setTreatmentId(treatmentId);
        treatmentAppointmentInfo.setCreateTime(new Date());
        treatmentAppointmentInfo.setQuestion(question);
        treatmentAppointmentInfo.setTel(tel);
        treatmentAppointmentInfo.setUserId(userId);
        treatmentAppointmentInfo.setName(name);
        return treatmentAppointmentInfo;
    }

    public Integer getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Integer treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
