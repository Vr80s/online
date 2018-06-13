/**
 * 
 */
var sid;
$(document)
		.ready(
				function() {
					// 点击改变礼物数量开始

					/* 点击100 */
					$(".import1").click(function() {
						$(".import").val(100);
						$(".import_hide").hide();
						$(".import").blur();
					});

					/* 点击50 */
					$(".import2").click(function() {
						$(".import").val(50);
						$(".import_hide").hide();
						$(".import").blur();
					});

					/* 点击10 */
					$(".import3").click(function() {
						$(".import").val(10);
						$(".import_hide").hide();
						$(".import").blur();
					});

					/* 点击1 */
					$(".import5").click(function() {
						$(".import").val(1);
						$(".import_hide").hide();
						$(".import").blur();
					});

					/* 点击礼物数量后面input框 */
					$(".import_div").click(function() {
						$(".import_hide").show();
						/* $(".import").val(1); */
						$(".import").blur();
					});

					/* 点击礼物 出现礼物数量 */
					$(".gift_ul_li li").click(function() {
//						$(".count").show();    选择礼物数量  礼物选择数量暂时没有。先隐藏掉
						$(".import").val(1);
						/* $(".import").blur(); */
					});

					/* 点击其他数量 */
					$(".import4").click(function() {
						$(".import").focus();
						$(".import_hide").hide();
						$(".import").val("");
					});

					/* 点击礼物关闭按钮 */
					$(".send_gifts_p1").click(function() {
						$(".count").hide();
						$(".import_hide").hide();
						$(".import").val(1);
						$(".gift_ul_li li").removeClass('gift_ul_li_li');
					});

					// 点击改变礼物数量结束

					$(".li1").click(
							function() {
								$(this).addClass('details');
								$(".li2").removeClass('details');
								$(".li3").removeClass('details');
								$(".li4").removeClass('details');
								$(".coze").show();
								$(".coze_bottom").show();
								$(".details1").hide();
								$(".document").hide();
								$(".leaderboard").hide();

								$(this).parent().addClass('details01');
								$(".li2").parent().removeClass('details01');
								$(".li3").parent().removeClass('details01');
								$(".li4").parent().removeClass('details01');

								setTimeout(function() {
									$(".chatmsg-box").mCustomScrollbar(
											"scrollTo", "bottom", "0");
								}, 50);
							});
					$(".li2")
							.click(
									function() {
										$(this).addClass('details');
										$(".li1").removeClass('details');
										$(".li3").removeClass('details');
										$(".li4").removeClass('details');
										$(this).parent().addClass('details01');
										$(".li1").parent().removeClass(
												'details01');
										$(".li3").parent().removeClass(
												'details01');
										$(".li4").parent().removeClass(
												'details01');
										$(".details1").show();
										$(".coze").hide();
										$(".document").hide();
										$(".leaderboard").hide();
										$(".details_chat").hide();
										$(".coze_bottom").hide();
										$(".coze_bottom").css("bottom", "0");
										$(".facebox-mobile").hide();
										$(".face_img01")
												.css("background",
														"url(/xcview/images/face.png) no-repeat");
										$(".face_img01").css("background-size",
												"100% 100%");
									});
					$(".li3")
							.click(
									function() {
										$(this).addClass('details');
										$(".li1").removeClass('details');
										$(".li2").removeClass('details');
										$(".li4").removeClass('details');
										$(this).parent().addClass('details01');
										$(".li2").parent().removeClass(
												'details01');
										$(".li1").parent().removeClass(
												'details01');
										$(".li4").parent().removeClass(
												'details01');
										$(".leaderboard").show();
										$(".coze").hide();
										$(".details1").hide();
										$(".document").hide();
										$(".details_chat").hide();
										$(".coze_bottom").hide();
										$(".coze_bottom").css("bottom", "0");
										$(".facebox-mobile").hide();
										$(".face_img01")
												.css("background",
														"url(/xcview/images/face.png) no-repeat");
										$(".face_img01").css("background-size",
												"100% 100%");

										// 刷新礼物排行榜
										refreshGiftRanking();
									});
					$(".li4")
							.click(
									function() {
										if( $('#doc img').attr('src') ){
									    	 $(".fd_img").css("display","none");
									    	$(".pinch-zoom-container").css("display","black");
									    	// $(".document").css("display","black");
									   // 不为空

										}else{
											 $(".fd_img").css("display","black");
											$(".pinch-zoom-container").css("display","none");
											// $(".document").css("display","none");
										// 为空

										}

										$(this).addClass('details');
										$(this).parent().addClass('details01');

										$(".li1").parent().removeClass(
												'details01');
										$(".li2").parent().removeClass(
												'details01');
										$(".li3").parent().removeClass(
												'details01');
										$(".li2").removeClass('details');
										$(".li3").removeClass('details');
										$(".li1").removeClass('details');
										$(".document").show();
										$(".coze").hide();
										$(".details1").hide();
										$(".leaderboard").hide();
										$(".details_chat").hide();
										$(".coze_bottom").hide();
										$(".coze_bottom").css("bottom", "0");
										$(".facebox-mobile").hide();
										$(".face_img01")
												.css("background",
														"url(/xcview/images/face.png) no-repeat");
										$(".face_img01").css("background-size",
												"100% 100%");
									});
					$(".li1").click();

					$(".close").click(function() {
						$(".details_chat").hide();
					});

					$(".order01").click(function() {
						if ($(this).html() == '您已预约') {
							return;
						}
						$(".buy_bottom_bg").show();
						$(".buy_bg").show();
						$(".buy_center").show();
					});
					$(".order02").click(function() {
						$(".buy_bg").show();
						// $(".buy_center1").show();
						// $(".buy_center").hide();
					});
					$(".order03").click(function() {
						$(".buy_bg").hide();
						$(".buy_center1").hide();
						$(".buy_center").hide();
					});
					$(".order_close").click(function() {
						$(".buy_bottom_bg").hide();
					});

					$(".give_lable2").click();

					/* 点击头部隐藏底部 */
					/* details_cen1 */

					// 关闭打赏
					$(".give_footer_span").click(function() {
						$(".give_bottom").hide();

						if (lineState != 2) {//
							$(".send_img").css('right', '3.2rem');
						} else {
							// 发送按钮
							// $(".send_img").css('right','0.4rem');
						}

						/* $("#sendChat").css('right','2.4rem'); */
					});
					// 打开打赏
					$(".give_a1_span02").click(
							function() {
								$(".give_bottom").show();

								// 发送按钮 coze_cen
								$(".send_img").css('right', '0.4rem');
								$(".coze_bottom").css("bottom", "0");
								$(".face_img01").css("background",
										"url(/xcview/images/face.png)");
								$(".face_img01").css("background-size",
										"100% 100%");
							});

					$(".coze").click(
							function() {
								$(".face_img01").css("background",
										"url(/xcview/images/face.png)");
								$(".face_img01").css("background-size",
										"100% 100%");
							});

					if (result.lineState != 3) { // 直播走里面

						// 点击发送

						$("#sendChat")
								.click(
										function() {
											// $(".poson").css("right","0rem");

											$(".coze_bottom").css('bottom',
													'0rem');
											/* $("#mywords").css('width','11.9rem'); */

											/* 礼物状态点击发送 */

											/* 礼物显示 */
											/* $(".give_a1").show(); */
											/* 礼物显示图片 */
											/* $(".give_a1_img").show(); */

											/* 隐藏表情和打赏 */
											/* $(".give_a01").hide(); */

											$(".face_img01")
													.css('background',
															'url(/xcview/images/face.png) no-repeat');
											$(".face_img01").css(
													'background-size',
													'100% 100%');
											/*
											 * $(".send_img").css('background','url(/xcview/images/jiantou02.jpg)
											 * no-repeat');
											 */
											$(".send_img").css(
													'background-size',
													'100% 100%');
											/* $(".give_a1_span02").show(); */
											/* $("#mywords").css("width","11.9rem"); */
											// $(".send_img").css('right','0.4rem');
										});
					}

//					添加判断浏览器的分享

					if(is_weixin()){
						// 点击分享
						$(".history_img_right").click(function() {
							$(".weixin_ceng").show();
						});
						
						//点击微信显示黑色指示背景
						$(".weixin_ceng").click(function() {
							$(".weixin_ceng").hide();
						});
						
					}else{
						
						$(".history_img_right").click(function() {
							$(".share").show();
						});
						
						$(".share_footer_call").click(function() {
							$(".share").hide();
						});
						
	//					点击取消
						$(".share_cancel").click(function() {
							$(".share").hide();
						});
						
	//					点击微信显示黑色指示
						/*$(".share_to_one").click(function() {
							$(".weixin_ceng").show();
						});*/
	
					}





					
					
					
					$(".gift_ul_li").on(
							"click",
							"li",
							function() {
								$(this).addClass('gift_ul_li_li').siblings()
										.removeClass('gift_ul_li_li');
							});

					/* 点击 发送 */

					/*
					 * $(".give_a1_span").click(function() {
					 * $("#mywords").css('width','11.8rem'); });
					 */
					/*
					 * $("#face").click(function() {
					 * $("#mywords").css('width','11.8rem'); });
					 */

					/* 微信 */
					$(".weixin_li").click(function() {
						$(".weixin_ceng").show();
					});
					$(".weixin_ceng").click(function() {
						$(this).hide();
					});
					$(".send_gifts_div").click(function() {
						$(".send_gifts").hide();
					});

					/* 点击礼物 */
					$(".give_a1").click(
							function() {
								$(".send_gifts").show();
								$(".coze_bottom").css("bottom", "0");
								$(".face_img01").css("background",
										"url(/xcview/images/face.png)");
								$(".face_img01").css("background-size",
										"100% 100%");
							});

					// 点击视频上部隐藏发送，展示礼物
					$(".details_cen1").click(function() {

						// 隐藏表情
						/* $(".give_a01").hide(); */

						// 改变input宽度值
						/* $(".textarea02").css("width","11.9rem"); */

						// 让input靠左
						$(".poson").css("right", "0");

						// 显示打赏
						/* $(".give_a1_span02").show(); */

						// 显示礼物
						/* $(".give_a1_img").show(); */
					});

					// 点击视频上部隐藏发送，展示礼物 vhall-h5-player
					$("#video").click(function() {

						// 隐藏表情
						/* $(".give_a01").hide(); */

						// 改变input宽度值
						/* $(".textarea02").css("width","11.9rem"); */

						/* 底部为0 */
						$(".coze_bottom").css("bottom", "0");

						// 让input靠左
						$(".poson").css("right", "0");

						// 显示打赏
						/* $(".give_a1_span02").show(); */

						// 显示礼物
						/* $(".give_a1_img").show(); */
					});

					$("#video video").click(function() {

						// 隐藏表情
						/* $(".give_a01").hide(); */

						// 改变input宽度值
						/* $(".textarea02").css("width","11.9rem"); */

						/* 底部为0 */
						$(".coze_bottom").css("bottom", "0");

						// 让input靠左
						// $(".poson").css("right","0");

						// 显示打赏
						/* $(".give_a1_span02").show(); */

						// 显示礼物
						/* $(".give_a1_img").show(); */
					});

					/* 点击视频区域 */
					$(".chatmsg-box").click(function() {
						// 隐藏表情
						/* $(".give_a01").hide(); */

						// 改变input宽度值
						/* $(".textarea02").css("width","11.9rem"); */

						/* 底部为0 */
						$(".coze_bottom").css("bottom", "0");

						// 让input靠左
						// $(".poson").css("right","0");

						// 显示打赏
						/* $(".give_a1_span02").show(); */

						// 显示礼物
						/* $(".give_a1_img").show(); */
					});

					// 打赏
					$(".give_a1_span02").click(function() {
						/* $(".facebox-mobile").hide(); */
						$(".coze_bottom").css("bottom", "0rem");
					});
					/* 关闭打赏 */
					$(".give_close01").click(function() {
						$(".give_bottom").hide();
						// $(".send_img").css("right","0.4rem");
					});

					// 文本域
					$("#mywords")
							.click(
									function() {
										$(".coze_bottom").css("bottom", "0rem");
										$(".give_a1_span").show();
										$(".give_a1").show();
										// $(".give_a1_img").show();

										/*
										 * $(".send_img").css('background','url(/xcview/images/jiantou01.jpg)
										 * no-repeat');
										 */
										$(".send_img").css('background-size',
												'100% 100%');

										$(".face_img01")
												.css('background',
														'url(/xcview/images/face.png) no-repeat');
										$(".face_img01").css('background-size',
												'100% 100%');

										// 这个是点击input时，隐藏打赏表情
										/* $(".give_a1_span02").hide(); */

										// 发送send_img
										$(".send_img").css("right", "0.1rem;");

									});

					// 发送
					$(".send_img").click(function() {
						if (lineState == 1) {// zhi'bo
							$(".coze_bottom").css("bottom", "0rem");
							/* $(".give_a1_span02").show(); */
							/* $("#face").hide(); */
							/* $("#mywords").css('width','11.8rem'); */
							$(".send_img").css("right", "0.2rem");
							/* $("#mywords").css("width","11.9rem"); */
							/* $(".give_a1_img").show(); */
						}
					});

					// 点击表情
					$("#face")
							.click(
									function() {
										/* $("#mywords").css('width','13.65rem'); */
										$(".coze_bottom").css("bottom",
												"7.3rem");
										$(".face_img01")
												.css('background',
														'url(/xcview/images/jianpan.png) no-repeat');
										$(".face_img01").css('background-size',
												'100% 100%');

										/*
										 * $("#face").css('background','url(/xcview/images/face.png)
										 * no-repeat');
										 * $("#face").css('background-size','100%
										 * 100%');
										 */
										/* $(".facebox-mobile").css("display","block"); */
										var a = $(".facebox-mobile").css(
												"display");
										if (a == "block") {

											setTimeout(
													function() {
														$(".facebox-mobile")
																.hide();
														$(".face_img01")
																.css(
																		'background',
																		'url(/xcview/images/face.png) no-repeat');
														$(".face_img01")
																.css(
																		'background-size',
																		'100% 100%');
													}, 10);

											$(".coze_bottom").css("bottom",
													"0rem");
										} else {
											$(".coze_bottom").css("bottom",
													"7.3rem");

											setTimeout(function() {
												$(".facebox-mobile").show();
											}, 10);
										}
									});

					// 打赏使用
					var aBtn6 = $('#cdPrices .give_lable1');
					for (i = 0; i < aBtn6.length; i++) {

						$(aBtn6[i]).click(
								function() {
									for (i = 0; i < aBtn6.length; i++) {
										$(aBtn6[i]).removeClass('givebg');
										$(aBtn6[i]).find('.give_money1').css(
												'color', '#000');
										$(aBtn6[i]).find('.give_lable1_span')
												.removeClass(
														'give_lable1_span1');
									}
									$(this).addClass('givebg');
									$(this).find('.give_money1').css('color',
											'#fff');
									$(this).find('.give_lable1_span').addClass(
											'give_lable1_span1');
								})
					}
				});

/**
 * 点击事件  
 */
function clearCheck() {
	var aBtn6 = $('#cdPrices .give_lable1');
	for (i = 0; i < aBtn6.length; i++) {
		for (i = 0; i < aBtn6.length; i++) {
			$(aBtn6[i]).removeClass('givebg');
			$(aBtn6[i]).find('.give_money1').css('color', '#000');
			$(aBtn6[i]).find('.give_lable1_span').removeClass(    
					'give_lable1_span1');
		}
	}
	$("#sid").val(sid);
}