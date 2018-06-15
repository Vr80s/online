package com.xczhihui.course.util;

public class TextStyleUtil {
    public static final String LEFT_TAG = "<span>";
    public static final String RIGHT_TAG = "</span>";

    public static String clearStyle(String content) {
        if (content != null) {
            content = content.replaceAll(LEFT_TAG, "").replaceAll(RIGHT_TAG, "");
        }
        return content;
    }
}
