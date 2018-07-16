package com.xczhihui.course.exception;

import java.io.Serializable;

import com.xczhihui.common.exception.IpandaTcmException;

/**
 * Description：课程相关异常
 * creed: Talk is cheap,show me the code
 *
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/4/25 0025 上午 9:06
 **/
public class CourseException extends IpandaTcmException implements Serializable {

    public CourseException(String msg, boolean alarm) {
        super(msg);
        this.alarm = alarm;
    }


    public CourseException(String msg) {
        super(msg);
        this.alarm = false;
    }

}
