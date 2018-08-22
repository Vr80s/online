/**  
* <p>Title: RedisCacheMessageRemindingUtil.java</p>  
* <p>Description: </p>  
* @author yangxuan@ixincheng.com  
* @date 2018年8月21日 
*/  
package com.xczhihui.common.support.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.common.support.domain.CouserMessagePushVo;
import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.support.service.XcRedisCacheService;
import com.xczhihui.common.util.redis.key.RedisCacheKey;

/**
* @ClassName: RedisCacheMessageRemindingUtil
* @Description: TODO(这里用一句话描述这个类的作用)
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年8月21日
*
*/
@Service("xcRedisCacheService")
public class XcRedisCacheServiceImpl implements XcRedisCacheService {

	@Autowired
	CacheService cacheService;
	
	public XcRedisCacheServiceImpl() {
        cacheService = new RedisCacheService();
    }

    public void saveCourseMessageReminding(CouserMessagePushVo course, String redisKey) {
        cacheService.set(redisKey + RedisCacheKey.REDIS_SPLIT_CHAR + course.getId(), course);
    }

    public void deleteCourseMessageReminding(CouserMessagePushVo course, String redisKey) {
        cacheService.delete(redisKey + RedisCacheKey.REDIS_SPLIT_CHAR + course.getId());
    }

    public List<CouserMessagePushVo> getCourseMessageRemindingList(String redisKey) {
        Set<String> keys = cacheService.getKeys(redisKey);
        List<CouserMessagePushVo> courses = new ArrayList<>();
        for (String key : keys) {
        	CouserMessagePushVo course = cacheService.get(key);
            courses.add(course);
        }
        return courses;
    }
	
}
