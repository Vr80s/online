package com.xczhihui.bxg.online.web.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.BeanUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.Gift;
import com.xczhihui.bxg.online.api.po.GiftStatement;
import com.xczhihui.bxg.online.api.po.RewardStatement;
import com.xczhihui.bxg.online.api.po.UserCoin;
import com.xczhihui.bxg.online.api.po.UserCoinConsumption;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.api.vo.RechargeRecord;
import com.xczhihui.bxg.online.common.domain.Reward;
import com.xczhihui.bxg.online.web.dao.RewardDao;
import com.xczhihui.bxg.online.web.dao.UserCoinDao;
import com.xczhihui.bxg.online.web.service.RewardService;

/**
 * ClassName: UserCoinServiceImpl.java <br>
 * Description: <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月11日<br>
 */
@Service
public class UserCoinServiceImpl implements UserCoinService {

    @Autowired
    private UserCoinDao userCoinDao;
    @Autowired
    private RewardDao rewardDao;
    @Autowired
    private RewardService rewardService;
    @Value("${rate}")
    private int rate;


    /* (non-Javadoc)
     * @see com.xczhihui.bxg.online.web.service.UserCoinService#getBalanceByUserId(java.lang.String)
     */
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

    /* (non-Javadoc)
     * @see com.xczhihui.bxg.online.web.service.UserCoinService#updateBalanceForIncrease(com.xczhihui.bxg.online.common.domain.UserCoinIncrease)
     */
    @Override
    public void updateBalanceForIncrease(UserCoinIncrease uci) {
//		if(1 == uci.getChangeType()){
//		}
        StringBuffer sql = new StringBuffer("UPDATE user_coin uc SET");
        if (2 == uci.getChangeType()) {//若为赠送，则赠送余额增加
            sql.append(" uc.`balance_give`=uc.`balance_give`+" + uci.getValue());
        } else if (3 == uci.getChangeType() || 4 == uci.getChangeType()) {//若为礼物打赏，则礼物打赏余额增加
            sql.append(" uc.`balance_reward_gift`=uc.`balance_reward_gift`+" + uci.getValue());
        } else {//若为充值  则充值月增加
            sql.append(" uc.`balance`=uc.`balance`+" + uci.getValue());
        }
        sql.append(", uc.`version`=\"" + BeanUtil.getUUID() + "\"");
        sql.append(", uc.`update_time` = now() ");
        sql.append("where user_id=\"" + uci.getUserId() + "\"");
        int updateCount = userCoinDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql.toString());
        if (updateCount < 1) {//若更新失败，则继续回调该方法
            throw new RuntimeException("系统繁忙,请稍后再试！");
//			updateBalanceForIncrease(uci);
        }

        DetachedCriteria dc = DetachedCriteria.forClass(UserCoin.class);
        dc.add(Restrictions.eq("userId", uci.getUserId()));
        UserCoin uc = userCoinDao.findEntity(dc);

        uci.setUserCoinId(uc.getId());
        uci.setBalance(uc.getBalance());
        uci.setBalanceGive(uc.getBalanceGive());
        uci.setBalanceRewardGift(uc.getBalanceRewardGift());
        uci.setCreateTime(new Date());
        uci.setDeleted(false);
        uci.setStatus(true);
        if (uci.getBrokerageValue() == null) {
            uci.setBrokerageValue(BigDecimal.ZERO);
        }
        userCoinDao.save(uci);
    }

    /* (non-Javadoc)
     * @see com.xczhihui.bxg.online.web.service.UserCoinService#updateBalanceForConsumption(com.xczhihui.bxg.online.common.domain.UserCoinConsumption)
     */
    @Override
    public  UserCoinConsumption updateBalanceForConsumption(UserCoinConsumption ucc) {
        DetachedCriteria dc = DetachedCriteria.forClass(UserCoin.class);
        dc.add(Restrictions.eq("userId", ucc.getUserId()));
        UserCoin uc = userCoinDao.findEntity(dc);

        if (uc == null) {
            throw new RuntimeException("用户账户不存在！");
        }

        BigDecimal balance = BigDecimal.ZERO;
        BigDecimal balanceGive = BigDecimal.ZERO;
        BigDecimal balanceRewardGift = BigDecimal.ZERO;
        //用户充值余额+平台赠送余额+收到的礼物打赏余额-消费金额  是否大于0
        if (uc.getBalance().add(uc.getBalanceGive()).add(uc.getBalanceRewardGift()).add(ucc.getValue()).compareTo(BigDecimal.ZERO) != -1) {//判断余额是否充足（ucc的value为负值）
            StringBuffer sql = new StringBuffer("UPDATE user_coin uc SET");
            //代币扣减消费顺序：平台赠送余额，充值余额，收到的礼物打赏余额
            //判断平台赠送余额是否足够支付消费金额
            if (uc.getBalanceGive().add(ucc.getValue()).compareTo(BigDecimal.ZERO) != -1) {
                sql.append(" uc.`balance_give`=uc.`balance_give`+" + ucc.getValue());
                balanceGive = ucc.getValue();
                //判断平台赠送余额+充值余额 是否足够支付消费金额
            } else if (uc.getBalanceGive().add(uc.getBalance()).add(ucc.getValue()).compareTo(BigDecimal.ZERO) != -1) {
                sql.append(" uc.`balance`=uc.`balance`+uc.`balance_give`+" + ucc.getValue());
                sql.append(", uc.`balance_give`=0");
                balanceGive = uc.getBalanceGive().negate();//取负
                balance = ucc.getValue().subtract(balanceGive);
            } else {
                sql.append(" uc.`balance_reward_gift`=uc.`balance`+uc.`balance_give`+uc.balance_reward_gift" + ucc.getValue());
                sql.append(", uc.`balance_give`=0");
                sql.append(", uc.`balance`=0");
                balanceGive = uc.getBalanceGive().negate();//取负
                balance = uc.getBalance().negate();//取负
                balanceRewardGift = ucc.getValue().subtract(balanceGive).subtract(balanceGive);
            }
            sql.append(", uc.`version`=\"" + BeanUtil.getUUID() + "\"");
            sql.append(", uc.`update_time`=now()");
            sql.append("where user_id=\"" + ucc.getUserId() + "\" and uc.version =\"" + uc.getVersion() + "\"");//乐观锁机制 ，version判断用于防止并发数据出错
            int updateCount = userCoinDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql.toString());
            if (updateCount < 1) {//若更新失败，则继续回调该方法
                throw new RuntimeException("系统繁忙,请稍后再试！");
//				updateBalanceForConsumption(ucc);
            }
        } else {
            //余额不足异常
            throw new RuntimeException("用户账户余额不足，请充值！");
        }

//		uc = userCoinDao.findEntity(dc);
        ucc.setUserCoinId(uc.getId());
        ucc.setBalanceValue(balance);
        ucc.setBalanceGiveValue(balanceGive);
        ucc.setBalanceRewardGift(balanceRewardGift);//暂不支持收到的金额进行消费
        ucc.setCreateTime(new Date());
        ucc.setDeleted(false);
        ucc.setStatus(true);
        userCoinDao.save(ucc);
        return ucc;
    }

    /* (non-Javadoc)
     * @see com.xczhihui.bxg.online.web.service.UserCoinService#saveUserCoin(java.lang.String)
     */
    @Override
    public void saveUserCoin(String userId) {
        UserCoin userCoin = new UserCoin();
        userCoin.setUserId(userId);
        userCoin.setBalance(BigDecimal.ZERO);
        userCoin.setBalanceGive(BigDecimal.ZERO);
        userCoin.setBalanceRewardGift(BigDecimal.ZERO);
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

    /* (non-Javadoc)
     * @see com.xczhihui.bxg.online.web.service.UserCoinService#updateBalanceForGiftReward(com.xczhihui.bxg.online.web.po.GiftStatement, com.xczhihui.bxg.online.web.po.Gift)
     */
    @Override
    public void updateBalanceForGift(GiftStatement giftStatement, Gift gift) {
        BigDecimal total = new BigDecimal(giftStatement.getCount() * giftStatement.getPrice());
        UserCoinConsumption ucc = new UserCoinConsumption();
        ucc.setValue(total.negate());
        ucc.setChangeType(8);//送礼物
        ucc.setUserId(giftStatement.getGiver());
        ucc.setOrderNoGift(giftStatement.getId().toString());//记录礼物流水id
        ucc = updateBalanceForConsumption(ucc);//扣除用户熊猫币余额

        //根据打赏比例获取主播实际获得的熊猫币   总数量*（1-平台抽成比例）
        BigDecimal addTotal = total.multiply(BigDecimal.ONE.subtract(new BigDecimal(gift.getBrokerage()).divide(new BigDecimal(100))));
        UserCoinIncrease uci = new UserCoinIncrease();

        uci.setChangeType(3);
        uci.setUserId(giftStatement.getReceiver());
        uci.setValue(addTotal);
        uci.setBrokerageValue(total.subtract(addTotal));//平台本笔交易抽成金额
        uci.setOrderNoGift(giftStatement.getId().toString());//记录礼物流水id
        uci.setPayType(null);//礼物统一支付方式为空
        uci.setOrderFrom(giftStatement.getClientType());//订单来源:1.pc 2.h5 3.app 4.其他
        updateBalanceForIncrease(uci);//更新主播的数量

    }

    /* (non-Javadoc)
     * @see com.xczhihui.bxg.online.web.service.UserCoinService#updateBalanceForReward(com.xczhihui.bxg.online.web.po.RewardStatement)
     */
    @Override
    public void updateBalanceForReward(RewardStatement rs) throws XMPPException, SmackException, IOException {
        rewardService.insert(rs);
        Reward reward = rewardDao.getReward(Integer.valueOf(rs.getRewardId()));
        //根据打赏比例获取主播实际获得的熊猫币: 打赏金额*兑换比例*(1-平台抽成比例)
        BigDecimal total = new BigDecimal(rs.getPrice()).multiply(new BigDecimal(rate));
        BigDecimal addTotal = total.multiply(BigDecimal.ONE.subtract(new BigDecimal(reward.getBrokerage()).divide(new BigDecimal(100))));
        UserCoinIncrease uci = new UserCoinIncrease();
        uci.setChangeType(4);
        uci.setUserId(rs.getReceiver());
        uci.setValue(addTotal);
        uci.setBrokerageValue(total.subtract(addTotal));//平台本笔交易抽成金额
        uci.setOrderNoReward(rs.getId().toString());//存入打赏流水id
        uci.setOrderFrom(rs.getClientType());//订单来源:1.pc 2.h5 3.app 4.其他
        uci.setPayType(rs.getPayType());
        updateBalanceForIncrease(uci);//更新主播的数量
    }

    @Override
    public BigDecimal getEnableEnchashmentBalance(String userId) {
        UserCoin uc = userCoinDao.getBalanceByUserId(userId);
        return uc.getBalanceRewardGift().add(uc.getBalance());//可提现熊猫币=充值+礼物打赏获得
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
        if (uc.getBalanceRewardGift().add(ucc.getValue()).compareTo(BigDecimal.ZERO) != -1) {//判断余额是否充足（ucc的value为负值）
            sql.append(" uc.`balance_reward_gift`=uc.balance_reward_gift" + ucc.getValue());
            balanceRewardGift = ucc.getValue();
        } else if (uc.getBalanceRewardGift().add(uc.getBalance()).add(ucc.getValue()).compareTo(BigDecimal.ZERO) != -1) {//判断余额是否充足（ucc的value为负值）
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
        ucc.setBalanceRewardGift(balanceRewardGift);//提现代币金额
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
