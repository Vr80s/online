package com.xczhihui.medical.anchor.service.impl;

import com.xczhihui.medical.anchor.service.IAnchorInfoService;
import com.xczhihui.medical.anchor.vo.CourseAnchorVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.BaseJunit4Test;

public class AnchorInfoServiceImplTest extends BaseJunit4Test {

    @Autowired
    private IAnchorInfoService service;

    /**
     * 获取主播详情
     */
    @Test
    public void testDetail(){

        CourseAnchorVO courseAnchor = service.detail("402880e860c4ebe30160c51302660000");
//        CourseAnchorVO courseAnchor = service.detail("23908ae85dad4541ba7ecf53fc52aab2");

        Assert.assertNotNull(courseAnchor);
    }

    /**
     * 获取主播认证信息
     */
    @Test
    public void testAUthInfo(){

//        Object o = service.authInfo("402880e860c4ebe30160c51302660000");
        Object o = service.authInfo("23908ae85dad4541ba7ecf53fc52aab2");

        Assert.assertNotNull(o);
    }

    /**
     * 更新主播详情
     */
    @Test
    public void testUpdate(){

        CourseAnchorVO courseAnchor = new CourseAnchorVO();
        courseAnchor.setUserId("23908ae85dad4541ba7ecf53fc52aab2");
        courseAnchor.setDetail("你好");
        courseAnchor.setDetail("<p><img src=\"http://test-www.ixincheng.com:38080/data/attachment/online/2018/01/29/22/bb0c4248067e4220bd075e2979065960.jpg\" title=\"\" alt=\"a4026bccd6ae402e8434453bc68244a0.jpg\"/></p><p>张三丰为武当派开山祖师，元惠宗敕封“忠孝神仙”；明成祖敕封“犹龙六祖隐仙寓化虚微普度天尊”；明英宗赐号“通微显化真人”；明宪宗特封号为“韬光尚志真仙”；明世宗赠封他为“清虚元妙真君”；明熹宗封号“飞龙显化宏仁济世真君”。</p><p>张三丰是道家内丹祖师和道家拳术祖师，是丹道修炼的集大成者，主张“福自我求，命自我造”。张三丰所创的武学有王屋山邋遢派、三丰自然派、三丰派、三丰正宗自然派、日新派、蓬莱派、檀塔派、隐仙派、武当丹派、犹龙派等至少十七支。</p><p><br/></p>");
//        courseAnchor.setHospitalId("1");
        courseAnchor.setWorkTime("一，二，四");
        service.update(courseAnchor);
    }

}