package com.xczhihui.bxg.online.manager.cloudClass.service.impl;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.cloudClass.dao.VideoResDao;
import com.xczhihui.bxg.online.manager.cloudClass.service.StudyPlanService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 学习计划模块业务层实现类
 * @Author Fudong.Sun【】
 * @Date 2017/1/3 17:00
 */
@Service
public class StudyPlanServiceImpl extends OnlineBaseServiceImpl implements StudyPlanService{

    @Autowired
    VideoResDao videoResDao;

    @Override
    public List<Map<String, Object>> planInfo(Integer courseId,Integer week) {
        int weekSize = 7;
        int startDay = (week-1)*weekSize;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT id,course_id,`day` from oe_plan_template where course_id=? ORDER BY `day` LIMIT ?,?");
        List<Map<String, Object>> planList = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql.toString(),courseId,startDay,weekSize);
        StringBuffer kpointSql= new StringBuffer();
        kpointSql.append(" select op.id,op.`name`,");
        kpointSql.append(" (select sum(time_to_sec(CONCAT('00:',video_time))) div 60 from oe_video where chapter_id=op.id)as min,");
        kpointSql.append(" (select sum(time_to_sec(CONCAT('00:',video_time))) mod 60 from oe_video where chapter_id=op.id)as sec");
        kpointSql.append(" from oe_chapter op where op.is_delete=0 and op.plan_template_id=? order by op.sort");
        for (Map<String, Object> planInfo : planList) {
            String templateId = (String) planInfo.get("id");
            List<Map<String, Object>> kPoints = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(kpointSql.toString(),templateId);
            for (Map<String, Object> kPoint : kPoints) {
                int min = Integer.parseInt(kPoint.get("min")==null? "0":kPoint.get("min").toString());
                int sec = Integer.parseInt(kPoint.get("sec")==null? "0":kPoint.get("sec").toString());
                String min_str = min<10? "0"+min : min+"";
                String sec_str = sec<10? "0"+sec : sec+"";
                kPoint.put("videoTime",min_str+":"+sec_str);
            }
            planInfo.put("kPoints",kPoints);
        }
        return planList;
    }
    @Override
    public List<TreeNode> getChapterTree(Integer courseId,String templateId) {
        String sql= "select id,name ,parent_id as pId,level ,level as type ,level as contenttype from oe_chapter where is_delete=0 and course_id= "+courseId +" order by sort";
        List<TreeNode> treeNodes=videoResDao.findEntitiesByJdbc(TreeNode.class, sql, null);
        StringBuffer kpointSql= new StringBuffer();
        kpointSql.append(" select op.id,op.`name`");
        kpointSql.append(" from oe_chapter op where op.is_delete=0 and op.plan_template_id=?");
        List<Map<String, Object>> kPoints = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(kpointSql.toString(),templateId);
        if(treeNodes!=null&&treeNodes.size()>0){
            for(TreeNode chapter:treeNodes){

                if(chapter.getLevel()==2){//章
                    chapter.setActName(chapter.getName());
					chapter.setName(chapter.getName());
                    chapter.setOpen(true);
                }

                if(chapter.getLevel()==3){//节
                    chapter.setActName(chapter.getName());
					chapter.setName(chapter.getName());
                    chapter.setOpen(true);
                }

                if(chapter.getLevel()==4){//知识点
                    chapter.setActName(chapter.getName());
					chapter.setName(chapter.getName());
                    chapter.setOpen(true);
                    //是否是勾选状态
                    for (Map<String, Object> kPoint : kPoints) {
                        if(chapter.getId().equalsIgnoreCase((String) kPoint.get("id"))){
                            chapter.setChecked(true);
                        }
                    }
                    //被其他模板配置后为不可勾选状态
                    String tempsql= "select op.id,op.`name`,op.plan_template_id from oe_chapter op where op.id=?";
                    List<Map<String, Object>> templist = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(tempsql,chapter.getId());
                    for (Map<String, Object> obj : templist) {
                        String tempId = (String) obj.get("plan_template_id");
                        if(tempId!=null && !templateId.equalsIgnoreCase(tempId)){
                            chapter.setChkDisabled(true);
                        }
                    }
                    chapter.setId("kpoint_"+chapter.getId());
                }
            }
        }
        return treeNodes;
    }

    @Override
    public void savePlanKnowledge(String templateId, Set<String> ids) {
        //清除原有知识点配置
        String delSql = "update oe_chapter set plan_template_id=null where plan_template_id=?";
        dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(delSql,templateId);
        //更新新的知识点配置
        if(ids.size()>0) {
            StringBuffer sql = new StringBuffer();
            sql.append("update oe_chapter set plan_template_id=:templateId where id in (:ids)");
            MapSqlParameterSource paramMap = new MapSqlParameterSource();
            paramMap.addValue("templateId", templateId);
            paramMap.addValue("ids", ids);
            dao.getNamedParameterJdbcTemplate().update(sql.toString(), paramMap);
        }
    }

    @Override
    public ResponseObject updateStudyDay(Integer totalDay, Integer oldDay, Integer courseId) {
        //新的授课天数大于原授课天数保留原天数数据，额外添加天数
        String updateSql;
        List<Object[]> batchArgs = new ArrayList<>();
        if (totalDay > oldDay) {
            updateSql="insert into oe_plan_template (id,course_id,`day`) values (?,?,?)";
            for(int i=oldDay+1;i<totalDay+1;i++) {
                Object[] args = new Object[] {UUID.randomUUID().toString().replace("-",""),courseId,i};
                batchArgs.add(args);
            }
            dao.getNamedParameterJdbcTemplate().getJdbcOperations().batchUpdate(updateSql.toString(),batchArgs);
        }
        //新的授课天数小于原授课天数删除多余天数和配置的知识点，保留剩余天数数据,
        if (totalDay < oldDay) {
            updateSql="update oe_chapter set plan_template_id=null where plan_template_id in (select id from oe_plan_template where course_id=? and day>"+totalDay+")";
            dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(updateSql,courseId);
            updateSql="delete from oe_plan_template where course_id=? and day>"+totalDay;
            dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(updateSql,courseId);
        }
        return ResponseObject.newSuccessResponseObject(totalDay);
    }

    @Override
    public ResponseObject ifExistPlan(Integer courseId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select count(1) from oe_plan where template_id in");
        sql.append(" (select id from oe_plan_template where course_id=?)");
        int useTemp = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql.toString(), Integer.class, courseId);
        if (useTemp > 0) {
            return ResponseObject.newSuccessResponseObject(true);
        } else {
            return ResponseObject.newSuccessResponseObject(false);
        }
    }

    @Override
    public ResponseObject ifExistTemplate(Integer courseId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select count(1) from oe_plan_template where course_id=?");
        int temp = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql.toString(), Integer.class, courseId);
        if (temp > 0) {
            return ResponseObject.newSuccessResponseObject(temp);
        } else {
            return ResponseObject.newErrorResponseObject("没有学习计划模板");
        }
    }

    @Override
    public ResponseObject savePlanTemplate(Integer totalDay, Integer courseId) {
        String sql="insert into oe_plan_template (id,course_id,`day`) values (?,?,?)";
        List<Object[]> batchArgs = new ArrayList<>();
        for(int i=1;i<totalDay+1;i++) {
            Object[] args = new Object[] {UUID.randomUUID().toString().replace("-",""),courseId,i};
            batchArgs.add(args);
        }
        dao.getNamedParameterJdbcTemplate().getJdbcOperations().batchUpdate(sql.toString(),batchArgs);
        return ResponseObject.newSuccessResponseObject("生成学习计划模板成功！");
    }

    @Override
    public ResponseObject ifHasResource(Integer courseId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select count(1) from oe_chapter where course_id=? and `level`=4 and is_delete=0");
        int res = dao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForObject(sql.toString(), Integer.class, courseId);
        if (res > 0) {
            return ResponseObject.newSuccessResponseObject(true);
        } else {
            return ResponseObject.newSuccessResponseObject(false);
        }
    }
}
