package com.xczhihui.medical.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.medical.service.DoctorPostsCommentService;
import com.xczhihui.medical.vo.DoctorPostsCommentVO;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.TableVo;
import com.xczhihui.utils.Tools;

/**
 * @author hejiwei
 */
@Controller
@RequestMapping("doctorPost/comment")
public class DoctorPostsCommentController {

    @Autowired
    private DoctorPostsCommentService doctorPostsCommentService;

    @RequestMapping(value = "index")
    public String index() {
        return "medical/post-comment";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public TableVo list(TableVo tableVo) {
        String params = tableVo.getsSearch();
        Groups groups = Tools.filterGroup(params);

        Group doctorNameGroup = groups.findByName("doctorName");
        String doctorName = null;
        if (doctorNameGroup != null && doctorNameGroup.getPropertyValue1() != null) {
            doctorName = doctorNameGroup.getPropertyValue1().toString();
        }
        Page<DoctorPostsCommentVO> page = doctorPostsCommentService.list(
                doctorName, tableVo.getCurrentPage(), tableVo.getiDisplayLength());
        int total = page.getTotalCount();
        tableVo.setAaData(page.getItems());
        tableVo.setiTotalDisplayRecords(total);
        tableVo.setiTotalRecords(total);
        return tableVo;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public ResponseObject delete(@RequestParam List<Integer> ids) {
        doctorPostsCommentService.deleteByIds(ids);
        return ResponseObject.newSuccessResponseObject();
    }
}
