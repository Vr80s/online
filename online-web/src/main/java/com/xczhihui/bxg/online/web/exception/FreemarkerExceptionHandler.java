package com.xczhihui.bxg.online.web.exception;

import java.io.IOException;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerExceptionHandler implements TemplateExceptionHandler {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handleTemplateException(TemplateException te, Environment env,
                                        Writer out) throws TemplateException {
        logger.warn("[Freemarker Error: " + te.getMessage() + "]");
        String missingVariable = "undefined";
        try {
            String[] tmp = te.getMessageWithoutStackTop().split("\n");
            if (tmp.length > 1)
                tmp = tmp[1].split(" ");
            if (tmp.length > 1)
                missingVariable = tmp[1];

            out.write("[出错了，请联系网站管理员：${ " + missingVariable + "}]");

            logger.error("[出错了，请联系网站管理员]", te);
        } catch (IOException e) {
            throw new TemplateException("Failed to print error message. Cause: " + e, env);
        }
    }
}  
