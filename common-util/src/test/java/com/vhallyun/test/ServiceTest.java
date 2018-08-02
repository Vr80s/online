package com.vhallyun.test;

import org.junit.Test;

import com.xczhihui.common.util.bean.DictionaryVo;
import com.xczhihui.common.util.vhallyun.*;

public class ServiceTest {

    @Test
    public void createAccessToken4Live() throws Exception {
        String accessToken4Live = BaseService.createAccessToken4Live("yuxin1", null, "ch_56f227c2");
        System.out.println(accessToken4Live);
    }

    @Test
    public void createRoom() throws Exception {
        String roomId = RoomService.create();
        System.out.println(roomId);
    }

    @Test
    public void createChannel() throws Exception {
        String channelId = ChannelService.create();
        System.out.println(channelId);
    }

    @Test
    public void createDocument() throws Exception {
        String documentId = DocumentService.create("https://img.qlchat.com/qlLive/temp/MY4A5V2F-8BJS-K3GL-1532933454769-25ZXTLEBJDYW.png");
        System.out.println(documentId);
    }

    @Test
    public void sent() throws Exception {
//        DictionaryVo dictionaryVo = new DictionaryVo("1","22","333");
//        ChatService.sentCustomBroadcast("ch_56f227c2",dictionaryVo.toString());
//        String str = "{\"body\":{\"content\":\"大家好今天我给大家讲讲针灸\",\"contentType\":1,\"courseId\":800,\"courseLiveAudioDiscussionVO\":null,\"discussionId\":null,\"id\":25,\"length\":null,\"likes\":0,\"pptImgId\":null,\"userId\":\"2c9aec345eba06eb015eba0820f80000\"},\"type\":1}";
        String str = "{\"body\":{\"content\":\"1大家好今天我给大家讲000讲针灸1\",\"contentType\":1,\"courseId\":800,\"courseLiveAudioDiscussionVO\":null,\"discussionId\":null,\"id\":56,\"length\":null,\"likes\":0,\"pptImgId\":null,\"userId\":\"2c9aec345eba06eb015eba0820f80000\"},\"type\":1}\n";
        ChatService.sentCustomBroadcast("ch_56f227c2",str);
    }
}
