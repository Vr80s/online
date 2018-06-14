

// 判断字段空值
function stringnull(zifu) {
	if (zifu == "" || zifu == null || zifu == undefined || zifu == "undefined"
			|| zifu == "null") {
		return false;
	}
	return true;

}

// 获取url中的参数，并返回一个对象
function getUrlParamsReturnJson() {
	var url = location.search; // 获取url中"?"符后的字串
	var theRequest = {};
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for (var i = 0; i < strs.length; i++) {
			theRequest[strs[i].split("=")[0]] = decodeURIComponent(strs[i]
					.split("=")[1]);
		}
	}
	return theRequest;
};
var paramsObj = getUrlParamsReturnJson();


/**
 * 例子  
 * @param url  		http://dev.ixincheng.com:10086/courses/list?courseType=3
 * @param arg   	courseType
 * @param arg_val   ""
 * @returns			http://dev.ixincheng.com:10086/courses/list?courseType=
 */
function changeURLArg(url,arg,arg_val){
	var pattern=arg+'=([^&]*)';
	var replaceText=arg+'='+arg_val; 
	if(url.match(pattern)){
		var tmp='/('+ arg+'=)([^&]*)/gi';
		tmp=url.replace(eval(tmp),replaceText);
		return tmp;
	}else{ 
		if(url.match('[\?]')){ 
			return url+'&'+replaceText; 
		}else{ 
			return url+'?'+replaceText; 
		} 
	}
}


/*
 * 显示所选中的条件
 * 和选中状态
 */
function viewConditionOption(obj) {

	/**
	 * 上面的选中状态
	 */
	obj.addClass("selected").siblings().removeClass("selected");
	
	var selectCondition = "";
	var subject = obj.attr("subject");
	var selectText = obj.text();
	var selectValue = obj.attr("data-id");

	var kindId = "selectCondition" + subject;

	var urlFinalParams = webUrlParam;
	if (subject == "menuType") {
		selectCondition = "分类";
		
		//js替换掉这个条件
		urlFinalParams = changeURLArg(webUrlParam,"menuType","");
		
	} else if (subject == "courseType") {
		selectCondition = "类型";
		
		urlFinalParams = changeURLArg(webUrlParam,"courseType","");
	} else if (subject == "lineState") {
		selectCondition = "状态";
		
		urlFinalParams = changeURLArg(webUrlParam,"lineState","");
	} else if (subject == "isFree") {
		selectCondition = "收费";
		urlFinalParams = changeURLArg(webUrlParam,"isFree","");	
	} else if (subject == "city") {
		selectCondition = "城市";
		urlFinalParams = changeURLArg(webUrlParam,"city","");	
	}
	var selectKind = '<dd id="' + kindId + '" class="query_dev_condition">'
			+ '<p class="wrap-border" subject=' + subject + ' value='
			+ selectValue + '>' + '<span>' + selectCondition + ' : </span>'
			+ '<span class="select-text" >' + selectText + '</span>'
			+ '<span class="select-close"><a href='+urlFinalParams+'><img src="/web/images/mynote-close-hover.png"></a></span>'
			+ '</p>' + '</dd>';

	
	if ($("#selectCondition" + subject).length > 0) {
		$("#selectCondition" + subject + " .select-text").html(obj.text());
		$("#selectCondition" + subject + " p").attr("value", selectValue);
	} else {
		$("#select-condition").append(selectKind);
	}
}
/**
 * 点击上面的标记进行筛选
 * 
 * @returns
 */
$(".select-all li dd").click(function() {

	var obj = $(this);
	viewConditionOption(obj);
	/*
	 * 查询条件封装
	 */
	var paramsObj = {};
	$("#select-condition dd").each(function() {
		var key = $(this).find("p").attr("subject");
		var value = $(this).find("p").attr("value");
		paramsObj[key] = value;
	})
	console.log("paramsObj:" + JSON.stringify(paramsObj));
})

/**
 * 如果跳转过来后，那么就需要选中 这些东西了
 */
function viewCondition(paramsObj) {
	// 分类
	if (stringnull(paramsObj.menuType)) {
		$("#select-kind dd").each(function() {
			var selectValue = $(this).attr("data-id");
			if (paramsObj.menuType == selectValue) {

				viewConditionOption($(this));
				
				$("[name='menuType']").val(selectValue);
				return;	
			}
		});
	}
	// 类型
	if (stringnull(paramsObj.courseType)) {
		$("#select-style dd").each(function() {
			var courseType = $(this).attr("data-id");
			if (paramsObj.courseType == courseType) {
				
				viewConditionOption($(this));
				$("[name='courseType']").val(courseType);
				return;
			}
		});
	}
	// 状态
	if (stringnull(paramsObj.lineState)) {
		$("#select-status dd").each(function() {
			var lineState = $(this).attr("data-id");
			if (paramsObj.lineState == lineState) {
				viewConditionOption($(this));
				$("[name='lineState']").val(lineState);
				return;
			}
		});
	}
	// 收费
	if (stringnull(paramsObj.isFree)) {
		$("#select-price dd").each(function() {
			var isFree = $(this).attr("data-id");
			if (paramsObj.isFree == isFree) {
				viewConditionOption($(this));
				$("[name='isFree']").val(isFree);
				return;
			}
		});
	}
	// 城市
	if (stringnull(paramsObj.city)) {
		$("#select-address dd").each(function() {
			var city = $(this).attr("data-id");
			if (paramsObj.city == city) {
				viewConditionOption($(this));
				$("[name='city']").val(city);
				return;
			}
		});
	}
	
	// 关键字
	if (stringnull(paramsObj.queryKey)) {
		$("#search-text").val(paramsObj.queryKey);
	}
	
	//综合排序、最新、人气、价格
	if (stringnull(paramsObj.sortOrder)) {
		var sortOrder = parseInt(paramsObj.sortOrder);
		if(sortOrder == 1|| sortOrder == 2 || sortOrder == 3){
			$(".wrap-tab ul li a").eq(sortOrder-1).css("color","#00BC12");
		}else{
			$(".wrap-tab .tab-price span").eq(sortOrder-4).css("color","#00BC12");
		}
	}
	
}
/**
 * 显示查询条件
 * 
 * @returns
 */
viewCondition(paramsObj);


/**
 * 选中后，这里还需要在处理下呢，如果
 *   如果是视频、音频的话。干掉 城市和直播状态的条件
 *   如果是直播的话。 干掉 城市的条件
 *   如果是线下班的话。 干掉 直播状态的条件
 */

//
$("#select-style dd").each(function() {
	var $this =  $(this);
	var courseType = $this.attr("data-id");
	courseType = parseInt(courseType);
	var urlFinalParams =$this.parent().attr("href");
	if (1 == courseType || 2 == courseType) { //视频。音频
		urlFinalParams = changeURLArg(urlFinalParams,"lineState","");
		urlFinalParams = changeURLArg(urlFinalParams,"city","");
	    $this.parent().attr("href",urlFinalParams);
	}else if(3 == courseType){ //直播
		urlFinalParams = changeURLArg(urlFinalParams,"city","");
	    $this.parent().attr("href",urlFinalParams);
	}else if(4 == courseType){ //线下课
		urlFinalParams = changeURLArg(urlFinalParams,"lineState","");
	    $this.parent().attr("href",urlFinalParams);
	}
});
/*
 * 
 */
if (stringnull(paramsObj.courseType) && (paramsObj.courseType == 3)) {
	$("#select-status-hide").show();
}else if (stringnull(paramsObj.courseType) && (paramsObj.courseType == 4)) {
	$("#select-address-hide").show();
}






