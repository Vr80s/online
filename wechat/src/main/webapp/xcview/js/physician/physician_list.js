	
var openId = getQueryString("openId");
if(stringnull(openId)){
    localStorage.setItem("openid",openId);
}

/**
 * 从列表页返回
 */
var before_url = document.referrer;
if(before_url.indexOf("/xcview/html/physician/index.html")!=-1){
	sessionStorage.setItem("curriculum_blck",1);
}else if(before_url.indexOf("/xcview/html/physician/search.html")!=-1){
	sessionStorage.setItem("curriculum_blck",2);
}
/**
 * 返回上一页
 */
function goto_back(){
	
	//去中医师主页
	location.href="/xcview/html/physician/index.html";
}

/**
 * 将上面的url封装为json对象
 */	
var paramsObj = getUrlParamsReturnJson();
var matching = getQueryString('type');

//医师数组
var typeArray = new Array();
var departmentIdArray = new Array();
var sortTypeArray = new Array();

/*
 * 点击进入搜索页面
 */
$(".header_seek").click(function(){
	
	location.href='/xcview/html/physician/search.html?search_back=2';
});


/*
 * 筛选条件渲染
 */
requestService("/xczh/doctors/screen",null,function(data){
	if(data.success==true){
		
		var bigObj = data.resultObject;
		
		typeArray = bigObj.doctorTypes;
		departmentIdArray = bigObj.departments;
		sortTypeArray = bigObj.sortTypes;

		
		/**
		 * 动态生成分类
		 */
		var pagenavi1 ="<li class='find_nav_cur'><a href='javascript: ;' data-title ='0' class='' title='0'>全部</a></li>";
		/**
		 * 动态生成滑动的效果
		 */
		var box01List = "<li class='li_list'><div class='li_list_main' id='draw_all_query_list'></div><div class='no_class no_class_one no_class_ones no_class0'><img src='/xcview/images/no_class.png'/><p>医师正在签约的路上...</p></div></li>"; //代表全部的

		for (var int = 0; int < typeArray.length; int++) {
			var obj = typeArray[int];
			var index=int+1;
			pagenavi1 +="<li><a href='javascript: ;' data-title ="+index+" title="+obj.code+">"+obj.value+"</a></li>";				
			box01List+="<li class='li_list'><div class='li_list_main' data-title ="+index+" id='query_list"+obj.code+"'></div><div class='no_class no_class"+index+"'><img src='/xcview/images/no_class.png'/><p>医师正在签约的路上...</p></div></li>"			
		}
		pagenavi1 +="<li class='sideline' style='left: 0px; width: 96px;'></li>";
		
		//医师分类筛选
		$(".box01_list").html(box01List);
		$("#pagenavi1").html(pagenavi1);
		
		// 筛选医师分类
		$('#draw_doctortype_list').html(template('doctortype_list',{items:typeArray}));
		
		// 筛选科室分类
		$('#draw_department_list').html(template('department_list',{items:departmentIdArray}));
		
		//智能筛选分类
		$('#draw_sorttype_list').html(template('sorttype_list',{items:sortTypeArray}));
	}else{
		alert("条件筛选errot!");
	}
},false)


/**
 * 创建查询参数
 * @param type
 * @param departmentId
 * @param sortType
 * @param lineState
 */
function createParamsAndQuery(type,departmentId,sortType,queryKey){
	
	//清空上面的查询条件
	paramsObj = {};
	
	/**
	 * 筛选条件拼接字符串
	 */
	var saisuanstr ="";
	/**
	 * 左滑右滑选中
	 */
	var typeAll = $("[class='find_nav_cur'] a").attr("title");
	if(parseInt(typeAll) == 0){
		$(".all_right_type_ones").find(".all_right_type_one").removeClass("all_right_type_one_add");
	}else{
		//从新赋值
		if(stringnull(type)){
			paramsObj.type = type;
			for (var int = 0; int < typeArray.length; int++) {
				var array_element = typeArray[int];
				if(type == array_element.id){
					
					/**
					 *上方tab选中 
					 */
					$(".all_right_type_ones").find(".all_right_type_one").removeClass("all_right_type_one_add");
					
					
					/**
					 * 医师类型tab选中
					 */
					$(".all_right_type_ones").find(".all_right_type_one").each(function(){
						 var sxmenu = $(this).attr("title");
						 if(sxmenu == type){
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

	/**
	 * 科室选中
	 */
	if(stringnull(departmentId)){
		paramsObj.departmentId = departmentId;
		
		for (var int = 0; int < departmentIdArray.length; int++) {
			var array_element = departmentIdArray[int];
			if(departmentId == array_element.id){
				$("#draw_department_list").find(".all_right_type_one").each(function(){
					 var sx_departmentId = $(this).attr("title");
					 if(sx_departmentId == departmentId){
						 $(this).addClass("all_right_type_one_add");
						 return;
					 }
				});
				saisuanstr += array_element.name+"-";
				break;
			}
		}
	}

	
	if(stringnull(sortType)){
	
		paramsObj.sortType = sortType;
	    var falg = false
		for (var int = 0; int < sortTypeArray.length; int++) {
			var array_element = sortTypeArray[int];
			if(sortType == array_element.id){
				falg = true;
				$("#draw_sorttype_list").find(".all_right_type_one").each(function(){
					 var sx_sortType = $(this).text();
					 if(sortType == sx_sortType){
						 $(this).addClass("all_right_type_one_add");
						 return;
					 }
				})
				saisuanstr += array_element.name+"-";
				break;
			}
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
		$(".li_list").removeClass("li_list0");
	}else{
		saisuanstr = "无";
		$(".hint").hide();
		$(".li_list").addClass("li_list0");
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
	var type = $(".all_mold0  .all_right_type_one_add").attr("title");
	var departmentId = $(".all_mold1  .all_right_type_one_add").attr("title");
	var sortType = $(".all_mold2  .all_right_type_one_add").attr("title");
	var queryKey = getQueryString('queryKey');
	
	paramsObj.pageSize = 1000;
	
	if(stringnull(type)){
		paramsObj.type =type;
	}else{
		delete paramsObj.type;
	}
	
	if(stringnull(departmentId)){
		 paramsObj.departmentId = departmentId;
	}else{
		delete paramsObj.departmentId;
	}

	
    if(stringnull(sortType)){
   	    paramsObj.sortType = sortType;
	}else{
		delete paramsObj.sortType;
	}
  
    if(stringnull(queryKey)){
    	paramsObj.queryKey = queryKey;
   	}else{
   		delete paramsObj.queryKey;
   	}
	
	var begin_falg = false;
	var begin = 0;
	
	var list = $(".all_right_type_ones").find(".all_right_type_one");
	for(var i = 0; i < list.length; i++) {
		var className = list[i].className;
		if(className.indexOf("all_right_type_one_add")!=-1){
			begin_falg = true;
			begin = i+1;
			break;
		}
    }
	
	//先存一下，然后在取一下
	var type_index = sessionStorage.getItem("type_index");
	if(type_index == parseInt(begin)){ //分类没有变动
		begin_falg = false;
	}else{
		sessionStorage.setItem("type_index",begin);
	}
	
	if(begin_falg || type_index =="type_index"){
		slide(begin);
	}else{
		createParamsAndQuery(type,departmentId,sortType,queryKey);
		
		queryDataByParams(paramsObj,type);
	}
}

function queryDataByParams(params,data_type){
	
	requestService("/xczh/doctors/list",params,function(data){
			
		     var id = "#draw_all_query_list";
		     if(data.success==true){
			
		    	if(stringnull(data_type)){
				   id = "#query_list"+data_type;
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
					
					data1+="<div class='li_list_div' >"+
				       "<div class='li_list_one' data-doctorId = "+item.id+" >"+
					       "<div class='li_list_one_left'>" +
					         "<img src='"+item.headPortrait+"?imageView2/2/w/212' class='one' />"  +
					      "</div>" +
				           "<div class='li_list_one_right'>" +
					           "<p class='p00'>" +
					           "<span class='span'>"+item.name+"</span>" +
					           "<span class='duty'>"+(item.title = item.title==null ? "" : item.title)+"</span></p>" +
					           "<p class='site'>"+(item.hospitalAddress = item.hospitalAddress==null ? "" : item.hospitalAddress)+"</p>"+
				            "</div>" +
				         "</div>" +
				     "</div>";
				}
				
			 $(id).html(data1);	
				
			 /**
			  * 点击页面进行跳转
			  */		
			 $(".li_list_div .li_list_one").click(function(){
				 var id = $(this).attr("data-doctorId");
				 window.location.href = "/xcview/html/physician/physicians_page.html?doctor=" + id + "";
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

    var type = $("[class='find_nav_cur'] a").attr("title");
	paramsObj.pageSize = 1000;
	
    if(type!=0){
    	paramsObj.type= type;
        queryDataByParams(paramsObj,type);
    }else{
    	//删除这个条件
    	delete paramsObj.type;
    	queryDataByParams(paramsObj);
    }
    createParamsAndQuery(paramsObj.type,paramsObj.departmentId,
    		paramsObj.sortType,paramsObj.queryKey);
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
	// $('.header_seek_main').append('<span class=span_list>' + urlAttribute +'<span class=right_span>×</span></span>');
	$('.header').append('<span class=span_list><div class=insert_span>' + urlAttribute +'</div><span class=right_span>×</span><span class=both></span></span>');
	
}


