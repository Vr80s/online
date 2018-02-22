package com.xczhihui.medical.anchor.service.impl;

import com.xczhihui.medical.anchor.service.IGiftOrderService;
import com.xczhihui.medical.anchor.vo.UserCoinIncreaseVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

import java.util.List;

public class GiftOrderServiceImplTest extends BaseJunit4Test {

    @Autowired
    private IGiftOrderService service;

    @Test
    public void testList(){

        List<UserCoinIncreaseVO> userCoinIncreaseVOS =
                service.list("23908ae85dad4541ba7ecf53fc52aab2");

        Assert.assertNotNull(userCoinIncreaseVOS);
    }


}