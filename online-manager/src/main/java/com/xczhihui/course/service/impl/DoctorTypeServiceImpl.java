package com.xczhihui.course.service.impl;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.DoctorType;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.dao.DoctorTypeDao;
import com.xczhihui.course.service.DoctorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题库service实现类
 *
 * @author snow
 */
@Service("DoctorTypeService")
public class DoctorTypeServiceImpl extends OnlineBaseServiceImpl implements
        DoctorTypeService {
	
    @Autowired
    private DoctorTypeDao doctorTypeDao;


	@Override
	public Page<DoctorType> findDoctorTypePage(DoctorType doctorType, int currentPage, int pageSize) {
		 Page<DoctorType> page = doctorTypeDao.findDoctorTypePage(doctorType,
				 currentPage, pageSize);
	     return page;
	}
    

    @Override
    public int getMaxSort() {
        return doctorTypeDao.getMaxSort();
    }

    @Override
    public void save(DoctorType Doctor) {
    	doctorTypeDao.save(Doctor);
    }

    @Override
    public void update(DoctorType Doctor) {
    	doctorTypeDao.update(Doctor);
    }

    @Override
    public DoctorType findById(String parseInt) {
        return doctorTypeDao.findById(parseInt);
    }


    @Override
    public String deletes(String[] _ids) {
        String msg = "";
        for (String id : _ids) {
            msg = doctorTypeDao.deleteById(id);
        }
        return msg;
    }

    @Override
    public void updateStatus(String id) {

    	DoctorType scoreType = doctorTypeDao.findById(id);
        if (scoreType.getStatus() != null && scoreType.getStatus() == 1) {
            scoreType.setStatus(0);
        } else {
            scoreType.setStatus(1);
        }
        doctorTypeDao.update(scoreType);
    }

    @Override
    public void updateSortUp(Integer id) {
        String hqlPre = "from DoctorType where  isDelete=0 and status = 1 and id = ?";
        DoctorType DoctorPre = dao.findByHQLOne(hqlPre, new Object[]{id});
        Integer DoctorPreSort = DoctorPre.getSort();

        String hqlNext = "from DoctorType where sort < (select sort from DoctorType where id= ? )  and isDelete=0 and status = 1 order by sort desc";
        DoctorType DoctorNext = dao.findByHQLOne(hqlNext, new Object[]{id});
        Integer DoctorNextSort = DoctorNext.getSort();

        DoctorPre.setSort(DoctorNextSort);
        DoctorNext.setSort(DoctorPreSort);

        dao.update(DoctorPre);
        dao.update(DoctorNext);
    }

    @Override
    public void updateSortDown(Integer id) {
        String hqlPre = "from DoctorType where  isDelete=0 and status = 1 and id = ?";
        DoctorType DoctorPre = dao.findByHQLOne(hqlPre, new Object[]{id});
        Integer DoctorPreSort = DoctorPre.getSort();
        String hqlNext = "from DoctorType where sort > (select sort from DoctorType where id= ? )  and isDelete=0 and status = 1 order by sort asc";
        DoctorType DoctorNext = dao.findByHQLOne(hqlNext, new Object[]{id});
        Integer DoctorNextSort = DoctorNext.getSort();

        DoctorPre.setSort(DoctorNextSort);
        DoctorNext.setSort(DoctorPreSort);

        dao.update(DoctorPre);
        dao.update(DoctorNext);
    }


	@Override
	public DoctorType finddoctorTypeTypeByName(String title) {
		String hqlPre = "from DoctorType where status = 1 and title = ?";
        DoctorType doctorPre = dao.findByHQLOne(hqlPre, new Object[]{title});
		return doctorPre;
	}

    @Override
    public List<DoctorType> getDoctorTypeList() {
        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "SELECT id,title FROM doctor_type where is_delete = 0  and status=1 order by sort";
        return dao.findEntitiesByJdbc(DoctorType.class, sql, params);
    }


}
