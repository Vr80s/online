package com.xczhihui.bxg.online.manager.cloudClass.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.shiro.util.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Project;
import com.xczhihui.bxg.online.common.domain.Chapter;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Project;
import com.xczhihui.bxg.online.common.domain.QuesStore;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.manager.cloudClass.dao.ProjectTypeDao;
import com.xczhihui.bxg.online.manager.cloudClass.dao.QuestionDao;
import com.xczhihui.bxg.online.manager.cloudClass.service.ProjectTypeService;
import com.xczhihui.bxg.online.manager.cloudClass.service.QuestionService;
import com.xczhihui.bxg.online.manager.cloudClass.util.ParseUtil;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.QuestionOptionPicture;
import com.xczhihui.bxg.online.manager.cloudClass.vo.TreeNode;
import com.xczhihui.bxg.online.manager.common.util.ExcelPoiUtil;
import com.xczhihui.bxg.online.manager.utils.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 题库service实现类
 * 
 * @author snow
 */
@Service("projectTypeService")
public class ProjectTypeServiceImpl extends OnlineBaseServiceImpl implements ProjectTypeService {
	@Autowired
	private ProjectTypeDao projectTypeDao;

	@Override
	public Page<Project> findProjectPage(Project project, int pageNumber,
			int pageSize) {
		Page<Project> page = projectTypeDao.findProjectPage(project, pageNumber, pageSize);
		return page;
	}

	@Override
	public Project findProjectTypeByNameAndByType(String name,Integer type) {
		DetachedCriteria dc = DetachedCriteria.forClass(Project.class);
		dc.add(Restrictions.eq("name", name));
		dc.add(Restrictions.eq("type", type));
		return projectTypeDao.findEntity(dc);
		//return projectTypeDao.findOneEntitiyByProperty(Project.class,"name",name);
	}

	@Override
	public int getMaxSort() {
		return projectTypeDao.getMaxSort();
	}

	@Override
	public void save(Project project) {
		projectTypeDao.save(project);
	}
	
	@Override
	public void update(Project project) {
		projectTypeDao.update(project);
	}
	
	@Override
	public Project findById(String parseInt) {
		return projectTypeDao.findById(parseInt);
	}

	@Override
	public boolean exists(Project existsEntity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String deletes(String[] _ids) {
		String msg = "";
        for(String id:_ids){
        	msg = projectTypeDao.deleteById(id);
        }
        return  msg;
	}

	@Override
	public void updateStatus(String id) {
		// TODO Auto-generated method stub
		Project scoreType=projectTypeDao.findById(id);
        if(scoreType.getStatus()!=null&&scoreType.getStatus()==1){
        	scoreType.setStatus(0);
        }else{
        	scoreType.setStatus(1);
        }
        projectTypeDao.update(scoreType);
	}

	@Override
	public void updateSortUp(Integer id) {
		 String hqlPre="from Project where  isDelete=0 and status = 1 and id = ?";
		 Project ProjectPre= dao.findByHQLOne(hqlPre,new Object[] {id});
         Integer ProjectPreSort=ProjectPre.getSort();
         
         String hqlNext="from Project where sort < (select sort from Project where id= ? )  and isDelete=0 and status = 1 order by sort desc";
         Project ProjectNext= dao.findByHQLOne(hqlNext,new Object[] {id});
         Integer ProjectNextSort=ProjectNext.getSort();
         
         ProjectPre.setSort(ProjectNextSort);
         ProjectNext.setSort(ProjectPreSort);
         
         dao.update(ProjectPre);
         dao.update(ProjectNext);
	}

	@Override
	public void updateSortDown(Integer id) {
		 String hqlPre="from Project where  isDelete=0 and status = 1 and id = ?";
		 Project ProjectPre= dao.findByHQLOne(hqlPre,new Object[] {id});
         Integer ProjectPreSort=ProjectPre.getSort();
         String hqlNext="from Project where sort > (select sort from Project where id= ? )  and isDelete=0 and status = 1 order by sort asc";
         Project ProjectNext= dao.findByHQLOne(hqlNext,new Object[] {id});
         Integer ProjectNextSort=ProjectNext.getSort();
         
         ProjectPre.setSort(ProjectNextSort);
         ProjectNext.setSort(ProjectPreSort);
         
         dao.update(ProjectPre);
         dao.update(ProjectNext);
	}
	
}
