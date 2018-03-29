package com.xczhihui.bxg.online.manager.cloudClass.util;

import java.util.Date;
import java.util.TimerTask;

import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.online.manager.vhall.VhallUtil;

public class Task extends TimerTask{
	
	
	private String videoId;
	
	
	public Task(String videoId){
		this.videoId = videoId;
	}
	
	public void run() {
    
		 System.out.println("}{}{}{}{}{}"+DateUtil.formatDate(new Date(),null));
		
		 System.out.println("{}{}{}{}{}{}  这个伟大的直播是否生成回访了   开始  ");

		
		 VhallUtil.recordList(videoId);
		
		 System.out.println("{}{}{}{}{}{}  这个伟大的直播是否生成回访了  结束  ");
	}




}