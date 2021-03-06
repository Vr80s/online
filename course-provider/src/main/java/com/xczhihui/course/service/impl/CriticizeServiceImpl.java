package com.xczhihui.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.common.util.IStringUtil;
import com.xczhihui.common.util.StringLegalUtil;
import com.xczhihui.common.util.enums.CriticizeLabel;
import com.xczhihui.course.exception.CriticizeException;
import com.xczhihui.course.mapper.CourseMapper;
import com.xczhihui.course.mapper.CriticizeMapper;
import com.xczhihui.course.model.Criticize;
import com.xczhihui.course.model.OnlineUser;
import com.xczhihui.course.model.Reply;
import com.xczhihui.course.service.ICriticizeService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2018-04-13
 */
@Service
public class CriticizeServiceImpl extends ServiceImpl<CriticizeMapper, Criticize> implements ICriticizeService {


    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CourseMapper iCourseMapper;

    /**
     * Description：求平均值，并且把小数点的都截取到5，或者大于5的
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/18 0018 上午 11:47
     **/
    public static Double getCriticizeStartLevel(Criticize criticize) {
        BigDecimal totalAmount = new BigDecimal(criticize.getOverallLevel());
        totalAmount = totalAmount.add(new BigDecimal(criticize.getContentLevel()));
        totalAmount = totalAmount.add(new BigDecimal(criticize.getDeductiveLevel()));

        // 得到平均数，保留一位小数
        BigDecimal startLevel = totalAmount.divide(new BigDecimal(3), 1, BigDecimal.ROUND_HALF_UP);
        String b = startLevel.toString();
        if (b.length() > 1 && !b.substring(b.length() - 1, b.length()).equals("0")) {
            String[] arr = b.split("\\.");
            Integer tmp = Integer.parseInt(arr[1]);
            if (tmp >= 5) {
                return (double) (Integer.parseInt(arr[0]) + 1);
            } else {
                return Double.valueOf(arr[0] + "." + 5);
            }
        } else {
            return startLevel.doubleValue();
        }
    }

    @Override
    public Map<String, Object> getCourseCriticizes(Page<Criticize> page, Integer courseId, String userId) throws UnsupportedEncodingException {
        return getCriticizes(page, null, courseId, userId);
    }

    @Override
    public Map<String, Object> getAnchorCriticizes(Page<Criticize> page, String anchorUserId, String userId) throws UnsupportedEncodingException {
        return getCriticizes(page, anchorUserId, null, userId);
    }

    @Override
    public void saveCriticize(String userId, String anchorUserId, Integer courseId, String content,
                              Float overallLevel, Float deductiveLevel, Float contentLevel, String criticizeLable) throws UnsupportedEncodingException {
        Criticize criticize = getCriticize(userId, anchorUserId, courseId, content, "", "",
                overallLevel, deductiveLevel, contentLevel, criticizeLable);
        verifyCriticizes(criticize);
        this.baseMapper.insert(criticize);
    }

    @Override
    public void saveReply(String userId, String content, String criticizeId) throws UnsupportedEncodingException {
        Criticize criticize = this.baseMapper.selectById(criticizeId);
        if (criticize == null) {
            throw new CriticizeException("被回复评论找不到了~");
        }
        saveCriticize(userId, criticize.getUserId(), criticize.getCourseId(),
                content, criticizeId, criticize.getCreatePerson());
    }

    @Override
    public Map<String, Object> updatePraise(Boolean isPraise, String criticizeId, String userId) {
        //根据id查出当前评论
        Criticize criticize = this.baseMapper.selectById(criticizeId);
        if (criticize == null) {
            throw new CriticizeException("评论走丢了~");
        }

        Map<String, Object> returnMap = new HashMap<>();
        //点赞排除排除已经点赞的，记录点赞人
        String userIds = criticize.getPraiseLoginNames();
        int sum = criticize.getPraiseSum() == null ? 0 : criticize.getPraiseSum();
        if (isPraise) {
            if (userIds == null || !userIds.contains(userId)) {
                criticize.setPraiseSum(++sum);
                if (userIds == null) {
                    criticize.setPraiseLoginNames(userId);
                } else {
                    criticize.setPraiseLoginNames(userIds + "," + userId);
                }
                this.baseMapper.updateById(criticize);
            }
        } else {
            if (criticize.getPraiseLoginNames().contains(userId)) {
                criticize.setPraiseSum(--sum);
                userIds = userIds.replace("," + userIds, "").replace(userIds, "");
                criticize.setPraiseLoginNames(userIds);
                this.baseMapper.updateById(criticize);
            }
        }
        returnMap.put("praiseSum", sum);
        return returnMap;
    }

    @Override
    public Integer hasCourse(String userId, Integer courseId) {

        return this.baseMapper.hasCourse(courseId, userId);
    }

    public String saveCriticize(String userId, String anchorUserId, Integer courseId, String content, String criticizeId, String createPerson) throws UnsupportedEncodingException {
        Criticize criticize = getCriticize(userId, anchorUserId, courseId, content, criticizeId, createPerson);
        verifyCriticizes(criticize);
        this.baseMapper.insert(criticize);
        return criticize.getId();
    }

    private Criticize getCriticize(String userId, String anchorUserId, Integer courseId, String content, String criticizeId, String createPerson) throws UnsupportedEncodingException {
        return getCriticize(userId, anchorUserId, courseId, content, criticizeId, createPerson, null, null, null, null);
    }

    private Criticize getCriticize(String userId, String anchorUserId, Integer courseId,
                                   String content, String criticizeId, String createPerson, Float overallLevel, Float deductiveLevel, Float contentLevel, String criticizeLable) throws UnsupportedEncodingException {
        Criticize criticize = new Criticize();
        criticize.setId(IStringUtil.getUuid());
        criticize.setCreatePerson(userId);
        criticize.setUserId(anchorUserId);
        criticize.setCourseId(courseId);
        criticize.setContent(content);
        criticize.setReplyUser(createPerson);
        criticize.setReplyCriticizeId(criticizeId);

        if (StringUtils.isNotBlank(criticize.getContent())) {
            content = criticize.getContent();
            criticize.setContent(content);
        }
        if ((overallLevel != null && overallLevel != 0)
                && (deductiveLevel != null && deductiveLevel != 0)
                && (contentLevel != null && contentLevel != 0)
                && (StringUtils.isNotBlank(criticizeLable))) {

            criticize.setOverallLevel(overallLevel);
            criticize.setDeductiveLevel(deductiveLevel);
            criticize.setContentLevel(contentLevel);
            criticize.setCriticizeLable(criticizeLable);
            boolean hasLevel = (overallLevel != null && overallLevel != 0) && (deductiveLevel != null && deductiveLevel != 0) && (contentLevel != null && contentLevel != 0);
            if (hasLevel) {
                Double startLevel = getCriticizeStartLevel(criticize);
                criticize.setStarLevel(startLevel.floatValue());
            }
        }
        Integer userFirstStars = 0;
        if (courseId != null && courseId > 0) {
            userFirstStars = findUserIsBuy(courseId, userId);
        } else {
            userFirstStars = hasUserAllCourseIsBuy(userId, anchorUserId);
        }
        if (userFirstStars != 0) {
            criticize.setBuy(true);
        } else {
            criticize.setBuy(false);
        }
        return criticize;
    }

    private void verifyCriticizes(Criticize criticize) {
        if (StringUtils.isBlank(criticize.getCreatePerson())) {
            throw new CriticizeException("用户id不为空");
        }
        if (StringUtils.isBlank(criticize.getUserId())) {
            throw new CriticizeException("主播用户id不为空");
        }
        if (StringUtils.isBlank(criticize.getContent())) {
            throw new CriticizeException("评论内容不为空");
        }
        if (criticize.getContent().length() > 200) {
            throw new CriticizeException("评论内容不超过200字");
        }
        boolean b = ((cheackLevel(criticize.getContentLevel()) && cheackLevel(criticize.getContentLevel()) && cheackLevel(criticize.getContentLevel()))
                || !(cheackLevel(criticize.getContentLevel()) && cheackLevel(criticize.getContentLevel()) && cheackLevel(criticize.getContentLevel())));
        if (!b) {
            throw new CriticizeException("评价等级有误");
        }
    }

    private boolean cheackLevel(Float f) {
        if (f == null || f == 0) {
            return false;
        } else if (f < 1 || f > 5) {
            throw new CriticizeException("评价等级有误");
        } else {
            return true;
        }
    }

    /**
     * Description：获取课程/主播评价列表
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/17 0017 下午 8:38
     **/
    public Map<String, Object> getCriticizes(Page<Criticize> page, String anchorUserId, Integer courseId, String userId) throws UnsupportedEncodingException {
        List<Criticize> criticizeList = null;
        if (courseId != null) {
            List<Integer> courseIds = iCourseMapper.selectCourseIdByCollectionId(courseId);
            if (courseIds.size() > 0) {
                courseIds.add(courseId);
                criticizeList = this.baseMapper.selectCollectionCriticize(page, courseIds, userId);
            } else {
                criticizeList = this.baseMapper.selectCourseCriticize(page, courseId, userId);
            }
        } else {
            criticizeList = this.baseMapper.selectAnchorCriticize(page, anchorUserId, userId);
        }

        criticizeList.forEach(criticize ->
                {
                    List<Reply> lr = new ArrayList<>();
                    if (criticize.getReplyContent() != null && !criticize.getReplyContent().equals("")) {
                        Reply r = new Reply();
                        OnlineUser ou = new OnlineUser();
                        ou.setLoginName(criticize.getReplyLoginName());
                        ou.setName(criticize.getReplyName());
                        ou.setSmallHeadPhoto(criticize.getReplySmallHeadPhoto());
                        r.setReplyContent(criticize.getReplyContent());
                        r.setCreateTime(criticize.getReplyCreateTime());
                        ou.setName(StringLegalUtil.isPhoneLegal(ou.getName()));
                        r.setOnlineUser(ou);
                        lr.add(r);
                    }
                    criticize.setReply(lr);
                    criticize.getOnlineUser().setName(StringLegalUtil.isPhoneLegal(criticize.getOnlineUser().getName()));
                }

        );
        for (Criticize criticize : criticizeList) {
            /*
             * 评论的内容要转化，回复的内容也需要转化
			 */
            if (userId != null && criticize.getPraiseLoginNames() != null && criticize.getPraiseLoginNames().contains(userId)) {
                criticize.setIsPraise(true);
            }
            if (StringUtils.isNotBlank(criticize.getContent())) {
                criticize.setContent(criticize.getContent());
            }
            if (criticize.getReply() != null && criticize.getReply().size() > 0) {
                List<Reply> replyList = criticize.getReply();
                String replyContent = replyList.get(0).getReplyContent();
                if (StringUtils.isNotBlank(replyContent)) {
                    replyList.get(0).setReplyContent(replyList.get(0).getReplyContent());
                }
                criticize.setReply(replyList);
            }
            criticize.setLabels(CriticizeLabel.getMultiLabelText(criticize.getCriticizeLable()));
        }
        //判断是否有评分权限
        Map<String, Object> map = new HashMap<String, Object>();
        if (userId != null && courseId != null) {
            Integer cv = this.findUserFirstStars(courseId, userId);
            map.put("commentCode", cv);
        } else {
            map.put("commentCode", 0);
        }
        map.put("items", criticizeList);

        log.info("current:" + page.getCurrent() + ",total:" + page.getTotal() + ",pages" + page.getPages());

        map.put("current", page.getCurrent());
        map.put("total", page.getTotal());
        map.put("pages", page.getPages());
        map.put("size", page.getSize());
        return map;
    }

    /**
     * Description：判断是否有评分权限。返回参数： 0 未购买     1 购买了，但是没有星级评论过     2 购买了，也星级评论了
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/18 0018 下午 12:17
     **/
    private Integer findUserFirstStars(Integer courseId, String userId) {
        //如果这个课程是免费的呢。
        if (this.baseMapper.hasCourse(courseId, userId) > 0) {
            if (this.baseMapper.hasCriticizeScore(courseId, userId) > 0) {
                return 2;
            }
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Description：判断是否有评分权限。返回参数： 0 未购买     1 购买了，但是没有星级评论过     2 购买了，也星级评论了
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/4/18 0018 下午 12:17
     **/
    private Integer findUserIsBuy(Integer courseId, String userId) {
        //如果这个课程是免费的呢。
        if (this.baseMapper.hasCourseIsBuy(courseId, userId) > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 判断这个用户有没有购买过这个主播的任意一个付费课程
     *
     * @param userId
     * @param userLecturerId
     * @return
     */
    private Integer hasUserAllCourseIsBuy(String userId, String userLecturerId) {
        if (this.baseMapper.hasUserAllCourseIsBuy(userId, userLecturerId) > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public List<Integer> selectPcCourseCommentMeanCount(Boolean collection, Integer courseId) {

        return this.baseMapper.selectPcCourseCommentMeanCount(collection, courseId);
    }

    @Override
    public List<Double> selectPcUserCommentMeanCount(String userId) {

        return this.baseMapper.selectPcUserCommentMeanCount(userId);
    }

    @Override
    public List<Integer> selectMobileCourseCommentMeanCount(Integer courseId) {
        return this.baseMapper.selectMobileCourseCommentMeanCount(courseId);
    }

    @Override
    public List<Integer> selectMobileUserCommentMeanCount(String userId) {
        return this.baseMapper.selectMobileUserCommentMeanCount(userId);
    }

    }
