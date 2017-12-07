package com.xczhihui.bxg.online.manager.medical.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.MedicalField;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.medical.dao.FieldDao;
import com.xczhihui.bxg.online.manager.medical.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
		String sql="select * from score_type where is_delete=0 and status=1 and id<>0 order by sort";
		Map<String,Object> params=new HashMap<String,Object>();
		List<MedicalField> voList=medicalFieldDao.findEntitiesByJdbc(MedicalField.class, sql, params);
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


}
