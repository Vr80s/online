package com.xczhihui.bxg.online.api.service;



import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.common.support.domain.SystemVariate;
import com.xczhihui.bxg.online.api.vo.JobVo;

/**
 * 关注service
 * ClassName: FocusService.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月15日<br>
 */
public interface CommonApiService {
	/**
	 * 获取职位
	 * @param
	 * @return
	 */
	List<JobVo> getJob(String group);

	/**
	 * 获取所有的常见问题
	 * @param
	 * @return
	 */
	List<Map<String,Object>> getProblems(String group);
	
	/**
	 * 获取单个常见问题和答案
	 * @param
	 * @return
	 */
	Map<String,Object> getProblemAnswer(String id);

	
	
	
}
