package com.xczhihui.common.util.enums;

public enum CourseLiveAudioMessageType {

    CourseLiveAudioContent(1),
    CourseLiveAudioContentLike(2),
    CourseLiveAudioContentDelete(3),
    CourseLiveAudioDiscussion(4),
    CourseLiveAudioDiscussionLike(5),
    CourseLiveAudioDiscussionDelete(6);

    private int code;

    private CourseLiveAudioMessageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}