package com.xczhihui.wechat.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.bxg.common.util.SLEmojiFilter;
import com.xczhihui.wechat.course.mapper.CourseMapper;
import com.xczhihui.wechat.course.mapper.CriticizeMapper;
import com.xczhihui.wechat.course.model.Criticize;
import com.xczhihui.wechat.course.model.Reply;
import com.xczhihui.wechat.course.service.ICriticizeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
@Service
public class CriticizeServiceImpl extends ServiceImpl<CriticizeMapper, Criticize> implements ICriticizeService {

    @Autowired
    private CourseMapper iCourseMapper;

    @Override
    public Map<String, Object> getCourseCriticizes(Page<Criticize> page, Integer courseId, String userId) throws UnsupportedEncodingException {
        return getCriticizes(page,null,courseId,userId);
    }

    @Override
    public Map<String, Object> getAnchorCriticizes(Page<Criticize> page, String anchorUserId, String userId) throws UnsupportedEncodingException {
        return getCriticizes(page,anchorUserId,null,userId);
    }

    /**
     * Description：获取课程/主播评价列表
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/17 0017 下午 8:38
     **/
    public Map<String,Object> getCriticizes(Page<Criticize> page, String anchorUserId,Integer courseId,String userId) throws UnsupportedEncodingException {
        List<Criticize> criticizeList = null;
        if(courseId!=null){
            List<Integer> courseIds = iCourseMapper.selectCourseIdByCollectionId(courseId);
            if(courseIds.size()>0){
                criticizeList = this.baseMapper.selectCourseCriticize(page,courseId,userId);
            }else{
                criticizeList = this.baseMapper.selectCollectionCriticize(page,courseIds,userId);
            }
        }else{
            criticizeList = this.baseMapper.selectAnchorCriticize(page,anchorUserId,userId);
        }

        criticizeList.forEach(criticize ->
            criticize.setReply(this.baseMapper.selectReplyByCriticizeId(criticize.getId()))
        );
        for(Criticize criticize:criticizeList){
            /*
			 * 评论的内容要转化，回复的内容也需要转化
			 */
            if(StringUtils.isNotBlank(criticize.getContent())){
                criticize.setContent(SLEmojiFilter.emojiRecovery2(criticize.getContent()));
            }
            if(criticize.getReply()!=null && criticize.getReply().size()>0){
                List<Reply> replyList = criticize.getReply();
                String replyContent = replyList.get(0).getReplyContent();
                if(StringUtils.isNotBlank(replyContent)){
                    replyList.get(0).setReplyContent(SLEmojiFilter.emojiRecovery2(replyList.get(0).getReplyContent()));
                }
                criticize.setReply(replyList);
            }
        }

        /**
         * 这里判断用户发表的评论中是否包含发表心心了，什么的如果包含的话就不返回了
         * 		并且判断这个用户有没有购买过这个课程
         */
        Map<String,Object> map = new HashMap<String,Object>();
        if(userId!=null && courseId!=null){
            Integer cv = this.findUserFirstStars(courseId,userId);
            map.put("commentCode", cv);
        }else{
            map.put("commentCode", 0);
        }
        map.put("items", criticizeList);

        return map;
    }

    private Integer findUserFirstStars(Integer courseId, String userId) {
        return this.baseMapper.hasCourse(courseId,userId);
    }

}
