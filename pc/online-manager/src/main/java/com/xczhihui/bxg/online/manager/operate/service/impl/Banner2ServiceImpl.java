package com.xczhihui.bxg.online.manager.operate.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Banner;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.manager.operate.dao.Banner2Dao;
import com.xczhihui.bxg.online.manager.operate.vo.Banner2Vo;
import com.xczhihui.bxg.online.manager.operate.service.Banner2Service;

@Service
public class Banner2ServiceImpl extends OnlineBaseServiceImpl implements Banner2Service {

	@Autowired
    private Banner2Dao banner2Dao;
	
	@Override
	public Page<Banner2Vo> findBanner2Page(Banner2Vo banner2Vo, Integer pageNumber, Integer pageSize) {
		Page<Banner2Vo> page = banner2Dao.findBanner2Page(banner2Vo, pageNumber, pageSize);
		return page;
	}

	@Override
	public void addBanner(Banner2Vo banner2Vo) {
		String sql="select ifnull(min(sort),0) from oe_banner2 ";
		int sort = dao.queryForInt(sql, null) - 1;

		Banner banner = new Banner();
		banner.setDescription(banner2Vo.getDescription());; //banner名称
		banner.setCreatePerson(banner2Vo.getCreatePerson()); //创建人
		banner.setCreateTime(new Date()); //当前时间
		banner.setDelete(false); //是否被删除
		banner.setImgHref(banner2Vo.getImgHref());; //图片的外链
		banner.setImgPath(banner2Vo.getImgPath());//图片的地址
		banner.setSort(sort);
		banner.setStatus(0);//默认禁用
		//banner.setStartTime();//启用时间为空
		//banner.setEndTime();//禁用时间为空
		banner.setClickCount(0);//点击次数为0
		dao.save(banner);
	}

	@Override
	public void updateBanner(Banner2Vo banner2Vo) {
		// TODO Auto-generated method stub
		Banner banner = dao.findOneEntitiyByProperty(Banner.class, "id", Integer.parseInt(banner2Vo.getId()));
		
		banner.setId(Integer.parseInt(banner2Vo.getId()));
		banner.setDescription(banner2Vo.getDescription());; //banner名称
		banner.setImgHref(banner2Vo.getImgHref());; //图片的外链
		banner.setImgPath(banner2Vo.getImgPath());//图片的地址
		dao.update(banner);
	}

	@Override
	public boolean updateStatus(Banner2Vo banner2Vo) {
		// TODO Auto-generated method stub
		Banner banner = dao.findOneEntitiyByProperty(Banner.class, "id", Integer.parseInt(banner2Vo.getId()));
		if(banner.getStatus() == 1){//将要被禁用
			banner.setEndTime(new Date());
		}else{//将要被启用
			//验证已经启用的是否达到8个，如果已经达到就不让启用
			String hqlPre="from Banner where isDelete=0 and status = 1";
			List<Banner> list= dao.findByHQL(hqlPre);

			if(list.size() < 8){
				if(banner.getStartTime() == null)//第一次启用
				{
					banner.setStartTime(new Date());
				}
			}else{
				return false;//达到8个就返回false
			}
		}
		banner.setStatus(Math.abs(banner.getStatus()-1));//状态
		dao.update(banner);
		return true;
	}
	
	@Override
	public void deletes(String[] ids) {
		// TODO Auto-generated method stub
		for(String id:ids){
			String hqlPre="from Banner where isDelete=0 and id = ?";
			Banner banner= dao.findByHQLOne(hqlPre,new Object[] {Integer.valueOf(id)});
            if(banner !=null){
            	banner.setDelete(true);
                dao.update(banner);
            }
        }
	}

	@Override
	public void updateSortUp(Integer id) {
		// TODO Auto-generated method stub
		 String hqlPre="from Banner where  isDelete=0 and status = 1 and id = ?";
		 Banner bannerPre= dao.findByHQLOne(hqlPre,new Object[] {id});
         Integer bannerPreSort=bannerPre.getSort();
         
         String hqlNext="from Banner where sort < (select sort from Banner where id= ? )  and isDelete=0 and status = 1 order by sort desc";
         Banner bannerNext= dao.findByHQLOne(hqlNext,new Object[] {id});
         Integer bannerNextSort=bannerNext.getSort();
         
         bannerPre.setSort(bannerNextSort);
         bannerNext.setSort(bannerPreSort);
         
         dao.update(bannerPre);
         dao.update(bannerNext);
	}

	@Override
	public void updateSortDown(Integer id) {
		// TODO Auto-generated method stub
		 String hqlPre="from Banner where  isDelete=0 and status = 1 and id = ?";
		 Banner bannerPre= dao.findByHQLOne(hqlPre,new Object[] {id});
         Integer bannerPreSort=bannerPre.getSort();
         String hqlNext="from Banner where sort > (select sort from Banner where id= ? )  and isDelete=0 and status = 1 order by sort asc";
         Banner bannerNext= dao.findByHQLOne(hqlNext,new Object[] {id});
         Integer bannerNextSort=bannerNext.getSort();
         
         bannerPre.setSort(bannerNextSort);
         bannerNext.setSort(bannerPreSort);
         
         dao.update(bannerPre);
         dao.update(bannerNext);
	}
}