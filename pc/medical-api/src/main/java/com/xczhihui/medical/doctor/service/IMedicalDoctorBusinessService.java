package com.xczhihui.medical.doctor.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.field.model.MedicalField;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface IMedicalDoctorBusinessService {

    /**
     * Description：获取医师分页信息
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 2:02 2017/12/10 0010
     **/
    public Page<MedicalDoctor> selectDoctorPage(Page<MedicalDoctor> page, Integer type, String hospitalId, String name, String field);

    public MedicalDoctor selectDoctorById(String id);

    List<MedicalField> getHotField();

    List<MedicalDoctor> selectRecDoctor();
}
