$(function() {
//左右侧边栏mune功能
	$('#accordion > li').click(function(){
		    $('#right-content > div').addClass('hide');
			$('#right-content > div').eq($(this).index()).removeClass('hide');
	})
	$('#accordion > li >.submenu >li').click(function(e){
		$('#accordion > li >.submenu >li a').removeClass("mune-active")
		$(this).find("a").addClass("mune-active")
		e.stopPropagation();
		
		var index = $(this).parent().parent().index();
		$('#right-content > div:eq('+index+') > div').addClass('hide')
		$('#right-content > div:eq('+index+') > div').eq($(this).index()).removeClass('hide');
	})
	var Accordion = function(el, multiple) {
		this.el = el || {};
		this.multiple = multiple || false;

		// Variables privadas
		var links = this.el.find('.link');
		// Evento
		links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
	}

	Accordion.prototype.dropdown = function(e) {
		var $el = e.data.el;
			$this = $(this),
			$next = $this.next();
//			判断若是有下拉子菜单,则给第一个菜单加点击
			if($(this).siblings(".submenu").find("li").length>0){
				$(this).siblings(".submenu").find("li").first().click();
				$next.slideToggle();
//				小箭头方向
//				$el.find('.submenu').not($next).parent().find("span").removeClass('glyphicon-triangle-bottom');			
//				$this.parent().find("span").toggleClass('glyphicon-triangle-bottom');
			}else{
				$el.find('.submenu').not($next).parent().find("span").removeClass('glyphicon-triangle-bottom');			
			}
			$this.parent().toggleClass('open');
			if (!e.data.multiple) {
				$el.find('.submenu').not($next).slideUp().parent().removeClass('open');
//				子菜单加颜色
				$el.find('.submenu').not($next).slideUp().parent().find(".submenu").find("a").removeClass("mune-active")
				
			};
	}	
	var accordion = new Accordion($('#accordion'), false);

//--------------------------------------左右侧边栏mune功能结束--学习中心开始--------------------------------------------

//暂无记录背景图
var noDataImg= '<div class="no-data-img">'+
					'<img src="../../images/icon-nodata.png"/>'+
				'</div>'+
				'<p>暂无记录</p>';
				
//已购/结束课程/历史记录选项卡
$(".my-class-nav li").click(function(){
	$(".my-class-nav li").removeClass("class-active");
	$(this).addClass("class-active");
	$(".save-class").addClass("hide").eq($(this).index()).removeClass("hide");
})
//播放历史
historyClass(1)
function historyClass(pages){
 RequestService("/history/list", "POST",{
 	pageNumber:pages,
 	pageSize:8
 }, function (data) {
 		if(data.success==true){
            if (data.resultObject.records.length == 0) {
            	$(".clear-history").addClass("hide")
            	$(".nodata-history").html(noDataImg).removeClass("hide");
            } else {
            	$(".clear-history").removeClass("hide");
            	$(".nodata-history").addClass("hide");  
            }
            	$("#history-template").html(template("content-history",{items:data.resultObject.records}))
            
            //分页添加
			if(data.resultObject.pages > 1) { //分页判断
					$(".not-data").remove();
		            $(".history_pages").removeClass("hide");
		            $(".history_pages .searchPage .allPage").text(data.resultObject.pages);
		            $("#history_doctors").pagination(data.resultObject.pages, {
		                num_edge_entries: 1, //边缘页数
		                num_display_entries: 4, //主体页数
		                current_page:pageNumber-1,
		                callback: function (page) {
		                    //翻页功能
		                    recruitList(page+1);
		                }
		            });
				}
				else {
					$(".history_pages").addClass("hide");
				}
        }else{
        	showTip("获取数据失败");
        }
    })
}
//删除历史
$(".clear-history").click(function(){
	 RequestService("/history/empty", "POST",null, function (data) {
   	if(data.success==true){
   		showTip(data.resultObject);
   		historyClass(1)
   	}else{
   		showTip("清空失败")		
   	}
   })
})
//已购课程
buyClass(1)
function buyClass (pages){
 RequestService("/userCourse/freeCourseList", "POST",{
 	pageNumber:pages,
 	pageSize:8
 }, function (data) {
 		if(data.success==true){
            if (data.resultObject.records.length == 0) {
            	$(".nodata-buyclass").html(noDataImg).removeClass("hide");
            } else {
            	$(".nodata-buyclass").addClass("hide");          	
            	$("#buy-template").html(template("purchased-class",{items:data.resultObject.records}))
            }
               //分页添加
			if(data.resultObject.pages > 1) { //分页判断
					$(".not-data").remove();
		            $(".buy_pages").removeClass("hide");
		            $(".buy_pages .searchPage .allPage").text(data.resultObject.pages);
		            $("#buy_doctors").pagination(data.resultObject.pages, {
		                num_edge_entries: 1, //边缘页数
		                num_display_entries: 4, //主体页数
		                current_page:pages-1,
		                callback: function (page) {
		                    //翻页功能
		                    buyClass(page+1)
		                }
		            });
				}
				else {
					$(".buy_pages").addClass("hide");
				}
        }else{
        	showTip("获取数据失败");
        }       
     })
	
}
//结束课程
endClass(1)
function endClass(pages){
 RequestService("/userCourse/myCourseType", "POST",{
 	pageNumber:pages,
 	pageSize:8,
 	type:2
 }, function (data) {
 		if(data.success==true){
            if (data.resultObject.records.length == 0) {
            	$(".nodata-endclass").html(noDataImg).removeClass("hide");
            } else {
            	$(".nodata-endclass").addClass("hide");          	
            }
            	$("#end-template").html(template("end-class",{items:data.resultObject.records}))
            
                //分页添加
			if(data.resultObject.pages > 1) { //分页判断
					$(".not-data").remove();
		            $(".end_pages").removeClass("hide");
		            $(".end_pages .searchPage .allPage").text(data.resultObject.pages);
		            $("#end_doctors").pagination(data.resultObject.pages, {
		                num_edge_entries: 1, //边缘页数
		                num_display_entries: 4, //主体页数
		                current_page:pages-1,
		                callback: function (page) {
		                    //翻页功能
		                    endClass(page+1)
		                }
		            });
				}
				else {
					$(".end_pages").addClass("hide");
				}
        }else{
    		showTip("获取数据失败");
    	}       
     })
}
//我关注的主播
 RequestService("/focus/myFocus", "POST",null, function (data) {
 		if(data.success==true){
            if (data.resultObject.length == 0) {
            	$(".no-follow-anchor").removeClass("hide");
            } else {
            	$(".no-follow-anchor").addClass("hide");          	
            	$("#anchor-template").html(template("anchor-box",{items:data.resultObject}))
            }
          }else{
    		showTip("获取数据失败");
    	}   
     })


//--------------------------------------学习中心结束--问答论坛开始--------------------------------------------

//我的提问  我的回答选项卡
$(".question-forum li").click(function(){
	$(".question-forum li").removeClass("mune-active");
	$(this).addClass("mune-active");
	$(".question-wrap").addClass("hide").eq($(this).index()).removeClass("hide");
})

//		我的提问    我的回答  由于hide后不能获取元素高度 展示更多文字
function showMoneText(){
		$("#show-set").removeClass("hide");
		$("#answer").removeClass("hide");
//点击收起,隐藏则字体
			var $dot5 = $('.dot5');
                $dot5.each(function () {
                    if ($(this).height() > 40) {
                        $(this).attr("data-txt", $(this).attr("data-text"));
                        $(this).height(40);
                        $(this).append('<span class="qq" style="margin-right:60px"> <a class="toggle" href="###" style="color:#2cb82c"><span class="opens">显示全部</span><span class="closes">收起</span></a></span>');
                    }
                    var $dot4 = $(this);

                    function createDots() {
                        $dot4.dotdotdot({
                            after: 'span.qq'
                        });
                    }

                    function destroyDots() {
                        $dot4.trigger('destroy');
                    }

                    createDots();
                    $dot4.on(
                        'click',
                        'a.toggle',
                        function () {
                            $dot4.toggleClass('opened');

                            if ($dot4.hasClass('opened')) {
                                destroyDots();
                            } else {
                                createDots();
                            }
                            return false;
                        }
                    );
                });
//获取高度后立马将其隐藏
		$("#show-set").addClass("hide")
		$("#answer").addClass("hide")
}

//我的提问
quizList(1)		
function quizList(pages){
	 RequestService("/online/questionlist/getQuestionList", "get",{
	 	pageNumber:pages,
	 	pageSize:5
	 }, function (data) {
   		if(data.success==true){
            if (data.resultObject.items.length == 0) {
            	$(".no-question-box").removeClass("hide");
            } else {   
            	$(".no-question-box").addClass("hide");  
            	$("#quiz-template").html(template("quiz-box",{items:data.resultObject.items}))
           		showMoneText();
            }
          }else{
    		showTip("获取数据失败");
    	}   
     })
}	
	
//我的回答

myAnswer(1)
function myAnswer(pages){
	 RequestService("/ask/my/findMyAnswers", "POST",{
	 	pageNumber:pages,
	 	pageSize:5
	 }, function (data) {
   		if(data.success==true){
            if (data.resultObject.items.length == 0) {
            	$(".no-answer-box").removeClass("hide");
            } else {
            	$(".no-answer-box").addClass("hide");
            	var tagsObject=data.resultObject.items
            	for(var i=0; i<tagsObject.length;i++){
            		tagsObject[i].tags=tagsObject[i].tags.split(",");   
            	}
            	$("#answer").html(template("answer-box",{items:data.resultObject.items}))
//          	showMoneText()//展示更多文字
            }
          }else{
    		showTip("获取数据失败");
    	}   
     })
}	

//我的帖子/我的回复选项卡
	$(".forum-wrap li").click(function(){
		$(".forum-wrap li").removeClass("mune-active");
		$(this).addClass("mune-active");
		$(".reply-post-wrap").addClass("hide").eq($(this).index()).removeClass("hide");
	})
//我的帖子	
template.config("escape", false);    //去掉arttemplate渲染时带有的标签
postList(1)
function postList (pages) {
 RequestService("/bbs/myPosts", "get",{
 	page:pages
 }, function (data) {
 		if(data.success==true){
            if (data.resultObject.records.length == 0) {
            	$(".post-box").addClass("hide");
            	$(".no-post-box").removeClass("hide");
            } else {
            	$(".no-post-box").addClass("hide");   
            	$(".post-box").removeClass("hide");
            	$("#post-template").html(template("post-model",{items:data.resultObject.records}))
            }
                //分页添加
			if(data.resultObject.pages > 1) { //分页判断
					$(".not-data").remove();
		            $(".post_pages").removeClass("hide");
		            $(".post_pages .searchPage .allPage").text(data.resultObject.pages);
		            $("#post_doctors").pagination(data.resultObject.pages, {
		                num_edge_entries: 1, //边缘页数
		                num_display_entries: 4, //主体页数
		                current_page:pages-1,
		                callback: function (page) {
		                    //翻页功能
		                    postList(page+1)
		                }
		            });
				}
				else {
					$(".post_pages").addClass("hide");
				}
          }else{
    		showTip("获取数据失败");
    	}   
     })
}	

//我的回复
replyList(1)
function replyList (pages) {
 RequestService("/bbs/myReplies", "get",{
 	page:pages
 }, function (data) {
 		if(data.success==true){
            if (data.resultObject.records.length == 0) {
            	$(".reply-box").addClass("hide")
            	$(".no-reply-box").removeClass("hide");
            } else {
            	$(".no-reply-box").addClass("hide"); 
            	$(".reply-box").removeClass("hide")
            	$("#reply-template").html(template("reply-model",{items:data.resultObject.records}))
            }
                //分页添加
			if(data.resultObject.pages > 1) { //分页判断
					$(".not-data").remove();
		            $(".reply_pages").removeClass("hide");
		            $(".reply_pages .searchPage .allPage").text(data.resultObject.pages);
		            $("#reply_doctors").pagination(data.resultObject.pages, {
		                num_edge_entries: 1, //边缘页数
		                num_display_entries: 4, //主体页数
		                current_page:pages-1,
		                callback: function (page) {
		                    //翻页功能
		                    replyList(page+1)
		                }
		            });
				}
				else {
					$(".reply_pages").addClass("hide");
				}
          }else{
    		showTip("获取数据失败");
    	}   
     })
}		
	
	
	
	
	
	
	
	
	
	

//--------------------------------------问答论坛结束    我的订单开始--------------------------------------------
//我的订单选项卡
	$(".order-box li").click(function(event){
		event.stopPropagation();
		$(".order-box li").removeClass("mune-active");
		$(this).addClass("mune-active");
		orderList(1,$(".all-change .select-order").attr("data-order"),5,$(this).attr("data-orderStatus"))
	})
	//全部订单/近三月订单筛选	
	$(".all-order").hover(function(){
		$(".all-change").stop().slideDown();
		$(".triangle-bottom").addClass("glyphicon-triangle-top")
	},function(){
		$(".all-change").stop().slideUp();
		$(".triangle-bottom").removeClass("glyphicon-triangle-top")	
	})
	$(".all-order span").click(function(){
		var spanText=$(this).text();
		$(".select-write").text(spanText);
	})

//	设置多少年前订单
	var setYear = new Date().getFullYear();
	$("#set-year").html(setYear+"年前订单");
//	选择时间进行传参
	$(".all-change span").click(function(event){
		event.stopPropagation();
		$(".all-change span").removeClass("select-order");
		$(this).addClass("select-order")
		orderList(1,$(this).attr("data-order"),5)
		$(".order-box li").first().click();
	})



//-------------------------------------- 我的订单结束    我的钱包开始--------------------------------------------
//点击充值切换页面
$(".main-wallet-top button").click(function(){
	$(".wallet-trade").addClass("hide");
	$(".recharge-wrap").removeClass("hide");
//	给选择金额一个点击
	$(".select-pay-money li:first").click();
//	给充值方式一个点击
	$(".main-bottom ul li:first").click();
})
//点击我的钱包回到第一个页面
$("#wallet-btn").click(function(){
	if($(".wallet-trade").hasClass("hide")){
		$(".wallet-trade").removeClass("hide");
		$(".recharge-wrap").addClass("hide");	
	}
})
//选择充值金额
var actualPay
$(".select-pay-money li").click(function(){
	$(".select-pay-money li").removeClass("select-money");
	$(this).addClass("select-money");
//	为了获取充值金额
	actualPay=$(this).find("span").text();
	return actualPay
})

//选择支付方式
	$(".main-bottom ul li").click(function(){
		$(".main-bottom ul li").removeClass("select-confirm").find("span").addClass("hide");
		$(this).addClass("select-confirm").find("span").removeClass("hide");
})
//熊猫币余额
balanceShow();
function balanceShow () {
	RequestService("/userCoin/balance", "get",null, function (data) {
		$(".balance-gold").html(data.resultObject);
		$(".balance span").html(data.resultObject);
	}) 	
}
//交易记录
tradeList(1)
function tradeList (pages) {
	RequestService("/userCoin/transactionRecords", "post",{
		pageNumber:pages,
		pageSize:6
	}, function (data) {
		if (data.success==true) {
			if (data.resultObject.length==0){
				$(".all-money-order").removeClass("hide")
			} else{
				$("#trade-template").html(template("trade-box",{items:data.resultObject}))
			}
		} else{
			showTip("获取数据失败-我的钱包接口")
		}
	}) 	
}
	//0 支付宝 1 微信  
	var payType
	$(".order-pay").click(function() {
				$(".select-pay li").each(function() {
					if($(this).hasClass("select-confirm")) {
						payType = $(this).attr("data-payType");
					}
					return;
				});
				if(payType == 0) {
                    window.open("/web/alipay/recharge/" + actualPay+"/");
				} else if(payType == 1) {
					window.open("/web/wxPay/recharge/"+actualPay+"/");
				}
			});
//-------------------------------------- 我的钱包结束,我的资料开始--------------------------------------------

//男女单选框
	$(".data-mune label").click(function() {
		$(this).find(".radio-cover em").addClass("active").parent().parent().siblings().find(".radio-cover em").removeClass("active");
	})
	$("#data-btn").click(function(){
		$(".address-info").iProvincesSelect("init",null)	
	})



//-------------------------------------- 我的资料结束,消息开始--------------------------------------------
//消息列表
newsList(1)
//消息总数
Newsnumber()

//将全部消息设为已读
$(".sign-read").click(function(){
	RequestService("/online/message/readMessage", "POST",null, function (data) {
		if(data.success==true){
			showTip(data.resultObject);
			newsList(1)
			Newsnumber()
		}else{
			showTip("操作失败");
		}

	})

})



//-------------------------------------- 我的消息结束,反馈开始--------------------------------------------
	$(".feedback-wrap button").click(function(evt) {
			RequestService("/online/user/isAlive", "get", null, function(data) {
				if(!data.success) { //登录判断
					$('#login').modal('show');$('#login').css("display", "block");
					localStorage.username = null;
					localStorage.password = null;
					$(".login").css("display", "none");
					$(".logout").css("display", "block");
				} else { //验证
					var title = $(".text-title").val().trim(),
						miaoshu = $(".textarea-miaoshu").val().trim();
					if(title == "") {
						$(".text-title").css("border-color", "red");
						$(".error-msg").text("请输入标题");
						$(".error-msg").show();
						return false;
					} else if(title.length < 5) {
						$(".text-title").css("border-color", "red");
						$(".error-msg").text("标题5~50字之间");
						$(".error-msg").show();
						return false;
					} else if(title.length > 4 && title.length < 51) {
						$(".error-msg").hide();
						$(".text-title").css("border-color", "#eee");
						$(".feedback-wrap button").attr("disabled","disabled")
						
						var data = {
							userId: localStorage.userid,
							title: title,
							describe: miaoshu
						};
						RequestService("/online/message/getMessageList", "get",data, function (data) {
							if(data.success==true){
								showTip("提交成功");
								$(".text-title").val("");
								$(".textarea-miaoshu").val("");

								setTimeout(function(){
									$(".feedback-wrap button").removeAttr("disabled")
//									$(this).attr("disabled")
								},3000)
							}
						})
						
					}
				}
			});

		});
});

//html内嵌点击事件

//我的订单-----------------------------------------------------------------------------------
orderList(1,0,5)	
	function orderList(pages,time,pageSize,status){
		var data = {
		 	pageNumber:pages,
		 	timeQuantum:time,
		 	pageSize:pageSize
		};
		if(status!=null){
			data.orderStatus=status;
		}
		RequestService("/web/getMyAllOrder", "get",data, function (data) {
		 	if(data.success==true){
		 		if(data.resultObject.items.length==0){
		 			$(".all-no-order").removeClass("hide");
					$("#order-template").html(template("order-content",{items:data.resultObject.items}))	
		 		}else{
					$("#order-template").html(template("order-content",{items:data.resultObject.items}))	
		 			$(".all-no-order").addClass("hide");
		 		}	
		 		 //分页添加
			if(data.resultObject.totalPageCount > 1) { //分页判断
					$(".not-data").remove();
		            $(".order_pages").removeClass("hide");
		            $(".order_pages .searchPage .allPage").text(data.resultObject.totalPageCount);
		            $("#order_doctors").pagination(data.resultObject.totalPageCount, {
		                num_edge_entries: 1, //边缘页数
		                num_display_entries: 4, //主体页数
		                current_page:pages-1,
		                callback: function (page) {
		                    //翻页功能
		                    orderList(page+1,time,pageSize,status)
		                }
		            });
				}
				else {
					$(".order_pages").addClass("hide");
				}
		 	}else{
		 		showTip("获取数据失败");
		 	}
		 })
	}
//已失效，重新购买课程
    function againBuy(index) {
       var $this = $(index);
       RequestService("/online/user/isAlive", "GET", null, function(data) {
           if(!data.success) {
               $('#login').modal('show');
           } else {
               var id = $this.data('id');

               if (!id) {
                   showTip("无法获取课程id");
               }
               RequestService("/order/" + id, "POST", null, function(data){
                   if (data.success) {
                       window.open("/order/pay?orderId=" + data.resultObject);
                   } else {
                       showTip(data.errorMessage);
                   }
               });
           }
       });

    };
//取消订单和删除订单
function deleteBtnOrder(index){
	var dataDelete=$(index).attr("data-delete"),
		dataNumber=$(index).attr("data-number");
		RequestService("/web/updateOrderStatu", "POST",{
			type:dataDelete,
			orderNo:dataNumber
		}, function(data) {
		 	if(data.success == true){
		 		showTip(data.resultObject);
		 		$(".order-box li:first").click(); //删除成功后tab选为第一个
		 		orderList(1,0,5)
		 	}else{
		 		showTip("操作失败");
		 	}
		 	
      })
	
}
//问答与论坛-----------------------------------------------------------------------------------
//我的提问、我的问答点赞
	function selectDown(index){	
		var answerId=$(index).attr("data-answerid"),
			$this=$(index);
		RequestService("/ask/answer/praiseAnswer", "get",{
			answer_id:answerId		
		}, function(data) {
		if($this.find("i").hasClass("select-active")){
			var thumbsUp=$this.find("p").text();              //为了避免数据刷新引起的内容展示高度获取不到而加的假象
			$this.find("i").removeClass("select-active");
			$this.find("p").html(parseInt(thumbsUp)-1)
		}else{
			var thumbsUp=$this.find("p").text();
			$this.find("i").addClass("select-active");
			$this.find("p").html(parseInt(thumbsUp)+1)
			
		}
      })
		
}




//我的消息--------------------------------------------------------------------------------
//消息列表
//若是字符串渲染,则是该语法
//	$("#news-template").html(template.compile(nnee)({
//      items: data.resultObject.items
//  }));
function newsList(pages){
	var setData={
		pageNumber:pages,
		pageSize:10
	}
	RequestService("/online/message/getMessageList", "get",setData, function (data) {
		
		if(data.resultObject.items.length == 0){
			$(".all-news-order").removeClass("hide");
		}else{
			$(".all-news-order").addClass("hide");
		}
		$("#news-template").html(template("news-box",{items:data.resultObject.items}))
    //分页添加
			if(data.resultObject.totalPageCount > 1) { //分页判断
					$(".not-data").remove();
		            $(".news_pages").removeClass("hide");
		            $(".news_pages .searchPage .allPage").text(data.resultObject.totalPageCount); //共几页
		            $("#news_doctors").pagination(data.resultObject.totalPageCount, {
		                num_edge_entries: 1, //边缘页数
		                num_display_entries: 4, //主体页数
		                current_page:pages-1,  //pages为传的页数
		                callback: function (page) {
		                    //翻页功能
		                    newsList(page+1);
		                }
		            });
				}
				else {
					$(".news_pages").addClass("hide");
				}

	})
}
//获取未读的消息条数
function Newsnumber(){
RequestService("/online/message/findMessageCount", "get",null, function (data) {
		if(data.success==true){
			var numberAll=data.resultObject.count;
			if(numberAll>99){
				numberAll=99;
			}
			if(data.resultObject.count==0){
				$(".mune-news .news-tip").addClass("hide");
				$(".sign-read").addClass("hide");
			}else{
				$(".mune-news .news-tip").removeClass("hide");
				$(".mune-news .news-tip").htme(numberAll);
				$(".sign-read").removeClass("hide");
			}
		}else{
			showTip("消息获取失败")
		}
	});
}
//将某条消息设为已读
function jump_msg() {
    var e = window.event || arguments.callee.caller.arguments[0];
    var id = $(e.target).data('id');
    RequestService("/online/message/updateReadStatusById", "post", {
        id: id
    }, function (data) {
        if (data.success == true) {
        	newsList(1);
        	Newsnumber();
            var url = $(e.target).data('url');
            if (url) {
                window.open(url, "_blank");
            }
        }
    }, false);
}
//删除消息
function deleteNews(index){
	var deleteId=$(index).attr("data-delete");
	RequestService("/online/message/deleteMessage", "post", {
        id: deleteId
    }, function (data) {
    	if(data.success== true){
    		showTip(data.resultObject);
    		newsList(1);
        	Newsnumber();
    	}else{
    		showTip(data.resultObject);
    	}
    })
}
 
    

