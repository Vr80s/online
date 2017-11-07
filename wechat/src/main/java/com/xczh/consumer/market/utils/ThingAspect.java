package com.xczh.consumer.market.utils;/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/13 13:15
 */

import com.alibaba.dubbo.rpc.RpcException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * @author liutao
 * @create 2017-09-13 13:15
 **/
@Aspect
@Component
public class ThingAspect {

    @Pointcut("execution(* com.xczh.consumer.market.controller..*.*(..))")
    public void controllerPointcut(){}

/*    @Before("recordLog()")
    public void before() {
    }*/

    @Around("controllerPointcut()")
    public Object  around(ProceedingJoinPoint pjp) throws Throwable{
        String methodName=pjp.getSignature().getName();
        Class<?> classTarget=pjp.getTarget().getClass();
        Class<?>[] par=((MethodSignature) pjp.getSignature()).getParameterTypes();
        Method objMethod=classTarget.getMethod(methodName, par);

        Transactional aCache=objMethod.getAnnotation(Transactional.class);
        if(aCache!=null){//支持事物
            Connection connection=JdbcUtil.getCurrentConnection();
            connection.setReadOnly(false);
            connection.setAutoCommit(false);
        }
        try {
            //调用真实方法
        Object o=  pjp.proceed();
            //提交事务
            if(aCache!=null){
                JdbcUtil.commit();
            }
            return o;
        } catch (Throwable e) {
            // 出现异常，回滚事务
            if(aCache!=null) {
                JdbcUtil.rollback();
            }
            //抛出异常
            if (e instanceof InvocationTargetException)
                throw ((InvocationTargetException)e).getTargetException();
            else
                throw e;

        } finally {
            //关闭数连接（将连接放回池里）
            JdbcUtil.closeConnection();

        }
    }

/*    @After("recordLog()")
    public void after() {
    }*/

    private void printLog(String str){
        System.out.println(str);
    }
}
