package com.xczhihui.course.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.vo.OnlineUserVO;


public interface IMyInfoService {

    /**
     * Description：根据用户id查找得到   查找主播的学员、课程数
     *
     * @param userId
     * @return List<BigDecimal>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    List<BigDecimal> selectCollegeCourseXmbNumber(String userId);

    /**
     * 结算页面记录信息
     * Description：
     *
     * @param id
     * @return List<Map<String,Object>>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    List<Map<String, Object>> selectSettlementList(Integer pageNumber, Integer pageSize, String id);

    /**
     * 提现页面记录信息
     * Description：
     *
     * @param id
     * @return List<Map<String,Object>>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    List<Map<String, Object>> selectWithdrawalList(Integer pageNumber, Integer pageSize, String id);

    /**
     * 更改用户信息
     * Description：
     *
     * @param user
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    void updateUserSetInfo(OnlineUserVO user);

    /**
     * Description：获取用户权限：0 普通用户  1 主播
     *
     * @param id
     * @return Integer
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    Integer getUserHostPermissions(String id);

    /**
     * Description：获取推荐的主播医师
     *
     * @return List<Map<String,String>>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    List<Map<String, Object>> hostInfoRec();

    Page<Map<String, Object>> findUserWallet(Page<Map<String, Object>> page, String id);

    /**
     * 查询主播信息
     *  不包含坐诊时间
     * @param userId
     * @return
     */
    Map<String, String> findHostInfoById(String userId);


    Map<String, Object> findDoctorInfoById(String userId);

    Map<String, Object> findDoctorInfoByDoctorId(String doctorId);

    /**
     * 通过用户id查看这个用户的主播权限
     * @param id
     * @return
     */
    Map<String, Object> findHostTypeByUserId(String id);

    /**
     * 查询原来用户主页的数据
     * @param object
     * @param lecturerId
     * @param boolean1
     * @return
     */
    Map<String, Object> selectUserHomePageData(String userId, String lecturerId, Boolean boolean1);

    /**
     * 通过用户信息获取主播信息，但是如果主播是医师的化，部分字段替换为医师简介
     *    包括坐诊时间
     * @param userId
     * @return
     */
    Map<String, String> findHostInfoByIdProbablyPhysician(String userId);
}
