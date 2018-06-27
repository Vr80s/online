package com.xczhihui.course.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

import lombok.Data;

@Data
public class CourseSolrVO implements Serializable {

    @Field
    private String id;
    @Field
    private String gradeName;
    @Field
    private String subtitle;
    @Field
    private Double currentPrice;
    @Field
    private String name;
    @Field
    private Boolean collection;
    @Field
    private Date startTime;
    @Field
    private String courseDetail;
    @Field
    private String anchorDetail;
    @Field
    private String menuName;
    @Field
    private Integer menuType;
    @Field
    private Integer learndCount;
    @Field
    private Integer watchState;
    @Field
    private String city;
    @Field
    private Date releaseTime;
    @Field
    private Integer recommendSort;
    @Field
    private Integer type;
    @Field
    private Integer courseForm;
    @Field
    private Integer multimediaType;
    @Field
    private Integer lineState;
    @Field
    private String smallImgPath;

    private String startDateStr;
}
