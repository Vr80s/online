package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.dao.ShareManageDao;
import com.xczhihui.bxg.online.web.service.ShareManageService;
import com.xczhihui.bxg.online.web.vo.ShareOrderVo;
import com.xczhihui.bxg.online.web.vo.ShareUserVo;
import com.xczhihui.bxg.online.web.vo.SubsidiesVo;
import com.xczhihui.user.center.web.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 分销管理模块业务实现
 * @Author Fudong.Sun【】
 * @Date 2016/12/8 16:11
 */
@Service
public class ShareManageServiceImpl implements ShareManageService{
    @Autowired
    ShareManageDao shareManageDao;

    @Override
    public SubsidiesVo findSubsidies(String userId) {
        return shareManageDao.findSubsidies(userId);
    }

    @Override
    public Page<ShareOrderVo> findShareOrders(String userId, Integer searchCase,String searchContent, Integer level, String startTime, String endTime, Integer pageNumber, Integer pageSize) {
        Page<ShareOrderVo> orders = shareManageDao.findShareOrders(userId, searchCase, searchContent, level, startTime, endTime, pageNumber, pageSize);
        for (ShareOrderVo order : orders.getItems()) {
            if(order.getLevel()==1 || order.getLevel()==2){
                //二级、三级用户用户名要求不显示全名
                String name = order.getLogin_name();
                if(order.getLogin_name().contains("@")){
                    //邮箱显示前两位及@符号之后内容，其他*号代替
                    order.setLogin_name(name.substring(0,2)+"***"+name.substring(name.indexOf("@"),name.length()));
                }else{
                    //手机号显示前三位及后两位，其他*代替
                    order.setLogin_name(name.substring(0,3)+"******"+name.substring(name.length()-2,name.length()));
                }
            }
        }
        return orders;
    }

    /**
     * 查询用户各级别用户人数
     * @return
     */
    @Override
    public Map<String, Object> findUserCount(HttpServletRequest request){
        //获取当前登录用户信息
        OnlineUser u = (OnlineUser) UserLoginUtil.getLoginUser(request);
        return shareManageDao.findUserCount(u);
    }


    /**
     * 获取当前登录用户的所有一级分享用户
     * @param searchCase 0:按照昵称搜索  1:按照用户名搜索
     * @param searchContent 搜索内容
     * @param startTime  搜索开始时间
     * @param endTime  结束时间
     * @param pageNumber 当前页面
     * @param pageSize  每页条数
     * @param request  当前登录用户
     * @return
     */
    @Override
    public Page<ShareUserVo> findOneLevelUser(Integer searchCase, String searchContent, String startTime, String endTime, Integer pageNumber, Integer pageSize, HttpServletRequest request) throws ParseException {
        //获取当前登录用户信息
        OnlineUser u = (OnlineUser) UserLoginUtil.getLoginUser(request);
        return  shareManageDao.findOneLevelUser(searchCase,searchContent,startTime,endTime,pageNumber,pageSize,u);
    }
    @Override
    public OnlineUser saveShareRelation(HttpServletRequest req, OnlineUser onlineUser) {
        /** 从cookie去获取分享者分享码，如果系统存在此分享码的用户，保存分享关系，新用户生成分享码 */
        String shareCode = CookieUtil.getCookieValue(req,"_usercode_");
        OnlineUser shareUser = shareManageDao.findOneEntitiyByProperty(OnlineUser.class, "shareCode", shareCode);//根据分享码查询用户
        if(shareUser!=null && !shareUser.getId().equalsIgnoreCase(onlineUser.getId())){
            onlineUser.setParentId(shareUser.getId());
            onlineUser.setChangeTime(new Date());
            if(onlineUser.getId()!=null){//分享注册不需要修改用户关系，分享购买课程需要修改用户关系
                shareManageDao.update(onlineUser);
            }
        }
        return onlineUser;
    }

    @Override
    public Map<String,Object> updateCheckShareRelation(HttpServletRequest req) {
        Map<String,Object> returnMap = new HashMap<>();
        //获取当前登录用户信息
        OnlineUser onlineUser = (OnlineUser) UserLoginUtil.getLoginUser(req);
        onlineUser = shareManageDao.findOneEntitiyByProperty(OnlineUser.class, "id", onlineUser.getId());//重新查询登录用户信息
        String shareCode = CookieUtil.getCookieValue(req,"_usercode_")==null? "" : CookieUtil.getCookieValue(req,"_usercode_");
        OnlineUser shareUser = shareManageDao.findOneEntitiyByProperty(OnlineUser.class, "shareCode", shareCode);//根据分享码查询用户
        if(shareUser!=null && onlineUser!=null){
        	if (shareUser.getId().equalsIgnoreCase(onlineUser.getId())) {
        		//自己给自己分享
                returnMap.put("isShow",false);
			} else if(onlineUser.getParentId()!=null && !onlineUser.getParentId().equalsIgnoreCase(shareUser.getId())){
				//他人给自己分享，但自己的上级不是这个分享者，提示是否改变用户关系
                returnMap.put("isShow",true);
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("id",onlineUser.getParentId());
                String oldShareMember = shareManageDao.getNamedParameterJdbcTemplate().queryForObject("select name from oe_user where id= :id",paramMap,String.class);
                returnMap.put("oldShareMember",oldShareMember);
                returnMap.put("newShareMember",shareUser.getName());
            } else {
            	//当前登录用户没有上级用户时，通过别人分享课程购买，此时当前登录用户的上次是分享课程用户
            	this.saveShareRelation(req,onlineUser);
                returnMap.put("isShow",false);
            }

        }
        return returnMap;
    }
}
