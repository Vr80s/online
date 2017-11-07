package com.xczhihui.bxg.user.center.service;

import java.io.Serializable;

/**
 * 缓存接口。
 * @author Haicheng Jiang
 */
public interface CacheService {

	/**
	 * 一分钟的秒数
	 */
	public final static int ONE_MINUTE = 60;

	/**
	 * 一小时的秒数
	 */
	public final static int ONE_HOUR = ONE_MINUTE * 60;

	/**
	 * 一天的秒数
	 */
	public final static int ONE_DAY = ONE_HOUR * 24;

	/**
	 * 存入缓存
	 * 
	 * @param key
	 * @param obj
	 * @param seconds
	 */
	public void set(final String key, final Serializable obj, final int seconds);

	/**
	 * 从缓存中获取对象
	 * 
	 * @param key
	 * @return
	 */
	public <T extends Serializable> T get(final String key);

	/**
	 * 删除缓存
	 * 
	 * @param key
	 */
	public void delete(String key);

	/**
	 * 增加缓存中数值。
	 * 
	 * @param key
	 * @param number
	 * @return
	 */
	public int inc(final String key, final int number);

	/**
	 * 获取缓存中的数值，key只能是inc方法中使用的key。
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(final String key);

}
