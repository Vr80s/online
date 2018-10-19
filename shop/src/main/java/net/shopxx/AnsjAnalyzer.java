/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: MpCk6RDBlx3B3+cU8uJyVwOCKSJMuocI
 */
package net.shopxx;

/**
 * Analyzer - Ansj
 * 
 * @author ixincheng
 * @version 6.1
 */
public class AnsjAnalyzer extends org.ansj.lucene5.AnsjAnalyzer {

	/**
	 * 默认类型
	 */
	public static final org.ansj.lucene5.AnsjAnalyzer.TYPE DEFAULT_TYPE = org.ansj.lucene5.AnsjAnalyzer.TYPE.index_ansj;

	/**
	 * 构造方法
	 */
	public AnsjAnalyzer() {
		super(DEFAULT_TYPE);
	}

}