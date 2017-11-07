var infoTable;
var searchJson = new Array();
var courseArray=new Array();
var nowTime;
$(function() {
	nowTime=show();
	loadInfoList();
    //下线时间 时间控件
    createDatePicker($("#startTime"));
    createDatePicker($("#stopTime"));
});

//投诉列表展示
function loadInfoList(){
	
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var dataFields = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
    	}},
    	{ "title": "序号", "class": "center","width":"5%","sortable": false,"data":"id",datafield: 'xuhao'},
        {title: '资讯标题', "class": "center","data": 'name', "sortable": false},
        {title: '分类', "class": "center", "width": "6%","data": 'informationtypeName', "sortable": false},
        {title: '链接地址', "class": "center", "width": "12%","data": 'hrefAdress', "sortable": false,"mRender":function(data,display,row){
        	
        	return "<div style='white-space:normal;'><a href='"+data+"' target='blank'>"+data+"</a></div>";
    	}},
        {title: '点击量', "class": "center", "width": "6%","data": 'clickCount', "sortable": false},
        {title: '展示时间段', "class": "center", "width": "12%","data": 'createTime', "sortable": false,"mRender":function(data,display,row){
        	var dayCount;
        	if(row.startTime==null&&row.endTime==null){//新添加资讯
        		return null
        	}else if(row.startTime!=null&&row.endTime==null){//启用状态的资讯（每次启用必须设置禁用时间为null）
        		dayCount=dateDiff(row.startTime,nowTime);
        		return dayCount+"天</br>("+row.startTime+"至"+nowTime+")"
        	}else if(row.startTime!=null&&row.endTime!=null){//禁用状态的资讯
        		dayCount=dateDiff(row.startTime,row.endTime);
        		return dayCount+"天</br>("+row.startTime+"至"+row.endTime+")"
        	}else{
        		return "数据异常";
        	}

        }},
        {title: '创建人', "class": "center", "width": "8%","data": 'createPerson', "sortable": false},
        {title: '状态', "class": "center", "width": "8%", "data": 'status', "sortable": false,"mRender":function(data,display,row){
        	var status ;
        	if(data == 0){
        		status = "已禁用";
            }else if(data == 1){
            	status = "已启用";
            }else {
            	status = "数据异常";
            }

        	return status;
        }},
        {"sortable": false,"class": "center","width":"80px","title":"排序","mRender":function (data, display, row) {
        	var str;
        	if(row.status ==1&&row.last!="yes"){
        		str='<a class="blue" href="javascript:void(-1);" title="上移" onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
            	'<a class="blue" href="javascript:void(-1);" title="下移" onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
        	}else if(row.status ==1&&row.last=="yes"){
        		str='<a class="blue" href="javascript:void(-1);" title="上移" onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
            	'<a class="gray" href="javascript:void(-1);" title="下移" ><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
        	}else{
        		str='<a class="gray" href="javascript:void(-1);" title="上移"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
            	'<a class="gray" href="javascript:void(-1);" title="下移" ><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
        	}
        	return '<div class="hidden-sm hidden-xs action-buttons">'+str;
    		}},
        
        {
            "sortable": false,"data":"id","class": "center","width":"8%","title":"操作","mRender":function (data, display, row) {
            	if(row.status=="1"){
    	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
    				'<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
    				'<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> ';
    	    	}else{
    	    		return '<div class="hidden-sm hidden-xs action-buttons">'+
    				'<a class="blue" href="javascript:void(-1);" title="修改" onclick="toEdit(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
    				'<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> ';
    	    	}
            }
        },
        { "title": "是否设为举荐", "class": "center","sortable": false,"data":"isHot","visible":false },
    ];

    infoTable = initTables("infoTable", basePath + "/operate/information/findinformationList", dataFields, true, true, 2,null,searchJson,function(data){
        var iDisplayStart = data._iDisplayStart;
        var countNum = data._iRecordsTotal;//总条数
        pageSize = data._iDisplayLength;//每页显示条数
        currentPage = iDisplayStart / pageSize +1;//页码
    	if(currentPage == 1){//第一页的第一行隐藏向上箭头
			$("#infoTable tbody tr:first td").eq(9).find('a').eq(0).css("pointer-events","none").removeClass("blue").addClass("gray");
		}
		if(countNum/pageSize < 1 || countNum/pageSize == 1){//数据不足一页隐藏下移箭头
			$("#infoTable tbody tr:last td").eq(9).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
		}
		var countPage;
		
		if(countNum%pageSize == 0){
			countPage = parseInt(countNum/pageSize);
		}else{
			countPage = parseInt(countNum/pageSize) + 1;
		}
		if(countPage == currentPage){//隐藏最后一条数据下移
			$("#infoTable tbody tr:last td").eq(9).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
		}
    });
}

 //条件搜索
 function search(orders){
	 
     var startTime = $("#startTime").val(); //开始时间
     var stopTime = $("#stopTime").val(); //结束时间
     var accuseType = $("#accuseType").val();
     var status = $("#status").val();
     var content = $("#content").val();

     
     if(startTime != "" || stopTime != "") {
         
    	 if (startTime != "" && stopTime != "" && startTime > stopTime) {
             alertInfo("开始日期不能大于结束日期");
             return;
         }
         searchJson.push('{"tempMatchType":"7","propertyName":"startTime","propertyValue1":"' + startTime + '","tempType":"String"}');
         searchJson.push('{"tempMatchType":"6","propertyName":"stopTime","propertyValue1":"' + stopTime + '","tempType":"String"}');
     }
     
     if(accuseType != ""){
    	 searchJson.push('{"tempMatchType":"8","propertyName":"accuseType","propertyValue1":"' + accuseType + '","tempType":"String"}');
     }
     
     if(status!=null&&status!=""){
         searchJson.push('{"tempMatchType":"9","propertyName":"status","propertyValue1":"' + status + '","tempType":"String"}');
     }

     if(content!=null&&content!=""){
         searchJson.push('{"tempMatchType":"10","propertyName":"content","propertyValue1":"' + content + '","tempType":"String"}');
     }
     searchButton(accuseTable,searchJson);
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();
     searchJson.pop();

}
//新增框
 $(".add_bx_info").click(function(){
	 $("#addInfo-form").resetForm();
	 var dialog = openDialog("addInfoDialog","dialogAddInfoDiv","新增资讯",580,300,true,"确定",function(){
		 if($("#addInfo-form").valid()){
			 mask();
			 $("#addInfo-form").attr("action", basePath+"/operate/information/addInfo");
			  $("#addInfo-form").ajaxSubmit(function(data){
	            	try{
              		data = jQuery.parseJSON(jQuery(data).text());
              	}catch(e) {
              		data = data;
              	  }
	                unmask();
	                if(data.success){
	                    $("#addInfoDialog").dialog("close");
	                    layer.msg(data.errorMessage);
	                    freshTable(infoTable);
	                    $("html").eq(0).css("overflow","scroll");
	                }else{
	                	layer.msg(data.errorMessage);
	                }
	            });
		 }
	 });
 });
 /**
  * 批量逻辑删除
  * 
  */

 $(".dele_bx").click(function(){
 	deleteAll(basePath+"/operate/information/deletes",infoTable);
 });
 
//修改
 function toEdit(obj){
	 $("#editInfo-form").resetForm();
	 var oo = $(obj).parent().parent().parent();
	 var row = infoTable.fnGetData(oo); // get datarow
	 $("#editId").val(row.id);
	 $("#editName").val(row.name);
	 $("#editInformationType").val(row.informationtype);
	 $("#editHrefAdress").val(row.hrefAdress);
	 if(row.isHot==true){
 		$("#editIsHot").attr("checked", true); 
 	}else{
 		$("#editIsHot").attr("checked", false); 
 	}
	 
	 for(i=0;i<$("#editInformationType option").length;i++){
 		if($("#editInformationType option").eq(i).val()==row.informationtype){
      			$("#editInformationType option").eq(i).attr("select","selected"); 
      			$("#editInformationType").val(row.informationtype);
      		}
 	}
	 
	 
	 var dialog = openDialog("editInfoDialog","dialogEditInfoDiv","修改资讯",580,300,true,"确定",function(){
		 if($("#editInfo-form").valid()){
			 mask();
			 $("#editInfo-form").attr("action", basePath+"/operate/information/updateInfoById");
			  $("#editInfo-form").ajaxSubmit(function(data){
	                unmask();
	                if(data.success){
	                    $("#editInfoDialog").dialog("close");
	                    layer.msg(data.errorMessage);
	                    freshTable(infoTable);
	                }else{
	                	layer.msg(data.errorMessage);
	                }
	            });
		 }
	 });
	 
 }
 
 
 function search(){
		searchButton(infoTable);

	};
	/**
	 * 状态修改
	 * @param obj
	 */
function updateStatus(obj){
	var oo = $(obj).parent().parent().parent();
	var row = infoTable.fnGetData(oo); // get datarow
	ajaxRequest(basePath+"/operate/information/updateStatus",{"id":row.id},function(data){
		if(data.success){
			  layer.msg(data.errorMessage);
			  freshTable(infoTable);
		  }else{
			  layer.msg(data.errorMessage);
		  }
		
	});
};	
	

/**
 * 上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = infoTable.fnGetData(oo);
	ajaxRequest(basePath+'/operate/information/upMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(infoTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

/**
 * 下移
 * @param obj
 */
function downMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = infoTable.fnGetData(oo);
	ajaxRequest(basePath+'/operate/information/downMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(infoTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

//获取当前时间
 function show(){
	   var mydate = new Date();
	   var str = "" + mydate.getFullYear() + "-";
	   str += (mydate.getMonth()+1) + "-";
	   str += mydate.getDate() ;
	   return str;
	  }
 
//字符串转成Time(dateDiff)所需方法
 function stringToTime(string) {
     var f = string.split(' ', 2);
     var d = (f[0] ? f[0] : '').split('-', 3);
     var t = (f[1] ? f[1] : '').split(':', 3);
     return (new Date(
    parseInt(d[0], 10) || null,
    (parseInt(d[1], 10) || 1) - 1,
 parseInt(d[2], 10) || null,
 parseInt(t[0], 10) || null,
 parseInt(t[1], 10) || null,
 parseInt(t[2], 10) || null
 )).getTime();
 }

 function dateDiff(date1, date2) {
     var type1 = typeof date1, type2 = typeof date2;
     if (type1 == 'string')
         date1 = stringToTime(date1);
     else if (date1.getTime)
         date1 = date1.getTime();
     if (type2 == 'string')
         date2 = stringToTime(date2);
     else if (date2.getTime)
         date2 = date2.getTime();

     return (date2 - date1) / (1000 * 60 * 60 * 24); //结果是秒
 }
 
function initType(){
	$.ajax({
		url:basePath+'/operate/information/getTypes',  
		 type:'get',      
		 dataType:'json',
		 success:function(data){
			 if(data.success){
				 $('#addTypefo-form').html('');
				 if(data.resultObject.length > 0){
					 for(var i=0; i<data.resultObject.length; i++){
						 initTypeHtml(i,data.resultObject[i].name,data.resultObject[i].value);
					 }
				 } else {
					 initTypeHtml(0,'',getRandomInt(999999999));
				 }
			 }else{
				 alertInfo('获得分类失败！'+data.errorMessage);
			 }
        }
	});
}
function initTypeHtml(index,name,value){
	var str = 
		'<div class="form-group" id="infotypediv-'+index+'"  style="margin-top: 18px;" >\
			<label class="col-sm-3 control-label no-padding-right">分类名称: </label>\
			<div class="col-sm-6">\
				<input type="text"   name="vos['+index+'].name"  id="typeName'+index+'"  value="'+name+'" class="col-xs-10 col-sm-12 {required:true,minlength:2,maxlength:40}">\
				<input type="hidden" name="vos['+index+'].value" id="typevalue'+index+'" value="'+value+'">\
			</div>\
			<button type="button" class="btn btn-sm btn-success" title="删除" onclick="$(this).parent().remove();" >\
				<i class="glyphicon glyphicon-remove"></i>\
			</button>\
		</div>';
	$('#addTypefo-form').append(str);
}

$(".add_type").click(function(){
	initType();
	var dialog = openDialog("addTypefoDialog","dialogAddTypefoDiv","分类管理",380,500,true,"确定",function(){
		if($("#addTypefo-form").valid()){
			 mask();
			 $("#addTypefo-form").attr("action", basePath+"/operate/information/updateTypes");
			 $("#addTypefo-form").ajaxSubmit(function(data){
				 try{
					 data = jQuery.parseJSON(jQuery(data).text());
				 }catch(e) {
					 data = data;
				 }
				 unmask();
				 if(data.success){
					 layer.msg('操作成功！');
					 $("#addTypefoDialog").dialog("close");
					 location.href = "#";
					 location.href =basePath+"/home#operate/information/index";
					 $("html").eq(0).css("overflow","scroll");
				 }else{
					 layer.msg('操作失败！'+data.errorMessage);
				 }
			 });
		 }
	});
});
$(".add_info_type").click(function(){
	var index = 0;
	var last = $("#addTypefo-form div:last-child");
	if(last.length > 0){
		index = $(last[0]).attr('id').split('-')[1];
		index ++;
	}
	initTypeHtml(index,'',getRandomInt(999999999));
});
 
 
 