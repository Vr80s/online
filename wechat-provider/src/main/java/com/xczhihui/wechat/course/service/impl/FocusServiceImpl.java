package com.xczhihui.wechat.course.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.wechat.course.mapper.FocusMapper;
import com.xczhihui.wechat.course.model.Focus;
import com.xczhihui.wechat.course.service.IFocusService;
import com.xczhihui.wechat.course.vo.FocusVo;

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
		// TODO Auto-generated method stub
		return focusMapper.selectFocusAndFansCount(userId);
	}

	@Override
	public List<FocusVo> selectFocusList(String userId) {
		// TODO Auto-generated method stub
		return focusMapper.selectFocusList(userId);
	}

	@Override
	public List<FocusVo> selectFansList(String userId) {
		// TODO Auto-generated method stub
		return focusMapper.selectFansList(userId);
	}

	@Override
	public Integer isFoursLecturer(String userId, String lecturerId) {
		// TODO Auto-generated method stub
		return focusMapper.isFoursLecturer(userId,lecturerId);
	}

	@Override
	public String updateFocus(String lecturerId, String userid, Integer type) {
		// TODO Auto-generated method stub
		try {
			Focus f = focusMapper.findFoursByUserIdAndlecturerId(userid,lecturerId);
			if(type !=null && type == 1){//增加关注
				if(f!=null){
					//return "你已经关注过了";
					throw new RuntimeException("你已经关注过了!");
				}
				f= new Focus();
				f.setId(UUID.randomUUID().toString().replace("-", ""));
				f.setUserId(userid);
				f.setLecturerId(lecturerId);
				f.setCreateTime(new Date());
				LOGGER.info("userid:"+userid+",lecturerId:"+lecturerId);
				focusMapper.insert(f);
			}else if(type !=null && type == 2){
				if(f==null){
					//return "你还未关注此主播";
					throw new RuntimeException("你还未关注此主播!");
				}
				focusMapper.deleteById(f.getId());
			}
			return "操作成功!";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("操作失败!");
		}
	}

	
	
}
