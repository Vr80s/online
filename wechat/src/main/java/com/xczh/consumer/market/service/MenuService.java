package com.xczh.consumer.market.service;

import java.sql.SQLException;
import java.util.List;

import com.xczh.consumer.market.vo.MenuVo;

/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/18 0:17
 */
public interface MenuService {

    List<MenuVo> list() throws SQLException;

}
