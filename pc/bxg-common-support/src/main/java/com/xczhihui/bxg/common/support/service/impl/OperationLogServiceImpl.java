package com.xczhihui.bxg.common.support.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.support.domain.OperationLog;
import com.xczhihui.bxg.common.support.service.OperationLogService;
/**
 * 记录用户操作日志
 * @author Haicheng.J
 */
@Service("operationLogService")
public class OperationLogServiceImpl implements OperationLogService {
	@Resource(name = "simpleHibernateDao")
	private SimpleHibernateDao dao;
	
	@Override
	public void add(String loginName, String className, String methodName, String description, long millisecond,int systemType,int operationType,String operationId,String tableName) {
		OperationLog log = new OperationLog();
		log.setCreatePerson(loginName);
		log.setCreateTime(new Date());
		log.setDelete(false);
		
		log.setClassName(className);
		log.setDescription(description);
		log.setMethodName(methodName);
		log.setMillisecond(millisecond);
		
		log.setOperationId(operationId);
		log.setOperationType(operationType);
		log.setSystemType(systemType);
		log.setTableName(tableName);
		
		dao.save(log);
	}
}
