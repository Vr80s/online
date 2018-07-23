package com.xczhihui.medical.doctor.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.DoctorBanner;
import com.xczhihui.medical.doctor.vo.DoctorBannerVO;

/**
 * 医师主页轮播图服务
 *
 * @author hejiwei
 */
public interface IMedicalDoctorBannerService {

    /**
     * 创建医师主页轮播图
     *
     * @param doctorBanner 数据实体
     * @param userId       用户id
     */
    void addBanner(DoctorBanner doctorBanner, String userId);

    /**
     * 更新医师主页轮播图
     *
     * @param doctorBanner 数据实体
     * @param id           数据id
     */
    void updateBanner(DoctorBanner doctorBanner, int id, String userId);

    /**
     * 更新上下架状态
     *
     * @param id     id
     * @param status 状态
     */
    void updateStatus(int id, boolean status, String userId);

    /**
     * 分页参数
     *
     * @param page   page
     * @param size   size
     * @param userId userId
     * @return
     */
    Page<DoctorBanner> list(int page, int size, String userId);

    /**
     * 获取轮播图数据
     *
     * @param id id
     * @return
     */
    DoctorBanner get(int id, String userId);

    /**
     * 查询医师的banner列表
     *
     * @param userId userId
     * @return
     */
    List<DoctorBannerVO> listByUserId(String userId);

    /**
     * 定期下架banner
     *
     * @return
     */
    Integer updateAllUnShelves();

    /**
     * 轮播图删除
     *
     * @param id id
     */
    void delete(int id);
}
