package com.xczhihui.stats.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.xczhihui.common.util.enums.ClientType;
import com.xczhihui.stats.service.StatsService;

/**
 * @author hejiwei
 */
@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Map<String, Object> getStatsData(Date startTime, Date endTime) {
        return ImmutableMap.of("userStatsData", getUserData(startTime, endTime),
                "anchorStatsData", getAnchorData(startTime, endTime), "courseStatsData", getCourseData(startTime, endTime),
                "orderStatsData", getOrderData(startTime, endTime), "incomeStatsData", getUserIncomeData(startTime, endTime));
    }

    private List<Map<String, Object>> getUserData(Date startTime, Date endTime) {
        Map<String, Object> param = new HashMap<>(2);
        Map<String, Object> result = new LinkedHashMap<>();
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        String sql = "SELECT count(id) AS value, origin AS name FROM oe_user WHERE create_time <= :endTime AND create_time >= :startTime GROUP BY origin";
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(sql, param);
        return turnName(list);
    }

    private List<Map<String, Object>> getAnchorData(Date startTime, Date endTime) {
        Map<String, Object> param = new HashMap<>(2);
        Map<String, Object> result = new LinkedHashMap<>();
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        String sql = "SELECT count(id) AS value, client_type AS name FROM course_anchor WHERE create_time <= :endTime AND create_time >= :startTime GROUP BY client_type";
        return turnName(namedParameterJdbcTemplate.queryForList(sql, param));
    }

    private List<Map<String, Object>> getCourseData(Date startTime, Date endTime) {
        Map<String, Object> param = new HashMap<>(2);
        Map<String, Object> result = new LinkedHashMap<>();
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        String sql = "SELECT count(id) AS value, client_type AS name FROM oe_course WHERE create_time <= :endTime AND create_time >= :startTime GROUP BY client_type";
        return turnName(namedParameterJdbcTemplate.queryForList(sql, param));
    }

    private List<Map<String, Object>> getOrderData(Date startTime, Date endTime) {
        Map<String, Object> param = new HashMap<>(2);
        Map<String, Object> result = new LinkedHashMap<>();
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        String sql = "SELECT count(id) AS value, order_from as name FROM oe_order WHERE create_time <= :endTime AND create_time >= :startTime GROUP BY order_from";
        return turnName(namedParameterJdbcTemplate.queryForList(sql, param));
    }

    private List<Map<String, Object>> getUserIncomeData(Date startTime, Date endTime) {
        Map<String, Object> param = new HashMap<>(2);
        Map<String, Object> result = new LinkedHashMap<>();
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        String sql = "SELECT sum(value) AS value, order_from as name" +
                " FROM user_coin_increase" +
                " WHERE (change_type = 3 OR change_type = 7)" +
                " and create_time <= :endTime AND create_time >= :startTime" +
                " GROUP BY order_from";
        return turnName(namedParameterJdbcTemplate.queryForList(sql, param));
    }

    private List<Map<String, Object>> turnName(List<Map<String, Object>> dataList) {
        if (dataList.isEmpty()) {
            dataList.add(ImmutableMap.of("name", "暂无数据", "value", 0));
        } else {
            dataList.forEach(data -> {
                String name = Optional.ofNullable(data.get("name"))
                        .map(nameObj -> ClientType.getClientType(Integer.parseInt(nameObj.toString())))
                        .orElse(ClientType.UNKNOWN).getText();
                data.put("name", name);
            });
        }
        return dataList;
    }
}
