package com.xczhihui.course.service;



import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.course.vo.ScoreTypeVo;

import java.util.List;

/**
 *   MenuService:菜单业务层接口类
 * * @author Rongcai Kang
 */
public interface ScoreTypeService {
	/**
	 * 查询课程列表数据
	 * @param menuVo
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
    public Page<ScoreTypeVo> findMenuPage(ScoreTypeVo menuVo, Integer pageNumber, Integer pageSize);

    /**
     * 查询课程类别名称是否存在
     * @param name
     * @return
     */
	public ScoreType findScoreTypeByName(String name);

	public List<ScoreType> list();

	/**
	 * 查询数据库中最大的sort
	 * @return
	 */
	public int getMaxSort();

	/**
	 * 保存实体
	 * @param entity
	 */
	public void save(ScoreType entity);

	/**
	 * 判断实体是否存在
	 * @param searchEntity
	 * @return
	 */
	public boolean exists(ScoreType searchEntity);

	/**
	 * 更新课程类别
	 * @param me
	 */
	public void update(ScoreType me);

	/**
	 *通过id进行查找
	 * @param string
	 * @return
	 */
	public ScoreType findById(String string);

	/**
	 * 删除数据
	 * @param _ids
	 * @return 
	 */
	public String deletes(String[] _ids);

	/**
	 * 修改启用禁用状态
	 * @param id
	 */
	public void updateStatus(String id);

}
