package com.xczhihui.medical.service;



import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalDepartment;

import java.util.List;

/**
 *   MenuService:菜单业务层接口类
 * * @author Rongcai Kang
 */
public interface DepartmentService {
	/**
	 * 查询课程列表数据
	 * @param menuVo
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
    public Page<MedicalDepartment> findMenuPage(MedicalDepartment menuVo, Integer pageNumber, Integer pageSize);

    /**
     * 查询课程类别名称是否存在
     * @param name
     * @return
     */
	public MedicalDepartment findMedicalDepartmentByName(String name);

	public List<MedicalDepartment> list();


	/**
	 * 保存实体
	 * @param entity
	 */
	public void save(MedicalDepartment entity);

	/**
	 * 判断实体是否存在
	 * @param searchEntity
	 * @return
	 */
	public boolean exists(MedicalDepartment searchEntity);

	/**
	 * 更新课程类别
	 * @param me
	 */
	public void update(MedicalDepartment me);

	/**
	 *通过id进行查找
	 * @param string
	 * @return
	 */
	public MedicalDepartment findById(String string);

	/**
	 * 删除数据
	 * @param _ids
	 * @return
	 */
	public String deletes(String[] _ids);

	/**
	 * 修改启用禁用状态
	 * @param id
	 */
	public void updateStatus(String id);

    List<MedicalDepartment> findAllDepartment(String id);

    void addDoctorDepartment(String id, String[] departmentId);

	public void updateSortUpRec(String id);

	public void updateSortDownRec(String id);

	/**
	 * Description：设置推荐值
	 * creed: Talk is cheap,show me the code
	 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
	 * @Date: 2018/3/9 14:19
	 **/
	public void updateSort(String id,Integer sort);
}
