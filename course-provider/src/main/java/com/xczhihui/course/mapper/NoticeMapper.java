package com.xczhihui.course.mapper;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.course.model.Notice;

/**
 * @author hejiwei
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    @Select("select * from oe_notice" +
            " where show_start_time <= now() and  now() <= show_end_time and status = 1 and is_delete = 0" +
            " order by create_time desc" +
            " limit 1")
    Notice findNewest();
}
