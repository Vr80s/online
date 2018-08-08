package com.xczhihui.bxg.online.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
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
}
