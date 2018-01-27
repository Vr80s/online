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
        {title: '序号', "class": "center", "width": "5%","data": 'id',datafield: 'xuhao', "sortable": false},
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
                if(data == 1){
                    return "视频";
                }
                return "音频";
            }},
        { "title": "直播状态", "class":"center","width":"6%","sortable":false,"data": 'liveStatus' ,"mRender":function (data, display, row) {
    		debugger
    			if(data==1 ){  //直播状态1.直播中，2预告，3直播结束
    				return "直播中";
    			}else if(data== 2){
    				return "预告";
    			}else if(data== 3){
    				return "结束";
    			}
    		} },    
        { "title": "上传人", "class":"center","width":"8%","sortable":false,"data": 'lecturerName'},
        { "title": "主播", "class":"center","width":"8%","sortable":false,"data": 'lecturer'},
        { "title": "状态", "class":"center","width":"8%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
			return row.status=="1"?"已启用":"已禁用";
			}
		},
		{ "sortable": false,"data":"id","class": "center","width":"10%","title":"排序","mRender":function (data, display, row) {
			if(row.status ==1){//如果是禁用
				return '<div class="hidden-sm hidden-xs action-buttons">'+
				'<a class="blue" name="upa" href="javascript:void(-1);" title="上移"  onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
				'<a class="blue" name="downa" href="javascript:void(-1);" title="下移"  onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
			}else{
				return '<div class="hidden-sm hidden-xs action-buttons">'+
				'<a class="gray" href="javascript:void(-1);" title="上移"  ><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
				'<a class="gray" href="javascript:void(-1);" title="下移"  ><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
			}
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
	
	$(".all_recommend_course").show();
	$(".jp_course").hide();
	$(".course_menu_id").hide();
	
    var json = new Array();
    
    //essenceSort
    
    // 课程名 
    json.push('{"tempMatchType":"9","propertyName":"course_name","propertyValue1":"'+$("#search_courseName").val()+'","tempType":"String"}');
    
    // 课程名 
    json.push('{"tempMatchType":"9","propertyName":"course_name","propertyValue1":"'+$("#search_courseName").val()+'","tempType":"String"}');
    
    // 直播大类型
    json.push('{"tempMatchType":"9","propertyName":"course_type","propertyValue1":"'+$("#search_type").val()+',"tempType":"Integer"}');
    
    // 直播状态 
    json.push('{"tempMatchType":"9","propertyName":"course_liveStatus","propertyValue1":"'+$("#search_liveStatus").val()+',"tempType":"Integer"}');
    
    // 媒体类型
    json.push('{"tempMatchType":"9","propertyName":"course_multimediaType","propertyValue1":"'+$("#search_multimediaType").val()+',"tempType":"Integer"}');
    
    
    
    searchButton(scoreTypeTable,json);
});

/**
 * 点击精品课程 
 */
$(".jpktj_bx").click(function(){
	
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
 * 取消普通的推荐
 */
$(".rec_P").click(function(){
	var ids = new Array();
	var trs = $(".dataTable tbody input[type='checkbox']:checked");
	for(var i = 0;i<trs.size();i++){
		ids.push($(trs[i]).val());
	}
	if(ids.length>0){ 
		ajaxRequest(basePath+"/cloudclass/course/updateRec",{'ids':ids.join(","),"isRec":0},function(data){
			if(!data.success){//如果失败
				//alertInfo(data.errorMessage);
				layer.msg(data.errorMessage);
			}else{
				if(!isnull(scoreTypeTable)){
                    layer.msg("取消推荐成功！");
                    //freshDelTable(P_courseTable);
                    search_menu();
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
	
	for(var i = 0;i<trs.size();i++){
		ids.push($(trs[i]).val());
	}
	if(ids.length>0){ 
		ajaxRequest(basePath+"/essencerecommend/course/updateEssenceRec",{'ids':ids.join(","),"isRec":0},function(data){
			if(!data.success){//如果失败
				layer.msg(data.errorMessage);
			}else{
				if(!isnull(scoreTypeTable)){
                    layer.msg("取消精品推荐成功！");
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
	ajaxRequest(basePath+'/realClass/course/upMove',{"id":aData.id},function(res){
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
	ajaxRequest(basePath+'/realClass/course/downMove',{"id":aData.id},function(res){
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




