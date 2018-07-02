package com.xczhihui.course.push;

public class Style {

    private int builderId;
    private int ring;
    private int vibrate;
    private int clearable;
    private int nId;
    private String ringRaw;
    private int lights;
    private int iconType;
    private String iconRes;
    private int styleId;
    private String smallIcon;

    public Style(int builderId) {
        this(builderId, 0, 0, 1, 0, 1, 0, 1);
    }

    public Style(int builderId, int ring, int vibrate, int clearable, int nId) {
        this.builderId = builderId;
        this.ring = ring;
        this.vibrate = vibrate;
        this.clearable = clearable;
        this.nId = nId;
    }

    public Style(int builderId, int ring, int vibrate, int clearable, int nId, int lights, int iconType, int styleId) {
        this.builderId = builderId;
        this.ring = ring;
        this.vibrate = vibrate;
        this.clearable = clearable;
        this.nId = nId;
        this.lights = lights;
        this.iconType = iconType;
        this.styleId = styleId;
    }

    public int getBuilderId() {
        return this.builderId;
    }

    public int getRing() {
        return this.ring;
    }

    public int getVibrate() {
        return this.vibrate;
    }

    public int getClearable() {
        return this.clearable;
    }

    public int getNId() {
        return this.nId;
    }

    public int getLights() {
        return this.lights;
    }

    public int getIconType() {
        return this.iconType;
    }

    public int getStyleId() {
        return this.styleId;
    }

    public String getRingRaw() {
        return this.ringRaw;
    }

    public void setRingRaw(String ringRaw) {
        this.ringRaw = ringRaw;
    }

    public String getIconRes() {
        return this.iconRes;
    }

    public void setIconRes(String iconRes) {
        this.iconRes = iconRes;
    }

    public String getSmallIcon() {
        return this.smallIcon;
    }

    public void setSmallIcon(String smallIcon) {
        this.smallIcon = smallIcon;
    }

    public boolean isValid() {
        if ((this.ring < 0) || (this.ring > 1)) {
            return false;
        }
        if ((this.vibrate < 0) || (this.vibrate > 1)) {
            return false;
        }
        if ((this.clearable < 0) || (this.clearable > 1)) {
            return false;
        }
        if ((this.lights < 0) || (this.lights > 1)) {
            return false;
        }
        if ((this.iconType < 0) || (this.iconType > 1)) {
            return false;
        }
        if ((this.styleId < 0) || (this.styleId > 1)) {
            return false;
        }
        return true;
    }
}