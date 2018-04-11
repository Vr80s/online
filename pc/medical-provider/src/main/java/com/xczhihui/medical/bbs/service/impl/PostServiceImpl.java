package com.xczhihui.medical.bbs.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.bbs.enums.PostFilterType;
import com.xczhihui.medical.bbs.mapper.*;
import com.xczhihui.medical.bbs.model.*;
import com.xczhihui.medical.bbs.service.IPostService;
import com.xczhihui.medical.bbs.vo.PostVO;
import com.xczhihui.medical.bbs.vo.ReplyVO;

/**
 * @author hejiwei
 */
@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private LabelMapper labelMapper;
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private BrowseRecordMapper browseRecordMapper;
    @Autowired
    private BBSUserStatusMapper bbsUserStatusMapper;

    @Override
    public Page<PostVO> list(int page, Integer labelId, String type) {
        Page<PostVO> postVOPage = new Page<>(page, 10);
        List<PostVO> postVOList;
        if (StringUtils.isBlank(type)) {
            postVOList = postMapper.listByLabelId(postVOPage, null);
        } else if (PostFilterType.LABEL.getValue().equals(type)) {
            postVOList = postMapper.listByLabelId(postVOPage, labelId);
        } else if (PostFilterType.GOOD.getValue().equals(type)) {
            postVOList = postMapper.listGood(postVOPage);
        } else if (PostFilterType.HOT.getValue().equals(type)) {
            postVOList = postMapper.listHot(postVOPage);
        } else {
            throw new IllegalArgumentException("非法的类型参数");
        }
        return postVOPage.setRecords(postVOList);
    }

    @Override
    public List<Label> getLabels() {
        return labelMapper.list();
    }

    @Override
    public List<PostVO> listHot() {
        return postMapper.listHot(new Page<>(1, 10));
    }

    @Override
    public boolean savePost(Post post) {
        Integer labelId = post.getLabelId();
        Label label = labelMapper.selectById(labelId);
        if (label == null || label.isDisabled()) {
            throw new IllegalArgumentException("标签不存在");
        }
        postMapper.insert(post);
        labelMapper.updatePostCount(labelId);
        return true;
    }

    @Override
    public boolean addReply(Reply reply) {
        Integer postId = reply.getPostId();
        Post post = postMapper.selectById(postId);
        if (post == null || post.isDeleted()) {
            throw new IllegalArgumentException("帖子已被删除");
        }
        Integer toReplyId = reply.getToReplyId();
        if (toReplyId != null && toReplyId != 0) {
            Reply toReply = replyMapper.selectById(toReplyId);
            if (toReply == null || toReply.isDeleted()) {
                throw new IllegalArgumentException("回复的回复已被删除");
            }
        }
        postMapper.updateReplyCount(postId);
        replyMapper.insert(reply);
        return true;
    }

    @Override
    public PostVO get(Integer id) {
        PostVO post = postMapper.get(id);
        if (post == null) {
            throw new IllegalArgumentException("帖子不存在");
        }
        return post;
    }

    @Override
    public Page<ReplyVO> listByPostId(int postId, int page) {
        Page<ReplyVO> replyVOPage = new Page<>(page, 3);
        List<ReplyVO> replyVOS = replyMapper.listByPostId(replyVOPage, postId);
        //查询回复的回复数据
        replyVOS.forEach(replyVO -> {
            Integer toReplyId = replyVO.getToReplyId();
            if (toReplyId != null && toReplyId != 0) {
                replyVO.setToReply(replyMapper.get(toReplyId));
            }
        });
        return replyVOPage.setRecords(replyVOS);
    }

    @Override
    public void addBrowseRecord(int postId, String userId) {
        if (userId != null) {
            Map<String, Object> params = new HashMap<>(2);
            EntityWrapper<BrowseRecord> ew = new EntityWrapper<BrowseRecord>();
            Integer count = browseRecordMapper.selectCount(ew.where("user_id = {0}", userId).and("post_id = {0}", postId));
            if (count == 0) {
                BrowseRecord br = new BrowseRecord(postId, userId);
                br.setInsertTime(new Date());
                browseRecordMapper.insert(br);
                postMapper.updateBrowseCount(postId);
            }
        } else {
            postMapper.updateBrowseCount(postId);
        }
    }

    @Override
    public boolean isBlacklist(String userId) {
        BBSUserStatus bbsUserStatus = new BBSUserStatus();
        bbsUserStatus.setUserId(userId);
        bbsUserStatus = bbsUserStatusMapper.selectOne(bbsUserStatus);
        return bbsUserStatus != null && "1".equals(bbsUserStatus.getBlacklist());
    }

    @Override
    public boolean isGags(String userId) {
        BBSUserStatus bbsUserStatus = new BBSUserStatus();
        bbsUserStatus.setUserId(userId);
        bbsUserStatus = bbsUserStatusMapper.selectOne(bbsUserStatus);
        return bbsUserStatus != null && "1".equals(bbsUserStatus.getGag());
    }

    @Override
    public Page<PostVO> listMyPosts(String userId, int page) {
        Page<PostVO> postVOPage = new Page<>(page, 10);
        return postVOPage.setRecords(postMapper.listMyPosts(postVOPage, userId));
    }

    @Override
    public Page<ReplyVO> listMyReplies(String userId, int page) {
        Page<ReplyVO> replyVOPage = new Page<>(page, 10);
        return replyVOPage.setRecords(replyMapper.listMyReplies(replyVOPage, userId));
    }
}
