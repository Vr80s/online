package com.xczhihui.stats.service.impl;

import com.google.common.collect.ImmutableMap;
import com.xczhihui.common.util.enums.ClientType;
import com.xczhihui.common.util.enums.OrderFrom;
import com.xczhihui.stats.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public Map<String, Object> getStatsDataLine(Date startTime, Date endTime) {
        return ImmutableMap.of("userStatsDataLine", getUserDataLine(startTime, endTime),"courseStatsDataLine", getCourseDataLine(startTime, endTime),
                "anchorStatsDataLine", getAnchorDataLine(startTime, endTime),"orderStatsDataLine", getOrderDataLine(startTime, endTime),
                "incomeStatsDataLine", getUserIncomeDataLine(startTime, endTime));
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
        return turnOrderFromName(namedParameterJdbcTemplate.queryForList(sql, param));
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
        return turnOrderFromName(namedParameterJdbcTemplate.queryForList(sql, param));
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
    private List<Map<String, Object>> turnOrderFromName(List<Map<String, Object>> dataList) {
        if (dataList.isEmpty()) {
            dataList.add(ImmutableMap.of("name", "暂无数据", "value", 0));
        } else {
            dataList.forEach(data -> {
                String name = Optional.ofNullable(data.get("name"))
                        .map(nameObj -> OrderFrom.getOrderFrom(Integer.parseInt(nameObj.toString())))
                        .orElse(OrderFrom.OTHER).getText();
                data.put("name", name);
            });
        }
        return dataList;
    }
    //统计用户sql
    private String getUserSql(Integer clientType,String name) {
        String userSql = "select createTime ,max(value) as value,'"+name+"' as name from  (" +
                "SELECT @cdate := date_add(@cdate,interval - 1 day) AS createTime , 0 as value, 1 AS name " +
                "from (SELECT @cdate :=date_add(date_format( :endTime , '%Y-%m-%d' ),interval + 1 day) from oe_user) t1 where @cdate > date_format( :startTime , '%Y-%m-%d' ) " +
                "union all " +
                "SELECT date_format( create_time , '%Y-%m-%d' ) as createTime , count(id) AS value, origin AS name FROM oe_user " +
                "WHERE create_time <= date_format( :endTime , '%Y-%m-%d %H:%i:%S' ) AND origin = "+clientType+" AND " +
                "create_time >= date_format( :startTime , '%Y-%m-%d' ) GROUP BY createTime,origin ORDER BY  createTime " +
                ") _tmpAllTable group by createTime";
        return userSql;
    }
    //统计课程sql
    private String getCourseSql(Integer clientType,String name) {
        String courseSql = "select createTime ,max(value) as value,'"+name+"' as name from  (" +
                "SELECT @cdate := date_add(@cdate,interval - 1 day) AS createTime , 0 as value, 1 AS name " +
                "from (SELECT @cdate :=date_add(date_format( :endTime , '%Y-%m-%d' ),interval + 1 day) from oe_course) t1 where @cdate > date_format( :startTime , '%Y-%m-%d' ) " +
                "union all " +
                "SELECT date_format( create_time , '%Y-%m-%d' ) as createTime , count(id) AS value, client_type AS name FROM oe_course " +
                "WHERE create_time <= date_format( :endTime , '%Y-%m-%d %H:%i:%S' ) AND client_type = "+clientType+" AND " +
                "create_time >= date_format( :startTime , '%Y-%m-%d' ) GROUP BY createTime,client_type ORDER BY  createTime " +
                ") _tmpAllTable group by createTime";
        return courseSql;
    }
    //统计主播sql
    private String getAnchorSql(Integer clientType,String name) {
        String anchorSql = "select createTime ,max(value) as value,'"+name+"' as name from  (" +
                "SELECT @cdate := date_add(@cdate,interval - 1 day) AS createTime , 0 as value, 1 AS name " +
                "from (SELECT @cdate :=date_add(date_format( :endTime , '%Y-%m-%d' ),interval + 1 day) from course_anchor) t1 where @cdate > date_format( :startTime , '%Y-%m-%d' ) " +
                "union all " +
                "SELECT date_format( create_time , '%Y-%m-%d' ) as createTime , count(id) AS value, client_type AS name FROM course_anchor " +
                "WHERE create_time <= date_format( :endTime , '%Y-%m-%d %H:%i:%S' ) AND client_type = "+clientType+" AND " +
                "create_time >= date_format( :startTime , '%Y-%m-%d' ) GROUP BY createTime,client_type ORDER BY  createTime " +
                ") _tmpAllTable group by createTime";
        return anchorSql;
    }
    //统计订单sql
    private String getOrderSql(Integer clientType,String name) {
        String anchorSql = "select createTime ,max(value) as value,'"+name+"' as name from  (" +
                "SELECT @cdate := date_add(@cdate,interval - 1 day) AS createTime , 0 as value, 1 AS name " +
                "from (SELECT @cdate :=date_add(date_format( :endTime , '%Y-%m-%d' ),interval + 1 day) from oe_order) t1 where @cdate > date_format( :startTime , '%Y-%m-%d' ) " +
                "union all " +
                "SELECT date_format( create_time , '%Y-%m-%d' ) as createTime , count(id) AS value, order_from AS name FROM oe_order " +
                "WHERE create_time <= date_format( :endTime , '%Y-%m-%d %H:%i:%S' ) AND order_from = "+clientType+" AND " +
                "create_time >= date_format( :startTime , '%Y-%m-%d' ) GROUP BY createTime,order_from ORDER BY  createTime " +
                ") _tmpAllTable group by createTime";
        return anchorSql;
    }
    //统计收入sql
    private String getUserIncomeSql(Integer clientType,String name) {
        String anchorSql = "select createTime ,max(value) as value,'"+name+"' as name from  (" +
                "SELECT @cdate := date_add(@cdate,interval - 1 day) AS createTime , 0 as value, 1 AS name " +
                "from (SELECT @cdate :=date_add(date_format( :endTime , '%Y-%m-%d' ),interval + 1 day) from user_coin_increase) t1 where @cdate > date_format( :startTime , '%Y-%m-%d' ) " +
                "union all " +
                "SELECT date_format( create_time , '%Y-%m-%d' ) as createTime , sum(value) AS value, order_from AS name FROM user_coin_increase " +
                "WHERE create_time <= date_format( :endTime , '%Y-%m-%d %H:%i:%S' ) AND order_from = "+clientType+" AND (change_type = 3 OR change_type = 7) AND " +
                "create_time >= date_format( :startTime , '%Y-%m-%d' ) GROUP BY createTime,order_from ORDER BY  createTime " +
                ") _tmpAllTable group by createTime";
        return anchorSql;
    }

    //折线图统计
    private List<List<Map<String, Object>>> getUserDataLine(Date startTime, Date endTime) {
        Map<String, Object> param = new HashMap<>(2);
        Map<String, Object> result = new LinkedHashMap<>();
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        String sql = "SELECT count(id) AS value, origin AS name, date_format( create_time , '%Y-%m-%d' ) AS createTime FROM oe_user WHERE create_time <= :endTime AND create_time >= :startTime " +
                " GROUP BY date_format( create_time , '%Y-%m-%d' ),origin ORDER BY  date_format( create_time , '%Y-%m-%d' )";

        String userSqlUnknown = "select createTime ,max(value) as value,'未知' as name from  (" +
                "SELECT @cdate := date_add(@cdate,interval - 1 day) AS createTime , 0 as value, 1 AS name " +
                "from (SELECT @cdate :=date_add(date_format( :endTime , '%Y-%m-%d' ),interval + 1 day) from oe_user) t1 where @cdate > date_format( :startTime , '%Y-%m-%d' ) " +
                "union all " +
                "SELECT date_format( create_time , '%Y-%m-%d' ) as createTime , count(id) AS value, origin AS name FROM oe_user WHERE create_time <= date_format( :endTime , '%Y-%m-%d' ) AND origin is null AND " +
                "create_time >= date_format( :startTime , '%Y-%m-%d' ) GROUP BY date_format( create_time , '%Y-%m-%d' ),origin ORDER BY  date_format( createTime , '%Y-%m-%d' ) " +
                ") _tmpAllTable group by createTime";

        List<Map<String, Object>> listUserUnknown = namedParameterJdbcTemplate.queryForList(userSqlUnknown, param);
        List<Map<String, Object>> listUserPC = namedParameterJdbcTemplate.queryForList(getUserSql(1,"pc"), param);
        List<Map<String, Object>> listUserH5 = namedParameterJdbcTemplate.queryForList(getUserSql(2,"h5"), param);
        List<Map<String, Object>> listUserAndroid = namedParameterJdbcTemplate.queryForList(getUserSql(3,"android"), param);
        List<Map<String, Object>> listUserIos = namedParameterJdbcTemplate.queryForList(getUserSql(4,"ios"), param);
        List<Map<String, Object>> listUserImport = namedParameterJdbcTemplate.queryForList(getUserSql(5,"导入"), param);
        List<List<Map<String, Object>>> l = new ArrayList<>();

        l.add(listUserUnknown);
        l.add(listUserPC);
        l.add(listUserH5);
        l.add(listUserAndroid);
        l.add(listUserIos);
        l.add(listUserImport);

        return l;
    }

    //课程统计
    private List<List<Map<String, Object>>> getCourseDataLine(Date startTime, Date endTime) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        String userSqlUnknown = "select createTime ,max(value) as value,'未知' as name from  (" +
                "SELECT @cdate := date_add(@cdate,interval - 1 day) AS createTime , 0 as value, 1 AS name " +
                "from (SELECT @cdate :=date_add(date_format( :endTime , '%Y-%m-%d' ),interval + 1 day) from oe_course) t1 where @cdate > date_format( :startTime , '%Y-%m-%d' ) " +
                "union all " +
                "SELECT date_format( create_time , '%Y-%m-%d' ) as createTime , count(id) AS value, client_type AS name FROM oe_course WHERE create_time <= date_format( :endTime , '%Y-%m-%d' ) AND client_type is null AND " +
                "create_time >= date_format( :startTime , '%Y-%m-%d' ) GROUP BY date_format( create_time , '%Y-%m-%d' ),client_type ORDER BY  date_format( createTime , '%Y-%m-%d' ) " +
                ") _tmpAllTable group by createTime";

        List<Map<String, Object>> listUserUnknown = namedParameterJdbcTemplate.queryForList(userSqlUnknown, param);
        List<Map<String, Object>> listUserPC = namedParameterJdbcTemplate.queryForList(getCourseSql(1,"pc"), param);
        List<Map<String, Object>> listUserH5 = namedParameterJdbcTemplate.queryForList(getCourseSql(2,"h5"), param);
        List<Map<String, Object>> listUserAndroid = namedParameterJdbcTemplate.queryForList(getCourseSql(3,"android"), param);
        List<Map<String, Object>> listUserIos = namedParameterJdbcTemplate.queryForList(getCourseSql(4,"ios"), param);
        List<Map<String, Object>> listUserImport = namedParameterJdbcTemplate.queryForList(getCourseSql(5,"其他"), param);
        List<List<Map<String, Object>>> l = new ArrayList<>();

        l.add(listUserUnknown);
        l.add(listUserPC);
        l.add(listUserH5);
        l.add(listUserAndroid);
        l.add(listUserIos);
        l.add(listUserImport);

        return l;
    }
    //主播统计
    private List<List<Map<String, Object>>> getAnchorDataLine(Date startTime, Date endTime) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        String userSqlUnknown = "select createTime ,max(value) as value,'未知' as name from  (" +
                "SELECT @cdate := date_add(@cdate,interval - 1 day) AS createTime , 0 as value, 1 AS name " +
                "from (SELECT @cdate :=date_add(date_format( :endTime , '%Y-%m-%d' ),interval + 1 day) from course_anchor) t1 where @cdate > date_format( :startTime , '%Y-%m-%d' ) " +
                "union all " +
                "SELECT date_format( create_time , '%Y-%m-%d' ) as createTime , count(id) AS value, client_type AS name FROM course_anchor WHERE create_time <= date_format( :endTime , '%Y-%m-%d' ) AND client_type is null AND " +
                "create_time >= date_format( :startTime , '%Y-%m-%d' ) GROUP BY date_format( create_time , '%Y-%m-%d' ),client_type ORDER BY  date_format( createTime , '%Y-%m-%d' ) " +
                ") _tmpAllTable group by createTime";

        List<Map<String, Object>> listUserUnknown = namedParameterJdbcTemplate.queryForList(userSqlUnknown, param);
        List<Map<String, Object>> listUserPC = namedParameterJdbcTemplate.queryForList(getAnchorSql(1,"pc"), param);
        List<Map<String, Object>> listUserH5 = namedParameterJdbcTemplate.queryForList(getAnchorSql(2,"h5"), param);
        List<Map<String, Object>> listUserAndroid = namedParameterJdbcTemplate.queryForList(getAnchorSql(3,"android"), param);
        List<Map<String, Object>> listUserIos = namedParameterJdbcTemplate.queryForList(getAnchorSql(4,"ios"), param);
        List<Map<String, Object>> listUserImport = namedParameterJdbcTemplate.queryForList(getAnchorSql(5,"其他"), param);
        List<List<Map<String, Object>>> l = new ArrayList<>();

        l.add(listUserUnknown);
        l.add(listUserPC);
        l.add(listUserH5);
        l.add(listUserAndroid);
        l.add(listUserIos);
        l.add(listUserImport);

        return l;
    }
    //订单统计
    private List<List<Map<String, Object>>> getOrderDataLine(Date startTime, Date endTime) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("startTime", startTime);
        param.put("endTime", endTime);

        List<Map<String, Object>> listUserUnknown = namedParameterJdbcTemplate.queryForList(getOrderSql(-1,"其他"), param);
        List<Map<String, Object>> listUserPresent = namedParameterJdbcTemplate.queryForList(getOrderSql(0,"赠送"), param);
        List<Map<String, Object>> listUserPC = namedParameterJdbcTemplate.queryForList(getOrderSql(1,"pc"), param);
        List<Map<String, Object>> listUserH5 = namedParameterJdbcTemplate.queryForList(getOrderSql(2,"h5"), param);
        List<Map<String, Object>> listUserAndroid = namedParameterJdbcTemplate.queryForList(getOrderSql(3,"android"), param);
        List<Map<String, Object>> listUserIos = namedParameterJdbcTemplate.queryForList(getOrderSql(4,"ios"), param);
        List<Map<String, Object>> listUserOffline = namedParameterJdbcTemplate.queryForList(getOrderSql(5,"线下"), param);
        List<Map<String, Object>> listUserStaff = namedParameterJdbcTemplate.queryForList(getOrderSql(6,"工作人员"), param);
        List<List<Map<String, Object>>> l = new ArrayList<>();

        l.add(listUserUnknown);
        l.add(listUserPresent);
        l.add(listUserPC);
        l.add(listUserH5);
        l.add(listUserAndroid);
        l.add(listUserIos);
        l.add(listUserOffline);
        l.add(listUserStaff);

        return l;
    }

    //收入统计
    private List<List<Map<String, Object>>> getUserIncomeDataLine(Date startTime, Date endTime) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        List<Map<String, Object>> listUserUnknown = namedParameterJdbcTemplate.queryForList(getUserIncomeSql(-1,"其他"), param);
        List<Map<String, Object>> listUserPresent = namedParameterJdbcTemplate.queryForList(getUserIncomeSql(0,"赠送"), param);
        List<Map<String, Object>> listUserPC = namedParameterJdbcTemplate.queryForList(getUserIncomeSql(1,"pc"), param);
        List<Map<String, Object>> listUserH5 = namedParameterJdbcTemplate.queryForList(getUserIncomeSql(2,"h5"), param);
        List<Map<String, Object>> listUserAndroid = namedParameterJdbcTemplate.queryForList(getUserIncomeSql(3,"android"), param);
        List<Map<String, Object>> listUserIos = namedParameterJdbcTemplate.queryForList(getUserIncomeSql(4,"ios"), param);
        List<Map<String, Object>> listUserOffline = namedParameterJdbcTemplate.queryForList(getUserIncomeSql(5,"线下"), param);
        List<Map<String, Object>> listUserStaff = namedParameterJdbcTemplate.queryForList(getUserIncomeSql(6,"工作人员"), param);
        List<List<Map<String, Object>>> l = new ArrayList<>();

        l.add(listUserUnknown);
        l.add(listUserPresent);
        l.add(listUserPC);
        l.add(listUserH5);
        l.add(listUserAndroid);
        l.add(listUserIos);
        l.add(listUserOffline);
        l.add(listUserStaff);

        return l;
    }


    /**
     * 获取某段时这里写代码片间内的所有日期
     * @param dBegin
     * @param dEnd
     * @return
     */
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))  {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

}
