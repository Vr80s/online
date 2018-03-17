


//搜索
$("#index_search").focus(function(){
	sessionStorage.setItem("queryType",0);
	location.href = "/bxg/xcpage/queryResult";
});


/**
 * 跳转列表页：视频、音频、直播
 * @param type
 */
function viewJump(type){
	if(type == 1){
		location.href = "/xcviews/html/index.html";
	}else if(type == 2){
		location.href = "/xcviews/html/frequency.html?multimedia_type=1";
	}else{
		location.href = "/xcviews/html/frequency.html?multimedia_type=2";
	}
}



function gotoDetails(courseObj){
	
	var courseId = courseObj.courseId;
	var type = courseObj.type;
	/**
	 * 添加观看记录
	 */
	addHistory(courseId,type);
	
	if(courseObj.type == 1){
		//增加观看记录
		sessionStorage.setItem("livePage", 0); 
		if(courseObj.lineState == 2){ //需要预约
			location.href = "/xcviews/html/foreshow.html?course_id="+courseId+"";
		}else{
			location.href = "/bxg/xcpage/courseDetails?courseId="+courseId;
		}
	}else{
		sessionStorage.setItem("bunchPage", 0);
		location.href = "/xcviews/html/particulars.html?courseId="+ courseId+"&multimedia_type="+courseObj.multimediaType;
	}
}


/**
 * 点击更多
 */
$(".more_btm").click(function(){
	var aBtn=$('.line_top_right').children();
	for(i=0;i<aBtn.length;i++){
		var cls =aBtn[i].className;
		if(cls == "index_list_div1"){
			if(i==0){//直播列表
				location.href = "/xcviews/html/index.html";
			}else if(i==1){//视频
				location.href = "/xcviews/html/frequency.html?multimedia_type=1";
			}else if(i==2){//音频
				location.href = "/xcviews/html/frequency.html?multimedia_type=2";
			}
		}
	}
})

/**
 * 请求banner   -- 两个
 */
var bannerList = "";
requestService("/bxg/binner/list",null, 
    function(data) {
		if(data.success){
			bannerList = data.resultObject;
			var result = data.resultObject;
			var str ="";
			//for (var int = 0; int < result.length; int++) {
			
		    for (var int = result.length-1;int >=0;int--) {	
				
				var wb = result[int];
				str+="<li class='sw-slide' onclick='jumpBannerUrl("+int+")'  >"+
		            "<img src='"+wb.img_path+"' alt='Concept for children game'>" +
		          "</li>";
			}
			$(".sw-slides").html(str);
		}else{
			alert("网络异常");
		};
},false)



function jumpBannerUrl(i) {
	if(bannerList[i].url=='')return;
	
	if(i==1){
		//判断这个课程的状态
		requestService("/bxg/live/liveDetails",{course_id : 609}, 
			    function(data) {
					if(data.success){
						var result = data.resultObject;
						if(result.lineState == 2){  //预告
							location.href="/xcviews/html/foreshow.html?course_id=609";
						}else if(result.lineState == 1){ //直播中
							location.href="/bxg/xcpage/courseDetails?courseId=609";
						}else if(result.lineState == 3){ //直播回放
							location.href="/bxg/xcpage/courseDetails?courseId=609";
						}
					}else{
						alert("网络异常");
					};
		},false)
	}else if(i==0){
		location.href=bannerList[i].url;
	}
}


/**
 * 直播、视频、音频的点击事件
 */


/**
 * 线下培训班
 * 
 * 		String keyWord = req.getParameter("keyWord");
		String s = req.getParameter("pageNumber");
		String e = req.getParameter("pageSize");
 * 
 */
var xxpxbUrlParams ={
	pageNumber:1,
	pageSize:5
}
requestService("/bxg/bunch/offLineClass",xxpxbUrlParams, 
    function(data) {
		if(data.success){
			var result = data.resultObject;
			var str ="";

			for (var int = result.length-1;int >=0;int--) {
				
				var wb = result[int];
				/**
				 * <div class="offline_bto_one"><a href="javascript: ;">
				 * <img src="/xcviews/images/xianxia_03.jpg" alt="" ></a></div>
				 */
				str+= "<div class='offline_bto_one'><a href='javascript: ;'>"+
                "<img src='"+wb.smallImgPath+"'  alt='' onclick='opitem("+wb.id+")' ></a></div>";
			}
			
		/*	for(var i=a.length-1;i>=0;i--){  
				
				console.log()i
				
			}  
			*/
			
			
			$("#partner").html(str);
		}else{
			alert("网络异常");
		};
})

  function opitem(id){
        location.href='/xcviews/html/training.html?id='+id;
  }

/**
 * 线上课程开始
 */
function onlineCourse(type){
	var url = "";
	var urlParam ={
			pageNumber:1,
			pageSize:6
	};
	if(type == 1){ //直播
		url = "/bxg/live/list";
	}else{//音频
		url = "/bxg/bunch/list";
		var multimedia_type = Number(type)-1;
		urlParam.multimedia_type=multimedia_type;
		urlParam.menu_id=0;
	}
	requestService(url,urlParam, 
	    function(data) {
			if(data.success){
				var result = data.resultObject;
				var str ="";
				for (var int = 0; int < result.length; int++) {
					var course = result[int];
					var watchStr ="";
					//需要判断是否加密，需要判断
					if(course.watchState == 0){  // watchState ： 0 免费   1 收费  2 密码 
						watchStr ="免费";
					}else if(course.watchState == 1){
						watchStr ="￥"+course.currentPrice;
					}else if(course.watchState == 2){
						watchStr ="加密";
					}
					/*
					 * 用户判断是音频呢还是视频呢
					 */
					course.multimediaType=urlParam.multimedia_type;

					str+="<div class='public1_list' onclick='gotoDetails("+JSON.stringify(course)+")' >";
					    if(type == 1){
					    	 /**
				             * 判断是直播呢，还是回放呢
				             */
							var samll = "";
							if(course.lineState == 3){ // 1 直播中，2 预告， 3 回放
								samll +="<div class='public1_img_opcity'><div class='public1_img_opcity_bg'></div><img src='/xcviews/images/huifang.png' alt=''><span>回放</span></div>";
							}else if(course.lineState == 2){
								samll +="<div class='public1_img_bg'><div class='public1_img_bg_color'></div><span>直播预告"+course.startTime.substring(0,10)+"</span></div>";
							}else if(course.lineState == 1){
								samll +="<div class='public1_img'><img src='/xcviews/images/tv0.png' alt=''><span>直播中</span></div>";
							}
					    	str+=samll;
					    }
				    str+="<div class='public1_list_img'>"+
							"<img src='"+course.smallImgPath+"' alt=''>"+
						"</div>"+
						"<div class='public1_title'>"+course.gradeName+"</div>"+
						"<div class='public1_cen'>"+
							"<div class='public1_cen_left'>"+course.name+"</div>"+
							"<div class='public1_cen_right'>"+watchStr+"</div>"+
						"</div>"+
			        "</div>";
				}
				$("#public1").html(str);
			}else{
				/*alert("请求有误");*/
				
			};
	})
}