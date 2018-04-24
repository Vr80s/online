package com.xczhihui.bxg.online.web.service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.Focus;
import com.xczhihui.bxg.online.common.domain.OnlineUser;

public interface FocusService  {

    /**
     * 添加关注
     * @param focus
     * @param onlineUser
     * @param onlineLecturer
     */
    void addFocusInfo(Focus focus,OnlineUser onlineUser, OnlineUser onlineLecturer);

    /**
     *
     * Description：取消关注
     * @param lecturerId  讲师id
     * @param userId  用户id
     * @return
     * @return ResponseObject
     *
     */
    ResponseObject removeFocus(String lecturerId, String userId);

    /**
     * 我的关注
     * @param userId
     * @param number
     * @param pageSize
     * @return
     */
    Page<Focus> findMyFocus(String userId, Integer number, Integer pageSize);

    /**
     * 我的粉丝
     * @param userId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<Focus> findMyFans(String userId,Integer pageNumber,Integer pageSize);
}
