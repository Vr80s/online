package com.xczhihui.bxg.online.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.CourseService;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.course.service.ICriticizeService;
import com.xczhihui.online.api.vo.CriticizeVo;

@Controller
@RequestMapping(value = "/criticize")
public class CriticizeController extends AbstractController {

    @Autowired
    private ICriticizeService criticizeService;
    @Autowired
    private CourseService courseService;

    /**
     * 添加评论
     */
    @RequestMapping(value = "saveCriticize")
    @ResponseBody
    public ResponseObject saveCriticize(HttpServletRequest req,
                                        HttpServletResponse res, CriticizeVo criticize)
            throws Exception {
        //获取当前登录用户信息
        OnlineUser ou = getCurrentUser();
        if (ou == null) {
            return ResponseObject.newSuccessResponseObject("登录失效");
        }
        criticizeService.saveCriticize(ou.getId(), criticize.getUserId(), criticize.getCourseId(), criticize.getContent()
                , criticize.getOverallLevel(), criticize.getDeductiveLevel(), criticize.getContentLevel(), criticize.getCriticizeLable());
        return ResponseObject.newSuccessResponseObject("评论成功");
    }

    /**
     * 得到此视频下的所有评论
     */
    @RequestMapping("getCriticizeList")
    @ResponseBody
    public ResponseObject getCriticizeList(HttpServletRequest req,
                                           HttpServletResponse res, @RequestParam(required = false) String userId,
                                           @RequestParam(required = false) Integer courseId,
                                           @RequestParam(required = false) Integer pageSize,
                                           @RequestParam(required = false) Integer pageNumber
    ) throws Exception {

        //获取当前登录用户信息
        OnlineUser user = getCurrentUser();
        Map<String, Object> map = null;
        if (courseId != null) {
            map = criticizeService.getCourseCriticizes(new Page<>(pageNumber, pageSize), courseId, user != null ? user.getId() : null);
        } else {
            map = criticizeService.getAnchorCriticizes(new Page<>(pageNumber, pageSize), userId, user != null ? user.getId() : null);
        }

        return ResponseObject.newSuccessResponseObject(map);

    }

    /**
     * 点赞、取消点赞
     *
     * @param request
     * @param criticizeId
     * @return
     */
    @RequestMapping("updatePraise")
    @ResponseBody
    public ResponseObject updatePraise(HttpServletRequest request,
                                       @RequestParam("criticizeId") String criticizeId,
                                       @RequestParam("praise") Boolean praise) {
        //获取当前登录用户信息
        OnlineUser user = getCurrentUser();
        if (user != null) {
            Map<String, Object> returnMap = criticizeService.updatePraise(praise, criticizeId, user.getId());
            return ResponseObject.newSuccessResponseObject(returnMap);
        } else {
            return ResponseObject.newErrorResponseObject("用户未登录！");
        }
    }

    /**
     * Description：增加回复
     *
     * @param request
     * @param content
     * @param criticizeId
     * @return ResponseObject
     * @throws UnsupportedEncodingException
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("saveReply")
    @ResponseBody
    public ResponseObject saveReply(HttpServletRequest request,
                                    @RequestParam("content") String content,
                                    @RequestParam("criticizeId") String criticizeId,
                                    @RequestParam(required = false) Integer collectionId) throws UnsupportedEncodingException {
        //获取当前登录用户信息
        OnlineUser user = getCurrentUser();
        if (user != null) {
            criticizeService.saveReply(user.getId(), content, criticizeId);
            return ResponseObject.newSuccessResponseObject("回复成功！");
        } else {
            return ResponseObject.newErrorResponseObject("用户未登录！");
        }
    }
}
