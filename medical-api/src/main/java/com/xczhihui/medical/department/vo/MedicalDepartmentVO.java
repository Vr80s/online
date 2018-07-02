package com.xczhihui.medical.department.vo;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public class MedicalDepartmentVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 科室表
     */
    private String id;
    /**
     * 科室名称
     */
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "MedicalDepartmentVO{" +
                ", id=" + id +
                ", name=" + name +
                "}";
    }
}
