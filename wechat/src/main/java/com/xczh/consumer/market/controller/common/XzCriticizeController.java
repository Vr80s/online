package com.xczh.consumer.market.controller.common;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.service.ICriticizeService;
import com.xczhihui.online.api.vo.CriticizeVo;

@Controller
@RequestMapping(value = "/xczh/criticize")
public class XzCriticizeController {

    @Autowired
    private ICriticizeService criticizeService;

    /**
     * 添加评论
     */
    @RequestMapping(value = "saveCriticize")
    @ResponseBody
    public ResponseObject saveCriticize(HttpServletRequest req,
                                        HttpServletResponse res, CriticizeVo criticize, @Account String accountId)
            throws Exception {
        criticizeService.saveCriticize(accountId, criticize.getUserId(), criticize.getCourseId(), criticize.getContent()
                , criticize.getOverallLevel(), criticize.getDeductiveLevel(), criticize.getContentLevel(),
                criticize.getCriticizeLable());
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
                                           @RequestParam(required = false) Integer pageNumber,
                                           @Account(optional = true) Optional<String> accountIdOpt
    ) throws Exception {

        Map<String, Object> map = null;

        if (courseId != null) {
            map = criticizeService.getCourseCriticizes(new Page<>(pageNumber, pageSize), courseId, accountIdOpt.orElse(null));
//			List<Integer> list  = criticizeService.selectMobileCourseCommentMeanCount(courseId);
//			map.put("labelCountList", list);
        } else {


            map = criticizeService.getAnchorCriticizes(new Page<>(pageNumber, pageSize), userId, accountIdOpt.orElse(null));
//			List<Integer> list  =  criticizeService.selectMobileUserCommentMeanCount(userId);
//			map.put("labelCountList", list);
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
                                       @RequestParam("praise") Boolean praise, @Account String accountId) {
        Map<String, Object> returnMap = criticizeService.updatePraise(praise, criticizeId, accountId);
        return ResponseObject.newSuccessResponseObject(returnMap);
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
                                    @RequestParam(required = false) Integer collectionId, @Account String accountId) throws UnsupportedEncodingException {
        criticizeService.saveReply(accountId, content, criticizeId);
        return ResponseObject.newSuccessResponseObject("回复成功！");
    }
}
