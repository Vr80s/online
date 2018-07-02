package com.xczhihui.course.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.course.mapper.MobileHotSearchMapper;
import com.xczhihui.course.model.MobileHotSearch;
import com.xczhihui.course.service.IMobileHotSearchService;


@Service
public class MobileHotSearchServiceImpl extends ServiceImpl<MobileHotSearchMapper, MobileHotSearch> implements IMobileHotSearchService {

    @Autowired
    private MobileHotSearchMapper mobileHotSearchMapper;

    @Override
    public List<MobileHotSearch> HotSearchList(Integer searchType) {
        return mobileHotSearchMapper.HotSearchList(searchType);
    }
}
