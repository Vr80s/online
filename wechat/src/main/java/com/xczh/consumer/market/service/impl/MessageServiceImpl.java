package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.dao.BasicSimpleDao;
import com.xczh.consumer.market.service.MessageService;
import com.xczh.consumer.market.utils.JdbcUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

/**
 * @author liutao
 * @create 2017-09-07 20:10
 **/
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private BasicSimpleDao basicSimpleDao;

    @Override
    public void add(String content,String userId) {
        try {
        	//content = "<font color=\"#2cb82c\">意见反馈：</font>"+content;
            basicSimpleDao.update(JdbcUtil.getCurrentConnection(),"insert into oe_message(title,id,user_id,context,type,status,is_delete,create_time,readstatus,answerStatus) values(?,?,?,?,?,?,?,?,?,?)", 
            		"无",UUID.randomUUID().toString().replaceAll("-",""),userId,content,2,1,0,new Date(),0,0);
            
            System.out.println("========================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
