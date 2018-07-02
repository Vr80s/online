package com.xczhihui.course.service;

import java.util.List;

import com.xczhihui.course.model.MobileHotSearch;

public interface IMobileHotSearchService {

    /**
     * Description：获取热门搜索列表  searchType：1 搜索框 2 热门搜索
     * creed: Talk is cheap,show me the code
     *
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/5/10 10:07
     **/
    public List<MobileHotSearch> HotSearchList(Integer searchType);
}
