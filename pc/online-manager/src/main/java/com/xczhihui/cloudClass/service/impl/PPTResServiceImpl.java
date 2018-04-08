package com.xczhihui.cloudClass.service.impl;

import java.util.HashMap;
import java.util.List;

import com.xczhihui.cloudClass.service.PPTResService;
import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.HttpUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Files;
import com.xczhihui.cloudClass.dao.PPTResDao;

@Service("pptResService")
public class PPTResServiceImpl implements PPTResService {
	 @Autowired
	 PPTResDao pptResDao;
	@Override
	public Page<Files> findPage(Files searchVo, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		return pptResDao.findPPTResPage(searchVo, currentPage, pageSize);
	}
	@Override
	public Page<Files> findCasePage(Files searchVo, int currentPage,
			int pageSize,String domain) {
		// TODO Auto-generated method stub
		return pptResDao.findCaseResPage(searchVo, currentPage, pageSize,domain);
	}
	@Override
	public void save(Files ppt) {
		// TODO Auto-generated method stub
		pptResDao.save(ppt);
	}
	@Override
	public void updatePPT(Files ppt,String isuploadFile) {
		// TODO Auto-generated method stub
		Files entity= pptResDao.findOneEntitiyByProperty(Files.class, "id", ppt.getId());
		if ("1".equals(isuploadFile)) {
			entity.setFileUrl(ppt.getFileUrl());
			entity.setSuffix(ppt.getFileUrl().substring(ppt.getFileUrl().lastIndexOf(".")));
		}
		entity.setName(ppt.getName());
		entity.setVersion(ppt.getVersion());
		entity.setDescription(ppt.getDescription());
		pptResDao.update(entity);
	}
	@Override
	public void deletePPTById(String id) {
		// TODO Auto-generated method stub
		Files entity = pptResDao.findOneEntitiyByProperty(Files.class, "id", id);
		entity.setDelete(true);
		pptResDao.update(entity);
	}
	@Override
	public void updateStatus(String id) {
		// TODO Auto-generated method stub
		Files entity=pptResDao.findOneEntitiyByProperty(Files.class, "id", id);
		
        if(entity!=null&&entity.getStatus()==1){
        	entity.setStatus(0);
        }else{
        	entity.setStatus(1);
        }
        
        String sql="select * from oe_files where course_id = "+entity.getCourseId()+" and chapter_id = '"+entity.getChapterId()+"' and is_delete=0 and status =1 and type = " +entity.getType()+" and id != '"+entity.getId()+"'";
        List<Files> temps= pptResDao.findEntitiesByJdbc(Files.class, sql, new HashMap<String, Object>());
        
        if(temps.size()>0&&0==entity.getType()){
        	throw new RuntimeException ("只能启用1条PPT数据！");
        }else if(temps.size()>0&&1==entity.getType()){
        	throw new RuntimeException ("只能启用1条教案数据！");
        }
		
        pptResDao.update(entity);
	}
	@Override
	public void deletes(String[] ids) {
		// TODO Auto-generated method stub
		for(String id:ids){
			Files entity=pptResDao.findOneEntitiyByProperty(Files.class, "id", id);
			entity.setDelete(true);
			pptResDao.update(entity);
			
        }
	}
	
	@Override
	public String mdFile2Html(String id) throws Exception {
		String sql = "select file_url from oe_files where id = ?";
		String url = pptResDao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql, String.class,id);
		byte[] arr = HttpUtil.sendGetRequestBytes(url);
		PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);
		return pdp.markdownToHtml(new String(arr,"utf-8"));
	}
}
