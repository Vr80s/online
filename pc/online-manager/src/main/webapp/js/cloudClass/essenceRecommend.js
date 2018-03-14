var scoreTypeTable;
var roleForm;
var searchCase;
var seed=1;
var searchCase = new Array();
$(function(){
	document.onkeydown=function(event){
		if(event.keyCode==13){
			return false
		}
	}
	
	//精品课推荐
	
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
	var objData = [{ "title": checkbox,"class":"center","width":"60px","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
				        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
				    }},
        { "title": "课程ID", "class": "center","width":"5%","sortable": false,"data":'id' },
        { "title": "课程名称", "class":"center","width":"9%","sortable":false,"data": 'courseName' },
        { "title": "所属学科", "class":"center","width":"6%","sortable":false,"data": 'xMenuName' },
        { "title": "课程大分类", "class":"center","width":"6%","sortable":false,"data": 'type' ,"mRender":function (data, display, row) {
    		debugger
    			if(data==1 ){  //课程分类 1:公开直播课（1.直播2.点播3.线下课）
    				return "直播课";
    			}else if(data== 2){
    				return "点播";
    			}else if(data== 3){
    				return "线下课";
    			}
    	} }, 
        { "title": "资源类型", "class":"center","width":"6%","sortable":false,"data": 'multimediaType' ,"mRender":function (data, display, row) {
        	
        		if(row.type == 2){
        			 if(data == 1){
                         return "视频";
                     }
                     return "音频";
        		}
            }},
        { "title": "直播状态", "class":"center","width":"6%","sortable":false,"data": 'liveStatus' ,"mRender":function (data, display, row) {
    		debugger
    		
    			if(row.type == 1){
    				if(data==1 ){  //直播状态1.直播中，2预告，3直播结束
    					return "直播中";
    				}else if(data== 2){
    					return "预告";
    				}else if(data== 3){
    					return "结束";
    				}else if(data== 4){
    					return "即将直播";
    				}else if(data== 5){
    					return "准备直播 ";
    				}else if(data== 6){
    					return "异常直播";
    				}
    			}
    		
    			
    		} },    
        { "title": "上传人", "class":"center","width":"8%","sortable":false,"data": 'lecturerName'},
        { "title": "主播", "class":"center","width":"8%","sortable":false,"data": 'lecturer'},
        { "title": "发布时间", "class":"center","width":"10%", "sortable":false,"data": 'releaseTime' },
        { "title": "推荐时效", "class":"center","width":"10%","sortable":false,"data": 'sortUpdateTime'},
        { "title": "推荐值", "class":"center","width":"6%", "sortable":false,"data": 'recommendSort' },
        
        { "title": "状态", "class":"center","width":"8%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
			return row.status=="1"?"已启用":"已禁用";
			}
		},
        { "sortable": false,"class": "center","width":"8%","title":"操作","mRender":function (data, display, row) {
				return '<div class="hidden-sm hidden-xs action-buttons">'+
                    '<a class="blue" href="javascript:void(-1);" title="设置推荐值" onclick="updateRecommendSort(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';

			}
		}
		      		];
	
	searchCase.push('{"tempMatchType":"9","propertyName":"essence_sort","propertyValue1":"1","tempType":"Integer"}');
	
	//课程分类
	scoreTypeTable = initTables("scoreTypeTable",basePath+"/essencerecommend/course/list",objData,true,true,2,null,searchCase,function(data){
			var iDisplayStart = data._iDisplayStart;
			var countNum = data._iRecordsTotal;//总条数
			pageSize = data._iDisplayLength;//每页显示条数
			currentPage = iDisplayStart / pageSize +1;//页码
			var countPage;
			if(countNum%pageSize == 0){
				countPage = parseInt(countNum/pageSize);
			}else{
				countPage = parseInt(countNum/pageSize) + 1;
			}
			$("[name='upa']").each(function(index){
				if(index == 0){
					$(this).css("pointer-events","none").removeClass("blue").addClass("gray");
				}
			});
			$("[name='downa']").each(function(index){
				if(index == $("[name='downa']").size()-1){
					$(this).css("pointer-events","none").removeClass("blue").addClass("gray");
				}
			});
	});
	
	//下线时间 时间控件
	createDatetimePicker($('.datetime-picker'));

	
	$(".all_recommend_course").show();
	$(".jp_course").hide();
	$(".course_menu_id").hide();
});



/**
 * 点击课程推荐管理  -- 查询出所有的没有被禁用的课程
 */
$(".all_bx").click(function(){
	
	$("#moveType").val(3);
	
	$(".all_recommend_course").show();
	$(".jp_course").hide();
	$(".course_menu_id").hide();
	
    var json = new Array();
    
    //essenceSort
    
    // 课程名 
    json.push('{"tempMatchType":"9","propertyName":"course_name","propertyValue1":"'+$("#search_courseName").val()+'","tempType":"String"}');
    
    // 直播大类型
    json.push('{"tempMatchType":"8","propertyName":"course_type","propertyValue1":"'+$("#search_type").val()+'","tempType":"String"}');
    
    // 直播状态 
    json.push('{"tempMatchType":"7","propertyName":"course_liveStatus","propertyValue1":"'+$("#search_liveStatus").val()+'","tempType":"String"}');
    
    // 媒体类型
    json.push('{"tempMatchType":"6","propertyName":"course_multimediaType","propertyValue1":"'+$("#search_multimediaType").val()+'","tempType":"String"}');
    // 学科
    json.push('{"tempMatchType":"5","propertyName":"menu_id","propertyValue1":"'+$("#search_menu").val()+'","tempType":"Integer"}');
    searchButton(scoreTypeTable,json);
});

/**
 * 点击精品课程 
 */
$(".jpktj_bx").click(function(){
	
	$("#moveType").val(1);
	
	$(".jp_course").show();
	$(".course_menu_id").hide();
	$(".all_recommend_course").hide();
	
    var json = new Array();
    
    //精品推荐
    json.push('{"tempMatchType":"9","propertyName":"is_essence","propertyValue1":"1","tempType":"Integer"}');
    //json.push('{"tempMatchType":"9","propertyName":"essence_sort","propertyValue1":"1","tempType":"Integer"}');
	
    searchButton(scoreTypeTable,json);
});

/**
 * 点击分类课程
 */
$(".flkc_bx").click(function(){
	
	$("#moveType").val(2);
	
	$(".course_menu_id").show();
	$(".jp_course").hide();
	$(".all_recommend_course").hide();
	
	var menuId = $("#search_menu").val();
	if(menuId==""){
		menuId = -1;
	}
    var json = new Array();
    //分类推荐
    json.push('{"tempMatchType":"5","propertyName":"is_type_recommend","propertyValue1":"1","tempType":"Integer"}');
    json.push('{"tempMatchType":"5","propertyName":"menu_id","propertyValue1":"'+menuId+'","tempType":"Integer"}');
    searchButton(scoreTypeTable,json);
});


/**
 * 取消或设置 分类的推荐
 */
$(".rec_fl").click(function(){
	var ids = new Array();
	var trs = $(".dataTable tbody input[type='checkbox']:checked");
	
	var title = $(this).attr("data-type");
	var isRec = 0;
	var tip ="取消分类推荐成功";
	if(title == "set"){
		isRec = 1;
		tip ="设置分类推荐成功";
	}
	
	for(var i = 0;i<trs.size();i++){
		
		if($(trs[i]).parent().parent().find("[name='fltj']").eq("0").text() == "已推荐" && isRec==1)
		{
			showDelDialog("","","无法推荐已推荐课程！","");
			return false;
		}
		
		ids.push($(trs[i]).val());
	}
	if(ids.length>0){ 
		ajaxRequest(basePath+"/essencerecommend/course/updateTypeRec",{'ids':ids.join(","),"isRec":isRec},function(data){
			if(!data.success){//如果失败
				layer.msg(data.errorMessage);
			}else{
				if(!isnull(scoreTypeTable)){
                    layer.msg(tip);
                    freshDelTable(scoreTypeTable);
				}
				layer.msg(data.errorMessage);
			}
		});
	}else{
		showDelDialog("","","请选择要取消推荐课程！","");
	}
})
/**
 * 取消精品推荐
 */
$(".rec_jp").click(function(){
	var ids = new Array();
	var trs = $(".dataTable tbody input[type='checkbox']:checked");
	
	var title = $(this).attr("data-type");
	var isRec = 0;
	var tip ="取消精品推荐成功";
	if(title == "set"){
		isRec = 1;
		tip ="设置精品推荐成功";
	}
	for(var i = 0;i<trs.size();i++){
		
		if($(trs[i]).parent().parent().find("[name='jptj']").eq("0").text() == "已推荐" && isRec==1)
		{
			showDelDialog("","","无法推荐已推荐课程！","");
			return false;
		}
		
		ids.push($(trs[i]).val());
	}
	if(ids.length>0){ 
		ajaxRequest(basePath+"/essencerecommend/course/updateEssenceRec",{'ids':ids.join(","),"isRec":isRec},function(data){
			if(!data.success){//如果失败
				layer.msg(data.errorMessage);
			}else{
				if(!isnull(scoreTypeTable)){
                    layer.msg(tip);
                    //freshDelTable(P_courseTable);
                    //search_menu();
                    freshDelTable(scoreTypeTable);
				}
				layer.msg(data.errorMessage);
			}
		});
	}else{
		showDelDialog("","","请选择要取消推荐课程！","");
	}
})	



function search_menu(){
	
	var menuId = $("#search_menu").val();
	if(menuId==""){
		menuId = -1;
	}
    var json = new Array();
    json.push('{"tempMatchType":"5","propertyName":"is_type_recommend","propertyValue1":"1","tempType":"Integer"}');
    json.push('{"tempMatchType":"5","propertyName":"menu_id","propertyValue1":"'+menuId+'","tempType":"Integer"}');
	searchButton(scoreTypeTable,json);
	
}



/**
 * 批量精品推荐
 *
 */
$(".add_jp").click(function(){
    var ids = new Array();
    var trs = $(".dataTable tbody input[type='checkbox']:checked");
    for(var i = 0;i<trs.size();i++){
        ids.push($(trs[i]).val());
    }
    if(ids.length>0){
        ajaxRequest(basePath+"/essencerecommend/course/updateEssenceRec",{'ids':ids.join(","),"isRec":1},function(data){
            if(!data.success){//如果失败
                layer.msg(data.errorMessage);
            }else{
                if(!isnull(scoreTypeTable)){
                    layer.msg("推荐成功！");
                    search();
                }
                layer.msg(data.errorMessage);
            }
        });
    }else{
        showDelDialog("","","请选择推荐课程！","");
    }
});
/**
 * 取消精品推荐
 *
 */
$(".deletes_jp").click(function(){
    var ids = new Array();
    var trs = $(".dataTable tbody input[type='checkbox']:checked");
    for(var i = 0;i<trs.size();i++){
        ids.push($(trs[i]).val());
    }
    if(ids.length>0){
        ajaxRequest(basePath+"/essencerecommend/course/updateEssenceRec",{'ids':ids.join(","),"isRec":0},function(data){
            if(!data.success){//如果失败
                layer.msg(data.errorMessage);
            }else{
                if(!isnull(scoreTypeTable)){
                    layer.msg("取消成功！");
                    search();
                }
                layer.msg(data.errorMessage);
            }
        });
    }else{
        showDelDialog("","","请选择要取消课程！","");
    }
});


/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTable.fnGetData(oo);
	ajaxRequest(basePath+"/cloudClass/scoreType/updateStatus",{"id":aData.id,"status":aData.status},function(){
		freshTable(scoreTypeTable);
	});
}
/**
 * Description：设置推荐值
 * @Date: 2018/3/9 14:11
 **/
function updateRecommendSort(obj){
    var oo = $(obj).parent().parent().parent();
    var row = scoreTypeTable.fnGetData(oo);
    $("#UpdateRecommendSort_id").val(row.id);
    var dialog = openDialog("UpdateRecommendSortDialog","dialogUpdateRecommendSortDiv","修改推荐值",350,200,true,"确定",function(){
        if($("#UpdateRecommendSortFrom").valid()){
            mask();
            $("#UpdateRecommendSortFrom").attr("action", basePath+"/cloudclass/course/updateRecommendSort");
            $("#UpdateRecommendSortFrom").ajaxSubmit(function(data){
                try{
                    data = jQuery.parseJSON(jQuery(data).text());
                }catch(e) {
                    data = data;
                }
                unmask();
                if(data.success){
                    $("#recommendSort").val("");
                    $("#UpdateRecommendSortDialog").dialog("close");
                    layer.msg(data.resultObject);
                        freshTable(scoreTypeTable);
                }else{
                    alertInfo(data.errorMessage);
                }
            });
        }
    });
};

// 原来 义帅的排序

///**
// * 上移
// * @param obj
// */
//function upMove(obj){
//	var oo = $(obj).parent().parent().parent();
//	var aData = scoreTypeTable.fnGetData(oo);
//	ajaxRequest(basePath+"/essencerecommend/course/upMove",{"id":aData.id},function(res){
//		if(res.success){
//			freshTable(scoreTypeTable);
//		}
//	});
//}
//
///**
// * 下移
// * @param obj
// */
//function downMove(obj){
//	var oo = $(obj).parent().parent().parent();
//	var aData = scoreTypeTable.fnGetData(oo);
//	ajaxRequest(basePath+"/essencerecommend/course/downMove",{"id":aData.id},function(res){
//		if(res.success){
//			freshTable(scoreTypeTable);
//		}
//	});
//}


// 更改的排序

/**
 * 课程排序列表上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTable.fnGetData(oo);
	
	var moveType =  $("#moveType").val();
	if(moveType == 3){
		return;
	}
	ajaxRequest(basePath+'/essencerecommend/course/upMove',{"id":aData.id,'moveType':moveType},function(res){
		if(res.success){
			freshTable(scoreTypeTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
	
};

/**
 * 课程排序列表下移
 * @param obj
 */
function downMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = scoreTypeTable.fnGetData(oo);
	var moveType =  $("#moveType").val();
	if(moveType == 3){
		return;
	}
	ajaxRequest(basePath+'/essencerecommend/course/downMove',{"id":aData.id,'moveType':moveType},function(res){
		if(res.success){
			freshTable(scoreTypeTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};


function search(){

    searchCase.push('{"tempMatchType":"5","propertyName":"name","propertyValue1":null,"tempType":"String"}');

	searchButton(scoreTypeTable,searchCase);

}




