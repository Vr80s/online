package com.xczhihui.common.support.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jayway.jsonpath.internal.Path;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.streaming.StreamingManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.support.domain.Attachment;
import com.xczhihui.common.support.service.AttachmentCenterService;
import com.xczhihui.common.support.service.AttachmentType;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.common.util.HttpUtil;
import com.xczhihui.common.util.JsonUtil;
import com.xczhihui.common.util.enums.AttachmentBusinessType;

/**
 * 附件上传统一处理
 */
@Service("attachmentCenterService")
public class AttachmentCenterServiceImpl implements AttachmentCenterService {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentCenterServiceImpl.class);

    private static final String DATE_FORMAT = "yyMddHHmmss";
    @Value("${qiniu.bucket}")
    private String bucket;
    @Value("${qiniu.prefix}")
    private String prefix;
    @Autowired
    private Auth auth;
    @Autowired
    @Qualifier("simpleHibernateDao")
    private SimpleHibernateDao simpleDao;
    @Autowired
    private UploadManager uploadManager;

    /**
     * 上传图片与附件
     *
     * @param createUserId 上传者ID
     * @param projectName
     * @param fileName     文件名
     * @param contentType  文件类型
     * @param fileData     文件二进制数据
     * @param fileType
     * @return
     * @throws Exception
     */
    @Override
    public String upload(String createUserId, String projectName, String fileName, String contentType, byte[] fileData, String fileType) throws Exception {
        Attachment attachment;
        //附件类型保持原文件名
        if (AttachmentBusinessType.ATTACHMENT.getValue().equals(fileType)) {
            attachment = addAttachmentRecord(createUserId, AttachmentType.valueOf(projectName.toUpperCase()),
                    CodeUtil.generateRandomString(6) + "-" + fileName, fileData, fileName);
        } else if (AttachmentBusinessType.PICTURE.getValue().equals(fileType)) {
            attachment = addAttachmentRecord(createUserId, AttachmentType.valueOf(projectName.toUpperCase()),
                    CodeUtil.generateRandomString(8) + "." + StringUtils.getFilenameExtension(fileName), fileData, fileName);
        } else {
            attachment = new Attachment(1, "请传入fileType参数，1图片，2附件");
        }
        return JsonUtil.getBaseGson().toJson(attachment);
    }
    
    
    
    

    /**
     * 上传附件
     *
     * @param createUserId 创建人的ID
     * @param type         区分个业务:squad,course等，在附件目录里有体现。
     * @param fileName
     * @param fileData
     * @param contentType  文件内容类型
     * @return
     */
    @Override
    public Attachment addAttachment(String createUserId, AttachmentType type, String fileName, byte[] fileData, String contentType) {
        try {
            return addAttachmentRecord(createUserId, type, CodeUtil.generateRandomString(6) + "-" + fileName, fileData, fileName);
        } catch (Exception e) {
            return new Attachment(1, "上传附件失败");
        }
    }

    @Override
    public Attachment addAttachmentRecord(String createUserId, AttachmentType type, String fileName, byte[] fileData, String originName) {
        //加前缀避免重复链接覆盖
        String filePath = DateFormatUtils.format(new Date(), DATE_FORMAT);
        Attachment attachment = new Attachment();
        try {
            String url = uploadQiniu(fileData, filePath + "/" + fileName);
            if (StringUtils.isEmpty(url)) {
                return new Attachment(1, "文件上传到七牛失败");
            }
            attachment.setFileName(fileName);
            attachment.setFileSize(fileData.length);
            attachment.setBusinessType(type.getType());
            attachment.setCreateTime(new Date());
            attachment.setCreatePerson(createUserId);
            attachment.setFilePath(filePath);
            attachment.setUrl(prefix + attachment.getFilePath() + "/" + fileName);
            attachment.setFileType(StringUtils.getFilenameExtension(fileName));
            attachment.setOrgFileName(originName);
            simpleDao.save(attachment);
        } catch (Exception e) {
            logger.error("upload file to qiniu fail。{}", e.getLocalizedMessage());
            return new Attachment(1, "上传文件失败");
        }
        return attachment;
    }

    
    
    
    
    /**
     * 上传文件到七牛，返回url
     *
     * @param fileData 上传的附件数据
     * @param fileName 文件名
     * @return url
     * @throws IOException
     */
    private String uploadQiniu(byte[] fileData, String fileName) throws IOException {
        Response response = uploadManager.put(fileData, fileName, getUploadToken());
        StringMap result = response.jsonToMap();
        return (String) result.get("url");
    }

    /**
     * 获取上传token
     *
     * @return 七牛token
     */
    private String getUploadToken() {
        return auth.uploadToken(bucket, null, 3600, new StringMap()
                .put("returnBody", "{\"url\": $(key), \"w\": $(imageInfo.width), \"h\": $(imageInfo.height)}"));
    }

    @Override
    public String getAttachmentJson(String aid) throws Exception {
        Attachment attachment = simpleDao.get(aid, Attachment.class);
        if (attachment == null) {
            attachment = new Attachment(1, "附件找不到");
        }
        return JsonUtil.getBaseGson().toJson(attachment);
    }

    /**
     * 下载附件 //TODO 此处保留为了兼容老版本调用，之后可直接走七牛的链接下载
     *
     * @param aid 附件id
     * @return
     * @throws Exception
     */
    @Override
    public byte[] getAttachmentData(String aid) throws Exception {
        Attachment attachment = simpleDao.get(aid, Attachment.class);
        if (attachment == null) {
            throw new NullPointerException("附件找不到或url为空");
        }
        return HttpUtil.downloadFile(attachment.getFilePath() + "/" + attachment.getFileName());
    }

    @Override
    public Attachment getAttachmentObject(String aid) throws Exception {
        Attachment attachment = simpleDao.get(aid, Attachment.class);
        return attachment == null ? new Attachment(1, "附件找不到") : attachment;
    }

    @Override
    public Attachment getAttachmentByFileName(String fileName) {
        Attachment attachment = simpleDao.findOneEntitiyByProperty(Attachment.class, "fileName", fileName);
        return attachment == null ? new Attachment(1, "附件找不到") : attachment;
    }
    
    /**
     * 使用前需做路径修改
     * main方法上传图片使用、批量上传图片使用
     * @param urlList
     * @param fileName
     */
    private static void downloadPicture(String urlList,String fileName) {
        //TODO
        
        
        URL url = null;
        try {
            // 网络图片下载并上传使用
            //url = new URL(urlList);
            //DataInputStream dataInputStream = new DataInputStream(url.openStream());

            //本地图片上传
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(urlList));
            
            
            String imageName =  "C:\\Users\\yangxuan\\Desktop\\VhallImg\\"+fileName;
            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
            
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            byte[] context=output.toByteArray();
            
            //写文件到本地 
            fileOutputStream.write(context);
            
            
            /**
             * 得到token
             */
            Auth autn =  Auth.create("Mc0SU4FEXmVBM33XZSxdSP2W496ntL9kDMjy3Dwi", "kpbCbnuuKFw3vWLbF5DhavDy08Jvsmyd83hgBZ9B");
            String token = autn.uploadToken("ipandatcm", null, 3600, new StringMap()
                    .put("returnBody", "{\"url\": $(key), \"w\": $(imageInfo.width), \"h\": $(imageInfo.height)}"));
            
            
            /**
             * 上传服务器
             */
            com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(Zone.zone2());
            UploadManager um  =  new UploadManager(cfg);
            
            
            //上传到七牛服务器上
            Response response = um.put(context, fileName, token);
            StringMap result = response.jsonToMap();
            System.out.println("七牛云图片地址："+(String) result.get("url"));
            
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
