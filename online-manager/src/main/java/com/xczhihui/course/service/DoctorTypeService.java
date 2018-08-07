package com.xczhihui.course.service;

import com.xczhihui.bxg.online.common.domain.DoctorType;
import com.xczhihui.common.util.bean.Page;

public interface DoctorTypeService {

    public Page<DoctorType> findDoctorTypePage(DoctorType DoctorType, int currentPage,
                                         int pageSize);


    public int getMaxSort();

    public void save(DoctorType DoctorType);

    DoctorType findById(String parseInt);

    void update(DoctorType DoctorType);


    public String deletes(String[] _ids);

    public void updateStatus(String id);

    public void updateSortUp(Integer id);

    public void updateSortDown(Integer id);

	public DoctorType finddoctorTypeTypeByName(String title);

}
