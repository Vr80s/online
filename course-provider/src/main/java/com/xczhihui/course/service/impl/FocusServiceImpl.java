package com.xczhihui.course.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.support.lock.Lock;
import com.xczhihui.common.util.IStringUtil;
import com.xczhihui.course.exception.FansException;
import com.xczhihui.course.mapper.FocusMapper;
import com.xczhihui.course.mapper.MyInfoMapper;
import com.xczhihui.course.model.Focus;
import com.xczhihui.course.service.IFocusService;
import com.xczhihui.course.util.HtmlUtil;
import com.xczhihui.course.vo.FocusVo;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service("focusServiceRemote")
public class FocusServiceImpl extends ServiceImpl<FocusMapper, Focus> implements IFocusService {


    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FocusServiceImpl.class);

    @Autowired
    private FocusMapper focusMapper;
    
    @Autowired
    private MyInfoMapper myInfoMapper;

    @Override
    public List<Integer> selectFocusAndFansCount(String userId) {
        List<Integer> counts = focusMapper.selectFocusAndFansCount(userId);
        counts.set(0,counts.get(0)+counts.get(2));
        return counts;
    }

    @Override
    public List<Integer> selectFocusOrFansCountOrCriticizeCount(String userId) {
        List<Integer> counts = focusMapper.selectFocusOrFansCountOrCriticizeCount(userId);
        counts.set(0,counts.get(0)+counts.get(3));
        return counts;
    }
    
    @Override
    public List<Integer> selectFocusAndFansCountAndCourseCount(String userId) {
        List<Integer> counts = focusMapper.selectFocusAndFansCountAndCourseCount(userId);
        counts.set(0,counts.get(0)+counts.get(2));
        return counts;
    }


    @Override
    public Integer selectFocusCount(String userId) {
        return focusMapper.selectFocusCount(userId);
    }

    @Override
    public Integer selectFansCount(String userId) {
        return focusMapper.selectFansCount(userId);
    }


    @Override
    public List<FocusVo> selectFocusList(String userId) {
        List<FocusVo> focusVos = focusMapper.selectFocusList(userId);
        for (FocusVo focusVo : focusVos) {
            if (focusVo.getDetail() != null) {
                focusVo.setDetail(IStringUtil.getTop100Char(HtmlUtil.getTextFromHtml(focusVo.getDetail())));
            }
            //医师认证啦
            if(focusVo.getType().equals(1)) {
                String hostId = focusVo.getUserId();
                Map<String, String> mapHeadProtrait = myInfoMapper.selectDoctorHeadPortraitAndByUserId(hostId);
                if(mapHeadProtrait!=null) {
                    focusVo.setDoctorId(mapHeadProtrait.get("doctorId"));
                    focusVo.setSmallHeadPhoto(mapHeadProtrait.get("headPortrait"));
                }
            }
        }
        return focusVos;
    }

    @Override
    public List<FocusVo> selectFansList(String userId) {
        return focusMapper.selectFansList(userId);
    }

    @Override
    public Integer isFoursLecturer(String userId, String lecturerId) {
        return focusMapper.isFoursLecturer(userId, lecturerId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Lock(lockName = "updateFocusHost", waitTime = 5, effectiveTime = 8)
    public void updateFocus(String lockId, String lecturerId, String userid, Integer type) {

        Focus f = focusMapper.findFoursByUserIdAndlecturerId(userid, lecturerId);
        if (type != null && type == 1) {//增加关注
            if (f != null) {
                throw new FansException("你已关注过了!");
            }
            f = new Focus();
            f.setId(UUID.randomUUID().toString().replace("-", ""));
            f.setUserId(userid);
            f.setLecturerId(lecturerId);
            f.setCreateTime(new Date());
            focusMapper.insert(f);
        } else if (type != null && type == 2) {
            if (f == null) {
                throw new FansException("你还未关注此主播!");
            }
            focusMapper.deleteById(f.getId());
        }
        
    }

}
