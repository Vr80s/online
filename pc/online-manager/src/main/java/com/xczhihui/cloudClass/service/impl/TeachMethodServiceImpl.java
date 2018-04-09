package com.xczhihui.cloudClass.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.TeachMethod;
import com.xczhihui.cloudClass.dao.TeachMethodDao;
import com.xczhihui.cloudClass.service.TeachMethodService;
import com.xczhihui.cloudClass.vo.TeachMethodVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MenuServiceImpl:菜单业务层接口实现类
 * 
 * @author Rongcai Kang
 */
@Service
public class TeachMethodServiceImpl extends OnlineBaseServiceImpl implements
		TeachMethodService {

	@Autowired
	private TeachMethodDao teachMethodDao;

	@Override
	public Page<TeachMethodVo> findTeachMethodPage(TeachMethodVo teachMethodVo,
			Integer pageNumber, Integer pageSize) {
		Page<TeachMethodVo> page = teachMethodDao.findTeachMethodPage(
				teachMethodVo, pageNumber, pageSize);
		return page;
	}

	@Override
	public List<TeachMethod> findTeachMethodByName(String name) {
		return teachMethodDao.findEntitiesByProperty(TeachMethod.class, "name",
				name);
	}

	/**
	 * 获取最大的排序
	 */
	@Override
	public int getMaxSort() {
		return teachMethodDao.getMaxSort();
	}

	/**
	 * 保存实体
	 */
	@Override
	public void save(TeachMethod entity) {
		teachMethodDao.save(entity);
	}

	@Override
	public TeachMethod findById(String id) {
		return teachMethodDao.findById(id);
	}

	@Override
	public List<TeachMethodVo> list() {
		String sql = "select * from teach_method where is_delete=0 and status=1 and id<>0 order by sort";
		Map<String, Object> params = new HashMap<String, Object>();
		List<TeachMethodVo> voList = teachMethodDao.findEntitiesByJdbc(
				TeachMethodVo.class, sql, params);
		return voList;
	}

	@Override
	public TeachMethod findByCourseId(String courseId) {
		String sql = "select * from teach_method where id =(select courseType from oe_course where id=:courseId )";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("courseId", courseId);
		List<TeachMethod> teachMethodList = teachMethodDao.findEntitiesByJdbc(
				TeachMethod.class, sql, params);
		if (teachMethodList != null && teachMethodList.size() > 0) {
			return teachMethodList.get(0);
		}
		return null;
	}

	@Override
	public boolean exists(TeachMethod searchEntity) {
		// 输入了一个名称 这个名称数据库已经存在了
		TeachMethod she = teachMethodDao.findByNotEqId(searchEntity);
		if (she != null) {
			return true;
		}
		return false;
	}

	@Override
	public void update(TeachMethod me) {
		teachMethodDao.update(me);

	}

	@Override
	public void updateStatus(String id) {
		TeachMethod teachMethod = teachMethodDao.findById(id);
		if (teachMethod.isStatus()) {
			teachMethod.setStatus(false);
		} else {
			teachMethod.setStatus(true);
		}
		teachMethodDao.update(teachMethod);
	}

	@Override
	public void deletes(String[] _ids) {
		teachMethodDao.deletes(_ids);
	}

}
