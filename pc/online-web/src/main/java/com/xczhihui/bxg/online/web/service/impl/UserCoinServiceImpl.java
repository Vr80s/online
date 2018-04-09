package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.support.service.impl.RedisCacheService;
import com.xczhihui.bxg.common.util.BeanUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.*;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.api.vo.OrderVo;
import com.xczhihui.bxg.online.api.vo.RechargeRecord;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.utils.OrderNoUtil;
import com.xczhihui.bxg.online.common.utils.lock.Lock;
import com.xczhihui.bxg.online.web.dao.CourseDao;
import com.xczhihui.bxg.online.web.dao.EnchashmentApplyDao;
import com.xczhihui.bxg.online.web.dao.UserCoinDao;
import com.xczhihui.medical.anchor.service.IAnchorInfoService;
import org.apache.commons.lang.StringUtils;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    public static BigDecimal iosRatio = new BigDecimal("0.3");

    @Autowired
    private RedisCacheService cacheService;
    @Autowired
    private IAnchorInfoService anchorInfoService;
    @Autowired
    private EnchashmentApplyDao enchashmentApplyDao;

    private static String anchorCache = "anchor_";

    @Override
    public String getBalanceByUserId(String userId) {
        UserCoin uc = userCoinDao.getBalanceByUserId(userId);
        if (uc == null) {
            throw new RuntimeException(userId + "--用户账户不存在！");
        }
        BigDecimal balanceTotal = uc.getBalance().add(uc.getBalanceGive());
        return balanceTotal.setScale(0, BigDecimal.ROUND_DOWN).toString();
    }

    @Override
    public String getSettlementBalanceByUserId(String userId) {
        anchorInfoService.validateAnchorPermission(userId);
        UserCoin uc = userCoinDao.getBalanceByUserId(userId);
        if (uc == null) {
            throw new RuntimeException(userId + "--用户账户不存在！");
        }
        String settlementBalance = uc.getBalanceRewardGift().setScale(2, BigDecimal.ROUND_DOWN).toString();
        return settlementBalance;
    }

    @Override
    public String getEnchashmentBalanceByUserId(String userId) {
        anchorInfoService.validateAnchorPermission(userId);
        UserCoin uc = userCoinDao.getBalanceByUserId(userId);
        if (uc == null) {
            throw new RuntimeException(userId + "--用户账户不存在！");
        }
        String enchashmentBalance = uc.getRmb().setScale(2, BigDecimal.ROUND_DOWN).toString();
        return enchashmentBalance;
    }

    @Override
    public void updateBalanceForRecharge(String userId, Payment payment, BigDecimal coin, OrderFrom orderFrom, String orderNo) {
        UserCoinIncrease userCoinIncrease=new UserCoinIncrease();
        userCoinIncrease.setUserId(userId);
        //充值
        userCoinIncrease.setChangeType(IncreaseChangeType.RECHARGE.getCode());
        userCoinIncrease.setBalanceType(BalanceType.BALANCE.getCode());
        //支付类型
        userCoinIncrease.setPayType(payment.getCode());
        userCoinIncrease.setValue(coin);
        userCoinIncrease.setCreateTime(new Date());
        userCoinIncrease.setOrderFrom(orderFrom.getCode());
        userCoinIncrease.setOrderNoRecharge(orderNo);

        updateBalanceForIncrease(userCoinIncrease);
    }

    @Override
    public void updateBalanceForIncrease(UserCoinIncrease uci) {

        BigDecimal balance = BigDecimal.ZERO;
        BigDecimal balanceGive = BigDecimal.ZERO;
        BigDecimal balanceRewardGift = BigDecimal.ZERO;
        BigDecimal rmb = BigDecimal.ZERO;

        UserCoin uc = userCoinDao.getBalanceByUserId(uci.getUserId());
        StringBuffer sql = new StringBuffer("UPDATE user_coin uc SET");
        if (IncreaseChangeType.GIVE.getCode() == uci.getChangeType()) {
            //若为赠送，则赠送余额增加
            sql.append(" uc.`balance_give`=uc.`balance_give`+" + uci.getValue());
            balanceGive = uci.getValue();
        } else if (IncreaseChangeType.GIFT.getCode() == uci.getChangeType() || IncreaseChangeType.REWARD.getCode() == uci.getChangeType() || IncreaseChangeType.COURSE.getCode() == uci.getChangeType()) {
            //若为礼物打赏或课程分成，则礼物打赏余额增加
            sql.append(" uc.`balance_reward_gift`=uc.`balance_reward_gift`+" + uci.getValue());
            balanceRewardGift = uci.getValue();
        } else if (IncreaseChangeType.RECHARGE.getCode() == uci.getChangeType()){
            //若为充值  则充值余额增加
            sql.append(" uc.`balance`=uc.`balance`+" + uci.getValue());
            balance = uci.getValue();
        }
        sql.append(", uc.`version`=\"" + BeanUtil.getUUID() + "\"");
        sql.append(", uc.`update_time` = now() ");
        sql.append("where user_id=\"" + uci.getUserId() + "\"");
        int updateCount = userCoinDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql.toString());
        if (updateCount < 1) {
            //若更新失败，则继续回调该方法
            throw new RuntimeException("网络异常,请稍后再试！");
        }

        uci.setUserCoinId(uc.getId());
        uci.setBalance(balance);
        uci.setBalanceGive(balanceGive);
        uci.setBalanceRewardGift(balanceRewardGift);
        uci.setRmb(rmb);
        uci.setCreateTime(new Date());
        uci.setDeleted(false);
        uci.setStatus(true);
        if (uci.getBrokerageValue() == null) {
            uci.setBrokerageValue(BigDecimal.ZERO);
        }
        userCoinDao.save(uci);
    }

    @Override
    public UserCoinConsumption updateBalanceForConsumption(UserCoinConsumption ucc) {
        UserCoin uc = userCoinDao.getBalanceByUserId(ucc.getUserId());

        if (uc == null) {
            throw new RuntimeException("用户账户不存在，请联系管理员！");
        }

        BigDecimal balance = BigDecimal.ZERO;
        BigDecimal balanceGive = BigDecimal.ZERO;
        BigDecimal balanceRewardGift = BigDecimal.ZERO;
        BigDecimal rmb = BigDecimal.ZERO;
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
                logger.error("刷礼物出现问题,userId:{}",ucc.getUserId());
                throw new RuntimeException("网络异常,请稍后再试！");
            }
        } else {
            //余额不足异常
            throw new RuntimeException("余额不足");
        }

        ucc.setUserCoinId(uc.getId());
        ucc.setBalanceValue(balance);
        ucc.setBalanceGiveValue(balanceGive);
        ucc.setRmb(rmb);
        //暂不支持主播收到的金额进行消费
        ucc.setBalanceRewardGift(balanceRewardGift);
        ucc.setCreateTime(new Date());
        ucc.setDeleted(false);
        ucc.setStatus(true);
        userCoinDao.save(ucc);
        return ucc;
    }


    @Override
    public UserCoinConsumption updateBalanceForBuyCourse(String userId, OrderFrom orderFrom, BigDecimal coin, String orderNo) {
        if(coin.compareTo(BigDecimal.ZERO) != 1){
            throw new RuntimeException("课程熊猫币价格必须大于0");
        }
        UserCoinConsumption ucc = new UserCoinConsumption();
        ucc.setUserId(userId);
        ucc.setOrderFrom(orderFrom.getCode());
        ucc.setValue(coin.negate());
        ucc.setOrderNoConsume(orderNo);
        ucc.setBalanceType(BalanceType.BALANCE.getCode());
        ucc.setChangeType(ConsumptionChangeType.COURSE.getCode());
        return updateBalanceForConsumption(ucc);
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
    public Page<RechargeRecord> getUserCoinIncreaseRecord(String userId,Integer pageNumber, Integer pageSize) {
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
        ucc.setOrderFrom(giftStatement.getClientType());
        //扣除用户熊猫币余额
        updateBalanceForConsumption(ucc);

        CourseAnchor courseAnchor = cacheService.get(anchorCache+giftStatement.getReceiver());
        if(courseAnchor == null){
            courseAnchor = userCoinDao.getCourseAnchor(giftStatement.getReceiver());
            //缓存60s
            cacheService.set(anchorCache+giftStatement.getReceiver(),courseAnchor,60);
        }else{
            logger.info("取到缓存主播分成数据");
        }

        BigDecimal ratio = courseAnchor.getGiftDivide();
        BigDecimal iOSBrokerageValue = BigDecimal.ZERO;
        //若为ios订单，计算苹果分成
        if(giftStatement.getClientType()== OrderFrom.IOS.getCode()){
            iOSBrokerageValue = total.multiply(iosRatio);
        }
        //主播获益=(总金额-ios抽成)*主播分成比例
        BigDecimal addTotal = (total.subtract(iOSBrokerageValue)).multiply(ratio.divide(new BigDecimal(100)));
        logger.info("礼物订单："+giftStatement.getId()+"总金额"+total+" 苹果分成="+iOSBrokerageValue+" 主播分成："+ratio+"="+addTotal);
        UserCoinIncrease uci = new UserCoinIncrease();

        uci.setChangeType(IncreaseChangeType.GIFT.getCode());
        uci.setUserId(giftStatement.getReceiver());
        uci.setValue(addTotal);

        //苹果分成
        uci.setIosBrokerageValue(iOSBrokerageValue);
        //平台本笔交易抽成金额 总金额-苹果分成-主播分成
        uci.setBrokerageValue(total.subtract(iOSBrokerageValue).subtract(addTotal));
        //主播分成比例
        uci.setRatio(ratio);

        //记录礼物流水id
        uci.setOrderNoGift(giftStatement.getId().toString());
        //礼物统一支付方式为空
        uci.setPayType(null);
        //订单来源:1.pc 2.h5 3.android 4.ios 5.线下 6.工作人员
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
        if(courseAnchor!=null){
            //根据打赏比例获取主播实际获得的熊猫币   总数量*分得熊猫币比例
            BigDecimal ratio = BigDecimal.ZERO;
            if(course.getType()== CourseForm.LIVE.getCode()){
                ratio = courseAnchor.getLiveDivide();
            }else if(course.getType()== CourseForm.VOD.getCode()){
                ratio = courseAnchor.getVodDivide();
            }else if(course.getType()== CourseForm.OFFLINE.getCode()){
                ratio = courseAnchor.getOfflineDivide();
            }
            BigDecimal iOSBrokerageValue = BigDecimal.ZERO;
            //若为ios订单，计算苹果分成
            if(orderVo.getOrder_from()== OrderFrom.IOS.getCode()){
                iOSBrokerageValue = total.multiply(iosRatio);
            }
            //主播获益=(总金额-ios抽成)*主播分成比例
            BigDecimal addTotal = (total.subtract(iOSBrokerageValue)).multiply(ratio.divide(new BigDecimal(100)));
            logger.info("订单："+orderVo.getOrderId()+"总金额"+total+" 苹果分成="+iOSBrokerageValue+" 主播分成："+ratio+"="+addTotal);
            UserCoinIncrease uci = new UserCoinIncrease();

            uci.setChangeType(IncreaseChangeType.COURSE.getCode());
            uci.setUserId(anchorId);
            uci.setValue(addTotal);
            //苹果分成
            uci.setIosBrokerageValue(iOSBrokerageValue);
            //平台本笔交易抽成金额 = 总金额-苹果分成-主播分成
            uci.setBrokerageValue(total.subtract(iOSBrokerageValue).subtract(addTotal));
            //主播分成比例
            uci.setRatio(ratio);
            //记录订单
            uci.setOrderNoCourse(orderVo.getOrderDetailId());
            //支付方式
            uci.setPayType(orderVo.getPayment().getCode());
            uci.setBalanceType(BalanceType.ANCHOR_BALANCE.getCode());
            //订单来源:1.pc 2.h5 3.android 4.ios 5.线下 6.工作人员
            uci.setOrderFrom(orderVo.getOrder_from());
            //更新主播的数量
            updateBalanceForIncrease(uci);
        }else{
            logger.info("课程{}主播不存在，未进行分成",orderVo.getCourse_id());
        }
    }

    @Override
    public void updateBalanceForCourses(List<OrderVo> orderVos) {
        for (OrderVo orderVo:orderVos){
            if(orderVo.getOrder_from()!= OrderFrom.WORKER.getCode()&&orderVo.getOrder_from()!= OrderFrom.GIVE.getCode()){
                updateBalanceForCourse(orderVo);
            }else{
                logger.info("订单{}为工作人员订单，不计入主播分成",orderVo.getOrderDetail());
            }
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
    public UserCoinConsumption updateBalanceForEnchashment(String userId, BigDecimal enchashmentSum, OrderFrom orderFrom,String enchashmentApplyId) {
        UserCoin uc = userCoinDao.getBalanceByUserId(userId);

        if (uc == null) {
            throw new RuntimeException("用户账户不存在！");
        }
        if(enchashmentSum.compareTo(BigDecimal.ZERO)!=1){
            throw new RuntimeException("提现金额必须大于0");
        }

        UserCoinConsumption ucc = new UserCoinConsumption();
        ucc.setValue(enchashmentSum.negate());
        ucc.setUserId(userId);
        StringBuffer sql = new StringBuffer("UPDATE user_coin uc SET");
        //判断余额是否充足（ucc的value为负值）
        if (uc.getRmb().add(ucc.getValue()).compareTo(BigDecimal.ZERO) != -1) {
            sql.append(" uc.`rmb`=uc.rmb" + ucc.getValue());
        } else {
            //余额不足异常
            throw new RuntimeException("用户账户可提现余额不足！");
        }
        sql.append(", uc.`version`=\"" + BeanUtil.getUUID() + "\"");
        sql.append(", uc.`update_time`=now()");
        //乐观锁机制 ，version判断用于防止并发数据出错
        sql.append("where user_id=\"" + ucc.getUserId() + "\" and uc.rmb >=" + ucc.getValue().negate() + " and uc.version =\"" + uc.getVersion() + "\"");
        int updateCount = userCoinDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql.toString());
        if (updateCount < 1) {
            throw new RuntimeException("网络异常,请稍后再试！");
        }
        ucc.setUserCoinId(uc.getId());
        ucc.setBalanceValue(BigDecimal.ZERO);
        ucc.setBalanceGiveValue(BigDecimal.ZERO);
        ucc.setBalanceRewardGift(BigDecimal.ZERO);
        ucc.setOrderFrom(orderFrom.getCode());
        ucc.setRmb(ucc.getValue());
        ucc.setOrderNoEnchashment(enchashmentApplyId);
        ucc.setCreateTime(new Date());
        ucc.setDeleted(false);
        ucc.setStatus(true);
        ucc.setBalanceType(BalanceType.ANCHOR_RMB.getCode());
        ucc.setChangeType(ConsumptionChangeType.ENCHASHMENT.getCode());
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

    /**
     * Description：结算
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/3/5 0005 下午 4:17
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Lock(lockName = "updateBalanceForSettlement")
    public void updateBalanceForSettlement4Lock(String lockKey, String userId, int amount, OrderFrom orderFrom) {

        BigDecimal value = new BigDecimal(amount).negate();

        if(value.compareTo(BigDecimal.ZERO) != -1){
            throw new RuntimeException("结算金额必须大于0");
        }
        UserCoin uc = userCoinDao.getBalanceByUserId(userId);
        if(uc.getBalanceRewardGift().add(value).compareTo(BigDecimal.ZERO) == -1){
            throw new RuntimeException("结算金额超出熊猫币余额");
        }
        UserCoinConsumption ucc = new UserCoinConsumption();
        ucc.setUserId(userId);
        ucc.setOrderFrom(orderFrom.getCode());
        ucc.setValue(value);

        String version = BeanUtil.getUUID();
        StringBuffer sql = new StringBuffer("UPDATE user_coin uc SET");
        sql.append(" uc.`balance_reward_gift`=uc.`balance_reward_gift`+" + ucc.getValue());
        sql.append(", uc.`version`=\"" + version + "\"");
        sql.append(", uc.`update_time`=now()");
        //乐观锁机制 ，version判断用于防止并发数据出错
        sql.append("where user_id=\"" + ucc.getUserId() + "\" and uc.balance_reward_gift >=" + ucc.getValue().negate() + " and uc.version =\"" + uc.getVersion() + "\"");
        int updateCount = userCoinDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql.toString());
        //若更新失败
        if (updateCount < 1) {
            throw new RuntimeException("网络异常,请稍后再试！");
        }

        ucc.setUserCoinId(uc.getId());
        ucc.setBalanceValue(BigDecimal.ZERO);
        ucc.setBalanceGiveValue(BigDecimal.ZERO);
        ucc.setBalanceRewardGift(ucc.getValue());
        ucc.setRmb(BigDecimal.ZERO);
        ucc.setCreateTime(new Date());
        ucc.setDeleted(false);
        ucc.setStatus(true);
        ucc.setChangeType(ConsumptionChangeType.SETTLEMENT.getCode());
        ucc.setBalanceType(BalanceType.ANCHOR_BALANCE.getCode());
        userCoinDao.save(ucc);

        UserCoinIncrease uci = new UserCoinIncrease();
        uci.setUserId(userId);
        uci.setOrderFrom(orderFrom.getCode());
        //结算扣币的流水id
        uci.setOrderNoSettlement(ucc.getId().toString());
        //人民币余额变动 = 结算熊猫币/兑换比例
        uci.setValue(value.negate().divide(new BigDecimal(rate)));

        sql = new StringBuffer("UPDATE user_coin uc SET");
        sql.append(" uc.`rmb`=uc.`rmb`+" + uci.getValue());
        sql.append(", uc.`version`=\"" + BeanUtil.getUUID() + "\"");
        sql.append(", uc.`update_time`=now()");
        //乐观锁机制 ，version判断用于防止并发数据出错
        sql.append("where user_id=\"" + ucc.getUserId() + "\" and uc.version =\"" + version + "\"");
        updateCount = userCoinDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql.toString());
        //若更新失败
        if (updateCount < 1) {
            throw new RuntimeException("网络异常,请稍后再试！");
        }

        uci.setUserCoinId(uc.getId());
        uci.setBalance(BigDecimal.ZERO);
        uci.setBalanceGive(BigDecimal.ZERO);
        uci.setBalanceRewardGift(BigDecimal.ZERO);
        uci.setRmb(uci.getValue());
        uci.setBalanceType(BalanceType.ANCHOR_RMB.getCode());
        uci.setChangeType(IncreaseChangeType.SETTLEMENT.getCode());
        uci.setBrokerageValue(BigDecimal.ZERO);
        uci.setCreateTime(new Date());
        uci.setDeleted(false);
        uci.setStatus(true);
        userCoinDao.save(uci);
    }

    /**
     * Description：提现申请
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/3/5 0005 下午 4:17
     **/
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Lock(lockName = "saveEnchashmentApplyInfo")
    public void saveEnchashmentApplyInfo4Lock(String lockKey, String userId, BigDecimal enchashmentSum, int bankCardId, OrderFrom orderFrom){
        EnchashmentApplyInfo enchashmentApplyInfo = new EnchashmentApplyInfo();
        String enchashmentApplyId = BeanUtil.getUUID();
        enchashmentApplyInfo.setId(enchashmentApplyId);
        enchashmentApplyInfo.setUserId(userId);
        enchashmentApplyInfo.setEnchashmentSum(enchashmentSum);
        enchashmentApplyInfo.setBankCardId(bankCardId);
        enchashmentApplyInfo.setOrderFrom(orderFrom.getCode());
        enchashmentApplyInfo.setDeleted(false);
        enchashmentApplyInfo.setTime(new Date());
        enchashmentApplyInfo.setStatus(ApplyStatus.UNTREATED.getCode());
        String enchashmentOrderNo = OrderNoUtil.getEnchashmentOrderNo();
        enchashmentApplyInfo.setOrderNo(enchashmentOrderNo);
        validateEnchashmentApplyInfo(enchashmentApplyInfo);
        //更新用户人民币余额
        this.updateBalanceForEnchashment(userId,enchashmentSum,orderFrom,enchashmentApplyId);
        enchashmentApplyDao.save(enchashmentApplyInfo);
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
