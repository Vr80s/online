package com.xczhihui.medical.anchor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.anchor.enums.CourseTypeEnum;
import com.xczhihui.medical.anchor.mapper.CourseAnchorMapper;
import com.xczhihui.medical.anchor.mapper.UserCoinIncreaseMapper;
import com.xczhihui.medical.anchor.model.CourseAnchor;
import com.xczhihui.medical.anchor.service.IGiftOrderService;
import com.xczhihui.medical.anchor.vo.UserCoinIncreaseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Page<UserCoinIncreaseVO> list(String userId, Page<UserCoinIncreaseVO> page) {

        // 获取主播的信息：分成比例
        CourseAnchor anchor = new CourseAnchor();
        anchor.setUserId(userId);
        CourseAnchor target = anchorMapper.selectOne(anchor);

        // 获取礼物订单的课程名称，直播时间
        List<UserCoinIncreaseVO> userCoinIncreaseVOList = userCoinIncreaseMapper.listGiftOrder(userId, page);

        // 根据直播id获取直播的礼物总价,获得总熊猫币
        Optional<List<UserCoinIncreaseVO>> userCoinIncreaseVOListOptional =
                Optional.ofNullable(userCoinIncreaseVOList);

        userCoinIncreaseVOListOptional.ifPresent(userCoinIncreaseVOs ->
                userCoinIncreaseVOs.stream()
                        .filter(vo -> StringUtils.isNotBlank(vo.getLiveId()))
                        .forEach(vo -> this.processUserCoinIncreaseVOList(vo, target))
        );

        return page.setRecords(userCoinIncreaseVOList);
    }

    /**
     * 礼物订单排行榜
     * @param userId 用户id
     */
    @Override
    public List<UserCoinIncreaseVO> sort(String userId) {
        return null;
    }

    private void processUserCoinIncreaseVOList(UserCoinIncreaseVO vo, CourseAnchor target){

        // 获取直播的礼物总价
        vo.setGiftTotalPrice(userCoinIncreaseMapper.sumGiftTotalPrice(vo.getLiveId()));

        // 获得总熊猫币
        vo.setValue(userCoinIncreaseMapper.sumValueByGift(vo.getLiveId()));

        // 获取赠送人昵称
        Optional<String> voOptional = Optional.ofNullable(vo.getGiver());
        vo.setName(voOptional.map(giver -> {
            // 根据giver（userId）获取用户昵称
            return "lalalrrrrr";
        }).orElse("游客"));

        // 根据课程类型获取分成比例
        if(vo.getType().equals(CourseTypeEnum.LIVE.getCode())){
            vo.setPercent(target.getLiveDivide().toString().substring(0,2) + "%");
        }
        if(vo.getType().equals(CourseTypeEnum.VOD.getCode())){
            vo.setPercent(target.getVodDivide().toString().substring(0,2) + "%");
        }
        if(vo.getType().equals(CourseTypeEnum.OFFLINE.getCode())){
            vo.setPercent(target.getOfflineDivide().toString().substring(0,2) + "%");
        }

    }
}
