package com.xczhihui.medical.doctor.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

import lombok.Data;

@Data
public class MedicalDoctorSolrVO implements Serializable {

    @Field
    private String id;
    @Field
    private Integer type;
    @Field
    private Integer recommendSort;
    @Field
    private Integer focusCount;
    @Field
    private String name;
    @Field
    private String namePinyin;
    @Field
    private String title;
    @Field
    private String description;
    @Field
    private String province;
    @Field
    private String city;
    @Field
    private String detailedAddress;
    @Field
    private String headPortrait;
    @Field
    private String hospitalName;
    @Field
    private List<String> departmentName;
    @Field
    private List<String> departmentId;
    @Field
    private String fieldText;
    @Field
    private Long createTime;


}
