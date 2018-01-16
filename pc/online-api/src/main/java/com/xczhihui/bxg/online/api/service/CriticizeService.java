package com.xczhihui.bxg.online.api.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.vo.CriticizeVo;

/**
 * 评论接口：
 * ClassName: CriticizeService.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年11月9日<br>
 */
public interface CriticizeService {
    /**
     * 提交评论
     * Description：
     * @param criticizeVo
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	public void saveCriticize(CriticizeVo criticizeVo) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 *  
	 * Description：得到此视频下的所有评论
	 * @param videoId 视频id
	 * @param name    当前用户登录名，用于判断是否点赞用的
	 * @param pageNumber 
	 * @param pageSize   
	 * @return
	 * @return Page<Criticize>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public Page<CriticizeVo> getVideoCriticize(String videoId, String name, Integer pageNumber, Integer pageSize);
	/**
	 * Description：点赞和取消点赞
	 * @param isPraise
	 * @param criticizeId
	 * @param user
	 * @return
	 * @return Map<String,Object>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public Map<String, Object> updatePraise(Boolean isPraise,String criticizeId, String loginName);
	
	
	
	 /**
     * 提交新的评论
     * Description：
     * @param criticizeVo
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	public void saveNewCriticize(CriticizeVo criticizeVo) throws IllegalAccessException, InvocationTargetException;

	/**
     * 提交回复
     * Description：
     * @param criticizeVo
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	public void saveReply(String content, String criticizeId, String id);
	
}
