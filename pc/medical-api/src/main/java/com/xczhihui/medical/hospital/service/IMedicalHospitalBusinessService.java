package com.xczhihui.medical.hospital.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.hospital.model.MedicalHospital;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface IMedicalHospitalBusinessService {

    /**
     * Description：获取医馆分页信息
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 2:03 2017/12/10 0010
     **/
    public Page<MedicalHospital> selectHospitalPage(Page<MedicalHospital> page);

    /**
     * Description：通过医馆id获取医馆详细信息
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 2:03 2017/12/10 0010
     **/
    public MedicalHospital selectHospitalById(String id);

}
