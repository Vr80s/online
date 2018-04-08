package com.xczhihui.course.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.TrackRecord;
import com.xczhihui.course.vo.TrackRecordVo;
import com.xczhihui.common.dao.HibernateDao;

/**
 * 云课堂课程管理DAO
 *
 * @author yxd
 */
@Repository("trackRecordDao")
public class TrackRecordDao extends HibernateDao<TrackRecord> {
    public Page<TrackRecordVo> findTrackRecordPage(TrackRecordVo vo, int pageNumber, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("applyId", vo.getApplyId());
        paramMap.put("gradeId", vo.getGradeId());
        StringBuilder sql = new StringBuilder();
        sql.append("select otr.id,otr.apply_id as applyId,otr.record_time as recordTime,otr.record_content as recordContent,otr.lecturer_id as lecturerId,ol.name as lecturerName from oe_track_record otr left join user ol on otr.create_person=ol.id where otr.apply_id=:applyId and otr.grade_id=:gradeId and otr.is_delete=0 and status=1");
//        sql.append("select otr.id,otr.apply_id as applyId,otr.record_time as recordTime,otr.record_content as recordContent,otr.lecturer_id as lecturerId,ol.name as lecturerName from oe_track_record otr left join oe_lecturer ol on otr.lecturer_id=ol.id where otr.apply_id=:applyId and otr.grade_id=:gradeId and otr.is_delete=0 and status=1");
        return this.findPageBySQL(sql.toString(), paramMap, TrackRecordVo.class, pageNumber, pageSize);
    }
}