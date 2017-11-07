package com.xczhihui.bxg.online.web.logger.service;

import com.xczhihui.bxg.online.web.logger.service.impl.BxgDatabaseLoggerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日志服务 工厂
 * @author majian
 * @date 2016-4-15
 */
public class LoggerServiceFactory {

    /**
     * 日志容器
     */
    private static Map<Class, Logger> loggerContext = new ConcurrentHashMap<Class, Logger>();

    /**
     * 返回日志容器
     *
     * @return
     */
    public static Map<Class, Logger> getLoggerContext() {
        return loggerContext;
    }

    /**
     * 是否存在日志容器中
     *
     * @param clazz
     * @return
     */
    public static boolean existsLoggerContext(Class clazz) {
        return loggerContext.containsKey(clazz);
    }


    /**
     * 追加到日志容器
     */
    public static void appendToLoggerContext(Class clazz, Logger logger) {
        if (!existsLoggerContext(clazz))
            loggerContext.put(clazz, logger);
    }

    /**
     * 私有化
     */
    private LoggerServiceFactory(){

    }

    /**
     * 返回日志
     * @param clazz
     * @return
     */
    public static Logger getLogger(Class clazz,Class loggerType){
        if(loggerType.getName().equals(Logger.class.getName())){
            if(!existsLoggerContext(clazz)){
                appendToLoggerContext(clazz, LoggerFactory.getLogger(clazz));
            }
            return (Logger)loggerContext.get(clazz);
        }else if(loggerType.getName().equals(BxgDatabaseLoggerServiceImpl.class.getName())){
            //TODO 这块如果调用自己的实现日志的话 可以从SpringApplicationContext中获取对应对象
        }else{
            throw new IllegalArgumentException("没有找到该Class的实现服务类");
        }
        return null;
    }

}
