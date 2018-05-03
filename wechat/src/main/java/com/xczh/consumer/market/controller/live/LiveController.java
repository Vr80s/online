package com.xczh.consumer.market.controller.live;

//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.online.api.service.LiveExamineInfoService;
import com.xczhihui.online.api.vo.LiveExamineInfo;

//import java.util.UUID;

/**
 * 直播控制器
 * ClassName: LiveController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月10日<br>
 */
@Controller
@RequestMapping("/bxg/live")
public class LiveController {

    @Autowired
    private LiveExamineInfoService liveExamineInfoService;

    @Autowired
    private OLAttachmentCenterService service;


    @Value("${gift.im.room.postfix}")
    private String postfix;
    @Value("${gift.im.boshService}")
    private String boshService;//im服务地址
    @Value("${gift.im.host}")
    private String host;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LiveController.class);

    /**
     * Description： 直播搜索接口
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("listKeywordQuery")
    @ResponseBody
    public ResponseObject listKeywordQuery(HttpServletRequest req,
                                           HttpServletResponse res, Map<String, String> params)
            throws Exception {

    	LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * Description： 直播列表页
     *
     * @param req
     * @param res
     * @param params
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("list")
    @ResponseBody
    public ResponseObject list(HttpServletRequest req,
                               HttpServletResponse res, Map<String, String> params)
            throws Exception {

    	LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    /**
     * Description：直播详情页面
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("liveDetails")
    @ResponseBody
    public ResponseObject liveDetails(HttpServletRequest req,
                                      HttpServletResponse res)
            throws Exception {

    	LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping("addLive")
    @ResponseBody
    public ResponseObject addLive(HttpServletRequest req,
                                  HttpServletResponse res, LiveExamineInfo liveExamineInfo, @RequestParam("file") MultipartFile file)
            throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        String projectName = "other";
        String fileType = "1"; //图片类型了
        String headImgPath = service.upload(null, //用户中心的用户ID
                projectName, file.getOriginalFilename(), file.getContentType(), file.getBytes(), fileType, null);

        //JSONObject json = JSONObject.parseObject(headImgPath);
        LOGGER.info("文件路径——path:" + headImgPath);
        //map.put("logo", json.get("url").toString());

        LOGGER.info("req.getParameterprice================" + req.getParameter("price"));
        if ("1".equals(liveExamineInfo.getSeeMode())) {//付费
            liveExamineInfo.setPrice(new BigDecimal(req.getParameter("price")));
        }
        if ("2".equals(liveExamineInfo.getSeeMode())) {//密码
            liveExamineInfo.setPassword(req.getParameter("password"));
        }

        liveExamineInfo.setLogo(headImgPath);
        String id = liveExamineInfoService.add(liveExamineInfo);
        Map<String, Object> result = new HashMap<>();
        result.put("examineId", id);
        return ResponseObject.newSuccessResponseObject(result);
    }

    @RequestMapping("myList")
    @ResponseBody
    public ResponseObject examineList(HttpServletRequest req,
                                      HttpServletResponse res)
            throws Exception {

        int pageNumber = 0;
        if (null != req.getParameter("pageNumber")) {
            pageNumber = Integer.valueOf(req.getParameter("pageNumber"));
        }
        int pageSize = 10;
        if (null != req.getParameter("pageSize")) {
            pageSize = Integer.valueOf(req.getParameter("pageSize"));
        }
        return ResponseObject.newSuccessResponseObject(liveExamineInfoService.liseByExamineStatus(req.getParameter("userId"), req.getParameter("type"), pageNumber, pageSize));
    }

    /**
     * 审核详情页
     *
     * @param req
     * @param res
     * @param examineId
     * @return
     * @throws Exception
     */
    @RequestMapping("examineDetails")
    @ResponseBody
    public ResponseObject examineDetails(HttpServletRequest req,
                                         HttpServletResponse res, String examineId)
            throws Exception {
        return ResponseObject.newSuccessResponseObject(liveExamineInfoService.examineDetails(examineId));
    }

    /**
     * 申诉
     *
     * @param req
     * @param res
     * @param examineId
     * @param content
     * @return
     * @throws Exception
     */
    @Transactional
    @RequestMapping("appeal")
    @ResponseBody
    public ResponseObject appeal(HttpServletRequest req,
                                 HttpServletResponse res, String examineId, String content)
            throws Exception {
        liveExamineInfoService.appeal(examineId, content);
        Map<String, Object> result = new HashMap<>();
        result.put("examineId", examineId);
        return ResponseObject.newSuccessResponseObject(result);
    }


    /**
     * 取消审核
     * Description：
     *
     * @param examineId
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("/cancelAudit")
    @ResponseBody
    public ResponseObject cancelAudit(HttpServletRequest req,
                                      HttpServletResponse res, String examineId) {
        try {
            liveExamineInfoService.cancelAudit(examineId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("网络异常,请稍等");
        }
        return ResponseObject.newSuccessResponseObject("取消成功");
    }


    @RequestMapping("selectCurrentLive")
    @ResponseBody
    public ResponseObject selectCurrentLive(HttpServletRequest req,
                                            HttpServletResponse res, Map<String, String> params)
            throws Exception {
        return ResponseObject.newSuccessResponseObject(liveExamineInfoService.liseByExamineStatus(req.getParameter("userId"), "2", 1, 10).get(0));
    }

    public static Object convertMap(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象
        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                try {
                    Object value = map.get(propertyName);
                    Object[] args = new Object[1];
                    args[0] = value;
                    descriptor.getWriteMethod().invoke(obj, args);
                } catch (Exception e) {
                }
            }
        }
        return obj;
    }

    @RequestMapping("/getMoneySum")
    @ResponseBody
    public ResponseObject getLiveMoneySum(String id) {
    	LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
    }

    @RequestMapping("/getPreLiveCount")
    @ResponseBody
    public ResponseObject getPreLiveCount(String userId) {
        if (userId == null) {
            return ResponseObject.newSuccessResponseObject(0);
        }
        return ResponseObject.newSuccessResponseObject(liveExamineInfoService.getPreLiveCount(userId));
    }

    @RequestMapping("/liveIsAvailable")
    @ResponseBody
    public ResponseObject liveIsAvailable(Integer id) {
    	LOGGER.info("老版本方法----》》》》");
	   	return ResponseObject.newErrorResponseObject("请使用最新版本");
    }
}
