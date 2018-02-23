package com.xczhihui.medical.anchor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.anchor.service.IGiftOrderService;
import com.xczhihui.medical.anchor.vo.UserCoinIncreaseVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

public class GiftOrderServiceImplTest extends BaseJunit4Test {

    @Autowired
    private IGiftOrderService service;

    @Test
    public void testList(){

        Page<UserCoinIncreaseVO> page = new Page<>();
        page.setCurrent(1);
        page.setSize(3);

        Page<UserCoinIncreaseVO> userCoinIncreaseVOS =
                service.list("23908ae85dad4541ba7ecf53fc52aab2", page);

        Assert.assertNotNull(userCoinIncreaseVOS);
    }


}