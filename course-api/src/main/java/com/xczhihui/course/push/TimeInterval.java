package com.xczhihui.course.push;

import org.json.JSONObject;

public class TimeInterval {

    private int startHour;
    private int startMin;
    private int endHour;
    private int endMin;

    public TimeInterval(int startHour, int startMin, int endHour, int endMin) {
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
    }

    public boolean isValid() {
        return (this.startHour >= 0) && (this.startHour <= 23) && (this.startMin >= 0) && (this.startMin <= 59)
                && (this.endHour >= 0) && (this.endHour <= 23) && (this.endMin >= 0) && (this.endMin <= 59);
    }

    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        JSONObject js = new JSONObject();
        JSONObject je = new JSONObject();
        js.put("hour", String.valueOf(this.startHour));
        js.put("min", String.valueOf(this.startMin));
        je.put("hour", String.valueOf(this.endHour));
        je.put("min", String.valueOf(this.endMin));
        json.put("start", js);
        json.put("end", je);
        return json;
    }
}
