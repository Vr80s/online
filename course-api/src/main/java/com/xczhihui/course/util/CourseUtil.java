package com.xczhihui.course.util;

import com.xczhihui.course.enums.RouteTypeEnum;
import com.xczhihui.course.model.Course;

public class CourseUtil {

    public static RouteTypeEnum getRouteType(Boolean collection, int type) {
        collection = collection == null ? false : collection;
        return collection ? RouteTypeEnum.COLLECTION_COURSE_DETAIL_PAGE :
                (type == Course.COURSE_TYPE_LIVE ? RouteTypeEnum.LIVE_COURSE_DETAIL_PAGE :
                        (type == Course.COURSE_TYPE_VIDEO_AUDIO ? RouteTypeEnum.VIDEO_AUDIO_COURSE_DETAIL_PAGE
                                : RouteTypeEnum.OFFLINE_COURSE_DETAIL_PAGE));
    }
    
    
    
    
	/**
	 * Description：计算评论星级
	 * @return
	 * @return Double
	 * @author name：yangxuan <br>
	 *         email: 15936216273@163.com
	 *
	 */
	public static Double criticizeStartLevel(Double startLevel) {
		
		
		if (startLevel != null && startLevel != 0) { // 不等于0
			String b = startLevel.toString();
			if (b.length() > 1
					&& !b.substring(b.length() - 1, b.length()).equals("0")) { // 不等于整数
				String[] arr = b.split("\\.");
				Integer tmp = Integer.parseInt(arr[1]);
				if (tmp >= 5) {
					return (double) (Integer.parseInt(arr[0]) + 1);
				} else {
					return Double.valueOf(arr[0] + "." + 5);
				}
			} else {
				return startLevel;
			}
		}else {
			
			return 0d;
		}
	}
}
