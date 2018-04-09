package com.xczhihui.medical.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.MedicalField;

import java.util.List;

/**
 * MenuService:菜单业务层接口类 * @author Rongcai Kang
 */
public interface FieldService {
	/**
	 * 查询课程列表数据
	 * 
	 * @param menuVo
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<MedicalField> findMenuPage(MedicalField menuVo,
			Integer pageNumber, Integer pageSize);

	/**
	 * 查询课程类别名称是否存在
	 * 
	 * @param name
	 * @return
	 */
	public MedicalField findMedicalFieldByName(String name);

	public List<MedicalField> list();

	/**
	 * 保存实体
	 * 
	 * @param entity
	 */
	public void save(MedicalField entity);

	/**
	 * 判断实体是否存在
	 * 
	 * @param searchEntity
	 * @return
	 */
	public boolean exists(MedicalField searchEntity);

	/**
	 * 更新课程类别
	 * 
	 * @param me
	 */
	public void update(MedicalField me);

	/**
	 * 通过id进行查找
	 * 
	 * @param string
	 * @return
	 */
	public MedicalField findById(String string);

	/**
	 * 删除数据
	 * 
	 * @param _ids
	 * @return
	 */
	public String deletes(String[] _ids);

	/**
	 * 修改启用禁用状态
	 * 
	 * @param id
	 */
	public void updateStatus(String id);

	List<MedicalField> findAllField(String id, Integer type);

	void addHospitalField(String id, String[] field);

	void addDoctorField(String id, String[] fieldId);
}
