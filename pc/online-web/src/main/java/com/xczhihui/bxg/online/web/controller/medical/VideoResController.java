package com.xczhihui.bxg.online.web.controller.medical;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.common.support.config.OnlineConfig;
import com.xczhihui.bxg.online.common.utils.cc.config.Config;
import com.xczhihui.bxg.online.common.utils.cc.util.APIServiceFunction;
import com.xczhihui.bxg.online.web.base.utils.TimeUtil;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.bxg.online.web.service.VideoResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: UserCoin.java <br>
 * Description:用户-代币余额表 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 下午 5:06 2018/1/20 0020<br>
 */
@RestController
@RequestMapping("videoRes")
public class VideoResController extends AbstractController{

    @Autowired
    private VideoResService videoResService;
    private static String categoryid = "7C85F5F633435474";

    /**
     * 获得上传地址
     * @param req
     * @return
     */
    @RequestMapping(value = "getUploadUrl", method = RequestMethod.GET)
    public String getUploadUrl(HttpServletRequest request,String title) {
        OnlineUser loginUser = getOnlineUser(request);
        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("userid", OnlineConfig.CC_USER_ID);
        paramsMap.put("title", title);
        paramsMap.put("description", TimeUtil.getCCtitleTimeStr());
        paramsMap.put("tag", loginUser.getLoginName());
        paramsMap.put("categoryid", categoryid);
        long time = System.currentTimeMillis();
        String url = Config.api_updateVideo + "?" + APIServiceFunction.createHashedQueryString(paramsMap, time, OnlineConfig.CC_API_KEY);
        url += "&categoryid="+categoryid;
        return url;
    }

    /**
     * 视频处理完成的回调
     *
     * @param videoid
     * @param status
     * @param duration
     * @param image
     * @throws IOException
     */
    @RequestMapping(value = "uploadSuccessCallback", method = RequestMethod.GET)
    public void uploadSuccessCallback(HttpServletResponse res, String duration, String image, String status,
                                      String videoid, String time, String hash) throws IOException {
        videoResService.uploadSuccessCallback(duration, image, status, videoid, time, hash);
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/xml; charset=utf-8");
        res.getWriter().write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><video>OK</video>");
    }


}
