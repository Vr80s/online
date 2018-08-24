package com.xczhihui.course.params;

import java.io.Serializable;
import java.util.Map;

public class SubMessage implements Serializable {

    private String content;

    /**
     * 模板code
     */
    private String code;

    private Map<String, String> params;

    private Map<String, Object> customParams;

    public SubMessage(String code, Map<String, String> params) {
        this.code = code;
        this.params = params;
    }

    public SubMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, Object> getCustomParams() {
        return customParams;
    }

    public void setCustomParams(Map<String, Object> customParams) {
        this.customParams = customParams;
    }
}
