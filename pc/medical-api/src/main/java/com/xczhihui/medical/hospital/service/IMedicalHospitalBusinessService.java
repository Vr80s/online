package com.xczhihui.medical.hospital.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.field.model.MedicalField;
import com.xczhihui.medical.field.vo.MedicalFieldVO;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;

import java.util.List;

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
    public Page<MedicalHospitalVo> selectHospitalPage(Page<MedicalHospitalVo> page, String name, String field);

    /**
     * Description：通过医馆id获取医馆详细信息
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 2:03 2017/12/10 0010
     **/
    public MedicalHospitalVo selectHospitalById(String id);

    /**
     * Description：获取推荐医馆列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 2:03 2017/12/10 0010
     **/
    public List<MedicalHospitalVo> selectRecHospital();

    List<MedicalFieldVO> getHotField();

    /**
     * 添加医师
     * @author zhuwenbao
     */
    void addDoctor(MedicalDoctor medicalDoctor);

    /**
     * 获取医疗领域（分页）
     * @param page 分页对象
     * @return 医疗领域列表
     */
    Page<MedicalFieldVO> getFieldsPage(Page page);

    /**
     * 修改医馆信息
     * @author zhuwenbao
     */
    void update(MedicalHospital medicalHospital);
}
