package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.Reward;
import com.xczh.consumer.market.bean.RewardStatement;

import java.util.List;

public interface RewardService {

    void insert(RewardStatement rewardStatement);

    List<Reward> listAll();

    Reward findById(String id);
}
