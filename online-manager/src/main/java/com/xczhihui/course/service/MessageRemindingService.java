package com.xczhihui.course.service;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/5/9 0009-下午 1:43<br>
 */
public interface MessageRemindingService {
	
//    void saveCourseMessageReminding(CouserMessagePushVo course, String key);
//
//    void deleteCourseMessageReminding(CouserMessagePushVo course, String key);
//
//    List<CouserMessagePushVo> getCourseMessageRemindingList(String key);

    void liveCourseMessageReminding();

    void offlineCourseMessageReminding();

    void collectionUpdateRemind();
}
