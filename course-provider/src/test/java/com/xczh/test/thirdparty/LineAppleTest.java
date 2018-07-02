package com.xczh.test.thirdparty;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xczhihui.course.model.LineApply;
import com.xczhihui.course.service.ILineApplyService;

import test.BaseJunit4Test;

/**
 * 医馆入驻测试类
 */
public class LineAppleTest extends BaseJunit4Test {


    @Autowired
    public ILineApplyService lineApplyServiceImpl;

    /**
     * 测试观看记录了
     */
    @Test
    public void saveLineApply() {


        LineApply lineApply = new LineApply();
        lineApply.setId("63dd1acaddf0469b8155c876da3e63e3");
        lineApply.setUserId("123");
        lineApply.setCourseId(621);
        lineApply.setUpdateTime(new Date());
        lineApply.setCreateTime(new Date());
        lineApply.setRealName("社会我平哥");
        lineApply.setSex(0);
        lineApply.setMobile("16639145471");
        lineApply.setWechatNo("www45399975");
        lineApplyServiceImpl.saveOrUpdate(lineApply);
    }
}