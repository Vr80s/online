package com.xczhihui.bxg.common.support.service;

import com.xczhihui.bxg.common.support.domain.Attachment;

/**
 * 附件中心
 * @author Haicheng Jiang
 */
public interface AttachmentCenterService {
	
	public final static String DEFAULT_DOMAIN = "ixincheng.com";
	public final static String COOKIE_TOKEN_NAME = "_uc_t_";
	public static final String SESSION_TOKEN = "_token_";
	public static final String SESSION_USER = "_user_";

	/**
	 * 添加一个附件，兼容老的方式
	 * @param createUserId  创建人的ID
	 * @param type  区分个业务:squad,course等，在附件目录里有体现。
	 * @param fileName
	 * @param fileData
	 * @param contentType 文件内容类型
	 * @param ticket 用户中心的票
	 * @return
	 */
	public Attachment addAttachment(String createUserId, AttachmentType type, String fileName, byte[] fileData,String contentType,String ticket);
	/**
	 * 添加一个附件
	 * @param createUserId 上传者ID
	 * @param type 类型，dual双元，univ院校，online在线，kcenter知识中心
	 * @param fileName 文件名
	 * @param contentType 文件类型
	 * @param fileData 文件二进制数据
	 * @param flag 1图片（直接src="xxx.png"形式），2附件（aid形式）
	 * @param ticket 用户中心的票
	 * @return
	 * @throws Exception 
	 */
	public String upload(String createUserId, String type, String fileName,String contentType, byte[] fileData,String flag,String ticket) throws Exception;
	/**
	 * 获得一个附件的json字符串
	 * @param aid 附件id
	 * @return
	 * @throws Exception
	 */
	public String getAttachmentJson(String aid) throws Exception;
	/**
	 * 获得一个附件的数据
	 * @param aid 附件id
	 * @return
	 * @throws Exception
	 */
	public byte[] getAttachmentData(String aid) throws Exception;
	
	/**
	 * 获得一个附件对象
	 * @param aid 附件id
	 * @return
	 * @throws Exception
	 */
	public Attachment getAttachmentObject(String aid) throws Exception;
	/**
	 * 删除附件 按id
	 * @param id
	 * @return
	 */
	public Attachment deleteAttachment(String id);
	/**
	 * 删除附件 按文件名称
	 * @param fileName
	 * @param type
	 * @return
	 */
	public Attachment deleteAttachment(String fileName, AttachmentType type);
	/**
	 * 获得附件中心地址
	 * @return
	 */
	String getAttachmentCenterPath();
	
	/**
	 * 保存带有全部数据的附件实体,在内网附件上传后，同步到附件中心时候使用
	 * @param attachment
	 */
	public void saveFullAttachmentData(Attachment attachment);
	/**
	 * 根据文件名获得一个附件信息
	 * @param fileName 如 adfl3489sllk.zip
	 */
	public Attachment getAttachmentByFileName(String fileName);
	
}
