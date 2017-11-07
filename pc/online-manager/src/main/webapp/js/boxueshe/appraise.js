var appraiseTable;
var searchCase;
var searchJson = new Array();
$(function() {
 //下线时间 时间控件
	createDatePicker($("#startTime"));
	createDatePicker($("#stopTime"));
});
//展示列表
$(function(){
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
		var objData = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"visible":false,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
		}},
       {title: '评价内容', "class": "center","width": "45%","data": 'content', "sortable": false,"mRender":function (data, display, row) {
	    	  var content = "<a href='javascrip:void(0);return false;' class='blue' onclick='showAppraise(this);return false;'>"+data.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/[\r\n]/g,"")+"</a>";
              return content;
	    }},
       {title: '相关文章ID', "class": "content","width": "10%","data": 'articleId', "sortable": false},
       {title: '作者/用户昵称', "class": "content","width": "12%","data": 'name', "sortable": false},
       {title: '评价时间', "class": "center","width": "15%","data": 'createTime', "sortable": false},
       { "sortable": false,"class": "center","width":"15%","title":"操作","mRender":function (data, display, row) {
		    return '<div class="hidden-sm hidden-xs action-buttons">'+
		    '<a class="blue ace-icon fa fa-trash-o bigger-130"  href="javascript:void(-1);" title="删除"  onclick="deleteRow(this)"></a></div>';
		}}
      ]
    appraiseTable = initTables("appraiseTable", basePath + "/boxueshe/appraise/list", objData,true, true, 0,null,searchCase,function(data){
        var iDisplayStart = data._iDisplayStart;
        var countNum = data._iRecordsTotal;//总条数
        pageSize = data._iDisplayLength;//每页显示条数
        currentPage = iDisplayStart / pageSize +1;//页码
        if(currentPage == 1){//第一页的第一行隐藏向上箭头
            $("#appraiseTable tbody tr:first td:last a:eq(3)").css("pointer-events","none").removeClass("blue").addClass("gray");
        }
        if(countNum/pageSize < 1 || countNum/pageSize == 1){//数据不足一页隐藏下移箭头
            $("#appraiseTable tbody tr:last td:last a:eq(4)").css("pointer-events","none").removeClass("blue").addClass("gray");
        }
        var countPage;
        if(countNum%pageSize == 0){
            countPage = parseInt(countNum/pageSize);
        }else{
            countPage = parseInt(countNum/pageSize) + 1;
        }
        if(countPage == currentPage){//隐藏最后一条数据下移
            $("#appraiseTable tbody tr:last td:last a:eq(4)").css("pointer-events","none").removeClass("blue").addClass("gray");
        }    	
    });  
})

//条件搜索
 function search(){
    var startTime = $("#startTime").val(); //开始时间
    var stopTime = $("#stopTime").val(); //结束时间
    if(startTime != "" || stopTime != "") {
        
   	 if (startTime != "" && stopTime != "" && startTime > stopTime) {
            alertInfo("开始日期不能大于结束日期");
            return;
        }
        searchJson.push('{"tempMatchType":"7","propertyName":"startTime","propertyValue1":"' + startTime + '","tempType":"String"}');
        searchJson.push('{"tempMatchType":"6","propertyName":"stopTime","propertyValue1":"' + stopTime + '","tempType":"String"}');
    }
     searchButton(appraiseTable,searchJson);
}
//点击评价内容详情
function showAppraise(obj){
	var oo = $(obj).parent().parent();
	var aData = appraiseTable.fnGetData(oo);
	
	$("#show_id").val(aData.id);
	$("#showDiv").text(aData.content);
	
	var dialog = openDialog("showAppraise","dialogShowAppraiseDiv","评价内容",480,300,true,"删除此评论",function(){
		ajaxRequest(basePath+"/boxueshe/appraise/delete/"+aData.id,null,function(res){
			if(res.success){
				$("html").css({'overflow-x':'hidden','overflow-y':'auto'});
				freshTable(appraiseTable);
				dialog.dialog( "close" ); //删除评论后关闭框
			}
			//layerAlterInfo(res.errorMessage);
		});
	
 	});
}


//单条删除
function deleteRow(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = appraiseTable.fnGetData(oo);
	console.log(aData);
	showDelDialog(function(){
		ajaxRequest(basePath+"/boxueshe/appraise/delete/"+aData.id+"/"+aData.articleId,null,function(res){
			if(res.success){
				freshTable(appraiseTable);
			}
			//layerAlterInfo(res.errorMessage);
		});
	});
}