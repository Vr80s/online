$(function() {
	$("#toResultIpt").keyup(function() {
		var resultIpt = $.trim($("#toResultIpt").val());
		if(resultIpt == null || resultIpt == '' || resultIpt == 0) {
			$(".hdrmb").addClass('hide');
		} else {
			$(".hdrmb").removeClass('hide');
		}
		$("#toResultIptRmb").html(resultIpt / 10);
	});
	getPhoneNumber();

	//if(!localStorage.AnchorsTbl){
	//  	 	$(".school").click();
	//  }else{
	////  	 $(".select_list li[data-name="+localStorage.AnchorsTbl+"]").click();
	//  	 if(localStorage.AnchorsTbl_School){
	//  	 	$('.littleBox .liveP').click();
	//  	 }
	//  }
});
var bankList;
var baseAssetInfo;

function initBasaeAssetInfo() {
	RequestService("/anchor/asset/getBaseAssetInfo", "get", null, function(data) {
		baseAssetInfo = data.resultObject;
		$(".coinBalance").html(data.resultObject.coinBalance);
		$("#toResultIpt").attr("placeholder", "本次最多可结算" + parseInt(data.resultObject.coinBalance) + "熊猫币");
		$(".rmb").html(data.resultObject.rmb);
		$(".bankCount").html(data.resultObject.bankCount);
		$(".hdrmb").addClass('hide');
		$(".amount").val('');
		$(".vcode").val('');
		$("#content_add_name").val('');
		$("#content_add_card").val('');
		$("#content_add_idCard").val('');

	});
	getCoinTransactionList(1);
	getRmbTransactionList(1);
	getBankList();
	getBankCardList();
}

function getCoinTransactionList(current) {
	RequestService("/anchor/asset/getCoinTransactionList?size=10&current=" + current, "get", null, function(data) {
		for(var i = 0; i < data.resultObject.records.length; i++) {
			if(data.resultObject.records[i].VALUE > 0) {
				data.resultObject.records[i].VALUE = "+" + data.resultObject.records[i].VALUE;
			}
		}
		var str = '<thead><tr><td>交易金额（熊猫币）</td><td>交易类型</td><td>商品</td><td>交易时间</td><td>备注</td></tr></thead><tbody id="coin_transaction_list"></tbody></table>';
		$('.content_bottom_bottom > .pandaTable').html(str);
		$("#coin_transaction_list").html(template('coin_transaction_list_tpl', data.resultObject));
		if(!data.resultObject || !data.resultObject.records || data.resultObject.records.length == 0) {
			$('.content_bottom_bottom > .pandaTable').html('<div style="padding-top:40px;text-align:center"><img src="/web/images/nobank.png" alt="" /><p style="font-size:16px;color:#999;margin-top:35px">暂无熊猫币交易记录</p></div>');
		}
		//      debugger
		//每次请求完数据就去渲染分页部分
		if(data.resultObject.pages > 1) { //分页判断
			$(".not-data").remove();
			$(".coin_transaction_pages").css("display", "block");
			$(".coin_transaction_pages .searchPage .allPage").text(data.resultObject.pages);
			$("#Pagination_coinTransaction").pagination(data.resultObject.pages, {
				num_edge_entries: 1, //边缘页数
				num_display_entries: 4, //主体页数
				current_page: current - 1,
				callback: function(page) {
					//翻页功能
					getCoinTransactionList(page + 1);
				}
			});
		} else {
			$(".coin_transaction_pages").css("display", "none");
		}
	});
}

function getRmbTransactionList(current) {
	RequestService("/anchor/asset/getRmbTransactionList?size=10&current=" + current, "get", null, function(data) {
		for(var i = 0; i < data.resultObject.records.length; i++) {
			if(data.resultObject.records[i].value > 0) {
				data.resultObject.records[i].value = "+" + data.resultObject.records[i].value;
			}
			if(data.resultObject.records[i].form == null) {
				data.resultObject.records[i].form = '无';
			}
			if(data.resultObject.records[i].acctPan == null) {
				data.resultObject.records[i].acctPan = '无';
			}
			if(data.resultObject.records[i].status == null) {
				data.resultObject.records[i].status = '无';
			}
			if(data.resultObject.records[i].dismissalRemark == null) {
				data.resultObject.records[i].dismissalRemark = '无';
			}
		}
		var str = '<thead><tr><td>交易金额（人民币）</td><td>交易类型</td><td>申请时间</td><td>提现方式</td><td>提现账户</td><td>状态</td><td>备注</td></tr></thead><tbody id="rmb_transaction_list"></tbody></table>';
		$('.content_bottom_bottom > .rmbTable').html(str);
		$("#rmb_transaction_list").html(template('rmb_transaction_list_tpl', data.resultObject));
		if(!data.resultObject || !data.resultObject.records || data.resultObject.records.length == 0) {
			$('.content_bottom_bottom > .rmbTable').html('<div style="padding-top:40px;text-align:center"><img src="/web/images/nobank.png" alt="" /><p style="font-size:16px;color:#999;margin-top:35px">暂无人民币交易记录</p></div>');
		}

		//银行卡处理
		for(var i = 0; i < $('.bankCard').length; i++) {
			if($('.bankCard').eq(i).text() != '无') {
				$('.bankCard').eq(i).text($('.bankCard').eq(i).text().trim().replace(/^\d{15}/, '***** ***** **** '));
			}
		}
		//      debugger
		//每次请求完数据就去渲染分页部分
		if(data.resultObject.pages > 1) { //分页判断
			$(".not-data").remove();
			$(".rmb_transaction_pages").css("display", "block");
			$(".rmb_transaction_pages .searchPage .allPage").text(data.resultObject.pages);
			$("#Pagination_rmbTransaction").pagination(data.resultObject.pages, {
				num_edge_entries: 1, //边缘页数
				num_display_entries: 4, //主体页数
				current_page: current - 1,
				callback: function(page) {
					//翻页功能
					getRmbTransactionList(page + 1);
				}
			});
		} else {
			$(".rmb_transaction_pages").css("display", "none");
		}
	});
}

//银行卡账号处理
function stringHidePart2(strObj) {
	var strLength = strObj.length;
	var star = '';
	var strRel = '';
	if(strLength > 3) {
		var hideSec = strObj.substring(3); //星号部分
		for(var i = 4; i < hideSec.length; i++) {
			star += "*";
		}
	};
	strRel = strObj.substring(0, 3) + star + strObj.substr(strObj.length - 4);
	// strRel=strRel.replace(/\s/g,'').replace(/(.{4})/g,"$1 ");

	return strRel;
};

function getBankList() {
	if(bankList == null) {
		RequestService("/anchor/asset/getBankList", "get", null, function(data) {
			var str = '';
			bankList = data.resultObject;
			for(var i = 0; i < data.resultObject.length; i++) {
				str += '<option value="' + data.resultObject[i].code + '">' + data.resultObject[i].value + '</option>';
			}
			$("#content_add_bank").html(str);
		});
	} else {
		var str = '';
		for(var i = 0; i < bankList.length; i++) {
			str += '<option value="' + bankList[i].code + '">' + bankList[i].value + '</option>';
		}
		$("#content_add_bank").html(str);
	}
}

function getBankCardList() {
	RequestService("/anchor/asset/getBankCardList?complate=true", "get", null, function(data) {
		for(var i = 0; i < data.resultObject.length; i++) {
			data.resultObject[i].tailNumber = data.resultObject[i].acctPan.substring(17, 21);
			if(data.resultObject[i].default) {
				$("#userNameIpt").val(data.resultObject[i].acctName);
				bankCardId = data.resultObject[i].id;
			}
		}

		$("#bank_card_list").html(template('bank_card_list_tpl', data));
		$("#bank_card").html(template('bank_card_tpl', data));

		//      绑定结果为空
		if(data.resultObject.length == 0 || !data.resultObject) {
			$('.content_Administration .Card_Administration').addClass('hide')
			$('#noBankCard').removeClass('hide');
			//			$('.content_Administration').html('<div style="padding-top:40px;text-align:center"><img src="/web/images/nobank.png" alt="" /><p style="font-size:16px;color:#999;margin-top:35px">暂无银行卡</p></div>');
		} else {
			$('#noBankCard').addClass('hide');
			$('.content_Administration .Card_Administration').removeClass('hide')
		}

		//提现中的银行卡点击选中效果
		$('#mymoney .content_toCash .chooseCard ul li').click(function() {
			$('#mymoney .content_toCash .chooseCard ul li').removeClass('redBorder')
			$(this).addClass('redBorder');
			$("#userNameIpt").val($(this).attr("acctName"));
			bankCardId = $(this).attr("bankCardId");
		})
	});
}

function saveSettlement() {
	$('.waring').addClass('hide');
	var amount = $.trim($("#toResultIpt").val());
	if(amount == null || amount == '' || amount == 0) {
		$(".toResultIpt_warn").removeClass("hide");
		return;
	} else {
		$(".toResultIpt_warn").addClass("hide");
	}
	if(parseInt(amount) > parseInt(baseAssetInfo.coinBalance)) {
		$(".toResultIpt_warn_beyond").removeClass("hide");
		return;
	} else {
		$(".toResultIpt_warn_beyond").addClass("hide");
	}
	RequestService("/anchor/settlement", "post", {
		"amount": amount
	}, function(data) {
		showTip(data.resultObject);
		$("#toResultIpt").val("");
		initBasaeAssetInfo();
		//      getCoinTransactionList(1);
		//      getRmbTransactionList(1);
	});
}

function saveBankCard() {
	var savedata = {};
	savedata.acctName = $.trim($('.content_add #content_add_name').val());
	savedata.acctPan = $.trim($('.content_add #content_add_card').val());
	savedata.tel = $.trim($('.content_add #content_add_bank').val());
	savedata.certId = $.trim($('.content_add #content_add_idCard').val());
	if(verifyBankCard(savedata)) {
		//  	showDel_bank();
		//  	$('#sureDel_bank').click(function(){
		RequestService("/anchor/asset/saveBankCard", "post", savedata, function(data) {
			if(data.success) {
				showTip(data.resultObject);
				//	                hideDel_bank()
				initBasaeAssetInfo();
			} else if(!data.success && data.code == 201) {
				$('#addBankCard .confirm-content').text('您填写的身份信息与主播认证信息不一致，是否继续添加？')
				showDel_bank()
				
				var falg = true
				$('#sureDel_bank').click(function() {
					if(!falg){
						return;
					}
					falg = false;
					savedata.code = 1;
					RequestService("/anchor/asset/saveBankCard", "post", savedata, function(data) {
						falg = true;
						if(data.success) {
							showTip(data.resultObject);
							initBasaeAssetInfo();
							hideDel_bank()
						} else {
							if(data.errorMessage == "无效卡号") {
                                $("#content_add_name").css("border","1px solid #F0F0F0");
                                $("#content_add_card").css("border","2px solid red");
								$("#content_add_card").focus();
							} else if(data.errorMessage == "持卡人认证失败") {
                                $("#content_add_name").css("border","2px solid red");
                                $("#content_add_card").css("border","1px solid #F0F0F0");
								$("#content_add_name").focus();
							}
							showTip(data.errorMessage);
							hideDel_bank()
						}
					},false)
				})

				//	            	  var r=confirm("填写的为其他人的身份证,是否还添加银行卡") 
				//	            	  if (r==true){
				//	            		  savedata.code=1;
				//	            		  RequestService("/anchor/asset/saveBankCard", "post", savedata, function(data) {
				//	          	            if(data.success){
				//	          	            	 showTip(data.resultObject);
				//	         	                 initBasaeAssetInfo();
				//	          	            }else{
				//	          	            	 if(data.errorMessage=="无效卡号"){
				//	                                 $("#content_add_card").focus();
				//	                             }else if(data.errorMessage=="持卡人认证失败") {
				//	                                 $("#content_add_name").focus();
				//	                             }
				//	         	                 showTip(data.errorMessage);
				//	          	            }
				//	            		  }) 
				//	            	  } else{
				//	            		  $("#content_add_name").focus();
				//	            		  //alert("更改账号信息");
				//	            	  }
			} else {
				if(data.errorMessage == "无效卡号") {
                    $("#content_add_name").css("border","1px solid #F0F0F0");
                    $("#content_add_card").css("border","1px solid #FF4012");
                    $('.content_add_card_error').removeClass('hide');
                    $('.content_add_name_error').addClass('hide');
					$("#content_add_card").focus();
				} else if(data.errorMessage == "持卡人认证失败") {
                    $("#content_add_name").css("border","1px solid #FF4012");
                    $("#content_add_card").css("border","1px solid #F0F0F0");
                    $('.content_add_name_error').removeClass('hide');
                    $('.content_add_card_error').addClass('hide');
					$("#content_add_name").focus();
				}
				showTip(data.errorMessage);
				//		                hideDel_bank()
			}
			//	        });
		})
	}
}

function deleteBankCard(id) {
	var title = "移除";
	var content = "确认移除该银行卡？";
	confirmBox(title, content, function(closefn) {
		RequestService("/anchor/asset/deleteBankCard?id=" + id, "get", null, function(data) {
			closefn();
			if(data.success) {
				showTip(data.resultObject);
				initBasaeAssetInfo();
				getBankCardList();
			} else {
				showTip(data.errorMessage);
			}
		});
	});
}

function setDefaultBankCard(id) {
	RequestService("/anchor/asset/setDefaultBankCard?id=" + id, "post", null, function(data) {
		if(data.success) {
			showTip(data.resultObject);
			getBankCardList();
		} else {
			showTip(data.errorMessage);
		}
	});
}

function sendVerificationCode() {
	RequestService("/anchor/sendVerificationCode", "post", null, function(data) {
		if(data.success) {
			//          showTip(data.resultObject);
			$('.phonePwdIpt_warn').addClass('hide');
			//倒计时部分
			var myTime = 60;
			var timer = null;
			timer = setInterval(auto, 1000);

			function auto() {
				myTime--;
				$(".getPassWord").html(myTime + 's');
				$(".getPassWord").removeAttr('onclick');
				$(".getPassWord").css({
					"background": "#dedede",
					"color": "#999999"
				})
				if(myTime == 0) {
					setTimeout(function() {
						clearInterval(timer)
						$(".getPassWord").html('获取验证码');
						$(".getPassWord").attr('onclick', 'btn_cade()');
						$(".getPassWord").css({
							"background": "#00bd12",
							"color": "white"
						})
					}, 1000)
				}
			}

		} else {
			//      	phonePwdIpt_warn 
			//          showTip(data.errorMessage);
			$('.phonePwdIpt_warn').text(data.errorMessage)
			$('.phonePwdIpt_warn').removeClass('hide');
		}
	});

}

function getPhoneNumber() {
	RequestService("/anchor/asset/getPhoneNumber", "get", null, function(data) {
		$("#phoneNumber").html(data.resultObject);
	});
}
var bankCardId;
//提现中的确定按钮点击
function saveEnchashment() {
	var data = {};
	data.amount = $.trim($('.amount').val());
	data.code = $.trim($('.vcode').val());
	data.bankCardId = bankCardId
	if(verifyEnchashment(data)) {
		RequestService("/anchor/enchashment", "post", data, function(data) {
			if(data.success) {
				$('.phonePwdIpt_warn ').addClass('hide')
				showTip(data.resultObject);
				initBasaeAssetInfo();
				window.location.reload();
			} else {
				if(data.errorMessage == "动态码不正确！") {
					$('.phonePwdIpt_warn ').text('动态码不正确');
					$('.phonePwdIpt_warn ').removeClass('hide')
				}
				//              showTip(data.errorMessage);
			}
		});
	}
}

function verifyEnchashment(data) {
	//提现金额
	$('.waring').addClass('hide');
	if(!isNv(data.amount)) {
		$('.warning_amount_null').removeClass('hide');
		return false;
	} else {
		$('.warning_amount_null').addClass('hide');
	}
	if(!numberCk(data.amount) || data.amount <= 0) {
		$('.warning_amount_illegal').removeClass('hide');
		return false;
	} else {
		$('.warning_amount_illegal').addClass('hide');
	}
	if(parseInt(baseAssetInfo.rmb) < parseInt(data.amount)) {
		$('.warning_amount_beyond').removeClass('hide');
		return false;
	} else {
		$('.warning_amount_beyond').addClass('hide');
	}
	//户名
	if(!isNv(data.bankCardId)) {
		$('.warning_bank_card_id').removeClass('hide');
		return false;
	} else {
		$('.warning_bank_card_id').addClass('hide');
	}
	//手机验证码
	if(!isNv(data.code)) {
		$('.phonePwdIpt_warn').text('请输入手机验证码')
		$('.phonePwdIpt_warn').removeClass('hide');
		return false;
	} else {
		$('.phonePwdIpt_warn').addClass('hide');
	}
	return true;
}

function verifyBankCard(data) {

	//验证
	//户名
	if(!isNv(data.acctName)) {
		$('.content_add_name_warn').removeClass('hide');
		$("#content_add_name").focus();
		return false;
	} else {
		$('.content_add_name_warn').addClass('hide');
	}

	//卡号
	if(!isNv(data.acctPan)) {
		$('.content_add_card_warn').removeClass('hide');
		$("#content_add_card").focus();
		return false;
	} else {
		$('.content_add_card_warn').addClass('hide');
	}

	//选择银行
	if(!isNv(data.tel)) {
		$('.content_add_bank_warn').removeClass('hide');
		return false;
	} else {
		$('.content_add_bank_warn').addClass('hide');
	}

	//身份证号
	if(!isNv(data.certId)) {
		$('.content_add_idCard_warn').removeClass('hide');
		$("#content_add_idCard").focus();
		return false;
	} else {
		$('.content_add_idCard_warn').addClass('hide');
	}
	//身份证号
	if(!isCardID(data.certId)) {
		$('.content_add_idCard_gs_warn').removeClass('hide');
		return false;
	} else {
		$('.content_add_idCard_gs_warn').addClass('hide');
	}
	return true;
}

function isNv(v) {
	if(v == null || v == '') {
		return false;
	}
	return true;
}