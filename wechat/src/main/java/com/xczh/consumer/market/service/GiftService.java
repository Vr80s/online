package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.Gift;
import com.xczh.consumer.market.bean.GiftStatement;
import com.xczh.consumer.market.vo.RankingUserVo;

import java.util.List;

public interface GiftService {

//    public GiftStatement addGiftStatement(GiftStatement giftStatement);

    List<Gift> listAll(Integer pageNumber, Integer pageSize);

    /**
     * 用户收到的礼物总数
     * @param userId
     * @return
     */
    int findByUserId(String userId);

    //课程收到的礼物数量

    /**
     * 课程收到的礼物总数
     * @param liveId
     * @return
     */
    public int findByLiveId(int liveId);


    /**
     * 榜单列表
     * @param liveId
     * @param type
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<RankingUserVo>  rankingList(String liveId, int type, int pageNumber, int pageSize) ;

    /**
     * 榜单列表（个人中心）
     * @return
     */
    List<RankingUserVo>  userRankingList(String userId) ;

    List<RankingUserVo>  newRankingList(String liveId, Integer current, Integer size);
}
