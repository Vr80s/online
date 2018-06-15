package com.xczhihui.medical.doctor.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.department.vo.MedicalDepartmentVO;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.doctor.vo.MedicalWritingVO;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import com.xczhihui.medical.field.vo.MedicalFieldVO;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface IMedicalDoctorBusinessService {

    /**
     * Description：获取医师分页信息
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 2:02 2017/12/10 0010
     **/
    public Page<MedicalDoctorVO> selectDoctorPage(Page<MedicalDoctorVO> page, Integer type, String hospitalId, String name, String field, String department);

    public MedicalDoctorVO selectDoctorById(String id);

    List<MedicalFieldVO> getHotField();

    List<MedicalDepartmentVO> getHotDepartment();

    List<MedicalDoctorVO> selectRecDoctor();

    List<OeBxsArticleVO> getNewsReports(String doctorId);

    OeBxsArticleVO getNewsReportByArticleId(String doctorId);

    Page<OeBxsArticleVO> getSpecialColumns(Page<OeBxsArticleVO> page, String doctorId);

    OeBxsArticleVO getSpecialColumnDetailsById(String articleId);

    List<MedicalWritingVO> getWritingsByDoctorId(String doctorId);

    MedicalWritingVO getWritingsDetailsById(String writingsId);

    List<MedicalWritingVO> getRecentlyWritings();

    List<OeBxsArticleVO> getRecentlyNewsReports();

    List<OeBxsArticleVO> getHotArticles();

    Page<OeBxsArticleVO> getNewsReportsByPage(Page<OeBxsArticleVO> page, String doctorId);

    List<OeBxsArticleVO> getHotSpecialColumn();

    List<MedicalDoctorVO> getHotSpecialColumnAuthor();

    Page<MedicalWritingVO> getWritingsByPage(Page<MedicalWritingVO> page);

    /**
     * 加入医馆
     *
     * @param medicalDoctor 加入医馆提交的信息
     * @author zhuwenbao
     */
    void joinHospital(MedicalDoctor medicalDoctor);

    /**
     * 获取医师的坐诊时间
     *
     * @param userId 医师id
     * @param type   坐诊时间的类型
     * @author zhuwenbao
     */
    String getWorkTimeById(String userId, Integer type);

    /**
     * 修改医师信息
     *
     * @param uid    修改人id
     * @param doctor 修改的内容
     * @author zhuwenbao
     */
    void update(String uid, MedicalDoctor doctor);

    /**
     * 根据doctorId获取医师详情
     *
     * @author zhuwenbao
     */
    MedicalDoctor selectDoctorByIdV2(String doctorId);

    /**
     * 添加医师
     *
     * @author zhuwenbao
     */
    void add(MedicalDoctor medicalDoctor);

    /**
     * 根据用户id获取其所在的医馆信息
     */
    MedicalHospitalVo getHospital(String uid);

    MedicalDepartment getDepartmentById(String departmentId);

    /**
     * 通过用户id获取医师id
     *
     * @param userId 用户id
     * @return 医师id
     */
    String getDoctorIdByUserId(String userId);

    /**
     * 获取医师信息
     *
     * @param id id
     * @return 医师信息
     */
    MedicalDoctor get(String id);

    /**
     * 通过医师id 查看医师账号信息
     *
     * @param doctorId 医师id
     * @return 医师账号
     */
    MedicalDoctorAccount getByDoctorId(String doctorId);

    /**
     * 随机查询同分类下医师
     *
     * @param type 分类
     * @param size 大小
     * @return 列表
     */
    List<MedicalDoctorVO> listRandomByType(String type, int size);

    /**
     * 查询名医，除推荐值最高的
     * @param type
     * @param pageNumber
     * @param pageSize
     * @return
     */
	List<MedicalDoctorVO> selectDoctorRecommendList4Random(Integer type, Integer pageNumber, Integer pageSize);
	/**
	 * 查找名医关联下医师所包含的课程
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
    List<MedicalDoctorVO>  selectDoctorCouserByAccountId(Integer pageNumber, Integer pageSize);
}
