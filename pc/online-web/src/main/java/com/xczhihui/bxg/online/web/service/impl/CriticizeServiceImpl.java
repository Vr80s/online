package com.xczhihui.bxg.online.web.service.impl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.service.CriticizeService;
import com.xczhihui.bxg.online.api.vo.Criticize;
import com.xczhihui.bxg.online.web.dao.VideoDao;
import com.xczhihui.bxg.online.web.vo.CriticizeVo;

/**
 * 评论接口实现类
 * ClassName: CriticizeServiceImpl.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年11月9日<br>
 */
@Service
public class CriticizeServiceImpl implements CriticizeService {

	@Autowired
	private VideoDao videoDao;
	
	@Override
	public void saveCriticize(Criticize criticize) throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		CriticizeVo cv = new CriticizeVo();
		BeanUtils.copyProperties(cv,criticize);
		videoDao.saveCriticize(cv);
	}

	@Override
	public Page<Criticize> getVideoCriticize(String videoId, String name,
			Integer pageNumber, Integer pageSize) {
		return videoDao.getVideoCriticize(videoId,name,pageNumber,pageSize,Criticize.class);
	}

	/*@Override
	public Page<Criticize> getVideoCriticize(String videoId, String name,
			Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		
		return videoDao.getVideoCriticize(videoId,name, pageNumber, pageSize);
	}*/

}
