package com.xczhihui.bxg.online.manager.mobile.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Project;
import com.xczhihui.bxg.online.manager.mobile.dao.MobileSearchDao;
import com.xczhihui.bxg.online.manager.mobile.service.MobileSearchService;
import com.xczhihui.bxg.online.manager.mobile.vo.MobileSearchVo;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 题库service实现类
 * 
 * @author snow
 */
@Service("mobileSearchService")
public class MobileSearchServiceImpl extends OnlineBaseServiceImpl implements MobileSearchService {
	@Autowired
	private MobileSearchDao mobileSearchDao;

	@Override
	public Page<MobileSearchVo> findMobileSearchPage(MobileSearchVo mobileSearchVo, int pageNumber,
											  int pageSize) {
		Page<MobileSearchVo> page = mobileSearchDao.findMobileSearchPage(mobileSearchVo, pageNumber, pageSize);
		return page;
	}

	@Override
	public MobileSearchVo findMobileSearchByNameAndByType(String name,Integer type) {
		DetachedCriteria dc = DetachedCriteria.forClass(MobileSearchVo.class);
		dc.add(Restrictions.eq("name", name));
		dc.add(Restrictions.eq("searchType", type));
		return mobileSearchDao.findEntity(dc);
	}

	@Override
	public int getMaxSort() {
		return mobileSearchDao.getMaxSort();
	}

	@Override
	public void save(MobileSearchVo mobileSearchVo) {
		mobileSearchDao.save(mobileSearchVo);
	}
	
	@Override
	public void update(MobileSearchVo mobileSearchVo) {
		mobileSearchDao.update(mobileSearchVo);
	}
	
	@Override
	public MobileSearchVo findById(String parseInt) {
		return mobileSearchDao.findById(parseInt);
	}

	@Override
	public boolean exists(MobileSearchVo existsEntity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String deletes(String[] _ids) {
		String msg = "";
        for(String id:_ids){
        	msg = mobileSearchDao.deleteById(id);
        }
        return  msg;
	}

	@Override
	public void updateStatus(String id) {
		// TODO Auto-generated method stub
		MobileSearchVo scoreType=mobileSearchDao.findById(id);
        if(scoreType.getStatus()!=null&&scoreType.getStatus()==1){
        	scoreType.setStatus(0);
        }else{
        	scoreType.setStatus(1);
        }
		mobileSearchDao.update(scoreType);
	}

	@Override
	public void updateSortUp(Integer id) {
		 String hqlPre="from MobileSearchVo where  isDelete=0 and status = 1 and id = ?";
		MobileSearchVo ProjectPre= dao.findByHQLOne(hqlPre,new Object[] {id});
         Integer ProjectPreSort=ProjectPre.getSeq();
         
         String hqlNext="from MobileSearchVo where seq < (select seq from MobileSearchVo where id= ? )  and isDelete=0 and status = 1 order by seq desc";
		MobileSearchVo ProjectNext= dao.findByHQLOne(hqlNext,new Object[] {id});
         Integer ProjectNextSort=ProjectNext.getSeq();
         
         ProjectPre.setSeq(ProjectNextSort);
         ProjectNext.setSeq(ProjectPreSort);
         
         dao.update(ProjectPre);
         dao.update(ProjectNext);
	}

	@Override
	public void updateSortDown(Integer id) {
		 String hqlPre="from MobileSearchVo where  isDelete=0 and status = 1 and id = ?";
		MobileSearchVo ProjectPre= dao.findByHQLOne(hqlPre,new Object[] {id});
         Integer ProjectPreSort=ProjectPre.getSeq();
         String hqlNext="from MobileSearchVo where seq > (select seq from MobileSearchVo where id= ? )  and isDelete=0 and status = 1 order by seq asc";
		MobileSearchVo ProjectNext= dao.findByHQLOne(hqlNext,new Object[] {id});
         Integer ProjectNextSort=ProjectNext.getSeq();
         
         ProjectPre.setSeq(ProjectNextSort);
         ProjectNext.setSeq(ProjectPreSort);
         
         dao.update(ProjectPre);
         dao.update(ProjectNext);
	}
	
}
