package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.Apply;
import com.xczh.consumer.market.dao.BasicSimpleDao;
import com.xczh.consumer.market.service.ApplyService;
import com.xczh.consumer.market.utils.JdbcUtil;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeAnnos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.UUID;

/**
 * @author liutao
 * @create 2017-09-20 15:45
 **/
@Service
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    private BasicSimpleDao basicSimpleDao;

    @Override
    public void saveOrUpdateBaseInfo(String userId,String realName, String phone) {


        try {
            Apply apply=  get(userId);
           if(apply!=null){
               basicSimpleDao.update(JdbcUtil.getCurrentConnection(),"update oe_apply set real_name=?,mobile=? where user_id=? ",realName,phone,userId);
           }else{
               basicSimpleDao.update(JdbcUtil.getCurrentConnection(),"insert into oe_apply(id,real_name,mobile,user_id) values(?,?,?,?)", UUID.randomUUID().toString().replaceAll("-",""),realName,phone,userId);
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void updateDetailsInfo(Apply apply) {

        try {
            basicSimpleDao
                    .update(JdbcUtil.getCurrentConnection()
                            ,"update oe_apply set wechat_no=?,id_card_no=?,qq=?,email=?,occupation=?,referee=?,sex=?,is_first=?" +
                                    " where user_id=? ",apply.getWechatNo(),apply.getIdCardNo(),apply.getQq(),apply.getEmail(),apply.getOccupation(),apply.getReferee(),apply.getSex(),apply.getIsFirst(),apply.getUserId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("操作失败!");
        }

    }

    @Override
    public Apply get(String userId) {
        String sql="select is_first isFirst,referee,occupation,wechat_no wechatNo,id,user_id userId,create_time createTime,real_name" +
                " realName,school_id schoolId,sex,birthday,mobile,email,qq,student_number studentNumber,id_card_no idCardNo,is_old_user isOldUser from oe_apply where user_id=? ";
        try {
        return    basicSimpleDao.query(JdbcUtil.getCurrentConnection(),sql,new BeanHandler<Apply>(Apply.class),userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
