package com.xczhihui.bxg.online.web.utils.alipay;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
//	public static String app_id = "2017072807932656";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
//    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQChsJLO96VFCaRCLozFDbk+w1X0te3s3I7bJ8c3igZsstFAbm8WDb5Y/h41BOGeVC2LOSsjZQC2awcs/fso2QpFPGTBlk5kcMgT4ezMijCSKKM2pK9Cvh97/02YO0g/VkjX6AZFVd77t+XwrkPOiygb9PyEDq/jIvO8WcIucdP771r/ZvE89NRypa9kAcr41twDYPWY62mNs2YyjYmTLQd5mFsi/4BBatdPz0XvtXA1kmSRUhvUcvzmCm6jk0RwXVy6Ea7Z0Ceu+4pfnc/aJFfcKJv6UJQQ3rmipmwinhYi2nJ4bVtIgx+ASNlXINWt3bTJmGMZqFVtCZ3l49yCOzfBAgMBAAECggEAMuyrAFaNDfZgbpu8qF+PJY5eJymZmw1ITQv1Oa/WICwdrZ5ajGadueenWemEqdo3Ue8agBZSqCGDbA8+KHpbOr0vuqz9WbMPwPtaGn23mIEGDrLFpE6/Gc2qAbVCJvilDqM8PmAyT7N2z1wDbSz04AFD+s+pY+9hNsRKXVhqfKFA71nJ4rT8EUBYGbhJLh7Gme8nS583umoWVwjHrvPLqfZe9QB576kbWwCJOBqXKStpTYea9bNZUWbtw20hGrDfCMyyzjUL3K0ecezL4vxTaSGR6unpcG9TdJfBq6KmIKJG/cyejI2R9NjhXfLHOCCqLc6BHe0qkNbC+fitLTCtVQKBgQDYfD9W7AX/jpCy3RvgF+vpB7c3uUS771lTtQ07nMn90Yavx/1mk2UodYymL2LRubCH1M9SHltdk9lBxe70YiPlhIJJZeCOs1CUErtfDoYnKERhwpGx/1s44qjs9ooPcSdNAKoRDjFXNIQPnw6+k1UzQ0dvrGXp/7/r0pnblJWviwKBgQC/M+Gf/YcZkrQ+GSGWUDS5AUWjZadbIlRCLSTW8GDMMVbmrzc+7DuMAA1QuhyHS1HDnz38NeJBEK3Uqs4qIYXn2lF5u2z/l6eAFEOd1OFFPE6NrjqUy+nslAuoO2QvuwJQ8AjjcjdPJKc66zRRrovNHLAkGextT+Ci5eNIxxOfYwKBgQC/XFz07d+DfjcEFJVOanbbXzmipT9PzQwuBS20UyzuE2c2PNcO9B2IPRhd0idM8hJMj13P3guvVUDHdjp6hcHrYU11qftsyK7ipQhBx2nodRy1ObNmHy44w4rFJEz3x3MRCxRJzTzqM/7EfDohVcULcl5UJZVU2gCBaYEda2NBbwKBgBuVsZRycD5JQwW+fHECK0kRnOlg7g8g2cUeXDVCQsTSzXXEi5ThYgnlrAYcg6clP6uYWsn7QCQg8uM+rTW41mfHwH9ugeAyEfFRexvXLZTeiXq5SyxSavI9vZzMzLxyH3hr2OxvevlJEXNXoZmzM+oonGTo9IokvwThY7QJPJR/AoGBAMpoH2mmmIpNZnAkJw82YS0PfUy3bF7nBUU5mEnZiEVnrMY10uqgohSDt+wslbpx2dL7NoRrSx3K5l5sEV4QNv/u1FZMF0cUSlXY78LTiLiPLZtmvXLmbZhmNag/irMXJ0pZ8Q5xOrO0Na4nuLPOfDrtXK1q2FIKeYDIUDgzvVli";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
//    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuzLNkQQ41cDv/azbuctIziPqUVDbpaT3r5NT5d6mhaDQ8m1v3UtFa0rM7oV4XiLhM8O0uBH6Dx0U0eb9izOjE2yIDWT8FJaraOKf6ncscpfawZX79TDg+L+531uBUFExsrhNaCZDNpmmREyXkPkpXHIlYDTpuUzwdJpXtaQPE9B8yeVsrixdEAT5XnSfyhqP/KJeo8P8Axj7w3aTY5vjduLXVfBOyOTVw/5bx2LfqFPkUl12xIr3L0KE1tSVAzdGrTEReWQDPkOU5Q7FdLsck+FCquadcZtg/Kj5d07dD/i++VeThK8yB6DaQ/dUloMTmwvYxclFnkGfGjR8qFXjYQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "/web/alipay/notify_url";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "/web/alipay/return_url";

	public static String close_url = "/close.html";

	public static String recharge_jump_url = "/rechargeJump.html";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
//	public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
	
	//支付宝网关（沙盒环境）
	//public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

