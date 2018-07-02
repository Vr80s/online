package com.xczhihui.bxg.online.web.service;

import java.io.IOException;
import java.util.List;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import com.xczhihui.bxg.online.common.domain.Reward;
import com.xczhihui.bxg.online.common.domain.RewardStatement;

public interface RewardService {

    void insert(RewardStatement rewardStatement) throws XMPPException, SmackException, IOException;

    List<Reward> findAll();

    Reward findRewardById(String rewardId);
}
