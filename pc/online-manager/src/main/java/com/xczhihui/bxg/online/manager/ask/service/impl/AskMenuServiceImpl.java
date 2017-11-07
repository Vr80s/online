package com.xczhihui.bxg.online.manager.ask.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.MenuCourseType;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.manager.ask.dao.AskMenuDao;
import com.xczhihui.bxg.online.manager.ask.service.AskMenuService;
import com.xczhihui.bxg.online.manager.cloudClass.dao.ScoreTypeDao;
import com.xczhihui.bxg.online.manager.ask.vo.MenuVo;

/**
 *   AskMenuServiceImpl:博问答学科管理业务层接口实现类
 *   @author yxd
 */
@Service
public class AskMenuServiceImpl extends OnlineBaseServiceImpl implements AskMenuService {

    @Autowired
    private AskMenuDao askMenuDao;
    
    @Autowired
    private ScoreTypeDao scoreTypeDao;

    /**
     * 获取全部一级与二级菜单
     * @return 菜单集合
     */
    /* @Override
    public List<MenuVo> getAllMenu(String name,Integer pageNumber,Integer pageSize) {
        List<MenuVo>  resultList =new ArrayList<>();
        List<Menu> firstMenu = this.getAllFirstMenu("", null, null);
        if (!CollectionUtils.isEmpty(firstMenu)) {
            for (Menu m : firstMenu) {
                MenuVo vo = new MenuVo();
                vo.setNumber(m.getNumber());
                vo.setName(m.getName());
                vo.setType(m.getType());
                List<Menu> secondMenu = this.getAllSecondMenuByIndex("",m.getNumber(), null, null);
                vo.setSencodMenu(secondMenu);
                resultList.add(vo);
            }
        }
        return resultList;
    }*/

    @Override
    public Page<MenuVo> findMenuPage(MenuVo menuVo, Integer pageNumber, Integer pageSize)  {
        Page<MenuVo> page = askMenuDao.findCloudClassMenuPage(menuVo, pageNumber, pageSize);
        return page;
    }

    @Override
    public Integer getMaxSort() {
        return askMenuDao.getMaxSort();
    }
    
    @Override
    public Integer getMaxYunSort() {
        return askMenuDao.getMaxYunSort();
    }
    @Override
    public Integer getMaxBoSort() {
        return askMenuDao.getMaxBoSort();
    }

    @Override
    public Integer getMaxNumber(Integer level) {
        return askMenuDao.getMaxNumber(level);
    }

    @Override
    public Integer getMaxNumber(Integer parentId, Integer level) {
        Menu parentMenu=askMenuDao.findById(parentId);
        return askMenuDao.getMaxNumber(parentMenu.getNumber(),2);
    }

    @Override
    public Integer getMaxSort(Integer parentId, Integer level) {
        Menu parentMenu=askMenuDao.findById(parentId);
        return askMenuDao.getMaxSort(parentMenu.getNumber(), 2);
    }

    @Override
    public List<MenuVo> list() {
        String sql="select * from oe_menu where is_delete = 0 and name <> '全部' and status=1 order by sort";
        Map<String,Object> params=new HashMap<String,Object>();
        List<MenuVo> voList=askMenuDao.findEntitiesByJdbc(MenuVo.class, sql, params);
        return voList;
    }

    @Override
    public Menu findMenuByName(String name) {
        return askMenuDao.findOneEntitiyByProperty(Menu.class,"name",name);
    }

    @Override
    public Menu find(Menu menu) {
        return askMenuDao.find(menu);
    }

    @Override
    public Menu findById(Integer id) {
        return askMenuDao.findById(id);
    }

    @Override
    public boolean exists(Menu menu) {
        //输入了一个名称 这个名称数据库已经存在了
        Menu she=askMenuDao.findByNotEqId(menu);
        if(she!=null){
            return true;
        }
        return false;
    }

    @Override
    public void save(Menu menu) {
        askMenuDao.save(menu);
    }

    /* (non-Javadoc)
     * @see com.xczhihui.bxg.online.manager.cloudClass.service.CloudClassMenuService#deletes(java.lang.String[])
     */
    @Override
    public String deletes(String[] ids) {
//        askMenuDao.deletes(ids);
    	String msg = "";
        for(String id:ids){
        	msg = askMenuDao.deleteById(id);
        }
        return  msg;
    }

    @Override
    public void update(Menu menu) {
        askMenuDao.update(menu);
    }

    @Override
    public String updateStatus(String id) {
        Menu menu=askMenuDao.findById(Integer.parseInt(id));
       
        if(menu.getBoStatus()!=null&&menu.getBoStatus()==1){//禁用
        	 if(menu.getYunStatus()==1){
        		 return "此学科在云课堂管理被启用，不能被禁用!";
             }
             menu.setBoStatus(0);
        }else{//启用
            menu.setBoStatus(1);
        }
        askMenuDao.update(menu);
        return "success";
    }
    
    @Override
    public void updateAskMenuLimit(MenuVo menu) {
        Menu temp=askMenuDao.findById(Integer.parseInt(menu.getId()));
        temp.setAskLimit(menu.getAskLimit());
        askMenuDao.update(temp);
    }

    @Override
    public void updateDirectionUp(String id) {
        Menu preMenu=askMenuDao.updateDirectionUp(Integer.parseInt(id));
        Menu me=askMenuDao.findById(Integer.parseInt(id));
        Integer meSort=me.getBoSort();
        me.setBoSort(preMenu.getBoSort());
        preMenu.setBoSort(meSort);
        askMenuDao.update(preMenu);
        askMenuDao.update(me);
    }

    @Override
    public void updateChildrenMenuDirectionUp(String pid, String id, Integer level) {
        Menu parentMenu=askMenuDao.findById(Integer.parseInt(pid));
        Menu preMenu=askMenuDao.updateDirectionUp(parentMenu.getNumber(),Integer.parseInt(id),level);
        Menu me=askMenuDao.findById(Integer.parseInt(id));
        Integer meSort=me.getBoSort();
        me.setBoSort(preMenu.getBoSort());
        preMenu.setBoSort(meSort);
        askMenuDao.update(preMenu);
        askMenuDao.update(me);

    }

    @Override
    public void updateDirectionDown(String id) {
        Menu downMenu=askMenuDao.updateDirectionDown(Integer.parseInt(id));
        Menu me=askMenuDao.findById(Integer.parseInt(id));
        Integer meSort=me.getBoSort();
        me.setBoSort(downMenu.getBoSort());
        downMenu.setBoSort(meSort);
        askMenuDao.update(downMenu);
        askMenuDao.update(me);
    }

    @Override
    public void updateChildrenMenuDirectionDown(String pid, String id, Integer level) {
        Menu parentMenu=askMenuDao.findById(Integer.parseInt(pid));
        Menu downMenu=askMenuDao.updateDirectionDown(parentMenu.getNumber(),Integer.parseInt(id),level);
        Menu me=askMenuDao.findById(Integer.parseInt(id));
        Integer meSort=me.getBoSort();
        me.setBoSort(downMenu.getBoSort());
        downMenu.setBoSort(meSort);
        askMenuDao.update(downMenu);
        askMenuDao.update(me);
    }

    @Override
    public List<Menu> findChildrenByNumber(Integer number,Integer level) {
        return askMenuDao.findChildrenByNumber(number,level);
    }

    /**
     * 获取全部一级菜单
     * @return
     */
    public List<Menu> getAllFirstMenu(String name, Integer pageNumber, Integer pageSize){
            pageNumber = pageNumber == null ? 1 : pageNumber;
            pageSize = pageSize == null ? 20 : pageSize;

            Map<String, Object> paramMap = new HashMap<String, Object>();
            StringBuffer and = new StringBuffer();
            if (StringUtils.hasText(name)) {
                and.append(" and name = :name ");
                paramMap.put("name", name);
            }

            Page<Menu> page = dao.findPageByHQL("from Menu where 1=1 and level=1 "+and, paramMap, pageNumber, pageSize);
            return page.getItems();
    }


    /**
     * 获取二级菜单，根据一级菜单编号
     * @return
     */
    public  List<Menu> getAllSecondMenuByIndex(String name,Integer number,Integer pageNumber, Integer pageSize){
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;

        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuffer and = new StringBuffer();
        if (StringUtils.hasText(name)) {
            and.append(" and name like :name");
            paramMap.put("name", name);
        }
        String sql="";
        if(number==10){
            sql="from Menu where 1=1 and isDelete=0 and level=2 and length(number)<=3  and number like '" +number+"%'"+and;
        }else
        {

            sql="from Menu where 1=1 and isDelete=0 and level=2 and number like '" +number+"%'"+and;
        }
        Page<Menu> page = dao.findPageByHQL(sql, paramMap, pageNumber, pageSize);
        return page.getItems();
    }

	@Override
	public List<ScoreType> findScoreType(String menuId) {
		return askMenuDao.findScoreType(menuId);
	}

	@Override
	public void saveMenuCourseType(MenuCourseType menuCourseType) {
		askMenuDao.save(menuCourseType);
		
	}

	@Override
	public String addMenuCourseType(String id, int menuId) {
		MenuCourseType mt = new MenuCourseType();
		mt.setMenuId(menuId);
		mt.setCourseTypeId(id);
		mt.setDelete(false);
		//mt.setCreatePerson(UserHolder.getCurrentUser().getId());
		mt.setCreateTime(new Date());
		askMenuDao.save(mt);
		return "添加成功！";
	}

	
	@Override
	public void removeMenuCourseType(Integer menuId) {
		 askMenuDao.removeMenuCourseType(menuId);
	}

	@Override
	public void updateScoreTypeUp(String id) {
		ScoreType scoreType = scoreTypeDao.updateScoreTypeUp(id);
        ScoreType me= scoreTypeDao.searchById(id);
        Integer meSort=me.getSort();
        me.setSort(scoreType.getSort());
        scoreType.setSort(meSort);
        askMenuDao.update(scoreType);
        askMenuDao.update(me);
		
	}

	@Override
	public void updateScoreTypeDown(String id) {
		ScoreType scoreType = scoreTypeDao.updateScoreTypeDown(id);
        ScoreType me= scoreTypeDao.searchById(id);
        Integer meSort=me.getSort();
        me.setSort(scoreType.getSort());
        scoreType.setSort(meSort);
        askMenuDao.update(scoreType);
        askMenuDao.update(me);
		
	}

	@Override
	public List<MenuVo> getMenuList() {
		return askMenuDao.getMenuList();
	}

}
