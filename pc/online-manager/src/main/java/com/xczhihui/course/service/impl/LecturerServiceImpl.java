package com.xczhihui.course.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.course.dao.LecturerDao;
import com.xczhihui.course.service.LecturerService;
import com.xczhihui.course.vo.MenuVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.support.service.SystemVariateService;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Lecturer;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.course.vo.LecturerVo;

/**
 * LecturerServiceImpl:讲师业务层接口实现类
 *
 * @author yxd
 */
@Service
public class LecturerServiceImpl extends OnlineBaseServiceImpl implements LecturerService {

    @Autowired
    private LecturerDao lecturerDao;
    @Autowired
    SystemVariateService systemVariateService;

    /**
     * 根据教师编号，查找教师实例
     *
     * @param lecturerId 教师的ID编号
     * @return 教师对象
     */
    @Override
    public Lecturer findLecturerById(Integer lecturerId) {
        Lecturer lecturer = null;
        if (lecturerId != null) {
            String hql = "from Lecturer where 1=1 and isDelete=0 and id = ?";
            lecturer = dao.findByHQLOne(hql, new Object[]{lecturerId});
        }
        return lecturer;
    }

    @Override
    public Page<LecturerVo> findLecturerPage(LecturerVo searchVo,
                                             int pageNumber, int pageSize) {
        // TODO Auto-generated method stub
        Page<LecturerVo> page = lecturerDao.findLecturerPage(searchVo, pageNumber, pageSize);
        for (LecturerVo lecturer : page.getItems()) {
            String str = systemVariateService.getNameByValue("role_type", lecturer.getRoleType());
            lecturer.setRoleTypeName(str);
        }
        return page;
    }

    @Override
    public List<Menu> getfirstMenus() {
        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "SELECT id,name FROM oe_menu where is_delete = 0 and name <> '全部' and status=1 ";
        dao.findEntitiesByJdbc(MenuVo.class, sql, params);
        return dao.findEntitiesByJdbc(Menu.class, sql, params);
    }

    @Override
    public void addLecturer(Lecturer lecturer) {
        lecturer.setCreateTime(new Date());
        lecturer.setCreatePerson(ManagerUserUtil.getUsername());
        dao.save(lecturer);
    }

    @Override
    public void updateLecturer(Lecturer lecturer) {
        Lecturer res = dao.get(lecturer.getId(), Lecturer.class);
        String p = res.getCreatePerson();
        Date cd = res.getCreateTime();

        BeanUtils.copyProperties(lecturer, res);
        res.setCreatePerson(p);
        res.setCreateTime(cd);
        res.setDelete(false);
        dao.update(res);
    }

    @Override
    public List<LecturerVo> findTeachRecordsByLecturerId(String id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        String sql = " SELECT g.name as teachRecords  from grade_r_lecturer lg,oe_grade g where lg.grade_id = g.id and lg.lecturer_id = :id";

        return dao.findEntitiesByJdbc(LecturerVo.class, sql, params);
    }

    @Override
    public String deletes(String[] ids) {
        for (String id : ids) {
            lecturerDao.deleteById(Integer.valueOf(id));
        }
        return "删除成功";
    }

    @Override
    public List<LecturerVo> listByGradeId(String gradeId) {
        String sql = "select ol.id,ol.name from oe_lecturer ol where  is_delete=0 and ol.id in (select lecturer_id from grade_r_lecturer where grade_id=:gradeId and is_delete=0 )";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("gradeId", gradeId);
        return dao.findEntitiesByJdbc(LecturerVo.class, sql, params);
    }
}
