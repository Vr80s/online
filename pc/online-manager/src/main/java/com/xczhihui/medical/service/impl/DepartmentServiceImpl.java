package com.xczhihui.medical.service.impl;

import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.MedicalDepartment;
import com.xczhihui.bxg.online.common.domain.MedicalDoctor;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorDepartment;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.medical.dao.DepartmentDao;
import com.xczhihui.medical.enums.MedicalExceptionEnum;
import com.xczhihui.medical.exception.MedicalException;
import com.xczhihui.medical.service.DepartmentService;

/**
 * MenuServiceImpl:菜单业务层接口实现类
 *
 * @author Rongcai Kang
 */
@Service
public class DepartmentServiceImpl extends OnlineBaseServiceImpl implements
        DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 查询列表
     */
    @Override
    public Page<MedicalDepartment> findMenuPage(MedicalDepartment menuVo,
                                                Integer pageNumber, Integer pageSize) {
        Page<MedicalDepartment> page = departmentDao.findMedicalDepartmentPage(
                menuVo, pageNumber, pageSize);
        return page;
    }

    /**
     * 查找输入的课程类别是否存在
     */
    @Override
    public MedicalDepartment findMedicalDepartmentByName(String name) {
        return departmentDao.findOneEntitiyByProperty(MedicalDepartment.class,
                "name", name);
    }

    @Override
    public List<MedicalDepartment> list() {
        String sql = "select * from medical_department where deleted=0 and status=1 order by  convert(name using gbk) ASC";
        // Map<String,Object> params=new HashMap<String,Object>();
        List<MedicalDepartment> voList = departmentDao.findEntitiesByJdbc(
                MedicalDepartment.class, sql, null);
        return voList;
    }

    /**
     * 保存实体
     */
    @Override
    public void save(MedicalDepartment entity) {
        String id = UUID.randomUUID().toString().replace("-", "");
        entity.setId(id);

        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "SELECT IFNULL(MAX(sort),0) as sort FROM medical_department ";
        List<MedicalDepartment> temp = dao.findEntitiesByJdbc(
                MedicalDepartment.class, sql, params);
        int sort;
        if (temp.size() > 0) {
            sort = temp.get(0).getSort().intValue() + 1;
        } else {
            sort = 1;
        }
        entity.setSort(sort);
        departmentDao.save(entity);
    }

    @Override
    public boolean exists(MedicalDepartment searchEntity) {
        // 输入了一个名称 这个名称数据库已经存在了
        MedicalDepartment she = departmentDao.findByNotEqId(searchEntity);
        return she != null;
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
        for (String id : ids) {
            msg = departmentDao.deleteById(id);
        }
        return msg;

    }

    @Override
    public void updateStatus(String id) {
        MedicalDepartment medicalDepartment = departmentDao.findById(id);
        if (medicalDepartment.getStatus()) {
            medicalDepartment.setStatus(false);
        } else {
            medicalDepartment.setStatus(true);
        }
        departmentDao.update(medicalDepartment);

    }

    @Override
    public List<MedicalDepartment> findAllDepartment(String id) {
        List<MedicalDepartment> list = list();

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("doctorId", id);
        List<String> mdfs = dao.getNamedParameterJdbcTemplate()
                .queryForList("select department_id from  medical_doctor_department where doctor_id = :doctorId and (deleted =false OR deleted is null)", mapSqlParameterSource, String.class);
        for (String mdf : mdfs) {
            for (MedicalDepartment aList : list) {
                if (mdf.equals(aList.getId())) {
                    aList.setHas(true);
                }
            }
        }
        return list;
    }

    /**
     * 修改医师的科室信息
     *
     * @param id 医师id
     */
    @Override
    public void addDoctorDepartment(String id, String[] departmentId) {

        // 根据医师id获取医师详情
        List<MedicalDoctor> doctors = dao.findEntitiesByProperty(
                MedicalDoctor.class, "id", id);
        if (CollectionUtils.isEmpty(doctors)) {
            throw new MedicalException(MedicalExceptionEnum.DOCTOR_NOT_EXIT);
        } else {
            if (StringUtils.isNotBlank(doctors.get(0).getSourceId())) {
                throw new MedicalException(MedicalExceptionEnum.MUST_NOT_HANDLE);
            }
        }

        List<MedicalDoctorDepartment> mhfs = dao.findEntitiesByProperty(
                MedicalDoctorDepartment.class, "doctorId", id);
        dao.getHibernateTemplate().deleteAll(mhfs);
        if (departmentId != null) {
            for (int i = 0; i < departmentId.length; i++) {
                MedicalDoctorDepartment medicalDoctorDepartment = new MedicalDoctorDepartment();
                String mid = UUID.randomUUID().toString().replace("-", "");
                medicalDoctorDepartment.setId(mid);
                medicalDoctorDepartment.setDepartmentId(departmentId[i]);
                medicalDoctorDepartment.setDoctorId(id);
                medicalDoctorDepartment.setCreateTime(new Date());
                dao.save(medicalDoctorDepartment);
            }
        }
    }

    @Override
    public void updateSortUpRec(String id) {

        String hqlPre = "from MedicalDepartment where  deleted=0 and id = ?";
        MedicalDepartment medicalDepartmentPre = dao.findByHQLOne(hqlPre,
                new Object[]{id});
        Integer medicalDepartmentPreSort = medicalDepartmentPre.getSort();

        String hqlNext = "from MedicalDepartment where sort > (select sort from MedicalDepartment where id= ? ) and deleted=0 and status = 1 order by sort asc";
        MedicalDepartment medicalDepartmentNext = dao.findByHQLOne(hqlNext,
                new Object[]{id});
        Integer medicalDepartmentNextSort = medicalDepartmentNext.getSort();

        medicalDepartmentPre.setSort(medicalDepartmentNextSort);
        medicalDepartmentNext.setSort(medicalDepartmentPreSort);

        dao.update(medicalDepartmentPre);
        dao.update(medicalDepartmentNext);

    }

    @Override
    public void updateSortDownRec(String id) {

        String hqlPre = "from MedicalDepartment where  deleted=0 and id = ?";
        MedicalDepartment medicalDepartmentPre = dao.findByHQLOne(hqlPre,
                new Object[]{id});
        Integer medicalDepartmentPreSort = medicalDepartmentPre.getSort();
        String hqlNext = "from MedicalDepartment where sort < (select sort from MedicalDepartment where id= ? )  and deleted=0 and status = 1 order by sort desc";
        MedicalDepartment medicalDepartmentNext = dao.findByHQLOne(hqlNext,
                new Object[]{id});
        Integer medicalDepartmentNextSort = medicalDepartmentNext.getSort();

        medicalDepartmentPre.setSort(medicalDepartmentNextSort);
        medicalDepartmentNext.setSort(medicalDepartmentPreSort);

        dao.update(medicalDepartmentPre);
        dao.update(medicalDepartmentNext);
    }

    @Override
    public void updateSort(String id, Integer sort) {
        String hqlPre = "from MedicalDepartment where  deleted=0 and id = ?";
        MedicalDepartment md = dao.findByHQLOne(hqlPre, new Object[]{id});
        if (md != null) {
            md.setSort(sort);
            dao.update(md);
        }
    }

}
