package com.xczhihui.bxg.online.manager.gift.web;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.common.support.domain.Attachment;
import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.bxg.common.support.service.AttachmentType;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.common.web.controller.AbstractController;
import com.xczhihui.bxg.online.manager.gift.service.GiftService;
import com.xczhihui.bxg.online.manager.gift.vo.GiftVo;
import com.xczhihui.bxg.online.manager.support.shiro.ManagerUserUtil;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.TableVo;
import com.xczhihui.bxg.online.manager.utils.Tools;

/**
 * 课程管理控制层实现类
 *
 * @author yxd
 */

@Controller
@RequestMapping("gift")
public class GiftController extends AbstractController {
    protected final static String GIFT_PATH_PREFIX = "/gift/";
    @Autowired
    private GiftService giftService;
    @Autowired
    private AttachmentCenterService att;
    @Value("${online.web.url:http://www.ixincheng.com}")
    private String weburl;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request) {

        // List<LecturerVo> lecturers = courseService.getLecturers();
        // request.setAttribute("lecturerVo", lecturers);


        return GIFT_PATH_PREFIX + "/gift";
    }

    //@RequiresPermissions("gift:menu")
    @RequestMapping(value = "list")
    @ResponseBody
    public TableVo gifts(TableVo tableVo) {
        int pageSize = tableVo.getiDisplayLength();
        int index = tableVo.getiDisplayStart();
        int currentPage = index / pageSize + 1;
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);

        GiftVo searchVo = new GiftVo();
        Group giftName = groups.findByName("search_giftName");

        if (giftName != null) {
            searchVo.setName(giftName.getPropertyValue1().toString());
        }

        Group status = groups.findByName("search_status");

        if (status != null) {
            searchVo.setStatus(status.getPropertyValue1().toString());
        }
        Page<GiftVo> page = giftService.findGiftPage(searchVo, currentPage,
                pageSize);
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;

    }

    /**
     * 添加
     *
     * @param courseVo
     * @return
     */
    //@RequiresPermissions("gift:menu")
    @RequestMapping(value = "addGift", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject add(GiftVo giftVo) {
        ResponseObject responseObj = new ResponseObject();
        try {
            giftService.addGift(giftVo);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("新增失败");
        }
        return responseObj;
    }

    /**
     * 查看
     *
     * @param id
     * @return
     */
    //@RequiresPermissions("gift:menu")
    @RequestMapping(value = "findCourseById", method = RequestMethod.GET)
    @ResponseBody
    public GiftVo findGiftById(Integer id) {
        return giftService.getGiftById(id);
    }

    /**
     * 编辑
     *
     * @param courseVo
     * @return
     */
    //@RequiresPermissions("gift:menu")
    @RequestMapping(value = "updateGiftById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateGiftById(GiftVo giftVo) {
        ResponseObject responseObj = new ResponseObject();

        try {
            giftService.updateGift(giftVo);
            responseObj.setSuccess(true);
            responseObj.setErrorMessage("修改成功");
        } catch (Exception e) {
            responseObj.setSuccess(false);
            responseObj.setErrorMessage("修改失败");
            e.printStackTrace();
        }
        return responseObj;
    }

    /**
     * 修改状态(禁用or启用)
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateStatus(Integer id) {
        giftService.updateStatus(id);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteCourseById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deleteCourseById(Integer id) {
        giftService.deleteGiftById(id);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    /**
     * 设置分成
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "updateBrokerage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject updateBrokerage(String ids, String brokerage) {
        giftService.updateBrokerage(ids, brokerage);
        return ResponseObject.newSuccessResponseObject("操作成功！");
    }

    /**
     * 上移
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "upMove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject upMove(Integer id) {
        ResponseObject responseObj = new ResponseObject();
        giftService.updateSortUp(id);
        responseObj.setSuccess(true);
        return responseObj;
    }

    /**
     * 下移
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "downMove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject downMove(Integer id) {
        ResponseObject responseObj = new ResponseObject();
        giftService.updateSortDown(id);
        responseObj.setSuccess(true);
        return responseObj;
    }

    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject deletes(String ids) throws InvocationTargetException,
            IllegalAccessException {
        ResponseObject responseObject = new ResponseObject();
        if (ids != null) {
            String[] _ids = ids.split(",");
            giftService.deletes(_ids);
        }
        responseObject.setSuccess(true);
        responseObject.setErrorMessage("删除成功!");
        return responseObject;
    }

    @RequestMapping(value = "uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public ResponseObject uploadImg(String content) {
        String str = content.split("base64,")[1];
        byte[] b = org.apache.commons.codec.binary.Base64.decodeBase64(str);
        Attachment a = att.addAttachment(ManagerUserUtil.getId(),
                AttachmentType.ONLINE, "1.png", b, "image/png");
        if (a.getError() != 0) {
            return ResponseObject.newErrorResponseObject("上传失败！");
        }
        return ResponseObject.newSuccessResponseObject(a);
    }
}
