package com.xczhihui.cloudClass.service.impl;

import com.xczhihui.cloudClass.service.ProjectTypeService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Project;
import com.xczhihui.cloudClass.dao.ProjectTypeDao;

/**
 * 题库service实现类
 * 
 * @author snow
 */
@Service("projectTypeService")
public class ProjectTypeServiceImpl extends OnlineBaseServiceImpl implements
		ProjectTypeService {
	@Autowired
	private ProjectTypeDao projectTypeDao;

	@Override
	public Page<Project> findProjectPage(Project project, int pageNumber,
			int pageSize) {
		Page<Project> page = projectTypeDao.findProjectPage(project,
				pageNumber, pageSize);
		return page;
	}

	@Override
	public Project findProjectTypeByNameAndByType(String name, Integer type) {
		DetachedCriteria dc = DetachedCriteria.forClass(Project.class);
		dc.add(Restrictions.eq("name", name));
		dc.add(Restrictions.eq("type", type));
		return projectTypeDao.findEntity(dc);
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
		for (String id : _ids) {
			msg = projectTypeDao.deleteById(id);
		}
		return msg;
	}

	@Override
	public void updateStatus(String id) {
		// TODO Auto-generated method stub
		Project scoreType = projectTypeDao.findById(id);
		if (scoreType.getStatus() != null && scoreType.getStatus() == 1) {
			scoreType.setStatus(0);
		} else {
			scoreType.setStatus(1);
		}
		projectTypeDao.update(scoreType);
	}

	@Override
	public void updateSortUp(Integer id) {
		String hqlPre = "from Project where  isDelete=0 and status = 1 and id = ?";
		Project ProjectPre = dao.findByHQLOne(hqlPre, new Object[] { id });
		Integer ProjectPreSort = ProjectPre.getSort();

		String hqlNext = "from Project where sort < (select sort from Project where id= ? )  and isDelete=0 and status = 1 order by sort desc";
		Project ProjectNext = dao.findByHQLOne(hqlNext, new Object[] { id });
		Integer ProjectNextSort = ProjectNext.getSort();

		ProjectPre.setSort(ProjectNextSort);
		ProjectNext.setSort(ProjectPreSort);

		dao.update(ProjectPre);
		dao.update(ProjectNext);
	}

	@Override
	public void updateSortDown(Integer id) {
		String hqlPre = "from Project where  isDelete=0 and status = 1 and id = ?";
		Project ProjectPre = dao.findByHQLOne(hqlPre, new Object[] { id });
		Integer ProjectPreSort = ProjectPre.getSort();
		String hqlNext = "from Project where sort > (select sort from Project where id= ? )  and isDelete=0 and status = 1 order by sort asc";
		Project ProjectNext = dao.findByHQLOne(hqlNext, new Object[] { id });
		Integer ProjectNextSort = ProjectNext.getSort();

		ProjectPre.setSort(ProjectNextSort);
		ProjectNext.setSort(ProjectPreSort);

		dao.update(ProjectPre);
		dao.update(ProjectNext);
	}

}
