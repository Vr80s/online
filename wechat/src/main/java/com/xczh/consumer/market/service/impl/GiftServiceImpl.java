package com.xczh.consumer.market.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczh.consumer.market.bean.Gift;
import com.xczh.consumer.market.bean.GiftStatement;
import com.xczh.consumer.market.dao.GiftMapper;
import com.xczh.consumer.market.dao.GiftStatementMapper;
import com.xczh.consumer.market.service.GiftService;
import com.xczh.consumer.market.vo.RankingUserVo;
import com.xczhihui.bxg.online.api.po.UserCoinConsumption;
import com.xczhihui.bxg.online.api.service.UserCoinService;


/**
 * @author
 * @create 2017-08-21 20:29
 **/
@Service("giftServiceLocal")
public class GiftServiceImpl implements GiftService {

    @Autowired
    private GiftMapper giftMapper;

    @Autowired
    private GiftStatementMapper giftStatementMapper;

    @Autowired
    private UserCoinService userCoinService;

    @Override
    public GiftStatement addGiftStatement(GiftStatement giftStatement) {
        Gift gift = giftMapper.get(giftStatement.getGiftId());
        if(gift == null||(gift.getIsDelete()!=null&&gift.getIsDelete()==true))throw new RuntimeException("找不到礼物！");
        giftStatement.setCreateTime(new Date());
        giftStatement.setGiftId(gift.getId()+"");
        giftStatement.setGiftName(gift.getName());
        giftStatement.setPrice(gift.getPrice());
        giftStatement.setCreateTime(new Date());
        giftStatement.setGiftImg(gift.getSmallimgPath());
        giftStatement.setPayType(1);
        giftStatement.setStatus(1);
        /*if(checkRemainder(giftStatement)){*/
            try {


                if(gift.getPrice()>0)
                    doConsumption(giftStatement);

                giftStatementMapper.addGiftStatement(giftStatement);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("操作失败！");
            }
/*        }else{
            throw new RuntimeException("余额不足！");
        }*/
        //giftStatement.setContinuousCount(gift.getContinuousCount());
        return giftStatement;
    }

    private boolean doConsumption(GiftStatement giftStatement) {
        double total = giftStatement.getCount()*giftStatement.getPrice();
        UserCoinConsumption ucc = new UserCoinConsumption();
        ucc.setValue(new BigDecimal( -total));
        ucc.setChangeType(8);//送礼物
        ucc.setUserId(giftStatement.getGiver());
        userCoinService.updateBalanceForConsumption(ucc);
        return true;
    }


    @Override
    public List<Gift> listAll(Integer pageNumber, Integer pageSize) {
        try {
            return giftMapper.listAll(pageNumber,pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    @Override
    public int findByUserId(String userId) {
        try {
            return giftMapper.findByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int findByLiveId(int liveId) {
        try {
            return giftMapper.findByLiveId(liveId);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<RankingUserVo> rankingList(String liveId, int type, int pageNumber, int pageSize) {
        try {
            return giftStatementMapper.rankingList(liveId,type,pageNumber,pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean checkRemainder(GiftStatement giftStatement) {
        // TODO Auto-generated method stub  此处为用户余额是否足以支付赠送礼物所需费用

        // TODO Auto-generated method stub  若有足够余额，则扣减

        // TODO Auto-generated method stub  若有足够余额，则为接收者增加余额
        return true;
    }
}
