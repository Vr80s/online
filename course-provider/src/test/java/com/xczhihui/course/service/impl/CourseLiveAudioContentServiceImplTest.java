package com.xczhihui.course.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xczhihui.course.service.ICourseLiveAudioContentService;
import com.xczhihui.course.vo.CourseLiveAudioContentVO;

import test.BaseJunit4Test;

public class CourseLiveAudioContentServiceImplTest extends BaseJunit4Test {

    @Autowired
    ICourseLiveAudioContentService courseLiveAudioContentService;
    @Test
    public void tsetList() {
        CourseLiveAudioContentVO courseLiveAudioContent = new CourseLiveAudioContentVO();
        courseLiveAudioContent.setContent("77777777");
        courseLiveAudioContent.setContentType(1);
        courseLiveAudioContent.setCourseId(1);
        courseLiveAudioContent.setDiscussionId(11);
        courseLiveAudioContent.setUserId("000000000000000000000");
        courseLiveAudioContent.setDiscussionId(77777777);
        courseLiveAudioContentService.saveCourseLiveAudioContent(courseLiveAudioContent);
    }
}