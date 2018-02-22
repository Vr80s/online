package com.xczhihui.medical.anchor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.anchor.mapper.UserCoinIncreaseMapper;
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

    /**
     * 获取用户所有收到过礼物的课程
     * @param userId 用户id
     */
    @Override
    public Page<UserCoinIncreaseVO> list(String userId, Page<UserCoinIncreaseVO> page) {

        // 获取礼物订单的课程名称，直播时间
        List<UserCoinIncreaseVO> userCoinIncreaseVOList = userCoinIncreaseMapper.listGiftOrder(userId, page);

        // 根据直播id获取直播的礼物总价,获得总熊猫币
        Optional<List<UserCoinIncreaseVO>> userCoinIncreaseVOListOptional =
                Optional.ofNullable(userCoinIncreaseVOList);

        userCoinIncreaseVOListOptional.ifPresent(userCoinIncreaseVOs ->
                userCoinIncreaseVOs.stream()
                        .filter(vo -> StringUtils.isNotBlank(vo.getLiveId()))
                        .forEach(vo -> this.processUserCoinIncreaseVOList(vo))
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

    private void processUserCoinIncreaseVOList(UserCoinIncreaseVO vo){

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

        vo.setPercent("80%");
    }
}
