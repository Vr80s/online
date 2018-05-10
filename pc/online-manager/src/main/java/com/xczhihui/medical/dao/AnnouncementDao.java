package com.xczhihui.medical.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.online.common.domain.Announcement;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.common.util.bean.Page;

/**
 * @author hejiwei
 */
@Repository
public class AnnouncementDao extends HibernateDao<Announcement> {

    public Page<Announcement> list(int page, int size, String hospitalId) {
        Map<String, Object> param = new HashMap<>(1);
        param.put("hospitalId", hospitalId);
        String sql = "select a.*, h.name as hospitalName" +
                "                from medical_hospital_announcement a join medical_hospital h on a.hospital_id = h.id" +
                "                where (:hospitalId is null OR a.hospital_id = :hospitalId) and a.deleted = false" +
                "                order by a.create_time desc";
        return this.findPageBySQL(sql, param, Announcement.class, page, size);
    }
}
