$(function() {
	// 分类
	var selectKind = '<dd id="alldAdd">' + '<p class="wrap-border">'
			+ '<span>分类 : </span>'
			+ '<span class="select-text" subject = "" >筛选条件</span>'
			+ '<span class="select-close" onclick="deleteKin()">x</span>'
			+ '</p>' + '</dd>';

	// $("#select-kind dd").click(function () {
	// var copyThisA = $(this).text();
	// if($(this).hasClass("selected")){
	// $(this).removeClass("selected");
	// $(this).siblings().removeClass("selected");
	// $("#kindAdd").remove();
	// }else{
	// $(this).addClass("selected").siblings().removeClass("selected");
	// if ($("#kindAdd").length > 0) {
	// $("#kindAdd .select-text").html($(this).text());
	// } else {
	// $("#select-condition").append(selectKind);
	// $("#alldAdd").attr("id","kindAdd");
	// $("#kindAdd .select-text").html(copyThisA);
	// }
	// }
	//            
	// });
	// 类型
	 var selectStyle = '<dd id="allAdd">' + '<p class="wrap-border">'
			+ '<span>类型 : </span>' + '<span class="select-text">筛选条件</span>'
			+ '<span class="select-close" onclick="deleteStyle()">x</span>'
			+ '</p>' + '</dd>';

	 // 综合排序,最新,人气价格
//	 $(".wrap-tab li").click(function(){
//		 if($(this).hasClass("selected")){
//			 	return;
//		 }else{
//			 $(".wrap-tab li").removeClass("selected").eq($(this).index()).addClass("selected");
//			 $(".tab-price p").removeClass("selected");
//			 $(".tab-top").removeClass("selected");
//			 $(".tab-bottom").removeClass("selected");
//		 }
//	 });
//	 
//	 $(".tab-price").click(function(){
//		 $(".tab-price p").addClass("selected");
//		 if($(".tab-top").hasClass("selected")){
//			 $(".tab-top").removeClass("selected");
//			 $(".tab-bottom").addClass("selected");
//		 }else{
//			 $(".tab-top").addClass("selected")
//			 $(".tab-bottom").removeClass("selected");
//		 }
//	 });
});

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

/*
 * 显示所选中的条件
 */
function viewConditionOption(obj) {

	var selectCondition = "";
	var subject = obj.attr("subject");
	var selectText = obj.text();
	var selectValue = obj.attr("data-id");

	var kindId = "selectCondition" + subject;

	if (subject == "menuType") {
		selectCondition = "分类";
	} else if (subject == "courseType") {
		selectCondition = "类型";
	} else if (subject == "lineState") {
		selectCondition = "状态";
	} else if (subject == "isFree") {
		selectCondition = "收费";
	} else if (subject == "city") {
		selectCondition = "城市";
	}
	var selectKind = '<dd id="' + kindId + '" class="query_dev_condition">'
			+ '<p class="wrap-border" subject=' + subject + ' value='
			+ selectValue + '>' + '<span>' + selectCondition + ' : </span>'
			+ '<span class="select-text" >' + selectText + '</span>'
			+ '<span class="select-close" onclick="deleteStatus()">x</span>'
			+ '</p>' + '</dd>';

	/**
	 * 上面的选中状态
	 */
	obj.addClass("selected").siblings().removeClass("selected");
	
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
				// $(this).click();
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
				// $(this).click();
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
				// $(this).click();
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
				// $(this).click();
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
				// $(this).click();
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

// 删除分类选项
function deleteKin() {
	$("#kindAdd").remove();
	$("#select-kind dd").removeClass("selected");
}
// 删除类型选项
function deleteStyle() {
	$("#styleAdd").remove();
	$("#select-style dd").removeClass("selected");
}
// 删除状态选项
function deleteStatus() {
	$("#statusAdd").remove();
	$("#select-status dd").removeClass("selected");
}
// 删除收费选项
function deletePrice() {
	$("#priceAdd").remove();
	$("#select-price dd").removeClass("selected");
}
// 删除收费选项
function deleteAddress() {
	$("#addressAdd").remove();
	$("#select-address dd").removeClass("selected");
}