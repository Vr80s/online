package com.xczhihui.course.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.course.dao.ScoreTypeDao;
import com.xczhihui.course.service.ScoreTypeService;
import com.xczhihui.course.vo.ScoreTypeVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *   MenuServiceImpl:菜单业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class ScoreTypeServiceImpl extends OnlineBaseServiceImpl implements ScoreTypeService {

    @Autowired
    private ScoreTypeDao scoreTypeDao;


    /**
     * 查询列表
     */
    @Override
    public Page<ScoreTypeVo> findMenuPage(ScoreTypeVo menuVo, Integer pageNumber, Integer pageSize)  {
        Page<ScoreTypeVo> page = scoreTypeDao.findScoreTypePage(menuVo, pageNumber, pageSize);
        return page;
    }


    /**
     * 查找输入的课程类别是否存在
     */
	@Override
	public ScoreType findScoreTypeByName(String name) {
		return scoreTypeDao.findOneEntitiyByProperty(ScoreType.class,"name",name);
	}

	@Override
	public List<ScoreType> list() {
		String sql="select * from score_type where is_delete=0 and status=1 and id<>0 order by sort";
		Map<String,Object> params=new HashMap<String,Object>();
		List<ScoreType> voList=scoreTypeDao.findEntitiesByJdbc(ScoreType.class, sql, params);
		return voList;
	}


	/**
	 * 获取最大的排序
	 */
	@Override
	public int getMaxSort() {
		return scoreTypeDao.getMaxSort();
	}


	/**
	 * 保存实体
	 */
	@Override
	public void save(ScoreType entity) {
		scoreTypeDao.save(entity);
	}


	@Override
	public boolean exists(ScoreType searchEntity) {
		//输入了一个名称 这个名称数据库已经存在了
        ScoreType she=scoreTypeDao.findByNotEqId(searchEntity);
        if(she!=null){
            return true;
        }
        return false;
	}


	@Override
	public void update(ScoreType me) {
		scoreTypeDao.update(me);
	}


	@Override
	public ScoreType findById(String parseInt) {
		return scoreTypeDao.findById(parseInt);
	}


	@Override
	public String deletes(String[] _ids) {
//		scoreTypeDao.deletes(_ids);
//        for(String id:_ids){
//            ScoreType menu=scoreTypeDao.findOneEntitiyByProperty(ScoreType.class,"id",Integer.parseInt(id));
//            if(menu!=null){
//            	scoreTypeDao.deletesByNumber(menu.getNumber());
//            }
//        }
		String msg = "";
        for(String id:_ids){
        	msg = scoreTypeDao.deleteById(id);
        }
        return  msg;
		
	}


	@Override
	public void updateStatus(String id) {
		ScoreType scoreType=scoreTypeDao.findById(id);
        if(scoreType.getStatus()!=null&&scoreType.getStatus()==1){
        	scoreType.setStatus(0);
        }else{
        	scoreType.setStatus(1);
        }
        scoreTypeDao.update(scoreType);
		
	}

}
