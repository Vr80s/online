package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.common.util.enums.HeadlineType;
import com.xczhihui.bxg.online.web.service.ArticleService;
import com.xczhihui.bxg.online.web.vo.AppraiseVo;
import com.xczhihui.bxg.online.web.vo.ArticleVo;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *   ArticleService:文章信息业务层接口实现类
 * * @author Rongcai Kang
 */
@Service
public class ArticleServiceImpl extends OnlineBaseServiceImpl implements ArticleService {

    /**
     * 获取博学社banner信息
     * @return
     */
    @Override
    public List<Map<String, Object>> getArticleBanner() {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        String  sql="select b.id,b.banner_path,b.title,a.name from oe_bxs_article b,article_type a where b.type_id = a.id and  b.is_delete= 0 and b.status=1 and b.is_recommend=1  order by b.sort desc ";
        return  dao.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
    }


    /**
     * 获取文章分类
     * @return
     */
    @Override
    public  List<Map<String,Object>>  getArticleType(){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        String  sql="select id,name from article_type  where `status`=1  order by sort desc ";
        return  dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
    }
    /**
     * 按分类获取分页后的文章
     * @param type
     * @return
     */
    @Override
    public Page<ArticleVo> getPaperArticle(Integer pageNumber, Integer pageSize, String type, String tagId)
    {
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        Map<String,Object> paramMap = new HashMap<String,Object>();
        String  sql="";
        if(tagId==null){
            paramMap.put("type",type);
            sql=" SELECT b.id,b.title,b.content,b.img_path,b.create_time,b.`user_id` name, Concat(\"[\",GROUP_CONCAT('\"',t.id,'\"'),\"]\") tagId," +
                " if(b.recommend_time< now(),0,b.sort) sort,"+
                "  Concat(\"[\",GROUP_CONCAT('\"',t.name,'\"'),\"]\") tag FROM oe_bxs_article b,article_r_tag ar,oe_bxs_tag t" +
                " where  b.id= ar.article_id and ar.tag_id=t.id and b.is_delete=0 " +
                " and b.status=1 and b.type_id=:type  GROUP BY b.id  order by sort desc,b.create_time desc";
        }else{
            paramMap.put("tagId",tagId);
            sql=" SELECT b.id,b.title,b.content,b.img_path,b.create_time,b.`user_id` name,  Concat(\"[\",GROUP_CONCAT('\"',t.id,'\"'),\"]\")  tagId," +
                    " if(b.recommend_time< now(),0,b.sort) sort,"+
                    " Concat(\"[\",GROUP_CONCAT('\"',t.name,'\"'),\"]\")  tag FROM oe_bxs_article b,article_r_tag ar,oe_bxs_tag t" +
                    " where  b.id= ar.article_id and ar.tag_id=t.id and b.is_delete=0 " +
                    " and b.status=1 and b.id  in (select article_id from article_r_tag  where status=1 and  tag_id=:tagId)  GROUP BY b.id  order by sort desc,b.create_time desc";
        }
        return dao.findPageBySQL(sql, paramMap, ArticleVo.class, pageNumber, pageSize);
    }


    /**
     * 获取热门文章
     * @return
     */
    @Override
    public List<Map<String,Object>>  getHotArticle(){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("t1", HeadlineType.DJZL.getCode());
        paramMap.put("t2",HeadlineType.MYBD.getCode());
        String sql="select id, title,if(recommend_time< now(),0,sort) sort from oe_bxs_article where is_delete=0 and `status`=1  order by sort desc,create_time desc limit 10";
        return  dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
    }

    /**
     * 热门标签
     * @return
     */
    @Override
    public List<Map<String, Object>> getHotTags() {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        String  sql=" SELECT a.* from  (select t.id, t.`name` from oe_bxs_article a ,article_r_tag art ,oe_bxs_tag t "+
                    " where a.id=art.article_id and art.tag_id=t.id and a.browse_sum > 0 order by a.browse_sum desc) as a  group by a.id  limit 8 ";
        return  dao.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
    }


    /**
     * 获取文章信息更具文章id
     * @return
     */
    @Override
    public Map<String,Object>  updateBrowseSumAndgetArticleById(Integer articleId, Integer preId, HttpServletRequest request)
    {

        String loginName="0";
        //获取当前登录用户信息
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
        if(loginUser !=null){
            loginName=loginUser.getLoginName();
        }
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("loginName",loginName);
        //预览文章
        String tableName="";
        String status="";
        if(preId ==null){
            tableName=" oe_bxs_article  ba";
            status=" and ba.status=1";
            paramMap.put("articleId",articleId);
            //修改文章的阅读量
            String  updateSql="update oe_bxs_article   set browse_sum=browse_sum+1  where id=:articleId";
            dao.getNamedParameterJdbcTemplate().update(updateSql, paramMap);
        }else {
            tableName=" oe_bxs_preview_article  ba";
            paramMap.put("articleId",preId);
        }
        String  sql=" SELECT ba.title,ba.content,ba.img_path,ba.create_time,ba.browse_sum,ba.praise_sum,ba.comment_sum,ba.`user_id` name,at.name typeName,Concat(\"[\",GROUP_CONCAT('\"',t.name,'\"'),\"]\")  tag , " +
                "Concat(\"[\",GROUP_CONCAT('\"',t.id,'\"'),\"]\") tagId, if(find_in_set(:loginName,ba.praise_login_names)>0,1,0)  as isPraise from "+tableName+",article_type at,article_r_tag ar,oe_bxs_tag t "+
                " where ba.type_id=at.id and ba.id =ar.article_id and ar.tag_id=t.id and " +
                "  ba.is_delete=0 "+status+" and ba.id=:articleId ";
        List< Map<String,Object>> articles=  dao.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
        return  articles.size() > 0 ? articles.get(0) : null;
    }

    /**
     * 相关推荐
     * @param articleId
     * @return
     */
    @Override
    public List<Map<String,Object>> getCorrelationTitle(Integer articleId){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("articleId", articleId);
        String sql= " select a.id,a.title from article_r_tag  art,oe_bxs_article a where art.article_id= a.id  and a.is_delete=0 and a.status=1 and a.id !=:articleId  and art.tag_id in (" +
                    " SELECT t.id from oe_bxs_article ba,article_r_tag ar,oe_bxs_tag t  where ba.id =ar.article_id and ar.tag_id=t.id " +
                    " and ba.is_delete=0 and ba.`status`=1  and ba.id=:articleId) group by art.article_id  order by a.browse_sum desc limit 7 ";
        return   dao.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
    }

    /**
     * 保存评论信息
     * @param appraiseVo
     */
    @Override
    public void  saveAppraise(AppraiseVo appraiseVo, HttpServletRequest request){
        //获取当前登录用户信息
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
        appraiseVo.setUser_id(loginUser.getId());
        appraiseVo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        String sql = "insert into oe_bxs_appraise (id,article_id,content,user_id,target_user_id,reply_comment_id) "
                   + "values (:id,:article_id,:content,:user_id,:target_user_id,:reply_comment_id)";
         dao.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(appraiseVo));

        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("articleId", appraiseVo.getArticle_id());
        //增加文章的评论数
        String  updateSql="update oe_bxs_article  set comment_sum=comment_sum+1  where id= :articleId";
        dao.getNamedParameterJdbcTemplate().update(updateSql,paramMap);
    }


    /**
     * 根据文章id，获取此文章下所有评论
     * @param articleId
     * @return
     */
    @Override
    public Page<AppraiseVo>  getAppraiseByArticleId(Integer articleId, Integer pageNumber, Integer pageSize, HttpServletRequest request){
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("articleId",articleId);
        //获取当前登录用户信息
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
        String user_id="-1";
        if(loginUser!= null ){
           user_id=loginUser.getId();
        }
        paramMap.put("userId", user_id);
        String sql=" SELECT ba.id,ba.content,ba.create_time,u.name,u.small_head_photo,ba.user_id,us.name nickName,if(ba.user_id=:userId,1,0) isMySelf " +
                   " from oe_bxs_appraise ba  LEFT JOIN oe_user us on ba.target_user_id=us.id,oe_user u " +
                   " where ba.user_id=u.id  and  ba.is_delete=0 and ba.article_id=:articleId  order by ba.create_time desc ";
        return  dao.findPageBySQL(sql, paramMap, AppraiseVo.class, pageNumber, pageSize);
    }

    /**
     * 更新文章点赞数
     * @param praiseSum 文章点赞数量
     * @param request
     * @return
     */
    @Override
    public  Map<String,Object>   updatePraiseSum(Integer articleId, Integer praiseSum, HttpServletRequest request){
        //获取当前登录用户信息
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);

        Map<String,Object> resultMap = new HashMap<String,Object>();
        Integer newPraiseSum=praiseSum;
        Boolean isPraise=false;
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("articleId",articleId);
        paramMap.put("loginName",loginUser.getLoginName());
        String  sql="SELECT id,praise_sum from oe_bxs_article  where id=:articleId   and find_in_set(:loginName,praise_login_names) > 0";
        List< Map<String,Object>>  articles= dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
        if (articles.size()>0){
            isPraise=true;
            newPraiseSum=Integer.valueOf(articles.get(0).get("praise_sum").toString());
        }else {
            newPraiseSum++;
            String  updateSql=" UPDATE oe_bxs_article set praise_sum=praise_sum+1,praise_login_names=if(praise_login_names is null,:loginName,concat(concat(praise_login_names,','),:loginName)) where  id=:articleId";
            dao.getNamedParameterJdbcTemplate().update(updateSql,paramMap);
            isPraise=false;
        }
        resultMap.put("praiseSum",newPraiseSum);
        resultMap.put("isPraise",isPraise);
        return  resultMap;
    }



    /**
     * 删除评论
     * @param appraiseId
     * @param request
     */
    @Override
    public  void   deleteAppraiseId(String appraiseId, HttpServletRequest request){
        //获取当前登录用户信息
        OnlineUser loginUser = (OnlineUser) UserLoginUtil.getLoginUser(request);
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("appraiseId",appraiseId);
        paramMap.put("userId",loginUser.getId());
        //查找此评论
        String  sql="select article_id from oe_bxs_appraise  where  id=:appraiseId and user_id=:userId";
        List< Map<String,Object>>  articles= dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
        if (articles.size() >0){
            String  deleteSql="delete from oe_bxs_appraise   where id=:appraiseId  and user_id=:userId";
            dao.getNamedParameterJdbcTemplate().update(deleteSql,paramMap);
            paramMap.put("articleId", articles.get(0).get("article_id"));
            //修改文章评论数
            String updateSql="update oe_bxs_article  set comment_sum=comment_sum-1 where  id=:articleId";
            dao.getNamedParameterJdbcTemplate().update(updateSql,paramMap);
        }
    }


    /**
     * 获取热门课程
     * @return
     */
    @Override
    public List<Map<String,Object>>  getHotCourses(){
         Map<String,Object> paramMap = new HashMap<String,Object>();
         String  sql=" SELECT c.id,c.grade_name courseName,c.original_cost as original_cost,c.current_price as current_price, c.smallimg_path, tm.`name`,c.description_show,c.is_free," +
                     " if(c.is_free=1,(SELECT count(*) FROM apply_r_grade_course where course_id=c.id),"+
                     " (select  sum(ifnull(student_count,0))+sum(ifnull(default_student_count,0)) from  oe_grade  where course_id=c.id  and is_delete=0 and status=1)) learnd_count"+
                     " from oe_course c,teach_method tm  where c.courseType=tm.id and c.is_delete=0 and c.`status`=1 and type=2  order by learnd_count desc limit 5";
         return   dao.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
    }

}
