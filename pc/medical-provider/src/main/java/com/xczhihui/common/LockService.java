package com.xczhihui.common;

/**
 * Description: 分布式锁切面服务<br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/3/4 0004-下午 8:46<br>
 */

import com.xczhihui.bxg.online.common.utils.RedissonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @Aspect 实现spring aop 切面（Aspect）：
 *         一个关注点的模块化，这个关注点可能会横切多个对象。事务管理是J2EE应用中一个关于横切关注点的很好的例子。 在Spring
 *         AOP中，切面可以使用通用类（基于模式的风格） 或者在普通类中以 @Aspect 注解（@AspectJ风格）来实现。
 *
 *         AOP代理（AOP Proxy）： AOP框架创建的对象，用来实现切面契约（aspect contract）（包括通知方法执行等功能）。
 *         在Spring中，AOP代理可以是JDK动态代理或者CGLIB代理。 注意：Spring
 *         2.0最新引入的基于模式（schema-based
 *         ）风格和@AspectJ注解风格的切面声明，对于使用这些风格的用户来说，代理的创建是透明的。
 * @author q
 *
 */
@Component
@Aspect
public class LockService {

    public static String LOCK_NAME = "lockName";
    public static String WAIT_TIME = "waitTime";
    public static String EFFECTIVE_TIME = "effectiveTime";

    @Autowired
    private RedissonUtil redissonUtil;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.xczhihui.common.Lock)")
    public void lockPointcut() {

    }

    @Around("lockPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Map<String,Object> map = getLockParams(point);
        String lockName = (String) map.get(LOCK_NAME);
        int waitTime = (int) map.get(WAIT_TIME);
        int effectiveTime = (int) map.get(EFFECTIVE_TIME);
        Object[] methodParam = null;
        Object object=null;
        boolean resl = false;
        //获取方法参数
        methodParam = point.getArgs();
        String lockKey = (String) methodParam[0];
        // 获得锁对象实例
        RLock redissonLock = redissonUtil.getRedisson().getLock(lockName+lockKey);
        try {
            //等待3秒 有效期8秒
            resl = redissonLock.tryLock(waitTime, effectiveTime, TimeUnit.SECONDS);
            if(resl){
               object = point.proceed(point.getArgs());
            }
        }catch (RuntimeException e){
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("网络错误，请重试");
        }finally {
            if(resl){
                logger.info("开锁,{}",lockName+lockKey);
                redissonLock.unlock();
            }else{
                logger.error("未获得锁,{}",lockName+lockKey);
                throw new RuntimeException("网络错误，请重试");
            }
        }
        return object;
    }

    /**
     * Description：获取方法的中锁参数
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/3/4 0004 下午 8:59
     **/
    public  Map<String,Object> getLockParams(ProceedingJoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        Class targetClass = Class.forName(targetName);
        Method[] method = targetClass.getMethods();
        Map<String,Object> map = new HashMap<>();
        for (Method m : method) {
            if (m.getName().equals(methodName)) {
                Class[] tmpCs = m.getParameterTypes();
                if (tmpCs.length == arguments.length) {
                    Lock lock = m.getAnnotation(Lock.class);
                    if (lock != null) {
                        String lockName = lock.lockName();
                        int waitTime = lock.waitTime();
                        int effectiveTime = lock.effectiveTime();
                        map.put(LOCK_NAME,lockName);
                        map.put(WAIT_TIME,waitTime);
                        map.put(EFFECTIVE_TIME,effectiveTime);
                    }
                    break;
                }
            }
        }
        return map;
    }
}
