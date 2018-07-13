package com.xczhihui.medical.doctor.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.MedicalDoctorQuestion;
import com.xczhihui.medical.doctor.vo.DoctorQuestionVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface IMedicalDoctorQuestionService {

 
    /**
     * Description：获取医师答疑分页信息
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 2:02 2017/12/10 0010
     **/
    public Page<MedicalDoctorQuestion> selectQuestionByDoctorId(Page<MedicalDoctorQuestion> page, String doctorId);

    /**
     * 增加答疑
     * @param accountId  用户id
     * @param doctorId   医师id
     * @param question   问题描述
     */
    public void addQuestion(String accountId, String doctorId, String question);

    /**
     * 
     * @param page
     * @param userId
     * @param isAnswer  -1 查全部     1  产
     * @return
     */
    public Page<MedicalDoctorQuestion> selectDoctorQuestionByUserId(Page<MedicalDoctorQuestion> page, String userId,
            Integer isAnswer);

    
    public Integer updateQuestion(DoctorQuestionVO doctorQuestionVO);

    public MedicalDoctorQuestion findQuestionDetailsById(Integer questionId);

}
