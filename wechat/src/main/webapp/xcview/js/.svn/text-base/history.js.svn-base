$(".history_span_one").click(function(){
	$(this).addClass('history_span_addclass');
	$(".history_span_two").removeClass('history_span_addclass');
	$(".history_span_three").removeClass('history_span_addclass');
	$(".zhibo").show();

	historyCommon(1,true);
});
$(".history_span_two").click(function(){
	$(this).addClass('history_span_addclass');
	$(".history_span_one").removeClass('history_span_addclass');
	$(".history_span_three").removeClass('history_span_addclass');
	
	historyCommon(2,true);
});
$(".history_span_three").click(function(){
	$(this).addClass('history_span_addclass');
	$(".history_span_one").removeClass('history_span_addclass');
	$(".history_span_two").removeClass('history_span_addclass');
	
	historyCommon(3,true);
});

/**
 * 
 */
//点击返回主页面
document.getElementById("history_img").addEventListener("tap",function() {
	var historyType =sessionStorage.getItem("historyType");
	if(historyType ==1){
		//跳转要调整的地方
		location.href = "/bxg/page/index/null/null";
	}else{
		historyType--;
		location.href = "/xcviews/html/frequency.html?multimedia_type="+historyType;
	}
})

/**
 * 在观看记录中进行看的时候
 */
var pageNumber =1;
var pageSize =20;

var type = sessionStorage.getItem("historyType");

/**
 * 清空当前type下的内容
 */
function emptyHistoryByType(){
	//history_span_addclass
	var obj =  $(".history_span_addclass")[0].className;
	if(obj.indexOf("history_span_one")!=-1){
	    type =1;
	}else if(obj.indexOf("history_span_two")!=-1){
		type =2;
	}else if(obj.indexOf("history_span_three")!=-1){
		type =3;
	}
	requestService("/bxg/history/empty", {
		type:type
	}, function(data) {
		if (data.success) {
			historyCommon(type,true);
			console.log("清空成功");
		}else{
			console.log("清空失败");
		}
	})
}

var dataLength = 0;
/**
 * 默认加载主播
 */

function historyCommon(type1,downOrOn){
	type  = type1
	if(downOrOn){
		pageNumber = 1;
	}else{
		pageNumber = pageNumber+1;
	}
	requestService("/bxg/history/list", {
		pageNumber:pageNumber,
		pageSize:pageSize,
		type:type
	}, function(data) {
		if (data.success) { 
			var content = document.getElementById("zhibo");
			if(downOrOn){
				content.innerHTML = "";
			}
			var objList = data.resultObject;
			
			dataLength = objList.length;
			
			if (objList.length>0) {
				$('.null_kongbai_cen').removeClass('null_kongbai_cen1');
				
				for (var int = 0; int < objList.length; int++) {
					var odiv = document.createElement("div");
					odiv.className = "wm-block mui-table-view-cell";
					odiv.id = 'div' + int;
					var liveHtml0 ="";
					var course = objList[int]; 
	        		liveHtml0 += "<div class='zhibo_list' align="+course.type+" name="+course.type+" title="+course.lineState+"  id="+course.courseId+" >";
	    			liveHtml0 += "<img src="+course.smallimgPath+" alt='' />";
	        		liveHtml0 += "<div class='zhibo_right'>";
	        		liveHtml0 += "<div class='zhibo_list_title'><a href='javascript: ;'>"+course.gradeName+"</a></div>";
	        		liveHtml0 += "<div class='zhibo_list_size'><img src='"+course.teacherHeadImg+"' alt='' />" +
	        				"<span class='zhibo_list_size1_span'>"+course.lecturerName+"</span><span class='zhibo_list_time'>"+course.timeDifference+"</span></div>";
	        		liveHtml0 += "</div></div>";
	        		odiv.innerHTML = liveHtml0;
					content.appendChild(odiv);
				}
				mui('#refreshContainer').pullRefresh().endPullupToRefresh(false);
				mui('#refreshContainer').pullRefresh().refresh(true);
			}else{
				var cells = document.body.querySelectorAll('.mui-table-view-cell');
				var num = cells.length;
				console.log(num);
				if(num == "0") {
					$('.null_kongbai_cen').addClass('null_kongbai_cen1');
					$('body').css('background','#efefef');
				} else {
					mui('#refreshContainer').pullRefresh().endPullupToRefresh(true);
					$('body').css('background','url(/xcviews/images/index_bg.jpg)');
				}
			}
		}else{
		}
	})
}


$(".history_img_right").click(function(){
	if(dataLength >0 ){
		$(".history_bg").show();
	}
	
});
$(".history_bg_bto1").click(function(){
	$(".history_bg").hide();
});
$(".history_bg_bto2").click(function(){
	$(".history_bg").hide();
});


if(type ==2){
	$(".history_span_two").click();
}else if(type ==3){
	$(".history_span_three").click();
}else{
	$(".history_span_one").click();
	type = 1;
}
mui("#refreshContainer").on('tap', '.zhibo_list', function (event) {
	var ev = $(this);
	// type='"+course.type+"' lineState='"+course.lineState+"'  id=
	var type = ev.context.align;
	var lineState = ev.context.title;
	var courseId = ev.context.id;
	
	//type lineState couserId
	//name='"+course.type+"' class='"+course.lineState+"'  id='"+course.id+"'
	if(type == 1){//直播
		sessionStorage.setItem("historyType", 1);
		sessionStorage.setItem("livePage", 2);
		if(lineState == 1){ //需要预约
			location.href = "/xcviews/html/foreshow.html?course_id="+courseId+"";
		}else{
			location.href = "/bxg/xcpage/courseDetails?courseId="+courseId+"";
		}
	}else{ //视频、银屏
		if(type==2){
			sessionStorage.setItem("historyType", 2);
		}else if(type==3){
			sessionStorage.setItem("historyType", 3);
		}
		sessionStorage.setItem("bunchPage", 2);
		location.href = "/xcviews/html/particulars.html?courseId="+courseId;
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
/**
 * 下拉刷新
 */
function pulldownRefresh() {
	setTimeout(function() {
		historyCommon(type,true);
		mui('#refreshContainer').pullRefresh().endPulldownToRefresh(); //refresh completed
	}, 500);
};
var count = 0;
/**
 * 上拉加载具体业务实现
 */
function pullupRefresh() {
	setTimeout(function() {
		historyCommon(type,false);
	}, 500);
}

