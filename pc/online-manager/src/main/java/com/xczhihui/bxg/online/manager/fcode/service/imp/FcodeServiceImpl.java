package com.xczhihui.bxg.online.manager.fcode.service.imp;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.fcode.dao.FcodeDao;
import com.xczhihui.bxg.online.manager.fcode.service.FcodeService;
import com.xczhihui.bxg.online.manager.fcode.vo.FcodeVo;
import com.xczhihui.bxg.online.manager.utils.TimeUtil;

@Service
public class FcodeServiceImpl  extends OnlineBaseServiceImpl implements FcodeService{
	@Autowired
	FcodeDao fcodeDao;
	@Override
	public Page<FcodeVo> findPage(FcodeVo searchVo, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
        sql.append(" SELECT "
        		+ "a.id, "
        		+ "a.lot_no, "
        		+ "a.auto, "
        		+ "a.subject_ids,"
        		+ "(select GROUP_CONCAT(e.`name`) from oe_menu e where e.id in (a.subject_ids)) as subjectNames, "
        		+ "a.NAME, "
        		+ "fcode_sum, " // (IF (a.auto = 1,(SELECT COUNT(1) FROM oe_fcode f WHERE f.lot_no = a.lot_no ),a.fcode_sum))AS fcodeSum,
        		+ "a.start_time, "
        		+ "a.end_time, "
        		+ "a.create_person, "
        		+ "a.create_time, "
        		+ "GROUP_CONCAT(b.course_id) as includeCourseIds, "
        		+ "GROUP_CONCAT(c.grade_name) as includeCourses ,"
        		+ "(SELECT COUNT(1) from oe_fcode d WHERE d.`status`=2 and d.lot_no =a.lot_no) as clockFcodeSum, "
        		+ "(SELECT COUNT(1) from oe_fcode d WHERE d.`status`=3 and d.lot_no =a.lot_no) as usedFcodeSum, "
        		+ "a.start_time <= now() startFlag, " //活动已开始
				+ "a.end_time <= now() endFlag " //活动已经结束
        		+ "FROM "
        		+ "oe_fcode_activity a, "
        		+ "oe_fcode_activity_course b, "
        		+ "oe_course c "
        		+ "WHERE "
        		+ "a.id = b.activity_id and b.course_id = c.id  ");
        if(searchVo.getName()!=null&&!"".equals(searchVo.getName())){
        	sql.append(" and a.lot_no like :name or a.create_person like :name or a.NAME like :name ");
        	 paramMap.put("name", "%" + searchVo.getName() + "%");
        }
        sql.append("GROUP BY a.lot_no ");
        
        Page<FcodeVo> fcodeVo=fcodeDao.findPageBySQL(sql.toString(), paramMap, FcodeVo.class, currentPage, pageSize);
		return fcodeVo;
	}
	@Override
	public void addFcodeRule(FcodeVo fcodeVo) throws DataAccessException, ParseException {
		// TODO Auto-generated method stub
		if(fcodeVo.getFcodeSum()==null){
			fcodeVo.setAuto(1);
		}else{
			fcodeVo.setAuto(0);
		}
		String sql ="select id from oe_fcode_lot where lot_no=:lotNo";
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("lotNo", fcodeVo.getLotNo());
		List<FcodeVo> temp = dao.getNamedParameterJdbcTemplate().query(sql,paramSource, BeanPropertyRowMapper.newInstance(FcodeVo.class));
		if(temp.size()>0){
			throw new RuntimeException("批次号已存在！");
		}
		sql=" INSERT INTO oe_fcode_lot (`id`, `lot_no`,`expiry_time`) VALUES (?, ?, ?)";
		fcodeDao.getNamedParameterJdbcTemplate().getJdbcOperations()
		.update(sql, new Object[]{UUID.randomUUID().toString().replace("-", ""),fcodeVo.getLotNo(),TimeUtil.addDate(fcodeVo.getEndTime(), 10)});
		
		String id = UUID.randomUUID().toString().replace("-", "");
		sql =" INSERT INTO oe_fcode_activity (`id`, `lot_no`, `name`, `subject_ids`, `auto`, `fcode_sum`, start_time, end_time, `create_time`, `create_person`) " +
		" VALUES (?, ?, ?, ?, ?, ?, ?, ?,now(),?)";
		//保存规则主表
		fcodeDao.getNamedParameterJdbcTemplate().getJdbcOperations()
					.update(sql, new Object[]{id,fcodeVo.getLotNo(),fcodeVo.getName(),fcodeVo.getSubjectIds(),
							fcodeVo.getAuto(),fcodeVo.getFcodeSum()==null?0:fcodeVo.getFcodeSum(),fcodeVo.getStartTime(),fcodeVo.getEndTime(),fcodeVo.getCreatePerson()});
		
		sql ="INSERT INTO oe_fcode_activity_course (id,activity_id,`course_id`) VALUES(?,?,?)";
		String[] courseIds = fcodeVo.getIncludeCourseIds().split(",");
		for(int i=0;i<courseIds.length;i++){
			fcodeDao.getNamedParameterJdbcTemplate().getJdbcOperations()
			.update(sql,new Object[]{UUID.randomUUID().toString().replace("-", ""),id,courseIds[i]});
		}
		sql ="INSERT INTO oe_fcode (id,lot_no,`fcode`,`status`) VALUES(?,?,?,?)";
		if(fcodeVo.getFcodeSum()!=null&&fcodeVo.getFcodeSum()>0&&1!=fcodeVo.getAuto()){
			for(int i=0;i<fcodeVo.getFcodeSum();i++){
				fcodeDao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql,new Object[]{UUID.randomUUID().toString().replace("-", ""),fcodeVo.getLotNo(),UUID.randomUUID().toString().replace("-", ""),0});
//				.update(sql,new Object[]{UUID.randomUUID().toString().replace("-", ""),id,UUID.randomUUID().toString().replace("-", ""),0});
			}
		}
	}
	@Override
	public void updateFcodeRule(FcodeVo fcodeVo) {
		// TODO Auto-generated method stub
		if(fcodeVo.getFcodeSum()==null){
			fcodeVo.setAuto(1);
		}else{
			fcodeVo.setAuto(0);
		}
		
		String sql ="select a.start_time <= now() startFlag, "//活动已开始
				+ "a.end_time <= now() endFlag "//活动已结束
				+ "from oe_fcode_activity where id=:id";
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", fcodeVo.getId());
		List<FcodeVo> temp = dao.getNamedParameterJdbcTemplate().query(sql,paramSource, BeanPropertyRowMapper.newInstance(FcodeVo.class));
		//活动期间
		if(temp.get(0).getStartFlag()&&!temp.get(0).getEndFlag()){
			sql =" UPDATE oe_fcode_activity SET end_time=? WHERE id=?";
			//保存规则主表
			fcodeDao.getNamedParameterJdbcTemplate().getJdbcOperations()
						.update(sql, new Object[]{fcodeVo.getEndTime(),fcodeVo.getId()});
		}
		//活动未开始
		if(!temp.get(0).getStartFlag()){
			sql =" UPDATE oe_fcode_activity SET name=?,subject_ids=?,start_time=?,end_time=? WHERE id=?";
			//保存规则主表
			fcodeDao.getNamedParameterJdbcTemplate().getJdbcOperations()
						.update(sql, new Object[]{fcodeVo.getName(),fcodeVo.getSubjectIds(),
								  fcodeVo.getStartTime(),fcodeVo.getEndTime(),fcodeVo.getId()});
			sql ="DELETE FROM oe_fcode_activity_course WHERE activity_id = ?";
			//删除已有关系
			fcodeDao.getNamedParameterJdbcTemplate().getJdbcOperations()
						.update(sql, new Object[]{fcodeVo.getId()});
			sql ="INSERT INTO oe_fcode_activity_course (id,activity_id,`course_id`) VALUES(?,?,?)";
			String[] courseIds = fcodeVo.getIncludeCourseIds().split(",");
			for(int i=0;i<courseIds.length;i++){
				fcodeDao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql,new Object[]{UUID.randomUUID().toString().replace("-", ""),fcodeVo.getId(),courseIds[i]});
			}
			
			sql ="DELETE FROM oe_fcode WHERE lot_no = ?";
			//删除已有关系
			fcodeDao.getNamedParameterJdbcTemplate().getJdbcOperations()
						.update(sql, new Object[]{fcodeVo.getId()});
			sql ="INSERT INTO oe_fcode (id,lot_no,`fcode`,`status`) VALUES(?,?,?,?)";
			if(fcodeVo.getFcodeSum()>0&&1!=fcodeVo.getAuto()){
				for(int i=0;i<fcodeVo.getFcodeSum();i++){
					fcodeDao.getNamedParameterJdbcTemplate().getJdbcOperations()
					.update(sql,new Object[]{UUID.randomUUID().toString().replace("-", ""),fcodeVo.getId(),UUID.randomUUID().toString().replace("-", ""),0});
				}
			}
		}
		//活动已结束
		if(!temp.get(0).getEndFlag()){
			throw new RuntimeException("活动已结束,非法操作!");
		}

	}
	@Override
	public Page<FcodeVo> findFcodeDetailPage(FcodeVo searchVo, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuilder sql = new StringBuilder();
        sql.append(" SELECT "
        		+ "t2.fcode,"
        		+ "t2.create_time, "
        		+ "t1.create_time as lockTime, "
        		+ "t2.use_time, "
        		+ "t2.status, "
        		+ "t2.used_course_id, "
        		+ "t4.grade_name as CourseName, "
        		+ "t3.`name`, "
        		+ "t3.login_name "
        		+ "FROM "
        		+ "oe_fcode t2 LEFT JOIN "
        		+ "oe_fcode_user t1 ON t2.fcode=t1.fcode "
        		+ "LEFT JOIN oe_user t3 ON t2.used_user_id = t3.id "
        		+ "LEFT JOIN oe_course t4 ON t2.used_course_id=t4.id "
        		+ "  LEFT JOIN oe_fcode_activity t5 ON t2.lot_no = t5.id " //2017.08.09  yuruixin
        		+ "WHERE  t5.lot_no=:lotNo ");
        paramMap.put("lotNo",searchVo.getLotNo());
        if(searchVo.getName()!=null&&!"".equals(searchVo.getName())){
        	sql.append(" and t1.fcode like :name or t4.grade_name like :name or t3.`name` like :name or t3.login_name like :name");
        	 paramMap.put("name", "%" + searchVo.getName() + "%");
        }
        if(searchVo.getStatus()!=null&&!"".equals(searchVo.getStatus())){
        	sql.append(" and t2.status =:status ");
        	 paramMap.put("status",searchVo.getStatus());
        }
        Page<FcodeVo> fcodeVo=fcodeDao.findPageBySQL(sql.toString(), paramMap, FcodeVo.class, currentPage, pageSize);
		return fcodeVo;
	}
	@Override
	public List<FcodeVo> getFcodeBylotNo(String lotNo,Integer status) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append("select id from oe_fcode where lot_no=:lotNo ");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("lotNo", lotNo);
		if(status!=null){
			sql.append(" and status=:status ");
			 paramSource.addValue("status", status);
		}
		
		List<FcodeVo> temp = dao.getNamedParameterJdbcTemplate().query(sql.toString(),paramSource, BeanPropertyRowMapper.newInstance(FcodeVo.class));
		return temp;
	}

}
