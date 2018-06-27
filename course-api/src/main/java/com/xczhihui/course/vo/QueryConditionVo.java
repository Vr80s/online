package com.xczhihui.course.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class QueryConditionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String city;

    private Integer isFree;

    private Integer lineState;

    private Integer courseType;

    private String menuType;

    private String queryKey;

    private Integer sortOrder;

    private Integer courseForm;
    private Integer multimediaType;

}
