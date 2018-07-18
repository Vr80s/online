package com.xczhihui.bxg.online.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2018-01-19
 */
@Entity
@Table(name = "collection_course_apply")
public class CollectionCourseApply  implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;
    
    
    /**
     * 申请合辑-课程关系表
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    
    /**
     * 合辑id
     */
    @Column(name = "collection_apply_id")
    private Integer collectionApplyId;
    
    /**
     * 课程id
     */
    @Column(name = "course_apply_id")
    private Integer courseApplyId;
    
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "collection_course_sort")
    private Integer collectionCourseSort;

    public Integer getCollectionCourseSort() {
        return collectionCourseSort;
    }

    public void setCollectionCourseSort(Integer collectionCourseSort) {
        this.collectionCourseSort = collectionCourseSort;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCollectionApplyId() {
        return collectionApplyId;
    }

    public void setCollectionApplyId(Integer collectionApplyId) {
        this.collectionApplyId = collectionApplyId;
    }

    public Integer getCourseApplyId() {
        return courseApplyId;
    }

    public void setCourseApplyId(Integer courseApplyId) {
        this.courseApplyId = courseApplyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CollectionCourseApply [id=" + id + ", collectionApplyId=" + collectionApplyId + ", courseApplyId="
                + courseApplyId + ", createTime=" + createTime + ", collectionCourseSort=" + collectionCourseSort + "]";
    }
 
}
