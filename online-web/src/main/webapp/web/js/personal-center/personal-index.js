
$(function() {
//左右侧边栏mune功能
	$('#accordion > li').click(function(){
		    $('#right-content > div').addClass('hide');
			$('#right-content > div').eq($(this).index()).removeClass('hide');
	})
	$('#accordion > li >.submenu >li').click(function(e){
		$('#accordion > li >.submenu >li a').removeClass("mune-active");
		$(this).find("a").addClass("mune-active");
		location.hash = $(this).find("a").attr("data-menu");
		e.stopPropagation();
		
//		判断数据加载
		var dataLoadTittle=$(this).find("a").attr("data-load");
		if (dataLoadTittle=="load-anchor") {
			anchorShow();
		}		
//		判断数据加载
		var index = $(this).parent().parent().index();
		$('#right-content > div:eq('+index+') > div').addClass('hide')
		$('#right-content > div:eq('+index+') > div').eq($(this).index()).removeClass('hide');
		if(location.hash == "#menu2-1"){
			$("#btnQuestion").click()
			quizList(1);		
		}
	})
	
	
		if(!location.hash){
	        	location.hash = "#menu1-1";
		}
		var hash = location.hash;
		
		if(location.hash == "#menu1-1" || location.hash == "#menu1-2"){
	    	$("#accordion li[data-menu='menu1-1']").click();
	    	$("#accordion li[data-menu='menu1-1']").addClass('open');
	    	$("#accordion li[data-menu='menu1-1'] .submenu").css('display','block')
   	 	}else if(location.hash == "#menu2-1" || location.hash == "#menu2-2"){
	    	$("#accordion li[data-menu='menu2-1']").click();
	    	$("#accordion li[data-menu='menu2-1']").addClass('open');
	    	$("#accordion li[data-menu='menu2-1'] .submenu").css('display','block')
    	}else if(location.hash == "#menu3"){
	    	$("#accordion li[data-menu='menu3']").click();
	    	$("#accordion li[data-menu='menu3']").addClass('open');
    	}else if(location.hash == "#menu4"){
	    	$("#accordion li[data-menu='menu4']").click();
	    	$("#accordion li[data-menu='menu4']").addClass('open');
    	}else if(location.hash == "#menu5"){
	    	$("#accordion li[data-menu='menu5']").click();
	    	$("#accordion li[data-menu='menu5']").addClass('open');
    	}else if(location.hash == "#menu6"){
	    	$("#accordion li[data-menu='menu6']").click();
	    	$("#accordion li[data-menu='menu6']").addClass('open');
    	}else if(location.hash == "#menu7"){
	    	$("#accordion li[data-menu='menu7']").click();
	    	$("#accordion li[data-menu='menu7']").addClass('open');
    	}	
//  	
//	判断路径的hash进行页面跳转
	if(hash == '#menu1-1'){
   		$("#accordion a[data-menu='menu1-1']").click();
	}else if(hash == '#menu1-2'){		
		$("#accordion a[data-menu='menu1-2']").click();
	}else if(hash == '#menu2-1'){
		$("#accordion a[data-menu='menu2-1']").click();
	}else if(hash == '#menu2-2'){		
		$("#accordion a[data-menu='menu2-2']").click();
	}else if(hash == '#menu3'){
		orderList(1,0,5);
		$("#accordion .link[data-menu='menu3']").click();
	}else if(hash == '#menu4'){
		tradeList(1)
		$("#accordion .link[data-menu='menu4']").click();
	}else if(hash == '#menu5'){
		$("#accordion .link[data-menu='menu5']").click();
	}else if(hash == '#menu6'){
		newsList(1)
		$("#accordion .link[data-menu='menu6']").click();
	}else if(hash == '#menu7'){
		$("#accordion .link[data-menu='menu7']").click();
	}
	
	
	
//$(window).bind('hashchange', function() {

//		if(location.hash == "#menu1-1" || location.hash == "#menu1-2"){
//			$("#accordion li").removeClass('open');
//	    	$("#accordion li[data-menu='menu1-1']").click();
//	    	$("#accordion li[data-menu='menu1-1']").addClass('open');
//	    	$("#accordion li[data-menu='menu1-1'] .submenu").css('display','block')
// 	 	}else if(location.hash == "#menu2-1" || location.hash == "#menu2-2"){
// 	 		$("#accordion li").removeClass('open');
//	    	$("#accordion li[data-menu='menu2-1']").click();
//	    	$("#accordion li[data-menu='menu2-1']").addClass('open');
//	    	$("#accordion li[data-menu='menu2-1'] .submenu").css('display','block')
//  	}else if(location.hash == "#menu3"){
//  		$("#accordion li").removeClass('open');
//	    	$("#accordion li[data-menu='menu3']").click();
//	    	$("#accordion li[data-menu='menu3']").addClass('open');
//  	}else if(location.hash == "#menu4"){
//  		$("#accordion li").removeClass('open');
//	    	$("#accordion li[data-menu='menu4']").click();
//	    	$("#accordion li[data-menu='menu4']").addClass('open');
//  	}else if(location.hash == "#menu5"){
//  		$("#accordion li").removeClass('open');
//	    	$("#accordion li[data-menu='menu5']").click();
//	    	$("#accordion li[data-menu='menu5']").addClass('open');
//  	}else if(location.hash == "#menu6"){
//  		$("#accordion li").removeClass('open');
//	    	$("#accordion li[data-menu='menu6']").click();
//	    	$("#accordion li[data-menu='menu6']").addClass('open');
//  	}else if(location.hash == "#menu7"){
//  		$("#accordion li").removeClass('open');
//	    	$("#accordion li[data-menu='menu7']").click();
//	    	$("#accordion li[data-menu='menu7']").addClass('open');
//  	}	


//	if(hash == '#menu5'){
//		$("#accordion .link[data-menu='menu5']").click();
//	}else if(hash == '#menu6'){
//			newsList(1)
//			$("#accordion .link[data-menu='menu6']").click();
//	}
//if(hash == '#menu1-1'){
// 		$("#accordion a[data-menu='menu1-1']").click();
//	}else if(hash == '#menu1-2'){		
//		$("#accordion a[data-menu='menu1-2']").click();
//	}else if(hash == '#menu2-1'){
//		$("#accordion a[data-menu='menu2-1']").click();
//	}else if(hash == '#menu2-2'){		
//		$("#accordion a[data-menu='menu2-2']").click();
//	}

//});
	
	
	
	
	
	
	
//	判断路径的hash进行页面跳转
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
			location.hash = $(this).attr("data-menu");
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
			if($(this).siblings(".tte").length>0){	
				quizList(1)	
			}
//		判断数据加载
		var dataLoadmune=$this.attr("data-load");
		if (dataLoadmune=="load-order") {
			orderList(1,0,5);
		}else if(dataLoadmune=="load-money"){
			tradeList(1)
		}
		else if(dataLoadmune=="load-news"){
			newsList(1)
		}
//		判断数据加载 
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
					'<img src="/web/images/icon-nodata.png"/>'+
				'</div>'+
				'<p>暂无记录</p>';
				
//已购/结束课程/历史记录选项卡
$(".my-class-nav li").click(function(){
	var loadData=$(this).attr("data-btn");
	if (loadData=="load-buy") {
		buyClass(1)
	}else if(loadData=="load-end"){
		endClass(1)
	}else if(loadData=="load-history"){
		historyClass(1)
	}
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
		                current_page:pages-1,
		                callback: function (page) {
		                    //翻页功能
		                    historyClass(page+1);
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

function buyClass (pages){

 RequestService("/userCourse/myCourseType", "POST",{
 	pageNumber:pages,
 	pageSize:8,
     type:1
 }, function (data) {
 		if(data.success==true){
 			$(".save-class .laod-buy").addClass("hide");
            if (data.resultObject.records.length == 0) {
            	$(".nodata-buyclass").html(noDataImg).removeClass("hide");
            } else {
            	$(".nodata-buyclass").addClass("hide");    
            	$(".save-class .laod-buy").addClass("hide");
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

function endClass(pages){
 RequestService("/userCourse/myCourseType", "POST",{
 	pageNumber:pages,
 	pageSize:8,
 	type:2
 }, function (data) {
 		if(data.success==true){
 			$(".save-class .laod-end").addClass("hide");
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
function anchorShow(){
 RequestService("/focus/myFocus", "POST",null, function (data) {
 		if(data.success==true){
 			$(".laod-anchor").addClass("hide");
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
}

//--------------------------------------学习中心结束--问答论坛开始--------------------------------------------

//我的提问  我的回答选项卡
$(".question-forum li").click(function(){
	$(".question-forum li").removeClass("mune-active");
	$(this).addClass("mune-active");
	$(".question-wrap").addClass("hide").eq($(this).index()).removeClass("hide");
	if($(this).attr("data-name")=="showText"){
		myAnswer(1);
//		showMoneText();
	}
})

//		我的提问    我的回答  由于hide后不能获取元素高度 展示更多文字


//我的提问
//quizList(1)
function quizList(pages){
	 RequestService("/online/questionlist/getMyQuestionList", "get",{
	 	pageNumber:pages,
	 	pageSize:5
	 }, function (data) {
   		if(data.success==true){
            if (data.resultObject.items.length == 0) {
            	$(".no-question-box").removeClass("hide");
            } else {   
            	$(".no-question-box").addClass("hide"); 
//          	内容底下的三张图片
            	var contentLength=data.resultObject.items
            	for(var i=0; i<contentLength.length;i++){
						contentQuestion = contentLength[i].content;           		
	            		contentLength[i].picture=getImgArray(contentQuestion);
//	            		
//	            		var noImg=contentQuestion.replace(/<(?!img).*?>/g, ""); 
//	            		data.resultObject.items.content=noImg;
            	}
//          	内容底下的三张图片  
            	$("#quiz-template").html(template("quiz-box",{items:data.resultObject.items}));
//          	去掉内容里的img标签
            	$(".list-Answer-right-text img").remove();
            	
				//控制阅读更多
				var $dot5 = $('.dot6');
                $dot5.each(function () {
                    if ($(this).height() > 40) {
                        $(this).attr("data-txt", $(this).attr("data-text"));
                        $(this).height(40);
                        $(this).append('<span class="qq" style="margin-right:60px"> <a class="toggle" href="###" style="color:#2cb82c"><span class="opens">阅读全文<span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span></span><span class="closes">收起<span class="glyphicon glyphicon-menu-up" aria-hidden="true"></span></span></a></span>');
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
                //控制阅读更多
            }
          }else{
    		showTip("获取数据失败");
    	}   
     })
}	
	
//我的回答

//myAnswer(1)
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
//          	内容底下的三张图片
            	var contentLength=data.resultObject.items
            	for(var i=0; i<contentLength.length;i++){
						contentQuestion = contentLength[i].content;           		
	            		contentLength[i].picture=getImgArray(contentQuestion);
//	            		var noImg=contentQuestion.replace(/<(?!img).*?>/g, ""); 
//	            		data.resultObject.items.content=noImg;
            	}
//          	内容底下的三张图片  
            	$("#answer").html(template("answer-box",{items:data.resultObject.items}))
//          	去掉内容的img标签
            	$(".list-Answer-right-text img").remove();
            	//控制阅读更多
            	var $dot5 = $('.dot5');
                $dot5.each(function () {
                    if ($(this).height() > 40) {
                        $(this).attr("data-txt", $(this).attr("data-text"));
                        $(this).height(40);
                        $(this).append('<span class="qq" style="margin-right:60px"> <a class="toggle" href="###" style="color:#2cb82c"><span class="opens">阅读全文<span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span></span><span class="closes">收起<span class="glyphicon glyphicon-menu-up" aria-hidden="true"></span></span></a></span>');
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
                //控制阅读更多
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
		orderStatus = $(this).attr("data-orderStatus");
		orderList(1,$(".all-change .select-order").attr("data-order"),5,orderStatus)
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

function tradeList (pages) {
	RequestService("/userCoin/transactionRecords", "post",{
		pageNumber:pages,
		pageSize:6
	}, function (data) {
		if (data.success==true) {
			$(".laod-money").addClass("hide");
			if (data.resultObject.records.length==0){
				$(".all-money-order").removeClass("hide")
			} else{
				$("#trade-template").html(template("trade-box",{items:data.resultObject.records}))
			}
			
			if(data.resultObject.pages > 1) { //分页判断
					$(".not-data").remove();
		            $(".money_pages").removeClass("hide");
		            $(".money_pages .searchPage .allPage").text(data.resultObject.pages);
		            $("#money_doctors").pagination(data.resultObject.pages, {
		                num_edge_entries: 1, //边缘页数
		                num_display_entries: 4, //主体页数
		                current_page:pages-1,
		                callback: function (page) {
		                    //翻页功能
		                    tradeList(page+1)
		                }
		            });
				}
				else {
					$(".money_pages").addClass("hide");
				}
			
			
		}else{
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
//	$("#data-btn").click(function(){
//		$(".address-info").iProvincesSelect("init",null)	
//	})
$(".address-info").iProvincesSelect("init",null);

//个人资料回显
echoData()
function echoData(){
	RequestService("/online/user/getUserData", "GET", "", function(data) {
		if(data.resultObject.nickName == undefined) {
			$(".data-mune .nickname").val("博小白");
		} else {
			$(".data-mune .nickname").val(data.resultObject.nickName);
		};
//		账号
		if(data.resultObject.loginName.indexOf("@") == -1) {
//			$(".emailname").val(data.resultObject.email);
			$(".account-number").val(data.resultObject.loginName).attr("disabled", "disabled").css("background", "#fafafa");
		} else {
//			$(".emailname").val(data.resultObject.loginName).attr("disabled", "disabled").css("background", "#fafafa");
			$(".account-number").val(data.resultObject.mobile);
		};
//		/邮箱
			$(".emailname").val(data.resultObject.email);




//		性别
		if(data.resultObject.sex == 1) {
			$("#myradio-man").attr("checked", true);
			$(".sex-select-all .radio-cover em").removeClass("active");
			$("#myradio-man").parent().find(".radio-cover em").addClass("active");
		} else if(data.resultObject.sex == 0) {
			$("#myradio-woman").attr("checked", true);
			$(".sex-select-all .radio-cover em").removeClass("active");
			$("#myradio-woman").parent().find(".radio-cover em").addClass("active");
		} else {
			$("#myradio-man,#myradio-woman").attr("checked", false);
			$(".sex-select-all .radio-cover em").removeClass("active");
		}
		
		//身份信息渲染	
		var SFinforid = data.resultObject.occupation;
		if(data.resultObject.job.length>=1){
			$("#identity-select").html(template("SFinfor",{items:data.resultObject.job}));
				$("#identity-select").html(template("SFinfor",{item:data.resultObject.job}))
		}
		if(SFinforid){
		$("#identity-select option[value='"+SFinforid+"']").attr("selected","selected");
		}
	//	职业信息
		if(data.resultObject.occupationOther !=''&&data.resultObject.occupationOther != null){
			$('.profession').val(data.resultObject.occupationOther);
		}
	//	省市区三联动
		var pcd = {};
	        pcd.province=data.resultObject.province;
	        pcd.city=data.resultObject.city;
	        pcd.district=data.resultObject.district;
			$(".data-mune .address-info").iProvincesSelect("init",pcd,true);
	})

}
//个人资料保存
	$(".save-data button").click(function() { //点击保存
		$.ajax({
			type: "get",
			url: bath + "/online/user/isAlive",
			async: false,
			success: function(data) {
				if(data.success === true) {
					geren();
					
				} else {

					$('#login').modal('show');
					$('#login').css("display", "block");
					$(".loginGroup .logout").css("display", "block");
					$(".loginGroup .login").css("display", "none");
					return false;
				}
			}
		});
	});
function geren() {
	RequestService("/online/user/checkNickName", "get", {
		nickName: $.trim($(".nickname").val())
	}, function(data) {
		var value = $.trim($(".nickname").val()), // 用jQuery的trim方法删除前后空格
			email = $(".emailname").val(),
			emailProving = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
		//昵称不能为空
		if(value == "" ) {
			$(".warning-name").text("用户名不能为空").removeClass("hide");
			return false;
		}else{
			$(".warning-name").addClass("hide")
		}
		if(value.length<2 || value.length>20) {
			$(".warning-name").text("用户名长度不能小于2或者大于20");
			return false;
		}else{
			$(".warning-name").addClass("hide")			
		}
//		邮箱认证
		if(email != "" && !emailProving.test(email)) {
			$(".warning-email").text("邮箱格式不正确").removeClass("hide");
			return false;
		}else{
			$(".warning-email").addClass("hide")			
		}

//		性别
		if($('.select-man em').hasClass('active')){
			sex = 1;
		}else{
			sex = 0;
		}	
		//身份信息验证
		if($('.identity-select').val() == 'volvo'){
			$(".identity-null").removeClass("hide");
			return false;
		}else{
			$(".identity-null").addClass("hide");
		}
		
		var pcd  = $(".address-info").iProvincesSelect("val");
//		保存个人信息数据
		RequestService("/online/user/updateUser", "POST", {
			userId: localStorage.userid,
			name: $(".nickname").val(),
			loginName: $(".account-number").val(),
			provinceName: pcd.provinceText,
			cityName: pcd.cityText,
			countyName: pcd.districtText,
			regionAreaId : pcd.province,
			regionCityId : pcd.city,
			regionId :  pcd.district,
			email : email,
			sex:sex,
			occupationOther:$('.profession').val(),
			occupation:$('#identity-select option:selected').attr('value'),
		}, function(data) {
			if(data.success == false){
				showTip(data.errorMessage)			
			}
			if(data.success == true) {
				showTip(data.resultObject)			
//				$(".intro .msg").remove();
//				$(".intro").append("<div class='msg' data-maxlengts='11'></div>");
				$(".doctor_inf h4").text($(".nickname").val()).attr("title", $(".nickname").val())
//				if($.trim($(".mycytextarea").val()) != "") {
//					$(".intro .msg0").text($.trim($(".mycytextarea").val())).attr("title", $.trim($(".mycytextarea").val()))
//				} else {
//					$(".intro .msg0").text("说点什么来彰显你的个性吧……").attr("title", "说点什么来彰显你的个性吧……")
//				}

				$(".block-center:eq(1)").text("保存成功");
				$(".loginGroup .name").text($(".nickname").val()).attr("title", $(".nickname").val())
//				$(".view-content .view-content-notbodys").html("");
//				$(".rrrTips").html("保存成功").css("display", "block");

				showTip("保存成功")

			} else {
				showTip("系统繁忙，稍后再试")
			}
		});
	})
};
//-------------------------------------- 我的资料结束,消息开始--------------------------------------------
//消息列表

//消息总数
Newsnumber()
//将全部消息设为已读
$(".sign-read").click(function(){
	RequestService("/message/readStatus", "PUT",null, function (data) {
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
						$.post(bath + "/online/message/addFeedBack", data, function(data) {
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
var orderStatus;
//我的订单-----------------------------------------------------------------------------------
	
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
		 		$(".laod-order").addClass("hide");
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
//  function againBuy(index) {
//     var $this = $(index);
//     RequestService("/online/user/isAlive", "GET", null, function(data) {
//         if(!data.success) {
//             $('#login').modal('show');
//         } else {
//             var id = $this.data('id');
//
//             if (!id) {
//                 showTip("无法获取课程id");
//             }
//             RequestService("/order/" + id, "POST", null, function(data){
//                 if (data.success) {
//                     window.open("/order/pay?orderId=" + data.resultObject);
//                 } else {
//                     showTip(data.errorMessage);
//                 }
//             });
//         }
//     });
//
//  };
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
				orderList(1,$(".all-change .select-order").attr("data-order"),5,orderStatus)
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
//提问阅览数
function previewNumber(index){
	var quesId=$(index).attr("data-id");
	RequestService("/online/questionlist/updateBrowseSum", "post",{
			id:quesId		
		}, function(data) {
			if(data.success==true){
				
			}
		})
}
//问答阅览数
function anwserNumber(index){
	var answerId=$(index).attr("data-id");
	RequestService("/online/questionlist/updateBrowseSum", "post",{
			id:answerId		
		}, function(data) {
			if(data.success==true){
				
			}
		})
}
var	contentQuestion
function getImgArray(str) {
    var imgReg = /<img.*?(?:>|\/>)/gi;
    var srcReg = /src=[\'\"]?([^\'\"]*)[\'\"]?/i;
    var arr = str.match(imgReg);
    var imgStr =new Array();
    if(arr!=null){
        for (var i = 0; i < arr.length; i++) {
            var src = arr[i].match(srcReg);
            //获取图片地址
            if (src[1]) {
                imgStr[i]=src[1];
                if(imgStr.length == 3){
                	return imgStr;
                }
            }
        }
        return imgStr;
    }
    return imgStr;
}


//我的消息--------------------------------------------------------------------------------
//消息列表
//若是字符串渲染,则是该语法
//	$("#news-template").html(template.compile(nnee)({
//      items: data.resultObject.items
//  }));
function newsList(page){
	var setData={
		page:page,
		size:10
	}
	RequestService("/message", "get",setData, function (data) {
		$(".load-news").addClass("hide");
		if(data.resultObject.records.length == 0){
			$(".all-news-order").removeClass("hide");
		}else{
			$(".all-news-order").addClass("hide");
		}
		$("#news-template").html(template("news-box",{items:data.resultObject.records}))
    //分页添加
			if(data.resultObject.pages > 1) { //分页判断
					$(".not-data").remove();
		            $(".news_pages").removeClass("hide");
		            $(".news_pages .searchPage .allPage").text(data.resultObject.pages); //共几页
		            $("#news_doctors").pagination(data.resultObject.pages, {
		                num_edge_entries: 1, //边缘页数
		                num_display_entries: 4, //主体页数
		                current_page:page-1,  //pages为传的页数
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
RequestService("/message/count", "get",null, function (data) {
		if(data.success==true){
			var numberAll=data.resultObject;
			if(numberAll>99){
				numberAll="99+";
			}
			if(data.resultObject==0){
				$(".mune-news .news-tip").addClass("hide");
				$(".sign-read").addClass("hide");
//				nav左侧的消息提醒
				$(".link .news-prompt").addClass("hide");
			}else{
				$(".mune-news .news-tip").removeClass("hide");
				$(".mune-news .news-tip").html(numberAll);
				$(".sign-read").removeClass("hide");
//				nav左侧的消息提醒
				$(".link .news-prompt").removeClass("hide");
				$(".link .news-prompt").html(numberAll);
			}
		}else{
			showTip("消息获取失败")
		}
	});
}
//将某条消息设为已读
//function jump_msg() {
//  var e = window.event || arguments.callee.caller.arguments[0];
//  var id = $(e.target).data('id');
//  RequestService("/message/"+id+"/readStatus", "PUT",null, function (data) {
//      if (data.success == true) {
//      	newsList(1);
//      	Newsnumber();
//          var url = $(e.target).data('url');
//          if (url) {
//              window.open(url, "_blank");
//          }
//      }
//  });
//}
function isBlank(str){	
		return str == "" || str == null
}
function jump_msg(index) {
	var point=$(index);
//  var e = window.event || arguments.callee.caller.arguments[0];
    var id = $(index).data('id');
    RequestService("/message/"+id+"/readStatus", "PUT",null, function (data) {
        if (data.success == true) {
        	point.find(".icon-tip").remove();
        	point.removeClass("weidu");
//      	newsList(1);
        	Newsnumber();
            var urlJump = $(index).data('url');
            if (isBlank(urlJump)==false) {
//              window.open(url, "_blank");
                location.href=urlJump;
            }
        }
    });
}
//删除消息
function deleteNews(index){
	event.stopPropagation();
	var deleteId=$(index).attr("data-delete");
	RequestService("/message/"+deleteId+"", "DELETE",null, function (data) {
    	if(data.success== true){
    		showTip(data.resultObject);
    		newsList(1);
        	Newsnumber();
    	}else{
    		showTip(data.resultObject);
    	}
    })
}
  
function showMoneText(){
//	$("#answer").removeClass("hide");
//点击收起,隐藏则字体
			var $dot5 = $('.dot5');
                $dot5.each(function () {
                    if ($(this).height() > 40) {
                        $(this).attr("data-txt", $(this).attr("data-text"));
                        $(this).height(40);
                        $(this).append('<span class="qq" style="margin-right:60px"> <a class="toggle" href="###" style="color:#2cb82c"><span class="opens">阅读全文<span class="glyphicon glyphicon-menu-down" aria-hidden="true"></span></span><span class="closes">收起<span class="glyphicon glyphicon-menu-up" aria-hidden="true"></span></span></a></span>');
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
//	$("#answer").addClass("hide");

}

