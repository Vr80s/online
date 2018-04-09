package com.xczhihui.cloudClass.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.TrackRecord;
import com.xczhihui.cloudClass.dao.TrackRecordDao;
import com.xczhihui.cloudClass.service.TrackRecordService;
import com.xczhihui.cloudClass.vo.TrackRecordVo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Service("trackRecordService")
public class TrackRecordServiceImpl extends OnlineBaseServiceImpl implements
		TrackRecordService {

	@Autowired
	private TrackRecordDao trackRecordDao;

	@Override
	public Page<TrackRecordVo> findPage(TrackRecordVo trackRecordVo,
			int pageNumber, int pageSize) {
		return trackRecordDao.findTrackRecordPage(trackRecordVo, pageNumber,
				pageSize);
	}

	@Override
	public void save(TrackRecordVo vo) throws InvocationTargetException,
			IllegalAccessException {
		TrackRecord entity = new TrackRecord();
		BeanUtils.copyProperties(entity, vo);
		trackRecordDao.save(entity);
	}

	@Override
	public void deleteById(String id) {
		String sql = "update oe_track_record set is_delete=1 where id=:id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		trackRecordDao.getNamedParameterJdbcTemplate().update(sql, params);
	}

	@Override
	public TrackRecord findById(String id) {
		return trackRecordDao.findOneEntitiyByProperty(TrackRecord.class, "id",
				id);
	}

	@Override
	public void update(TrackRecord entity) {
		trackRecordDao.update(entity);
	}
}
