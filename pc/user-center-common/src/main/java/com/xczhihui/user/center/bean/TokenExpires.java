package com.xczhihui.user.center.bean;

/**
 * token有效期类型
 * 
 * @author liyong
 *
 */
public enum TokenExpires {



		/**
		 * 有效期一个小时。
		 */
		Hour(60 * 60),

		/**
		 * 有效期天数。
		 */
		Day(Hour.getExpires() * 24),
		TwoDay(Day.getExpires() * 2),
		ThreeDay(Day.getExpires() * 3),
		FourDay(Day.getExpires() * 4),
		FiveDay(Day.getExpires() * 5),
		TenDay(Day.getExpires() * 10),
		TwentyDay(Day.getExpires() * 20);

		private TokenExpires(int expires) {
			this.expires = expires;
		}

		/**
		 * 过期时间的秒数。
		 */
		private int expires;

		/**
		 * 过期时间的秒数。
		 *
		 * @return
		 */
		public int getExpires() {
			return this.expires;
		}
	}
