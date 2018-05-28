package com.xczhihui.common.support.config;

import org.springframework.beans.factory.annotation.Value;

public class OnlineConfig {

	@Value("${cc.user.id}")
	public String ccuserId;
	@Value("${cc.api.key}")
	public String ccApiKey;
	@Value("${cc.player.id}")
	public String ccPlayerId;

}
