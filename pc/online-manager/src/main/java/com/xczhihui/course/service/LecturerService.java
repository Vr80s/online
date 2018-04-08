package com.xczhihui.course.service;

import java.util.List;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Lecturer;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.course.vo.LecturerVo;

/**
 *   LecturerService:讲师业务层接口类
 * * @author Rongcai Kang
 */
public interface LecturerService {

    /**
     * 根据课程编号，查找课程实例
     * @param lecturerId 课程的ID编号
     * @return  课程对象
     */
    public Lecturer  findLecturerById(Integer  lecturerId);

	public Page<LecturerVo> findLecturerPage(LecturerVo searchVo,
			int pageNumber, int pageSize);

	public List<Menu> getfirstMenus();

	public void addLecturer(Lecturer lecturer);

	public void updateLecturer(Lecturer lecturer);
	
	public List<LecturerVo> findTeachRecordsByLecturerId(String id);

	public String deletes(String[] ids);

	public List<LecturerVo> listByGradeId(String gradeId);
	
	
}
