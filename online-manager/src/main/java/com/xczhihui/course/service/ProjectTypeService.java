package com.xczhihui.course.service;

import com.xczhihui.bxg.online.common.domain.Project;
import com.xczhihui.common.util.bean.Page;

public interface ProjectTypeService {

    public Page<Project> findProjectPage(Project project, int currentPage,
                                         int pageSize);

    public Project findProjectTypeByNameAndByType(String name, Integer type);

    public int getMaxSort();

    public void save(Project project);

    Project findById(String parseInt);

    void update(Project project);

    public boolean exists(Project existsEntity);

    public String deletes(String[] _ids);

    public void updateStatus(String id);

    public void updateSortUp(Integer id);

    public void updateSortDown(Integer id);

}
