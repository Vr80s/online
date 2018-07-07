	
var openId = getQueryString("openId");
if(isNotBlank(openId)){
    localStorage.setItem("openid",openId);
}

/**
 * 从列表页返回
 */
var before_url = document.referrer;
if(before_url.indexOf("home_page.html")!=-1){
	sessionStorage.setItem("curriculum_blck",1);
}else if(before_url.indexOf("search.html")!=-1){
	sessionStorage.setItem("curriculum_blck",2);
}
/**
 * 返回上一页
 */
function goto_back(){

/*	var curriculum_blck = sessionStorage.getItem("curriculum_blck");
	if(curriculum_blck == 1){
		location.href="home_page.html";
	}else if(curriculum_blck == 2){
		location.href="search.html?search_back=2";
	}*/
	
	//去学堂
	location.href="home_page.html";
}

/**
 * 将上面的url封装为json对象
 */	
var paramsObj = getUrlParamsReturnJson();
var matching = getQueryString('menuType');
var menuTypeArray = new Array();
var courseTypeArray = new Array();
var cityTypeArray = new Array();

/**
 * 默认搜索条件
 */
//var defaultKey = localStorage.getItem("defaultKey");
//if(isNotBlank(defaultKey)){
////	$(".header_seek_main img").html(defaultKey);
////	$(".header_seek_main span").html(defaultKey);
////	$(".span_hide").hide();
//}
/*
 * 点击进入搜索页面
 */
$(".header_seek").click(function(){
	
	location.href='/xcview/html/search.html?search_back=2';
	
//	$(".span_hide").show();
})


/*
 * 赛选条件渲染
 */
requestService("/xczh/classify/listScreen",null,function(data){
	if(data.success==true){
		$('#draw_type_list').html(template('type_list',{items:data.resultObject[0]}));
		/**
		 * 动态生成分类
		 */
		var pagenavi1 ="<li class='find_nav_cur'><a href='javascript: ;' data-title ='0' class='' title='0'>全部</a></li>";
		/**
		 * 动态生成滑动的效果
		 */
		
		// $(".no_class").addClass("no_class"+index+"");

		var box01List = "<li class='li_list'><div class='li_list_main' id='draw_all_query_list'></div><div class='no_class no_class_one no_class_ones no_class0'><img src='../images/no_class.png'/><p>呀~什么也没有，快试试别的吧</p></div></li>"; //代表全部的

		for (var int = 0; int < data.resultObject[0].length; int++) {
			var obj = data.resultObject[0][int];
			var index=int+1;
			pagenavi1 +="<li><a href='javascript: ;' data-title ="+index+" title="+obj.id+">"+obj.name+"</a></li>";		
			// box01List+="<li class='li_list'><div class='li_list_main' data-title ="+index+" id='query_list"+obj.id+"'></div><div class='no_class no_class"+index+"'><img src='../images/no_class.png'/><p>呀~什么也没有，快试试别的吧</p></div><div class='size_show size_show"+index+"' style='font-size:0.2rem;'>我是加载中</div></li>"		
			box01List+="<li class='li_list'><div class='li_list_main' data-title ="+index+" id='query_list"+obj.id+"'></div><div class='no_class no_class"+index+"'><img src='../images/no_class.png'/><p>呀~什么也没有，快试试别的吧</p></div></li>"		
			// box01List+="<li class='li_list'><div class='li_list_main' data-title ="+index+" id='query_list"+obj.id+"'></div><div class='no_class no_class"+index+"'><img src='../images/no_class.png'/><p>呀~什么也没有，快试试别的吧</p></div><div class='um-win um-win"+index+"'><div class='um-content'><div class='spinner'><div class='spinner-container container1'><div class='circle1'></div><div class='circle2'></div><div class='circle3'></div><div class='circle4'></div></div><div class='spinner-container container2'><div class='circle1'></div><div class='circle2'></div><div class='circle3'></div><div class='circle4'></div></div><div class='spinner-container container3'><div class='circle1'></div><div class='circle2'></div><div class='circle3'></div><div class='circle4'></div></div></div></div><div class='um-footer'></div></div></li>"		
		}
		pagenavi1 +="<li class='sideline' style='left: 0px; width: 96px;'></li>";
		$(".box01_list").html(box01List);
		$("#pagenavi1").html(pagenavi1);
		$('#draw_isfree_list').html(template('isfree_list',{items:data.resultObject[1]}));
		$('#draw_course_big_list').html(template('course_big_list',{items:data.resultObject[2]}));
		$('#draw_city_list').html(template('city_list',{items:data.resultObject[3]}));
		$('#draw_live_status_list').html(template('live_status_list',{items:data.resultObject[4]}));
		
		menuTypeArray = data.resultObject[0];
		courseTypeArray = data.resultObject[2];
		cityTypeArray = data.resultObject[3];
	}else{
		alert("条件筛选errot!");
	}
},false)


/**
 * 创建查询参数
 * @param menuType
 * @param isFree
 * @param courseType
 * @param city
 * @param lineState
 */
function createParamsAndQuery(menuType,isFree,courseType,city,lineState,queryKey){
	
	//清空上面的查询条件
	paramsObj = {};
	
	var saisuanstr ="";
	
	var menuTypeAll = $("[class='find_nav_cur'] a").attr("title");
	if(parseInt(menuTypeAll) == 0){
		$(".all_right_type_ones").find(".all_right_type_one").removeClass("all_right_type_one_add");
	}else{
		//从新赋值
		if(isNotBlank(menuType)){
			paramsObj.menuType = menuType;
			for (var int = 0; int < menuTypeArray.length; int++) {
				var array_element = menuTypeArray[int];
				if(menuType == array_element.id){
					$(".all_right_type_ones").find(".all_right_type_one").removeClass("all_right_type_one_add");
					/**
					 * 让筛选条件 ---》 变为选中
					 */
					$(".all_right_type_ones").find(".all_right_type_one").each(function(){
						 var sxmenu = $(this).attr("title");
						 if(sxmenu == menuType){
							 $(this).addClass("all_right_type_one_add");
							 return;
						 }
					});
					saisuanstr += array_element.name+"-";
					break;
				}
			} 
		}
	}

	if(isNotBlank(isFree)){
		paramsObj.isFree = isFree;
		if(isFree==0){
			saisuanstr +="付费-";
		}else{
			saisuanstr +="免费-";
		}
	}
	
	if(isNotBlank(courseType)){
		paramsObj.courseType = courseType;
		
	    if(courseType == 3){
	    	$(".all_mold4").show();
	    }else if(courseType == 4){
	    	$(".all_mold3").show();
	    }
		
		for (var int = 0; int < courseTypeArray.length; int++) {
			var array_element = courseTypeArray[int];
			if(courseType == array_element.id){
				/**
				 * 让筛选条件 ---》 变为选中
				 */
				$("#draw_course_big_list").find(".all_right_type_one").each(function(){
					 var sx_courseType = $(this).attr("title");
					 if(sx_courseType == courseType){
						 $(this).addClass("all_right_type_one_add");
						 return;
					 }
				});
				saisuanstr += array_element.name+"-";
				break;
			}
		}
		
	}
	if(isNotBlank(city)){
		if(!isNotBlank(courseType)){
			$("#draw_course_big_list").find(".all_right_type_one").eq(3).addClass("all_right_type_one_add");
			saisuanstr += "线下课程"+"-";
			/**
			 * 选中线下培训班  --》 然后在选中 所对应的城市
			 */
			$(".all_mold3").show();
		}	
		paramsObj.city = city;
	    var falg = false
		for (var int = 0; int < cityTypeArray.length; int++) {
			var array_element = cityTypeArray[int];
			if(city == array_element.name){
				falg = true;
				$(".all_right_type_twos").find(".all_right_type_one").each(function(){
					 var sx_city = $(this).text();
					 if(city == sx_city){
						 $(this).addClass("all_right_type_one_add");
						 return;
					 }
				})
				saisuanstr += array_element.name+"-";
				break;
			}
		}
	    if(!falg){
	    	if("全国课程" != city){
	    	    saisuanstr +="其他-";
	    	    $(".all_right_type_twos").find(".all_right_type_one").each(function(){
				     var sx_city = $(this).text();
					 if("其他" == sx_city){
						 $(this).addClass("all_right_type_one_add");
						 return;
					 }
			    })
	    	}
	    }
	}
	if(isNotBlank(lineState)){
		paramsObj.lineState = lineState;
		
		
		/**
		 * 显示直播状态 
		 * --》选中直播课程
		 * -->选中直播转态
		 */
		//显示直播状态 
		$(".all_mold4").show();
		
		if(!isNotBlank(courseType)){
			$("#draw_course_big_list").find(".all_right_type_one").eq(2).addClass("all_right_type_one_add");
			saisuanstr += "直播课程"+"-";
		}	
		
		$("#draw_live_status_list").find(".all_right_type_one").each(function(){
			 var sx_lineState = $(this).attr("title");
			 if(lineState == sx_lineState){
				 $(this).addClass("all_right_type_one_add");
				 return;
			 }
		})
		
		if(lineState==1){
			saisuanstr +="直播中-";
		}else if(lineState==2 || lineState==4 || lineState==5){
			saisuanstr +="未直播-";
		}else if(lineState==3){
			saisuanstr +="精彩回放-";
		}
	}
	
	
	if(isNotBlank(queryKey)){
		paramsObj.queryKey = queryKey;
		saisuanstr +=queryKey+"-";
	}
	
	//将查询条件更改----判断有无条件
	if(saisuanstr.length>0){
		saisuanstr = saisuanstr.substring(0, saisuanstr.length-1);
		$(".hint").show();
//		$("#slider1").css("padding-top","70px");
		$(".li_list").removeClass("li_list0");
	}else{
		saisuanstr = "无";
		$(".hint").hide();
		$(".li_list").addClass("li_list0");
//		$("#slider1").css("padding-top","30px");
		
	}
	$("#sxtj").text(saisuanstr);
	return paramsObj;
}

/**
 * 点击确认按钮获取查询进行查询
 */

function submit(){
	
	/**
	 * 在浏览器地址上获取课程信息
	 */
	var menuType = $(".all_mold0  .all_right_type_one_add").attr("title");
	var isFree = $(".all_mold1  .all_right_type_one_add").attr("title");
	var courseType = $(".all_mold2  .all_right_type_one_add").attr("title");
	var city = $(".all_mold3  .all_right_type_one_add").text();
	var lineState = $(".all_mold4  .all_right_type_one_add").attr("title");
	var queryKey = getQueryString('queryKey');
	
	// paramsObj.pageNumber = num;
	paramsObj.pageSize = 1000;
	// paramsObj.downUp = "down";
	
	if(isNotBlank(menuType)){
		paramsObj.menuType =menuType;
	}else{
		delete paramsObj.menuType;
	}
	
	if(isNotBlank(isFree)){
		 paramsObj.isFree = isFree;
	}else{
		delete paramsObj.isFree;
	}
    if(isNotBlank(courseType)){
    	 paramsObj.courseType = courseType;
	}else{
		delete paramsObj.courseType;
	}
    if(isNotBlank(city)){
   	    paramsObj.city = city;
	}else{
		delete paramsObj.city;
	}
    if(isNotBlank(lineState)){
    	paramsObj.lineState = lineState;
   	}else{
   		delete paramsObj.lineState;
   	}
	
    if(isNotBlank(queryKey)){
    	paramsObj.queryKey = queryKey;
   	}else{
   		delete paramsObj.queryKey;
   	}
	
	var checkType = 0;
	//赛选条件选中的那个
	var list = $(".all_right_type_ones").find(".all_right_type_one");
	for(var i = 0; i < list.length; i++) {
		var className = list[i].className;
		if(className.indexOf("all_right_type_one_add")!=-1){
			checkType = i+1;
			break;
		}
    }
	
	var currentCheckType = 0;
	//目前是那个
	var realList = $("#pagenavi1").find("li");
	for(var i = 0; i < realList.length; i++) {
		var className = realList[i].className;
		if(className.indexOf("find_nav_cur")!=-1){
			currentCheckType=i;
			break;
		}
    }
	
	//如果分类变化的话，进行滑动啦
	if(currentCheckType != checkType){
		
		//滑动到第几个	
		slide(checkType);
	//分类没有变化的话，不滑动	
	}else{
		
		createParamsAndQuery(menuType,isFree,courseType,city,lineState,queryKey);
		queryDataByParams(paramsObj,menuType);
	}
}

// 测试加载中
/*var load = {
	start: function (){
		var index = $(".find_nav_cur a").attr("data-title");
		$(".um-win").hide();
		$(".um-win"+index).show();
	},
	end: function(){
		var index = $(".find_nav_cur a").attr("data-title");
		$(".um-win"+index).hide();
	}
}*/
function queryDataByParams(params,data_type){

	// 测试加载中
	// load.start();
	
	// 
	requestService("/xczh/recommend/queryAllCourse",params,function(data){
		if(data.success==true){
			//createListInfo(data,data_type)
			
			 // 测试加载中
			 //load.end();
			
			 if(isNotBlank(data_type)){
					var id = "#query_list"+data_type;
				}else{
					var id = "#draw_all_query_list";
				}
				var data1 ="";
				$(id).html(data1);

				// 判断有无数据显示隐藏背景图
				var index = $(".find_nav_cur a").attr("data-title");
				if(data.resultObject.length<=0){
					$(".li_list_main").css("background","#f8f8f8");
					$(".no_class").hide();
					$(".no_class"+index).show();
				}else{
					$(".li_list_main").css("background","#fff");
					$(".no_class").hide();
				}

				for (var int = 0; int < data.resultObject.length; int++) {
					var item = data.resultObject[int];
					
					var statusImg="";  //视频、音频不同的图片
					if(item.type == 1){
						statusImg+="/xcview/images/tv_auto.png";
					}else if(item.type == 2){
						statusImg+="/xcview/images/frequency.png";
					}else if(item.type == 3){
						statusImg+="/xcview/images/Sinatv_auto.png";
					}else if(item.type == 4){
						statusImg+="/xcview/images/offline.png";
					}
					
					var statusImg1="<img src="+statusImg+"  class='two'  />";
					
					var isFreeStr ="";
					if(item.watchState == 1){
						isFreeStr+="<p class='p0' style='margin-top: 0rem;font-size: 0.2rem;'><span style='margin-top: 0rem;margin-top: -0.11rem;display: block;'>免费</span></p>";
					}else if(item.watchState == 0){
						isFreeStr+="<p class='p0' style='margin-top: -0.02rem;font-size: 0.2rem;'><span style='display: block;float: left;margin-top: -0.09rem;'>"+item.currentPrice+"</span><span class='span' style='display: block;float: left;margin-top: -0.09rem;'>熊猫币</span></p>";
					}
					var typeStr="";
					if(item.type ==3){
						if(item.lineState==1){
							typeStr +="<p class='zhibo_play'>直播中</p>";
//						}else if(item.lineState==2 || item.lineState==3){
						}else if(item.startDateStr.indexOf(":")== -1 ){
							typeStr +="<p class='p5' style='min-width: 1rem;'><img src='/xcview/images/learn.png'><span>"+item.startDateStr+"</span></p>";		
						}else{
							typeStr +="<p class='p5' style='min-width: 1rem;'><img src='/xcview/images/Sinatv_time.png'><span>"+item.startDateStr+"</span></p>";
						}
					}else if(item.type ==4){
						typeStr +="<p class='p2'><img src='/xcview/images/location_four.png' style='width:0.19rem;height:0.24rem;'><span>"+item.city+"</span></p>";
						//alert(typeStr);
					}
					data1+="<div class='li_list_div' >"+
						       "<div class='li_list_one' data-courseId = "+item.id+" data-title="+item.type+" data-watchState="+item.watchState+" data-collection="+item.collection+"   data-lineState="+item.lineState+">"+
							       "<div class='li_list_one_left'>" +
//							          "<img src='"+item.smallImgPath +"' class='one' />" + statusImg1 +
							         "<img src='"+item.smallImgPath+"?imageView2/2/w/212' class='one' />" + statusImg1 +
							      "</div>" +
							      
							      
						           "<div class='li_list_one_right'>" +
							           "<p class='p00'>" +
							           "<span>"+item.gradeName+"</span>" +
							           "<span class='span'>"+item.name+"</span></p>" +
							           "<div class='div'>" + isFreeStr +"<p class='p1'><img src='/xcview/images/population.png' alt=''>" +
							             "<span>"+item.learndCount+"</span></p>"+typeStr+"</div>" +
						            "</div>" +
						             
						            
						         "</div>" +
						     "</div>";
				}
				
				var text = $("#sxtj").text();
				
				
				
				if("免费-直播课程-未直播" == text){
//					alert(data1);
				}else{
					$(id).html(data1);
				}
				$(id).html(data1);
				/*
				 * 点击跳转到单个课程
				 */
				 $(".li_list_div .li_list_one").click(function(){
					
					 var type =$(this).attr("data-title");
					 var courseId =$(this).attr("data-courseId");
					 var watchState=$(this).attr("data-watchState");  //收费付费
					 var collection=$(this).attr("data-collection");  //专辑是否
					 var lineState=$(this).attr("data-lineState");
					 
					if(watchState==1){
						if(type == 1 || type == 2){
							//增加学习记录
							requestService("/xczh/history/add",{courseId:courseId, recordType:1},function(data) {
								 console.log("增加学习记录");
							}) 
							if(collection=="true"){
								location.href = "/xcview/html/live_select_album.html?course_id="+courseId;
							}else{
								location.href = "/xcview/html/live_audio.html?my_study="+courseId;
							}
						}else if(type == 4){
							location.href = "/xcview/html/school_class.html?course_id="+courseId;
						}else if(type == 3){ //直播
							common_jump_all(courseId,watchState,lineState);
						}
					}else{
						if(type == 1 || type == 2){
							location.href = "/xcview/html/school_audio.html?course_id="+courseId;
						}else if(type == 4){
							location.href = "/xcview/html/school_class.html?course_id="+courseId;
						}else if(type == 3){ //直播
							common_jump_all(courseId);
						}
					}
				})
		}else{
			$(".no_class").show();
			$(".li_list_main").css("background","#f8f8f8");
			alert("查询数据结果error!");
		}
	})
}


/**
 * 这里先请求出所有的
 */
function typeQuery(){

    var menuType = $("[class='find_nav_cur'] a").attr("title");
    
	// paramsObj.pageNumber = 1;
	paramsObj.pageSize = 1000;
	// paramsObj.downUp = "down";
    
    if((menuType ==0 && matching == 'goodCourse') || (menuType ==0 && matching == 'newCourse')){
    	paramsObj.menuType= matching;
    	queryDataByParams(paramsObj);
    }else if(menuType!=0){
    	paramsObj.menuType= menuType;
        queryDataByParams(paramsObj,menuType);
    }else{
    	//删除这个条件
    	delete paramsObj.menuType;
    	queryDataByParams(paramsObj);
    }
  
    createParamsAndQuery(paramsObj.menuType,paramsObj.isFree,paramsObj.courseType,
			paramsObj.city,paramsObj.lineState,paramsObj.queryKey);
}
//顶部搜索框获取文字显示
function getQueryString(key){
        var reg = new RegExp("(^|&)"+key+"=([^&]*)(&|$)");
        var result = window.location.search.substr(1).match(reg);
        return result?decodeURIComponent(result[2]):null;
    }
var urlAttribute=getQueryString('queryKey')
if (urlAttribute=='' || urlAttribute== null) {
	
} else{
	$('.header_seek_main .span_hide').hide();
//	$('.header_seek_main').append('<span style='margin-left: 0.38rem;margin-top: -0.6rem;'>' + urlAttribute + '</span>');
	$('.header_seek_main').append('<span>' + urlAttribute + '</span>');
	
}


/**
 * ************************************ 页面刷新下刷新事件
 * **************************************************
 */
//mui.init();
//mui.init({
//	pullRefresh: {
//		container: '#refreshContainer',
//		down: {
//			callback: pulldownRefresh
//		},
//		up: {
//			contentrefresh: '正在加载...',
//			callback: pullupRefresh
//		}
//	}
//});
//
///**
// * 下拉刷新
// */
//function pulldownRefresh() {
//    num = 1;
//    
//    
//  
//    
//    setTimeout(function() {
//        //refresh(num,10,'down');
//    	
//    	    var menuType = $("[class='find_nav_cur'] a").attr("title");
//    	    
//    	    
//    		paramsObj.pageNumber = num;
//	    	paramsObj.pageSize = 10;
//	    	paramsObj.downUp = "down";
//    	    
//    	    if((menuType ==0 && matching == 'goodCourse') || (menuType ==0 && matching == 'newCourse')){
//    	    	paramsObj.menuType= matching;
//    	    	queryDataByParams(paramsObj);
//    	    }else if(menuType!=0){
//    	    	paramsObj.menuType= menuType;
//    	        queryDataByParams(paramsObj,menuType);
//    	    }else{
//    	    	//删除这个条件
//    	    	delete paramsObj.menuType;
//    	    	queryDataByParams(paramsObj);
//    	    }
//    	
//    }, 500);
//};
//var count = 0;
///**
// * 上拉加载具体业务实现
// */
//function pullupRefresh() {
//    num++;
//    setTimeout(function() {
//    	
//	    var menuType = $("[class='find_nav_cur'] a").attr("title");
//	    paramsObj.pageNumber = num;
//    	paramsObj.pageSize = 10;
//    	paramsObj.downUp = "up";
//   	    
//   	    if((menuType ==0 && matching == 'goodCourse') || (menuType ==0 && matching == 'newCourse')){
//   	    	paramsObj.menuType= matching;
//   	    	queryDataByParams(paramsObj);
//   	    }else if(menuType!=0){
//   	    	paramsObj.menuType= menuType;
//   	        queryDataByParams(paramsObj,menuType);
//   	    }else{
//   	    	//删除这个条件
//   	    	delete paramsObj.menuType;
//   	    	queryDataByParams(paramsObj);
//   	    }
//    	
//    	//queryDataByParams(num,10,'up');
//    }, 500);
//}



    
//从分类跳转过来并在搜索框获取文字显示

//  var top_nav=getQueryString('menuType')
//  if(top_nav == 208){
// 		$('.header_seek').append('<span>' + "脉诊大全"+ '</span>');	
//  }else if(top_nav == 205){
// 		$('.header_seek').append('<span>' + "各家综合" + '</span>');		
//  }else if(top_nav == 204){
// 		$('.header_seek').append('<span>' + "古籍经典" + '</span>');		
//  }else if(top_nav == 203){
// 		$('.header_seek').append('<span>' + "药膳食疗" + '</span>');		
//  }else if(top_nav == 202){
// 		$('.header_seek').append('<span>' + "美容养生" + '</span>');		
//  }else if(top_nav == 202){
// 		$('.header_seek').append('<span>' + "各式推拿" + '</span>');		
//  }else if(top_nav == 200){
// 		$('.header_seek').append('<span>' + "针灸疗法" + '</span>');		
//  }
//JQ预加载分界线----------------------------------------------------------------

