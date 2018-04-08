package com.xczhihui.bxg.online.manager.headline.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.headline.service.ArticleTypeService;
import com.xczhihui.bxg.online.manager.headline.vo.ArticleTypeVo;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 博学社文章分类管理业务实现类
 * @Author Fudong.Sun【】
 * @Date 2017/1/9 10:59
 */
@Service
public class ArticleTypeServiceImpl extends OnlineBaseServiceImpl implements ArticleTypeService {
    @Override
    public Page<ArticleTypeVo> findArticleTypePage(ArticleTypeVo typeVo, int pageNumber, int pageSize) {
        Map<String, Object> paramMap = new HashMap<>();
        StringBuilder sql = new StringBuilder("SELECT at.id,at.`name`,at.create_time,at.`status`,");
        sql.append(" (select count(1) from oe_bxs_article where type_id = at.id) as articleNum");
        sql.append(" from article_type at");
        if(typeVo.getSortType() !=null && typeVo.getSortType() == 1){
            sql.append(" order by (select count(1) from oe_bxs_article where type_id = at.id) desc,at.sort desc");
        }else {
            sql.append(" order by at.status desc, at.sort desc");
        }
        Page<ArticleTypeVo> types = dao.findPageBySQL(sql.toString(), paramMap, ArticleTypeVo.class, pageNumber, pageSize);
        types.getItems().stream().filter(temp -> typeVo.getSortType() != null && typeVo.getSortType() == 1).forEach(temp -> temp.setSortType(1));
        return types;
    }

    @Override
    public void saveType(ArticleTypeVo typeVo) {
        String sql = "select count(1) from article_type where name=?";
        int count = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql, Integer.class,typeVo.getName());
        if(count>0){
            throw new IllegalArgumentException("文章分类名重复！");
        }else {
            sql = "insert into article_type (id,name,create_time) values(:id,:typeName,now())";
            Map<String, Object> param = new HashMap<>();
            param.put("id", UUID.randomUUID().toString().replace("-", ""));
            param.put("typeName", typeVo.getName());
            dao.getNamedParameterJdbcTemplate().update(sql, param);
        }
    }

    @Override
    public void updateTypeById(ArticleTypeVo typeVo) {
        String sql = "select count(1) from article_type where name=? and id <>?";
        int count = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql, Integer.class,typeVo.getName(),typeVo.getId());
        if(count>0){
            throw new IllegalArgumentException("文章分类名重复！");
        }else {
            sql = "update article_type set name=?,create_time=now() where id=?";
            dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, typeVo.getName(), typeVo.getId());
        }
    }

    @Override
    public void updateStatus(String id) {
        String sql = "select id,name,create_time,status,sort from article_type where id=?";
        List<Map<String,Object>> types = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql,id);
        Map<String,Object> type = types.size()>0? types.get(0):null;
        if(type!=null) {
            int status = Integer.parseInt(type.get("status").toString());
            if (1 == status) {//禁用
                sql = "update article_type set status=0 where id=?";
                dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, id);
            } else if (0 == status) {//启用前判断，最多只能启用6个
                sql = "select count(1) from article_type where status=1";
                int count = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql, Integer.class);
//                if (count >= 6){
//                    throw new RuntimeException("最多能启用6个文章分类！");
//                }else{
                    sql = "update article_type set status=1 where id=?";
                    dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, id);
//                }
            }
        }
    }

    @Override
    public void deletes(Set<String> ids) {
        //删除前判断分类下是否有文章，没有文章方可删除
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("ids", ids);
        String sql = "select count(1) from oe_bxs_article where type_id in (:ids)";
        int count = dao.getNamedParameterJdbcTemplate().queryForObject(sql,paramMap,Integer.class);
        if (count > 0){
            throw new RuntimeException("所选对象已经被引用，不能被删除！");
        }else {
            sql = "delete from article_type where id in (:ids)";
            dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
        }
    }

    @Override
    public void updateSortUp(String id){
        //查询排序相邻的两个文章分类
        String sql = "select id,name,create_time,status,sort from article_type where id=?";
        List<Map<String,Object>> typeList = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql,id);
        Map<String,Object> type = typeList.size()>0? typeList.get(0):null;
        String preId = type.get("id").toString();
        int sort = Integer.parseInt(type.get("sort").toString());
        String nextSql = "select id,name,create_time,status,sort from article_type where sort >"+ sort +" and status=1 order by sort asc limit 1";
        List<Map<String,Object>> nextTypeList = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(nextSql);
        Map<String,Object> nextType = nextTypeList.size()>0? nextTypeList.get(0):null;
        String nextId = nextType.get("id").toString();
        int nextSort = Integer.parseInt(nextType.get("sort").toString());
        if(type!=null && nextType!=null){
            //交换更新sort
            String edit_sql = "update article_type set sort=? where id=?";
            dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(edit_sql, nextSort, preId);
            dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(edit_sql, sort, nextId);
        }
    }

    @Override
    public void updateSortDown(String id) {
        //查询排序相邻的两个文章分类
        String sql = "select id,name,create_time,status,sort from article_type where id=?";
        List<Map<String,Object>> typeList = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql,id);
        Map<String,Object> type = typeList.size()>0? typeList.get(0):null;
        String preId = type.get("id").toString();
        int sort = Integer.parseInt(type.get("sort").toString());
        String nextSql = "select id,name,create_time,status,sort from article_type where sort <"+ sort +" order by sort desc limit 1";
        List<Map<String,Object>> nextTypeList = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(nextSql);
        Map<String,Object> nextType = nextTypeList.size()>0? nextTypeList.get(0):null;
        String nextId = nextType.get("id").toString();
        int nextSort = Integer.parseInt(nextType.get("sort").toString());
        if(type!=null && nextType!=null){
            //交换更新sort
            String edit_sql = "update article_type set sort=? where id=?";
            dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(edit_sql, nextSort, preId);
            dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(edit_sql, sort, nextId);
        }
    }
}
