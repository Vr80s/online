package com.xczhihui.course.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.xczhihui.course.mapper.FocusMapper;
import com.xczhihui.course.model.Focus;
import com.xczhihui.course.service.IFocusService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.support.lock.Lock;
import com.xczhihui.course.vo.FocusVo;

/**
 * <p>
 *  服务实现类
 * </p>
 * @author yuxin
 * @since 2017-12-09
 */
@Service("focusServiceRemote")
public class FocusServiceImpl extends ServiceImpl<FocusMapper,Focus> implements IFocusService {

	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FocusServiceImpl.class);
	
	@Autowired
	private FocusMapper focusMapper;

	@Override
	public List<Integer> selectFocusAndFansCount(String userId) {
		return focusMapper.selectFocusAndFansCount(userId);
	}

	@Override
	public List<FocusVo> selectFocusList(String userId) {
		return focusMapper.selectFocusList(userId);
	}

	@Override
	public List<FocusVo> selectFansList(String userId) {
		return focusMapper.selectFansList(userId);
	}

	@Override
	public Integer isFoursLecturer(String userId, String lecturerId) {
		return focusMapper.isFoursLecturer(userId,lecturerId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Lock(lockName = "updateFocusHost",waitTime = 5,effectiveTime = 8)
	public void updateFocus(String lockId,String lecturerId, String userid, Integer type) {
		
		try {
			Focus f = focusMapper.findFoursByUserIdAndlecturerId(userid,lecturerId);
			if(type !=null && type == 1){//增加关注
				if(f!=null){
					//return "你已经关注过了";
					throw new RuntimeException("你已经关注过了!");
					//return "你已经关注过了";
				}
				f= new Focus();
				f.setId(UUID.randomUUID().toString().replace("-", ""));
				f.setUserId(userid);
				f.setLecturerId(lecturerId);
				f.setCreateTime(new Date());
				focusMapper.insert(f);
			}else if(type !=null && type == 2){
				if(f==null){
					//return "你还未关注此主播";
					throw new RuntimeException("你还未关注此主播!");
				}
				focusMapper.deleteById(f.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("操作过于频繁!");
		}
	}
}
