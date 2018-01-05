/**
 * 先写一部分吧js把，今后在完善
 */
// 跳转到观看记录页面
/*document.getElementById("home_img_right").addEventListener("tap", function() {
	sessionStorage.setItem("historyType",1);
	location.href = "/bxg/xcpage/history";
})*/
// 点击搜索进入关键字搜索页面吧....
document.getElementById("home_img").addEventListener("tap", function() {
	/*sessionStorage.setItem("queryType",1)*/
	window.history.back(-1);
})

var controllerAddress = "/bxg/live/list";
var pageNumber = 1;
var pageSize = 20;
var loadFlag = true;
/**
 * ************************************请求列表页**************************************************
 */
/**
 * 请求直播列表
 * 
 * @param downOrOn
 *            上拉加载还是下拉刷新 true 下拉 false 上拉
 */
function requestLiveList(downOrOn) {
	if (downOrOn) {
		pageNumber = 1;
	} else {
		pageNumber = pageNumber + 1;
	}
	var urlparm = {
		pageNumber : pageNumber,
		pageSize : pageSize
	};
	requestService(
			controllerAddress,
			urlparm,
			function(data) {
				if (data.success) {
					var content = document.getElementById("content");
					if(downOrOn){
						content.innerHTML = "";
					}
					// 直播数据结果
					var liveList = data.resultObject;
					// 先添加直播的
					// 先全部添加下吧
					//a 标签 锚点 
					//var  a_name = "maodian"+live.id;
					
					if(liveList.length>0){
						for (var int = 0; int < liveList.length; int++) {
							
							var odiv = document.createElement("div");
							odiv.className = "wm-block mui-table-view-cell";
							odiv.id = 'div' + int;
							
							var allHtml;var liveHtml0 = "";
							var live = liveList[int];
							var currentOnlineNumber = live.learndCount;
							
							var watchStr ="";
							if(live.watchState == 0){  // watchState ： 0 免费   1 收费  2 密码 
								watchStr ="免费";
							}else if(live.watchState == 1){
								watchStr ="￥"+live.currentPrice;
							}else if(live.watchState == 2){
								watchStr ="加密";
							}
							
							
							if (live.lineState == 0) {
								
								liveHtml0 += "<div class='home_list3'>";
								
								liveHtml0 += "<div class='broadcast3'><div class='broadcast3_bg'></div><img src='/xcviews/images/play.png' alt='' /><span>回放</span></div>";
								// 课程详情
								liveHtml0 += "<img class='course_img' src='"
										+ live.smallImgPath+"' name="+live.id+" id="+live.lineState+"  width='100%'  />";
								// 主播详情
								liveHtml0 += "<div class='list1_bottom' onclick='zhuboDetails("
										+ live.userId
										+ ")'><img src='"
										+ live.headImg
										+ "' alt='"+live.userId+"' class='list1_img' />"
										+ "<div class='list1_title'><div class='list1_title_top'>"
										+ live.gradeName
										+ "</div><div class='list1_title_bottom'>"
										+ live.name + "</div></div>";
								liveHtml0 += "<div class='list1_right'><img src='/xcviews/images/zan.png' alt='' /><span>"
										+ currentOnlineNumber
										+ "</span><div class='index_price'><span>"+watchStr+"</span>"
										+"</div></div></div></div>";
							} else if (live.lineState == 1) { // 直播还未开始

								liveHtml0 += "<div class='home_list2'>";
								
								liveHtml0 += "<div class='broadcast2'><span>直播预告 "
										+ live.startTime + "</span></div>";

								liveHtml0 += "<img class='course_img' name="+live.id+" ' id="+live.lineState+" src='"
										+ live.smallImgPath
										+ "' width='100%'  onclick='courseDetails("
										+ JSON.stringify(live) + ",1)' />";

								liveHtml0 += "<div class='list1_bottom'><img src='"
										+ live.headImg
										+ "' alt='"+live.userId+"' class='list1_img' />"
										+ "<div class='list1_title'><div class='list1_title_top'>"
										+ live.gradeName
										+ "</div><div class='list1_title_bottom'>"
										+ live.name + "</div></div>";
								liveHtml0 += "<div class='list1_right'><img src='/xcviews/images/zan.png' alt='' /><span>"
										+ currentOnlineNumber
										+ "</span><div class='index_price'><span>"+watchStr+"</span>"
										+"</div></div></div></div>";
							} else if (live.lineState == 2) { // 正在直播

								liveHtml0 += "<div class='home_list1'>";
								
								liveHtml0 += "<div class='broadcast1'><img  src='/xcviews/images/tv.png' alt='' /><span>直播中</span></div>";
								liveHtml0 += "<img class='course_img' name="+live.id+" ' id="+live.lineState+" src='"
										+ live.smallImgPath
										+ "'  onclick='courseDetails(" + JSON.stringify(live)
										+ ",1)' width='100%'  />";

								liveHtml0 += "<div class='list1_bottom'><img src='"  
										+ live.headImg
										+ "' alt='"+live.userId+"' class='list1_img'   />"
										+ "<div class='list1_title'><div class='list1_title_top'>"
										+ live.gradeName
										+ "</div><div class='list1_title_bottom'>"
										+ live.name + "</div></div>";
								liveHtml0 += "<div class='list1_right'><img src='/xcviews/images/zan.png' alt='' /><span>"
										+ currentOnlineNumber
										+ "</span><div class='index_price'><span>"+watchStr+"</span>"
										+"</div></div></div></div>";
							}
							odiv.innerHTML = liveHtml0;
							content.appendChild(odiv);
						}
						mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);
						mui('#refreshContainer').pullRefresh().refresh(true);

						//$("#content").scrollTop(1000);
					}else{
					}
				}else{
					var cells = document.body.querySelectorAll('.mui-table-view-cell');
					var num = cells.length;
					console.log(num);
					if(num == "0") {
						document.getElementById("content").innerHTML ="";
						var odiv = document.createElement("div");
						odiv.id = 'content1';
						odiv.innerHTML = '<img style="width:7.675rem;margin-top:4.75rem;" src="/xcviews/images/zhibo_null.png" alt="" class="kongbai" />';
						document.getElementById("content").appendChild(odiv);
					} else {
						mui('#refreshContainer').pullRefresh().endPullupToRefresh(true);
					}
				} 
			});
}
requestLiveList(true);


/**
 * 点击进入主播用户页面
 */
mui("#refreshContainer").on('tap', '.list1_img', function (event) {
	
	//pageNumber
	sessionStorage.setItem("live_pageNumber", pageNumber); 
	
	var ev = this;
	var lecturerId = ev.alt;
	location.href = "/xcviews/html/personage.html?lecturerId="+lecturerId;
});
/**
 * 点击进入详情页
 */
mui("#refreshContainer").on('tap', '.course_img', function (event) {
	var ev = this;
	var courseId = ev.name;
	var lineState = ev.id;
	
	//增加观看记录
	addHistory(courseId,1);
	
	sessionStorage.setItem("livePage", 1); 
	if(lineState == 1){ //需要预约
		location.href = "/xcviews/html/foreshow.html?course_id="+courseId+"";
	}else{
		location.href = "/bxg/xcpage/courseDetails?courseId="+courseId;
	}
});

/**
 * ************************************ 页面刷新下刷新事件
 * **************************************************
 */
mui.init();
mui.init({
	pullRefresh: {
		container: '#refreshContainer',
		down: {
			callback: pulldownRefresh
		},
		up: {
			contentrefresh: '正在加载...',
			callback: pullupRefresh
		}
	}
});
/*mui('.mui-scroll-wrapper').scroll({
	 deceleration: 0.0005, //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
	 indicators: false //是否显示滚动条
});
*/
/**
 * 下拉刷新
 */
function pulldownRefresh() {
	setTimeout(function() {
		requestLiveList(true);
		mui('#refreshContainer').pullRefresh().endPulldownToRefresh(); //refresh completed
	}, 500);
};
var count = 0;
/**
 * 上拉加载具体业务实现
 */
function pullupRefresh() {
	setTimeout(function() {
		requestLiveList(false);
	}, 500);
}


