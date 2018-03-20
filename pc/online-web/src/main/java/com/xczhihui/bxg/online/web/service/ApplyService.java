package com.xczhihui.bxg.online.web.service;/**
 * Created by admin on 2016/8/31.
 */

import com.xczhihui.bxg.online.common.domain.Apply;
import com.xczhihui.bxg.online.common.domain.OnlineUser;

/**
 * 学员信息业务层接口类
 *
 * @author 康荣彩
 * @create 2016-08-31 12:03
 */
public interface ApplyService {

    /**
     * 保存学员信息，并且对中间表生成数据
     * @return ResponseObject
     */
    public String saveApply(OnlineUser onlineUser,Integer courseId,  Integer gradeId);
    /**
     * 判断一个用户是否老用户
     * @param realName 真实姓名
     * @param idCardNumber 身份证号
     * @param user 登录帐号
     * @return
     * @throws Exception 
     */
    public boolean updateUserOrcheckUser(String realName,String idCardNumber,String lot_no,OnlineUser user) throws Exception;
	Apply getUserApply(String userId);
	void saveApply(Apply apply, OnlineUser onlineUser);
}
