package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.web.dao.AskTagDao;
import com.xczhihui.bxg.online.web.dao.MenuDao;
import com.xczhihui.bxg.online.web.service.MenuService;
import com.xczhihui.bxg.online.web.vo.MenuVo;
import com.xczhihui.bxg.online.web.vo.ScoreTypeVo;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 *   MenuServiceImpl:菜单业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class MenuServiceImpl  extends OnlineBaseServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private AskTagDao askTagDao;


    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }



    /**
     * 获取全部一级与二级菜单
     * @return 菜单集合
     */
    @Override
    public List<MenuVo> getAllMenu(Integer pageNumber,Integer pageSize,String type) throws InvocationTargetException, IllegalAccessException {
        List<MenuVo>  resultList =new ArrayList<>();
        List<Menu> firstMenu = this.getAllFirstMenu( null, null,type);
        if (!CollectionUtils.isEmpty(firstMenu)) {
            for (Menu m : firstMenu) {
                MenuVo vo = new MenuVo();
                BeanUtils.copyProperties(vo, m);
                List<ScoreTypeVo> secondMenu = this.getAllSecondMenuByIndex(m.getId(), null, null);
                if (!CollectionUtils.isEmpty(secondMenu))
                       vo.setSencodMenu(secondMenu);
                       resultList.add(vo);
            }
        }
        return resultList;
    }

    @Override
    public Menu findById(Integer id) {
        return dao.findOneEntitiyByProperty(Menu.class,"id",id);
    }

    @Override
    public List<MenuVo> findUserCourseMenus(String userId) {
        return menuDao.findUserCourseMenus(userId);
    }


    /**
     * 云课堂模块获取全部一级菜单
     * @param type 
     * @return
     */
    public List<Menu> getAllFirstMenu( Integer pageNumber, Integer pageSize, String type){
            pageNumber = pageNumber == null ? 1 : pageNumber;
            pageSize = pageSize == null ? 20 : pageSize;
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("type", Integer.valueOf(type));
            Page<Menu> page = dao.findPageByHQL("from Menu where 1=1  and isDelete = 0  and status =1 and yunStatus=1 and type = :type order by yunSort ", paramMap, pageNumber, pageSize);
            return page.getItems();
    }

    /**
     * 博文答首页模块获取全部一级菜单
     * @return
     */
    public List<Menu> getFirstQuestionMenu( Integer pageNumber, Integer pageSize){
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        Page<Menu> page = dao.findPageByHQL("from Menu where 1=1  and isDelete = 0  and status =1 and boStatus=1  order by boSort ", paramMap, pageNumber, pageSize);
        Page<Menu> page = dao.findPageByHQL("from Menu where 1=1  and isDelete = 0  and status =1 and boStatus=1 and id!=2 and id!=3 and id!=1 order by boSort ", paramMap, pageNumber, pageSize);
        return page.getItems();
    }

    /**
     * 博问答提问页面获取有权限的一级菜单
     * @return
     */
    public List<Menu> getQuestionFirstMenu( Integer pageNumber, Integer pageSize){
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Page<Menu> page = dao.findPageByHQL("from Menu where id !=0 and   isDelete = 0  and status = 1 and boStatus=1  and id!=2 and id!=3 and id!=1  order by boSort ", paramMap, pageNumber, pageSize);

        return page.getItems();
    }




    /**
     * 获取课程类别信息，根据学科ID号
     * @return
     */
    public  List<ScoreTypeVo> getAllSecondMenuByIndex(Integer menuID,Integer pageNumber, Integer pageSize){
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String sql="";
        //如果menuId=0 查找中间表中所有数据，按照课程类别id分组
        if(menuID == 0){
            sql=" select mc.*,st.`name`  from  ( select  '0' as menu_id ,course_type_id   from  menu_coursetype group by course_type_id ) as mc  join score_type st on  mc.course_type_id = st.id  where st.is_delete = 0  and st.status = 1 order by st.sort desc";
        }else{ //根据对应学科id查找学科下面的课程类别
            sql="select mc.menu_id,mc.course_type_id,st.`name` from  menu_coursetype mc JOIN score_type  st where  st.is_delete = 0  and st.status = 1 and    mc.course_type_id = st.id  and  mc.menu_id = :menuId  order by st.sort desc  ";
            paramMap.put("menuId",menuID);
        }
        Page<ScoreTypeVo> page = dao.findPageBySQL(sql, paramMap, ScoreTypeVo.class, pageNumber, pageSize);
        if(page.getItems().size()>1){
        	page.getItems().add(0,  page.getItems().get(page.getItems().size() - 1));
        	page.getItems().remove( page.getItems().size()-1);
        }

        return  page.getItems();
    }

}
