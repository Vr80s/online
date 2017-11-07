package com.xczhihui.bxg.online.manager.barrier.service;

import java.util.List;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Barrier;
import com.xczhihui.bxg.online.manager.barrier.vo.BarrierVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;

public interface BarrierService {

	public Page<BarrierVo> findBarrierPage(BarrierVo barrierVo, Integer pageNumber, Integer pageSize);
	
	/**
	 * 根据条件分页获取课程信息。
	 * 
	 * @param groups
	 * @param pageVo
	 * @return
	 */
    public Page<CourseVo> findCoursePage(CourseVo courseVo,  int pageNumber, int pageSize);
	
	/**
	 * 新增
	 * 
	 *@return void
	 */
	public void addBarrier(BarrierVo barrierVo);

	/**
	 * 修改关卡
	 * 
	 *@return void
	 */
	public void updateBarrier(BarrierVo barrierVo);
	
	/**
	 * 获取到barrier详细信息
	 * 
	 *@return void
	 */
	public BarrierVo getBarrierDetail(BarrierVo barrierVo);

	/**
	 * 逻辑批量删除
	 * 
	 *@return void
	 */
	public void deletes(String[] ids);

	/**
	 * 获取到问题
	 * 
	 *@return void
	 */
	public List getQuestions(String kpointIds);
	
	/**
	 * 获取到已有的闯关
	 * 
	 *@return void
	 */
	public List getBarriersSelect(String courseId,String id);

	/**
	 * 启用关卡
	 * @param courseId
	 */
	public void update_UseBarrier(String courseId);
}
