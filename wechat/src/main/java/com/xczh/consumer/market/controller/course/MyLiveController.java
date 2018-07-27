package com.xczh.consumer.market.controller.course;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.utils.Broadcast;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.ImInformLiveStatusType;
import com.xczhihui.common.util.enums.LiveInStatus;
import com.xczhihui.course.service.IFocusService;

/**
 * 点播控制器 ClassName: BunchPlanController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@Controller
@RequestMapping("/xczh/mylive")
public class MyLiveController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MyInfoController.class);
    
    @Autowired
    @Qualifier("focusServiceRemote")
    private IFocusService ifocusService;

    @Autowired
    private OnlineCourseService onlineCourseService;
    
    @Autowired
    private Broadcast broadcast;

    /**
     * Description：开始直播时调用，因为要区分这个直播来自app直播呢，还是pc端直播了。
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("appLivePre")
    @ResponseBody
    public ResponseObject appLivePre(@RequestParam("courseId") Integer courseId) {
        try {
            onlineCourseService.updateLiveSourceType(courseId);
            return ResponseObject.newSuccessResponseObject("操作成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("操作失败!");
        }
    }
    
    /**
     * 更改直播中类型
     * 
     * @param courseId
     * @return
     */
    @RequestMapping("updateLiveType")
    @ResponseBody
    public ResponseObject updateLiveType(@RequestParam("courseId")Integer courseId,
            @RequestParam("type")Integer type) {

        try {
            
            onlineCourseService.updateLiveType(courseId,type);
            /**
             * 发送IM消息
             */
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("senderInfo", "微吼系统--》manager --》web ");
            map.put("rewardInfo", "直播间" + courseId);
            map.put("sendTime", System.currentTimeMillis());  //发送时间
            map.put("rewardTotal", "0");
            
            //直播刚刚结束后，提示用户生产回放的时间    待定
            String messageBody = "";
            //退出直播并没有结束
            if(LiveInStatus.EXIT_LIVE.getCode().equals(type)) {
                map.put("messageType", ImInformLiveStatusType.LIVE_EXIT_NOEND.getCode());
                messageBody = JSONObject.toJSONString(map);
                
            //正常直播    
            }else if(LiveInStatus.NORMAL_LIVE.getCode().equals(type)) {
                map.put("messageType", ImInformLiveStatusType.GO_ON_NOEND.getCode());
                messageBody = JSONObject.toJSONString(map);
            }
            LOGGER.info("广播的参数--》" + map.toString());
            
            
            broadcast.loginAndSend(courseId+"", messageBody);
            
            return ResponseObject.newSuccessResponseObject("操作成功!");
        } catch (Exception e) {
            e.printStackTrace();
            
            return ResponseObject.newErrorResponseObject("操作失败!");
        }
    }
    
}
