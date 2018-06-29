package com.xczhihui.bxg.online.web.body.doctor;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xczhihui.medical.doctor.enums.DoctorBannerEnum;
import com.xczhihui.medical.doctor.model.DoctorBanner;

/**
 * @author hejiwei
 */
public class DoctorBannerBody {

    @NotBlank(message = "图片链接")
    private String imgUrl;

    @NotBlank(message = "类型不能为空")
    private int type;

    @NotBlank(message = "链接参数不能为空")
    private String linkParam;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date endTime;

    private boolean status;

    public DoctorBanner build() {
        DoctorBanner doctorBanner = new DoctorBanner();
        doctorBanner.setCreateTime(new Date());
        doctorBanner.setStartTime(this.startTime);
        doctorBanner.setEndTime(this.endTime);
        doctorBanner.setImgUrl(this.imgUrl);
        doctorBanner.setStatus(this.status);
        doctorBanner.setType(this.type);
        doctorBanner.setLinkParam(linkParam);
        doctorBanner.setRouteType(DoctorBannerEnum.getByType(type).getRouteType());
        return doctorBanner;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLinkParam() {
        return linkParam;
    }

    public void setLinkParam(String linkParam) {
        this.linkParam = linkParam;
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
