package com.xczh.consumer.market.controller.common;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;

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
    public ResponseObject saveCriticize(CriticizeVo criticize, @Account String accountId)
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
    public ResponseObject getCriticizeList(@RequestParam(required = false) String userId,
                                           @RequestParam(required = false) Integer courseId,
                                           @RequestParam(required = false) Integer pageSize,
                                           @RequestParam(required = false) Integer pageNumber,
                                           @Account(optional = true) Optional<String> accountIdOpt
    ) throws Exception {

        Map<String, Object> map;
        if (courseId != null) {
            map = criticizeService.getCourseCriticizes(new Page<>(pageNumber, pageSize), courseId, accountIdOpt.orElse(null));
        } else {
            map = criticizeService.getAnchorCriticizes(new Page<>(pageNumber, pageSize), userId, accountIdOpt.orElse(null));
        }
        return ResponseObject.newSuccessResponseObject(map);

    }

    /**
     * 点赞、取消点赞
     *
     * @param criticizeId
     * @return
     */
    @RequestMapping("updatePraise")
    @ResponseBody
    public ResponseObject updatePraise(@RequestParam("criticizeId") String criticizeId,
                                       @RequestParam("praise") Boolean praise, @Account String accountId) {
        Map<String, Object> returnMap = criticizeService.updatePraise(praise, criticizeId, accountId);
        return ResponseObject.newSuccessResponseObject(returnMap);
    }

    /**
     * Description：增加回复
     *
     * @param content
     * @param criticizeId
     * @return ResponseObject
     * @throws UnsupportedEncodingException
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping("saveReply")
    @ResponseBody
    public ResponseObject saveReply(@RequestParam("content") String content,
                                    @RequestParam("criticizeId") String criticizeId, @Account String accountId) throws UnsupportedEncodingException {
        criticizeService.saveReply(accountId, content, criticizeId);
        return ResponseObject.newSuccessResponseObject("回复成功！");
    }
}
