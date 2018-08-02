package com.xczhihui.course.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xczhihui.common.util.vhallyun.ChatService;
import com.xczhihui.course.service.ICourseLiveAudioContentService;
import com.xczhihui.course.vo.CourseLiveAudioContentVO;
import com.xczhihui.course.vo.CourseLiveAudioDiscussionVO;
import com.xczhihui.course.vo.CourseLiveAudioPPTVO;

import test.BaseJunit4Test;

public class CourseLiveAudioContentServiceImplTest extends BaseJunit4Test {

    @Autowired
    ICourseLiveAudioContentService courseLiveAudioContentService;

    @Test
    public void tsetList() throws Exception {
        CourseLiveAudioContentVO courseLiveAudioContent = new CourseLiveAudioContentVO();
        courseLiveAudioContent.setContent("你也说说哪里好？");
        courseLiveAudioContent.setContentType(2);
        courseLiveAudioContent.setCourseId(800);
        courseLiveAudioContent.setDiscussionId(7);
        courseLiveAudioContent.setUserId("96cf3b35965c4fd2941faaf74c7abefc");
        courseLiveAudioContentService.saveCourseLiveAudioContent(courseLiveAudioContent);
//        courseLiveAudioContent.setContent("https://media.qlchat.com/qlLive/audio/GLWCKVU9-37Z3-4JWH-1503578255088-8R4UGS2TRLX7.m4a");
//        courseLiveAudioContentService.saveCourseLiveAudioContent(courseLiveAudioContent);
//        courseLiveAudioContent.setContent("https://media.qlchat.com/qlLive/audio/L2JDON7S-14DP-9N31-1503577927313-DKZXM9WJ385V.m4a");
//        courseLiveAudioContentService.saveCourseLiveAudioContent(courseLiveAudioContent);
//        courseLiveAudioContent.setContent("https://media.qlchat.com/qlLive/audio/ZY4JFQB3-7TJS-NJP8-1503577915792-BIWNUNP95KH8.m4a");
//        courseLiveAudioContentService.saveCourseLiveAudioContent(courseLiveAudioContent);
    }

    @Test
    public void tsetList1() throws Exception {
        CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO = new CourseLiveAudioDiscussionVO();
        courseLiveAudioDiscussionVO.setCourseId(800);
        courseLiveAudioDiscussionVO.setContentType(1);
        courseLiveAudioDiscussionVO.setUserId("96cf3b35965c4fd2941faaf74c7abefc");
        courseLiveAudioDiscussionVO.setContent("讲得真好");
        courseLiveAudioContentService.saveCourseLiveAudioDiscussion(courseLiveAudioDiscussionVO);
//        courseLiveAudioDiscussionVO.setUserId("04af366ab0f54a969720452a076d88d2");
//        courseLiveAudioContentService.saveCourseLiveAudioDiscussion(courseLiveAudioDiscussionVO);
//        courseLiveAudioDiscussionVO.setUserId("06f8bc9055e74487afc3c3d221866266");
//        courseLiveAudioContentService.saveCourseLiveAudioDiscussion(courseLiveAudioDiscussionVO);
    }

    @Test
    public void tsetList2() throws Exception {
        courseLiveAudioContentService.deleteCourseLiveAudioContent("96cf3b35965c4fd2941faaf74c7abefc",14);
    }
    @Test
    public void tsetList3() throws Exception {
        courseLiveAudioContentService.saveCourseLiveAudioContentLike(11,"04af366ab0f54a969720452a076d88d2");
    }
    @Test
    public void tsetList4() throws Exception {
        CourseLiveAudioDiscussionVO courseLiveAudioDiscussionVO = new CourseLiveAudioDiscussionVO();
        courseLiveAudioDiscussionVO.setContentType(1);
        courseLiveAudioDiscussionVO.setCourseId(800);
        courseLiveAudioDiscussionVO.setContent("niubis老师好厉害66666666");
        courseLiveAudioDiscussionVO.setUserId("cd5bd6030d8b4105a8b8a7a7e0708605");
        courseLiveAudioContentService.saveCourseLiveAudioDiscussion(courseLiveAudioDiscussionVO);
    }

    @Test
    public void tsetList5() throws Exception {
        courseLiveAudioContentService.deleteCourseLiveAudioDiscussion("cd5bd6030d8b4105a8b8a7a7e0708605",14);
    }
    @Test
    public void tsetList6() throws Exception {
        courseLiveAudioContentService.saveCourseLiveAudioDiscussionLike(10,"cd5bd6030d8b4105a8b8a7a7e0708605");
    }

    @Test
    public void tsetList7() throws Exception {
        List<CourseLiveAudioPPTVO> ppts = new ArrayList<>();
        CourseLiveAudioPPTVO ppt = new CourseLiveAudioPPTVO();
        ppt.setImgUrl("img");
        ppt.setSort(1);
        ppts.add(ppt);
        ppts.add(ppt);
        courseLiveAudioContentService.saveCourseLiveAudioPPTs(800,"2222222",ppts);
    }

    @Test
    public void sent() throws Exception {
//        DictionaryVo dictionaryVo = new DictionaryVo("1","22","333");
//        ChatService.sentCustomBroadcast("ch_56f227c2",dictionaryVo.toString());
        String str = "{\"body\":{\"content\":\"大家好今天我给大家讲讲针灸\",\"contentType\":1,\"courseId\":800,\"courseLiveAudioDiscussionVO\":null,\"discussionId\":null,\"id\":25,\"length\":null,\"likes\":0,\"pptImgId\":null,\"userId\":\"2c9aec345eba06eb015eba0820f80000\"},\"type\":1}";
        CourseLiveAudioContentVO courseLiveAudioContentVO = new CourseLiveAudioContentVO();
        courseLiveAudioContentVO.setId(25);
        courseLiveAudioContentVO.setContent("大家好今天我给大家讲123讲针灸");
        courseLiveAudioContentVO.setContentType(1);
        courseLiveAudioContentVO.setCourseId(800);
        courseLiveAudioContentVO.setLikes(0);
        courseLiveAudioContentVO.setUserId("2c9aec345eba06eb015eba0820f80000");
        ChatService.sentCustomBroadcast("ch_56f227c2",courseLiveAudioContentVO.toJson());
    }

}