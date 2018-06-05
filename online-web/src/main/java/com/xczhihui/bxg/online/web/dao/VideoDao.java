package com.xczhihui.bxg.online.web.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.xczhihui.common.util.enums.CourseForm;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.online.api.vo.CriticizeVo;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Criticize;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.vo.ChapterLevelVo;
import com.xczhihui.bxg.online.web.vo.CourseApplyVo;
import com.xczhihui.bxg.online.web.vo.UserVideoVo;

/**
 * 视频相关功能数据访问层
 *
 * @Author Fudong.Sun【】
 * @Date 2016/11/2 15:22
 */
@Repository
public class VideoDao extends SimpleHibernateDao {

    /**
     * 根据节Id查询所有知识点
     *
     * @param id
     * @return
     */
    public List<Map<String, Object>> getKnowledgesBySectionId(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("select id,name from oe_chapter where parent_id=:id and status=1 and is_delete=0");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return this.getNamedParameterJdbcTemplate().queryForList(sql.toString(), paramMap);
    }

    /**
     * 没有传入节ID，默认获取第一章第一节知识点
     *
     * @return
     */
    public List<Map<String, Object>> getFirstKnowledges(String courseId) {
        StringBuffer sql = new StringBuffer();
        sql.append("select id,name from oe_chapter where parent_id =");
        sql.append(" (select id from oe_chapter where parent_id=(select id from oe_chapter where status=1 and is_delete=0 and LEVEL=2 and course_id =:courseId order by sort limit 1)");
        sql.append(" and status=1 and is_delete=0 order by sort limit 1) and status=1 and is_delete=0");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("courseId", courseId);
        return this.getNamedParameterJdbcTemplate().queryForList(sql.toString(), paramMap);
    }

    /**
     * 获取用户购买页知识点下视频列表
     *
     * @param id 节ID
     * @return
     */
    public List<Map<String, Object>> getBuyVideos(String id, String userId) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ov.id,ov.chapter_id chapterId,ov.name as videoName,");
        sql.append(" ov.video_id videoId,ov.video_time videoTime,ov.video_size videoSize,");
        sql.append(" ov.course_id courseId,ov.is_try_learn isLearn ");
        sql.append(" from oe_video ov");
        sql.append(" LEFT JOIN user_r_video urv on ov.id=urv.video_id");
        sql.append(" where ov.STATUS=1 and ov.chapter_id =:id and urv.user_id =:userId");
        sql.append(" ORDER BY ov.is_try_learn DESC,ov.sort DESC");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("userId", userId);
        return this.getNamedParameterJdbcTemplate().queryForList(sql.toString(), paramMap);
    }

    /**
     * 获取全部试学的视频
     *
     * @param id 节ID
     * @return
     */
    public List<Map<String, Object>> getTryLearnVideos(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ov.id,ov.chapter_id chapterId,ov.name as videoName,");
        sql.append(" ov.video_id videoId,ov.video_time videoTime,ov.video_size videoSize,");
        sql.append(" ov.course_id courseId,ov.is_try_learn isLearn ");
        sql.append(" from oe_video ov where STATUS=1 and is_delete=0 and ov.chapter_id =:id and is_try_learn=1");
        sql.append(" ORDER BY ov.is_try_learn DESC,ov.sort DESC");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return this.getNamedParameterJdbcTemplate().queryForList(sql.toString(), paramMap);
    }

    /**
     * 点赞、取消点赞
     */
    public void praise(CriticizeVo criticizeVo) {
        String sql = "update oe_criticize set praise_sum =:praiseSum, praise_login_names =:praiseLoginNames where id =:id";
        this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(criticizeVo));
    }

    /**
     * 修改学员视频学习状态,记录最后学习时间
     */
    public void updateStudyStatus(String studyStatus, String videoId, String userId) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> paramMap = new HashMap<>();
        sql.append(" update user_r_video set study_status =:studyStatus,last_learn_time=now() where video_id =:videoId and user_id =:userId");
        if ("2".equalsIgnoreCase(studyStatus)) {
            sql.append(" and study_status=0");
        } else {
            sql.append(" and study_status=2");
        }
        paramMap.put("studyStatus", studyStatus);
        paramMap.put("videoId", videoId);
        paramMap.put("userId", userId);
        this.getNamedParameterJdbcTemplate().update(sql.toString(), paramMap);
    }

    /**
     * 根据视频id获取学习过的学员
     *
     * @param id 节ID
     * @return
     */
    public List<Map<String, Object>> getLearnedUser(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ov.name videoName,ou.name userName,");
        sql.append(" (select small_head_photo from oe_user where id=urv.user_id) as smallPhoto");
        sql.append(" from user_r_video urv LEFT JOIN oe_video ov on urv.video_id=ov.id");
        sql.append(" LEFT JOIN oe_user ou on urv.user_id=ou.id");
        sql.append(" where urv.STATUS=1 and urv.is_delete=0 and urv.study_status=1 and urv.video_id =:id ORDER BY RAND() limit 0,8");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return this.getNamedParameterJdbcTemplate().queryForList(sql.toString(), paramMap);
    }

    /**
     * 根据课程id获取购买过该课程的学员
     *
     * @param id 节ID
     * @return
     */
    public List<Map<String, Object>> getPurchasedUser(String id) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT oo.pay_time time,ou.name userName,");
        sql.append(" ou.small_head_photo as smallPhoto");
        sql.append(" from oe_order_detail od,oe_order oo");
        sql.append(" JOIN oe_user ou on oo.user_id=ou.id");
//        sql.append(" LEFT JOIN oe_user ou on oo.user_id=ou.id");
        sql.append(" where oo.id=od.order_id and  oo.is_delete=0 and oo.order_status=1 and od.course_id =:id ORDER BY RAND() limit 0,8");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        return this.getNamedParameterJdbcTemplate().queryForList(sql.toString(), paramMap);
    }

    /**
     * 获取主播的或者课程的评价数
     *
     * @return
     * @throws IllegalAccessException
     */
    public Page<Criticize> getUserOrCourseCriticize(String teacherId, Integer courseId,
                                                    Integer pageNumber, Integer pageSize, String userId) {

        try {

            Map<String, Object> paramMap = new HashMap<>();
            pageNumber = pageNumber == null ? 1 : pageNumber;
            pageSize = pageSize == null ? 10 : pageSize;
            /**
             * 购买者这里怎么显示了啊。好尴尬了，不能用多个循环吧，不然会卡点呢
             * 	 或者是购买成功	
             *
             * 一个专辑下存在多个课程，然后课程
             */
            if (courseId != null || teacherId != null) {
                StringBuffer sql = new StringBuffer("select c from Criticize c  where c.status = 1  and c.onlineUser is not null  ");

                if (org.apache.commons.lang.StringUtils.isNotBlank(teacherId)) {
                    sql.append("  and c.userId =:userId ");
                    paramMap.put("userId", teacherId);
                } else if (courseId != null && courseId != 0) {
                    //查找这个课程是不是专辑、如果是专辑就 用in来查找啦
                    List<Integer> list = getCoursesIdListByCollectionId(courseId);

                    if (list.size() > 0) {
                        list.add(courseId);
                        String str = "";
                        for (int i = 0; i < list.size(); i++) {
                            Integer array_element = list.get(i);
                            if (i == list.size() - 1) {
                                str += array_element;
                            } else {
                                str += array_element + ",";
                            }
                        }
                        sql.append("  and c.courseId in (" + str + ") ");
                    } else {
                        sql.append("  and c.courseId =:courseId ");
                        paramMap.put("courseId", courseId);
                    }
                }
                sql.append(" order by c.createTime desc ");

                Page<Criticize> criticizes = this.findPageByHQL(sql.toString(), paramMap, pageNumber, pageSize);

                if (criticizes.getTotalCount() > 0) {
                    String loginName = "";
                    if (userId != null) {
                        //这里就查询了一次，所是ok的。这是不是需要查询下。
                        OnlineUser u = this.get(userId, OnlineUser.class);
                        loginName = u.getLoginName();
                    }
                    for (Criticize c : criticizes.getItems()) {
                        /**
                         * 判断会否点过赞
                         */
                        String loginNames = c.getPraiseLoginNames();
                        Boolean isPraise = false;
                        if (org.apache.commons.lang.StringUtils.isNotBlank(loginNames)) {
                            for (String loginName1 : loginNames.split(",")) {
                                if (loginName.equals(loginName1)) {
                                    isPraise = true;
                                }
                            }
                        }
                        c.setIsPraise(isPraise);
                        /**
                         * 星级的平均数
                         */
                        if (c.getOverallLevel() != null && !"".equals(c.getOverallLevel()) && c.getOverallLevel() != 0) {

                            System.out.println("c.getOverallLevel():" + c.getOverallLevel());
                            System.out.println("c.getContentLevel():" + c.getContentLevel());
                            System.out.println("c.getDeductiveLevel():" + c.getDeductiveLevel());

                            BigDecimal totalAmount = new BigDecimal(c.getOverallLevel() != null ? c.getOverallLevel() : 0);
                            totalAmount = totalAmount.add(new BigDecimal(c.getContentLevel() != null ? c.getContentLevel() : 0));
                            totalAmount = totalAmount.add(new BigDecimal(c.getDeductiveLevel() != null ? c.getDeductiveLevel() : 0));

                            String startLevel = "0";
                            try {
                                startLevel = divCount(totalAmount.doubleValue(), 3d, 1);
                                System.out.println("startLevel:" + startLevel);
                                c.setStarLevel(Float.valueOf(startLevel));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                return criticizes;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取评论列表有误!");
        }
        return null;
    }

    /**
     * Description：求平均值，并且把小数点的都截取到5，或者大于5的
     *
     * @param value1
     * @param value2
     * @param scale
     * @return String
     * @throws IllegalAccessException
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public static String divCount(double value1, double value2, int scale) throws IllegalAccessException {
        //如果精确范围小于0，抛出异常信息  
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }

        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        /**
         * 得到平均数，保留以为小数
         */
        BigDecimal b3 = b1.divide(b2, 1, BigDecimal.ROUND_HALF_UP);

        return criticizeStartLevel(b3.doubleValue()).toString();
    }


    public static Double criticizeStartLevel(Double startLevel) {

        if (startLevel != null && startLevel != 0) { // 不等于0
            String b = startLevel.toString();
            if (b.length() > 1
                    && !b.substring(b.length() - 1, b.length()).equals("0")) { // 不等于整数
                String[] arr = b.split("\\.");
                Integer tmp = Integer.parseInt(arr[1]);
                if (tmp >= 5) {
                    return (double) (Integer.parseInt(arr[0]) + 1);
                } else {
                    return Double.valueOf(arr[0] + "." + 5);
                }
            } else {
                return startLevel;
            }
        }
        return startLevel;
    }


    /**
     * Description：通过课程id得到这个专辑的信息
     *
     * @param courseId
     * @return List<Integer>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public List<Integer> getCoursesIdListByCollectionId(Integer courseId) {

        String sql = "SELECT \n" +
                "  oc.`id` \n" +
                "FROM\n" +
                "  `oe_course` oc \n" +
                "  JOIN `collection_course` cc \n" +
                "    ON oc.id = cc.`course_id` \n" +
                "WHERE cc.`collection_id` = " + courseId + " \n";
        List<Integer> list = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql, Integer.class);
        return list;

    }

}
