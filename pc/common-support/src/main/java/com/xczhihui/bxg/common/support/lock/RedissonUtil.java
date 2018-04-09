package com.xczhihui.bxg.common.support.lock;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Redisson
 * @author zhuwenbao
 */
@Component
public class RedissonUtil {

    private final static Logger logger = LoggerFactory.getLogger(RedissonUtil.class);

    private String redisHost;

    private String redisPort;

    private Config config = new Config();

    private Redisson redisson = null;

    /**
     * RedissonUtil执行完构造方法后执行该方法
     */
    @PostConstruct
    private void init(){

        try {
            config.useSingleServer().setAddress(new StringBuilder().append(redisHost)
                    .append(":").append(redisPort).toString());

            redisson = (Redisson) Redisson.create(config);

            logger.info("----------Redisson init successfully in {} : {}", redisHost, redisPort);

        }catch (Exception e){

            logger.error("----------Redisson init unsuccessfully", e);

        }

    }

    public Redisson getRedisson() {
        return redisson;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public String getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }

}
