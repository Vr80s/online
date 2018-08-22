package com.xczhihui.common.support.service;

import java.util.List;

import com.xczhihui.common.support.domain.CouserMessagePushVo;

/**
 * 缓存接口
 *
 * @author liyong
 */
public interface XcRedisCacheService {

	void saveCourseMessageReminding(CouserMessagePushVo course, String key);

    void deleteCourseMessageReminding(CouserMessagePushVo course, String key);

    List<CouserMessagePushVo> getCourseMessageRemindingList(String key);
}
