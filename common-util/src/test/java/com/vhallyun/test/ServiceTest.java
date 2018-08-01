package com.vhallyun.test;

import org.junit.Test;

import com.xczhihui.common.util.bean.DictionaryVo;
import com.xczhihui.common.util.vhallyun.*;

public class ServiceTest {

    @Test
    public void createAccessToken4Live() throws Exception {
        String accessToken4Live = BaseService.createAccessToken4Live("yuxin2", null, "ch_56f227c2");
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
        DictionaryVo dictionaryVo = new DictionaryVo("1","22","333");
        ChatService.sentCustomBroadcast("ch_56f227c2",dictionaryVo.toString());
    }
}
