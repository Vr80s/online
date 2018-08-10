var banner2Table;
var banner2Form;
var nowTime;
var searchJson = new Array();
var courseArray=new Array();
$(function() {
	nowTime=show();
    $("#addProjectType").val(1);
    $("#updateProjectType").val(1);

    searchJson.push('{"tempMatchType":1,"propertyName":"search_type","propertyValue1":"1","tempType":Integer}');
    loadBanner2List();
});

//列表展示
function loadBanner2List(){
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var dataFields = [
		{ "title": checkbox,"class":"center","width":"5%","height":"68px","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
		    return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
		}},
        {title: '序号', "class": "center", "width": "5%","height":"68px","data": 'id',datafield: 'xuhao', "sortable": false},
        {title: '专题类别名称', "class": "center","height":"68px","data": 'name', "sortable": false},
        { "title": "创建人", "class":"center","sortable":false,"data": 'createPerson' },
		{ "title": "链接", "class":"center","sortable":false,"data": 'linkCondition' },
		{ "title": "状态", "class":"center","sortable":false,"data": 'status',"mRender":function (data, display, row) {
			return row.status=="1"?"已启用":"已禁用";
			}
		},
 /*       { "title": "启用时间", "class":"center","sortable":false,"data": 'startTime' },
        { "title": "关闭时间", "class":"center","sortable":false,"data": 'endTime' },
        { "title": "点击数", "class":"center","sortable":false,"data": 'clickNum' },*/
	     {title: '排序', "class": "center", "width": "8%","height":"34px","data": 'sort', "sortable": false,"mRender":function(data, display, row){
	        	var str;
	        	if(row.status ==1){//如果是禁用
	        		str='<a class="blue" name="upa" href="javascript:void(-1);" title="上移" onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
	            	'<a class="blue" href="javascript:void(-1);" name="downa" title="下移" onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
	        	}else{//如果是不禁用
	        		str='<a class="gray" href="javascript:void(-1);" title="上移"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
	            	'<a class="gray" href="javascript:void(-1);" title="下移" ><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
	        	}
	        	return '<div class="hidden-sm hidden-xs action-buttons">'+str;
	     }},
        {title:"操作","class": "center","width":"6%","height":"34px","data":"id","sortable": false,"mRender":function (data, display, row) {

                var buttons= '<div class="hidden-sm hidden-xs action-buttons"><a class="blue" href="javascript:void(-1);" title="修改" onclick="updateBanner2(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
	   			if(row.status==1) {
					buttons+='<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> ';
				}else{
					buttons+='<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> ';
				};
				buttons += "</div>";
                return buttons;
            }
        }
    ];

    banner2Table = initTables("banner2Table", basePath + "/cloudClass/projectType/list", dataFields, true, true, 2,null,searchJson,function(data){
        
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

    /**
     * 展示课程专题管理
     *
     */
    $(".zykgl_bx").click(function(){
        $("#courseDiv").show();

        var banneType =  $(this).attr("title");

        $("#addProjectType").val(banneType);
        $("#updateProjectType").val(banneType);

        var json = new Array();
        json.push('{"tempMatchType":1,"propertyName":"search_type","propertyValue1":"'+banneType+'","tempType":Integer}');
        searchButton(banner2Table,json);
    });

    banner2Form = $("#addBanner2-form").validate({
        messages: {
        	name: {
				required:"请输入banner名称！",
            },
            icon: {
				required:"请选择图片！"
			}
        }
    });
    
    banner2FormEdit = $("#updateBanner2-form").validate({
        messages: {
        	name: {
				required:"请输入banner名称！",
            },
            icon: {
				required:"请选择图片！"
			}			
        }
    });

    createImageUpload($('.uploadImg'));//生成图片编辑器
}

 //条件搜索
 function search(){
	 
     var search_status = $("#search_status").val();
     var search_description = $("#search_description").val();
     
     if(search_status != ""){
    	 searchJson.push('{"tempMatchType":"8","propertyName":"status","propertyValue1":"' + search_status + '","tempType":"String"}');
     }
     
     if(search_description!=null&&search_description!=""){
         searchJson.push('{"tempMatchType":"9","propertyName":"description","propertyValue1":"' + search_description + '","tempType":"String"}');
     }

     searchButton(banner2Table,searchJson);
     searchJson.pop();
     searchJson.pop();

}

//新增框
 $(".add_bx").click(function(){
 	banner2Form.resetForm();
 	
 	//$(".remove").trigger("click");
 	$(".clearfixAdd").remove();
 	$("#addDiv").prepend("<div class=\"clearfixAdd\">\n" +
						  "	<input type=\"file\" name=\"imgPath_file\" id=\"imgPath_file\" class=\"uploadImg\"/>\n" +
						  "</div>");
 	createImageUpload($('#imgPath_file'));//生成图片编辑器

 	var dialog = openDialog("addBanner2Dialog","dialogAddBanner2Div","新增banner",580,500,true,"确定",function(){
 		if($("#addBanner2-form").valid()){
 			 mask();
 			 $("#addBanner2-form").attr("action", basePath+"/cloudClass/projectType/add");
 	            $("#addBanner2-form").ajaxSubmit(function(data){
 	            	data = getJsonData(data);
 	                unmask();
 	                if(data.success){
 	                    $("#addBanner2Dialog").dialog("close");
 	                    layer.msg(data.errorMessage);
 	                    freshTable(banner2Table);
 	                }else{
 	                	layer.msg(data.errorMessage);
 	               }
 	               $("html").eq(0).css("overflow","scroll");
 	         });
 		}
 	});
 });

function updateBanner2(obj){
	var oo = $(obj).parent().parent().parent();
	var row = banner2Table.fnGetData(oo); // get datarow
	$("#updateCourse-form").resetForm();
	$("#update_description").val(row.name);
	$("#update_imgPath").val(row.icon);
	$("#update_id").val(row.id);
    $("#update_linkCondition").val(row.linkCondition);


	reviewImage("update_imgPath_file",row.icon);
	$(".remove").hide();
	
 	var dialog = openDialog("updateBanner2Dialog","dialogUpdateBanner2Div","修改banner",580,500,true,"确定",function(){
 		if($("#updateBanner2-form").valid()){
 			 mask();
 			 $("#updateBanner2-form").attr("action", basePath+"/cloudClass/projectType/update");
 	            $("#updateBanner2-form").ajaxSubmit(function(data){
 	            	data = getJsonData(data);
 	                unmask();
 	                if(data.success){
 	                    $("#updateBanner2Dialog").dialog("close");
 	                    layer.msg(data.errorMessage);
 	                    freshTable(banner2Table);
 	                }else{
 	                	layer.msg(data.errorMessage);
 	               }
 	               $("html").eq(0).css("overflow","scroll");
 	         });
 		}
 	});
}

/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj){
	var oo = $(obj).parent().parent().parent();
	var row = banner2Table.fnGetData(oo); // get datarow
	ajaxRequest(basePath+"/cloudClass/projectType/updateStatus",{"id":row.id},function(data){
		layer.msg(data.resultObject);
		freshTable(banner2Table);
	});
};

//图片上传统一上传到附件中心---- 修改  列表页
$("#addBanner2-form").on("change","#imgPath_file",function(){
 	var v = this.value.split(".")[1].toUpperCase();
 	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
 		layer.alert("图片格式错误,请重新选择.");
 		this.value="";
 		return;
 	}
 	var id = $(this).attr("id");
 	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
 		if (data.error == 0) {
 			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
 			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
 			
 			$("#add_imgPath").val(data.url);
 			document.getElementById("imgPath_file").focus();
 			document.getElementById("imgPath_file").blur();
 			$(".remove").hide();
 		}else {
 			alert(data.message);
 		}
 	})
 });

//图片上传统一上传到附件中心---- 修改  列表页
$("#updateBanner2-form").on("change","#update_imgPath_file",function(){
 	var v = this.value.split(".")[1].toUpperCase();
 	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
 		layer.alert("图片格式错误,请重新选择.");
 		this.value="";
 		return;
 	}
 	var id = $(this).attr("id");
 	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
 		if (data.error == 0) {
 			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
 			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
 			
 			$("#update_imgPath").val(data.url);
 			document.getElementById("update_imgPath_file").focus();
 			document.getElementById("update_imgPath_file").blur();
 			$(".remove").hide();
 		}else {
 			alert(data.message);
 		}
 	})
 });

 //展示图片大图
 function showImg(rowId,rowDescription,rowImgPath){
	 var a=new Image()
	 a.src = rowImgPath;
	 
	 layer.open({
		  title:false,
		  type: 1,
		  skin: 'layui-layer-rim', //加上边框 
		  area: [(a.widht+20)+"px",(a.height+12)+"px"], //宽高 926 386
		  content: '<img src="'+rowImgPath+'" onclick="layer.closeAll()"/>',
		  shadeClose:true
		});
	 
}

 /**
  * 批量逻辑删除
  * 
  */
$(".dele_bx").click(function(){
 	deleteAll(basePath+"/cloudClass/projectType/deletes",banner2Table);
});

/**
 * 上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = banner2Table.fnGetData(oo);
	ajaxRequest(basePath+'/cloudClass/projectType/upMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(banner2Table);
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
	var aData = banner2Table.fnGetData(oo);
	ajaxRequest(basePath+'/cloudClass/projectType/downMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(banner2Table);
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