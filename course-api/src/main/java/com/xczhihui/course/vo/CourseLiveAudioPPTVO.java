package com.xczhihui.course.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import lombok.Data;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2018-07-31
 */
@Data
public class CourseLiveAudioPPTVO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer courseId;
    private String imgUrl;
    private Integer sort;
    private String userId;


    public static void main(String[] args) {
        List<CourseLiveAudioPPTVO> lists = new ArrayList<>();
        CourseLiveAudioPPTVO courseLiveAudioPPTVO = new CourseLiveAudioPPTVO();
        courseLiveAudioPPTVO.courseId=11;
        courseLiveAudioPPTVO.imgUrl="11111111111";
        lists.add(courseLiveAudioPPTVO);
        String s = JSON.toJSONString(lists, SerializerFeature.WriteMapNullValue);
        System.out.println(s);
    }

}
