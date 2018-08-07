package com.xczhihui.course.service;

import com.xczhihui.course.model.MobileAdvertisement;

public interface IMobileAdvertisementService {

    MobileAdvertisement selectMobileAdvertisement(String source);

    /**
     * Description：增加点击次数
     * creed: Talk is cheap,show me the code
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/8/7 17:07
     **/
    void addClickNum(Integer id);


}
