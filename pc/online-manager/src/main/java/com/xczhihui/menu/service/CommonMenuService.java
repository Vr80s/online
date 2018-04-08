package com.xczhihui.menu.service;



import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.MenuCourseType;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.cloudClass.vo.MenuVo;

import java.util.List;

/**
 *   MenuService:公共学科业务层接口类
 * * @author yxd
 */
public interface CommonMenuService {

    //public List<MenuVo> getAllMenu(String name, Integer pageNumber, Integer pageSize);
    public Page<MenuVo> findMenuPage(MenuVo menuVo, Integer pageNumber, Integer pageSize);
    public Integer getMaxSort();
    public Integer getMinSort();
    public Integer getMaxNumber(Integer level);
    public Integer getMaxNumber(Integer parentId,Integer level);
    public Menu findMenuByName(String name);
    public Menu find(Menu menu);
    public Menu findById(Integer id);
    public boolean exists(Menu menu);
    public void save(Menu entity);
    public String deletes(String[] ids);
    public void update(Menu menu);
    public void updateStatus(String id);
    public void updateDirectionUp(String id);
    public void updateChildrenMenuDirectionUp(String pid,String id,Integer level);
    public void updateDirectionDown(String id);
    public void updateChildrenMenuDirectionDown(String pid,String id,Integer level);
    public List<Menu> findChildrenByNumber(Integer number,Integer level);
    public Integer getMaxSort(Integer parentId, Integer level);

    public List<MenuVo> list();
    /**
     * 查询课程列表表
     * @param menuId
     * @return
     */
	public List<ScoreType> findScoreType(String menuId);
	/**
	 * 保存数据到学科课程类别中间表
	 * @param menuCourseType
	 */
	public void saveMenuCourseType(MenuCourseType menuCourseType);
	/**
     * 增加学科拥有的课程类别（二级菜单）
     * @param id
     * @param menuId
     * @return
     */
	public String addMenuCourseType(String id, int menuId);
	
	/**
     * 当checkbox为选中状态时，点击取消选中，将中间表的数据删除；
     * @param id
     * @param menuId
     * @return
     */
	public void removeMenuCourseType(Integer menuId);
	
	/**
	 * 课程类别管理上移
	 * @param id
	 */
	public void updateScoreTypeUp(String id);
	
	/**
	 * 课程管理类别下移
	 * @param id
	 */
	public void updateScoreTypeDown(String id);
	public Integer getMaxBoSort();
	public Integer getMaxYunSort();
	public Integer getMinBoSort();
	public Integer getMinYunSort();
}
