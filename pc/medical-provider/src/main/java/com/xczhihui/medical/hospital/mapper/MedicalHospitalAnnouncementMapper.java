package com.xczhihui.medical.hospital.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.hospital.model.MedicalHospitalAnnouncement;
import com.xczhihui.medical.hospital.vo.MedicalHospitalAnnouncementVO;

/**
 * @author hejiwei
 */
public interface MedicalHospitalAnnouncementMapper extends BaseMapper<MedicalHospitalAnnouncement> {

    /**
     * 分页查询医馆公告
     *
     * @param page       页码
     * @param hospitalId 医馆id
     * @return 列表数据
     */
    @Select({"select id as id, content as content, create_time as createTime, hospital_id as hospitalId" +
            " from medical_hospital_announcement" +
            " where deleted = false and hospital_id = #{hospitalId}" +
            " order by create_time desc"})
    List<MedicalHospitalAnnouncementVO> list(Page<MedicalHospitalAnnouncementVO> page, @Param("hospitalId") String hospitalId);

    /**
     * 逻辑删除
     *
     * @param id         id
     * @param hospitalId 医馆id
     * @return 更新行数
     */
    @Update("update medical_hospital_announcement set deleted = true where id = #{id} and hospital_id = #{hospitalId} and deleted = false")
    int markDeleted(@Param("id") String id, @Param("hospitalId") String hospitalId);
}
