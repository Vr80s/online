package com.xczhihui.bxg.common.support.service;

import com.xczhihui.bxg.common.support.domain.Attachment;

/**
 * 附件中心
 *
 * @author Haicheng Jiang
 */
public interface AttachmentCenterService {

    /**
     * 添加一个附件，兼容老的方式
     *
     * @param createUserId 创建人的ID
     * @param type         区分个业务:squad,course等，在附件目录里有体现。
     * @param fileName
     * @param fileData
     * @param contentType  文件内容类型
     * @return
     */
    Attachment addAttachment(String createUserId, AttachmentType type, String fileName, byte[] fileData, String contentType);

    /**
     * 添加一个附件
     *
     * @param createUserId 上传者ID
     * @param projectName         类型，dual双元，univ院校，online在线，kcenter知识中心
     * @param fileName     文件名
     * @param contentType  文件类型
     * @param fileData     文件二进制数据
     * @param fileType         1图片（直接src="xxx.png"形式），2附件（aid形式）
     * @return
     * @throws Exception
     */
    String upload(String createUserId, String projectName, String fileName, String contentType, byte[] fileData, String fileType) throws Exception;

    /**
     * 获得一个附件的json字符串
     *
     * @param aid 附件id
     * @return
     * @throws Exception
     */
    String getAttachmentJson(String aid) throws Exception;

    /**
     * 获得一个附件的数据
     *
     * @param aid 附件id
     * @return
     * @throws Exception
     */
    byte[] getAttachmentData(String aid) throws Exception;

    /**
     * 获得一个附件对象
     *
     * @param aid 附件id
     * @return
     * @throws Exception
     */
    Attachment getAttachmentObject(String aid) throws Exception;

    /**
     * 根据文件名获得一个附件信息
     *
     * @param fileName 如 adfl3489sllk.zip
     */
    Attachment getAttachmentByFileName(String fileName);

}
