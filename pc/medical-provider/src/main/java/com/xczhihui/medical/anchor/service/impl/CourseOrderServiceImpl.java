package com.xczhihui.medical.anchor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.anchor.enums.CourseTypeEnum;
import com.xczhihui.medical.anchor.mapper.CourseAnchorMapper;
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
import java.util.Optional;

@Service("courseOrderServiceImpl")
public class CourseOrderServiceImpl implements ICourseOrderService {

    @Autowired
    private UserCoinIncreaseMapper userCoinIncreaseMapper;
    @Autowired
    private CourseAnchorMapper anchorMapper;

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
                if(StringUtils.isNotBlank(endTime)){
                    end = LocalDateTime.parse(endTime, dateTimeFormatter);
                    if(start.isAfter(end)){
                        throw new RuntimeException("起始时间不应大于终止时间");
                    }
                }
            }

            if(courseForm != null && courseForm == 2){
                if(multimediaType == null){
                    throw new RuntimeException("请选择多媒体类型");
                }
            }else{
                multimediaType = null;
            }

            // 获取主播的信息：分成比例
            CourseAnchor anchor = new CourseAnchor();
            anchor.setUserId(userId);
            CourseAnchor target = anchorMapper.selectOne(anchor);

            // 获取课程订单的订单号，课程名称，支付时间，支付用户，实际支付的价格
            List<UserCoinIncreaseVO> userCoinIncreaseVOList = userCoinIncreaseMapper.listCourseOrder(userId, page, gradeName, start, end, courseForm, multimediaType);

            // 根据课程id获取：苹果扣除的总数，分成比例，课程获得总熊猫币
//            Optional<List<UserCoinIncreaseVO>> userCoinIncreaseVOListOptional = Optional.ofNullable(userCoinIncreaseVOList);
//            userCoinIncreaseVOListOptional.ifPresent(userCoinIncreaseVOs ->
//                    userCoinIncreaseVOs.stream()
//                            .filter(vo -> StringUtils.isNotBlank(vo.getCourseId()))
//                            .forEach(vo -> this.processUserCoinIncreaseVOList(vo, target))
//            );

            page.setRecords(userCoinIncreaseVOList);

            return page;

        }catch (DateTimeParseException e){

            throw new RuntimeException("时间格式错误");

        }
    }

    private void processUserCoinIncreaseVOList(UserCoinIncreaseVO vo, CourseAnchor target){

        // 苹果扣除的总数
        vo.setIosBrokerageValue(userCoinIncreaseMapper.sumIosBrokerageValueByCourseId(vo.getCourseId()));

        // 课程获得总熊猫币
//        vo.setValue(userCoinIncreaseMapper.sumValueByCourseByUciId(vo.getId()));
        vo.setValue(userCoinIncreaseMapper.sumValueByCourse(vo.getCourseId()));

        // 根据课程类型获取分成比例
//        if(vo.getType().equals(CourseTypeEnum.LIVE.getCode())){
//            vo.setPercent(target.getLiveDivide().toString().substring(0,2) + "%");
//        }
//        if(vo.getType().equals(CourseTypeEnum.VOD.getCode())){
//            vo.setPercent(target.getVodDivide().toString().substring(0,2) + "%");
//        }
//        if(vo.getType().equals(CourseTypeEnum.OFFLINE.getCode())){
//            vo.setPercent(target.getOfflineDivide().toString().substring(0,2) + "%");
//        }
    }

}
