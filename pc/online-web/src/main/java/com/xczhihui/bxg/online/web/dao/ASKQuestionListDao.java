package com.xczhihui.bxg.online.web.dao;/**
 * Created by admin on 2016/9/19.
 */

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.web.base.utils.MysqlUtils;
import com.xczhihui.bxg.online.web.vo.AskAccuseVo;
import com.xczhihui.bxg.online.web.vo.AskAnswerVo;
import com.xczhihui.bxg.online.web.vo.AskQuestionVo;
import com.xczhihui.bxg.online.web.vo.CourseVo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 提问列表页底层类
 *
 * @author 康荣彩
 * @create 2016-09-19 18:34
 */
@Repository
public class ASKQuestionListDao extends SimpleHibernateDao {


    /**
     * 获取问题列表信息
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页显示条数
     * @param status     状态，0待回答，1回答中，2已解决
     * @param tag        标签信息
     * @param title      标题
     * @param text       纯文本内容
     * @param content    内容
     * @param menuId     学科ID号
     * @return
     */
/*    public Page<AskQuestionVo> findListQuestion(Integer pageNumber, Integer pageSize, Integer menuId, String status, String tag, String title, String text, String content) {
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String sql = "";
        title = MysqlUtils.replaceESC(title);
        tag = MysqlUtils.replaceESC(tag);

        String titleSql ="";
        String tagSql = "";
        //判断学员是否根据问题标题和标签进行搜索
        if (!"".equals(title) && title != null){
            titleSql =" and  q.title like :title ";
        }
        if (!"".equals(tag) && tag != null){
            tagSql=" and q.tags  like :tag ";
        }

        sql= " select m.ask_limit, q.create_nick_name,q.create_head_img,q.title,q.content,q.accused,q.text,q.tags,q.create_time,q.create_person,q.answer_sum,q.browse_sum, m.`name`,m.id as ment_id ,q.id,NOW() as systemTime" +
             " from oe_ask_question q  join oe_menu m " +
             " where  q.ment_id = m.id  and q.is_delete = 0 " +title+tag+ "  order by q.create_time  desc ";
        //查询全部学科下的问题信息
        if (menuId == -1) {
            //查询某个学科下全部问题
            if (status.equals("-1")) {
                sql = "select m.ask_limit, q.create_nick_name,q.create_head_img,q.title,q.content,q.accused,q.text,q.tags,q.create_time,q.create_person,q.answer_sum,q.browse_sum, m.`name`,m.id as ment_id ,q.id,NOW() as systemTime" +
                        " from oe_ask_question q  join oe_menu m " +
                        " where  q.ment_id = m.id  and q.is_delete = 0   and q.tags  like :tag  and  q.title like :title    order by q.create_time  desc    ";
            } else {
                //状态:status=2  已解决   否则未解决
                if (status.equals("2")) {
                    sql = "select  m.ask_limit, q.create_nick_name,q.create_head_img,q.title,q.content,q.accused,q.text,q.tags,q.create_time,q.create_person,q.answer_sum,q.browse_sum, m.`name`,m.id as ment_id ,q.id,NOW() as systemTime" +
                            " from oe_ask_question q  join oe_menu m " +
                            " where  q.ment_id = m.id  and q.is_delete = 0  and  q.status = :status    and q.tags  like :tag  and  q.title like :title   order by q.create_time  desc    ";
                    paramMap.put("status", status);
                } else {
                    sql = "select  m.ask_limit, q.create_nick_name,q.create_head_img,q.title,q.content,q.accused,q.text,q.tags,q.create_time,q.create_person,q.answer_sum,q.browse_sum, m.`name`,m.id as ment_id ,q.id,NOW() as systemTime" +
                            " from oe_ask_question q  join oe_menu m " +
                            " where  q.ment_id = m.id  and q.is_delete = 0  and  q.status != 2   and q.tags  like :tag  and  q.title like :title    order by q.create_time  desc    ";
                }
            }
        } else {
            //查询某个学科下全部问题
            if (status.equals("-1")) {
                sql = "select m.ask_limit,  q.create_nick_name,q.create_head_img,q.title,q.content,q.accused,q.text,q.tags,q.create_time,q.create_person,q.answer_sum,q.browse_sum, m.`name`,m.id as ment_id,q.id,NOW() as systemTime" +
                        " from oe_ask_question q  join oe_menu m " +
                        " where  q.ment_id = m.id  and q.is_delete = 0   and    q.ment_id = :menuId   and q.tags  like :tag  and  q.title like :title  order by q.create_time  desc    ";
            } else {
                //状态:status=2  已解决   否则未解决
                if (status.equals("2")) {
                    sql = "select  m.ask_limit,  q.create_nick_name,q.create_head_img,q.title,q.content,q.accused,q.text,q.tags,q.create_time,q.create_person,q.answer_sum,q.browse_sum, m.`name`,m.id as ment_id ,q.id,NOW() as systemTime" +
                            " from oe_ask_question q  join oe_menu m " +
                            " where  q.ment_id = m.id  and q.is_delete = 0   and    q.ment_id = :menuId  and  q.status = :status    and q.tags  like :tag  and  q.title like :title   order by q.create_time  desc    ";
                    paramMap.put("status", status);
                } else {
                    sql = "select  m.ask_limit,  q.create_nick_name,q.create_head_img,q.title,q.content,q.accused,q.text,q.tags,q.create_time,q.create_person,q.answer_sum,q.browse_sum, m.`name`,m.id as ment_id,q.id,NOW() as systemTime" +
                            " from oe_ask_question q  join oe_menu m " +
                            " where  q.ment_id = m.id  and q.is_delete = 0   and    q.ment_id = :menuId  and  q.status != 2  and q.tags  like :tag  and  q.title like :title    order by q.create_time  desc   ";
                }

            }
            paramMap.put("menuId", menuId);
        }
        paramMap.put("tag", "%" + tag + "%");
        paramMap.put("title", "%" + title + "%");

        Page<AskQuestionVo> page = this.findPageBySQL(sql, paramMap, AskQuestionVo.class, pageNumber, pageSize);
        return page;
    }*/


    public Page<AskQuestionVo> findListQuestion(Integer pageNumber, Integer pageSize, Integer menuId, String status, String tag, String title, String text, String content) {
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String sql = "";
        title = MysqlUtils.replaceESC(title);
        tag = MysqlUtils.replaceESC(tag);

        String titleSql ="";
        String tagSql = "";
        String menuSql="";
        String statuSql="";
        //判断学员是否根据问题标题和标签进行搜索
        //根据问题标题搜索，拼接的sql
        if (!"".equals(title) && title != null){
            titleSql =" and  q.title like :title ";
            paramMap.put("title", "%" + title + "%");
        }
        //根据标签搜索，拼接的sql
        if (!"".equals(tag) && tag != null){
            tagSql=" and q.tags  like :tag ";
            paramMap.put("tag", "%" + tag + "%");
        }
        //根据学科搜索，拼接的sql
        if(menuId != null && menuId > 0){
            menuSql=" and  q.ment_id = :menuId  ";
            paramMap.put("menuId", menuId);
        }
        //根据问题状态搜索，拼接的sql
        if(!"".equals(status) && status!=null && !"-1".equals(status)){
              if("2".equals(status)){
                  statuSql= " and  q.status = :status " ;
                  paramMap.put("status", status);
              }else{
                  statuSql= " and  q.status != 2 ";
              }


        }
        sql= " select m.ask_limit, q.create_nick_name,q.create_head_img,q.title,q.content,q.accused,q.text,q.tags,q.create_time,q.create_person,q.answer_sum,q.browse_sum, m.`name`,m.id as ment_id ,q.id,NOW() as systemTime" +
                " from oe_ask_question q  join oe_menu m " +
                " where  q.ment_id = m.id  and q.is_delete = 0 "+titleSql+tagSql+menuSql+statuSql + " order by q.create_time  desc ";

        Page<AskQuestionVo> page = this.findPageBySQL(sql, paramMap, AskQuestionVo.class, pageNumber, pageSize);
        return page;
    }


    /**
     * 获取问题数据，根据问题ID号
     *
     * @param questionId 问题ID号
     */
    public AskQuestionVo findQuestionById(String questionId) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        String sql = " select q.user_id as userId, a.`status` as handleStatus,m.* ,q.create_nick_name,q.create_head_img,q.title,q.content,q.accused,q.status,q.text,q.tags," +
                " q.create_time,q.answer_sum,q.browse_sum, q.create_person,q.id,NOW() as systemTime from oe_ask_question q left join oe_ask_accuse a  on q.id = a.target_id" +
                " join ( select m.ask_limit,m.`name`,m.id as ment_id  from oe_ask_question q join oe_menu m on q.ment_id = m.id  and q.id=? ) as m" +
                " where   q.id=? and  q.is_delete = 0   order by a.`status` limit 1";
        List<AskQuestionVo> questionVos = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, BeanPropertyRowMapper.newInstance(AskQuestionVo.class), questionId, questionId);
        return questionVos.size() > 0 ? questionVos.get(0) : null;
    }


    /**
     * 查找当前用户是否有收藏过此问题
     *
     * @param questionId 问题ID号
     * @param userId     用户ID号
     */
    public Boolean findCollectionByQidAndUserId(String questionId, String userId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String sql = "select  count(*) from oe_ask_collection where question_id = :questionId  and user_id = :userId  and is_delete=0";
        paramMap.put("questionId", questionId);
        paramMap.put("userId", userId);
        int count = ((Integer) this.getNamedParameterJdbcTemplate().queryForObject(sql, (Map) paramMap, Integer.class)).intValue();
        return count > 0 ? true : false;
    }


    /**
     * 修改问题信息的浏览数
     *
     * @param qu 问题的id号
     */
    public void updateBrowseSum(AskQuestionVo qu) {
        String sql = " update oe_ask_question  set browse_sum = (browse_sum + 1)  where id = ? and is_delete = 0 ";
        this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, qu.getId());
    }


    /**
     * 添加提问信息
     *
     * @param qu 参数封装对象
     */
    public void saveQuestion(AskQuestionVo qu) {
        //获取学科名
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id",qu.getMent_id());
        String sqlquery="select name from oe_menu where is_delete=0 and status=1 and  id=:id  limit 1";
        String name =this.getNamedParameterJdbcTemplate().queryForObject(sqlquery,paramMap,String.class);
        qu.setName(name);
        qu.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        String sql = "insert into oe_ask_question (id,title,content,text,ment_id,tags,create_nick_name,create_head_img,create_person,name,user_id,video_id)   "
                + " values (:id,:title,:content,:text,:ment_id,:tags,:create_nick_name,:create_head_img,:create_person,:name,:userId,:videoId) ";
        this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(qu));
    }


    /**
     * 查找类似问题,根据提问标题
     *
     * @param title 提问标题信息
     * @return 类似问题集合
     */
    public List<AskQuestionVo> findSimilarProblemByTitle(String title) {
        String sql = " select q.id, q.title,q.answer_sum from oe_ask_question q  where q.title   like  ?  and q.is_delete = 0 order by q.answer_sum desc  limit 5 ";
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, new Object[]{"%" + title + "%"}, BeanPropertyRowMapper.newInstance(AskQuestionVo.class));
    }


    /**
     * 查找与当前问题相关的课程信息（筛选条件是：课程所属学科与问题所属学科一样）
     *
     * @param menuId 学科id号
     * @return
     */
    public List<CourseVo> getCourseByMenuId(Integer menuId) {
        List<CourseVo> courseVoList = null;
        if (menuId != null) {
            String sql = " select c.id,c.grade_name as courseName,c.smallimg_path as smallimgPath ,c.original_cost as originalCost ,c.current_price as currentPrice ,c.is_free as isFree  ,tm.`name` as scoreName,c.description_show,c.multimedia_type multimediaType," +
						 "IFNULL((SELECT  COUNT(*) FROM apply_r_grade_course WHERE course_id = c.id),0) + IFNULL(default_student_count, 0) learnd_count"+
                         " from  oe_course c left join  teach_method tm on c.courseType = tm.id   where  c.is_delete=0  and c.`status`=1 AND ISNULL(c.type) and c.menu_id =?  order by c.sort desc limit 5";
            courseVoList = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, new Object[]{menuId}, BeanPropertyRowMapper.newInstance(CourseVo.class));
        }
        return courseVoList;
    }

    /**
     * 根据问题id号，查找对应点赞数最多的回答信息，只获取1条,
     *
     * @param questionId 问题id号
     * @return
     */
    public AskAnswerVo findAskAnswerByQuestionId(String questionId, OnlineUser u) {
        String praise = u != null ? "find_in_set('" + u.getLoginName() + "',praise_login_names) > 0 and praise_login_names is not null" : "false";
        String sql = "select *," + praise + " as praise from oe_ask_answer   where   question_id=? and is_delete = 0    order by praise_sum desc,create_time desc  limit 1 ";
        List<AskAnswerVo> askAnswerVos = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, new Object[]{questionId}, BeanPropertyRowMapper.newInstance(AskAnswerVo.class));
        return askAnswerVos.size() > 0 ? askAnswerVos.get(0) : null;
    }


    /**
     * 根据投诉目标id查找投诉问题,
     *
     * @param targetId 投诉目标id号
     * @return
     */
    public Boolean findAccuseByTargetId(String targetId) {
        String sql = "select *   from oe_ask_accuse   where   target_id=?  and status =0 ";
        List<AskAccuseVo> askAccuseVos = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, new Object[]{targetId}, BeanPropertyRowMapper.newInstance(AskAccuseVo.class));
        return askAccuseVos.size() > 0 ? true : false;
    }


    /**
     * 热门回答(按照回答数量高排序)
     *
     * @param
     * @return
     */
    public List<AskQuestionVo> getHotAnswer() {
        String sql = "select id, title,answer_sum  from oe_ask_question  where  answer_sum >0  and is_delete = 0 order by answer_sum desc   limit 7";
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, BeanPropertyRowMapper.newInstance(AskQuestionVo.class));
    }

    /**
     * 相似问题 1、按问题标签搜索(7条)
     * 2、如果按标签搜索没有相似问题，再按照学科搜索(7条)
     * 3、如果标签搜索，相似问题不足7条，接着按学科搜索 总共7条
     *
     * @param tags   问题标签,格式[java,c++]
     * @param menuId 学科id号
     * @return
     */
    public List<AskQuestionVo> getSameProblem(String[] tags, Integer menuId, String questionId) {
        int length = tags.length; //参数长度
        Object[] obj = new Object[length + 1];  //第一次按标签查询参数数组
        Object[] array = new Object[length + 3];  //第二次查询参数数组
        List<AskQuestionVo> questionVos = new ArrayList<AskQuestionVo>();
        StringBuffer sqlbf = null;
        String sql = "";  //存储第一次查询的sql语句，没有排序条件的sql
        //如果tags参数大于0，开始拼接sql语句
        if (tags.length > 0) {
            sqlbf = new StringBuffer("select id, title,answer_sum ,create_time from oe_ask_question  where is_delete = 0");
//            sqlbf = new StringBuffer("select id, title,answer_sum ,create_time from oe_ask_question  where    answer_sum > 0 and is_delete = 0");
            //循环标签数组，追加sql语句
            for (int i = 0; i < tags.length; i++) {
                if (i == 0) {
                    sqlbf.append("  and  ( find_in_set(?,tags) >0 ");
                } else {
                    sqlbf.append(" or find_in_set(?,tags) >0 ");
                }
                obj[i] = tags[i];
                array[i] = tags[i];
            }

            sql = sqlbf.append(")").toString();
            sqlbf.append("  and id !=?   order by  answer_sum  desc,create_time  desc limit 7");
            obj[length] = questionId;
            array[length] = questionId;

            //根据标签查询问题信息
            questionVos = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sqlbf.toString(), obj, BeanPropertyRowMapper.newInstance(AskQuestionVo.class));
        }
        //如果根据标签查询问题小于7条，则再根据学科查询问题，进行追加
        String id = "";   //存放按标签搜索出来的问题id
        String menuSql = "";
        if (questionVos.size() > 0 && questionVos.size() < 7) {

            //循环将已查处的问题id组装
            for (int j = 0; j < questionVos.size(); j++) {
                if (j == 0) {
                    id += "'" + questionVos.get(j).getId() + "'";
                } else {
                    id += ",'" + questionVos.get(j).getId() + "'";
                }
            }
            menuSql = sql + " and id !=?  UNION all select id,title,answer_sum,create_time  from oe_ask_question  where id not in (" + id + ")  and  ment_id =? and id != ? and is_delete = 0  and answer_sum > 0 order by  answer_sum  desc,create_time  desc  limit 7  ";
            //根据标签和学科查询问题信息
            array[length + 1] = menuId;
            array[length + 2] = questionId;
            questionVos = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(menuSql, array, BeanPropertyRowMapper.newInstance(AskQuestionVo.class));
        } else {
            menuSql = " select id,title,answer_sum,create_time  from oe_ask_question  where  ment_id =? and id != ? and is_delete = 0  and answer_sum > 0 order by  answer_sum  desc,create_time  desc  limit 7  ";
            questionVos = this.getNamedParameterJdbcTemplate().getJdbcOperations().query(menuSql, new Object[]{menuId, questionId}, BeanPropertyRowMapper.newInstance(AskQuestionVo.class));
        }

        return questionVos;
    }


    /**
     * 删除问题信息
     *
     * @param questionId 问题id号
     * @param user
     * @return
     */
    public void deleteQuestionById(String questionId, OnlineUser u, User user) {
        String sql = "select user_id, create_person from oe_ask_question where id = ? and is_delete = 0";
        List<Map<String, Object>> check = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql, questionId);
        if (check == null || check.size() <= 0) {
            throw new RuntimeException(String.format("不存在"));
        }

        String deleteSql = "";
        if (user == null) { //非管理员
            if (!check.get(0).get("user_id").toString().equals(u.getId())) {
                throw new RuntimeException("您不是此问题的提问者，无权删除！");
            }
            deleteSql = "delete from   oe_ask_question   where  id =?  ";
            this.getNamedParameterJdbcTemplate().getJdbcOperations().update(deleteSql, questionId);
        } else {
            deleteSql = "update  oe_ask_question set is_delete = 1    where  id =?  ";
        }
        this.getNamedParameterJdbcTemplate().getJdbcOperations().update(deleteSql, questionId);

        //改变投诉表中此问题投诉信息状态
        String updateSql = "update  oe_ask_accuse set status=1    where  target_id =?  and  target_type=0";
        this.getNamedParameterJdbcTemplate().getJdbcOperations().update(updateSql, questionId);
    }


    /**
     *
     * @param videoId  视频id
     * @param type : 1、全部问题  2、我的问题
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<AskQuestionVo> findVideoQuestion(String videoId,Integer type,Integer pageNumber, Integer pageSize,OnlineUser u) {
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId",u.getId());
        paramMap.put("videoId",videoId);
        StringBuffer sql = new StringBuffer();
        sql.append(" select if(q.user_id=:userId,true,false) isMyself,  q.create_nick_name,q.create_head_img,q.title,q.content,q.tags,q.create_time,q.create_person, ");
        sql.append(" q.answer_sum,q.browse_sum,q.praise_sum, m.id as ment_id ,q.id from oe_ask_question q  join oe_menu m  ");
        sql.append(" where  q.ment_id = m.id  and q.is_delete = 0  and q.video_id=:videoId  ");
        //我的问题
        if(type ==2){
            sql.append(" and q.user_id=:userId") ;
        }
        sql.append("   order by q.create_time  desc  ");

        Page<AskQuestionVo> page = this.findPageBySQL(sql.toString(),paramMap,AskQuestionVo.class,pageNumber,pageSize);
        return page;
    }


    /**
     * 修改问题信息内容
     * @param questionVo
     */
    public void updateQuestion(AskQuestionVo  questionVo){
        String sql = "update oe_ask_question  set content=:content  where id=:id";
        this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(questionVo));
    }

    /**
     * 获取被管理员删除的问题信息
     */
    public  Map<String, Object>  findDeleteAccuseQuestion(String questionId){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("questionId", questionId);
        String  sql =" select q.id,q.user_id, q.title,a.user_id userId,  case a.accuse_type  when 0 then '广告营销等垃圾信息' when 1 then '抄袭内容' " +
                     " when 2 then '辱骂等不文明言语的人身攻击' when 3 then '色情或反动的违法信息'  else a.content END AS text" +
                     " from oe_ask_question q, oe_ask_accuse  a where q.id=a.target_id  and a.target_type=0  and q.id=:questionId and q.is_delete=1";
        List<Map<String, Object>> courses= this.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
        return  courses.size() > 0 ? courses.get(0): null;
    }
}
