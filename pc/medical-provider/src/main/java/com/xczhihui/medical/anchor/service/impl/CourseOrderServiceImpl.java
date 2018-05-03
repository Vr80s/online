package com.xczhihui.medical.anchor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.exception.AnchorWorkException;
import com.xczhihui.common.util.enums.CourseForm;
import com.xczhihui.medical.anchor.mapper.UserCoinIncreaseMapper;
import com.xczhihui.medical.anchor.model.CourseAnchor;
import com.xczhihui.medical.anchor.service.ICourseOrderService;
import com.xczhihui.medical.anchor.vo.UserCoinIncreaseVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service("courseOrderServiceImpl")
public class CourseOrderServiceImpl implements ICourseOrderService {

    @Autowired
    private UserCoinIncreaseMapper userCoinIncreaseMapper;

    /**
     * 获取用户的课程订单列表
     * @param userId 用户id
     * @param courseForm 课程类型
     * @param multimediaType 多媒体类型
     * @param gradeName 课程名
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    @Override
    public Page<UserCoinIncreaseVO> list(String userId, Page<UserCoinIncreaseVO> page,
                                         String gradeName, String startTime, String endTime,
                                         Integer courseForm, Integer multimediaType) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime start = null, end = null;

            if(StringUtils.isNotBlank(startTime)){
                start = LocalDateTime.parse(startTime, dateTimeFormatter);
            }
            if(StringUtils.isNotBlank(endTime)){
                end = LocalDateTime.parse(endTime, dateTimeFormatter);
            }
            if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
                if(end.isBefore(start)){
                    throw new AnchorWorkException("起始时间不应大于终止时间");
                }
            }

            if(courseForm != null && courseForm == CourseForm.VOD.getCode()){
                if(multimediaType == null){
                    throw new AnchorWorkException("请选择多媒体类型");
                }
            }else{
                multimediaType = null;
            }

            // 获取主播的信息：分成比例
            CourseAnchor anchor = new CourseAnchor();
            anchor.setUserId(userId);
            String startTimeStr = start==null ? null:start.toString();
            String endTimeStr = end==null ? null:end.toString();
            // 获取课程订单的订单号，课程名称，支付时间，支付用户，实际支付的价格
            List<UserCoinIncreaseVO> userCoinIncreaseVOList = userCoinIncreaseMapper.listCourseOrder(userId, page, gradeName, startTimeStr, endTimeStr, courseForm, multimediaType);

            page.setRecords(userCoinIncreaseVOList);
            return page;
        }catch (DateTimeParseException e){
            throw new AnchorWorkException("时间格式错误");
        }
    }

}
