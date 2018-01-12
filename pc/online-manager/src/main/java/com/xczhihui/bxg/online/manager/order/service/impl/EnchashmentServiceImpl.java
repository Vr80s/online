package com.xczhihui.bxg.online.manager.order.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.BeanUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.EnchashmentApplication;
import com.xczhihui.bxg.online.api.po.EnchashmentRecord;
import com.xczhihui.bxg.online.api.po.UserCoin;
import com.xczhihui.bxg.online.api.po.UserCoinConsumption;
import com.xczhihui.bxg.online.api.po.UserCoinIncrease;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.message.dao.MessageDao;
import com.xczhihui.bxg.online.manager.order.dao.EnchashmentDao;
import com.xczhihui.bxg.online.manager.order.service.EnchashmentService;
import com.xczhihui.bxg.online.manager.order.vo.MessageShortVo;

@Service
public class EnchashmentServiceImpl extends OnlineBaseServiceImpl implements EnchashmentService {

	@Autowired
    private EnchashmentDao enchashmentDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@Override
	public Page<EnchashmentApplication> findEnchashmentPage(EnchashmentApplication orderVo, Integer pageNumber, Integer pageSize) {
		Page<EnchashmentApplication> page = enchashmentDao.findEnchashmentPage(orderVo, pageNumber, pageSize);
		return page;
	}

	@Override
	public void updateHandleEnchashment(EnchashmentApplication ea) throws IllegalAccessException, InvocationTargetException {
		DetachedCriteria dc = DetachedCriteria.forClass(EnchashmentApplication.class);
		dc.add(Restrictions.eq("id", ea.getId().intValue()));
		EnchashmentApplication e = dao.findEntity(dc);
		if(e.getEnchashmentStatus()!=0){
			throw new RuntimeException("该提现已处理过！");
		}
		
		if(ea.getEnchashmentStatus()!=1&&ea.getEnchashmentStatus()!=2){//驳回
			throw new RuntimeException("处理状态不对");
		}
		if(e.getEnchashmentStatus()==1){
			e.setTicklingTime(ea.getTicklingTime());
		}else{
			e.setTicklingTime(new Date());
			e.setCauseType(ea.getCauseType());
		}
		e.setEnchashmentStatus(ea.getEnchashmentStatus());
		e.setOperateRemark(ea.getOperateRemark());
		dao.update(e);
		String msg_id = UUID.randomUUID().toString().replaceAll("-", ""); //最新消息的id
		String content = "";
		//"快去加入班级QQ群："+course.get("qqno")+"~";
		MessageShortVo messageShortVo = new MessageShortVo();
		messageShortVo.setUser_id(e.getUserId());
		messageShortVo.setId(msg_id);
		messageShortVo.setCreate_person(ea.getOperator());
		messageShortVo.setType(1);
		EnchashmentRecord er = new EnchashmentRecord();
		if(e.getEnchashmentStatus()==1){//若为打款，则保存至提现记录表
			BeanUtils.copyProperties(er, e);
			er.setEnchashmentApplicationId(e.getId());
			er.setId(null);
			dao.save(er);
			content="编号："+ea.getId()+"提现申请已通过审核并打款，72小时内到账，请注意查收！";
		}else if(e.getEnchashmentStatus()==2){//驳回---将提现金额重回打入用户账户
			updateUserCoinForEnchashment(e);
			String cause = "";
			switch (ea.getCauseType()) {
			case 0:
				cause = "未能与提现人取得联系";
				break;
			case 1:
				cause = "支付宝账号有误";
				break;
			case 2:
				cause = "微信账号有误";
				break;
			case 3:
				cause = "手机号有误";
				break;
			case 4:
				cause = "姓名有误";
				break;
			case 5:
				cause = "其他";
				break;
			default:
				break;
			}
			content="编号："+e.getId()+"提现申请已被驳回，驳回原因："+cause+"--"+e.getOperateRemark();
		}
		messageShortVo.setContext(content);
		messageDao.saveMessage(messageShortVo);
		
	}
	
	private void updateUserCoinForEnchashment(EnchashmentApplication ea) {
		DetachedCriteria dc1 = DetachedCriteria.forClass(UserCoinConsumption.class);
		dc1.add(Restrictions.eq("orderNoEnchashment", ea.getId().toString()));
		UserCoinConsumption ucc = dao.findEntity(dc1);
		if(ucc==null){
			throw new RuntimeException("提现申请数据异常");
		}
		UserCoinIncrease uci = new UserCoinIncrease();
		uci.setUserId(ea.getUserId());
//		uci.setValue(ea.getEnchashmentSum().subtract(ucc.getValue()));//从代币消费表中取出提现时扣除的数据，并冲回用户代币余额
		uci.setValue(ucc.getValue().negate());//从代币消费表中取出提现时扣除的数据，并冲回用户代币余额
		uci.setChangeType(5);
		uci.setOrderFrom(4);
		StringBuffer sql = new StringBuffer("UPDATE user_coin uc SET");
		sql.append(" uc.`balance_reward_gift`=uc.`balance_reward_gift`-"+ ucc.getBalanceRewardGift().toString()+",");
		sql.append(" uc.`balance`=uc.`balance`-"+ ucc.getBalanceValue().toString());
		sql.append(", uc.`version`=\"" + BeanUtil.getUUID() + "\"");
		sql.append(", uc.`update_time` = now() ");
		sql.append("where user_id=\"" + uci.getUserId() + "\"");
		
		int updateCount = dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql.toString());
		if (updateCount < 1) {// 若更新失败，则继续回调该方法
//			updateUserCoinForEnchashment(ea);
			throw new RuntimeException("系统繁忙请稍后再试！");
		}

		DetachedCriteria dc = DetachedCriteria.forClass(UserCoin.class);
		dc.add(Restrictions.eq("userId", uci.getUserId()));
		UserCoin uc = dao.findEntity(dc);

		uci.setUserCoinId(uc.getId());
		uci.setBalance(uc.getBalance());
		uci.setBalanceGive(uc.getBalanceGive());
		uci.setBalanceRewardGift(uc.getBalanceRewardGift());
		uci.setCreateTime(new Date());
		uci.setPayType(3);//其他
		uci.setDeleted(false);
		uci.setStatus(true);
		uci.setOrderNoReject(ea.getId().toString());//存入对应的提现单号
		if (uci.getBrokerageValue() == null) {
            uci.setBrokerageValue(BigDecimal.ZERO);
        }
		dao.save(uci);
	}

}
