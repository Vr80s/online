package com.xczhihui.quene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRedisQuene {
    private static Logger logger = LoggerFactory.getLogger(TestRedisQuene.class);
    public static byte[] redisKey = "KEY-yuxin".getBytes();
    static{
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        pop();
    }

    private static void pop() throws Exception {
        Message msg = new Message(1,null);
        while(msg != null){
            byte[] bytes = JedisUtil.rpop(redisKey);
            if(bytes==null){
                logger.info("取完了");
                return;
            }
            msg = (Message) ObjectUtil.bytesToObject(bytes);
            double d = Math.random();//生成一个0~1的随机数
            if(d>0.5){//模拟失败场景
                JedisUtil.lpush(redisKey, ObjectUtil.objectToBytes(msg));
                logger.info("reset==="+msg.getId());
            }else{
                logger.info(msg.getId()+"   "+msg.getContent());
            }
        }
    }

    private static void init() throws Exception {
        Message msg1 = new Message(11, "内容1");
        JedisUtil.lpush(redisKey, ObjectUtil.objectToBytes(msg1));
        Message msg2 = new Message(21, "内容2");
        JedisUtil.lpush(redisKey, ObjectUtil.objectToBytes(msg2));
        Message msg3 = new Message(31, "内容3");
        JedisUtil.lpush(redisKey, ObjectUtil.objectToBytes(msg3));
    }

}