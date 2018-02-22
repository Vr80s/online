package com.xczhihui.medical.anchor.service.impl;

import com.xczhihui.medical.anchor.mapper.UserCoinIncreaseMapper;
import com.xczhihui.medical.anchor.service.ICourseOrderService;
import com.xczhihui.medical.anchor.vo.UserCoinIncreaseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("courseOrderServiceImpl")
public class CourseOrderServiceImpl implements ICourseOrderService {

    @Autowired
    private UserCoinIncreaseMapper userCoinIncreaseMapper;

    /**
     * 获取用户的课程订单列表
     * @param userId 用户id
     */
    @Override
    public List<UserCoinIncreaseVO> list(String userId) {

        // 获取课程订单的订单号，课程名称，支付时间，支付用户，实际支付的价格
        List<UserCoinIncreaseVO> userCoinIncreaseVOList = userCoinIncreaseMapper.listCourseOrder(userId);

        // 根据课程id获取：苹果扣除的总数，课程获得总熊猫币
        Optional<List<UserCoinIncreaseVO>> userCoinIncreaseVOListOptional = Optional.ofNullable(userCoinIncreaseVOList);
        userCoinIncreaseVOListOptional.ifPresent(userCoinIncreaseVOs ->
                userCoinIncreaseVOs.stream()
                        .filter(vo -> StringUtils.isNotBlank(vo.getCourseId()))
                        .forEach(vo -> this.processUserCoinIncreaseVOList(vo))
        );

        return userCoinIncreaseVOList;
    }

    private void processUserCoinIncreaseVOList(UserCoinIncreaseVO vo){

        // 苹果扣除的总数
        vo.setIosBrokerageValue(userCoinIncreaseMapper.sumIosBrokerageValue(vo.getCourseId()));

        // 课程获得总熊猫币
        vo.setValue(userCoinIncreaseMapper.sumValueByCourse(vo.getCourseId()));

    }

}
