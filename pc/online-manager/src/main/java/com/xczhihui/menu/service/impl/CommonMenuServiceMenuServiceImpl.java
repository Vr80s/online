package com.xczhihui.menu.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.MenuCourseType;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.menu.dao.CommonMenuDao;
import com.xczhihui.menu.service.CommonMenuService;
import com.xczhihui.course.dao.ScoreTypeDao;
import com.xczhihui.course.vo.MenuVo;

/**
 * CommonMenuServiceMenuServiceImpl:公共学科层接口实现类
 * 
 * @author yxd
 */
@Service
public class CommonMenuServiceMenuServiceImpl extends OnlineBaseServiceImpl
		implements CommonMenuService {

	@Autowired
	private CommonMenuDao commonMenuDao;

	@Autowired
	private ScoreTypeDao scoreTypeDao;

	/**
	 * 获取全部一级与二级菜单
	 * 
	 * @return 菜单集合
	 */
	/*
	 * @Override public List<MenuVo> getAllMenu(String name,Integer
	 * pageNumber,Integer pageSize) { List<MenuVo> resultList =new
	 * ArrayList<>(); List<Menu> firstMenu = this.getAllFirstMenu("", null,
	 * null); if (!CollectionUtils.isEmpty(firstMenu)) { for (Menu m :
	 * firstMenu) { MenuVo vo = new MenuVo(); vo.setNumber(m.getNumber());
	 * vo.setName(m.getName()); vo.setType(m.getType()); List<Menu> secondMenu =
	 * this.getAllSecondMenuByIndex("",m.getNumber(), null, null);
	 * vo.setSencodMenu(secondMenu); resultList.add(vo); } } return resultList;
	 * }
	 */

	@Override
	public Page<MenuVo> findMenuPage(MenuVo menuVo, Integer pageNumber,
			Integer pageSize) {
		Page<MenuVo> page = commonMenuDao.findCloudClassMenuPage(menuVo,
				pageNumber, pageSize);
		return page;
	}

	@Override
	public Integer getMaxSort() {
		return commonMenuDao.getMaxSort();
	}

	@Override
	public Integer getMaxYunSort() {
		// TODO Auto-generated method stub
		return commonMenuDao.getMaxYunSort();
	}

	@Override
	public Integer getMaxBoSort() {
		return commonMenuDao.getMaxBoSort();
	}

	@Override
	public Integer getMaxNumber(Integer level) {
		return commonMenuDao.getMaxNumber(level);
	}

	@Override
	public Integer getMaxNumber(Integer parentId, Integer level) {
		Menu parentMenu = commonMenuDao.findById(parentId);
		return commonMenuDao.getMaxNumber(parentMenu.getNumber(), 2);
	}

	@Override
	public Integer getMaxSort(Integer parentId, Integer level) {
		Menu parentMenu = commonMenuDao.findById(parentId);
		return commonMenuDao.getMaxSort(parentMenu.getNumber(), 2);
	}

	@Override
	public List<MenuVo> list() {
		String sql = "select * from oe_menu where is_delete = 0 and name <> '全部' and status=1 order by sort";
		Map<String, Object> params = new HashMap<String, Object>();
		List<MenuVo> voList = commonMenuDao.findEntitiesByJdbc(MenuVo.class,
				sql, params);
		return voList;
	}

	@Override
	public Menu findMenuByName(String name) {
		return commonMenuDao.findOneEntitiyByProperty(Menu.class, "name", name);
	}

	@Override
	public Menu find(Menu menu) {
		return commonMenuDao.find(menu);
	}

	@Override
	public Menu findById(Integer id) {
		return commonMenuDao.findById(id);
	}

	@Override
	public boolean exists(Menu menu) {
		// 输入了一个名称 这个名称数据库已经存在了
		Menu she = commonMenuDao.findByNotEqId(menu);
		if (she != null) {
			return true;
		}
		return false;
	}

	@Override
	public void save(Menu menu) {
		commonMenuDao.save(menu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CloudClassMenuService#deletes(java.lang.String[])
	 */
	@Override
	public String deletes(String[] ids) {
		// commonMenuDao.deletes(ids);
		String msg = "";
		for (String id : ids) {
			msg = commonMenuDao.deleteById(id);
		}
		return msg;
	}

	@Override
	public void update(Menu menu) {
		commonMenuDao.update(menu);
	}

	@Override
	public void updateStatus(String id) {
		Menu menu = commonMenuDao.findById(Integer.parseInt(id));
		if (menu.getStatus() != null && menu.getStatus() == 1) {
			menu.setStatus(0);
		} else {
			menu.setStatus(1);
		}
		commonMenuDao.update(menu);
	}

	@Override
	public void updateDirectionUp(String id) {
		Menu preMenu = commonMenuDao.updateDirectionUp(Integer.parseInt(id));
		Menu me = commonMenuDao.findById(Integer.parseInt(id));
		Integer meSort = me.getSort();
		me.setSort(preMenu.getSort());
		preMenu.setSort(meSort);
		commonMenuDao.update(preMenu);
		commonMenuDao.update(me);
	}

	@Override
	public void updateChildrenMenuDirectionUp(String pid, String id,
			Integer level) {
		Menu parentMenu = commonMenuDao.findById(Integer.parseInt(pid));
		Menu preMenu = commonMenuDao.updateDirectionUp(parentMenu.getNumber(),
				Integer.parseInt(id), level);
		Menu me = commonMenuDao.findById(Integer.parseInt(id));
		Integer meSort = me.getSort();
		me.setSort(preMenu.getSort());
		preMenu.setSort(meSort);
		commonMenuDao.update(preMenu);
		commonMenuDao.update(me);

	}

	@Override
	public void updateDirectionDown(String id) {
		Menu downMenu = commonMenuDao.updateDirectionDown(Integer.parseInt(id));
		Menu me = commonMenuDao.findById(Integer.parseInt(id));
		Integer meSort = me.getSort();
		me.setSort(downMenu.getSort());
		downMenu.setSort(meSort);
		commonMenuDao.update(downMenu);
		commonMenuDao.update(me);
	}

	@Override
	public void updateChildrenMenuDirectionDown(String pid, String id,
			Integer level) {
		Menu parentMenu = commonMenuDao.findById(Integer.parseInt(pid));
		Menu downMenu = commonMenuDao.updateDirectionDown(
				parentMenu.getNumber(), Integer.parseInt(id), level);
		Menu me = commonMenuDao.findById(Integer.parseInt(id));
		Integer meSort = me.getSort();
		me.setSort(downMenu.getSort());
		downMenu.setSort(meSort);
		commonMenuDao.update(downMenu);
		commonMenuDao.update(me);
	}

	@Override
	public List<Menu> findChildrenByNumber(Integer number, Integer level) {
		return commonMenuDao.findChildrenByNumber(number, level);
	}

	/**
	 * 获取全部一级菜单
	 * 
	 * @return
	 */
	public List<Menu> getAllFirstMenu(String name, Integer pageNumber,
			Integer pageSize) {
		pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 20 : pageSize;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuffer and = new StringBuffer();
		if (StringUtils.hasText(name)) {
			and.append(" and name = :name ");
			paramMap.put("name", name);
		}

		Page<Menu> page = dao.findPageByHQL("from Menu where 1=1 and level=1 "
				+ and, paramMap, pageNumber, pageSize);
		return page.getItems();
	}

	/**
	 * 获取二级菜单，根据一级菜单编号
	 * 
	 * @return
	 */
	public List<Menu> getAllSecondMenuByIndex(String name, Integer number,
			Integer pageNumber, Integer pageSize) {
		pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 20 : pageSize;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuffer and = new StringBuffer();
		if (StringUtils.hasText(name)) {
			and.append(" and name like :name");
			paramMap.put("name", name);
		}
		String sql = "";
		if (number == 10) {
			sql = "from Menu where 1=1 and isDelete=0 and level=2 and length(number)<=3  and number like '"
					+ number + "%'" + and;
		} else {

			sql = "from Menu where 1=1 and isDelete=0 and level=2 and number like '"
					+ number + "%'" + and;
		}
		Page<Menu> page = dao
				.findPageByHQL(sql, paramMap, pageNumber, pageSize);
		return page.getItems();
	}

	@Override
	public List<ScoreType> findScoreType(String menuId) {
		return commonMenuDao.findScoreType(menuId);
	}

	@Override
	public void saveMenuCourseType(MenuCourseType menuCourseType) {
		commonMenuDao.save(menuCourseType);

	}

	@Override
	public String addMenuCourseType(String id, int menuId) {
		MenuCourseType mt = new MenuCourseType();
		mt.setMenuId(menuId);
		mt.setCourseTypeId(id);
		mt.setDelete(false);
		// mt.setCreatePerson(ManagerUserUtil.getId());
		mt.setCreateTime(new Date());
		commonMenuDao.save(mt);
		return "添加成功！";
	}

	@Override
	public void removeMenuCourseType(Integer menuId) {
		commonMenuDao.removeMenuCourseType(menuId);
	}

	@Override
	public void updateScoreTypeUp(String id) {
		ScoreType scoreType = scoreTypeDao.updateScoreTypeUp(id);
		ScoreType me = scoreTypeDao.searchById(id);
		Integer meSort = me.getSort();
		me.setSort(scoreType.getSort());
		scoreType.setSort(meSort);
		commonMenuDao.update(scoreType);
		commonMenuDao.update(me);

	}

	@Override
	public void updateScoreTypeDown(String id) {
		ScoreType scoreType = scoreTypeDao.updateScoreTypeDown(id);
		ScoreType me = scoreTypeDao.searchById(id);
		Integer meSort = me.getSort();
		me.setSort(scoreType.getSort());
		scoreType.setSort(meSort);
		commonMenuDao.update(scoreType);
		commonMenuDao.update(me);

	}

	@Override
	public Integer getMinSort() {
		// TODO Auto-generated method stub
		return commonMenuDao.getMinSort();
	}

	@Override
	public Integer getMinBoSort() {
		// TODO Auto-generated method stub
		return commonMenuDao.getMinBoSort();
	}

	@Override
	public Integer getMinYunSort() {
		// TODO Auto-generated method stub
		return commonMenuDao.getMinYunSort();
	}

}
