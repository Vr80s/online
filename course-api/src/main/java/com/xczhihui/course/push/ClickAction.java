package com.xczhihui.course.push;

import org.json.JSONObject;

public class ClickAction {
    public static final int TYPE_ACTIVITY = 1;
    public static final int TYPE_URL = 2;
    public static final int TYPE_INTENT = 3;
    private int actionType;
    private String url;
    private int confirmOnUrl;
    private String activity;
    private String intent;
    private int atyAttrIntentFlag;
    private int atyAttrPendingIntentFlag;
    private String packageDownloadUrl;
    private int confirmOnPackageDownloadUrl;
    private String packageName;

    public ClickAction() {
        this.url = "";
        this.actionType = 1;
        this.activity = "";

        this.atyAttrIntentFlag = 0;
        this.atyAttrPendingIntentFlag = 0;

        this.packageDownloadUrl = "";
        this.confirmOnPackageDownloadUrl = 1;
        this.packageName = "";
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setConfirmOnUrl(int confirmOnUrl) {
        this.confirmOnUrl = confirmOnUrl;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public void setAtyAttrIntentFlag(int atyAttrIntentFlag) {
        this.atyAttrIntentFlag = atyAttrIntentFlag;
    }

    public void setAtyAttrPendingIntentFlag(int atyAttrPendingIntentFlag) {
        this.atyAttrPendingIntentFlag = atyAttrPendingIntentFlag;
    }

    public void setPackageDownloadUrl(String packageDownloadUrl) {
        this.packageDownloadUrl = packageDownloadUrl;
    }

    public void setConfirmOnPackageDownloadUrl(int confirmOnPackageDownloadUrl) {
        this.confirmOnPackageDownloadUrl = confirmOnPackageDownloadUrl;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("action_type", this.actionType);
        JSONObject browser = new JSONObject();
        browser.put("url", this.url);
        browser.put("confirm", this.confirmOnUrl);
        json.put("browser", browser);
        json.put("activity", this.activity);
        json.put("intent", this.intent);

        JSONObject aty_attr = new JSONObject();
        aty_attr.put("if", this.atyAttrIntentFlag);
        aty_attr.put("pf", this.atyAttrPendingIntentFlag);
        json.put("aty_attr", aty_attr);

        return json.toString();
    }

    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.put("action_type", this.actionType);
        JSONObject browser = new JSONObject();
        browser.put("url", this.url);
        browser.put("confirm", this.confirmOnUrl);
        json.put("browser", browser);
        json.put("activity", this.activity);
        json.put("intent", this.intent);

        JSONObject aty_attr = new JSONObject();
        aty_attr.put("if", this.atyAttrIntentFlag);
        aty_attr.put("pf", this.atyAttrPendingIntentFlag);
        json.put("aty_attr", aty_attr);

        return json;
    }

    public boolean isValid() {
        if ((this.actionType < 1) || (this.actionType > 3)) {
            return false;
        }
        if (this.actionType == 2) {
            if ((this.url.isEmpty()) || (this.confirmOnUrl < 0) || (this.confirmOnUrl > 1)) {
                return false;
            }
            return true;
        }
        if (this.actionType == 3) {
            if (this.intent.isEmpty()) {
                return false;
            }
            return true;
        }
        return true;
    }
}
