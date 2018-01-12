package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.service.CacheService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.util.Calendar;

/**
 * 由redis实现的缓存。
 * @author Alex Wang
 */
@Service
public class RedisCacheServiceImpl implements CacheService {

	
	
	private JedisPool pool;
	/*
	* #redis连接池配置
#服务地址，默认redis-server
redis-server-host=127.0.0.1
#服务端口，默认6379
redis-server-port=6379
#最大空闲数，默认10
redis-server-maxIdle=10
#最大连接数，默认50
redis-server-maxTotal=50
#连接超时时间，默认50000，50秒
redis-server-timeout=50000*/
	public RedisCacheServiceImpl() {
		String host = null;
		host = host == null ? "redis-server" : host;
		
		String portstr = "6379";
		int port = portstr == null ? 6379 : Integer.valueOf(portstr);
		
		String maxIdlestr = null;
		int maxIdle = maxIdlestr == null ? 10 : Integer.valueOf(maxIdlestr);
		
		String maxTotalstr = null;
		int maxTotal = maxTotalstr == null ? 50 : Integer.valueOf(maxTotalstr);
		
		String timeoutstr = null;
		int timeout = timeoutstr == null ? 50000 : Integer.valueOf(timeoutstr);
		
		String password =null;

		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(maxIdle);
		config.setMaxTotal(maxTotal);
		
		if (password == null) {
			pool = new JedisPool(config,host,port,timeout);
		} else {
			pool = new JedisPool(config,host,port,timeout,password);
		}
	}

	/**
	 * 按自然天缓存数据
	 * 
	 * @param key
	 */
	public void setNatureDay(String key, Serializable obj) {
		Calendar cal = Calendar.getInstance();
		int hours = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int seconds = cal.get(Calendar.SECOND);
		int time = ONE_DAY - (hours * 60 * 60 + minute * 60 + seconds);
		this.set(key, obj, time);
	}

	@Override
	public void set(final String key, final Serializable obj, final int seconds) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			byte[] value = object2Bytes(obj);
			jedis.setex(key.getBytes(), seconds, value);
		} finally {
			this.release(jedis);
		}
	}

	@Override
	public int inc(final String key, final int count) {
		Jedis jedis = null;
		Long c = Long.valueOf(0);
		try {
			jedis = this.getJedis();
			c = jedis.incrBy(key, count);
		} finally {
			this.release(jedis);
		}
		return c.intValue();
	}

	@Override
	public int getInt(final String key) {
		Jedis jedis = null;
		int count = 0;
		try {
			jedis = this.getJedis();
			String obj = jedis.get(key);
			if (obj != null && obj.trim().length() > 0) {
				count = Integer.parseInt(obj);
			}
		} finally {
			this.release(jedis);
		}
		return count;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Serializable> T get(String key) {
		Jedis jedis = null;
		Serializable obj = null;
		try {
			jedis = this.getJedis();
			byte[] value = jedis.get(key.getBytes());
			obj = byte2Object(value);
		} finally {
			this.release(jedis);
		}
		return (T) obj;
	}

	@Override
	public void delete(String key) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.del(key);
		} finally {
			this.release(jedis);
		}
	}

	/**
	 * key还有多长时间过期
	 * 
	 * @param key
	 * @return
	 */
	public int getTTL(String key) {
		Jedis jedis = null;
		int time = -1;
		try {
			jedis = this.getJedis();
			time = jedis.ttl(key).intValue();
		} finally {
			this.release(jedis);
		}
		return time;
	}

	private Serializable byte2Object(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
            return null;
        }

		try {
			ObjectInputStream inputStream;
			inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
			return (Serializable) inputStream.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private byte[] object2Bytes(Object value) {
		if (value == null) {
			return null;
		}

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream outputStream;
		try {
			outputStream = new ObjectOutputStream(arrayOutputStream);
			outputStream.writeObject(value);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			try {
				arrayOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return arrayOutputStream.toByteArray();
	}

	private Jedis getJedis() {
		return pool.getResource();
	}

	/**
	 * 将jedis对象放回池中。
	 * 
	 * @param jedis
	 */
	private void release(Jedis jedis) {
		if (jedis != null) {
			this.pool.returnResource(jedis);
		}
	}
}
