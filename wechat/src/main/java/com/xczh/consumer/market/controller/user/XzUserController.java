package com.xczh.consumer.market.controller.user;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.enums.TokenExpires;
import com.xczhihui.common.util.enums.VCodeType;
import com.xczhihui.course.util.XzStringUtils;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.service.VerificationCodeService;
import com.xczhihui.user.center.utils.UCCookieUtil;
import com.xczhihui.user.center.vo.OeUserVO;
import com.xczhihui.user.center.vo.Token;

@Controller
@RequestMapping(value = "/xczh/user")
public class XzUserController {
    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private VerificationCodeService verificationCodeService;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(XzUserController.class);

    /**
     * Description：发送短信验证码
     *
     * @param req
     * @param vtype
     * @param username
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping(value = "sendCode")
    @ResponseBody
    public ResponseObject sendCode(HttpServletRequest req,
                                   @RequestParam("vtype") Integer vtype,
                                   @RequestParam("username") String username) {
        if (!XzStringUtils.checkPhone(username)) {
            return ResponseObject.newErrorResponseObject("请输入正确的手机号");
        }

        verificationCodeService.addMessage(username, vtype == null ? VCodeType.RETISTERED : VCodeType.getType(vtype));
        return ResponseObject.newSuccessResponseObject("发送成功");
    }

    /**
     * h5普通浏览器、APP提交注册。
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "phoneRegist")
    @ResponseBody
    public ResponseObject phoneRegist(HttpServletRequest req,
                                      HttpServletResponse res,
                                      @RequestParam("password") String password,
                                      @RequestParam("username") String username,
                                      @RequestParam("code") String code) throws Exception {

        if (!XzStringUtils.checkPhone(username)) {
            return ResponseObject.newErrorResponseObject("请输入正确的手机号");
        }
        if (!XzStringUtils.checkPassword(password)) {
            return ResponseObject.newErrorResponseObject("密码为6-18位英文大小写字母或者阿拉伯数字");
        }
        verificationCodeService.checkCode(username, VCodeType.RETISTERED, code);
        OnlineUser ou = onlineUserService.addPhoneRegistByAppH5(req, password, username);
        Token t = userCenterService.loginMobile(username, password, TokenExpires.TenDay);
        ou.setTicket(t.getTicket());
        ou.setUserCenterId(t.getUserId());
        UCCookieUtil.writeTokenCookie(res, t);
        UCCookieUtil.clearThirdPartyCookie(res);

        return ResponseObject.newSuccessResponseObject(ou);
    }

    /**
     * Description：浏览器端登录
     *
     * @param req
     * @param res
     * @return ResponseObject
     * @throws Exception
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @SuppressWarnings("unused")
    @RequestMapping("login")
    @ResponseBody
    @Transactional
    public ResponseObject login(HttpServletRequest req, HttpServletResponse res,
                                @RequestParam("username") String username,
                                @RequestParam("password") String password) throws Exception {

        if (!XzStringUtils.checkPhone(username)) {
            return ResponseObject.newErrorResponseObject("请输入正确的手机号");
        }
        //存储在redis中了，有效期为10天。
        Token t = userCenterService.loginMobile(username, password, TokenExpires.TenDay);

        OnlineUser o = onlineUserService.findUserByLoginName(username);

        //把用户中心的数据给他   这里im都要用到
        OeUserVO user = userCenterService.getUserVO(username);
        o.setUserCenterId(user.getId());
        o.setPassword(user.getPassword());
        //把这个票给前端
        o.setTicket(t.getTicket());
        UCCookieUtil.writeTokenCookie(res, t);
        UCCookieUtil.clearThirdPartyCookie(res);

        return ResponseObject.newSuccessResponseObject(o);
    }

    /**
     * 忘记密码
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "forgotPassword")
    @ResponseBody
    public ResponseObject forgotPassword(HttpServletRequest req,
                                         @RequestParam("username") String username,
                                         @RequestParam("password") String password,
                                         @RequestParam("code") String code) throws Exception {
        if (!XzStringUtils.checkPhone(username)) {
            return ResponseObject.newErrorResponseObject("请输入正确的手机号");
        }

        if (!XzStringUtils.checkPassword(password)) {
            return ResponseObject.newErrorResponseObject("密码为6-18位英文大小写字母或者阿拉伯数字");
        }

        Integer vtype = VCodeType.FORGOT_PASSWORD.getCode();
        if (verificationCodeService.checkCode(username, VCodeType.FORGOT_PASSWORD, code)) {
            userCenterService.resetPassword(username, password);
            return ResponseObject.newSuccessResponseObject("修改密码成功");
        } else {
            return ResponseObject.newErrorResponseObject("修改密码失败");
        }
    }


    /**
     * 图形验证码
     *
     * @param req
     * @param res
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "vcode")
    public void vcode(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception {
        int width = 105, height = 46;
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        g.setColor(Color.GRAY);
        Random random = new Random();
        for (int i = 0; i < 40; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(10);
            int y2 = random.nextInt(10);
            g.drawLine(x1, y1, x1 + x2, y1 + y2);
        }
        Font font = new Font("Times New Roman", Font.PLAIN, 30);
        g.setFont(font);

        String[] ops = {"+", "-", "*", "/", "="};//定义运算符
        int num1 = random.nextInt(10);//生成第一个操作数
        String strRand1 = String.valueOf(num1);
        int red1 = random.nextInt(255);
        int green1 = random.nextInt(255);
        int blue1 = random.nextInt(255);
        g.setColor(new Color(red1, green1, blue1)); //画出第一个操作数
        g.drawString(strRand1, 13 * 0 + 6, 30);

        //int op_num=random.nextInt(4);//随机生成一个运算符数组中的下标，从而得到随机的一个运算符。这里是0~3之间一个随机值。因为4是等号
        int op_num = random.nextInt(2);//只有加减
        String strRand2 = (String) ops[op_num];
        int red2 = random.nextInt(255);
        int green2 = random.nextInt(255);
        int blue2 = random.nextInt(255);
        g.setColor(new Color(red2, green2, blue2));       //画出操作运算符
        g.drawString(strRand2, 13 * 1 + 16, 30);

        int num2 = (random.nextInt(9) + 1);    //随机生成0~8之间的一个数+1，作为第二个操作数。因为有可能出现除法，所以第二个操作数不能为0。所以+1，使数在1~9之间。
        String strRand3 = String.valueOf(num2);
        int red3 = random.nextInt(255);
        int green3 = random.nextInt(255);
        int blue3 = random.nextInt(255);
        g.setColor(new Color(red3, green3, blue3));       //画出第二个操作数
        g.drawString(strRand3, 13 * 2 + 26, 30);

        String strRand4 = (String) ops[4];
        int red4 = random.nextInt(255);
        int green4 = random.nextInt(255);
        int blue4 = random.nextInt(255);
        g.setColor(new Color(red4, green4, blue4));            //画出等号
        g.drawString(strRand4, 13 * 3 + 30, 30);

        Integer randomCode = 0;         //由运算符的不同执行不同的运算，得到验证码结果值
        switch (op_num) {
            case 0:
                randomCode = num1 + num2;
                break;
            case 1:
                randomCode = num1 - num2;
                break;
            default:
                break;
        }
        String appUniqueId = req.getParameter("appUniqueId");
        if (appUniqueId != null && !"".equals(appUniqueId)) {//来自app端的请求
            String imgCode = appUniqueId + "code";
            cacheService.set(imgCode, randomCode.toString(), 1 * 1800);
        } else {                                           //来自网页请求
            HttpSession session = req.getSession();
            session.setAttribute("randomCode", randomCode.toString());//把运算符结果值set到session中，用于其他文件进行验证码校对
        }
        buffImg.flush();
        g.dispose();
        res.setContentType("image/jpeg");
        res.setHeader("Pragma", "no-cache");
        res.setHeader("Cache-Control", "no-cache");
        res.setDateHeader("Expires", 0);
        OutputStream outputStream = res.getOutputStream();
        ImageIO.write(buffImg, "jpeg", outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * Description：校验手机号
     *
     * @param username 手机号
     * @return ResponseObject
     * @throws SQLException
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    @RequestMapping(value = "verifyPhone")
    @ResponseBody
    public ResponseObject sendCode(@RequestParam("username") String username) throws SQLException {
        if (!XzStringUtils.checkPhone(username)) {
            return ResponseObject.newErrorResponseObject("请输入正确的手机号");
        }
        onlineUserService.verifyPhone(username);
        return ResponseObject.newSuccessResponseObject("验证成功");
    }
}
