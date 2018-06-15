package com.xczhihui.course.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class FocusVo implements Serializable {

    private String id;                    //主键id
    private String userId;            //用户id
    private String name;                //名字
    private String smallHeadPhoto;        //头像
    private String detail;        //详情

}
