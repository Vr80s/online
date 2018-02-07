$(function(){
//	分类渲染
	var noNumber='<p style="font-size:15px;text-aline:center;">暂无数据</p>'
requestService("/bxg/bunch/schoolClass",null,function(data){
	if(data.success==true){
		$('#classify_course_type').html(template('my_data0',{items:data.resultObject[0]}));
		$('#classify_special_type').html(template('my_data1',{items:data.resultObject[1]}));
		$('#classify_mold_type').html(template('my_data2',{items:data.resultObject[2]}));
	}
	else{
		$("#classify_mold_type").html(template.compile(noNumber))
	}
})


//推荐模块开始
//轮播/大师课/名医渲染
requestService("/xczh/recommend/recommendTop",null, 
    function(data) {
		if(data.success){
//大师课
//		console.log(data);
    	$("#slide_one").html(template('nav_list',{items:data.resultObject.project.records}))
//名医
    	$("#phy_box").html(template('wrap_phy',{items:data.resultObject.doctorList}))
    	
    	
		
//轮播
			var result = data.resultObject.banner.records;
			var str ="";
			for (var int = 0; int < result.length; int++) {
				var wb = result[int];
				str+="<li class='sw-slide'>"+
		            "<img src='"+wb.imgPath+"' alt='Concept for children game'>" +
		          "</li>";
			}
			$("#sw-slides").html(str);
		}else{
			alert("网络异常");
		};
},false)
//精品课程
requestService("/xczh/recommend/recommendCourse",null,function(data) {
	if(data.success==true){
			
//		console.log(data)
    	$(".first_box").html(template('shipin',{items:data.resultObject}))
    	$(".careful_class").click(function(){
    		var data_num=$(this).attr("menuType");
			window.location.href="curriculum_table.html?menuType="+data_num+"";
		})
    	
			var myHeight=$(".tjks").height();
			$(".gieTa").height(myHeight);
	
	}
		
	
})
//推荐模块结束

//线下课开始-------------------------------------------------------------------
requestService("/xczh/bunch/offLine",null, 
    function(data) {
		if(data.success){
//			各省城市                                                        											//跟参数
    		$("#xx_slide_one").html(template('xx_nav_list',{items:data.resultObject.cityList.records}))
//轮播
			var result = data.resultObject.banner.records;
			var str ="";
			for (var int = 0; int < result.length; int++) {
				var wb = result[int];
				str+="<li class='sw-slide'>"+
		            "<img src='"+wb.imgPath+"' alt='Concept for children game'>" +
		          "</li>";
			}
			$("#xx-slides").html(str);
		}else{
			alert("网络异常");
		};
		
		if(data.success==true){
	    	$(".acupunctures").html(template('acupunctures',{items:data.resultObject.allCourseList}))
		
//				var myHeight=$(".tjks").height();
//	
//				$(".gieTa").height(myHeight);
				
		
		}
		
},false)
//线下课banner下的城市点击
$(".go_search").click(function(){
	var city_class=$(this).find("span").text();
	window.location.href="curriculum_table.html?city="+city_class+"";
})
//线下课标题点击
$(".all_class").click(function(){
	var all_class=$(this).text();
//	if(all_class=='全国课程'){
//		all_class='';
//	}
	window.location.href="curriculum_table.html?city="+all_class+"";
})
//线下课结束------------------------------------------------------



//直播开始-----------------------------------------------------------
requestService("/xczh/live/onlineLive",null, 
    function(data) {
		if(data.success){
	
//轮播
			var result = data.resultObject.banner.records;
			var str ="";
			for (var int = 0; int < result.length; int++) {
				var wb = result[int];
				str+="<li class='sw-slide'>"+
		            "<img src='"+wb.imgPath+"' alt='Concept for children game'>" +
		          "</li>";
			}
			$("#zb-slides").html(str);
		}else{
			alert("网络异常");
		};
		
		if(data.success==true){
	    	$(".newests").html(template('newests',{items:data.resultObject.allCourseList}))
				/*var myHeight=$(".tjks").height();
				$(".gieTa").height(myHeight);*/
				var lineState=$(this).attr("lineState")
			$(".newest_title").click(function(){
				window.location.href="curriculum_table.html?menuType="+lineState+"";				
			})
			
				
		}
		
		
		
},false)

//直播结束---------------------------------------------------------------



//听课开始

requestService("/xczh/bunch/listenCourse",null, 
    function(data) {
		if(data.success){
	
//轮播
			var result = data.resultObject.banner.records;
			var str ="";
			for (var int = 0; int < result.length; int++) {
				var wb = result[int];
				str+="<li class='sw-slide'>"+
		            "<img src='"+wb.imgPath+"' alt='Concept for children game'>" +
		          "</li>";
			}
			$("#tk-slides").html(str);
		}else{
			alert("网络异常");
		};
		
		if(data.success==true){
 	    	$(".lecturess").html(template('lectures',{items:data.resultObject.listenCourseList}))
				/*var myHeight=$(".tjks").height();
	
				$(".gieTa").height(myHeight);*/
				
		}
		
		
		
},false)

//听课结束



//swiper医师滑动
var swiper = new Swiper('.swiper-container', {
    slidesPerView: 5,
    paginationClickable: true,
    spaceBetween: 10
});
//swiper线下课省滑动
var swiper = new Swiper('#swiper1', {
    slidesPerView: 5,
    paginationClickable: true,
    spaceBetween: 10
});

//判断是否购买及主播本人


})
//JQ预加载分界线----------------------------------------------------------------

function jump(id){
	requestService("/xczh/course/details?courseId="+id,null,function(data) {
		debugger;
		var course = data.resultObject;
		if(course.watchState == 0||course.watchState == 1){
			if(course.type==1||course.type==2){
//				视频音频购买
				location.href="school_audio.html?course_id="+id
			}else if(course.type==3){
//				直播购买
				location.href="school_play.html?course_id="+id
			}else{
//				线下课购买
				location.href="school_class.html?course_id="+id
			}			
		}else if(course.watchState == 2||course.watchState == 3){
			if(course.type==1||course.type==2){
				if(course.collection){
//					专辑视频音频播放页
				location.href="live_select_album.html?course_id="+id					
				}else{
//					单个视频音频播放
				location.href="live_audio.html?my_study="+id					
				}
			}else if(course.type==3){
//					播放页面
				location.href="live_audio.html?my_study="+id									
			}else{
//					线下课页面
				location.href="live_class.html?my_study="+id									
			}		
		}
	})
}