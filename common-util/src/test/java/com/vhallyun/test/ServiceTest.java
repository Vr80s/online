package com.vhallyun.test;

import org.junit.Test;

import com.xczhihui.common.util.vhallyun.BaseService;
import com.xczhihui.common.util.vhallyun.ChannelService;
import com.xczhihui.common.util.vhallyun.DocumentService;
import com.xczhihui.common.util.vhallyun.RoomService;

public class ServiceTest {

    @Test
    public void createAccessToken4Live() throws Exception {
        String accessToken4Live = BaseService.createAccessToken4Live("yrx", null, null);
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
}
