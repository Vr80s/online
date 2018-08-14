package com.xczhihui.course.service.impl;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.enums.ClientType;
import com.xczhihui.course.dao.EssenceRecommendDao;
import com.xczhihui.course.service.EssenceRecommendService;
import com.xczhihui.course.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("essenceRecommendServiceImpl")
public class EssenceRecommendServiceImpl extends OnlineBaseServiceImpl
        implements EssenceRecommendService {
    @Value("${vhall.callback.url}")
    String vhall_callback_url;
    @Value("${vhall.private.key}")
    String vhall_private_key;
    @Autowired
    private EssenceRecommendDao essenceRecommendDao;
    @Value("${env.flag}")
    private String envFlag;
    @Value("${vhall.user.id}")
    private String liveVhallUserId;

    @Override
    public Page<CourseVo> findCoursePage(CourseVo courseVo, int pageNumber,
                                         int pageSize) {
        Page<CourseVo> page = essenceRecommendDao.findEssenceRecCoursePage(
                courseVo, pageNumber, pageSize);
        page.getItems().forEach(data -> {
            Integer clientType = data.getClientType();
            if(clientType != null){
                String clientName = ClientType.getClientType(clientType).getText();
                data.setClientName(clientName);
            } else {
                data.setClientName("未知");
            }

        });
        return page;
    }


}
