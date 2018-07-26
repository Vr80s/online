package com.xczhihui.medical.hospital.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

import lombok.Data;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Data
public class MedicalHospitalSolrVO implements Serializable {

    @Field
    private String id;
    @Field
    private String name;
    @Field
    private String description;
    @Field
    private String province;
    @Field
    private String city;
    @Field
    private String detailedAddress;
    @Field
    private Integer authentication;
    @Field
    private Integer recommendSort;
    @Field
    private Double mhscore;
    @Field
    private List<String> medicalHospitalPictures;
    @Field
    private List<String> fieldIdList;
    @Field
    private List<String> fieldTextList;
    @Field
    private Long createTime;

}
