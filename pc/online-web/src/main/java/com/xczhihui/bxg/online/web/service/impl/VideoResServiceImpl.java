package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.support.config.OnlineConfig;
import com.xczhihui.bxg.online.common.utils.cc.util.Md5Encrypt;
import com.xczhihui.bxg.online.web.service.VideoResService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service("videoResService")
public class VideoResServiceImpl implements VideoResService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void uploadSuccessCallback(String duration,String image,String status,String videoid,String time,String hash) {
		if ("OK".equals(status)) {

			String url = String.format("duration=%s&image=%s&status=%s&videoid=%s&time=%s&salt=%s",
					duration,image,status,videoid,time, OnlineConfig.CC_API_KEY);

			String md5 = Md5Encrypt.md5(url);
			if (!md5.equals(hash)) {
				logger.error(videoid+"|uploadSuccessCallback接口验证失败！");
			}
			if (System.currentTimeMillis() / 1000 - Long.valueOf(time) > 5) {
				logger.error(videoid+"|uploadSuccessCallback接口恶意调用！");
			}

			String ms = "00:00";
			int duration_s = StringUtils.hasText(duration) ? Integer.valueOf(duration) : 0;
			if (duration_s > 0) {
				String m = String.valueOf((duration_s / 60));
				String s = String.valueOf((duration_s % 60));
				m = m.length()==1 ? "0"+m : m;
				s = s.length()==1 ? "0"+s : s;
				ms = m+":"+s;
			}
//			videoResDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(
//					"update oe_video set video_time=? where video_id=? ",ms,videoid);

			logger.info(videoid+"|片长更新成功|"+ms);
		} else {
			logger.error(videoid+"|视频处理失败！");
		}
	}
}
