var typeTable;
var addForm;
var updateForm;
var searchJson = new Array();
$(function() {
	loadTypesList();
	addForm = $("#add-form");
	updateForm = $("#update-form")
});

//列表展示
function loadTypesList(){
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var dataFields = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
			return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
		}},
		{ "title": "分类名称", "class":"center","width":"18%","sortable":false,"data": 'name' },
		{ "title": "文章数", "class":"center","width":"8%","sortable":false,"data": 'articleNum' },
		{ "title": "创建日期", "class":"center","width":"12%","sortable":false,"data": 'create_time' },
		{ "title": "状态", "class":"center","width":"8%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
			if(data==1){
				return data="<span name='zt'>已启用</span>";
			}else{
				return data="<span name='zt'>已禁用</span>";
			}
		} },
		{"sortable": false,"class": "center","width":"8%","title":"排序","mRender":function (data, display, row) {
			if(row.status ==1){//如果是禁用
	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
	    		'<a class="blue" name="upa" href="javascript:void(-1);" title="上移"  onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
	        	'<a class="blue" name="downa" href="javascript:void(-1);" title="下移"  onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
	    	}else{
	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
	    		'<a class="gray" href="javascript:void(-1);" title="上移"  ><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
	        	'<a class="gray" href="javascript:void(-1);" title="下移"  ><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
	    	}
//			return '<div class="hidden-sm hidden-xs action-buttons">'+
//				'<a class="blue" href="javascript:void(-1);" id="moveUp" title="上移" onclick="upMove(this)" name="upa"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
//				'<a class="blue" href="javascript:void(-1);" id="moveDown" title="下移" onclick="downMove(this)" name="downa"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
		}},
		{title:"操作","class": "center","width":"8%","data":"id","sortable": false,"mRender":function (data, display, row) {
			var buttons= '<div class="hidden-sm hidden-xs action-buttons"><a class="blue" href="javascript:void(-1);" title="修改" onclick="updateArticleType(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
			if(row.status=="1"){
				buttons += '<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a>';
			}else{
				buttons += '<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a>';
			}
			buttons += '</div>';
			return buttons;
}}
    ];

	typeTable = initTables("typeTable", basePath + "/boxueshe/articletype/types", dataFields, true, true, 0,null,searchJson,function(data){
		var iDisplayStart = data._iDisplayStart;
		var countNum = data._iRecordsTotal;//总条数
		pageSize = data._iDisplayLength;//每页显示条数
		currentPage = iDisplayStart / pageSize +1;//页码
		if(currentPage == 1){//第一页的第一行隐藏向上箭头
			$("#typeTable tbody tr:first td").eq(5).find('a').eq(0).css("pointer-events","none").removeClass("blue").addClass("gray");
		}
		if(countNum/pageSize < 1 || countNum/pageSize == 1){//数据不足一页隐藏下移箭头
			$("#typeTable tbody tr:last td").eq(5).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
		}
		var countPage;
		if(countNum%pageSize == 0){
			countPage = parseInt(countNum/pageSize);
		}else{
			countPage = parseInt(countNum/pageSize) + 1;
		}
		if(countPage == currentPage){//隐藏最后一条数据下移
			$("#typeTable tbody tr:last td").eq(5).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
		}
		if(data.aoData[1]!=undefined && (data.aoData[1]._aData.sortType==1)){
			$("#typeTable tbody #moveUp,#typeTable tbody #moveDown").css("pointer-events","none").removeClass("blue").addClass("gray");
		}
//		$("[name='upa']").each(function(index){
//			if(index == 0){
//				$(this).css("pointer-events","none").removeClass("blue").addClass("gray");
//			}
//		});
		$("[name='downa']").each(function(index){
			if(index == $("[name='downa']").size()-1){
				$(this).css("pointer-events","none").removeClass("blue").addClass("gray");
			}
		});
    });
}

 //条件搜索
 function search(sortType){
	 if(sortType == 1){
         searchJson.push('{"tempMatchType":"9","propertyName":"sortType","propertyValue1":"' + sortType + '","tempType":"String"}');
     }
     searchButton(typeTable,searchJson);
}

/**
 * 新增文章分类
 */
$(".add_type").click(function(){
	addForm.resetForm();
	openDialog("addDialog", "addTypeDiv", "新增分类", 350, 200, true, "确定", function () {
		if ($("#add-form").valid()) {
			mask();
			$("#add-form").attr("action", basePath + "/boxueshe/articletype/addType");
			$("#add-form").ajaxSubmit(function (data) {
				unmask();
				if (data.success) {
					$("#addDialog").dialog("close");
					layer.msg(data.resultObject);
					freshTable(typeTable);
				} else {
					layer.msg(data.errorMessage);
				}
			});
		}
	});
});

/**
 * 修改分类名
 */
function updateArticleType(obj) {
	var oo = $(obj).parent().parent().parent();
	var row = typeTable.fnGetData(oo);
	updateForm.resetForm();
	$("#edit_id").val(row.id)
	$("#edit_name").val(row.name);
	openDialog("updateDialog", "updateTypeDiv", "修改分类", 350, 200, true, "确定", function () {
		if ($("#update-form").valid()) {
			mask();
			$("#update-form").attr("action", basePath + "/boxueshe/articletype/updateTypeById");
			$("#update-form").ajaxSubmit(function (data) {
				unmask();
				if (data.success) {
					$("#updateDialog").dialog("close");
					layer.msg(data.resultObject);
					freshTable(typeTable);
				} else {
					layer.msg(data.errorMessage);
				}
			});
		}
	});
}

/**
  * 批量逻辑删除
  * 
  */
$(".del_batch").click(function(){
 	deleteAll(basePath+"/boxueshe/articletype/deletes",typeTable);
});

/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj){
	var oo = $(obj).parent().parent().parent();
	var row = typeTable.fnGetData(oo);
	ajaxRequest(basePath+"/boxueshe/articletype/updateStatus",{"id":row.id},function(res){
		if(res.success) {
			layer.msg(res.resultObject);
			freshTable(typeTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

/**
 * 排序列表上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = typeTable.fnGetData(oo);
	ajaxRequest(basePath+'/boxueshe/articletype/upMove',{"id":aData.id},function(res){
		if(res.success){
			layer.msg(res.resultObject);
			freshTable(typeTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

/**
 * 排序列表下移
 * @param obj
 */
function downMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = typeTable.fnGetData(oo);
	ajaxRequest(basePath+'/boxueshe/articletype/downMove',{"id":aData.id},function(res){
		if(res.success){
			layer.msg(res.resultObject);
			freshTable(typeTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};
