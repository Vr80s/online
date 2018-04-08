package com.xczhihui.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xczhihui.common.vo.KeyValVo;
import com.xczhihui.system.dao.LinkDao;
import com.xczhihui.system.service.LinkService;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Otherlink;
import com.xczhihui.common.util.ConvertNullToEmptyStringUtil;
import com.xczhihui.system.vo.OtherLinkVo;

/**
 * 友情链接
 * @author duanqh
 *
 */
@Service
public class LinkServiceImpl implements LinkService {
	
	@Autowired
	private LinkDao dao;

	@Override
	public PageVo findPageLink(Groups groups, PageVo page) {
		
		return dao.findPagevideo(groups, page);
	}

	@Override
	public void addLink(Otherlink link) {
		dao.save(link);
	} 

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public void deleteBatch(String... ids) {
		dao.deleteBatch(ids);
	}

	@Override
	public Otherlink getLinkById(Integer id) {
		
		return dao.get(id, Otherlink.class);
	}

	@Override
	public void update(Otherlink link) {
		
		dao.update(link);
	}

	@Override
	public List<KeyValVo> getSortlist() {
		List<Otherlink> links = dao.findByHQL("from Otherlink");
		List<KeyValVo> listsort = new ArrayList<>();
		for(int i = 1; i < links.size() + 2 ; i++){
			KeyValVo vo = new KeyValVo();
			vo.setId(i);
			vo.setName(i);
			listsort.add(vo);
		}
		return listsort;
	}

	@Override
	public void updateStatus(Integer id) {
		
		Otherlink link = dao.get(id, Otherlink.class);
	
		  if(link.getStatus()!=null&&"1".equals(link.getStatus())){
			  link.setStatus("0");
	         }else{
	        	 link.setStatus("1");
	         }
		dao.update(link);
	}


	@Override
    public void updateUpMove(Integer id) {
		Otherlink otherlinkCur = dao.get(id,Otherlink.class);
		Integer otherlinkCurSort=otherlinkCur.getSort();
		
		Otherlink otherlinkPre = dao.getPreEntity(otherlinkCur);
		Integer otherlinkPreSort = otherlinkPre.getSort();
		
		otherlinkCur.setSort(otherlinkPreSort);
		otherlinkPre.setSort(otherlinkCurSort);
	         
	    dao.update(otherlinkCur);
	    dao.update(otherlinkPre);
	}


	@Override
    public void updateDownMove(Integer id) {
		Otherlink otherlinkCur = dao.get(id,Otherlink.class);
		Integer otherlinkCurSort=otherlinkCur.getSort();
		
		Otherlink otherlinkNext = dao.getNextEntity(otherlinkCur);
		Integer otherlinkNextSort = otherlinkNext.getSort();
		
		otherlinkCur.setSort(otherlinkNextSort);
		otherlinkNext.setSort(otherlinkCurSort);
	         
	    dao.update(otherlinkCur);
	    dao.update(otherlinkNext);
	}

	
	@Override
    public Page<OtherLinkVo> findOtherLinkPage(Map<String, Object> paramMap, int pageNumber, int pageSize) {
		Page<OtherLinkVo> linkPage = this.dao.findOtherLinkPage(paramMap, pageNumber, pageSize);
		return ConvertNullToEmptyStringUtil.convert(linkPage);
		
	}


	@Override
    public boolean findSort(Integer sort, Integer id) {
		List<Otherlink> list = new ArrayList<>();
		if(id!=null&&!"".equals(id)){//修改
			list = dao.findByHQL("from Otherlink b where b.isDelete = 0 and b.sort = ? and b.id != ?", sort,id);
		}else{
			list = dao.findByHQL("from Otherlink b where b.isDelete = 0 and b.sort = ?", sort);
		}
		
		if(list.size() > 0){
			return false;
		}else{
			return true;
		}
	}

}
