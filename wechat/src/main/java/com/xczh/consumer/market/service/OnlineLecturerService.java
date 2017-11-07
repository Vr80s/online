package com.xczh.consumer.market.service;

import com.xczh.consumer.market.vo.LecturVo;

import java.sql.SQLException;
import java.util.List;

/**
 * 微信端讲师service
 * ClassName: OnlineLecturerService.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月10日<br>
 */
public interface OnlineLecturerService {

	public List<LecturVo> findLecturerById(Integer courseId) throws SQLException;

}
