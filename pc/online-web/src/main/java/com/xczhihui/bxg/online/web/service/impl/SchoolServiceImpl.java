package com.xczhihui.bxg.online.web.service.impl;/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.web.dao.SchoolDao;
import com.xczhihui.bxg.online.web.service.SchoolService;
import com.xczhihui.bxg.online.web.vo.SchoolVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 学校信息业务层实现类
 * @author 康荣彩
 * @create 2016-08-29 18:32
 */
@Service
public class SchoolServiceImpl  extends OnlineBaseServiceImpl implements SchoolService {

    @Autowired
    private SchoolDao schoolDao;

    /**
     * 根据市级编号 查找对应的学校信息
     * @param cityId
     * @return
     */
    public List<SchoolVo> getSchoolList(String cityId) {
        List<SchoolVo> schools= schoolDao.getSchoolList(cityId);
        return  schools;
    }
}
