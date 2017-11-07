package com.xczhihui.bxg.online.manager.user.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.ImageUtil;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.manager.user.dao.UserDao;
import com.xczhihui.bxg.online.manager.user.service.PersonService;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.UserStatus;
import com.xczhihui.user.center.bean.UserType;
import com.xczhihui.user.center.utils.CodeUtil;

@Service("personService")
public class PersonServiceImpl implements PersonService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private UserDao userDao;

	@Autowired
	private UserCenterAPI userCenterAPI;

	private AttachmentCenterService attachmentCenterService;

	private static int DEFAULT_WIDTH = 45;
	private static int DEFAULT_HEIGHT = 45;
	private static String DEFAULT_FORMAT = "png";

	@Override
	public void updatePersonInfo(User oldUser, User newUser) {
		oldUser.setName(newUser.getName());
		oldUser.setMobile(newUser.getMobile());
		oldUser.setEmail(newUser.getEmail());
		oldUser.setQq(newUser.getQq());
		this.userDao.update(oldUser);
		this.userCenterAPI.update(oldUser.getLoginName(), 
				oldUser.getName(), 
				oldUser.getSex(), 
				oldUser.getEmail(), 
				oldUser.getMobile(), 
				UserType.TEACHER.getValue(),UserStatus.NORMAL.getValue());
	}

	@Override
	public void updateHeadPhoto(User user, byte[] image) {
		Set<String> oldHeadPhotos = new HashSet<>();
		oldHeadPhotos.add(user.getBigHeadPhoto());
		oldHeadPhotos.add(user.getSmallHeadPhoto());

		String fileName = user.getId() + "_big.png";
		byte[] fileData = image;
		String ext = StringUtils.getFilenameExtension(fileName);
		Attachment att = this.attachmentCenterService.addAttachment(
		        user.getId(), AttachmentType.HEADPHOTO, fileName, fileData,
		        ext, null);
		// user.setBigHeadPhoto(att.getFilePath() + "/" + att.getFileName());
		user.setBigHeadPhoto(att.getId());

		try {
			try (InputStream imageInputStream = new ByteArrayInputStream(image);
			        ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream()) {
				String format = DEFAULT_FORMAT;
				int width = DEFAULT_WIDTH;
				int height = DEFAULT_HEIGHT;

				ImageUtil.scaleImage(imageInputStream, imageOutputStream,
				        format, width, height);
				fileName = user.getId() + "_small.png";
				fileData = imageOutputStream.toByteArray();
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		att = this.attachmentCenterService.addAttachment(
		        user.getId(), AttachmentType.HEADPHOTO, fileName, fileData,
		        ext, null);
		// user.setSmallHeadPhoto(att.getFilePath() + "/" + att.getFileName());

		user.setSmallHeadPhoto(att.getId());
		this.userDao.update(user);
		this.deteleOldHeadPhoto(oldHeadPhotos);
	}

	private void deteleOldHeadPhoto(Set<String> oldHeadPhotos) {
		logger.info("oldHeadPhotos:{}", oldHeadPhotos);
		if (oldHeadPhotos != null) {
			oldHeadPhotos.forEach(file -> {
				if (StringUtils.hasText(file)) {
					file = file.trim();
					int i = file.lastIndexOf('/');
					if (i > 0) {
						file = file.substring(i + 1);
					}
					logger.info("file:{}", file);
					// this.attachmentCenterService.deleteAttachment(file,
					// AttachmentType.HEADPHOTO);

					this.attachmentCenterService.deleteAttachment(file);
				}
			});
		}
	}

	@Override
	public void updatePassword(User user, String newpassword, String oldpassword) {
		try {
			this.userCenterAPI.login(user.getLoginName(), oldpassword);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new IllegalArgumentException("原密码不正确");
		}
		this.userCenterAPI.updatePassword(user.getLoginName(),null,newpassword);
		newpassword = CodeUtil.encodePassword(newpassword, user.getLoginName());
		user.setPassword(newpassword);
		this.userDao.update(user);
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setAttachmentCenterService(
	        AttachmentCenterService attachmentCenterService) {
		this.attachmentCenterService = attachmentCenterService;
	}

}
