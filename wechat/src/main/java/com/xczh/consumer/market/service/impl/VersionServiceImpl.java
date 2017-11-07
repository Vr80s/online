package com.xczh.consumer.market.service.impl;/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/7 21:06
 */

import com.xczh.consumer.market.dao.BasicSimpleDao;
import com.xczh.consumer.market.service.VersionService;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.VersionInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * @author liutao
 * @create 2017-09-07 21:06
 **/
@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    private BasicSimpleDao basicSimpleDao;


    @Override
    public VersionInfoVo getNewVersion() {

        try {
            List<VersionInfoVo> list=basicSimpleDao.queryPage(JdbcUtil.getCurrentConnection(),"select version,down_url downUrl,is_must_update isMustUpdate,filename from app_version_info where `status`=1 and is_delete=0 ORDER BY sort desc",1,1,VersionInfoVo.class,null);
            if(list.size()==1){
                return list.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
