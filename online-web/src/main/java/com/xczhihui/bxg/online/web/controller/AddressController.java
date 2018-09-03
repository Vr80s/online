package com.xczhihui.bxg.online.web.controller;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.online.api.service.CityService;
import com.xczhihui.online.api.vo.UserAddressManagerVo;

/**
 * 点播控制器 ClassName: BunchPlanController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2017年8月11日<br>
 */
@Controller
@RequestMapping("/address")
public class AddressController extends AbstractController {

    @Autowired
    private CityService cityService;

    /**
     * 得到用户的地址管理列表
     */
    @RequestMapping("getAddressAll")
    @ResponseBody
    public ResponseObject getAddressAll(HttpServletRequest req, HttpServletResponse res) throws Exception {

        //获取当前登录用户信息
        OnlineUser u = getCurrentUser();
        /**
         * 获取所有的省份
         */
        List<UserAddressManagerVo> list = cityService.getAddressAll(u.getId());
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 保存编辑的地址
     */
    @RequestMapping("saveAddress")
    @ResponseBody
    public ResponseObject saveAddress(HttpServletRequest req,
                                      HttpServletResponse res, @ModelAttribute UserAddressManagerVo udm)
            throws Exception {
        //获取当前登录用户信息
        OnlineUser ou = getCurrentUser();
        if (ou == null) {
            throw new RuntimeException("未登录");
        }
        udm.setUserId(ou.getId());
        cityService.saveAddress(udm);
        return ResponseObject.newSuccessResponseObject("保存成功");
    }

    /**
     * 保存编辑的地址
     */
    @RequestMapping("updateAddress")
    @ResponseBody
    public ResponseObject updateAddress(HttpServletRequest req,
                                        HttpServletResponse res, @ModelAttribute UserAddressManagerVo udm) throws SQLException {
        cityService.updateAddress(udm);
        return ResponseObject.newSuccessResponseObject("修改成功");
    }

    /**
     * 设置地址为默认地址
     */
    @RequestMapping("updateIsAcquies")
    @ResponseBody
    public ResponseObject updateIsAcquies(HttpServletRequest req, HttpServletResponse res) throws SQLException {
        String newId = req.getParameter("newId");
        //获取当前登录用户信息
        OnlineUser ou = getCurrentUser();
        cityService.updateIsAcquies(newId, ou.getId());
        return ResponseObject.newSuccessResponseObject("修改成功");
    }

    /**
     * 删除这个地址啦
     */
    @RequestMapping("deleteAddressById")
    @ResponseBody
    public ResponseObject deleteAddressById(HttpServletRequest req, HttpServletResponse res) throws SQLException {
        String id = req.getParameter("id");
        //获取当前登录用户信息
        OnlineUser ou = getCurrentUser();
        cityService.deleteAddressById(id, ou.getId());
        return ResponseObject.newSuccessResponseObject("删除成功");
    }

    /**
     * 根据id查找地址
     */
    @RequestMapping("findAddressById")
    @ResponseBody
    public ResponseObject findAddressById(HttpServletRequest req, HttpServletResponse res) throws SQLException {
        String id = req.getParameter("id");
        UserAddressManagerVo umv = cityService.findAddressById(id);
        return ResponseObject.newSuccessResponseObject(umv);
    }

    /**
     * 得到所有都省份和市
     */
    @RequestMapping("getAll")
    @ResponseBody
    public ResponseObject getAll(HttpServletRequest req, HttpServletResponse res) throws Exception {
        /**
         * 获取所有的省份
         */
        List<Map<String, Object>> list = cityService.getAllProvinceCity();
        return ResponseObject.newSuccessResponseObject(list);
    }


    /**
     * 得到所有都省、市、地区
     */
    @RequestMapping("getAllPCC")
    @ResponseBody
    public ResponseObject getAllPCC(HttpServletRequest req, HttpServletResponse res) throws Exception {
       
        return ResponseObject.newSuccessResponseObject(cityService.getAllProvinceCityCounty());
    }

    /**
     * 得到所有的国家
     */
    @RequestMapping("getProvince")
    @ResponseBody
    public ResponseObject getProvince(HttpServletRequest req, HttpServletResponse res) throws Exception {
        /**
         * 获取所有的省份
         */
        List<Map<String, Object>> list = cityService.getAllProvinceCity();
        return ResponseObject.newSuccessResponseObject(list);
    }

}
