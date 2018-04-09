package com.xczhihui.medical.hospital.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.hospital.model.MedicalHospitalRecruit;
import com.xczhihui.medical.hospital.vo.MedicalHospitalRecruitVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface IMedicalHospitalRecruitBusinessService {

    /**
     * Description：获取最近时间七个医馆招聘信息
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 2:03 2017/12/10 0010
     **/
    List<MedicalHospitalRecruitVO> selectRecHospitalRecruit();

    /**
     * Description：获取最近时间七个医馆招聘信息
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 2:03 2017/12/10 0010
     **/
    List<MedicalHospitalRecruitVO> selectHospitalRecruitByHospitalId(String hospitalId);

    /**
     * 医馆招聘列表分页查询
     *
     * @param hospitalId 医馆id
     * @param keyword    关键字
     * @param page       页码
     * @param size       一页几条数据
     * @return
     */
    Page<MedicalHospitalRecruitVO> listByPage(String hospitalId, String keyword, int page, int size);

    /**
     * 新增医馆招聘
     *20180409--yuxin--修改参数，去除onlineUser
     * @param medicalHospitalRecruit 招聘信息
     * @param userId             操作用户
     *
     * @return
     */
    Object save(MedicalHospitalRecruit medicalHospitalRecruit, String userId);

    /**
     * 更新医馆招聘
     *20180409--yuxin--修改参数，去除onlineUser
     * @param id                     招聘信息id
     * @param medicalHospitalRecruit 招聘信息
     * @return
     */
    Object update(String id, MedicalHospitalRecruit medicalHospitalRecruit);

    /**
     * 更新招聘信息的状态
     *
     * @param id     招聘信息id
     * @param status 更新的状态 true->发布 false -> 关闭
     * @return
     */
    Object updateStatus(String id, boolean status);

    /**
     * 逻辑删除招聘信息
     *
     * @param id 招聘信息id
     * @return
     */
    Object delete(String id);

    /**
     * 获取招聘信息
     *
     * @param id 招聘信息id
     * @return
     */
    Object get(String id);
}
