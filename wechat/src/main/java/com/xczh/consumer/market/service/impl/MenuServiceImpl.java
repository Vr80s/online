package com.xczh.consumer.market.service.impl;/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/18 0:17
 */

import com.xczh.consumer.market.dao.BasicSimpleDao;
import com.xczh.consumer.market.service.MenuService;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.MenuVo;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.print.DocFlavor;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liutao
 * @create 2017-09-18 0:17
 **/
@Component
public class MenuServiceImpl implements MenuService {

    @Autowired
    private BasicSimpleDao basicSimpleDao;


    @Override
    public List<MenuVo> list() throws SQLException{

       /* try {
            List<MenuVo> list=  basicSimpleDao.query(JdbcUtil.getCurrentConnection(),"select * from oe_menu  where is_delete=0 ",new BeanListHandler<MenuVo>(MenuVo.class));
        return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
		String sql="SELECT id,name,id as menu_id FROM oe_menu where is_delete = 0 and name <> '全部' and status=1 and yun_status = 1";
		
		List<MenuVo> list=    basicSimpleDao.query(JdbcUtil.getCurrentConnection(),sql,new BeanListHandler<MenuVo>(MenuVo.class));
        return list;
    }

	@Override
	public List<MenuVo> offlineCity() throws SQLException {
		// TODO Auto-generated method stub
		String sql=" select city as name,count(*) as countNumber from oe_course where city is not null group by city ORDER BY id desc ";
		List<MenuVo> list=    basicSimpleDao.query(JdbcUtil.getCurrentConnection(),sql,new BeanListHandler<MenuVo>(MenuVo.class));
        return list;
	}
}
