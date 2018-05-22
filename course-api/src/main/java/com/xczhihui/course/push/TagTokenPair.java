package com.xczhihui.course.push;

/**
 * @author hejiwei
 */
public class TagTokenPair {

    private final String tag;
    private final String token;

    public TagTokenPair(String tag, String token) {
        this.tag = tag;
        this.token = token;
    }

    public String getTag() {
        return tag;
    }

    public String getToken() {
        return token;
    }
}
