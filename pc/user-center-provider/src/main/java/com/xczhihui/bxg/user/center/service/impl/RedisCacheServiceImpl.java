package com.xczhihui.bxg.user.center.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;

import org.springframework.stereotype.Service;

import com.xczhihui.bxg.user.center.service.CacheService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 由redis实现的缓存。
 * @author Haicheng Jiang
 */
public class RedisCacheServiceImpl implements CacheService {

	private JedisPool pool;

	public RedisCacheServiceImpl(String host,int port,int maxIdle, int maxTotal,int timeout) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(maxIdle);
		config.setMaxTotal(maxTotal);
		pool = new JedisPool(config,host,port,timeout);
	}
	
	public RedisCacheServiceImpl(String host,int port,int maxIdle, int maxTotal,int timeout,String password) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(maxIdle);
		config.setMaxTotal(maxTotal);
		pool = new JedisPool(config,host,port,timeout,password);
	}
	
	public RedisCacheServiceImpl() {
		pool = new JedisPool(new JedisPoolConfig(), "redis-server", 6379);
	}

	/**
	 * 按自然天缓存数据
	 * 
	 * @param key
	 * @param value
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
		if (bytes == null || bytes.length == 0)
			return null;

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
