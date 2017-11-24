/**
 * 
 */
//点击搜索进入关键字搜索页面吧....
document.getElementById("grabble_img").addEventListener("tap",function() {
	var queryType = sessionStorage.getItem("queryType");
	if(queryType == 0){
		//跳转要调整的地方
		location.href = "/bxg/page/index/null/null";
	}else{
		location.href = "/xcviews/html/frequency.html?multimedia_type="+queryType;
	}
	
})

function courseDetails1(courseObj,page){
	/**
	 * 这个地方只需要判断是否已经预约就行了..
	 *  就直播需要预约啦。其他的不需要啦
	 */
	
	if(courseObj.type == 1){//直播
		sessionStorage.setItem("queryType", 1);
		sessionStorage.setItem("livePage", 3);
		if(lineState == 2){ //需要预约
			location.href = "/xcviews/html/foreshow.html?course_id="+courseId+"";
		}else{
			location.href = "/bxg/xcpage/courseDetails?"+courseId+"";
		}
	}else{ //视频、银屏
		if(courseObj.type==2){
			sessionStorage.setItem("queryType", 2);
		}else if(courseObj.type==3){
			sessionStorage.setItem("queryType", 3);
		}
		sessionStorage.setItem("bunchPage", 3);
		location.href = "/xcviews/html/particulars.html?courseId="+courseId;
	}
	
	
}

function queryResult(){
	var result_input = document.getElementById("result_input").value;
	var result ="";
	if (stringnull(result_input)) {
		result = result_input;
	    $("#search_after").show();
	    $("#search_before").hide();
	} else {
		$("#search_before").show();
		$("#search_after").hide();
		result = "";
	}
	requestService("/bxg/live/listKeywordQuery", {
		keyword : result
	}, function(data) {
		if (data.success) { //去详情页面
			var allMap = data.resultObject;
			var bozhuList = allMap.bozhu;
			var zhiboList = allMap.zhibo;
			var bunchList =	allMap.bunch;	
			if(bozhuList.length==0&&zhiboList.length==0&&bunchList.length==0){
				 $("#hot_hot").hide();
				 $('.non_payment_null').css('display','block');
				 $('.non_payment_null').addClass('non_payment_nul_img');
				 return;
			}else{
				 $("#hot_hot").show(); $('.non_payment_null').css('display','none');
			}
			var bozhuHtml ="";var zhiboHtml ="";var bunchHtml ="";
			
			if(result == ""){ //说明搜索结果是null的，是搜索前页面
				for (var int = 0; int < bozhuList.length; int++) {
					var bozhu = bozhuList[int];
					bozhuHtml +="<li class='teacher_li' title="+bozhu.userId+"><a href='javascript: ;'><img src='"+bozhu.headImg+"'"+
							"<br><p class='teacher_name'>"+bozhu.name+"</a></li>";
				}
				 var bozhu_result = document.getElementById("bozhu_result_before");
				 bozhu_result.innerHTML =bozhuHtml;
			}else{
				for (var int = 0; int < bozhuList.length; int++){
					var bozhu = bozhuList[int];
					bozhuHtml +="<li class='teacher_li' title="+bozhu.userId+" ><a href='javascript: ;'><img src='"+bozhu.headImg+"' alt='' />";
					bozhuHtml +="<div class='result_zhibo_ul_div'>"+bozhu.name+"<br /> ";
					bozhuHtml +="<p>粉丝：<span>"+bozhu.count+"</span></p></div></a></li>";
				}
				 var bozhu_result = document.getElementById("bozhu_result_after");
				 
				 bozhu_result.innerHTML =bozhuHtml;
			}
			
			 for (var int = 0; int < zhiboList.length; int++) {
				var zhibo = zhiboList[int];
				zhiboHtml +="<div class='zhibo_list1' onclick='courseDetails("+zhibo.id+","+zhibo.lineState+")' >";
				zhiboHtml +="<img  src='"+zhibo.smallImgPath+"' alt='' class='zhibo_list1_img' />";
				zhiboHtml +="<div class='zhibo_right1'><div class='zhibo_list_title1'>" +
						"<a href='javascript: ;'>"+zhibo.gradeName+"</a></div>";
				zhiboHtml +="<div class='zhibo_list_size1'>" +
				"<img src='"+zhibo.headImg+"' alt='' /><span>"+zhibo.name+"</span></div></div></div>";
			}
			 var zhibo_result = document.getElementById("zhibo_result");
			 zhibo_result.innerHTML =zhiboHtml;
			 
			 for (var int = 0; int < bunchList.length; int++) {
				var bunch = bunchList[int];
				bunchHtml +="<div class='zhibo_list1' onclick='courseDetails("+bunch.id+","+null+")' >";
				bunchHtml +="<img  src='"+bunch.smallImgPath+"' alt='' class='zhibo_list1_img' />";
				bunchHtml +="<div class='zhibo_right1'><div class='zhibo_list_title1'>" +
						"<a href='javascript: ;'>"+bunch.gradeName+"</a></div>";
				bunchHtml +="<div class='zhibo_list_size1'>" +
				"<img src='"+bunch.headImg+"' alt='' /><span>"+bunch.name+"</span></div></div></div>";
			 }
			 var kecheng_result = document.getElementById("kecheng_result");
			 kecheng_result.innerHTML =bunchHtml;
			 
		}else{
		  
		}
	})
}
queryResult();

/**
 * 观看视频详情
 */
function courseDetails(courseid,lineState){
	if(stringnull(lineState)){
		if(lineState !=2 ){
			location.href = "/bxg/xcpage/courseDetails?courseId="+courseid;
			return;
		}else{
			location.href = "/xcviews/html/foreshow.html?course_id=" + courseid;
			return;
		}
	}else{
		location.href = "/xcviews/html/particulars.html?courseId=" + courseid;
		return;
	}
}


/**
 * 点击主播头像到主播页面
 */
mui("#search_before").on('tap', '.teacher_li', function (event) {
	var ev = this;
	var lecturerId = ev.title;
	location.href = "/xcviews/html/personage.html?lecturerId="+lecturerId;
});

mui("#search_after").on('tap', '.teacher_li', function (event) {
	var ev = this;
	var lecturerId = ev.title;
	location.href = "/xcviews/html/personage.html?lecturerId="+lecturerId;
});





