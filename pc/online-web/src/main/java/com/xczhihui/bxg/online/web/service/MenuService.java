package com.xczhihui.bxg.online.web.service;


import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.web.vo.MenuVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 *   MenuService:菜单业务层接口类
 * * @author Rongcai Kang
 */
public interface MenuService
{

    public List<MenuVo> getAllMenu(Integer pageNumber,Integer pageSize, String type) throws InvocationTargetException, IllegalAccessException;
    public Menu findById(Integer id);

    /**
     * 返回用户课程中所有菜单
     * @param userId
     * @return
     */
    public List<MenuVo> findUserCourseMenus(String userId);


    /**
     * 获取全部一级菜单
     * @return
     */
    public List<Menu> getAllFirstMenu( Integer pageNumber, Integer pageSize ,String type);

    /**
     * 博文答首页模块获取全部一级菜单
     * @return
     */
    public List<Menu> getFirstQuestionMenu( Integer pageNumber, Integer pageSize);

    /**
     * 博问答提问页面获取有权限的一级菜单
     * @return
     */
    public List<Menu> getQuestionFirstMenu( Integer pageNumber, Integer pageSize);

}
