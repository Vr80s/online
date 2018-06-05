package com.xczhihui.common.util.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论标签枚举
 *
 * @author hejiwei
 */

public enum CriticizeLabel {

    NICE(1, "很赞"),
    VERY_USEFUL(2, "干货很多"),
    ADVICE(3, "超值推荐"),
    LIKE(4, "喜欢"),
    DID_RIGHT_THING(5, "买对了");

    private int code;

    private String text;

    CriticizeLabel(int code, String text) {
        this.code = code;
        this.text = text;
    }

    private static CriticizeLabel getLabel(int code) {
        for (CriticizeLabel criticizeLabel : CriticizeLabel.values()) {
            if (code == criticizeLabel.code) {
                return criticizeLabel;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static List<String> getMultiLabelText(String labels) {
        if (labels == null || labels.isEmpty()) {
            return null;
        } else {
            String[] labelCodes = labels.split(",");
            List<String> labelTexts = new ArrayList<>(labelCodes.length);
            if (labelCodes.length > 0) {
                for (String labelCode : labelCodes) {
                    CriticizeLabel criticizeLabel = null;
                    try {
                        criticizeLabel = CriticizeLabel.getLabel(Integer.parseInt(labelCode));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (criticizeLabel != null) {
                        labelTexts.add(criticizeLabel.text);
                    }
                }
            }
            return labelTexts;
        }
    }
}
