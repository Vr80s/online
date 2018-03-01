	
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
			var index=int+1;
			pagenavi1 +="<li><a href='javascript: ;' data-title ="+index+" title="+obj.id+">"+obj.name+"</a></li>";		
			box01List+="<li class='li_list'><div class='li_list_main' data-title ="+index+" id='query_list"+obj.id+"'></div></li>"		
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
	//从新赋值
	if(stringnull(menuType)){
		paramsObj.menuType = menuType;
		
		$(".all_right_type_ones").find(".all_right_type_one").removeClass("all_right_type_one_add");
		
		for (var int = 0; int < menuTypeArray.length; int++) {
			var array_element = menuTypeArray[int];
			if(menuType == array_element.id){
				
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
	if(stringnull(city)){
		if(!stringnull(courseType)){
			$("#draw_course_big_list").find(".all_right_type_one").eq(3).addClass("all_right_type_one_add");
			saisuanstr += "线下课程"+"-";
			/**
			 * 选中线下培训班  --》 然后在选中 所对应的城市
			 */
			$(".all_mold3").show();
		}	
		paramsObj.city = city;
		
		if("全国课程"!=city){
		
			for (var int = 0; int < cityTypeArray.length; int++) {
				var array_element = cityTypeArray[int];
				if(city == array_element.name){
					
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
			
		}
	}
	if(stringnull(lineState)){
		paramsObj.lineState = lineState;
		
		
		/**
		 * 显示直播状态 
		 * --》选中直播课程
		 * -->选中直播转态
		 */
		//显示直播状态 
		$(".all_mold4").show();
		
		if(!stringnull(courseType)){
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
	
	if(stringnull(menuType)){
		paramsObj.menuType =menuType;
	}else{
		delete paramsObj.menuType;
	}
	
	if(stringnull(isFree)){
		 paramsObj.isFree = isFree;
	}else{
		delete paramsObj.isFree;
	}
    if(stringnull(courseType)){
    	 paramsObj.courseType = courseType;
	}else{
		delete paramsObj.courseType;
	}
    if(stringnull(city)){
   	    paramsObj.city = city;
	}else{
		delete paramsObj.city;
	}
    if(stringnull(lineState)){
    	paramsObj.lineState = lineState;
   	}else{
   		delete paramsObj.lineState;
   	}
	//var submitParamsObj =  createParamsAndQuery(menuType,isFree,courseType,city,lineState);
	/**
	 * 判断滑动--》到第几个
	 */
//	$(".all_right_type_ones").find(".all_right_type_one").each(function(){
//		 var className = $(this).attr("class");
//		 if(className.indexOf("all_right_type_one_add")!=-1){
//			 
//			 return;
//		 }
//	});
	
	var begin =0;
	var list = $(".all_right_type_ones").find(".all_right_type_one");
	for(var i = 0; i < list.length; i++) {
		var className = list[i].className;
		if(className.indexOf("all_right_type_one_add")!=-1){
			begin = i+1;
			break;
		}
    }
	slide(begin);
	
	
	//queryDataByParams(submitParamsObj);
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
			
			if(data.resultObject.length<=0){
				$(".li_list_main").css("background","#f8f8f8");
				$(".no_class").show();
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
					isFreeStr+="<p class='p0'><span>免费</span></p>";
				}else if(item.watchState == 0){
					isFreeStr+="<p class='p0'><span>"+item.currentPrice+"</span><span class='span'>熊猫币</span></p>";
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
						           "<span>"+item.gradeName+"</span><br />" +
						           "<span class='span'>"+item.name+"</span></p>" +
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
//									视频音频购买
								location.href="school_audio.html?course_id="+id
							}else if(course.type==3){
//									直播购买
								location.href="school_play.html?course_id="+id
							}else{
//									线下课购买
								location.href="school_class.html?course_id="+id
							}			
						}else if(course.watchState == 2||course.watchState == 3){
							if(course.type==1||course.type==2){
								if(course.collection){
//										专辑视频音频播放页
								location.href="live_select_album.html?course_id="+id					
								}else{
//										单个视频音频播放
								location.href="live_audio.html?my_study="+id					
								}
							}else if(course.type==3){
//										播放页面
								location.href="live_audio.html?my_study="+id									
							}else{
//										线下课页面
								location.href="live_class.html?my_study="+id									
							}		
						}
					})
				}
			})
			
		}else{
			$(".no_class").show();
			$(".li_list_main").css("background","#f8f8f8");
			alert("查询数据结果error!");
		}
	},false)
}

/**
 * 这里先请求出所有的
 */

function typeQuery(){

    var menuType = $("[class='find_nav_cur'] a").attr("title");
    
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

