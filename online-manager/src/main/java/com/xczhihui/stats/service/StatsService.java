package com.xczhihui.stats.service;

import java.util.Date;
import java.util.Map;

/**
 * @author hejiwei
 */
public interface StatsService {

    /**
     * 统计数据汇总
     *
     * @param startTime startTime
     * @param endTime   endTime
     * @return
     */
    Map<String, Object> getStatsData(Date startTime, Date endTime);
}
