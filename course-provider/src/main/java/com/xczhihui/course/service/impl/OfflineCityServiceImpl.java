package com.xczhihui.course.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.util.enums.BannerType;
import com.xczhihui.common.util.enums.PagingFixedType;
import com.xczhihui.course.mapper.MobileBannerMapper;
import com.xczhihui.course.mapper.OfflineCityMapper;
import com.xczhihui.course.model.MobileBanner;
import com.xczhihui.course.model.OfflineCity;
import com.xczhihui.course.service.IMobileBannerService;
import com.xczhihui.course.service.IOfflineCityService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class OfflineCityServiceImpl extends ServiceImpl<OfflineCityMapper, OfflineCity> implements IOfflineCityService {

    @Autowired
    private OfflineCityMapper iOfflineCityMapper;
    
    @Autowired
    private MobileBannerMapper iMobileBannerMapper;
    
    @Autowired
    private IMobileBannerService mobileBannerService;

    @Override
    public Page<OfflineCity> selectOfflineCityPage(Page<OfflineCity> page) {
        List<OfflineCity> records = iOfflineCityMapper.selectOfflineCityPage(page);
        return page.setRecords(records);
    }

    @Override
    public Page<OfflineCity> selectOfflineRecommendedCityPage(
            Page<OfflineCity> page) {
        List<OfflineCity> records = iOfflineCityMapper.selectOfflineRecommendedCityPage(page);
        return page.setRecords(records);
    }

	@Override
	public Map<String, Object> getOffLine(int clientType, Boolean onlyThread, String mobileSource) {
		
		
	    Map<String, Object> mapAll = new HashMap<String, Object>();
        //线下课banner
        Page<MobileBanner> MobileBannerPage = new Page<>();
        MobileBannerPage.setRecords(mobileBannerService.selectMobileBannerPage(BannerType.REAL.getCode(),
        		onlyThread, mobileSource, clientType));
        mapAll.put("banner", MobileBannerPage);
        
        //城市
        Page<OfflineCity> offlineCityPage = new Page<>();
        offlineCityPage.setCurrent(1);
        offlineCityPage.setSize(PagingFixedType.OFFLINE_CITY_RECOMMEND.getValue());
        
        List<OfflineCity> list = iOfflineCityMapper.selectOfflineRecommendedCityPage(offlineCityPage);
        mapAll.put("cityList", offlineCityPage.setRecords(list));

        List<OfflineCity> ocl = new ArrayList<OfflineCity>();
        for (int i = 0; i < PagingFixedType.OFFLINE_CITY_COURSE_RECOMMEND.getValue(); i++) {
        	ocl.add(list.get(i));
		}
        
        //城市  城市中的课程
        List<Map<String, Object>> mapCourseList = mobileBannerService.realCourseList(ocl, PagingFixedType.PC_REAL_PAGETYPE_UP.getValue(),
                PagingFixedType.PC_REAL_PAGETYPE_DOWN.getValue(),onlyThread);
        
        mapAll.put("allCourseList", mapCourseList);
        
		return mapAll;
	}

	@Override
	public String getClassIfyOffLine() {
		List<OfflineCity> records = iOfflineCityMapper.selectOfflineCityPage(new Page<OfflineCity>(1,PagingFixedType.OFFLINE_CITY_CLASSIFY_RECOMMEND.getValue()));
		return records.stream().map(OfflineCity::getCityName).collect(Collectors.joining(", "));
	}
	
}
