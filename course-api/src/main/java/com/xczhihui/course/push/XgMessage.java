package com.xczhihui.course.push;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class XgMessage {
    public static final int TYPE_NOTIFICATION = 1;
    public static final int TYPE_MESSAGE = 2;
    private String title;
    private String content;
    private int expireTime;
    private String sendTime;
    private List<TimeInterval> acceptTimes;
    private int type;
    private int multiPkg;
    private Style style;
    private ClickAction action;
    private Map<String, Object> custom;
    private String raw;
    private int loopInterval;
    private int loopTimes;

    public XgMessage() {
        this.title = "";
        this.content = "";
        this.sendTime = "2013-12-20 18:31:00";
        this.acceptTimes = new ArrayList<>();
        this.multiPkg = 0;
        this.raw = "";
        this.loopInterval = -1;
        this.loopTimes = -1;
        this.action = new ClickAction();
        this.style = new Style(0);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public int getExpireTime() {
        return this.expireTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendTime() {
        return this.sendTime;
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

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public void setMultiPkg(int multiPkg) {
        this.multiPkg = multiPkg;
    }

    public int getMultiPkg() {
        return this.multiPkg;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public void setAction(ClickAction action) {
        this.action = action;
    }

    public void setCustom(Map<String, Object> custom) {
        this.custom = custom;
    }

    public void setRaw(String raw) {
        this.raw = raw;
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
        if ((this.type < 1) || (this.type > 2)) {
            return false;
        }
        if ((this.multiPkg < 0) || (this.multiPkg > 1)) {
            return false;
        }
        if (this.type == 1) {
            if (!this.style.isValid()) {
                return false;
            }
            if (!this.action.isValid()) {
                return false;
            }
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
        if ((this.loopInterval > 0) && (this.loopTimes > 0) && ((this.loopTimes - 1) * this.loopInterval + 1 > 15)) {
            return false;
        }
        return true;
    }

    public String toJson() {
        if (!this.raw.isEmpty()) {
            return this.raw;
        }
        JSONObject json = new JSONObject();
        if (this.type == 1) {
            json.put("title", this.title);
            json.put("content", this.content);
            json.put("accept_time", acceptTimeToJsonArray());
            json.put("builder_id", this.style.getBuilderId());
            json.put("ring", this.style.getRing());
            json.put("vibrate", this.style.getVibrate());
            json.put("clearable", this.style.getClearable());
            json.put("n_id", this.style.getNId());
            json.put("ring_raw", this.style.getRingRaw());
            json.put("lights", this.style.getLights());
            json.put("icon_type", this.style.getIconType());
            json.put("icon_res", this.style.getIconRes());
            json.put("style_id", this.style.getStyleId());
            json.put("small_icon", this.style.getSmallIcon());
            json.put("action", this.action.toJsonObject());
        } else if (this.type == 2) {
            json.put("title", this.title);
            json.put("content", this.content);
            json.put("accept_time", acceptTimeToJsonArray());
        }
        json.put("custocontent", this.custom);
        return json.toString();
    }
}
