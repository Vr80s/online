package com.xczhihui.medical.doctor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.DoctorBanner;
import com.xczhihui.medical.doctor.vo.DoctorBannerVO;

/**
 * @author hejiwei
 */
public interface MedicalDoctorBannerMapper extends BaseMapper<DoctorBanner> {

    /**
     * 分页查询医师轮播图列表
     *
     * @param userId 用户id
     * @param page   分页参数
     * @return
     */
    @Select({"select id, img_url as imgUrl, type, link_param as linkParam, start_time as startTime, end_time as endTime," +
            " status, user_Id as userId, create_time as createTime" +
            " from doctor_banner " +
            " where user_id = #{userId}" +
            " order by create_time desc"})
    List<DoctorBanner> list(@Param("userId") String userId, Page<DoctorBanner> page);

    /**
     * 查询医师轮播图列表
     *
     * @param userId 用户id
     * @return
     */
    @Select({"select img_url as imgUrl,link_param as linkParam, route_type as routeType, user_id as userId " +
            "             from doctor_banner " +
            "             where  status =1 and (start_time is null or end_time is null or (start_time <= current_date() and end_time >= current_date()))" +
            "             order by create_time desc limit 3"})
    List<DoctorBannerVO> listByUserId(@Param("userId") String userId);

    /**
     * 查询上架的轮播图数量
     *
     * @param userId 用户id
     * @return
     */
    @Select({"select count(id) from doctor_banner where user_id = #{userId} and status is true"})
    int countOnShelf(@Param("userId") String userId);
}
