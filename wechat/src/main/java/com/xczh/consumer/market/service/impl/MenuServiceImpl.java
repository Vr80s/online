package com.xczh.consumer.market.service.impl;/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/18 0:17
 */

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xczh.consumer.market.dao.BasicSimpleDao;
import com.xczh.consumer.market.service.MenuService;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.MenuVo;

/**
 * @author liutao
 * @create 2017-09-18 0:17
 **/
@Component
public class MenuServiceImpl implements MenuService {

    @Autowired
    private BasicSimpleDao basicSimpleDao;


    @Override
    public List<MenuVo> list() throws SQLException {
        String sql = "SELECT id,name,id as menu_id FROM oe_menu where is_delete = 0 and name <> '全部' and status=1 and yun_status = 1 order by  yun_sort desc ";
        List<MenuVo> list = basicSimpleDao.query(JdbcUtil.getCurrentConnection(), sql, new BeanListHandler<MenuVo>(MenuVo.class));
        return list;
    }
}
