package com.xczh.consumer.market.service;

import com.xczh.consumer.market.vo.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OLCourseServiceI {

	public List<WxcpOeMenuVo> categoryList() throws Exception;
	public List<WxcpCourseVo> courseListByCategory(int menu_id, int pageNumber, int pageSize)throws Exception;
	public WxcpCourseVo courseDetail1(int course_id, String user_id)throws Exception;
	public WxcpCourseVo courseDetail2(int course_id)throws Exception;
	public WxcpCourseVo courseOrderDetail(int course_id)throws Exception;
	public List<ChapterVo> videoTrailersList(int course_id)throws Exception;

	public List<MyCourseVo> myCourseList(String user_id, int number, int pageSize)throws Exception;

	public List<ChapterVo> videoLookList(int course_id) throws Exception;
	List<Map<String, Object>> categoryXCList() throws Exception;
	List<CourseLecturVo> courseXCListByCategory(String menu_id, int number,
                                                int pageSize, Integer multimedia_type) throws Exception;
	/**
	 * xc 课程详情页面
	 * Description：
	 * @param parseInt
	 * @return
	 * @return CourseLecturVo
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public CourseLecturVo bunchDetailsByCourseId(int course_id)throws SQLException;
	public List<CourseLecturVo> courseCategoryXCList1(int i, int j,
                                                      String queryParam)throws Exception;
	/**
	 * 线下培训班
	 * Description：
	 * @param keyWord
	 * @param number
	 * @param pageSize
	 * @return
	 * @return List<CourseLecturVo>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public List<CourseLecturVo> offLineClass(String keyWord, int number,
			int pageSize)throws SQLException;

	/**
	 * 线下培训班列表
	 * Description：
	 * @param number
	 * @param pageSize
	 * @return
	 * @return List<CourseLecturVo>
	 * @author name：liutao
	 *
	 */
	public List<CourseLecturVo> offLineClassList( int number,
											 int pageSize)throws SQLException;

	/**
	 * 线下培训班详情
	 * Description：
	 * @return
	 * @return List<CourseLecturVo>
	 * @author name：liutao
	 *
	 */
	CourseLecturVo offLineClassItem( Integer id,String userId)throws SQLException;
	
	public List<CourseLecturVo> recommendCourseList(int i, int j, String str,List<MenuVo> listmv) throws SQLException;
}
