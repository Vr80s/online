	
	
var paramsObj = getUrlParamsReturnJson();

var courseTypeArray = new Array();	
/*
 * 条件渲染
 */
requestService("/xczh/classify/listScreen",null,function(data){
	if(data.success==true){
		$('#draw_type_list').html(template('type_list',{items:data.resultObject[0]}));
		/**
		 * 动态生成分类
		 */
		var pagenavi1 ="<li class='find_nav_cur'><a href='javascript: ;' class='' title='0'>全部</a></li>";
		/**
		 * 动态生成滑动的效果
		 */
		var box01List = "<li class='li_list'><div class='li_list_main' id='draw_all_query_list'></div></li>"; //代表全部的
		for (var int = 0; int < data.resultObject[0].length; int++) {
			var obj = data.resultObject[0][int];
			pagenavi1 +="<li><a href='javascript: ;' data-title ="+int+" title="+obj.id+">"+obj.name+"</a></li>";
		
			box01List+="<li class='li_list'><div class='li_list_main' data-title ="+int+" id='query_list"+int+"'></div></li>"
		
		}
		pagenavi1 +="<li class='sideline' style='left: 0px; width: 96px;'></li>";
		
		$(".box01_list").html(box01List);
		$("#pagenavi1").html(pagenavi1);
		$('#draw_isfree_list').html(template('isfree_list',{items:data.resultObject[1]}));
		$('#draw_course_big_list').html(template('course_big_list',{items:data.resultObject[2]}));
		$('#draw_city_list').html(template('city_list',{items:data.resultObject[3]}));
		$('#draw_live_status_list').html(template('live_status_list',{items:data.resultObject[4]}));
	
		courseTypeArray = data.resultObject[0];
	}else{
		alert("条件筛选errot!");
	}
},false)


function queryDataByParams(params,data_type){
	requestService("/xczh/recommend/queryAllCourse",params,function(data){
		if(data.success==true){
			if(stringnull(data_type)){
				var id = "#query_list"+data_type;
				//$(id).html(template('all_query_list',{items:data.resultObject}));
			}else{
				//$('#draw_all_query_list').html(template('all_query_list',{items:data.resultObject}));
				var id = "#draw_all_query_list";
			}
			var data1 ="";
			for (var int = 0; int < data.resultObject.length; int++) {
				var item = data.resultObject[int];
				
				
				if(item.type == 1){
					
				}else if(item.type == 2){
					
				}
				
				data1+="<div class='li_list_div'>"+
					   "<div class='li_list_one'>"+
						   "<div class='li_list_one_left'>" +
						  "<img src='"+item.smallImgPath+"' class='one' />" +
						  
						   "<img src='/xcview/images/tv_auto.png' class='two' />" +
						  
						   "</div>" +
					   "<div class='li_list_one_right'>" +
						   "<p class='p00'>" +
						   "<span>"+item.name+"</span>:" +
						   "<span>"+item.gradeName+"</span></p>" +
						   "<div class='div'>" +
						  "<p class='p0'><span>免费</span></p>" +
						  "<p class='p1'><img src='/xcview/images/population.png' alt=''><span>12345</span></p>" +
						    "</div>" +
					  "</div>" +
					  "</div><div>";
			}
			$(id).html(data1);
		}else{
			alert("查询数据结果errot!");
		}
	},false)
}
queryDataByParams(paramsObj);

/**
 * 这里先请求出所有的
 */

function typeQuery(){
//	$(".find_nav_list li").each(function(){
//		$(".sideline").css({left:0});
//		$(".find_nav_list li").eq(0).addClass("find_nav_cur").siblings().removeClass("find_nav_cur");
//	});
    var typeId = $("[class='find_nav_cur'] a").attr("title");
    var data_type = $("[class='find_nav_cur'] a").attr("data-title");
    if(typeId!=0){
    	 paramsObj.menuType= typeId;
    }
    queryDataByParams(paramsObj,data_type);
}

//requestService("/xczh/classify/listScreen",null,function(data){
//	if(data.success==true){
//		$('#draw_type_list').html(template('type_list',{items:data.resultObject[0]}));
//		$('#draw_isfree_list').html(template('isfree_list',{items:data.resultObject[1]}));
//		$('#draw_course_big_list').html(template('course_big_list',{items:data.resultObject[2]}));
//		$('#draw_city_list').html(template('city_list',{items:data.resultObject[3]}));
//		$('#draw_live_status_list').html(template('live_status_list',{items:data.resultObject[4]}));
//	
//		courseTypeArray = data.resultObject[0];
//	}else{
//		
//		alert("条件筛选errot!");
//	}
//},false)







//JQ预加载分界线----------------------------------------------------------------

