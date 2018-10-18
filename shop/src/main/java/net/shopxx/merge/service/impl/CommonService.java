/**  
* <p>Title: CommonServiceImpl.java</p>  
* <p>Description: </p>  
* @author yangxuan@ixincheng.com  
* @date 2018年10月18日 
*/  
package net.shopxx.merge.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.util.redis.key.RedisCacheKey;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;

import net.sf.json.JSONObject;
import net.shopxx.entity.Store;

/**
* @ClassName: CommonServiceImpl
* @Description: TODO(这里用一句话描述这个类的作用)
* @author yangxuan
* @email yangxuan@ixincheng.com
* @date 2018年10月18日
*
*/

@Component
public class CommonService {

     private static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);
	

     @Autowired
     private CacheService redisCacheService;
     
     @Autowired
     private IMedicalDoctorBusinessService medicalDoctorBusinessService;
     
	 public Map<String, Object> getDoctorInfoByStore(Store store){
	    	
    	 //医师推荐信息
        LOGGER.info("store.getBusiness().getDoctorId() " + store.getBusiness().getDoctorId());

        String key = RedisCacheKey.STORE_DOCTOR_RELEVANCE +
                RedisCacheKey.REDIS_SPLIT_CHAR + store.getId();

        String value = redisCacheService.get(key);
        if (value != null) {
            LOGGER.info("value :" + value);
            JSONObject jasonObject = JSONObject.fromObject(value);
            return jasonObject;
        } else {
            String doctorId = store.getBusiness().getDoctorId();
            if (doctorId != null) {
                Map<String, Object> map = medicalDoctorBusinessService.getDoctorInfoByDoctorId(doctorId);
                LOGGER.info("map tostring " + (map != null ? map.toString() : null));
                JSONObject jasonObject = JSONObject.fromObject(map);
                redisCacheService.set(key, jasonObject.toString());
                return map;
            }
            return null;
        }
    }
	
}
