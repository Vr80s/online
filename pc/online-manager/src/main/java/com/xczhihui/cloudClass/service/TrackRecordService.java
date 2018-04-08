package com.xczhihui.cloudClass.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.TrackRecord;
import com.xczhihui.cloudClass.vo.TrackRecordVo;

import java.lang.reflect.InvocationTargetException;

public interface TrackRecordService {

	/**
	 * 根据条件分页获取角色信息。
	 *
	 * @return
	 */
	public Page<TrackRecordVo> findPage(TrackRecordVo trackRecordVo, int pageNumber, int pageSize);

	public void save(TrackRecordVo vo) throws InvocationTargetException, IllegalAccessException;

	public void deleteById(String id);

	public TrackRecord findById(String id);

	public void update(TrackRecord entity);

}
