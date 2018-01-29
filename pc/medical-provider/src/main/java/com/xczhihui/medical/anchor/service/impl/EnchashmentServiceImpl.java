package com.xczhihui.medical.anchor.service.impl;

import com.xczhihui.bxg.common.util.BeanUtil;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.common.enums.OrderFrom;
import com.xczhihui.medical.anchor.mapper.CourseApplyInfoMapper;
import com.xczhihui.medical.anchor.model.EnchashmentApplyInfo;
import com.xczhihui.medical.anchor.service.IEnchashmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 *  主播课程相关服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2018-01-19
 */
@Service
public class EnchashmentServiceImpl implements IEnchashmentService {

    @Autowired
    private UserCoinService userCoinService;
    @Autowired
    private CourseApplyInfoMapper courseApplyInfoMapper;

    @Override
    public void saveEnchashmentApplyInfo(String userId, BigDecimal enchashmentSum, int bankCardId, OrderFrom orderFrom){
        EnchashmentApplyInfo enchashmentApplyInfo = new EnchashmentApplyInfo();
        String enchashmentApplyId = BeanUtil.getUUID();
        enchashmentApplyInfo.setId(enchashmentApplyId);
        enchashmentApplyInfo.setUserId(userId);
        enchashmentApplyInfo.setEnchashmentSum(enchashmentSum);
        enchashmentApplyInfo.setBankCardId(bankCardId);
        enchashmentApplyInfo.setOrderFrom(orderFrom.getCode());
        validateEnchashmentApplyInfo(enchashmentApplyInfo);
        //更新用户人民币余额
        userCoinService.updateBalanceForEnchashment(userId,enchashmentSum,orderFrom,enchashmentApplyId);

    }

    /**
     * Description：提现申请校验
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 4:04 2018/1/29 0029
     **/
    private void validateEnchashmentApplyInfo(EnchashmentApplyInfo enchashmentApplyInfo) {
        if(StringUtils.isBlank(enchashmentApplyInfo.getUserId())){
            throw new RuntimeException("用户不可为空");
        }
        if(enchashmentApplyInfo.getEnchashmentSum()==null){
            throw new RuntimeException("提现金额不可为空");
        }else if(enchashmentApplyInfo.getEnchashmentSum().compareTo(BigDecimal.ZERO)!=1){
            throw new RuntimeException("提现金额必须大于0");
        }

    }
}
