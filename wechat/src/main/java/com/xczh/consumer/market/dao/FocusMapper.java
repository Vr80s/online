package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.wxpay.entity.FocusVo;

import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class FocusMapper extends BasicSimpleDao{
	/***
	 * 我的关注（关注的人）  -- 讲师id
	 * @param course_id
	 * @return
	 * @throws SQLException
	 */
	public List<FocusVo> findMyFocus(String userId, Integer number, Integer pageSize) throws SQLException{
		StringBuffer sql = new StringBuffer("");
		
		//select * from 
/*		 select of.lecturer_id as lecturerId,user_id,ou.name as lecturerName,ou.small_head_photo as lecturerHeadImg,
		  (select count(*) from oe_focus where lecturer_id = lecturerId) as fansCount,
		   (select if(count(*) =0,1,2) from oe_focus where user_id = lecturerId and lecturer_id ='45715e6ccf2d42caa8505eddbcc19fb7' ) as isFocus
		     from oe_focus as of,oe_user as ou
		       where of.user_id = "45715e6ccf2d42caa8505eddbcc19fb7" and of.lecturer_id = ou.id; */
/*		sql.append(" select lecturer_id as lecturerId,lecturer_name as lecturerName,"
				+ "lecturer_head_img as lecturerHeadImg, ");
		sql.append(" (select count(*) from oe_focus where lecturer_id = lecturerId) as fansCount,");  //粉丝总数
		sql.append(" (select if(count(*) =0,1,2) from oe_focus where user_id = lecturerId and lecturer_id =? ) as isFocus");
		sql.append(" from oe_focus as of                              ");
		sql.append(" where of.user_id = ?                             ");*/
		
		sql.append(" select of.lecturer_id as lecturerId,user_id,ou.name as lecturerName,ou.small_head_photo as lecturerHeadImg, ");
		sql.append(" (select count(*) from oe_focus where lecturer_id = lecturerId) as fansCount,");  //粉丝总数
		sql.append(" (select if(count(*) =0,1,2) from oe_focus where user_id = lecturerId and lecturer_id =? ) as isFocus");
		sql.append(" from oe_focus as of,oe_user as ou                              ");
		sql.append(" where of.user_id = ?   and of.lecturer_id = ou.id   and ou.is_delete = 0  and ou.status = 0     ");
		
		List<FocusVo> list = super.queryPage(JdbcUtil.getCurrentConnection(), sql.toString(),
				number,pageSize,FocusVo.class,userId,userId);
		return list;
	}
	/***
	 * 我的粉丝   用户id就用现有的用户id
	 * @param course_id
	 * @return
	 * @throws SQLException
	 */
	public List<FocusVo> findMyFans(String userId, Integer pageNumber, Integer pageSize) throws SQLException{
		StringBuffer sql = new StringBuffer("");
	/*	sql.append(" select user_id as userId,user_name as userName,user_head_img as userHeadImg,");
		sql.append(" (select count(*) from oe_focus where lecturer_id = userId) as fansCount,"); //粉丝数
		sql.append(" (select if(count(*)=0,0,2)  from oe_focus where lecturer_id = userId and user_id = ?) as isFocus");//咱俩是否关注了
		sql.append(" from oe_focus as of                              ");
		sql.append(" where of.lecturer_id = ?                             ");*/

		
		sql.append(" select  user_id as lecturerId,ou.name as lecturerName,ou.small_head_photo as lecturerHeadImg,");
		sql.append(" (select count(*) from oe_focus where lecturer_id = lecturerId) as fansCount,"); //粉丝数
		sql.append(" (select if(count(*)=0,0,2)  from oe_focus where lecturer_id = lecturerId and user_id = ?) as isFocus");//咱俩是否关注了
		sql.append(" from oe_focus as of,oe_user as ou                               ");
		sql.append(" where of.lecturer_id = ?   and of.user_id = ou.id  and ou.is_delete = 0  and ou.status = 0  ");
		List<FocusVo> list = super.queryPage(JdbcUtil.getCurrentConnection(), sql.toString(),
				pageNumber,pageSize,FocusVo.class,userId,userId);
		return list;
	}
    /**
     * Description：增加关注信息
     * @param onlineUser
     * @param onlineLecturer
     * @param course_id
     * @throws SQLException
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	public void addFocusInfo(OnlineUser onlineUser, OnlineUser onlineLecturer, int course_id) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into oe_focus 		");
		sql.append("(id,user_id,user_name,user_head_img,lecturer_id,lecturer_name,lecturer_head_img,course_id,room_number )");
		sql.append("values                              ");
		sql.append("(?,?,?,?,?,?,?,?,?)  ");
		this.update(JdbcUtil.getCurrentConnection(),sql.toString(), 
		    UUID.randomUUID().toString().replace("-", ""),onlineUser.getId(),onlineUser.getName(),
		    onlineUser.getSmallHeadPhoto(),onlineLecturer.getId(),onlineLecturer.getName(),
		    onlineLecturer.getSmallHeadPhoto(),course_id,onlineLecturer.getRoomNumber()
		);	
	}
	/**
	 * 
	 * Description：讲师下的粉丝总数
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @return Integer
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public Integer findMyFansCount(String userId) throws SQLException {
		// TODO Auto-generated method stub
		
		
	/*	String sql = "select count(*) as allCount from oe_focus where lecturer_id = ?";
		Map<String, Object> map = super.query(JdbcUtil.getCurrentConnection(), sql.toString(), new MapHandler(),userId);
		Integer all_count = 0;
		if(map!=null && map.size()>0){
			Object allCount = map.get("allCount");
			all_count = Integer.parseInt(allCount.toString());
		}
		return all_count;*/
		
		String sql = "select count(*) as allCount from oe_focus of,oe_user as ou where "
				+ "  of.lecturer_id = ?  and of.user_id = ou.id and ou.is_delete = 0  and ou.status = 0 group by  of.user_id   ";
		List<Map<String, Object>> map = super.query(JdbcUtil.getCurrentConnection(), sql.toString(), new MapListHandler(),userId);
		Integer all_count = 0;
		if(map!=null && map.size()>0){
			all_count = map.size();
		}
		return all_count;
	}
	/**
	 * Description：我关注的讲师总数
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @return Integer
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public Integer findMyFocusCount(String userId) throws SQLException {
		// TODO Auto-generated method stub
		
/*		select of.lecturer_id as lecturerId from oe_focus as of,oe_user as ou 
	         where of.user_id = '09280d143bec426bae88198b69325166' and  of.lecturer_id = ou.id  group by  lecturer_id */
		
/*		String sql = "select count(*) as allCount from oe_focus where user_id = ? ";
		Map<String, Object> map = super.query(JdbcUtil.getCurrentConnection(), sql.toString(), new MapHandler(),userId);
		Integer all_count = 0;
		if(map!=null && map.size()>0){
			Object allCount = map.get("allCount");
			all_count = Integer.parseInt(allCount.toString());
		}
		return all_count;*/
		
		
		String sql = " select of.lecturer_id as lecturerId from oe_focus as of,oe_user as ou  ";
		sql+="  where of.user_id = ? and  of.lecturer_id = ou.id and ou.is_delete = 0  and ou.status = 0   group by  of.lecturer_id ";
		List<Map<String, Object>> map = super.query(JdbcUtil.getCurrentConnection(), sql.toString(), new MapListHandler(),userId);
		Integer all_count = 0;
		if(map!=null && map.size()>0){
			all_count = map.size();
		}
		return all_count;
		
	}
	/**
	 * 我是否已经关注了这个讲师呢   0 未关注 1已关注
	 * Description：
	 * @param userId
	 * @param lecturer_id
	 * @return
	 * @return Integer
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 * @throws SQLException 
	 */
	public Integer myIsFourslecturer(String userId,String lecturerId) throws SQLException{
		String sql = "select count(*) as allCount from oe_focus where user_id = ?  and  lecturer_id = ?";
		Map<String, Object> map = super.query(JdbcUtil.getCurrentConnection(), sql.toString(), new MapHandler(),userId,lecturerId);
		Integer all_count = 0;
		if(map!=null && map.size()>0){
			Object allCount = map.get("allCount");
			all_count = Integer.parseInt(allCount.toString());
		}
		if(all_count>0){
			all_count =1;
		}
		return all_count;
	}
	
	public ResponseObject cancelFocus(String userId, String lecturerId) throws SQLException {
		String sql = "delete from oe_focus where user_id = ?  and  lecturer_id = ?";
		super.update(JdbcUtil.getCurrentConnection(), sql,userId,lecturerId);
		return ResponseObject.newSuccessResponseObject("取消关注成功!");
	}
	
}
