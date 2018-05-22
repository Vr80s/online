package com.xczhihui.course.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author hejiwei
 */
@Component
public class Env {

    private static final String PROD = "prod";
    private static final String TEST = "test";
    private static final String DEV = "dev";

    @Value("${env.flag}")
    private String env;

    public boolean isProd() {
        return PROD.equals(env);
    }
}
