	
/**
 * 将上面的url封装为json对象
 */	
var paramsObj = getUrlParamsReturnJson();

var menuTypeArray = new Array();
var courseTypeArray = new Array();
var cityTypeArray = new Array();

/**
 * 默认搜索条件
 */
var defaultKey = localStorage.getItem("defaultKey");
if(stringnull(defaultKey)){
	$(".header_seek_main img").html(defaultKey);
}
/*
 * 点击进入搜索页面
 */
$(".header_seek").click(function(){
	
	location.href='/xcview/html/search.html';
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
		
		menuTypeArray = data.resultObject[0];
		courseTypeArray = data.resultObject[2];
		cityTypeArray = data.resultObject[3];
	}else{
		alert("条件筛选errot!");
	}
},false)


/**
 * 创建参数并且进行查询
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
	//从新赋值
	if(stringnull(menuType)){
		paramsObj.menuType = menuType;
		for (var int = 0; int < menuTypeArray.length; int++) {
			var array_element = menuTypeArray[int];
			if(menuType == array_element.id){
				saisuanstr += array_element.name+"-";
				break;
			}
		}
	}
	if(stringnull(isFree)){
		paramsObj.isFree = isFree;
		if(isFree==0){
			saisuanstr +="收费-";
		}else{
			saisuanstr +="免费-";
		}
	}
	
	if(stringnull(courseType)){
		paramsObj.courseType = courseType;
		for (var int = 0; int < courseTypeArray.length; int++) {
			var array_element = courseTypeArray[int];
			if(courseType == array_element.id){
				saisuanstr += array_element.name+"-";
				break;
			}
		}
	}
	if(stringnull(city)){
		paramsObj.city = city;
		for (var int = 0; int < cityTypeArray.length; int++) {
			var array_element = cityTypeArray[int];
			if(city == array_element.name){
				saisuanstr += array_element.city+"-";
				break;
			}
		}
	}
	if(stringnull(lineState)){
		paramsObj.lineState = lineState;
		if(lineState==1){
			saisuanstr +="直播中-";
		}else if(lineState==2){
			saisuanstr +="未直播-";
		}else if(lineState==3){
			saisuanstr +="精彩回放-";
		}
	}
	
	if(stringnull(lineState)){
		paramsObj.lineState = lineState;
		if(lineState==1){
			saisuanstr +="直播中-";
		}else if(lineState==2){
			saisuanstr +="未直播-";
		}else if(lineState==3){
			saisuanstr +="精彩回放-";
		}
	}
	
	if(stringnull(queryKey)){
		paramsObj.queryKey = queryKey;
		saisuanstr +=queryKey+"-";
	}
	
	//将查询条件更改
	if(saisuanstr.length>0){
		saisuanstr = saisuanstr.substring(0, saisuanstr.length-1);
	}else{
		saisuanstr = "无";
	}
	$("#sxtj").text(saisuanstr);
	
	return paramsObj;
}


/**
 * 点击确认按钮获取查询进行查询
 */
function submit(){
	
//	menuType	否	Ingteger	课程所属学科menuid（传id）
//	courseType	否	Ingteger	课程类型 1：视频 2：音频 3：直播 4：线下培训班（传id）
//	isFree	否	Integer	是否免费 0：收费 1：免费（传id）
//	lineState	否	Integer	直播状态1.直播中，2预告，3直播结束（传id）
//	city	否	String	所在城市 （传城市名）
//	queryKey	否	String	检索的关键字
	
	/**
	 * 在浏览器地址上获取课程信息
	 */
	var menuType = $(".all_mold0  .all_right_type_one_add").attr("title");
	var isFree = $(".all_mold1  .all_right_type_one_add").attr("title");
	var courseType = $(".all_mold2  .all_right_type_one_add").attr("title");
	var city = $(".all_mold3  .all_right_type_one_add").attr("title");
	var lineState = $(".all_mold4  .all_right_type_one_add").attr("title");
	
	var submitParamsObj =  createParamsAndQuery(menuType,isFree,courseType,city,lineState);

	queryDataByParams(submitParamsObj);
}




function queryDataByParams(params,data_type){
	requestService("/xczh/recommend/queryAllCourse",params,function(data){
		if(data.success==true){
			if(stringnull(data_type)){
				var id = "#query_list"+data_type;
			}else{
				var id = "#draw_all_query_list";
			}
			var data1 ="";
			for (var int = 0; int < data.resultObject.length; int++) {
				var item = data.resultObject[int];
				var statusImg="";  //视频、音频不同的图片
				if(item.type == 1){
					statusImg+="/xcview/images/tv_auto.png";
				}else if(item.type == 2){
					statusImg+="xcview/images/frequency.png";
				}else if(item.type == 3){
					statusImg+="/xcview/images/Sinatv_auto.png";
				}else if(item.type == 4){
					statusImg+="/xcview/images/offline.png";
				}
				
				var statusImg1="<img src="+statusImg+"  class='two'  />";
				
				var isFreeStr ="";
				if(item.watchState == 0){
					isFreeStr+="<p class='p0'><span>免费</span></p>";
				}else if(item.watchState == 2){
					isFreeStr+="<p class='p0'><span>"+item.currentPrice+"</span>熊猫币</p>";
				}
				var typeStr="";
				if(item.type ==3){
					typeStr +="<p class='p2'><img src='/xcview/images/learn.png'><span>" +item.startDateStr+"</span></p>";
				}else if(item.type ==4){
					typeStr +="<p class='p2'><img src='/xcview/images/location_four.png'><span>" +item.city+"</span></p>";
				}
				data1+="<div class='li_list_div' aaa() >"+
					       "<div class='li_list_one' data-title="+item.id+" >"+
						     "<div class='li_list_one_left'>" +
						          "<img src='"+item.smallImgPath+"' class='one' />" +
						       statusImg1 +
						      "</div>" +
					           "<div class='li_list_one_right'>" +
						           "<p class='p00'>" +
						           "<span>"+item.name+"</span>:" +
						           "<span>"+item.gradeName+"</span></p>" +
						           "<div class='div'>" +
						              isFreeStr +
						             "<p class='p1'><img src='/xcview/images/population.png' alt=''><span>"+item.learndCount+"</span></p>" +
						               typeStr+
						            "</div>" +
					            "</div>" +
					         "</div>" +
					     "</div>";
			}
			$(id).html(data1);
			
		/*
		 * 点击跳转到单个课程
		 */
		 $(".li_list_div .li_list_one").click(function(){
			 
			var id =$(this).attr("data-title");
			if(stringnull(id)){
				requestService("/xczh/course/details?courseId="+id,null,function(data) {
		
					var course = data.resultObject;
					if(course.watchState == 0||course.watchState == 1){
						if(course.type==1||course.type==2){
		//						视频音频购买
							location.href="school_audio.html?course_id="+id
						}else if(course.type==3){
		//						直播购买
							location.href="school_play.html?course_id="+id
						}else{
		//						线下课购买
							location.href="school_class.html?course_id="+id
						}			
					}else if(course.watchState == 2||course.watchState == 3){
						if(course.type==1||course.type==2){
							if(course.collection){
		//							专辑视频音频播放页
							location.href="live_select_album.html?course_id="+id					
							}else{
		//							单个视频音频播放
							location.href="live_audio.html?my_study="+id					
							}
						}else if(course.type==3){
		//							播放页面
							location.href="live_audio.html?my_study="+id									
						}else{
		//							线下课页面
							location.href="live_class.html?my_study="+id									
						}		
					}
				})
			}
		})

		}else{
			alert("查询数据结果errot!");
		}
	},false)
}

/**
 * 将查询条件搞下
 */
createParamsAndQuery(paramsObj.menuType,paramsObj.isFree,paramsObj.courseType,
		paramsObj.city,paramsObj.lineState,paramsObj.queryKey);
/**
 * 查询所有
 */
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
//顶部搜索框获取文字显示
function getQueryString(key){
        var reg = new RegExp("(^|&)"+key+"=([^&]*)(&|$)");
        var result = window.location.search.substr(1).match(reg);
        return result?decodeURIComponent(result[2]):null;
    }
    var urlAttribute=getQueryString('queryKey')
    if (urlAttribute=='' || urlAttribute== null) {
    	
    } else{
   		$('.header_seek').append('<span>' + urlAttribute + '</span>');	
    }

    
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

