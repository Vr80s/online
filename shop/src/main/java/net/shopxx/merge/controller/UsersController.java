/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 * FileId: MvRfpDi1EfzXsgGQ+Ed73dmsq60Qn+Gt
 */
package net.shopxx.merge.controller;

import net.shopxx.controller.member.BaseController;
import net.shopxx.entity.Member;
import net.shopxx.merge.service.UsersRelationService;
import net.shopxx.merge.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * 接口测试类  非业务接口！！！！！！！！！！！！！！！！！！！
 */
@Controller
@RequestMapping("/user")
public class UsersController extends BaseController {

	@Inject
	private UsersRelationService usersRelationService;

	@GetMapping("/save")
	@ResponseBody
	public void save(String ipandatcmUserId) {
		usersRelationService.saveUserRelation(ipandatcmUserId);
	}

	@GetMapping("/get")
	@ResponseBody
	public Member get(String ipandatcmUserId) {
		Member member = usersRelationService.getMemberByIpandatcmUserId(ipandatcmUserId);
		return member;
	}

}