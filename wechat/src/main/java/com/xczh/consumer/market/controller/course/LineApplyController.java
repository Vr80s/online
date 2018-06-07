package com.xczh.consumer.market.controller.course;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.body.course.LineApplyBody;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.course.service.ILineApplyService;

@Controller
@RequestMapping("/xczh/apply")
public class LineApplyController {

    @Autowired
    private ILineApplyService lineApplyService;

    @Autowired
    private OnlineUserService onlineUserService;

    /**
     * 增加线下课报名记录
     *
     * @param lineApplyBody
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject add(@Account String accountId,
                              LineApplyBody lineApplyBody) {
        lineApplyService.saveOrUpdate(accountId, lineApplyBody.build(accountId));
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    /**
     * 通过用户id获取
     *
     * @param accountId
     * @return
     */
    @RequestMapping(value = "applyInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseObject applyInfo(@Account String accountId, @RequestParam Integer courseId) {
        Map<String, Object> result = new HashMap<>(2);
        result.put("submitted", lineApplyService.submitted(accountId, courseId));
        result.put("applyInfo", lineApplyService.findLineApplyByUserId(accountId));
        return ResponseObject.newSuccessResponseObject(result);
    }
}
