package com.xczhihui.common.vo;

/**
 * K,V数据
 * @author duanqh
 *
 */
public class KeyValVo {

	/**
	 * key
	 */
	private Object id;

	/**
	 * value
	 */
	private Object name;

	/**
	 * key
	 */
	private Object _key;

	/**
	 * value
	 */
	private Object _value;


	public Object get_key() {
		return _key;
	}

	public void set_key(Object _key) {
		this._key = _key;
	}

	public Object get_value() {
		return _value;
	}

	public void set_value(Object _value) {
		this._value = _value;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public Object getName() {
		return name;
	}

	public void setName(Object name) {
		this.name = name;
	}
	
	
}
