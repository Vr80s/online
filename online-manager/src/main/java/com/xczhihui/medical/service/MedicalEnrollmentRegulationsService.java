package com.xczhihui.medical.service;


import java.util.List;

import com.xczhihui.bxg.online.common.domain.MedicalEnrollmentRegulations;
import com.xczhihui.common.util.bean.Page;

/**
 * Description：师承-招生简章
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/5/21 16:07
 **/
public interface MedicalEnrollmentRegulationsService {
    /**
     * 招生简章列表
     */
    Page<MedicalEnrollmentRegulations> findEnrollmentRegulationsPage(MedicalEnrollmentRegulations edicalEnrollmentRegulations, int pageNumber, int pageSize);

    /**
     * 更新简章-招生简章
     */
    public void update(MedicalEnrollmentRegulations mer);

    /**
     * 通过id进行查找
     */
    public MedicalEnrollmentRegulations findById(Integer id);

    /**
     * 查看详情-招生简章
     */
    MedicalEnrollmentRegulations enrollmentRegulationsDetail(Integer id);

    /**
     * 添加-招生简章
     */
    public void save(MedicalEnrollmentRegulations mer);

    /**
     * 修改状态(禁用or启用)
     *
     * @return
     */
    public void updateStatus(Integer id);

    /**
     * 报名列表
     */
    List<MedicalEnrollmentRegulations> getAllMedicalEntryInformationList();

}
