package com.xczhihui.bxg.online.api.service;



import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.vo.CriticizeVo;
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
}
