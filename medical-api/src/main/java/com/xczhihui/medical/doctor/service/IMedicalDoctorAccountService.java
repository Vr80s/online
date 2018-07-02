package com.xczhihui.medical.doctor.service;


import com.baomidou.mybatisplus.service.IService;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface IMedicalDoctorAccountService extends IService<MedicalDoctorAccount> {

    /**
     * Description：通过用户信息直接得到医师信息
     * creed: Talk is cheap,show me the code
     *
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/6/20 18:18
     **/
    MedicalDoctorAccount getByUserId(String userId);
}
