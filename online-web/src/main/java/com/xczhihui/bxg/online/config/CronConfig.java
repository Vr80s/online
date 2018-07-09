package com.xczhihui.bxg.online.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBannerService;

/**
 * @author hejiwei
 */
@Configuration
@EnableScheduling
public class CronConfig {

    private static final Logger logger = LoggerFactory.getLogger(CronConfig.class);

    @Autowired
    private IMedicalDoctorBannerService medicalDoctorBannerService;
    @Autowired
    private ICommonMessageService commonMessageService;

    @Scheduled(fixedRate = 60000)
    private void updateStatusCronJob() {
        Integer updateCnt = medicalDoctorBannerService.updateAllUnShelves();
    }
//
//    @Scheduled(fixedRate = 300000)
//    private void addCronMessage() {
//        String userId1 = "fdc224fcf57c42cba7b22d5ba6da707c";
//        String userId2 = "d44983e894b34064bc8672e7117f743d";
//        commonMessageService.saveMessage(new BaseMessage.Builder(MessageTypeEnum.SYSYTEM.getVal())
//                .buildAppPush("定时消息推送~~~")
//                .buildWeb("定时消息推送~~~").build(userId1, RouteTypeEnum.NONE, null));
//        commonMessageService.saveMessage(new BaseMessage.Builder(MessageTypeEnum.SYSYTEM.getVal())
//                .buildAppPush("定时消息推送~~~")
//                .buildWeb("定时消息推送~~~").build(userId2, RouteTypeEnum.NONE, null));
//    }
}
