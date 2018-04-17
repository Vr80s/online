package com.xczhihui.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.support.shiro.ManagerUserUtil;

/**
 * @author hejiwei
 */
public class ImageUtils {
    private static final Pattern IMG_PATTERN = Pattern.compile("<img\\s+(?:[^>]*)src\\s*=\\s*([^>]+)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    public static String base64ToimageURL(String content, AttachmentCenterService attachmentCenterService) {
        Matcher matcher = IMG_PATTERN.matcher(content);
        while (matcher.find()) {
            String group = matcher.group(1);
            if (StringUtils.hasText(group)) {
                group = group.replaceAll("\"", "");
                // 存在base64编码的数据，才进行64Toimage转换以及上传
                if (group.split("base64,").length > 1) {
                    String str = group.split("base64,")[1];
                    byte[] b = org.apache.commons.codec.binary.Base64
                            .decodeBase64(str);
                    Attachment a = attachmentCenterService.addAttachment(ManagerUserUtil.getId(),
                            AttachmentType.ONLINE, "1.png", b, "image/png");
                    content = content.replace(group, a.getUrl());
                }

            }
        }
        return content;
    }
}
