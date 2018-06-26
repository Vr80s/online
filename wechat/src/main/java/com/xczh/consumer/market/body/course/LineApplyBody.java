package com.xczh.consumer.market.body.course;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.xczhihui.common.util.XzStringUtils;
import com.xczhihui.common.util.enums.UserSex;
import com.xczhihui.course.exception.LineApplyException;
import com.xczhihui.course.model.LineApply;

/**
 * @author hejiwei
 */
public class LineApplyBody {

    private String realName;

    private Integer sex;

    private String mobile;

    private String wechatNo;

    private Integer courseId;

    public LineApply build(String accountId) {
        LineApply lineApply = new LineApply();
        lineApply.setUserId(accountId);
        lineApply.setCourseId(courseId);
        lineApply.setCreateTime(new Date());
        lineApply.setUpdateTime(new Date());
        lineApply.setMobile(mobile);
        lineApply.setDelete(false);
        lineApply.setRealName(realName);
        lineApply.setSex(sex);
        lineApply.setWechatNo(wechatNo);
        lineApply.setLearned(false);
        validate(lineApply);
        return lineApply;
    }

    private void validate(LineApply lineApply) {
        if (StringUtils.isBlank(lineApply.getUserId())) {
            throw new LineApplyException("用户id不可为空");
        }
        String name = lineApply.getRealName();
        //过滤掉可能出现的表情字符
        if (StringUtils.isNotBlank(name)) {
            lineApply.setRealName(name);
        }
        name = lineApply.getRealName();
        if (!StringUtils.isNotBlank(name) || (name.length() > 20)) {
            throw new LineApplyException("昵称最多允许输入20个字符");
        }

        if (!XzStringUtils.checkNickName(name)) {
            throw new LineApplyException("昵称支持中文、字母、数字、'-'、'_'的组合，4-20个字符");
        }
        
        if (!XzStringUtils.checkPhone(lineApply.getMobile())) {
            throw new LineApplyException("请输入正确的手机号");
        }

        if (StringUtils.isBlank(lineApply.getWechatNo())) {
            throw new LineApplyException("微信号不能为空");
        }

        if ((lineApply.getWechatNo().length() > 50)) {
            throw new LineApplyException("微信号长度不能超过50");
        }

        if (lineApply.getSex() == null || !UserSex.isValid(lineApply.getSex())) {
            throw new LineApplyException("性别不合法,0 女  1男   2 未知");
        }
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
