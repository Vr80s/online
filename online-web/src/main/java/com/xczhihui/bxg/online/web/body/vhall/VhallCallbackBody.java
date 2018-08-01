package com.xczhihui.bxg.online.web.body.vhall;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微吼云回调实体封装
 *
 * @author hejiwei
 */
public class VhallCallbackBody implements Serializable {

    private static final String TRANS_OVER = "record/trans-over";
    private static final String CREATED_SUCCESS = "record/created-success";

    private String event;

    private String refer;

    private String time;

    @JsonProperty("record_id")
    private String recordId;
    @JsonProperty("document_id")
    private String documentId;

    private Integer status;

    private String signature;

    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        if (TRANS_OVER.equals(this.event)) {
            params.put("document_id", this.documentId);
        } else if (CREATED_SUCCESS.equals(this.event)) {
            params.put("record_id", this.recordId);
        } else {
            throw new IllegalArgumentException("回调参数的event名参数错误");
        }
        params.put("event", event);
        params.put("refer", refer);
        params.put("time", time);
        params.put("status", String.valueOf(status));
        return params;
    }

    @Override
    public String toString() {
        return "VhallCallbackBody{" +
                "event='" + event + '\'' +
                ", refer='" + refer + '\'' +
                ", time='" + time + '\'' +
                ", recordId='" + recordId + '\'' +
                ", documentId='" + documentId + '\'' +
                ", status=" + status +
                ", signature='" + signature + '\'' +
                '}';
    }

    public boolean isTransOverEvent() {
        return TRANS_OVER.equals(this.event);
    }

    public boolean isCreatedEvent() {
        return CREATED_SUCCESS.equals(this.event);
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
