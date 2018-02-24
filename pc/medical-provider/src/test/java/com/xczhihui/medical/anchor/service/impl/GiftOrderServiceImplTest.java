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

//        Page<UserCoinIncreaseVO> userCoinIncreaseVOS =
//                service.list("ff80808161c0dd000161c17b63490000", page, null, null, null);

//        Page<UserCoinIncreaseVO> userCoinIncreaseVOS =
//                service.list("ff80808161c0dd000161c17b63490000", page,
//                        "测试", null, null);

        Page<UserCoinIncreaseVO> userCoinIncreaseVOS =
                service.list("ff80808161c0dd000161c17b63490000", page,
                        null, "2018-02-29 12:12:12", null);

        Assert.assertNotNull(userCoinIncreaseVOS);
    }


}