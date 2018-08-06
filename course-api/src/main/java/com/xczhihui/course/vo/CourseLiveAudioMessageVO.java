package com.xczhihui.course.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xczhihui.common.util.enums.CourseLiveAudioMessageType;

import lombok.Data;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yuxin
 * @since 2018-07-31
 */
@Data
public class CourseLiveAudioMessageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    public CourseLiveAudioMessageVO(Integer type, Object body) {
        this.type = type;
        this.body = body;
    }

    private int type;
    private Object body;

    public String toJson() {
        return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCheckSpecialChar);
    }
}
