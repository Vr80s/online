$(function() {

	/*----------------------------		我的资产部分js		--------------------------------*/

	//底部固定表单部分点击人民币熊猫币切换效果
	//点击人民币按钮
	$('#mymoney .rmbMoney ').click(function() {
		//变色
		$('#mymoney .pandaMoney').removeClass('orange')
		$(this).addClass('orange');

		//变表单
		$('#mymoney .pandaTable').addClass('hide');
		$('#mymoney .rmbTable').removeClass('hide');

	})

	//点击熊猫币按钮
	$('#mymoney .pandaMoney').click(function() {
		//变色
		$('#mymoney .rmbMoney').removeClass('orange')
		$(this).addClass('orange');

		//变表单
		//变表单
		$('#mymoney .rmbTable').addClass('hide');
		$('#mymoney .pandaTable').removeClass('hide');
	})

	//顶部的几个绿色按钮点击事件
	//结算按钮点击出现结算结构
	$('#mymoney .Settlement').click(function() {
		//表单重置
		content_SettlementReset();
		//显示对应表单内容
		showContent('content_Settlement', $(this).text());

	})

	//结算中的确定按钮点击
	$('#mymoney .content_Settlement .content_Settlement_btn').click(function() {
		var toResultNum = $.trim($('.content_Settlement #toResultIpt').val());
		//验证
		if(toResultNum == '') {
			$('.toResultIpt_warn').removeClass('hide');
			return false;
		} else {
			$('.toResultIpt_warn').addClass('hide');
		}

	})

	//提现按钮点击出发事件
	$('#mymoney .toCash').click(function() {
		showContent('content_toCash', $(this).text())
	})

	//提现中银行卡设置默认
	$('#mymoney .content_toCash .chooseCard .cardBottom a').click(function(event) {
		//		$('#mymoney .content_toCash .chooseCard .cardBottom a').text('设为默认');
		//		$(this).text('取消默认');
		if($(this).text() == '取消默认') {
			$(this).text('设为默认');
		} else if($(this).text() == '设为默认') {
			$(this).text('取消默认')
		}
		event.stopPropagation();

	})

	//管理按钮点击触发事件
	$('#mymoney .administration').click(function() {
		showContent('content_Administration', $(this).text())
		getBankCardList()
	})

	//新增银行卡
	$('#mymoney .addNew').click(function() {
		showContent('content_add', $(this).text())
	})

	/*@点击顶部的按钮对应的中间变动结构显示出来
	 *@输入参数1为对应显示部分的模块类名
	 *@输入参数2为点击的按钮的名字
	 * */
	function showContent(contentName, btnName) {
		$('#toCashTip').addClass('hide')
		//判断隐藏部分的头部标题名字
		var contentTopName;
		if(btnName == '管理') {
			contentTopName = '银行卡管理'
		} else if(btnName == '新增') {
			contentTopName = '新增银行卡'
		} else if(btnName == '提现') {
			contentTopName = "提现"
			$('#toCashTip').removeClass('hide')
		} else {
			contentTopName = btnName
		}
		$('#mymoney .content_mid > div').addClass('hide');
		$('#mymoney .content_mid').removeClass('hide');
		$('.content_mid .content_mid_top .title').text(contentTopName);
		$(".content_mid ." + contentName + "").removeClass('hide');
	}

	/*@中间变化结构隐藏方法
	 */
	function midHide() {
		$('.content_mid').addClass('hide');
	}

	//结算的重置部分
	function content_SettlementReset() {
		$('.content_Settlement #toResultIpt').val('');
		$('.toResultIpt_warn').addClass('hide');
	}

	/*-----------------------------		我的收益部分js		--------------------------------*/

	//	礼物收益部分排行榜切换
	//
	$('.toRankingList').click(function() {
		rankcount *= -1;
		if(rankcount == -1) {
			$(this).text('返回');
			$(this).siblings('.title').text('排行榜');
			//底部列表的变化
			$('.gift_Resive_mid').addClass('hide');
			$('.gift_Resive_bottom').addClass('hide');
			$('.gift_Resive_bottom2').removeClass('hide');
		} else {
			$(this).text('排行榜');
			$(this).siblings('.title').text('礼物订单');
			$(this).addClass('hide')
			//底部列表的变化
			$('.gift_Resive_bottom2').addClass('hide');
			$('.gift_Resive_mid').removeClass('hide');
			$('.gift_Resive_bottom').removeClass('hide');
		}
	})

	//课程订单赛选部分渲染
	//	RequestService("/menu/getAllMenu?type=1", "GET", "", function (data) {
	// 		 $("#order_List").html(template('orderListTpl', {item:data.resultObject}));
	//	});
	//	

});

//	礼物收益部分排行榜切换
var rankcount = 1;

function rankList(liveId) {
	rankcount = -1;
	$(".toRankingList").text('返回');
	$(".gift_Resive_top .title").text('排行榜');
	$(".toRankingList").removeClass('hide');
	//底部列表的变化
	$('.gift_Resive_mid').addClass('hide');
	$('.gift_Resive_bottom').addClass('hide');
	$('.gift_Resive_bottom2').removeClass('hide');

	//调用排行榜的分页
	getRankingList(1, liveId);

}

//排行榜分页列表

function getRankingList(current, liveId) {
	RequestService("/medical/order/gift/rankingList?size=10&current=" + current + "&liveId=" + liveId, "get", null, function(data) {
		for(var i = 0; i < data.resultObject.records.length; i++) {
			if(data.resultObject.records[i].VALUE > 0) {
				data.resultObject.records[i].VALUE = "+" + data.resultObject.records[i].VALUE;
			}
		}
		$("#myRanking_list").html(template('myRanking_list_Tpl', data.resultObject));
		// debugger
		//每次请求完数据就去渲染分页部分
		if(data.resultObject.pages > 1) { //分页判断
			$(".not-data").remove();
			$(".RankingList_page").css("display", "block");
			$(".RankingList_page .searchPage .allPage").text(data.resultObject.pages);
			$("#Pagination_RankingList").pagination(data.resultObject.pages, {
				num_edge_entries: 1, //边缘页数
				num_display_entries: 4, //主体页数
				current_page: current - 1,
				callback: function(page) {
					//翻页功能
					getRankingList(page + 1, liveId);
				}
			});
		} else {
			$(".RankingList_page").css("display", "none");
		}
	});
}

//课程收益列表
//	getCourseResiveList (1);
function getCourseResiveList(current, gradeName, startTime, endTime) {
	if(gradeName == undefined) {
		gradeName = ''
	}
	if(startTime == undefined) {
		startTime = ''
	}
	if(endTime == undefined) {
		endTime = ''
	}
	var courseForm;
	var multimediaType;
	var courseType = $("#order_List").val();
	if(courseType == 1) {
		courseForm = 1;
	} else if(courseType == 2) {
		courseForm = 2;
		multimediaType = 1;
	} else if(courseType == 3) {
		courseForm = 3;
	} else if(courseType == 4) {
		courseForm = 2;
		multimediaType = 2;
	}
	var url = "/medical/order/course/list?size=10&current=" + current + "&gradeName=" + gradeName + "&startTime=" + startTime + "&endTime=" + endTime;
	if(courseForm != null) {
		url += "&courseForm=" + courseForm;
	}
	if(multimediaType != null) {
		url += "&multimediaType=" + multimediaType;
	}

	RequestService(url, "get", null, function(data) {
		if(data.success == false) {
			$('#blackTip').text(data.errorMessage);
			$('#blackTip').toggle();
			setTimeout(function() {
				$('#blackTip').toggle();
			}, 2000)
			return false;
		}
		for(var i = 0; i < data.resultObject.records.length; i++) {
			if(data.resultObject.records[i].VALUE > 0) {
				data.resultObject.records[i].VALUE = "+" + data.resultObject.records[i].VALUE;
			}
		}
		$("#myResive_list").html(template('myResive_list_Tpl', data.resultObject));

		if(!data.resultObject || data.resultObject.records.length == 0 || !data.resultObject.records) {
			//      	$('.kecheng_Resive_bottom').html('<div style="padding-top:100px;text-align:center"><img src="/web/images/nomoney.png" alt="" /><p style="font-size:16px;color:#999;margin-top:35px">暂无记录</p></div>');
			$('.kecheng_Resive_bottom > table').addClass('hide')
			$('.kecheng_Resive_bottom > div').removeClass('hide')
		} else {
			$('.kecheng_Resive_bottom > div').addClass('hide')
			$('.kecheng_Resive_bottom > table').removeClass('hide')
		}

		// debugger
		//每次请求完数据就去渲染分页部分
		if(data.resultObject.pages > 1) { //分页判断
			$(".not-data").remove();
			$(".courseResive_pages").css("display", "block");
			$(".courseResive_pages .searchPage .allPage").text(data.resultObject.pages);
			$("#Pagination_myResive").pagination(data.resultObject.pages, {
				num_edge_entries: 1, //边缘页数
				num_display_entries: 4, //主体页数
				current_page: current - 1,
				callback: function(page) {
					//翻页功能
					getCourseResiveList(page + 1, gradeName, startTime, endTime);
				}
			});
		} else {
			$(".courseResive_pages").css("display", "none");
		}
	});
}

//	 礼物收益列表
//	getGiftResiveList (1);
function getGiftResiveList(current, gradeName, startTime, endTime) {
	if(gradeName == undefined) {
		gradeName = ''
	}
	if(startTime == undefined) {
		startTime = ''
	}
	if(endTime == undefined) {
		endTime = ''
	}
	RequestService("/medical/order/gift/list?size=10&current=" + current + "&gradeName=" + gradeName + "&startTime=" + startTime + "&endTime=" + endTime, "get", null, function(data) {
		if(data.success == false) {
			$('#blackTip').text(data.errorMessage);
			$('#blackTip').toggle();
			setTimeout(function() {
				$('#blackTip').toggle();
			}, 2000)
			return false;
		}
		for(var i = 0; i < data.resultObject.records.length; i++) {
			if(data.resultObject.records[i].VALUE > 0) {
				data.resultObject.records[i].VALUE = "+" + data.resultObject.records[i].VALUE;
			}
		}

		$("#gift_Resive_list").html(template('gift_Resive_Tpl', data.resultObject));

		//没事有搜索结果
		if(!data.resultObject || data.resultObject.records.length == 0 || !data.resultObject.records) {
			$('.gift_Resive_bottom > table').addClass('hide');
			$('#noGiftList').removeClass('hide');
			//      	$('.gift_Resive_bottom').html('<div style="padding-top:100px;text-align:center"><img src="/web/images/nomoney.png" alt="" /><p style="font-size:16px;color:#999;margin-top:35px">暂无记录</p></div>');
		} else {
			$('#noGiftList').addClass('hide');
			$('.gift_Resive_bottom > table').removeClass('hide');
		}
		// debugger
		//每次请求完数据就去渲染分页部分
		if(data.resultObject.pages > 1) { //分页判断
			$(".not-data").remove();
			$(".giftResive_pages").css("display", "block");
			$(".giftResive_pages .searchPage .allPage").text(data.resultObject.pages);
			$("#Pagination_myGiftResive").pagination(data.resultObject.pages, {
				num_edge_entries: 1, //边缘页数
				num_display_entries: 4, //主体页数
				current_page: current - 1,
				callback: function(page) {
					//翻页功能
					getGiftResiveList(page + 1, gradeName, startTime, endTime);
				}
			});
		} else {
			$(".giftResive_pages").css("display", "none");
		}
	});
}

//条件搜索课程收益列表
function searchCourseResiveList() {
	//	console.log($('.search_classIpt').val(),$('.Order_start_time').val(),$('.Order_end_time').val())
	getCourseResiveList(1, $('.classSearch_Name').val(), $('.Order_start_time').val(), $('.Order_end_time').val());
}

//条件搜索礼物收益列表
function searchgiftResiveList() {
	//	console.log($('.search_classIpt').val(),$('.Order_start_time').val(),$('.Order_end_time').val())
	getGiftResiveList(1, $('.giftSearch_Name').val(), $('.Gift_start_time').val(), $('.Gift_end_time').val());
}