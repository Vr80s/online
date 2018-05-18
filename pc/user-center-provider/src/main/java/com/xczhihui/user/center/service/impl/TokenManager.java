package com.xczhihui.user.center.service.impl;


import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.user.center.model.OeUser;
import com.xczhihui.user.center.service.CacheService;
import com.xczhihui.user.center.vo.Token;

/**
 * Description：管理用户使用的Token
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 2018/5/15 0015 上午 9:57
 **/
class TokenManager {

	private CacheService cacheService;

	// Token的缺省有效期
	private final int DEFAULT_EXPIRES = CacheService.ONE_HOUR;

	TokenManager(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	/**
	 * 根据用户信息创建Token；过期时间一个小时。
	 * 
	 * @param user
	 * @return
	 */
	Token createToken(OeUser user) {
		return this.createToken(user, DEFAULT_EXPIRES);
	}

	/**
	 * 根据用户信息创建Token。
	 * 
	 * @param user
	 * @param expires
	 *            过期时间 单位秒
	 * @return
	 */
	Token createToken(OeUser user, int expires) {
		if (user == null) {
			return null;
		}
		Token token = new Token();
		// 票的生成策略用UUID
		String ticket = CodeUtil.getRandomUUID();
		token.setTicket(ticket);
		token.setLoginName(user.getLoginName());
		token.setUserId(user.getId());
		token.setOrigin(user.getOrigin());
		long time = System.currentTimeMillis() + expires * 1000;
		token.setExpires(time);
		token.setMobile(user.getMobile());
		token.setEmail(user.getEmail());
		token.setNickName(user.getName());
		token.setPassWord(user.getPassword());
		token.setUuid(user.getId());
		token.setHeadPhoto(user.getSmallHeadPhoto());

		String userRedisKey="t_u_k_"+user.getId();
		this.cacheService.set(userRedisKey, token, expires);
		return token;
	}

	/**
	 * 删除票。
	 * 
	 * @param ticket
	 * @return
	 */
	Token deleteTicket(String ticket) {
		Token token = this.cacheService.get(ticket);
		if (token != null) {
			this.cacheService.delete(ticket);
		}
		return token;
	}

	/**
	 * 延长票的有效期。
	 * 
	 * @param ticket
	 * @return
	 */
	Token reflushTicket(String ticket) {
		return this.reflushTicket(ticket, DEFAULT_EXPIRES);
	}
	
	/**
	 * 延长票的有效期。
	 * @param ticket
	 * @param expires 过期时间 单位秒
	 * @return
	 */
	Token reflushTicket(String ticket, int expires) {
		Token token = this.cacheService.get(ticket);
		if (token != null) {
			long time = System.currentTimeMillis() + expires * 1000;
			token.setExpires(time);
			this.cacheService.set(token.getTicket(), token, expires);
		}
		return token;
	}

	Token getTicket(String ticket) {
		return this.cacheService.get(ticket);
	}
	
	
	/**
	 * 根据用户信息创建Token。
	 * @param user
	 * @param expires
	 *            过期时间 单位秒
	 * @return
	 */
	Token createTokenMobile(OeUser user, int expires) {
		if (user == null) {
			return null;
		}
		Token token = new Token();
		// 票的生成策略用UUID
		String ticket = CodeUtil.getRandomUUID();
		token.setTicket(ticket);
		token.setLoginName(user.getLoginName());
		token.setUserId(user.getId());
		token.setOrigin(user.getOrigin());
		long time = System.currentTimeMillis() + expires * 1000;
		token.setExpires(time);
		token.setMobile(user.getMobile());
		token.setEmail(user.getEmail());
		token.setNickName(user.getName());
		token.setPassWord(user.getPassword());
		token.setUuid(user.getId());
		token.setHeadPhoto(user.getSmallHeadPhoto());
		return token;
	}
	
}
