package com.xczhihui.bxg.online.manager.medical.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorField;
import com.xczhihui.bxg.online.common.domain.MedicalField;
import com.xczhihui.bxg.online.common.domain.MedicalHospitalField;
import com.xczhihui.bxg.online.manager.medical.dao.FieldDao;
import com.xczhihui.bxg.online.manager.medical.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *   MenuServiceImpl:菜单业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class FieldServiceImpl extends OnlineBaseServiceImpl implements FieldService {

    @Autowired
    private FieldDao medicalFieldDao;


    /**
     * 查询列表
     */
    @Override
    public Page<MedicalField> findMenuPage(MedicalField menuVo, Integer pageNumber, Integer pageSize)  {
        Page<MedicalField> page = medicalFieldDao.findMedicalFieldPage(menuVo, pageNumber, pageSize);
        return page;
    }


    /**
     * 查找输入的课程类别是否存在
     */
	@Override
	public MedicalField findMedicalFieldByName(String name) {
		return medicalFieldDao.findOneEntitiyByProperty(MedicalField.class,"name",name);
	}

	@Override
	public List<MedicalField> list() {
		String sql="select * from medical_field where deleted=0 and status=1 order by  convert(name using gbk) ASC";
//		Map<String,Object> params=new HashMap<String,Object>();
		List<MedicalField> voList=medicalFieldDao.findEntitiesByJdbc(MedicalField.class, sql, null);
		return voList;
	}


	/**
	 * 保存实体
	 */
	@Override
	public void save(MedicalField entity) {
		String id = UUID.randomUUID().toString().replace("-","");
		entity.setId(id);
		medicalFieldDao.save(entity);
	}


	@Override
	public boolean exists(MedicalField searchEntity) {
		//输入了一个名称 这个名称数据库已经存在了
        MedicalField she=medicalFieldDao.findByNotEqId(searchEntity);
        if(she!=null){
            return true;
        }
        return false;
	}


	@Override
	public void update(MedicalField me) {
		medicalFieldDao.update(me);
	}


	@Override
	public MedicalField findById(String parseInt) {
		return medicalFieldDao.findById(parseInt);
	}


	@Override
	public String deletes(String[] _ids) {
		String msg = "";
        for(String id:_ids){
        	msg = medicalFieldDao.deleteById(id);
        }
        return  msg;

	}

	@Override
	public void updateStatus(String id) {
		MedicalField medicalField=medicalFieldDao.findById(id);
		if(medicalField.getStatus()){
			medicalField.setStatus(false);
		}else{
			medicalField.setStatus(true);
		}
		medicalFieldDao.update(medicalField);

	}

	@Override
	public List<MedicalField> findAllField(String id, Integer type) {
		List<MedicalField> list = list();
		if(type == 1){//医馆
			List<MedicalHospitalField> mhfs = dao.findEntitiesByProperty(MedicalHospitalField.class, "hospitalId", id);
			for (int i = 0; i < mhfs.size(); i++) {
				for (int j = 0; j < list.size(); j++) {
					if(mhfs.get(i).getFieldId().equals(list.get(j).getId())){
						list.get(j).setHas(true);
					}
				}
			}
		}else{//医师
			List<MedicalDoctorField> mdfs = dao.findEntitiesByProperty(MedicalDoctorField.class, "doctorId", id);
			for (int i = 0; i < mdfs.size(); i++) {
				for (int j = 0; j < list.size(); j++) {
					if(mdfs.get(i).getFieldId().equals(list.get(j).getId())){
						list.get(j).setHas(true);
					}
				}
			}
		}
		return list;
	}

	@Override
	public void addHospitalField(String id, String[] field) {
		List<MedicalHospitalField> mhfs = dao.findEntitiesByProperty(MedicalHospitalField.class, "hospitalId", id);
		for (int i = 0; i < mhfs.size(); i++) {
			dao.delete(mhfs.get(i));
		}
		for (int i = 0; i < field.length; i++) {
			MedicalHospitalField medicalHospitalField = new MedicalHospitalField();
			String mid = UUID.randomUUID().toString().replace("-","");
			medicalHospitalField.setId(mid);
			medicalHospitalField.setFieldId(field[i]);
			medicalHospitalField.setHospitalId(id);
			medicalHospitalField.setCreateTime(new Date());
			dao.save(medicalHospitalField);
		}
	}

	@Override
	public void addDoctorField(String id, String[] fieldId) {
		List<MedicalDoctorField> mhfs = dao.findEntitiesByProperty(MedicalDoctorField.class, "doctorId", id);
		for (int i = 0; i < mhfs.size(); i++) {
			dao.delete(mhfs.get(i));
		}
		for (int i = 0; i < fieldId.length; i++) {
			MedicalDoctorField medicalDoctorField = new MedicalDoctorField();
			String mid = UUID.randomUUID().toString().replace("-","");
			medicalDoctorField.setId(mid);
			medicalDoctorField.setFieldId(fieldId[i]);
			medicalDoctorField.setDoctorId(id);
			medicalDoctorField.setCreateTime(new Date());
			dao.save(medicalDoctorField);
		}
	}


}
