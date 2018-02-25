package com.xczhihui.medical.anchor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.xczhihui.medical.anchor.enums.CourseTypeEnum;
import com.xczhihui.medical.anchor.mapper.CourseAnchorMapper;
import com.xczhihui.medical.anchor.mapper.UserCoinIncreaseMapper;
import com.xczhihui.medical.anchor.model.CourseAnchor;
import com.xczhihui.medical.anchor.service.IGiftOrderService;
import com.xczhihui.medical.anchor.vo.UserCoinIncreaseVO;
import com.xczhihui.medical.enums.MedicalExceptionEnum;
import com.xczhihui.medical.exception.MedicalException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("giftOrderServiceImpl")
public class GiftOrderServiceImpl implements IGiftOrderService {

    @Autowired
    private UserCoinIncreaseMapper userCoinIncreaseMapper;
    @Autowired
    private CourseAnchorMapper anchorMapper;

    /**
     * 获取用户所有收到过礼物的课程
     * @param userId 用户id
     */
    @Override
    public Page<UserCoinIncreaseVO> list(String userId, Page<UserCoinIncreaseVO> page,
                                         String gradeName, String startTime, String endTime) {

        try {

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime start = null, end = null;

            if(StringUtils.isNotBlank(startTime)){
                start = LocalDateTime.parse(startTime, dateTimeFormatter);
                if(StringUtils.isNotBlank(endTime)){
                    end = LocalDateTime.parse(endTime, dateTimeFormatter);
                    if(end.isBefore(start)){
                        throw new MedicalException(MedicalExceptionEnum.DATE_START_IS_AFTER_END);
                    }
                }
            }

            // 获取主播的信息
            CourseAnchor courseAnchor = new CourseAnchor();
            courseAnchor.setUserId(userId);
            CourseAnchor anchor = anchorMapper.selectOne(courseAnchor);

            // 获取礼物订单的课程名称，直播时间
            List<UserCoinIncreaseVO> userCoinIncreaseVOList = userCoinIncreaseMapper.listGiftOrder(userId, page, gradeName, start, end);

            // 根据直播id获取直播的礼物总价,总熊猫币，平台扣除
            Optional<List<UserCoinIncreaseVO>> userCoinIncreaseVOListOptional =
                    Optional.ofNullable(userCoinIncreaseVOList);

            List<UserCoinIncreaseVO> result = userCoinIncreaseVOListOptional.map(option ->
                    option.stream()
                            .filter(vo -> StringUtils.isNotBlank(vo.getLiveId()))
                            .map(vo -> this.processUserCoinIncreaseVOList(vo, anchor))
                            .collect(Collectors.toList())
            ).orElse(null);

            return page.setRecords(result);

        }catch (DateTimeParseException e){

            throw new MedicalException(MedicalExceptionEnum.DATE_FORMAT_WRONG);

        }
    }

    /**
     * 礼物订单排行榜
     * @param userId 用户id
     */
    @Override
    public Page<UserCoinIncreaseVO> sort(String liveId, String userId, Page<UserCoinIncreaseVO> page) {

        if(StringUtils.isBlank(liveId)){
            throw new MedicalException(MedicalExceptionEnum.COURSE_IS_EMPTY);
        }

        // ranking ：用来显示排序时的数字
        int ranking = (page.getCurrent() - 1) * page.getSize() + 1;

        // 礼物排行榜 总贡献值排序
        List<UserCoinIncreaseVO> result = userCoinIncreaseMapper.rankGiftList(liveId, page);
        if(CollectionUtils.isNotEmpty(result)){
            for (UserCoinIncreaseVO vo: result){

                vo.setRanking(ranking++);

                // 获取平台扣除
                vo.setIosBrokerageValue(userCoinIncreaseMapper.sumGiverIosBrokerageValueByLiveId(vo.getGiver(), userId));

                // 获取熊猫币
                vo.setValue(userCoinIncreaseMapper.sumValue(vo.getGiver(), userId));

                // 获取主播的信息
                CourseAnchor courseAnchor = new CourseAnchor();
                courseAnchor.setUserId(userId);
                CourseAnchor anchor = anchorMapper.selectOne(courseAnchor);

                // 根据课程类型获取分成比例
                if(vo.getType().equals(CourseTypeEnum.LIVE.getCode())){
                    vo.setPercent(anchor.getLiveDivide().toString().substring(0,2) + "%");
                }
                if(vo.getType().equals(CourseTypeEnum.VOD.getCode())){
                    vo.setPercent(anchor.getVodDivide().toString().substring(0,2) + "%");
                }
                if(vo.getType().equals(CourseTypeEnum.OFFLINE.getCode())){
                    vo.setPercent(anchor.getOfflineDivide().toString().substring(0,2) + "%");
                }
            }
        }

        return page.setRecords(result);

    }

    private UserCoinIncreaseVO processUserCoinIncreaseVOList(UserCoinIncreaseVO vo, CourseAnchor anchor){

        // 获取直播的礼物总价
        vo.setGiftTotalPrice(userCoinIncreaseMapper.sumGiftTotalPriceByLiveId(vo.getLiveId()));

        // 获得总熊猫币
        vo.setValue(userCoinIncreaseMapper.sumValueByGift(vo.getLiveId()));

        // 平台扣除
        vo.setIosBrokerageValue(userCoinIncreaseMapper.sumIosBrokerageValueByLiveId(vo.getLiveId()));

        // 根据课程类型获取分成比例
        if(vo.getType().equals(CourseTypeEnum.LIVE.getCode())){
            vo.setPercent(anchor.getLiveDivide().toString().substring(0,2) + "%");
        }
        if(vo.getType().equals(CourseTypeEnum.VOD.getCode())){
            vo.setPercent(anchor.getVodDivide().toString().substring(0,2) + "%");
        }
        if(vo.getType().equals(CourseTypeEnum.OFFLINE.getCode())){
            vo.setPercent(anchor.getOfflineDivide().toString().substring(0,2) + "%");
        }

        return vo;

    }
}
