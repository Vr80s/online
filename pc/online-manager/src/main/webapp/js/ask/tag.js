var searchCase = new Array();
var tagTable;
var addTagForm;


$(function(){
	
	document.onkeydown=function(event){
		if(event.keyCode == 13) {
            return false;
        }
	}
	
	
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
	var objData = [{ "title": checkbox,"class":"center","width":"3%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
				        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
				    }},
	               { "title": "序号", "class": "center","width":"4%","sortable": false,"data":"id" },
	               { "title": "标签名称", "class": "center","width":"20%","sortable": false,"data":"name" },
	               { "title": "问题数", "class": "center","width":"4%","sortable": false,"data":"quesCount" },
	               { "title": "<div class='yingyongshu'><span>引用数(</span><span class='yingyongshuValue'>最近一个月</span><span>)</span><span id='yinyongshuSanjiao' class='sanjiao'></span><ul id='yinyongshulist'><li data-month='30' >最近一个月</li><li data-month='60'>最近三个月</li><li data-month='180'>最近六个月</li></ul></div>","class": "center","width":"8%","sortable": false,"data":"citesCount" },
	               { "title": "创建日期", "class":"center","width":"5%","sortable":false,"data": 'createTime' },
	               { "title": "状态", "class":"center","width":"5%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
						return row.status==1?"已启用":"已禁用";
						}
					},
					{ "sortable": false,"data":"id","class": "center","width":"5%","title":"排序","mRender":function (data, display, row) {
						if(row.status ==1){//如果是禁用
				    		return '<div class="hidden-sm hidden-xs action-buttons">'+
				    		'<a class="blue" id="moveUp" name="upa" href="javascript:void(-1);" title="上移"  onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
				        	'<a class="blue" id="moveDown" name="downa" href="javascript:void(-1);" title="下移"  onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
				    	}else{
				    		return '<div class="hidden-sm hidden-xs action-buttons">'+
				    		'<a class="gray" href="javascript:void(-1);" title="上移"  ><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
				        	'<a class="gray" href="javascript:void(-1);" title="下移"  ><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
				    	}
//				   		var buttons= '<div class="hidden-sm hidden-xs action-buttons">';
//							buttons+='<a class="blue" id="moveUp" href="javascript:void(-1);" title="上移" onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
//							'<a class="blue" id="moveDown" href="javascript:void(-1);" title="下移" onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a>';
//							return buttons;
			   			}
					},
	               { "sortable": false,"data":"id","class": "center","width":"4%","title":"操作","mRender":function (data, display, row) {
	            	   var buttons= '<div class="hidden-sm hidden-xs action-buttons">'
	           		
	            	   if(row.quesCount!=0){
	            		   buttons+='<a class="gray" href="javascript:void(-1);" title="修改" onclick=""><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
	            	   }else{
	            		   buttons+='<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
	            	   }
	            		if(row.status==1) {
	           			buttons+='<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> ';
	           		}else{
	           			buttons+='<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> ';
	           		};
	           		return buttons;
	           	   		}},
	           	   	{ "title": "排序类型", "class": "center","width":"80px","sortable": false,"data":"sortType","visible":false }
		      		];	
	
	searchCase.push('{"tempMatchType":"7","propertyName":"menuId","propertyValue1":"' + $("#menuId").val() + '","tempType":"String"}');
	tagTable = initTables("tagTable",basePath+"/ask/tag/findTagList",objData,true,true,2,null,searchCase,function(data){
		var iDisplayStart = data._iDisplayStart;
		var countNum = data._iRecordsTotal;//总条数
		pageSize = data._iDisplayLength;//每页显示条数
		currentPage = iDisplayStart / pageSize +1;//页码
		if(currentPage == 1){//第一页的第一行隐藏向上箭头
//			$("#cloudClassTable tbody tr:first td:last a:eq(4)").hide();
			$("#tagTable tbody tr:first td").eq(7).find('a').eq(0).css("pointer-events","none").removeClass("blue").addClass("gray");
		}
		if(countNum/pageSize < 1 || countNum/pageSize == 1){//数据不足一页隐藏下移箭头
//			$("#cloudClassTable tbody tr:last td:last a:eq(5)").hide();
			$("#tagTable tbody tr:last td").eq(7).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
		}
		var countPage;
		if(countNum%pageSize == 0){
			countPage = parseInt(countNum/pageSize);
		}else{
			countPage = parseInt(countNum/pageSize) + 1;
		}
//		if(countPage == currentPage){//隐藏最后一条数据下移
////			$("#cloudClassTable tbody tr:last td:last a:eq(5)").hide();
//			$("#tagTable tbody tr:last td").eq(7).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
//		}

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
		
		if(data.aoData[1]!=undefined && (data.aoData[1]._aData.sortType=='question' || data.aoData[1]._aData.sortType=='use')){
			$("#tagTable tbody #moveUp,#tagTable tbody #moveDown").css("pointer-events","none").removeClass("blue").addClass("gray");
		}
		
		
	});
	
	addTagForm = $("#addTag-form").validate({
		 messages: {
			 name: {
					required:"标签不能为空！"
	            },
		 }
	});
	
	
	$(document).click(function(){
		$("#yinyongshulist").css("display","none");
		 $("#yinyongshuSanjiao").toggleClass("sanjiao sanjiao2");
			if($("#yinyongshulist").css("display")=="none"){
				$("#yinyongshuSanjiao").removeClass("sanjiao2").addClass("sanjiao");
				}
		});
	
});


$(".yingyongshu").click(function(event){
	event.stopPropagation();
    $("#yinyongshulist").toggle();
    $("#yinyongshuSanjiao").toggleClass("sanjiao sanjiao2");
    
})

$("#yinyongshulist li").on("click",function(event){
	event.stopPropagation();
    var yingyongshuTarget=$(this);
    $(".yingyongshuValue").text($(this).text());
    
     $("#yinyongshulist").css("display","none")
     var month=$(yingyongshuTarget).attr("data-month")
    
    searchCase.push('{"tempMatchType":"7","propertyName":"menuId","propertyValue1":"' + $("#menuId").val() + '","tempType":"String"}');
 	searchCase.push('{"tempMatchType":"10","propertyName":"monthSort","propertyValue1":"' + month + '","tempType":"String"}');
 	searchButton(tagTable,searchCase);
 	searchCase.pop();
 	searchCase.pop();
     
})


//新增框
$(".add_bx").click(function(){
	addTagForm.resetForm();
	var dialog = openDialog("addTagDialog","dialogAddTagDiv","新增标签",580,200,true,"确定",function(){
		if($("#addTag-form").valid()){
			mask();
			 $("#addTag-form").attr("action", basePath+"/ask/tag/addTag");
	            $("#addTag-form").ajaxSubmit(function(data){
	            	data = getJsonData(data);
	                unmask();
	                if(data.success){
	                    $("#addTagDialog").dialog("close");
	                    layer.msg(data.errorMessage);
	                    freshTable(tagTable);
	                }else{
	                	layer.msg(data.errorMessage);
	                }
	            });
		}
	});
});


function deleteBatch(){
	deleteAll(basePath+"/ask/tag/deletes",tagTable);
}

function search(){
	searchCase.push('{"tempMatchType":"7","propertyName":"menuId","propertyValue1":"' + $("#menuId").val() + '","tempType":"String"}');
	searchButton(tagTable,searchCase);
	searchCase.pop();
}

function quesSort(){
	searchCase.push('{"tempMatchType":"7","propertyName":"menuId","propertyValue1":"' + $("#menuId").val() + '","tempType":"String"}');
	searchCase.push('{"tempMatchType":"8","propertyName":"quesSort","propertyValue1":quesSort,"tempType":"String"}');
	searchButton(tagTable,searchCase);

	searchCase.pop();
	searchCase.pop();

}

function citesSort(){
	searchCase.push('{"tempMatchType":"7","propertyName":"menuId","propertyValue1":"' + $("#menuId").val() + '","tempType":"String"}');
	searchCase.push('{"tempMatchType":"9","propertyName":"citesSort","propertyValue1":citesSort,"tempType":"String"}');
	searchButton(tagTable,searchCase);
	searchCase.pop();
	searchCase.pop();
}


/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = tagTable.fnGetData(oo);
	ajaxRequest(basePath+"/ask/tag/updateStatus",{"id":aData.id},function(){
		freshTable(tagTable);
	});
}


/**
 * 上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = tagTable.fnGetData(oo);
	ajaxRequest(basePath+"/ask/tag/up",{"id":aData.id},function(res){
		if(res.success){
			freshTable(tagTable);
		}
	});
}

/**
 * 下移
 * @param obj
 */
function downMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = tagTable.fnGetData(oo);
	ajaxRequest(basePath+"/ask/tag/down",{"id":aData.id},function(res){
		if(res.success){
			freshTable(tagTable);
		}
	});
}
//返回上一页
$('#returnbutton').on('click',function(){
//	$("#dialogAddTagDiv").hide();
	window.location.href=basePath+'/home#ask/menu/index?page='+$('#page').val();
});

//修改标签
function toEdit(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = tagTable.fnGetData(oo); // get datarow
	$("#editId").val(aData.id);
	$("#editName").val(aData.name);
	openDialog("editTagDialog","dialogEditTagDiv","修改标签",580,200,true,"提交",function(){
		if($("#update-form").valid()){
			mask();
			$("#update-form").attr("action",basePath+"/ask/tag/update");
			$("#update-form").ajaxSubmit(function(data){
				unmask();
				if(data.success){
					$("#editTagDialog").dialog("close");

					layer.msg(data.errorMessage);
					freshTable(tagTable);
				}else{
					layer.msg(data.errorMessage);
				}
			});
		}
	});
}


