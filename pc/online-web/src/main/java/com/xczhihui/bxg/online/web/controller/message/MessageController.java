package com.xczhihui.bxg.online.web.controller.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.bxg.online.web.controller.AbstractController;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.model.Message;
import com.xczhihui.course.service.ICommonMessageService;

/**
 * 消息
 *
 * @author hejiwei
 */
@RestController
@RequestMapping("message")
public class MessageController extends AbstractController {

    @Autowired
    private ICommonMessageService commonMessageService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseObject list(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size) {
        Page<Message> list = commonMessageService.list(page, size, getUserId());
        list.getRecords().forEach(message -> message.setUrl(MultiUrlHelper.getUrl(message.getRouteType(), MultiUrlHelper.URL_TYPE_WEB, message.getDetailId())));
        return ResponseObject.newSuccessResponseObject(list);
    }

    @RequestMapping(value = "{id}/readStatus", method = RequestMethod.PUT)
    public ResponseObject updateReadStatus(@PathVariable String id) {
        commonMessageService.updateReadStatus(id, getUserId());
        return ResponseObject.newSuccessResponseObject("操作成功");
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseObject markDelete(@PathVariable String id) {
        commonMessageService.deleteById(id, getUserId());
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseObject deleteAll() {
        commonMessageService.deleteById(null, getUserId());
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    @RequestMapping(value = "count", method = RequestMethod.GET)
    public ResponseObject count() {
        return ResponseObject.newSuccessResponseObject(commonMessageService.countUnReadCntByUserId(getUserId()));
    }

    @RequestMapping(value = "notice", method = RequestMethod.GET)
    public ResponseObject listNotice() {
        return ResponseObject.newSuccessResponseObject(commonMessageService.getNewestNotice());
    }

    /**
     * 用于处理老的消息数据
     *
     * @return
     */
    @RequestMapping(value = "clean", method = RequestMethod.POST)
    public ResponseObject cleanMessage() {
        commonMessageService.updateMessage();
        return ResponseObject.newSuccessResponseObject();
    }
}
