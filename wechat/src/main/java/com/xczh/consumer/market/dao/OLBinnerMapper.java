package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.WxcpBinner;
import com.xczh.consumer.market.utils.JdbcUtil;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * 轮播图
 * @author yjy
 *
 */
@Repository
public class OLBinnerMapper extends BasicSimpleDao {

	/****
	 * 轮播图列表查询
	 * @return
	 * @throws SQLException
	 */
	public List<WxcpBinner>  binnerList() throws SQLException{
		StringBuffer sql = new StringBuffer(" ");
		sql.append(" select * from oe_course_mobile_banner where status=1 order by seq asc");
		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanListHandler<>(WxcpBinner.class) );
	}
	
	/***
	 * 增加点击次数
	 * @param binner_id
	 * @throws SQLException
	 */
	public void binnerClick(String binner_id) throws SQLException{
		StringBuffer sql = new StringBuffer(" ");
		sql.append(" select * from oe_course_mobile_banner where id=?");
		WxcpBinner binner = super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new BeanHandler<>(WxcpBinner.class),binner_id);
		
		AtomicInteger hittimes = new AtomicInteger(binner.getClick_sum());
		int hittime = hittimes.incrementAndGet();
		StringBuffer sql2 = new StringBuffer("update oe_course_mobile_banner set click_sum = ? where id = ? ");
		Object params[] = {hittime,binner_id};
		super.update(JdbcUtil.getCurrentConnection(),sql2.toString(),params);	
	}
}
