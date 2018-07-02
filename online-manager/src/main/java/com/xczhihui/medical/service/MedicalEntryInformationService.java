package com.xczhihui.medical.service;


import java.util.List;

import com.xczhihui.bxg.online.common.domain.MedicalEntryInformation;
import com.xczhihui.common.util.bean.Page;

/**
 * Description：师承-报名
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/5/21 16:07
 **/
public interface MedicalEntryInformationService {
    /**
     * 报名列表
     */

    Page<MedicalEntryInformation> findEntryInformationPage(MedicalEntryInformation edicalEnrollmentRegulations, int pageNumber, int pageSize);

    /**
     * 查看详情-报名
     */
    MedicalEntryInformation entryInformationDetail(Integer id);

    /**
     * 更新简章-报名
     */
    public void update(MedicalEntryInformation mer);

    /**
     * 报名列表
     */
    List<MedicalEntryInformation> getAllMedicalEntryInformationList(Integer merId);

    /**
     * 是否为徒弟
     *
     * @return
     */
    public void updateIsApprentice(Integer id, Integer apprentice);

}
