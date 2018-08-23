package com.xczhihui.bxg.online.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.util.enums.LiveStatus;
import com.xczhihui.common.util.enums.LiveStatusEvent;
import com.xczhihui.common.util.redis.key.RedisCacheKey;
import com.xczhihui.common.util.vhallyun.RoomService;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.medical.doctor.model.Treatment;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBannerService;
import com.xczhihui.medical.doctor.service.IRemoteTreatmentService;

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
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IRemoteTreatmentService remoteTreatmentService;
    @Autowired
    private CacheService cacheService;

    @Scheduled(fixedRate = 60000)
    private void updateStatusCronJob() {
        Integer updateCnt = medicalDoctorBannerService.updateAllUnShelves();
    }

    @Scheduled(cron = "0 0 2 * * ?")
    private void updateLiveCourseStatus() {
        List<String> roomIds = RoomService.listLiveOpening();
        for (String roomId : roomIds) {
            Integer liveStatus = courseService.findLiveStatusByDirectId(roomId);
            if (liveStatus != null && liveStatus == LiveStatus.LIVEING.getId()) {
                courseService.updateCourseLiveStatus(LiveStatusEvent.STOP.getName(), roomId, null);
            }
        }
    }

    @Scheduled(fixedRate = 60000)
    private void updateTreatmentStatus() {
        List<Treatment> treatments = remoteTreatmentService.selectUpcomingExpire();
        for (Treatment treatment : treatments) {
            if (remoteTreatmentService.updateExpired(treatment)) {
                Integer courseId = treatment.getCourseId();
                if (courseId != null) {
                    courseService.updateStatus(courseId, 0);
                }
            }
        }
    }
}
