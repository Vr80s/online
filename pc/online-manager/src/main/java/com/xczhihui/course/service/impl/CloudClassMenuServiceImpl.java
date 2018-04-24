package com.xczhihui.course.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.course.dao.CloudClassMenuDao;
import com.xczhihui.course.service.CloudClassMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.MenuCourseType;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.course.dao.ScoreTypeDao;
import com.xczhihui.course.vo.MenuVo;

/**
 * MenuServiceImpl:菜单业务层接口实现类
 *
 * @author Rongcai Kang
 */
@Service
public class CloudClassMenuServiceImpl extends OnlineBaseServiceImpl implements
		CloudClassMenuService {

	@Autowired
	private CloudClassMenuDao cloudClassMenuDao;

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
		Page<MenuVo> page = cloudClassMenuDao.findCloudClassMenuPage(menuVo,
				pageNumber, pageSize);
		return page;
	}

	@Override
	public Integer getMaxSort() {
		return cloudClassMenuDao.getMaxSort();
	}

	@Override
	public Integer getMaxYunSort() {
		return cloudClassMenuDao.getMaxYunSort();
	}

	@Override
	public Integer getMaxBoSort() {
		return cloudClassMenuDao.getMaxBoSort();
	}

	@Override
	public Integer getMaxNumber(Integer level) {
		return cloudClassMenuDao.getMaxNumber(level);
	}

	@Override
	public Integer getMaxNumber(Integer parentId, Integer level) {
		Menu parentMenu = cloudClassMenuDao.findById(parentId);
		return cloudClassMenuDao.getMaxNumber(parentMenu.getNumber(), 2);
	}

	@Override
	public Integer getMaxSort(Integer parentId, Integer level) {
		Menu parentMenu = cloudClassMenuDao.findById(parentId);
		return cloudClassMenuDao.getMaxSort(parentMenu.getNumber(), 2);
	}

	@Override
	public List<MenuVo> list() {
		String sql = "select * from oe_menu where is_delete = 0 and name <> '全部' and status=1  and yun_status=1 order by sort";
		Map<String, Object> params = new HashMap<String, Object>();
		List<MenuVo> voList = cloudClassMenuDao.findEntitiesByJdbc(
				MenuVo.class, sql, params);
		return voList;
	}

	@Override
	public Menu findMenuByName(String name) {
		return cloudClassMenuDao.findOneEntitiyByProperty(Menu.class, "name",
				name);
	}

	@Override
	public Menu find(Menu menu) {
		return cloudClassMenuDao.find(menu);
	}

	@Override
	public Menu findById(Integer id) {
		return cloudClassMenuDao.findById(id);
	}

	@Override
	public boolean exists(Menu menu) {
		// 输入了一个名称 这个名称数据库已经存在了
		Menu she = cloudClassMenuDao.findByNotEqId(menu);
		if (she != null) {
			return true;
		}
		return false;
	}

	@Override
	public void save(Menu menu) {
		cloudClassMenuDao.save(menu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see CloudClassMenuService#deletes(java.lang.String[])
	 */
	@Override
	public String deletes(String[] ids) {
		// cloudClassMenuDao.deletes(ids);
		String msg = "";
		for (String id : ids) {
			msg = cloudClassMenuDao.deleteById(id);
		}
		return msg;
	}

	@Override
	public void update(Menu menu) {
		cloudClassMenuDao.update(menu);
	}

	@Override
	public void updateStatus(String id) {
		Menu menu = cloudClassMenuDao.findById(Integer.parseInt(id));
		if (menu.getYunStatus() != null && menu.getYunStatus() == 1) {
			menu.setYunStatus(0);
		} else {
			menu.setYunStatus(1);
		}
		cloudClassMenuDao.update(menu);
	}

	@Override
	public void updateDirectionUp(String id) {
		Menu preMenu = cloudClassMenuDao
				.updateDirectionUp(Integer.parseInt(id));
		Menu me = cloudClassMenuDao.findById(Integer.parseInt(id));
		Integer meSort = me.getYunSort();
		me.setYunSort(preMenu.getYunSort());
		preMenu.setYunSort(meSort);
		cloudClassMenuDao.update(preMenu);
		cloudClassMenuDao.update(me);
	}

	@Override
	public void updateChildrenMenuDirectionUp(String pid, String id,
			Integer level) {
		Menu parentMenu = cloudClassMenuDao.findById(Integer.parseInt(pid));
		Menu preMenu = cloudClassMenuDao.updateDirectionUp(
				parentMenu.getNumber(), Integer.parseInt(id), level);
		Menu me = cloudClassMenuDao.findById(Integer.parseInt(id));
		Integer meSort = me.getYunSort();
		me.setYunSort(preMenu.getYunSort());
		preMenu.setYunSort(meSort);
		cloudClassMenuDao.update(preMenu);
		cloudClassMenuDao.update(me);

	}

	@Override
	public void updateDirectionDown(String id) {
		Menu downMenu = cloudClassMenuDao.updateDirectionDown(Integer
				.parseInt(id));
		Menu me = cloudClassMenuDao.findById(Integer.parseInt(id));
		Integer meSort = me.getYunSort();
		me.setYunSort(downMenu.getYunSort());
		downMenu.setYunSort(meSort);
		cloudClassMenuDao.update(downMenu);
		cloudClassMenuDao.update(me);
	}

	@Override
	public void updateChildrenMenuDirectionDown(String pid, String id,
			Integer level) {
		Menu parentMenu = cloudClassMenuDao.findById(Integer.parseInt(pid));
		Menu downMenu = cloudClassMenuDao.updateDirectionDown(
				parentMenu.getNumber(), Integer.parseInt(id), level);
		Menu me = cloudClassMenuDao.findById(Integer.parseInt(id));
		Integer meSort = me.getYunSort();
		me.setYunSort(downMenu.getYunSort());
		downMenu.setYunSort(meSort);
		cloudClassMenuDao.update(downMenu);
		cloudClassMenuDao.update(me);
	}

	@Override
	public List<Menu> findChildrenByNumber(Integer number, Integer level) {
		return cloudClassMenuDao.findChildrenByNumber(number, level);
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
		return cloudClassMenuDao.findScoreType(menuId);
	}

	@Override
	public void saveMenuCourseType(MenuCourseType menuCourseType) {
		cloudClassMenuDao.save(menuCourseType);

	}

	@Override
	public String addMenuCourseType(String id, int menuId) {
		MenuCourseType mt = new MenuCourseType();
		mt.setMenuId(menuId);
		mt.setCourseTypeId(id);
		mt.setDelete(false);
		mt.setCreateTime(new Date());
		cloudClassMenuDao.save(mt);
		return "添加成功！";
	}

	@Override
	public void removeMenuCourseType(Integer menuId) {
		cloudClassMenuDao.removeMenuCourseType(menuId);
	}

	@Override
	public void updateScoreTypeUp(String id) {
		ScoreType scoreType = scoreTypeDao.updateScoreTypeUp(id);
		ScoreType me = scoreTypeDao.searchById(id);
		Integer meSort = me.getSort();
		me.setSort(scoreType.getSort());
		scoreType.setSort(meSort);
		cloudClassMenuDao.update(scoreType);
		cloudClassMenuDao.update(me);

	}

	@Override
	public void updateScoreTypeDown(String id) {
		ScoreType scoreType = scoreTypeDao.updateScoreTypeDown(id);
		ScoreType me = scoreTypeDao.searchById(id);
		Integer meSort = me.getSort();
		me.setSort(scoreType.getSort());
		scoreType.setSort(meSort);
		cloudClassMenuDao.update(scoreType);
		cloudClassMenuDao.update(me);

	}

	@Override
	public List<MenuVo> getMenuList() {
		return cloudClassMenuDao.getMenuList();
	}

}
