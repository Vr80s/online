package com.xczhihui.medical.hospital.service;


import com.xczhihui.medical.hospital.vo.MedicalHospitalRecruitVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface IMedicalHospitalRecruitBusinessService {

    /**
     * Description：获取最近时间七个医馆招聘信息
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 2:03 2017/12/10 0010
     **/
    public List<MedicalHospitalRecruitVo> selectRecHospitalRecruit();

    /**
     * Description：获取最近时间七个医馆招聘信息
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 2:03 2017/12/10 0010
     **/
    public List<MedicalHospitalRecruitVo> selectHospitalRecruitByHospitalId(String hospitalId);



}
