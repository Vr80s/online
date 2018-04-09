package com.xczhihui.cloudClass.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.TeachMethod;
import com.xczhihui.cloudClass.vo.TeachMethodVo;

import java.util.List;

/**
 * MenuService:菜单业务层接口类 * @author Rongcai Kang
 */
public interface TeachMethodService {

	/**
	 * 查询授课方式列表
	 * 
	 * @param teachMethodVo
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Page<TeachMethodVo> findTeachMethodPage(TeachMethodVo teachMethodVo,
			Integer pageNumber, Integer pageSize);

	/**
	 * 查询授课方式名称是否存在
	 * 
	 * @param name
	 * @return
	 */
	public List<TeachMethod> findTeachMethodByName(String name);

	/**
	 * 查询数据库中最大的sort
	 * 
	 * @return
	 */
	public int getMaxSort();

	/**
	 * 保存实体
	 * 
	 * @param entity
	 */
	public void save(TeachMethod entity);

	/**
	 * 通过id进行查找
	 * 
	 * @param id
	 * @return
	 */
	public TeachMethod findById(String id);

	public List<TeachMethodVo> list();

	/**
	 * 通过id进行查找
	 * 
	 * @param courseId
	 * @return
	 */
	public TeachMethod findByCourseId(String courseId);

	/**
	 * 判断实体是否存在
	 * 
	 * @param searchEntity
	 * @return
	 */
	public boolean exists(TeachMethod searchEntity);

	/**
	 * 更新实体
	 * 
	 * @param me
	 */
	public void update(TeachMethod me);

	/**
	 * 更新状态
	 * 
	 * @param id
	 */
	public void updateStatus(String id);

	/**
	 * 删除数据
	 * 
	 * @param _ids
	 */
	public void deletes(String[] _ids);
}
