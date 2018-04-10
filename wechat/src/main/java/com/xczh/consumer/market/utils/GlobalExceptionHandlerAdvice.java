package com.xczh.consumer.market.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import com.xczhihui.bxg.common.util.EmailUtil;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;



/**
 * 全局异常处理
 * @author liutao
 * @create 2017-09-12 20:26
 **/
@ControllerAdvice
public class GlobalExceptionHandlerAdvice {

    private static final  String BIZ_RUNTIME_EXCEPTION_MESSAGE="服务器错误,请联系管理员";
    private static final  String BIZ_Parameter_EXCEPTION_MESSAGE="参数有误";
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerAdvice.class);
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseObject handleException(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        ex.printStackTrace(pw);
        pw.flush();
        sw.flush();
        LOGGER.error("运行时异常.message:"+ex.getMessage());
        LOGGER.error("运行时异常.栈信息:"+sw.toString());
        try {
            EmailUtil.sendExceptionMailBySSL("wechat端",ex.getMessage(),printStackTraceToString(ex));
        } catch (MessagingException e) {
            e.printStackTrace();
        }

//                ex.getMessage():BIZ_RUNTIME_EXCEPTION_MESSAGE
//        if(ex.getMessage()!=null){
//        	!isContainChinese(ex.getMessage().substring(0,1))
//        }else{
//        	BIZ_RUNTIME_EXCEPTION_MESSAGE:ex.getMessage()==null
//        			BIZ_RUNTIME_EXCEPTION_MESSAGE
//        			ex.getMessage()
//        			BIZ_RUNTIME_EXCEPTION_MESSAGE
//        }
        
        LOGGER.error("ex.getMessage()：~~~~~~~~~~~~"+ex.getMessage());
        
        LOGGER.error("打印出这个异常啦：~~~~~~~~~~~~"+ex.getMessage()!=null?!isContainChinese(ex.getMessage().substring(0,1))?BIZ_RUNTIME_EXCEPTION_MESSAGE:ex.getMessage()==null?BIZ_RUNTIME_EXCEPTION_MESSAGE:ex.getMessage():BIZ_RUNTIME_EXCEPTION_MESSAGE);
        
        return  ResponseObject.newErrorResponseObject(ex.getMessage()!=null?!isContainChinese(ex.getMessage().substring(0,1))?BIZ_RUNTIME_EXCEPTION_MESSAGE:ex.getMessage()==null?BIZ_RUNTIME_EXCEPTION_MESSAGE:ex.getMessage():BIZ_RUNTIME_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class,MethodArgumentTypeMismatchException.class})
    @ResponseBody
    public ResponseObject processMissingServletRequestParameterException() {
        return  ResponseObject.newErrorResponseObject(BIZ_Parameter_EXCEPTION_MESSAGE);
    }

    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public static String printStackTraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }
}
