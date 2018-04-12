package com.xczhihui.bxg.online.web.body;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.xczhihui.bxg.online.web.support.sensitive.SensitivewordFilter;
import com.xczhihui.medical.bbs.model.Post;

/**
 * 帖子参数实体封装
 *
 * @author hejiwei
 */
public class PostBody {

    @NotBlank(message = "标题不能为空")
    @Length(min = 5, max = 50)
    private String title;

    private int labelId;

    @NotBlank(message = "内容不能为空")
    private String content;

    public Post build(String userId) {
        Post post = new Post();
        post.setContent(this.content);
        post.setInitTime(new Date());
        post.setLabelId(labelId);
        post.setUserId(userId);
        post.setTitle(this.title);
        return post;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
