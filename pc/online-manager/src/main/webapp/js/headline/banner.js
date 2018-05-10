var bannerTable
var searchJson = new Array();
$(function(){
	document.onkeydown=function(event){
		if(event.keyCode == 13) {
            return false;
        }
	}
});
//
$(function(){
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
    var dataFields = [
              		{ "title": checkbox,"class":"center","width":"5%","height":"68px","sortable":false,"data": 'id',"visible":false,"mRender":function(data,display,row){
            		    return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
            		}},  
            		{title: 'ID', "class": "center", "width": "5%","height":"68px","data": 'id',"sortable": false},
            		{title: '文章标题', "class": "center","height":"68px","data": 'title', "sortable": false,"mRender":function (data, display, row) {
            			return data.replace(/</g,'&lt;').replace(/>/g,'&gt;');
            	    }},
            		{title: 'banner图片', "class": "center", "width": "144px","height":"38px","data": 'bannerPath', "sortable": false,"mRender":function(data,display,row){
            			if(data!=null&&""!=data){
            				return "<img src='"+data+"' style='width:128px;height:68px;cursor:pointer;' onclick='showImg(\""+row.id+"\",\""+row.title+"\",\""+row.bannerPath+"\")'/>";
            			}else{
            				return "暂无图片"
            			}
                    	
                    }},
                    {title: '分类', "class": "center", "width": "9%","height":"68px","data": 'typeName', "sortable": false},
                    {title: '引用的标签', "class": "center", "width": "12%","height":"68px","data": 'tagName', "sortable": false},
                    {title: '作者', "class": "center", "width": "9%","height":"68px","data": 'author', "sortable": false},
                    {title: '更新时间', "class": "center","width": "15%","data": 'createTime', "sortable": false},
                    {"sortable": false,"class": "center","width":"10%","title":"排序","mRender":function (data, display, row) {
                    	return '<div class="hidden-sm hidden-xs action-buttons">'+
                		'<a class="blue" href="javascript:void(-1);" title="上移" onclick="upMove(this)" name="upa"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
                    	'<a class="blue" href="javascript:void(-1);" title="下移" onclick="downMove(this)" name="downa"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
                	}},
                    { "sortable": false,"class": "center","width":"15%","title":"操作","mRender":function (data, display, row) {
                    		return '<div class="hidden-sm hidden-xs action-buttons">'+
                			'<a class="blue" href="javascript:void(-1);" title="取消推荐" onclick="updateRec(this);">取消推荐</a> ' +
                    		'<a class="blue" href="javascript:void(-1);" title="设置图片" onclick="updateRecImg(this);">设置图片</a> </div>';
                  		}
                	}
     ]
    bannerTable = initTables("bannerTable", basePath + "/headline/banner/list", dataFields, true, true, 1,null,searchJson,function(data){
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
		$("#updateRecImg-form").validate({
			messages: {
				recImgPath: {
					required: "请选择图片！"
				}
			}
		});
		
    });
    
})
//取消推荐

function updateRec(obj){
	var oo = $(obj).parent().parent().parent();
	var row = bannerTable.fnGetData(oo); // get datarow
	ajaxRequest(basePath+"/headline/banner/updateRec",{"id":row.id},function(data){
		if(data.success){
			layer.msg("取消成功！");
			freshTable(bannerTable);
		}else{
			layer.msg("取消失败！");
		}
	});
};

/**
 * 设置图片
 * @param obj
 */
function updateRecImg(obj){
	var oo = $(obj).parent().parent().parent();
	var row = bannerTable.fnGetData(oo); // get datarow
	
	$(".clearfixUpdate").remove();
 	$("#updateRecImg").prepend("<div class=\"clearfixUpdate\">\n" +
						  "	<input type=\"file\" name=\"update_bannerPath_file\" id=\"update_bannerPath_file\" class=\"uploadImg\"/>\n" +
						  "</div>");
 	
 	createImageUpload($('.uploadImg'));//生成图片编辑器

	$("#update_id").val(row.id);
	$("#update_bannerPath").val(row.bannerPath);
	
	if(row.bannerPath != null && row.bannerPath != ""){
		reviewImageRecImg("update_bannerPath_file",row.bannerPath);
		
		$(".remove").hide();
	}

	var dialog = openDialog("updateRecImgDialog","dialogUpdateRecImgDiv","设置banner图片",580,330,true,"确定",function(){
 		if($("#updateRecImg-form").valid()){
 			 mask();
 			 $("#updateRecImg-form").attr("action", basePath+"/headline/banner/updateBannerPath");
 	            $("#updateRecImg-form").ajaxSubmit(function(data){
                    data = getJsonData(data);
 	                unmask();
 	                if(data.success){
 	                    $("#updateRecImgDialog").dialog("close");
 	                    layer.msg(data.errorMessage);
 	                    freshTable(bannerTable);
 	                }else{
 	                	layer.msg(data.errorMessage);
 	               }
 	         });
 		}
 	});
};
/**
 * 上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = bannerTable.fnGetData(oo);
	ajaxRequest(basePath+'/headline/banner/upMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(bannerTable);
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
	var aData = bannerTable.fnGetData(oo);
	ajaxRequest(basePath+'/headline/banner/downMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(bannerTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

/**
 * 图片回显
 * @param inputId  input[type=file]的id
 * @param imgSrc   图片路径
 */
function reviewImageRecImg(inputId,imgSrc){
	var fileName = imgSrc;
	if(imgSrc.indexOf("/")>-1){
		fileName = imgSrc.substring(imgSrc.lastIndexOf("/")+1);
	}
	$("#"+inputId).parent().find('.ace-file-name').remove();
	$("#"+inputId).parent().find(".ace-file-container").addClass('hide-placeholder').attr('data-title', null)
	.addClass('selected').html('<span class="ace-file-name" data-title="'+fileName+'">'
			 +('<img class="middle" style="width: 252px; height: 97px;" src="'+imgSrc+'"><i class="ace-icon fa fa-picture-o file-image"></i>')
					 +'</span>');
}
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

//图片上传统一上传到附件中心---- 修改  列表页
$("#updateRecImg-form").on("change","#update_bannerPath_file",function(){
 	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
 	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
 		layer.alert("图片格式错误,请重新选择.");
 		this.value="";
 		return;
 	}
 	var id = $(this).attr("id");
 	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
 		if (data.error == 0) {
 			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
 			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 252px; height: 97px;");
 			
 			$("#update_bannerPath").val(data.url);
 			document.getElementById("update_bannerPath_file").focus();
 			document.getElementById("update_bannerPath_file").blur();
 			$(".remove").hide();
 		}else {
 			alert(data.message);
 		}
 	})
 });