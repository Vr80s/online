package com.xczhihui.bxg.online.web.service;

import com.xczhihui.bxg.online.common.domain.MenuTag;
import com.xczhihui.bxg.online.web.vo.AskAccuseVo;
import com.xczhihui.bxg.online.web.vo.GroupLevelVo;
import com.xczhihui.bxg.online.web.vo.MenuTagVo;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 菜单接口
 * @author duanqh
 *
 */
public interface MenuTagService {

	/**
	 * 获取所有一级菜单
	 * @return 所有一级菜单
	 */
	public List<MenuTag> getAllFirstMenu();

	/**
	 * 根据一级id获取到所有二级菜单
	 * @param firstMenuId
	 * @return 该id下的二级菜单
	 */
	public List<MenuTag> getAllSecondMenu(String firstMenuId);


	/**
	 * 根据一级id获取到所有三级菜单
	 * @param firstMenuId
	 * @return 该id下的二级菜单
	 */
	public List<MenuTag> getAllThirdMenu(String firstMenuId);


	/**
	 * 获取到所有一二级菜单数据
	 * @return
	 */
	public List<MenuTagVo> getAllMenu();
	
	/**
	 * 通过id获取menu
	 * @param id
	 * @return
	 */
	public MenuTag findMenu(String id);


	/**
	 * 根据一级id获取到所有二级菜单
	 * @return 该id下的二级菜单
	 */
	public List<GroupLevelVo> getAllMenuTagVoByParentId(String parentMenuId, int level);

	/**
	 * 返回所有子节点
	 * @param pid
	 * @return
	 */
	public List<MenuTag> findChildren(String pid);

	/**
	 * 返回首页的所有二级菜单
	 * @param firstMenuId
	 * @return
	 */
	public List<MenuTag> getAllSecondMenuByIndex(String firstMenuId);


      /************************************************   新版博文答相关接口 ********************************************** */

	/**
	 * 获取全部学科以及学科下的标签
	 * @return 菜单集合
	 */
	public List<Map> getMenuTags() throws InvocationTargetException, IllegalAccessException;

	/**
	 * 博文答提问时获取有权限学科以及学科下的标签
	 * @return 菜单集合
	 */
	public List<Map> getQuestionMenuTags(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException;
	/**
	 * 投诉
	 * @param ac 参数封装对象
	 */
	public  String    saveAccuse(AskAccuseVo ac);

	/**
	 * 修改投诉状态
	 * @param ac 参数封装对象
	 */
	public String updateAccuseStatus(AskAccuseVo ac);
}
