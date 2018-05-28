package com.xczhihui.bxg.online.web.vo;

/**
 * @Author Fudong.Sun【】
 * @Date 2016/12/9 19:14
 */
public class SubsidiesVo {
    /**
     * 总学费补贴
     */
    private String allFee;
    /**
     * 一级学费补贴
     */
    private String levelFee1;
    /**
     * 二级学费补贴
     */
    private String levelFee2;
    /**
     * 三级学费补贴
     */
    private String levelFee3;

    public String getAllFee() {
        return allFee;
    }

    public void setAllFee(String allFee) {
        this.allFee = allFee;
    }

    public String getLevelFee1() {
        return levelFee1;
    }

    public void setLevelFee1(String levelFee1) {
        this.levelFee1 = levelFee1;
    }

    public String getLevelFee2() {
        return levelFee2;
    }

    public void setLevelFee2(String levelFee2) {
        this.levelFee2 = levelFee2;
    }

    public String getLevelFee3() {
        return levelFee3;
    }

    public void setLevelFee3(String levelFee3) {
        this.levelFee3 = levelFee3;
    }
}
