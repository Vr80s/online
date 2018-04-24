package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.base.service.OnlineBaseService;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.MenuTag;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.dao.AskTagDao;
import com.xczhihui.bxg.online.web.dao.CourseDao;
import com.xczhihui.bxg.online.web.dao.MenuTagDao;
import com.xczhihui.bxg.online.web.service.MenuService;
import com.xczhihui.bxg.online.web.service.MenuTagService;
import com.xczhihui.bxg.online.web.vo.AskAccuseVo;
import com.xczhihui.bxg.online.web.vo.AskTagVo;
import com.xczhihui.bxg.online.web.vo.GroupLevelVo;
import com.xczhihui.bxg.online.web.vo.MenuTagVo;
import com.xczhihui.bxg.online.web.vo.MenuVo;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单业务层实现类
 * @author duanqh
 *
 */
@Service
public class MenuTagServiceImpl  implements OnlineBaseService, MenuTagService {
	
	@Autowired
	public MenuTagDao menuTagDao;

	@Autowired
	public MenuService  menuService;

	@Autowired
	public AskTagDao   askTagDao;

	@Autowired
	private CourseDao courseDao;

	@Override
	public List<MenuTag> getAllFirstMenu() {
		return menuTagDao.getAllFirstMenuTag();
		//return dao.findByHQL("from Menu where pid = ? and isDelete = 0 and status = 1", "-1");
	}
	
	@Override
	public MenuTag findMenu(String id) {
		return menuTagDao.findByHQLOne("from Menu where id = ? and isDelete = 0 and status = 1", id);
	}

	@Override
    public List<GroupLevelVo>	getAllMenuTagVoByParentId(String parentMenuId, int level) {
		List<GroupLevelVo> result = new ArrayList<>();
		List<MenuTag> menus=null;
		if(level==2) {
			menus= this.getAllSecondMenu(parentMenuId);
		}else{
			menus = this.getAllThirdMenu(parentMenuId);
		}
		if(menus!=null&&menus.size()>0){
			for(MenuTag menu:menus){
				GroupLevelVo groupLevelVo=new GroupLevelVo();
				groupLevelVo.setId(menu.getId());
				groupLevelVo.setName(menu.getMenu());
				groupLevelVo.setLevel(level);
				result.add(groupLevelVo);
			}
		}
		return result;
	}

	@Override
	public List<MenuTag> findChildren(String pid) {
		return menuTagDao.findByHQL("from Menu where pid = ? and isDelete = 0 and status = 1", pid);
	}

	@Override
	public List<MenuTag> getAllSecondMenu(String firstMenuId) {
		return menuTagDao.getSecondChildrenMenuTagByPid(firstMenuId);
	}

	@Override
	public List<MenuTag> getAllSecondMenuByIndex(String firstMenuId) {
		return menuTagDao.getSecondChildrenMenuTagByPidByIndex(firstMenuId);
	}


	@Override
	public List<MenuTag> getAllThirdMenu(String firstMenuId) {
		return menuTagDao.getThirdChildrenMenuTagByPid(firstMenuId);
	}



	@Override
	public List<MenuTagVo> getAllMenu() {
		List<MenuTagVo> result = new ArrayList<>();
		List<MenuTag> firstMenu = this.getAllFirstMenu();
		if (!CollectionUtils.isEmpty(firstMenu)) {
			for (MenuTag m : firstMenu) {
				MenuTagVo vo = new MenuTagVo();
				String menu = m.getMenu();
				vo.setId(m.getId());
				vo.setMenu(menu);
				String firstMenuId = m.getId();
				List<MenuTag> secondMenu = this.getAllSecondMenuByIndex(firstMenuId);
				vo.setSencodMenu(secondMenu);
				result.add(vo);
			}
		}
		return result;
	}



	/************************************************   新版博文答相关接口 ********************************************** */

	/**
	 * 获取全部学科以及学科下的标签
	 * @return 菜单集合
	 */
	@Override
    public List<Map> getMenuTags() throws InvocationTargetException, IllegalAccessException {
		List<Map> resultList = new ArrayList<Map>();
		//获取全部学科信息
		List<Menu> firstMenu = menuService.getFirstQuestionMenu(null, null);
		if (!CollectionUtils.isEmpty(firstMenu)) {
			for (Menu m : firstMenu) {
				Map   map=new HashMap();
				MenuVo vo = new MenuVo();
				BeanUtils.copyProperties(vo, m);
				map.put("menu", vo);
				List<AskTagVo> tags = askTagDao.getTagsByMenuId(m.getId());
				if (!CollectionUtils.isEmpty(tags)) {
                    map.put("tag", tags);
                }
				if(tags.size()>0) {
                    resultList.add(map);
                }
			}
		}
		return resultList;
	}

	/**
	 * 博文答提问时获取有权限学科以及学科下的标签
	 * @return 菜单集合
	 */
	@Override
    public List<Map> getQuestionMenuTags(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
		//获取当前登录用户信息
		OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
		List<Map> resultList = new ArrayList<Map>();
		//获取全部学科信息
		List<Menu> firstMenu = menuService.getQuestionFirstMenu(null, null);
		if (!CollectionUtils.isEmpty(firstMenu)) {
			for (Menu m : firstMenu) {
				Map   map=new HashMap();
				MenuVo vo = new MenuVo();
				BeanUtils.copyProperties(vo, m);
				if(m.getAskLimit()==0){ //此学科只有购买付费课程的用户看的到
					//看当前用户是否针对当前学科有权限，如果有，可以看到回答信息，否则必须购买才可以看到
					OnlineUser user=menuTagDao.findOneEntitiyByProperty(OnlineUser.class, "id",loginUser.getId());
					if(user.getMenuId().equals(m.getId())) {
						map.put("menu", vo);
						List<AskTagVo> tags = askTagDao.getTagsByMenuId(m.getId());
						if (!CollectionUtils.isEmpty(tags)) {
                            map.put("tag", tags);
                        }
						/*yuruixin-20170831*/
						if(tags.size()>1) {
                            resultList.add(map);
                        }
						/*yuruixin-20170831*/
					}else {
						if(courseDao.checkUserToPay(loginUser.getId(),m.getId())) {  //购买了付费课程，此学科公开显示
							map.put("menu", vo);
							List<AskTagVo> tags = askTagDao.getTagsByMenuId(m.getId());
							if (!CollectionUtils.isEmpty(tags)) {
                                map.put("tag", tags);
                            }
							/*yuruixin-20170831*/
							if(tags.size()>1) {
                                resultList.add(map);
                            }
							/*yuruixin-20170831*/
						}
					}
				}else{  //全部用户都可以看到
					map.put("menu", vo);
					List<AskTagVo> tags = askTagDao.getTagsByMenuId(m.getId());
					if (!CollectionUtils.isEmpty(tags)) {
                        map.put("tag", tags);
                    }
					/*yuruixin-20170831*/
					if(tags.size()>0) {
                        resultList.add(map);
                    }
					/*yuruixin-20170831*/
				}
			}
		}
		return resultList;
	}



	/**
	 * 投诉
	 * @param ac 参数封装对象
	 */
	@Override
    public  String    saveAccuse(AskAccuseVo ac){
		menuTagDao.saveAccuse(ac);
		return  "投诉成功!";
	}


	/**
	 * 修改投诉状态
	 * @param ac 参数封装对象
	 */
	@Override
    public String updateAccuseStatus(AskAccuseVo ac){
		menuTagDao.updateAccuseStatus(ac.getTarget_id(),ac.getTarget_type());
		return  "true";
	}
}
