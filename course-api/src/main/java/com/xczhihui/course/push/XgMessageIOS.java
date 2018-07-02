package com.xczhihui.course.push;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class XgMessageIOS {
    public static final int TYPE_APNS_NOTIFICATION = 11;
    public static final int TYPE_REMOTE_NOTIFICATION = 12;
    private int expireTime;
    private String sendTime;
    private List<TimeInterval> acceptTimes;
    private int type;
    private Map<String, Object> custom;
    private String raw;
    private String alertStr;
    private JSONObject alertJo;
    private int badge;
    private String sound;
    private String category;
    private int loopInterval;
    private int loopTimes;

    public XgMessageIOS() {
        this.sendTime = "2014-03-13 16:13:00";
        this.acceptTimes = new ArrayList<>();
        this.raw = "";
        this.alertStr = "";
        this.alertJo = new JSONObject();
        this.badge = 0;
        this.sound = "";
        this.category = "";
        this.loopInterval = -1;
        this.loopTimes = -1;
        this.type = 11;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public void addAcceptTime(TimeInterval acceptTime) {
        this.acceptTimes.add(acceptTime);
    }

    public String acceptTimeToJson() {
        JSONArray jsonArr = new JSONArray();
        for (TimeInterval ti : this.acceptTimes) {
            JSONObject jtmp = ti.toJsonObject();
            jsonArr.put(jtmp);
        }
        return jsonArr.toString();
    }

    public JSONArray acceptTimeToJsonArray() {
        JSONArray jsonArr = new JSONArray();
        for (TimeInterval ti : this.acceptTimes) {
            JSONObject jtmp = ti.toJsonObject();
            jsonArr.put(jtmp);
        }
        return jsonArr;
    }

    public void setCustom(Map<String, Object> custom) {
        this.custom = custom;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public void setAlert(String alert) {
        this.alertStr = alert;
    }

    public void setAlert(JSONObject alert) {
        this.alertJo = alert;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getLoopInterval() {
        return this.loopInterval;
    }

    public void setLoopInterval(int loopInterval) {
        this.loopInterval = loopInterval;
    }

    public int getLoopTimes() {
        return this.loopTimes;
    }

    public void setLoopTimes(int loopTimes) {
        this.loopTimes = loopTimes;
    }

    public boolean isValid() {
        if (!this.raw.isEmpty()) {
            return true;
        }
        if ((this.type < 11) || (this.type > 12)) {
            return false;
        }
        if ((this.expireTime < 0) || (this.expireTime > 259200)) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            sdf.parse(this.sendTime);
        } catch (ParseException e) {
            return false;
        }
        for (TimeInterval ti : this.acceptTimes) {
            if (!ti.isValid()) {
                return false;
            }
        }
        if (this.type == 12) {
            return true;
        }
        return (!this.alertStr.isEmpty()) || (this.alertJo.length() != 0);
    }

    public String toJson() {
        if (!this.raw.isEmpty()) {
            return this.raw;
        }
        JSONObject json = new JSONObject(this.custom);
        json.put("accept_time", acceptTimeToJsonArray());

        JSONObject aps = new JSONObject();
        if (this.type == 12) {
            aps.put("content-available", 1);
        } else if (this.type == 11) {
            if (this.alertJo.length() != 0) {
                aps.put("alert", this.alertJo);
            } else {
                aps.put("alert", this.alertStr);
            }
            if (this.badge != 0) {
                aps.put("badge", this.badge);
            }
            if (!this.sound.isEmpty()) {
                aps.put("sound", this.sound);
            }
            if (!this.category.isEmpty()) {
                aps.put("category", this.category);
            }
        }
        json.put("aps", aps);

        return json.toString();
    }
}
