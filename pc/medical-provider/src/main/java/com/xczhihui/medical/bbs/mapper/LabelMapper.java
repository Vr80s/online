package com.xczhihui.medical.bbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.bbs.model.Label;

/**
 * @author hejiwei
 */
public interface LabelMapper extends BaseMapper<Label> {

    /**
     * 所有的标签
     *
     * @return 标签列表
     */
    @Select("select id, name, label_img_url as labelImgUrl from quark_label where is_disable = false order by sort desc")
    List<Label> list();

    /**
     * 更新帖子回复数
     *
     * @param id id
     * @return 更新行数
     */
    @Update("update quark_label set posts_count = posts_count + 1 where id = #{id}")
    void updatePostCount(@Param("id") Integer id);
}
