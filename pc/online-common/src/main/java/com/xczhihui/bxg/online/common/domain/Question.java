package com.xczhihui.bxg.online.common.domain;/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.common.support.domain.BasicEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 常见问题实体类信息
 *
 * @author 康荣彩
 * @create 2016-08-29 15:32
 */
@Entity
@Table(name = "question")
public class Question  extends BasicEntity {

    /**
     * 课程id号
     */
    @Column(name = "course_id")
    private Integer courseId;

    /**
     * 问/答信息
     */
    @Column(name = "question_name")
    private String questionName;

    /**
     * 章节对应的pid值
     */
    @Column(name = "pid")
    private String pid;


    /**
     * 排序字段
     */
    @Column(name = "sort")
    private Integer sort;


    /**
     * 是否禁用状态:true:启用 false:禁用
     */
    @Column(name = "status")
    private boolean status;

}
