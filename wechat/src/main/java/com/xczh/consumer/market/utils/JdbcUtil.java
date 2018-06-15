package com.xczh.consumer.market.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JdbcUtil {

	private static Logger logger = LoggerFactory.getLogger(JdbcUtil.class);
	public static ThreadLocal<Map<String,Connection>> currentConnections = new ThreadLocal<Map<String,Connection>>();

	private static ComboPooledDataSource comboPooledDataSource;


	@Autowired
	public  void setComboPooledDataSource(ComboPooledDataSource comboPooledDataSource) {
		JdbcUtil.comboPooledDataSource = comboPooledDataSource;
	}

	public static Connection getCurrentConnection() {


		Map<String, Connection> map = new HashMap<String, Connection>();

		Connection connection=null;

		if (currentConnections.get() == null) {
		try {
			connection = comboPooledDataSource.getConnection();
			connection.setAutoCommit(true);
			connection.setReadOnly(false);
			map.put("default", connection);
			currentConnections.set(map);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		}else{
			connection=currentConnections.get().get("default");
		}
			return connection;
	}

	/**
	 * 提交事务
	 */
	public static void commit() {
		try {
			if (currentConnections.get() != null) {
				for(Map.Entry<String, Connection> e : currentConnections.get().entrySet()){
					if (e.getValue() != null && !e.getValue().isClosed() && !e.getValue().isReadOnly()) {
						e.getValue().commit();
					}
				}
			}
		} catch (Exception e) {
			logger.error("事务提交失败！", e);
			e.printStackTrace();
		}
	}

	/**
	 * 回滚事务
	 */
	public static void rollback() {
		try {
			if (currentConnections.get() != null) {
				for(Map.Entry<String, Connection> e : currentConnections.get().entrySet()){
					if (e.getValue() != null && !e.getValue().isClosed() && !e.getValue().isReadOnly()) {
						e.getValue().rollback();
					}
				}
			}
		} catch (Exception e) {
			logger.error("事务回滚失败！", e);
			e.printStackTrace();
		}
	}

	/**
	 * 关闭jdbc连接
	 */
	public static void closeConnection() {
		try {
			if (currentConnections.get() != null) {
				for(Map.Entry<String, Connection> e : currentConnections.get().entrySet()){
					if (e.getValue() != null && !e.getValue().isClosed()) {
						e.getValue().close();
					}
				}
			}
			currentConnections.remove();
		} catch (Exception e) {
			logger.error("关闭jdbc连接失败！", e);
			e.printStackTrace();
		}
	}

}
