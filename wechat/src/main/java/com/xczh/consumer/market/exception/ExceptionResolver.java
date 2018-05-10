package com.xczh.consumer.market.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.exception.IpandaTcmException;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.xczhihui.common.util.EmailUtil;

/**
 * 全局异常处理
 *
 * @author liutao
 * @create 2017-09-12 20:26
 **/
@ControllerAdvice
public class ExceptionResolver {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExceptionResolver.class);
    private static final String BIZ_RUNTIME_EXCEPTION_MESSAGE = "服务器错误,请联系管理员";
    private static final String BIZ_Parameter_EXCEPTION_MESSAGE = "参数有误";
    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseObject handleException(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        ex.printStackTrace(pw);
        pw.flush();
        sw.flush();
        //异常通知告警
        if((ex instanceof IpandaTcmException)&&((IpandaTcmException) ex).isAlarm()){
            try {
                String subject = "业务异常";
                EmailUtil.sendExceptionMailBySSL("wechat端",subject,printStackTraceToString(ex));
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        LOGGER.error("运行时异常.message:" + ex.getMessage());
        LOGGER.error("运行时异常.栈信息:" + sw.toString());
        LOGGER.error(!isContainChinese(ex.getMessage().substring(0, 1)) ? BIZ_RUNTIME_EXCEPTION_MESSAGE : ex.getMessage() == null ? BIZ_RUNTIME_EXCEPTION_MESSAGE : ex.getMessage());

        return ResponseObject.newErrorResponseObject(ex.getMessage() != null ? !isContainChinese(ex.getMessage().substring(0, 1)) ? BIZ_RUNTIME_EXCEPTION_MESSAGE : ex.getMessage() == null ? BIZ_RUNTIME_EXCEPTION_MESSAGE : ex.getMessage() : BIZ_RUNTIME_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    @ResponseBody
    public ResponseObject processMissingServletRequestParameterException() {
        return ResponseObject.newErrorResponseObject(BIZ_Parameter_EXCEPTION_MESSAGE);
    }

    public static boolean isContainChinese(String str) {
        Matcher m = CHINESE_PATTERN.matcher(str);
        return m.find();
    }

    public static String printStackTraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }
}
