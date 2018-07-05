package com.xczh.consumer.market.controller.user;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xczh.consumer.market.auth.Account;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.WeihouInterfacesListUtil;
import com.xczhihui.common.util.XzStringUtils;
import com.xczhihui.common.util.enums.VCodeType;
import com.xczhihui.course.service.IMyInfoService;
import com.xczhihui.course.vo.OnlineUserVO;
import com.xczhihui.online.api.service.CityService;
import com.xczhihui.online.api.service.CommonApiService;
import com.xczhihui.online.api.vo.UserAddressManagerVo;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.service.VerificationCodeService;
import com.xczhihui.user.center.utils.UCCookieUtil;
import com.xczhihui.user.center.vo.Token;

/**
 * 用户controller
 *
 * @author zhangshixiong
 * @date 2017-02-22
 */
@Controller
@RequestMapping(value = "/xczh/set")
public class XzUserSetController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(XzUserSetController.class);

    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private OLAttachmentCenterService service;
    @Autowired
    private CityService cityService;
    @Autowired
    private IMyInfoService myInfoService;
    @Autowired
    private CommonApiService commonApiService;
    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     * 修改密码
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "editPassword")
    @ResponseBody
    public ResponseObject editPassword(@RequestParam("oldPassword") String oldPassword,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("username") String username) throws Exception {

        if (!XzStringUtils.checkPassword(oldPassword)) {
            return ResponseObject.newErrorResponseObject("密码为6-18位英文大小写字母或者阿拉伯数字");
        }
        if (!XzStringUtils.checkPassword(newPassword)) {
            return ResponseObject.newErrorResponseObject("密码为6-18位英文大小写字母或者阿拉伯数字");
        }
        if (!XzStringUtils.checkPhone(username)) {
            return ResponseObject.newErrorResponseObject("请输入正确的手机号");
        }
        // 更新用户密码
        userCenterService.updatePassword(username, oldPassword, newPassword);
        return ResponseObject.newSuccessResponseObject("修改密码成功");
    }

    /**
     * Description：给 vtype 包含 3 和 4 发送短信。 如果是3的话，需要判断此手机号是否绑定，在发短信。
     * 如果是4的话，需要判断此要更改的手机号是否绑定，如果绑定就不发短信了。
     *
     * @param req
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping(value = "phoneCheck")
    @ResponseBody
    public ResponseObject phoneCheck(@RequestParam("username") String username,
                                     @RequestParam("vtype") Integer vtype) throws Exception {
        if (!XzStringUtils.checkPhone(username)) {
            return ResponseObject.newErrorResponseObject("请输入正确的手机号");
        }
        VCodeType vCodeType = VCodeType.getType(vtype);
        if (vCodeType.equals(VCodeType.NEW_PHONE) || vCodeType.equals(VCodeType.OLD_PHONE)) {
            verificationCodeService.addMessage(username, vCodeType);
            return ResponseObject.newSuccessResponseObject("发送成功");
        } else {
            return ResponseObject.newErrorResponseObject("类型参数错误");
        }
    }

    /**
     * Description：验证是否收到验证码啦
     *
     * @param req
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping(value = "phoneCheckAndCode")
    @ResponseBody
    public ResponseObject phoneCheckAndCode(@RequestParam("username") String username,
                                            @RequestParam("code") String code,
                                            @RequestParam("vtype") Integer vtype) throws Exception {
        if (!XzStringUtils.checkPhone(username)) {
            return ResponseObject.newErrorResponseObject("请输入正确的手机号");
        }
        if (verificationCodeService.checkCode(username, VCodeType.getType(vtype), code)) {
            return ResponseObject.newSuccessResponseObject("验证码正确");
        } else {
            return ResponseObject.newErrorResponseObject("验证码错误");
        }
    }

    /**
     * Description：更换手机号 --
     *
     * @param req
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping(value = "updatePhone")
    @ResponseBody
    @Transactional
    public ResponseObject updatePhone(@RequestParam("oldUsername") String oldUsername,
                                      @RequestParam("newUsername") String newUsername,
                                      @RequestParam("code") String code,
                                      @RequestParam("vtype") Integer vtype) throws Exception {
        if (!XzStringUtils.checkPhone(oldUsername)
                || !XzStringUtils.checkPhone(newUsername)) {
            return ResponseObject.newErrorResponseObject("请输入正确的手机号");
        }
        VCodeType vCodeType = VCodeType.getType(vtype);
        if (vCodeType.equals(VCodeType.NEW_PHONE) || vCodeType.equals(VCodeType.OLD_PHONE)) {
            if (verificationCodeService.checkCode(newUsername, vCodeType, code)) {
                // 更新用户信息
                userCenterService.updateLoginName(oldUsername, newUsername);
                // 更新用户表中的密码
                OnlineUser o = onlineUserService.findUserByLoginName(oldUsername);
                o.setLoginName(newUsername);
                onlineUserService.updateUserLoginName(o);
                return ResponseObject.newSuccessResponseObject("更改手机号成功");
            }
            return ResponseObject.newSuccessResponseObject("动态码错误");
        } else {
            return ResponseObject.newErrorResponseObject("类型参数错误");
        }
    }

    /**
     * Description：根据 id 获取用户信息
     *
     * @param req
     * @param userId
     * @return ResponseObject
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping(value = "getUserInfo")
    @ResponseBody
    public ResponseObject getUserInfo(@RequestParam("userId") String userId) {
        try {
            OnlineUser ou = onlineUserService.findUserById(userId);
            if (ou != null) {
                return ResponseObject.newSuccessResponseObject(ou);
            } else {
                return ResponseObject.newErrorResponseObject("获取用户信息有误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("获取错误信息啦" + e.getMessage());
            return ResponseObject.newErrorResponseObject("信息有误");
        }
    }

    /**
     * 判断用户是否登录着
     *
     * @param req
     * @param res
     * @param params
     * @return 如果登录着返回当前用户，否则返回错误
     * @throws Exception
     */
    @RequestMapping("isLogined")
    @ResponseBody
    public ResponseObject isLogined(HttpServletRequest req)
            throws Exception {
        Token t = UCCookieUtil.readTokenCookie(req);
        if (t == null) {
            return ResponseObject.newErrorResponseObject("请登录");
        } else {
            String ticket = t.getTicket();
            if (StringUtils.isBlank(ticket)) {
                return ResponseObject.newErrorResponseObject("请登录");
            }
            Token token = userCenterService.getToken(ticket);

            if (token != null) { // 正常登录着
                OnlineUser onlineUser = onlineUserService.findUserByLoginName(token.getLoginName());
                return ResponseObject.newSuccessResponseObject(onlineUser);
            } else {
                return ResponseObject.newErrorResponseObject("token过期，重新登录");
            }
        }
    }

    /**
     * 退出登录 -- 清理缓存啦
     *
     * @param req
     * @param res
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping("logout")
    @ResponseBody
    public ResponseObject logout(HttpServletRequest req,
                                 HttpServletResponse res)
            throws Exception {
        UCCookieUtil.clearTokenCookie(res);
        String token = req.getParameter("token");
        if (token != null) {
            userCenterService.deleteToken(token);
        }
        return ResponseObject.newSuccessResponseObject("退出成功");
    }

    /**
     * Description：用户中心保存接口
     *
     * @param request
     * @param response
     * @param user
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping("userInfo")
    @ResponseBody
    @Transactional
    public ResponseObject userInfo(HttpServletRequest request,OnlineUserVO user) throws Exception {

        if (!StringUtils.isNotBlank(user.getId())) {
            return ResponseObject.newErrorResponseObject("用户id不能为空");
        }
        LOGGER.info("user.getId():" + user.getId());
        /**
         * 保存个人资料信息
         */
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile fileMul = multipartRequest.getFile("file");
        if (fileMul != null && !fileMul.isEmpty()) {
            // 获得文件名：
            String filename = fileMul.getOriginalFilename();
            if (filename != null && !"".equals(filename.trim())) {
                filename = filename.toLowerCase();
                if (!filename.endsWith("image") && !filename.endsWith("gif")
                        && !filename.endsWith("jpg")
                        && !filename.endsWith("png")
                        && !filename.endsWith("bmp")) {
                    return ResponseObject.newErrorResponseObject("文件类型有误");
                }
                byte[] bs = fileMul.getBytes();
                LOGGER.info("个人头像:bytes():" + bs.length + ",filename:" + filename);
                String contentType = fileMul.getContentType();// 文件类型
                String projectName = "other";
                String fileType = "1"; // 图片类型了
                String headImgPath = service.upload(null, // 用户中心的用户ID
                        projectName, filename, contentType,
                        bs, fileType, null);
                LOGGER.info("文件路径——path:" + headImgPath);
                user.setSmallHeadPhoto(headImgPath);
            }
        }

        String provinceCityName = user.getProvinceName();
        if (StringUtils.isNotBlank(provinceCityName)) {
            String[] str = provinceCityName.split(" ");
            // 获取省市县
            if (str.length == 3) {
                user.setProvinceName(str[0]);
                user.setCityName(str[1]);
                user.setCountyName(str[2]);
            }
        }
        /**
         * 更新信息
         */
        myInfoService.updateUserSetInfo(user);

        /**
         * 如果用户信息发生改变。 更改缓存中的数据 那么就改变token的信息，也就是redsei里面的信息
         */
        String token = request.getParameter("token");
        OnlineUser newUser = onlineUserService.findUserById(user.getId());
        if (token != null) {
            userCenterService.updateTokenInfo(newUser.getId(), token);
        }
        /**
         * 更改微吼信息
         */
        if (StringUtils.isNotBlank(user.getName())
                || StringUtils.isNotBlank(user.getSmallHeadPhoto())) {

            String weiHouResp = WeihouInterfacesListUtil.updateUser(
                    user.getId(), null, newUser.getName(),
                    newUser.getSmallHeadPhoto());
            if (weiHouResp == null) {
                LOGGER.info("同步微吼昵称，头像失败");
            }
        }
        return ResponseObject.newSuccessResponseObject(newUser);
    }

    /**
     * Description：用户中心保存接口 h5
     *
     * @param request
     * @param response
     * @param user
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping("userInfoWechat")
    @ResponseBody
    @Transactional
    public ResponseObject userInfoWechat(HttpServletRequest request,OnlineUserVO user) throws Exception {
        /**
         * 保存个人资料信息
         */
        String provinceCityName = user.getProvinceName();
        // cityResult.className
        String citys = user.getRegionId();
        if (StringUtils.isNotBlank(provinceCityName)) {
            String[] str = provinceCityName.split(" ");
            // 获取省市县
            if (str.length == 3) {
                user.setProvinceName(str[0]);
                user.setCityName(str[1]);
                user.setCountyName(str[2]);
            }
        }
        if (StringUtils.isNotBlank(provinceCityName)) {
            String[] str = citys.split(" ");
            // 获取省市县
            if (str.length == 3) {
                user.setRegionAreaId(str[0]);
                user.setRegionCityId(str[1]);
                user.setRegionId(str[2]);
            }
        }
        /**
         * 更新信息
         */
        myInfoService.updateUserSetInfo(user);
        /**
         * 如果用户信息发生改变。 更改缓存中的数据 那么就改变token的信息，也就是redsei里面的信息
         */
        String token = request.getParameter("token");
        OnlineUser newUser = onlineUserService.findUserById(user.getId());
        if (token != null) {
            userCenterService.updateTokenInfo(newUser.getId(), token);
        }
        /**
         * 更改微吼信息
         */
        if (StringUtils.isNotBlank(user.getName())
                || StringUtils.isNotBlank(user.getSmallHeadPhoto())) {

            String weiHouResp = WeihouInterfacesListUtil.updateUser(
                    user.getId(), null, newUser.getName(),
                    newUser.getSmallHeadPhoto());
            if (weiHouResp == null) {
                LOGGER.info("同步微吼昵称，头像失败");
            }
        }
        return ResponseObject.newSuccessResponseObject(user);
    }

    /**
     * Description：微信端修改头像
     *
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping("wechatSaveHeadImg")
    @ResponseBody
    @Transactional
    public ResponseObject wechatSaveHeadImg(HttpServletRequest request,@Account OnlineUser account) throws Exception {
        // TODO
        String base64Data = request.getParameter("base64Data");
        String imageName = request.getParameter("imageName");
        // logger.debug("上传文件的数据："+base64Data);
        String dataPrix = "";
        String data = "";

        // logger.debug("对数据进行判断");
        if (base64Data == null || "".equals(base64Data)) {
            throw new Exception("上传失败，上传图片数据为空");
        } else {
            String[] d = base64Data.split("base64,");
            if (d != null && d.length == 2) {
                dataPrix = d[0];
                data = d[1];
            } else {
                throw new Exception("上传失败，数据不合法");
            }
        }
        String suffix = "";
        if ("data:image/jpeg;".equalsIgnoreCase(dataPrix)) {// data:image/jpeg;base64,base64编码的jpeg图片数据
            suffix = ".jpg";
        } else if ("data:image/x-icon;".equalsIgnoreCase(dataPrix)) {// data:image/x-icon;base64,base64编码的icon图片数据
            suffix = ".ico";
        } else if ("data:image/gif;".equalsIgnoreCase(dataPrix)) {// data:image/gif;base64,base64编码的gif图片数据
            suffix = ".gif";
        } else if ("data:image/png;".equalsIgnoreCase(dataPrix)) {// data:image/png;base64,base64编码的png图片数据
            suffix = ".png";
        } else {
            throw new Exception("上传图片格式不合法");
        }

        // 因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
        byte[] bs123 = Base64Utils.decodeFromString(data);

        String projectName = "other";
        String fileType = "1"; // 图片类型了

        Map<String, String> map = new HashMap<String, String>();
        String headImgPath = service.upload(null, projectName, imageName,
                suffix, bs123, fileType, null);

        LOGGER.info("文件路径——path:" + headImgPath);
        map.put("smallHeadPhoto", headImgPath);

        onlineUserService.updateUserCenterData(account, map);
        /**
         * 更新微吼信息
         */
        String weiHouResp = WeihouInterfacesListUtil.updateUser(account.getId(),
                null, null, map.get("smallHeadPhoto"));
        /**
         * 如果用户信息发生改变。那么就改变token的信息，也就是redsei里面的信息
         */
        OnlineUser newUser = onlineUserService.findUserByLoginName(account
                .getLoginName());
        request.getSession().setAttribute("_user_", newUser);
        if (weiHouResp == null) {
            LOGGER.info("同步微吼头像失败");
        }
        return ResponseObject.newSuccessResponseObject(map);
    }

    /**
     * 得到用户的地址管理列表
     */
    @RequestMapping("getAddressAll")
    @ResponseBody
    public ResponseObject getAddressAll(@Account String accountId) throws Exception {
        List<UserAddressManagerVo> list = cityService.getAddressAll(accountId);
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 保存编辑的地址
     */
    @RequestMapping("saveAddress")
    @ResponseBody
    public ResponseObject saveAddress(@ModelAttribute UserAddressManagerVo udm, @Account String accountId)
            throws Exception {
        udm.setUserId(accountId);

        if (!XzStringUtils.checkPhone(udm.getPhone())) {
            return ResponseObject.newErrorResponseObject("请输入正确的手机号");
        }
        try {
            cityService.saveAddress(udm);
            return ResponseObject.newSuccessResponseObject("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("保存失败");
        }
    }

    /**
     * 保存编辑的地址
     */
    @RequestMapping("updateAddress")
    @ResponseBody
    public ResponseObject updateAddress(@ModelAttribute UserAddressManagerVo udm) {
        try {
            if (!XzStringUtils.checkPhone(udm.getPhone())) {
                return ResponseObject.newErrorResponseObject("请输入正确的手机号");
            }
            cityService.updateAddress(udm);
            return ResponseObject.newSuccessResponseObject("修改成功");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("修改失败");
        }
    }

    /**
     * 设置地址为默认地址
     */
    @RequestMapping("updateIsAcquies")
    @ResponseBody
    public ResponseObject updateIsAcquies(String newId,@Account String accountId) {
        try {
            cityService.updateIsAcquies(newId, accountId);
            return ResponseObject.newSuccessResponseObject("修改成功");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("修改失败");
        }
    }

    /**
     * 删除这个地址啦
     */
    @RequestMapping("deleteAddressById")
    @ResponseBody
    public ResponseObject deleteAddressById(String id,@Account String accountId) {
        try {
            cityService.deleteAddressById(id, accountId);
            return ResponseObject.newSuccessResponseObject("删除成功");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("删除失败");
        }
    }

    /**
     * 根据id查找地址
     */
    @RequestMapping("findAddressById")
    @ResponseBody
    public ResponseObject findAddressById(String id) {
        try {
            UserAddressManagerVo umv = cityService.findAddressById(id);
            return ResponseObject.newSuccessResponseObject(umv);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("修改失败");
        }
    }

    /**
     * 根据用户id得到该用户的默认地址
     */
    @RequestMapping("findAddressByUserId")
    @ResponseBody
    public ResponseObject findAcquiescenceAddressById(@Account String accountId) throws SQLException {
        UserAddressManagerVo umv = cityService.findAddressByUserIdAndAcq(accountId);
        return ResponseObject.newSuccessResponseObject(umv);
    }

    /**
     * 得到所有都省、市、地区
     */
    @RequestMapping("getAllProvinces")
    @ResponseBody
    public ResponseObject getAllProvinces(Map<String, String> params)
            throws Exception {
        /**
         * 获取所有的省份
         */
        List<Map<String, Object>> list = cityService.getAllProvinceCityCounty();
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * Description：身份信息
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    @RequestMapping(value = "jobVo")
    @ResponseBody
    public ResponseObject JobVo(String group)
            throws Exception {
        return ResponseObject.newSuccessResponseObject(commonApiService.getJob(group));
    }

    /**
     * 得到所有都省份和市
     */
    @RequestMapping("getAll")
    @ResponseBody
    public ResponseObject getAll(Map<String, String> params)
            throws Exception {
        /**
         * 获取所有的省份
         */
        List<Map<String, Object>> list = cityService.getAllProvinceCityCounty();
        return ResponseObject.newSuccessResponseObject(list);
    }

    /**
     * 得到所有的国家
     */
    @RequestMapping("getProvince")
    @ResponseBody
    public ResponseObject getProvince(Map<String, String> params)
            throws Exception {
        /**
         * 获取所有的省份
         */
        // List<Map<String, Object>> list = cityService.getProvince();
        List<Map<String, Object>> list = cityService.getAllProvinceCity();
        return ResponseObject.newSuccessResponseObject(list);
    }

    @RequestMapping("check")
    @ResponseBody
    public ResponseObject check(){
        return ResponseObject.newSuccessResponseObject("已登录！");
    }
}
