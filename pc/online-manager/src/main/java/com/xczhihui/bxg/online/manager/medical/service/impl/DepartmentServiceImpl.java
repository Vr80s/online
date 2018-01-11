package com.xczhihui.bxg.online.manager.medical.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.MedicalDepartment;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorDepartment;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorField;
import com.xczhihui.bxg.online.common.domain.MedicalHospitalField;
import com.xczhihui.bxg.online.manager.medical.dao.DepartmentDao;
import com.xczhihui.bxg.online.manager.medical.service.DepartmentService;
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
	public String deletes(String[] _ids) {
		String msg = "";
        for(String id:_ids){
        	msg = departmentDao.deleteById(id);
        }
        return  msg;

	}

	@Override
	public void updateStatus(String id) {
		MedicalDepartment MedicalDepartment=departmentDao.findById(id);
		if(MedicalDepartment.getStatus()){
			MedicalDepartment.setStatus(false);
		}else{
			MedicalDepartment.setStatus(true);
		}
		departmentDao.update(MedicalDepartment);

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

	@Override
	public void addDoctorDepartment(String id, String[] fieldId) {
		List<MedicalDoctorDepartment> mhfs = dao.findEntitiesByProperty(MedicalDoctorDepartment.class, "doctorId", id);
		for (int i = 0; i < mhfs.size(); i++) {
			dao.delete(mhfs.get(i));
		}
		if(fieldId!=null){
			for (int i = 0; i < fieldId.length; i++) {
				MedicalDoctorDepartment medicalDoctorDepartment = new MedicalDoctorDepartment();
				String mid = UUID.randomUUID().toString().replace("-","");
				medicalDoctorDepartment.setId(mid);
				medicalDoctorDepartment.setDepartmentId(fieldId[i]);
				medicalDoctorDepartment.setDoctorId(id);
				medicalDoctorDepartment.setCreateTime(new Date());
				dao.save(medicalDoctorDepartment);
			}
		}
	}


}
