package com.xczhihui.bxg.online.manager.medical.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.bxg.online.manager.medical.dao.DepartmentDao;
import com.xczhihui.bxg.online.manager.medical.enums.MedicalExceptionEnum;
import com.xczhihui.bxg.online.manager.medical.exception.MedicalException;
import com.xczhihui.bxg.online.manager.medical.service.DepartmentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *   MenuServiceImpl:菜单业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class DepartmentServiceImpl extends OnlineBaseServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;


    /**
     * 查询列表
     */
    @Override
    public Page<MedicalDepartment> findMenuPage(MedicalDepartment menuVo, Integer pageNumber, Integer pageSize)  {
        Page<MedicalDepartment> page = departmentDao.findMedicalDepartmentPage(menuVo, pageNumber, pageSize);
        return page;
    }


    /**
     * 查找输入的课程类别是否存在
     */
	@Override
	public MedicalDepartment findMedicalDepartmentByName(String name) {
		return departmentDao.findOneEntitiyByProperty(MedicalDepartment.class,"name",name);
	}

	@Override
	public List<MedicalDepartment> list() {
		String sql="select * from medical_Department where deleted=0 and status=1 order by  convert(name using gbk) ASC";
//		Map<String,Object> params=new HashMap<String,Object>();
		List<MedicalDepartment> voList=departmentDao.findEntitiesByJdbc(MedicalDepartment.class, sql, null);
		return voList;
	}


	/**
	 * 保存实体
	 */
	@Override
	public void save(MedicalDepartment entity) {
		String id = UUID.randomUUID().toString().replace("-","");
		entity.setId(id);
		departmentDao.save(entity);
	}


	@Override
	public boolean exists(MedicalDepartment searchEntity) {
		//输入了一个名称 这个名称数据库已经存在了
        MedicalDepartment she=departmentDao.findByNotEqId(searchEntity);
        if(she!=null){
            return true;
        }
        return false;
	}


	@Override
	public void update(MedicalDepartment me) {
		departmentDao.update(me);
	}


	@Override
	public MedicalDepartment findById(String parseInt) {
		return departmentDao.findById(parseInt);
	}


	@Override
	public String deletes(String[] ids) {
		String msg = "";
        for(String id:ids){
        	msg = departmentDao.deleteById(id);
        }
        return  msg;

	}

	@Override
	public void updateStatus(String id) {
		MedicalDepartment medicalDepartment=departmentDao.findById(id);
		if(medicalDepartment.getStatus()){
			medicalDepartment.setStatus(false);
		}else{
			medicalDepartment.setStatus(true);
		}
		departmentDao.update(medicalDepartment);

	}

	@Override
	public List<MedicalDepartment> findAllDepartment(String id) {
		List<MedicalDepartment> list = list();
		List<MedicalDoctorDepartment> mdfs = dao.findEntitiesByProperty(MedicalDoctorDepartment.class, "doctorId", id);
		for (int i = 0; i < mdfs.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if(mdfs.get(i).getDepartmentId().equals(list.get(j).getId())){
					list.get(j).setHas(true);
				}
			}
		}
		return list;
	}

	/**
	 * 修改医师的科室信息
	 * @param id 医师id
	 */
	@Override
	public void addDoctorDepartment(String id, String[] departmentId) {

		// 根据医师id获取医师详情
		List<MedicalDoctor> doctors = dao.findEntitiesByProperty(MedicalDoctor.class, "id", id);
		if(CollectionUtils.isEmpty(doctors)){
			throw new MedicalException(MedicalExceptionEnum.DOCTOR_NOT_EXIT);
        }else{
			if(StringUtils.isNotBlank(doctors.get(0).getSourceId())){
                throw new MedicalException(MedicalExceptionEnum.MUST_NOT_HANDLE);
            }
		}

		List<MedicalDoctorDepartment> mhfs = dao.findEntitiesByProperty(MedicalDoctorDepartment.class, "doctorId", id);
		for (int i = 0; i < mhfs.size(); i++) {
			dao.delete(mhfs.get(i));
		}
		if(departmentId!=null){
			for (int i = 0; i < departmentId.length; i++) {
				MedicalDoctorDepartment medicalDoctorDepartment = new MedicalDoctorDepartment();
				String mid = UUID.randomUUID().toString().replace("-","");
				medicalDoctorDepartment.setId(mid);
				medicalDoctorDepartment.setDepartmentId(departmentId[i]);
				medicalDoctorDepartment.setDoctorId(id);
				medicalDoctorDepartment.setCreateTime(new Date());
				dao.save(medicalDoctorDepartment);
			}
		}
	}


}
