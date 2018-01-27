package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.util.BeanUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.*;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.api.vo.OrderVo;
import com.xczhihui.bxg.online.api.vo.RechargeRecord;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.enums.BalanceType;
import com.xczhihui.bxg.online.common.enums.ConsumptionChangeType;
import com.xczhihui.bxg.online.common.enums.CourseForm;
import com.xczhihui.bxg.online.common.enums.IncreaseChangeType;
import com.xczhihui.bxg.online.web.dao.CourseDao;
import com.xczhihui.bxg.online.web.dao.RewardDao;
import com.xczhihui.bxg.online.web.dao.UserCoinDao;
import com.xczhihui.bxg.online.web.service.RewardService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: UserCoinServiceImpl.java <br>
 * Description: <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月11日<br>
 */
@Service
public class UserCoinServiceImpl implements UserCoinService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserCoinDao userCoinDao;
    @Autowired
    private CourseDao courseDao;
    @Value("${rate}")
    private int rate;

    @Override
    public Map<String, String> getBalanceByUserId(String userId) {
    	
        UserCoin uc = userCoinDao.getBalanceByUserId(userId);
        if (uc == null) {
            throw new RuntimeException(userId + "--用户账户不存在！");
        }
        BigDecimal balanceTotal = uc.getBalance().add(uc.getBalanceGive()).add(uc.getBalanceRewardGift());
        Map<String, String> balance = new HashMap<String, String>();
        balance.put("balance_reward_gift", uc.getBalanceRewardGift().setScale(0, BigDecimal.ROUND_DOWN).toString());
        balance.put("balance_give", uc.getBalanceGive().setScale(0, BigDecimal.ROUND_DOWN).toString());
        balance.put("balance", uc.getBalance().setScale(0, BigDecimal.ROUND_DOWN).toString());
        balance.put("balanceTotal", balanceTotal.setScale(0, BigDecimal.ROUND_DOWN).toString());
        return balance;
    }

    @Override
    public void updateBalanceForIncrease(UserCoinIncrease uci) {
        StringBuffer sql = new StringBuffer("UPDATE user_coin uc SET");
        if (IncreaseChangeType.GIVE.getCode() == uci.getChangeType()) {
            //若为赠送，则赠送余额增加
            sql.append(" uc.`balance_give`=uc.`balance_give`+" + uci.getValue());
        } else if (IncreaseChangeType.GIFT.getCode() == uci.getChangeType() || IncreaseChangeType.REWARD.getCode() == uci.getChangeType() || IncreaseChangeType.COURSE.getCode() == uci.getChangeType()) {
            //若为礼物打赏或课程分成，则礼物打赏余额增加
            sql.append(" uc.`balance_reward_gift`=uc.`balance_reward_gift`+" + uci.getValue());
        } else if (IncreaseChangeType.RECHARGE.getCode() == uci.getChangeType()){
            //若为充值  则充值余额增加
            sql.append(" uc.`balance`=uc.`balance`+" + uci.getValue());
        } else if (IncreaseChangeType.SETTLEMENT.getCode() == uci.getChangeType()){
            //若为结算  则人民币余额增加
            sql.append(" uc.`rmb`=uc.`rmb`+" + uci.getValue());
        }
        sql.append(", uc.`version`=\"" + BeanUtil.getUUID() + "\"");
        sql.append(", uc.`update_time` = now() ");
        sql.append("where user_id=\"" + uci.getUserId() + "\"");
        int updateCount = userCoinDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql.toString());
        if (updateCount < 1) {
            //若更新失败，则继续回调该方法
            throw new RuntimeException("网络异常,请稍后再试！");
//			updateBalanceForIncrease(uci);
        }

        DetachedCriteria dc = DetachedCriteria.forClass(UserCoin.class);
        dc.add(Restrictions.eq("userId", uci.getUserId()));
        UserCoin uc = userCoinDao.findEntity(dc);

        uci.setUserCoinId(uc.getId());
        uci.setBalance(uc.getBalance());
        uci.setBalanceGive(uc.getBalanceGive());
        uci.setBalanceRewardGift(uc.getBalanceRewardGift());
        uci.setRmb(uc.getRmb());
        uci.setCreateTime(new Date());
        uci.setDeleted(false);
        uci.setStatus(true);
        if (uci.getBrokerageValue() == null) {
            uci.setBrokerageValue(BigDecimal.ZERO);
        }
        userCoinDao.save(uci);
    }

    @Override
    public  UserCoinConsumption updateBalanceForConsumption(UserCoinConsumption ucc) {
        DetachedCriteria dc = DetachedCriteria.forClass(UserCoin.class);
        dc.add(Restrictions.eq("userId", ucc.getUserId()));
        UserCoin uc = userCoinDao.findEntity(dc);

        if (uc == null) {
            throw new RuntimeException("用户账户不存在，请联系管理员！");
        }

        BigDecimal balance = BigDecimal.ZERO;
        BigDecimal balanceGive = BigDecimal.ZERO;
        BigDecimal balanceRewardGift = BigDecimal.ZERO;
        //用户充值余额+平台赠送余额-消费金额  是否大于0
        //判断余额是否充足（ucc的value为负值）
        if (uc.getBalance().add(uc.getBalanceGive()).add(ucc.getValue()).compareTo(BigDecimal.ZERO) != -1) {
            StringBuffer sql = new StringBuffer("UPDATE user_coin uc SET");
            //代币扣减消费顺序：平台赠送余额，充值余额
            //判断平台赠送余额是否足够支付消费金额
            if (uc.getBalanceGive().add(ucc.getValue()).compareTo(BigDecimal.ZERO) != -1) {
                sql.append(" uc.`balance_give`=uc.`balance_give`+" + ucc.getValue());
                balanceGive = ucc.getValue();
                //判断平台赠送余额+充值余额 是否足够支付消费金额
            } else if (uc.getBalanceGive().add(uc.getBalance()).add(ucc.getValue()).compareTo(BigDecimal.ZERO) != -1) {
                sql.append(" uc.`balance`=uc.`balance`+uc.`balance_give`+" + ucc.getValue());
                sql.append(", uc.`balance_give`=0");
                //取负
                balanceGive = uc.getBalanceGive().negate();
                balance = ucc.getValue().subtract(balanceGive);
            }
            sql.append(", uc.`version`=\"" + BeanUtil.getUUID() + "\"");
            sql.append(", uc.`update_time`=now()");
            //乐观锁机制 ，version判断用于防止并发数据出错
            sql.append("where user_id=\"" + ucc.getUserId() + "\" and uc.version =\"" + uc.getVersion() + "\"");
            int updateCount = userCoinDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql.toString());
            //若更新失败
            if (updateCount < 1) {
                throw new RuntimeException("网络异常,请稍后再试！");
//				updateBalanceForConsumption(ucc);
            }
        } else {
            //余额不足异常
            throw new RuntimeException("用户账户余额不足，请充值！");
        }

        ucc.setUserCoinId(uc.getId());
        ucc.setBalanceValue(balance);
        ucc.setBalanceGiveValue(balanceGive);
        //暂不支持主播收到的金额进行消费
        ucc.setBalanceRewardGift(balanceRewardGift);
        ucc.setCreateTime(new Date());
        ucc.setDeleted(false);
        ucc.setStatus(true);
        userCoinDao.save(ucc);
        return ucc;
    }

    @Override
    public void saveUserCoin(String userId) {
        UserCoin userCoin = new UserCoin();
        userCoin.setUserId(userId);
        userCoin.setBalance(BigDecimal.ZERO);
        userCoin.setBalanceGive(BigDecimal.ZERO);
        userCoin.setBalanceRewardGift(BigDecimal.ZERO);
        userCoin.setRmb(BigDecimal.ZERO);
        userCoin.setDeleted(false);
        userCoin.setCreateTime(new Date());
        userCoin.setStatus(true);
        userCoin.setVersion(BeanUtil.getUUID());
        userCoinDao.save(userCoin);
    }

    @Override
    public Page<RechargeRecord> getUserCoinIncreaseRecord(String userId,
                                                          Integer pageNumber, Integer pageSize) {
        return userCoinDao.getUserCoinIncreaseRecord(userId, pageNumber, pageSize);
    }

    @Override
    public void updateBalanceForGift(GiftStatement giftStatement, Gift gift) {
        BigDecimal total = new BigDecimal(giftStatement.getCount() * giftStatement.getPrice());
        UserCoinConsumption ucc = new UserCoinConsumption();
        ucc.setValue(total.negate());
        //送礼物
        ucc.setChangeType(ConsumptionChangeType.GIFT.getCode());
        ucc.setUserId(giftStatement.getGiver());
        //记录礼物流水id
        ucc.setOrderNoGift(giftStatement.getId().toString());
        ucc.setBalanceType(BalanceType.BALANCE.getCode());
        //扣除用户熊猫币余额
        ucc = updateBalanceForConsumption(ucc);

        CourseAnchor courseAnchor = userCoinDao.getCourseAnchor(giftStatement.getReceiver());
        //根据打赏比例获取主播实际获得的熊猫币   总数量*分得熊猫币比例
        BigDecimal addTotal = total.multiply(courseAnchor.getGiftDivide().divide(new BigDecimal(100)));
        UserCoinIncrease uci = new UserCoinIncrease();

        uci.setChangeType(IncreaseChangeType.GIFT.getCode());
        uci.setUserId(giftStatement.getReceiver());
        uci.setValue(addTotal);
        //平台本笔交易抽成金额 总金额-主播分得
        uci.setBrokerageValue(total.subtract(addTotal));
        //记录礼物流水id
        uci.setOrderNoGift(giftStatement.getId().toString());
        //礼物统一支付方式为空
        uci.setPayType(null);
        //订单来源:1.pc 2.h5 3.app 4.其他
        uci.setOrderFrom(giftStatement.getClientType());
        uci.setBalanceType(BalanceType.ANCHOR_BALANCE.getCode());
        //更新主播的数量
        updateBalanceForIncrease(uci);

    }

    @Override
    public void updateBalanceForCourse(OrderVo orderVo) {
        //课程单价换算为熊猫币
        BigDecimal total = new BigDecimal(orderVo.getActual_pay()).multiply(new BigDecimal(rate));
        Course course = courseDao.getCourse(orderVo.getCourse_id());
        String anchorId = course.getUserLecturerId();
        CourseAnchor courseAnchor = userCoinDao.getCourseAnchor(anchorId);
        //根据打赏比例获取主播实际获得的熊猫币   总数量*分得熊猫币比例
        BigDecimal ratio = BigDecimal.ZERO;
        if(course.getType()== CourseForm.LIVE.getCode()){
            ratio = courseAnchor.getLiveDivide();
        }else if(course.getType()== CourseForm.VOD.getCode()){
            ratio = courseAnchor.getVodDivide();
        }else if(course.getType()== CourseForm.OFFLINE.getCode()){
            ratio = courseAnchor.getOfflineDivide();
        }
        BigDecimal addTotal = total.multiply(ratio.divide(new BigDecimal(100)));
        logger.info("订单："+orderVo.getOrderId()+"总金额"+total+"====获得分成"+ratio+"="+addTotal);
        UserCoinIncrease uci = new UserCoinIncrease();

        uci.setChangeType(IncreaseChangeType.COURSE.getCode());
        uci.setUserId(anchorId);
        uci.setValue(addTotal);
        //平台本笔交易抽成金额 总金额-主播分得
        uci.setBrokerageValue(total.subtract(addTotal));
        //记录订单
        uci.setOrderNoCourse(orderVo.getOrderDetailId());
        //支付方式为空
        uci.setPayType(null);
        uci.setBalanceType(BalanceType.ANCHOR_BALANCE.getCode());
        //订单来源:1.pc 2.h5 3.android 4.ios 5.线下 6.工作人员
        uci.setOrderFrom(orderVo.getOrder_from());
        //更新主播的数量
        updateBalanceForIncrease(uci);
    }

    @Override
    public void updateBalanceForCourses(List<OrderVo> orderVos) {
        for (OrderVo orderVo:orderVos){
             updateBalanceForCourse(orderVo);
        }
    }

    @Override
    public void updateBalanceForReward(RewardStatement rs) throws XMPPException, SmackException, IOException {
        throw new RuntimeException("打赏功能暂时关闭，请通过送礼物方式为主播加油！");
        /*rewardService.insert(rs);
        Reward reward = rewardDao.getReward(Integer.valueOf(rs.getRewardId()));
        //根据打赏比例获取主播实际获得的熊猫币: 打赏金额*兑换比例*(1-平台抽成比例)
        BigDecimal total = new BigDecimal(rs.getPrice()).multiply(new BigDecimal(rate));
        BigDecimal addTotal = total.multiply(BigDecimal.ONE.subtract(new BigDecimal(reward.getBrokerage()).divide(new BigDecimal(100))));
        UserCoinIncrease uci = new UserCoinIncrease();
        uci.setChangeType(4);
        uci.setUserId(rs.getReceiver());
        uci.setValue(addTotal);
        //平台本笔交易抽成金额
        uci.setBrokerageValue(total.subtract(addTotal));
        //存入打赏流水id
        uci.setOrderNoReward(rs.getId().toString());
        //订单来源:1.pc 2.h5 3.app 4.其他
        uci.setOrderFrom(rs.getClientType());
        uci.setPayType(rs.getPayType());
        //更新主播的数量
        updateBalanceForIncrease(uci);*/
    }

    @Override
    public BigDecimal getEnableEnchashmentBalance(String userId) {
        UserCoin uc = userCoinDao.getBalanceByUserId(userId);
        //可提现熊猫币=打赏+卖课获得
        return uc.getBalanceRewardGift();
    }

    @Override
    public UserCoinConsumption updateBalanceForEnchashment(UserCoinConsumption ucc) {
        DetachedCriteria dc = DetachedCriteria.forClass(UserCoin.class);
        dc.add(Restrictions.eq("userId", ucc.getUserId()));
        UserCoin uc = userCoinDao.findEntity(dc);

        if (uc == null) {
            throw new RuntimeException("用户账户不存在！");
        }

        BigDecimal balanceRewardGift = BigDecimal.ZERO;
        BigDecimal balance = BigDecimal.ZERO;

        StringBuffer sql = new StringBuffer("UPDATE user_coin uc SET");
        //用户收到的礼物打赏余额-提现金额  是否大于0
        //判断余额是否充足（ucc的value为负值）
        if (uc.getBalanceRewardGift().add(ucc.getValue()).compareTo(BigDecimal.ZERO) != -1) {
            sql.append(" uc.`balance_reward_gift`=uc.balance_reward_gift" + ucc.getValue());
            balanceRewardGift = ucc.getValue();
        } else if (uc.getBalanceRewardGift().add(uc.getBalance()).add(ucc.getValue()).compareTo(BigDecimal.ZERO) != -1) {
            //判断余额是否充足（ucc的value为负值）
            sql.append(" uc.`balance`=uc.balance + uc.balance_reward_gift +" + ucc.getValue() + ",");
            sql.append(" uc.`balance_reward_gift`= 0 ");
            balanceRewardGift = uc.getBalanceRewardGift().negate();
            balance = ucc.getValue().subtract(balanceRewardGift);
        } else {
            //余额不足异常
            throw new RuntimeException("用户账户可提现余额不足！");
        }
        sql.append(", uc.`version`=\"" + BeanUtil.getUUID() + "\"");
        sql.append(", uc.`update_time`=now()");
        sql.append("where user_id=\"" + ucc.getUserId() + "\" and uc.version =\"" + uc.getVersion() + "\"");//乐观锁机制 ，version判断用于防止并发数据出错

        int updateCount = userCoinDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql.toString());
        if (updateCount < 1) {
            throw new RuntimeException("系统繁忙,请稍后再试！");
//			updateBalanceForEnchashment(ucc);
        }
        ucc.setUserCoinId(uc.getId());
        ucc.setBalanceValue(balance);
        ucc.setBalanceGiveValue(BigDecimal.ZERO);
        //提现代币金额
        ucc.setBalanceRewardGift(balanceRewardGift);
        ucc.setCreateTime(new Date());
        ucc.setDeleted(false);
        ucc.setStatus(true);
        userCoinDao.save(ucc);
        return ucc;
    }

    @Override
    public boolean checkRechargeOrder(String orderNo) {
        UserCoinIncrease uci = userCoinDao.getUserCoinIncreaseRecordByOrder(orderNo);
        if (uci != null) {
            return true;
        }
        return false;
    }

    @Override
    public Object getUserCoinConsumptionRecord(String userId, Integer pageNumber, Integer pageSize) {
        return userCoinDao.consumptionCoinList(userId, pageNumber, pageSize);
    }
}
