package com.xczh.consumer.market.service;

import com.xczh.consumer.market.vo.MenuVo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/18 0:17
 */
public interface MenuService {

    List<MenuVo> list()throws SQLException;

    List<MenuVo> offlineCity()throws SQLException;
}
