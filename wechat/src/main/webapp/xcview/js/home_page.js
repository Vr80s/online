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
	

			var myHeight=$(".tjks").height();

			$(".gieTa").height(myHeight);
			
	
	}
	
})
//推荐模块结束

//线下课开始
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
		
				var myHeight=$(".tjks").height();
	
				$(".gieTa").height(myHeight);
				
		
		}
		
},false)

//线下课结束



//直播开始
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
				
		}
		
		
		
},false)

//直播结束



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



})
//JQ预加载分界线----------------------------------------------------------------

