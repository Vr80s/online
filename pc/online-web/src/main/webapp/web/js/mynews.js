//如果有链接的点击事件
	function on_click_msg(msg_id, msg_link, orderNo) {
		debugger;
		var e=window.event || arguments.callee.caller.arguments[0];
		RequestService("/online/message/updateReadStatusById", "post", {
			id: msg_id
		}, function(data) {
			if(data.success == true) {
				$(e.target).parents(".li").removeClass("weidu");
				$(e.target).parents(".li").find(".rqj-new-red").remove();
				unReadNews();
				newsMark();
				if(orderNo!=null){
					RequestService("/web/getOrderStatus", "get", {
						orderNo: orderNo
					}, function(data) {
						if(data.resultObject==1){
							rTips("订单已支付");
						}else if(data.resultObject==0){
							if(msg_link!=null){
								window.open(msg_link,"_blank");
							}
						}else{
							rTips("订单已关闭")
						}
					});
				}else{
					if(msg_link!=null){
						window.open(msg_link,"_blank");
					}
				}
			}
		}, false);
	};
function createNews() { //模板加载
	//删除确认,取消 
	$(".pages").css("display", "none");
	$(".qxCloser,.qxQuit").click(function() {
		$(".mask").css("display", "none");
		$(".del-sure").css("display", "none");
		$(".active").removeClass("active");
	});
	$(".my-news .btn-item").off().on("click", function() {
		$(this).addClass("color2cb").siblings().removeClass("color2cb");
		$(".read-all").attr("data-type", $(this).attr("data-type"));
		unReadNews();
		cyinstant($(this).attr("data-type"));
	});
	unReadNews();
	//全部标记为已读
	$(".read-all").off().on("click", function() {
		var type=$(this).attr("data-type");
		RequestService("/online/message/readMessage","post",{
			type:type===undefined?"":type
		},function(data){
			if(data.success==true){
				unReadNews();
				cyinstant(type);
			}
		});
	});
	cyinstant();
};
//未读消息总数
function unReadNews() {
	RequestService("/online/message/findMessageCount", "get", null, function(data) {
		if(data.success == true) {
			var newsAll = data.resultObject.count;
			var newsCourse = data.resultObject.courseMessageCount;
			var newsQuestion = data.resultObject.questionMessageCount;
			var newsComent = data.resultObject.comentMessageCount;
			var newsSystem = data.resultObject.systemCount;
			if(newsAll == 0) {
				$(".my-news .tabbar .btn-item").eq(0).find("i").hide()
				$(".messageBox .messageCount em").html(newsAll);
				$(".messageBox .messageCount").hide();
			} else if(newsAll > 99) {
				newsAll = 99
				$(".messageBox .messageCount em").html(newsAll).show();
			} else {
//				$(".my-news .tabbar .btn-item").eq(0).find("i").show().html(newsAll).show();
				$(".messageBox .messageCount em").html(newsAll).show();
			};
			if(newsCourse == 0) {
				$(".my-news .tabbar .btn-item").eq(1).find("i").hide()
			} else if(newsCourse > 99) {
				newsCourse = 99
				$(".my-news .tabbar .btn-item").eq(1).find("i").html(newsCourse).show();
			} else {
				$(".my-news .tabbar .btn-item").eq(1).find("i").html(newsCourse).show();
			};
			if(newsQuestion == 0) {
				$(".my-news .tabbar .btn-item").eq(2).find("i").hide()
			} else if(newsQuestion > 99) {
				newsQuestion = 99
				$(".my-news .tabbar .btn-item").eq(2).find("i").html(newsQuestion).show();
			} else {
				$(".my-news .tabbar .btn-item").eq(2).find("i").html(newsQuestion).show();
			};
			if(newsComent == 0) {
				$(".my-news .tabbar .btn-item").eq(3).find("i").hide()
			} else if(newsComent > 99) {
				newsComent = 99
				$(".my-news .tabbar .btn-item").eq(3).find("i").html(newsComent).show();
			} else {
				$(".my-news .tabbar .btn-item").eq(3).find("i").html(newsComent).show();
			};
			if(newsSystem == 0) {
				$(".my-news .tabbar .btn-item").eq(4).find("i").hide()
			} else if(newsSystem > 99) {
				newsSystem = 99
				$(".my-news .tabbar .btn-item").eq(4).find("i").html(newsSystem).show();
			} else {
				$(".my-news .tabbar .btn-item").eq(4).find("i").html(newsSystem).show();
			};
		} else {
			if(data.errorMessage == "请登录") {
				$('#login').modal('show');
				$('#login').css("display", "block");
				$(".loginGroup .logout").css("display", "block");
				$(".loginGroup .login").css("display", "none");
				return false;
			}
		}
	});
};
var paginOpts = {
	num_edge_entries: 1,
	num_display_entries: 5,
	items_per_page: 1,
	show_if_single_page: true
};
//$(".pages div:eq(0)").attr('id','Pagination');

function setSectionData(requstUrl, data, type) { //系统消息查询
	paginOpts.callback = pageselectCallback;
	var defaultParam = {
		pageNumber: "1",
		pageSize: "15"
	};
	var param = $.extend(defaultParam, data);
	RequestService(requstUrl, "GET", param, function(data) {
		var result = data.resultObject;
		if(result.totalCount > 0) {
			$(".not-data").remove();
			$(".pages").css("display", "block");
			$(".searchPage .allPage").text(result.totalPageCount);
			$("#Pagination").pagination(result.totalPageCount, paginOpts);
			$(".pages").css("margin-bottom", "30px");

		} else {
			/*$(".view-content-content").append("<div class='not-data'>对不起，暂无数据</div>");*/
			$(".pages").css("display", "none");
		}

		createSection(type, result.items);
	});

	function pageselectCallback(data) { //分页功能
		RequestService(requstUrl, "GET", {
			currentPage: (data + 1),
			pageSize: "15"
		}, function(data) {
			createSection(type, data.resultObject.items);

		});
	}
};
//为勾选便点击删除或者标记
function rTips(text) {
	if(text!=null){
		$(".rTips").html(text);
	}
	$(".rTips").css("display", "block");
	setTimeout(function() {
		$(".rTips").css("display", "none");
	}, 1000)
};
//删除标记已读的时间添加函数绑定
function newsMark() {
	//删除确认,确定 
	$(".cy-data .delete").off().on("click", function() {
		var $id = $(this).attr("data-id");
		var $this = $(this);
		$.ajax({
			type: "get",
			url: bath + "/online/user/isAlive",
			async: false,
			success: function(data) {
				if(data.success === true) {
					$(".mask").css("display", "block");
					$(".del-sure").css("display", "block");
					$(".qxSure").off().on("click", function() {
						//请求接口上传问题ID
						RequestService("/online/message/deleteMessage", "POST", {
							id: $id
						}, function(data) {
							if(data.success == true) {
								$this.parent().parent().remove();
								$(".mask").css("display", "none");
								$(".del-sure").css("display", "none");
								unReadNews();
								cyinstant($(".read-all").attr("data-type"));
							}
						});
					});
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
	if($(".cy-data .weidu").length==0){
		$(".read-all").hide()
	}else{
		$(".read-all").css("display","block");
	};
};
var news =
	'{{each items as $value i}}' +
	'{{if $value.readstatus!=1&&$value.readstatus!=2}}' +
	"<li data-id={{$value.id}} class='weidu li'>" +
	"<div class='back clearfix'>" +
	"<span class='rqj-new-red'></span>" +
	"{{else}}" +
	"<li data-id={{$value.id}} class='li'>" +
	"<div class='back clearfix'>" +
	'{{/if}}' +
	"<div class='rqj-article' onclick='on_click_msg(\"{{$value.id}}\")'>{{#$value.context}}</div>" +
	"<span class='rqj-mynews-right'>{{#timeTypeChange1($value.createTime)}}</span>" +
	"<span class='delete' title='删除' data-id={{$value.id}}><img src='/web/images/cha.png'></span>" +
	"</div></li>" +
	"{{/each}}";
//添加消息
function appendData(data) {
	$(".cy-data li").remove();
	$(".cy-data").html(template.compile(news)({
		items: data.resultObject.items
	}));
	newsMark();
	layer.closeAll('loading');
};

//获取消息列表
function cyinstant(type) {
	var emptyDefaul =
		"<div class='page-no-result'>" +
		"<img class='img' src='../images/personcenter/nonotes.png' style='padding-top:14px'>" +
		"<div class='no-title'>暂无数据哦</div>" +
		"</div>";
	paginOpts.callback = pageselectCallback;
	RequestService("/online/message/getMessageList", "GET", {
		type: type === undefined ? "" : type,
		pageNumber: "1",
		pageSize: "15"
	}, function(data) {
		if(data.success == true) {
			$(".cy-data").empty();
			if(data.resultObject.totalPageCount == 0) {
				$(".read-all").hide();
				$(".view-content-notbodys").html(emptyDefaul);
				$(".re-bt").css("display", "none");
				$(".pages").css("display", "none");
			} else if(data.resultObject.totalPageCount == 1) { //分页判断
				/*$(".view-content-content").append("<div class='not-data'>对不起，暂无数据</div>");*/
				$(".not-data").remove();
				/*
					                $("#Pagination").pagination(data.resultObject.totalPageCount, paginOpts);*/
				$(".searchPage .allPage").text(data.resultObject.totalPageCount);
				$(".re-bt").css("display", "block");
				$(".pages").css("display", "none");
				$(".view-content-notbodys").html("");
			} else {
				$(".not-data").remove();
				$(".pages").css("display", "block");
				$(".re-bt").css("display", "block");
				$("#Pagination").pagination(data.resultObject.totalPageCount, paginOpts);
				$(".searchPage .allPage").text(data.resultObject.totalPageCount);
				$(".pages").css("margin-bottom", "30px");
				$(".view-content-notbodys").html("");
			}
			appendData(data);
		} else {
			$(".read-all").hide();
			$(".view-content-notbodys").html(emptyDefaul);
			$(".re-bt").css("display", "none");
			$(".pages").css("display", "none");
		}
	});

	function pageselectCallback(data) {
		RequestService("/online/message/getMessageList", "GET", {
			pageNumber: (data + 1),
			pageSize: "15"
		}, function(data) {
			appendData(data);
		});
	};
}