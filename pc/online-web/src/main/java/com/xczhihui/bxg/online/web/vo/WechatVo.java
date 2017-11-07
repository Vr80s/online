package com.xczhihui.bxg.online.web.vo;

/**
 * 微信分销业务层对接接口类
 * @author 康荣彩
 * @create 2016-11-21 16:21
 */
public class WechatVo {

    /**
     * 注册账号(手机号)
     */
      private  String   mobile;
    /**
     * 课程id号
     */
      private  Integer  courseId;

     /**
      * 实际支付金额
      */
      private  String   money;

      /**
       * 微信订单号
       */
      private  String   transactionId;

      /**
       * 商户订单号
       */
      private  String   orderNo;

     /**
      * 时间戳
      */
      private  String  timestamp;

    /**
     * 签名(MD5加密)
     */
      private String sign;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Integer getCourseId() {
            return courseId;
        }

        public void setCourseId(Integer courseId) {
            this.courseId = courseId;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
}
