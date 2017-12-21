package com.xczh.consumer.market.vo;/**
 * @Author liutao【jvmtar@gmail.com】
 * @Date 2017/9/7 21:01
 */

import java.io.Serializable;

/**
 * @author liutao
 * @create 2017-09-07 21:01
 **/
public class VersionInfoVo implements Serializable {

    private String version;
    private String downUrl;
    private Boolean isMustUpdate;
    private Boolean isUpdate;
    private String filename;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public Boolean getMustUpdate() {
        return isMustUpdate;
    }

    public void setMustUpdate(Boolean mustUpdate) {
        isMustUpdate = mustUpdate;
    }

    public Boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Boolean update) {
        isUpdate = update;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
