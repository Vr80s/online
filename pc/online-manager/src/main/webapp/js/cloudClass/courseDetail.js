var courseDetailForm;
var _courseRecTable;
var _courseTable;
var tempDesId = "";//用于判断切换保存的变量
var ueEditccpe =  UE.getEditor('courseContentPreviewEdit',{
	toolbars:[['source', //源代码
	          'undo', //撤销
	          'redo', //重做
	          'bold', //加粗
			  'forecolor', //字体颜色
			  'backcolor', //背景色
	          'indent', //首行缩进
	          'removeformat',//清除格式
			  'formatmatch', //格式刷
			  'blockquote', //引用
	          'fontfamily', //字体
	          'fontsize', //字号
	          'paragraph', //段落格式
	          'italic', //斜体
	          'underline', //下划线
	          'strikethrough', //删除线
	          'superscript', //上标
	          'subscript', //下标
	          'touppercase', //字母大写
	          'tolowercase', //字母小写
	          'justifyleft', //居左对齐
	          'justifyright', //居右对齐
	          'justifycenter', //居中对齐
	          'justifyjustify',//两端对齐
			  'link', //超链接
	          'unlink', //取消链接
	          'simpleupload', //单图上传
	          'insertimage', //多图上传
  			  'insertvideo', //视频
	          'emotion', //表情
	          'map', //Baidu地图
	          ] ],
//	toolbars: [
//				['anchor', //锚点
//				'undo', //撤销
//				'redo', //重做
//				'bold', //加粗
//				'indent', //首行缩进
//				'snapscreen', //截图
//				'italic', //斜体
//				'underline', //下划线
//				'strikethrough', //删除线
//				'subscript', //下标
//				'fontborder', //字符边框
//				'superscript', //上标
//				'formatmatch', //格式刷
//				'source', //源代码
//				'blockquote', //引用
//				'pasteplain', //纯文本粘贴模式
//				'selectall', //全选
//				'print', //打印
//				'preview', //预览
//				'horizontal', //分隔线
//				'removeformat', //清除格式
//				'time', //时间
//				'date', //日期
//				'unlink', //取消链接
//				'insertrow', //前插入行
//				'insertcol', //前插入列
//				'mergeright', //右合并单元格
//				'mergedown', //下合并单元格
//				'deleterow', //删除行
//				'deletecol', //删除列
//				'splittorows', //拆分成行
//				'splittocols', //拆分成列
//				'splittocells', //完全拆分单元格
//				'deletecaption', //删除表格标题
//				'inserttitle', //插入标题
//				'mergecells', //合并多个单元格
//				'deletetable', //删除表格
//				'cleardoc', //清空文档
//				'insertparagraphbeforetable', //"表格前插入行"
//				'insertcode', //代码语言
//				'fontfamily', //字体
//				'fontsize', //字号
//				'paragraph', //段落格式
//				'simpleupload', //单图上传
//				'insertimage', //多图上传
//				'edittable', //表格属性
//				'edittd', //单元格属性
//				'link', //超链接
//				'emotion', //表情
//				'spechars', //特殊字符
//				'searchreplace', //查询替换
//				'map', //Baidu地图
//				'gmap', //Google地图
//				'insertvideo', //视频
//				'help', //帮助
//				'justifyleft', //居左对齐
//				'justifyright', //居右对齐
//				'justifycenter', //居中对齐
//				'justifyjustify', //两端对齐
//				'forecolor', //字体颜色
//				'backcolor', //背景色
//				'insertorderedlist', //有序列表
//				'insertunorderedlist', //无序列表
//				'fullscreen', //全屏
//				'directionalityltr', //从左向右输入
//				'directionalityrtl', //从右向左输入
//				'rowspacingtop', //段前距
//				'rowspacingbottom', //段后距
//				'pagebreak', //分页
//				'insertframe', //插入Iframe
//				'imagenone', //默认
//				'imageleft', //左浮动
//				'imageright', //右浮动
//				'attachment', //附件
//				'imagecenter', //居中
//				'wordimage', //图片转存
//				'lineheight', //行间距
//				'edittip ', //编辑提示
//				'customstyle', //自定义标题
//				'autotypeset', //自动排版
//				'webapp', //百度应用
//				'touppercase', //字母大写
//				'tolowercase', //字母小写
//				'background', //背景
//				'template', //模板
//				'scrawl', //涂鸦
//				'music', //音乐
//				'inserttable', //插入表格
//				'drafts', // 从草稿箱加载
//				'charts' // 图表
//				]
//	          ],
   autoHeightEnabled: false,
   autoFloatEnabled: true,
   enableAutoSave:false,
   imagePopup:false
});
var ueEditccpe_content =  UE.getEditor('courseDetail_content',{
	toolbars:[['source', //源代码
	           'undo', //撤销
	           'redo', //重做
	           'bold', //加粗
	           'forecolor', //字体颜色
	           'backcolor', //背景色
	           'indent', //首行缩进
	           'removeformat',//清除格式
	           'formatmatch', //格式刷
	           'blockquote', //引用
	           'fontfamily', //字体
	           'fontsize', //字号
	           'paragraph', //段落格式
	           'italic', //斜体
	           'underline', //下划线
	           'strikethrough', //删除线
	           'superscript', //上标
	           'subscript', //下标
	           'touppercase', //字母大写
	           'tolowercase', //字母小写
	           'justifyleft', //居左对齐
	           'justifyright', //居右对齐
	           'justifycenter', //居中对齐
	           'justifyjustify',//两端对齐
	           'link', //超链接
	           'unlink', //取消链接
	           'simpleupload', //单图上传
	           'insertimage', //多图上传
	           'insertvideo', //视频
	           'emotion', //表情
	           'map', //Baidu地图
	           ] ],
	           autoHeightEnabled: false,
	           autoFloatEnabled: true,
	           enableAutoSave:false,
	           imagePopup:false
});
//var ueEditccpe_courseOutline_content =  UE.getEditor('courseOutline_content',{
//	toolbars:[['source', //源代码
//	           'undo', //撤销
//	           'redo', //重做
//	           'bold', //加粗
//	           'forecolor', //字体颜色
//	           'backcolor', //背景色
//	           'indent', //首行缩进
//	           'removeformat',//清除格式
//	           'formatmatch', //格式刷
//	           'blockquote', //引用
//	           'fontfamily', //字体
//	           'fontsize', //字号
//	           'paragraph', //段落格式
//	           'italic', //斜体
//	           'underline', //下划线
//	           'strikethrough', //删除线
//	           'superscript', //上标
//	           'subscript', //下标
//	           'touppercase', //字母大写
//	           'tolowercase', //字母小写
//	           'justifyleft', //居左对齐
//	           'justifyright', //居右对齐
//	           'justifycenter', //居中对齐
//	           'justifyjustify',//两端对齐
//	           'link', //超链接
//	           'unlink', //取消链接
//	           'simpleupload', //单图上传
//	           'insertimage', //多图上传
//	           'insertvideo', //视频
//	           'emotion', //表情
//	           'map', //Baidu地图
//	           ] ],
//	           autoHeightEnabled: false,
//	           autoFloatEnabled: true,
//	           enableAutoSave:false,
//	           imagePopup:false
//});
var ueEditccpe_commonProblem_content =  UE.getEditor('commonProblem_content',{
	toolbars:[['source', //源代码
	           'undo', //撤销
	           'redo', //重做
	           'bold', //加粗
	           'forecolor', //字体颜色
	           'backcolor', //背景色
	           'indent', //首行缩进
	           'removeformat',//清除格式
	           'formatmatch', //格式刷
	           'blockquote', //引用
	           'fontfamily', //字体
	           'fontsize', //字号
	           'paragraph', //段落格式
	           'italic', //斜体
	           'underline', //下划线
	           'strikethrough', //删除线
	           'superscript', //上标
	           'subscript', //下标
	           'touppercase', //字母大写
	           'tolowercase', //字母小写
	           'justifyleft', //居左对齐
	           'justifyright', //居右对齐
	           'justifycenter', //居中对齐
	           'justifyjustify',//两端对齐
	           'link', //超链接
	           'unlink', //取消链接
	           'simpleupload', //单图上传
	           'insertimage', //多图上传
	           'insertvideo', //视频
	           'emotion', //表情
	           'map', //Baidu地图
	           ] ],
	           autoHeightEnabled: false,
	           autoFloatEnabled: true,
	           enableAutoSave:false,
	           imagePopup:false
});

$(function() {
	getSecoundMenu();
	$('#myTab a').click(function (e) {
	  e.preventDefault()
	  $(this).tab('show');
	  $("html").eq(0).css("overflow","scroll");
	})

	$('#myTab a[href="#profile"]').tab('show') // Select tab by name
	$('#myTab a:first').tab('show') // Select first tab
	$('#myTab a:last').tab('show') // Select last tab
	$('#myTab li:eq(2) a').tab('show') // Select third tab (0-indexed)

	document.onkeydown=function(event){

//		if(event.keyCode==13){
//			return false
//		}
	}


	courseDetailForm = $("#courseDetailForm").validate({
		messages : {
			smallImgPath:{
				required:"列表页图不能为空！"
			},detailImgPath:{
				required:"详情页图不能为空！"
			},courseDetail:{
				required:"课程详情内容不能为空！",
				minlength:"课程详情内容不能为空！",
			},courseOutline:{
				required:"课程大纲内容不能为空！",
				minlength:"课程大纲内容不能为空！"
			},commonProblem:{
				required:"常见问题不能为空！",
				minlength:"常见问题不能为空！"
			}
		}
	});

	$("#addDes-form").validate({
		messages : {
			courseTitle:{
				required:"故事标题不能为空！"
			}
		}
	});
	$("#updateDes-form").validate({
		messages : {
			courseTitle:{
				required:"故事标题不能为空！"
			}
		}
	});

	$.ajax({
		url:basePath+'/cloudclass/course/getCourseDetail',
		dataType:'json',
		type:'post',
		data:{courseId:$('#courseId').val()},
		success:function(data){
			if (data.success) {
				debugger;
				if(data.resultObject.smallImgPath && data.resultObject.smallImgPath != ''){
					$('#edit_smallImgPath').val(data.resultObject.smallImgPath);
					reviewImage("edit_smallImgPath", data.resultObject.smallImgPath);
				}
				/*2017-08-14---yuruixin*/
				if(data.resultObject.smallImgPath1 && data.resultObject.smallImgPath1 != ''){
					$('#edit_smallImgPath1').val(data.resultObject.smallImgPath1);
					reviewImage("edit_smallImgPath1", data.resultObject.smallImgPath1);
				}
				if(data.resultObject.smallImgPath2 && data.resultObject.smallImgPath2 != ''){
					$('#edit_smallImgPath2').val(data.resultObject.smallImgPath2);
					reviewImage("edit_smallImgPath2", data.resultObject.smallImgPath2);
				}
				/*2017-08-14---yuruixin*/
				if(data.resultObject.detailImgPath && data.resultObject.detailImgPath != ''){
					$('#edit_detailImgPath').val(data.resultObject.detailImgPath);
					reviewImage("edit_detailImgPath", data.resultObject.detailImgPath);
				}
				if(data.resultObject.courseDetail && data.resultObject.courseDetail != ''){
//					$('#courseDetail_content').html(data.resultObject.courseDetail);
					ueEditccpe_content.ready(function(){
						ueEditccpe_content.setContent(data.resultObject.courseDetail);
					});
					$('#courseDetail').html(data.resultObject.courseDetail);
				}
//				if(data.resultObject.courseOutline && data.resultObject.courseOutline != ''){
//					ueEditccpe_courseOutline_content.ready(function(){
//						ueEditccpe_courseOutline_content.setContent(data.resultObject.courseOutline);
//					});
////					$('#courseOutline_content').html(data.resultObject.courseOutline);
//					$('#courseOutline').html(data.resultObject.courseOutline);
//				} else {
//					initTemplate('outline','courseOutline_content','courseOutline');
//				}
				if(data.resultObject.commonProblem && data.resultObject.commonProblem != ''){
					ueEditccpe_commonProblem_content.ready(function(){
						ueEditccpe_commonProblem_content.setContent(data.resultObject.commonProblem);
					});
//					$('#commonProblem_content').html(data.resultObject.commonProblem);
					$('#commonProblem').html(data.resultObject.commonProblem);
				} else {
					initTemplate('problem','commonProblem_content','commonProblem');
				}
				if(data.resultObject.descriptionShow==1){//1是展示
					$("#descriptionShow").prop("checked",true);
				}else{
					$("#descriptionShow").prop("checked",false);
				}
				$("#titleXQ").text(data.resultObject.gradeName);
				$(".remove").hide();
			}
		}
	});

	function initTemplate(type,contentid,id){
		$.ajax({
			url:basePath+'/cloudclass/course/getTemplate',
			dataType:'json',
			type:'get',
			data:{type:type},
			success:function(data){
				if(data.success){
					switch (type) {
					case "outline":
						ueEditccpe_courseOutline_content.ready(function(){
							ueEditccpe_courseOutline_content.setContent(data.resultObject);
						});
						break;
					case "problem":
						ueEditccpe_commonProblem_content.ready(function(){
							ueEditccpe_commonProblem_content.setContent(data.resultObject);
						});
						break;

					default:
						break;
					}
//					$('#'+contentid).html(data.resultObject);
					$('#'+id).html(data.resultObject);
				}
			}
		});
	}

	createImageUpload($('.uploadImg'));//生成图片编辑器

//	$(".wysiwyg-editor").ace_wysiwyg({
//		toolbar : [ {
//			name : 'viewSource',
//			className : 'btn-info'
//		},null,'font', null, 'fontSize', null, {
//			name : 'bold',
//			className : 'btn-info'
//		}, {
//			name : 'italic',
//			className : 'btn-info'
//		}, {
//			name : 'strikethrough',
//			className : 'btn-info'
//		}, {
//			name : 'underline',
//			className : 'btn-info'
//		}, null, {
//			name : 'insertunorderedlist',
//			className : 'btn-success'
//		}, {
//			name : 'insertorderedlist',
//			className : 'btn-success'
//		}, {
//			name : 'outdent',
//			className : 'btn-purple'
//		}, {
//			name : 'indent',
//			className : 'btn-purple'
//		}, null, {
//			name : 'justifyleft',
//			className : 'btn-primary'
//		}, {
//			name : 'justifycenter',
//			className : 'btn-primary'
//		}, {
//			name : 'justifyright',
//			className : 'btn-primary'
//		}, {
//			name : 'justifyfull',
//			className : 'btn-inverse'
//		}, null, {
//			name : 'createLink',
//			className : 'btn-pink'
//		}, {
//			name : 'unlink',
//			className : 'btn-pink'
//		}, null, {
//			name : 'insertImage',
//			className : 'btn-success'
//		}, null, 'foreColor', null, {
//			name : 'undo',
//			className : 'btn-grey'
//		}, {
//			name : 'redo',
//			className : 'btn-grey'
//		} ],
//		'wysiwyg' : {
//			fileUploadError : showErrorAlert
//		},
//		uploadType:{type:'url',action:basePath+'/cloudclass/course/uploadImg'}//图片上传方式，url/base64
//	}).prev().addClass('wysiwyg-style2');

	initGsjd();//加载左侧树节点

	//这里开始加载第三个Tab页的东西

	var objRecData = [
      { "title": "序号", "class": "center","width":"5%","sortable": false,"data":"id" },
      { "title": "课程名称", "class":"center","width":"33%","sortable":false,"data": 'courseName' },
      { "title": "所属学科", "class":"center","width":"8%","sortable":false,"data": 'xMenuName' },
      { "title": "所属课程类别", "class":"center","width":"9%","sortable":false,"data": 'scoreTypeName' },
//      { "title": "授课方式", "class":"center","width":"10%","sortable":false,"data": 'teachMethodName' },
      { "title": "课程时长", "class":"center","width":"8%", "sortable":false,"data": 'courseLength'},
      { "title": "原价格/现价格", "class":"center","width":"9%","sortable":false,"mRender":function(data,display,row){
      	return data = row.originalCost+"/"+row.currentPrice;
      }},
      {"sortable": false,"class": "center","width":"10%","title":"排序","mRender":function (data, display, row) {
      	return '<div class="hidden-sm hidden-xs action-buttons">'+
  		'<a class="blue" href="javascript:void(-1);" title="上移" onclick="upMoveRec(this)" name="upa"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
      	'<a class="blue" href="javascript:void(-1);" title="下移" onclick="downMoveRec(this)" name="downa"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
  	}},
      { "sortable": false,"class": "center","width":"8%","title":"操作","mRender":function (data, display, row) {
      		return '<div class="hidden-sm hidden-xs action-buttons"><input type="hidden" name="realRecCourseId" value="'+row.showCourseId+'">'+
  			'<a class="blue" href="javascript:void(-1);" title="取消推荐" onclick="deletes(\''+row.id+'\');">取消推荐</a> </div>';
      }
  	}];

	var searchCaseRecTable = new Array();
	$("#searchDivTj .searchTr").each(function() {
		if (!isnull($(this).find('.propertyValue1').val())) {
			var propertyValue2 = $(this).find('.propertyValue2').val();
			if(!isnull(propertyValue2)){
				searchCaseRecTable.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
						+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()
						+',"propertyValue2":"'+propertyValue2+'"}');
			}else{
				searchCaseRecTable.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
						+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()+'}');
			}
		}
	});

	_courseRecTable = initTables("courseRecTable",basePath+"/cloudClass/courseRecommend/recList",objRecData,true,false,1,null,searchCaseRecTable,function(data){
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
		$("#courseRecTable_info").hide();
	});

//	 //重载方法，实现图片等附件传到我们的附件中心

    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function(action) {
    	var url = '/ueditor/upload'
        if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
            return url;
        } else if (action == 'uploadvideo') {//视频上传：
            return url;
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    }

});

$('#okbt,#previewbt').on('click',function(e){
	var id = $(e.currentTarget).attr('id');
	var methodName = id=='previewbt' ? 'addPreview' : 'updateCourseDetail';
//	if($('#courseDetail_content').html().indexOf('<img')!=-1){
//		$('#courseDetail').val("img");
//	}else{
//		$('#courseDetail').val($('#courseDetail_content').text());
//	}
//		$('#courseDetail').val(ueEditccpe.getContent());
		$('#courseDetail').val(ueEditccpe_content.getContent());
//		$('#courseOutline').val(ueEditccpe_courseOutline_content.getContent());
		$('#commonProblem').val(ueEditccpe_commonProblem_content.getContent());

//	if($('#courseOutline_content').html().indexOf('<img')!=-1){
//		$('#courseOutline').val("img");
//	}else{
//		$('#courseOutline').val($('#courseOutline_content').text());
//	}

//	if($('#commonProblem_content').html().indexOf('<img')!=-1){
//		$('#commonProblem').val("img");
//	}else{
//		$('#commonProblem').val($('#commonProblem_content').text());
//	}

	var validate = id=='previewbt' ? true : $("#courseDetailForm").valid();

	if(validate){

		/*var courseDetail_content_val = $('#courseDetail_content').next().val();
		if(courseDetail_content_val){
			$('#courseDetail').val(courseDetail_content_val);
		} else {
			$('#courseDetail').val($('#courseDetail_content').html());
		}

		var courseOutline_content_val = $('#courseOutline_content').next().val();
		if (courseOutline_content_val) {
			$('#courseOutline').val(courseOutline_content_val);
		} else {
			$('#courseOutline').val($('#courseOutline_content').html());
		}

		var commonProblem_content_val = $('#commonProblem_content').next().val();
		if(commonProblem_content_val){
			$('#commonProblem').val(commonProblem_content_val);
		} else {
			$('#commonProblem').val($('#commonProblem_content').html());
		}*/

		mask();
		$("#courseDetailForm").attr("action", basePath+"/cloudclass/course/"+methodName);
		$("#courseDetailForm").ajaxSubmit(function(data){
			unmask();
			try{
				data = jQuery.parseJSON(jQuery(data).text());
			}catch(e) {
				data = data;
			}
			if(data.success){
				if(id=='previewbt'){
					window.open($('#weburl').val()+'/web/courseDetailPreview/'+$('#courseId').val(),'熊猫中医在线');
				} else {
					alertInfo("保存成功！");
				}
				$("html").eq(0).css("overflow","scroll");
			}else{
				layer.msg(data.errorMessage);
				$("html").eq(0).css("overflow","scroll");
			}
		});
	}

});

$('#cancelbt,#returnbutton,#returnbutton2').on('click',function(){
	window.location.href=basePath+'/home#cloudclass/course/index?page='+$('#page').val();
});

//图片上传统一上传到附件中心---- 修改  列表页
$("#courseDetailForm").on("change","#smallImgPath_file",function(){
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");

			$("#edit_smallImgPath").val(data.url);
			document.getElementById("smallImgPath_file").focus();
			document.getElementById("smallImgPath_file").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});

//图片上传统一上传到附件中心---- 修改 详情页
$("#courseDetailForm").on("change","#detailImgPath_file",function(){
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
		  	$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");

		  	$("#edit_detailImgPath").val(data.url);
		  	document.getElementById("detailImgPath_file").focus();
		  	document.getElementById("detailImgPath_file").blur();
		  	$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});

//图片上传统一上传到附件中心---- 修改  列表页
$("#courseDetailForm").on("change","#smallImgPath_file",function(){
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
			
			$("#edit_smallImgPath").val(data.url);
			document.getElementById("smallImgPath_file").focus();
			document.getElementById("smallImgPath_file").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});
//图片上传统一上传到附件中心---- 修改  列表页
$("#courseDetailForm").on("change","#smallImgPath_file1",function(){
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
			
			$("#edit_smallImgPath1").val(data.url);
			document.getElementById("smallImgPath_file1").focus();
			document.getElementById("smallImgPath_file1").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});
//图片上传统一上传到附件中心---- 修改  列表页
$("#courseDetailForm").on("change","#smallImgPath_file2",function(){
	var v = this.value.split(".")[this.value.split(".").length-1].toUpperCase();
	if(v!='BMP' && v!='GIF' && v!='JPEG' && v!='PNG' && v!='SVG' && v!='JPG'){
		layer.alert("图片格式错误,请重新选择.");
		this.value="";
		return;
	}
	var id = $(this).attr("id");
	ajaxFileUpload(this.id,basePath+"/attachmentCenter/upload?projectName=online&fileType=1", function(data){
		if (data.error == 0) {
			$("#"+id).parent().find(".ace-file-name").after("<img scr='' class='middle'/>");
			$("#"+id).parent().find(".ace-file-name img").attr("src",data.url);
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
			
			$("#edit_smallImgPath2").val(data.url);
			document.getElementById("smallImgPath_file2").focus();
			document.getElementById("smallImgPath_file2").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});

//图片上传统一上传到附件中心---- 修改 详情页
$("#courseDetailForm").on("change","#detailImgPath_file",function(){
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
			$("#"+id).parent().find(".ace-file-name img").attr("style","width: 250px; height: 140px;");
			
			$("#edit_detailImgPath").val(data.url);
			document.getElementById("detailImgPath_file").focus();
			document.getElementById("detailImgPath_file").blur();
			$(".remove").hide();
		}else {
			alert(data.message);
		}
	})
});

//刷新故事节点
function initGsjd(){
	$("#ce").hide();
	syncRequest(basePath+"/cloudClass/courseDescription/getDesList",{"courseId":$("#desCourseId").val()},function(data){
		if(data.success){//如果成功更新左侧的列表
			$("#titleUl").replaceWith("<ul class=\"list-group\" id=\"titleUl\" style=\"margin: 0;\"></ul>");//首先还原列表
			if(data.resultObject.length>0){
				$("#ce").show();
			}
			for(var i=0 ;i<data.resultObject.length;i++){
				$("#titleUl").append('<a href="void(0)" onclick="selTA(\''+data.resultObject[i].id+'\');return false;" id="desA'+data.resultObject[i].id+'" class="list-group-item">'+ (i+1) + "&nbsp;<span style='white-space:pre;' id=\"desS"+data.resultObject[i].id+"\">"+data.resultObject[i].courseTitle +
						'</span><span class="badge badge-info" style="display:none" id="desST'+data.resultObject[i].id+'"><i class="glyphicon glyphicon-edit" onclick="editDes(\''+data.resultObject[i].id+'\')"></i>&nbsp;&nbsp;<i class="glyphicon glyphicon-trash" onclick="delDes(\''+data.resultObject[i].id+'\')"></i></span></a>' +
						'<input type="hidden" id="desIdHid'+data.resultObject[i].id+'" name="desId" value="'+ data.resultObject[i].id+'">'+
						'<input type="hidden" id="desConHid'+data.resultObject[i].id+'" name="desCon" value=\'' + data.resultObject[i].courseContent + '\'>'+
						'<input type="hidden" id="desStatusHid'+data.resultObject[i].id+'" name="desStatus" value="' + data.resultObject[i].status + '">'+
						'<input type="hidden" id="desTitleHid'+data.resultObject[i].id+'" name="desTitle" value="' + data.resultObject[i].courseTitle + '">');
				if(i == 0){
					var desIdTemp = data.resultObject[i].id;
					var desConTemp = data.resultObject[i].courseContent;
					ueEditccpe.ready(function(){
						ueEditccpe.setContent(desConTemp);
						selTA(desIdTemp);
					});

					if(data.resultObject[i].status==1){
						$("#desStatusEdit").attr("checked",true);
					}else{
						$("#desStatusEdit").attr("checked",false);
					}
				}
			}
		}else{//如果失败给出提示
			layer.msg("故事标题加载失败！");
		}
		$("html").eq(0).css("overflow","scroll");
	});
	$("html").eq(0).css("overflow","scroll");
}

$("#tjgsbt").on("click",function(){//点击添加UL

	$("#courseTitle").val("");
	//先从后台插入一条数据
	//然后重新查询出所有的数据进行遍历
	var dialog = openDialog("desDialog","dialogDesDiv","添加故事标题",580,200,true,"确定",function(){

		if($("#titleUl input[name='desId']").length>=9){
			alert("最多只能添加九个故事标题！");
			return false;
		}

		if($("#addDes-form").valid()){

			//把上个保存了
			if(tempDesId != "" && tempDesId != null&& $("#desConHid"+tempDesId).val() != ueEditccpe.getContent()){//必须编辑过才保存
				$("#update_desId").val(tempDesId);
				$("#update_courseTitle").val($("#desTitleHid"+tempDesId).val());
				$("#update_courseContent").val(ueEditccpe.getContent());

				if($("#desStatusEdit").is(':checked')){//如果选中赋值1 否则0
					$("#update_status").val(1);
				}else{
					$("#update_status").val(0);
				}
				$("#updateDes-form").attr("action", basePath+"/cloudClass/courseDescription/updateCourseDescriptionById");
		        $("#updateDes-form").ajaxSubmit(function(data){
		        	unmask();
		        	if(data.success){
		        		$("#desConHid"+tempDesId).val($("#update_courseContent").val());
		        		//layer.msg(data.errorMessage);
		        		if($("#desStatusEdit").is(':checked')){//如果选中赋值1 否则0
		        			$("#desStatusHid"+tempDesId).val(1);
		        		}else{
		        			$("#desStatusHid"+tempDesId).val(0);
		        		}
		        	}else{
		        		layer.msg(data.errorMessage);
		        	}
		        });
			}else{
			}

	        mask();
	        $("#addDes-form").attr("action", basePath+"/cloudClass/courseDescription/addCourseDescription");
	        $("#addDes-form").ajaxSubmit(function(data){
	        	tempDesId = "";
	            unmask();
	            if(data.success){
	                $("#desDialog").dialog("close");
	                layer.msg(data.errorMessage);
	                initGsjd();
	                selTA($("input[name='desId']").eq($("input[name='desId']").length-1).val());
	            }else{
	            	layer.msg(data.errorMessage);
	            }
	        });
	    }
	});
});

//删除一个故事
function delDes(desId){
	showDelDialog(function(){
		syncRequest( basePath+"/cloudClass/courseDescription/deletes",{'ids':desId},function(data){
			if(!data.success){
				//alertInfo(data.errorMessage);
				layer.msg(data.errorMessage);
			}else{
				tempDesId = "";
				layer.msg(data.errorMessage);
				$('#desSaveBtn').unbind("click");//先移除所有的绑定事件
				$('#testSaveBtn').unbind("click");//先移除所有的绑定事件
				ueEditccpe.setContent("");//清空文本
				initGsjd();
			}
		});
	},"","","");
}

function editDes(desId){//修改

	if(desId==""){
		alert("请选择一条数据！");
		return false;
	}

	$("#update_desId").val(desId);
	$("#update_courseTitle").val($("#desTitleHid"+desId).val());
	$("#update_courseContent").val($("#desConHid"+desId).val());
	$("#update_status").val($("#desStatusHid"+desId).val());

	var dialog = openDialog("desDialogUpdate","dialogDesDivUpdate","修改故事标题",580,200,true,"确定",function(){
		if($("#updateDes-form").valid()){
	        mask();

	        $("#updateDes-form").attr("action", basePath+"/cloudClass/courseDescription/updateCourseDescriptionById");
	        $("#updateDes-form").ajaxSubmit(function(data){
	            unmask();
	            if(data.success){
	                $("#desDialogUpdate").dialog("close");
	                layer.msg(data.errorMessage);
	                $("#desS"+desId).text($("#update_courseTitle").val());
	                $("#desTitleHid"+desId).val($("#update_courseTitle").val());
	            }else{
	            	layer.msg(data.errorMessage);
	            }
	        });
	    }
	});
}
var di="";
//点击当前A标签的动作
function selTA(desId){
	if(di==desId)return;
	di=desId;
	debugger;
	//把上个保存了
	if(tempDesId != "" && tempDesId != null&& $("#desConHid"+tempDesId).val() != ueEditccpe.getContent()){//必须编辑过才保存
		$("#update_desId").val(tempDesId);
		$("#update_courseTitle").val($("#desTitleHid"+tempDesId).val());
		$("#update_courseContent").val(ueEditccpe.getContent());

		if($("#desStatusEdit").is(':checked')){//如果选中赋值1 否则0
			$("#update_status").val(1);
		}else{
			$("#update_status").val(0);
		}
		$("#updateDes-form").attr("action", basePath+"/cloudClass/courseDescription/updateCourseDescriptionById");
        $("#updateDes-form").ajaxSubmit(function(data){
        	unmask();
        	if(data.success){
        		$("#desConHid"+tempDesId).val($("#update_courseContent").val());
        		//layer.msg(data.errorMessage);
        		if($("#desStatusEdit").is(':checked')){//如果选中赋值1 否则0
        			$("#desStatusHid"+tempDesId).val(1);
        		}else{
        			$("#desStatusHid"+tempDesId).val(0);
        		}
        	}else{
        		layer.msg(data.errorMessage);
        	}
        	tempDesId = desId;
        });
	}else{
		tempDesId = desId;
	}

	$(".badge").hide();
	$("#desST"+desId).show();


	// editor准备好之后才可以使用
	ueEditccpe.setContent($("#desConHid"+desId).val());

	if($("#desStatusHid"+desId).val()==1){
    	$("#desStatusEdit").prop("checked",true);
    }else{
    	$("#desStatusEdit").prop("checked",false);
    }

	$('#desSaveBtn').unbind("click");//先移除所有的绑定事件
	$("#desSaveBtn").on('click',function(){
		$("#update_desId").val(desId);
		$("#update_courseTitle").val($("#desTitleHid"+desId).val());
		$("#update_courseContent").val(ueEditccpe.getContent());

		if($("#desStatusEdit").is(':checked')){//如果选中赋值1 否则0
			$("#update_status").val(1);
		}else{
			$("#update_status").val(0);
		}

		$("#updateDes-form").attr("action", basePath+"/cloudClass/courseDescription/updateCourseDescriptionById");
        $("#updateDes-form").ajaxSubmit(function(data){
            unmask();
            if(data.success){
            	$("#desConHid"+desId).val(ueEditccpe.getContent());
                layer.msg(data.errorMessage);
                if($("#desStatusEdit").is(':checked')){//如果选中赋值1 否则0
    				$("#desStatusHid"+desId).val(1);
    			}else{
    				$("#desStatusHid"+desId).val(0);
    			}
            }else{
            	layer.msg(data.errorMessage);
            }
        });
	});

	$('#testSaveBtn').unbind("click");//先移除所有的绑定事件
	$("#testSaveBtn").on('click',function(){
		$("#update_desId").val(desId);
		$("#update_courseTitle").val($("#desTitleHid"+desId).val());
		$("#update_courseContentPreview").val(ueEditccpe.getContent());

		if($("#desStatusEdit").is(':checked')){//如果选中赋值1 否则0
			$("#update_status").val(1);
		}else{
			$("#update_status").val(0);
		}

		$("#updateDes-form").attr("action", basePath+"/cloudClass/courseDescription/testCourseDescriptionById");
	    $("#updateDes-form").ajaxSubmit(function(data){
	        unmask();
	        if(data.success){
	        	alert("成功保存预览");
	        	var newTab=window.open('about:blank');
	       		 newTab.location.href = weburl+"/web/html/courseIntroductionPagePreview.html?desId="+desId+"&courseId="+$("#courseId").val();
	        	//这里改跳像另一边接口
	        	//$("#desConHid"+desId).val(ueEditccpe.getContent());
	            //layer.msg(data.errorMessage);

	        }else{
	        	//layer.msg(data.errorMessage);
	        }
	    });
	});
}

/**
 * 上移
 * @param obj
 */
function upMoveRec(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = _courseRecTable.fnGetData(oo);
	ajaxRequest(basePath+'/cloudClass/courseRecommend/upMoveRec',{"id":aData.id},function(res){
		if(res.success){
			freshTable(_courseRecTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

/**
 * 下移
 * @param obj
 */
function downMoveRec(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = _courseRecTable.fnGetData(oo);
	ajaxRequest(basePath+'/cloudClass/courseRecommend/downMoveRec',{"id":aData.id},function(res){
		if(res.success){
			freshTable(_courseRecTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};

//批量删除
function deletes(id){
	ajaxRequest(basePath+"/cloudClass/courseRecommend/deletes",{'ids':id},function(data){
		if(!data.success){//如果失败
			//alertInfo(data.errorMessage);
			layer.msg(data.errorMessage);
		}else{
			if(!isnull(_courseRecTable)){
				freshDelTable(_courseRecTable);
			}
			layer.msg(data.errorMessage);
		}
	});
}


function addRecCourse(){//修改


	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
	var objData = [{ "title": checkbox,"class":"center","width":"10%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
    }},
    { "title": "学科名称", "class":"center","sortable":false,"data": 'xMenuName' },
    { "title": "课程类别", "class":"center","sortable":false,"data": 'scoreTypeName' },
    { "title": "授课方式", "class":"center","sortable":false,"data": 'teachMethodName' },
    { "title": "课程名称", "class":"center","sortable":false,"data": 'courseName' },
    { "title": "现价格", "class":"center","width":"10%","sortable":false,"data": 'currentPrice'}];

	var searchCaseCourseTable = new Array();
	$("#searchDivKclb .searchTr").each(function() {
		if (!isnull($(this).find('.propertyValue1').val())) {
			var propertyValue2 = $(this).find('.propertyValue2').val();
			if(!isnull(propertyValue2)){
				searchCaseCourseTable.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
						+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()
						+',"propertyValue2":"'+propertyValue2+'"}');
			}else{
				searchCaseCourseTable.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
						+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()+'}');
			}
		}
	});

	_courseTable = initTables("courseTable",basePath+"/cloudclass/course/list",objData,true,true,0,null,searchCaseCourseTable,function(data){

			$("#courseDiv .dataTables_length").hide();

	});

	$("#yxzkcRec").replaceWith('<ul class="list-group" id="yxzkcRec" style="margin: 0px;"></ul>');
	if($("#courseRecTable .dataTables_empty").length==0){

		//初始化已选择的值
		var ids = new Array();
		var names = new Array();
		$("#courseRecTable tbody tr").each(function(){
			ids.push($(this).find("input[name='realRecCourseId']").val());
			names.push($(this).find("td").eq(1).text());
		})
		for(var i=0 ;i<ids.length;i++){
			$("#yxzkcRec").append('<a href="javascript:void(0);" onclick="javascript:void(0);return false;" id="recCourse'+ids[i]+'" style="height:40px;line-height:40px;border:0px;" class="list-group-item">'
							 + "<span id=\"recCourseS"+ids[i]+"\" style=\"margin-top: -10px;width:170px;float:left;word-break:keep-all;white-space:nowrap; overflow:hidden; text-overflow:ellipsis;\" title=\""+names[i]+"\">"+names[i] +
					'</span><span class="badge badge-info" id="recCourseST'+ids[i]+'"><i class="glyphicon glyphicon-trash" onclick="delRecCourse(\''+ids[i]+'\')"></i></span></a>'+
					'<input type="hidden" id="recCourseHid'+ids[i]+'" name="recCourseHid" value="'+ids[i]+'">');
		}
	}
	
     $("#search_menu").val("");
  	 $("#search_scoreType").val("");
  	 $("#search_courseType").val("");
  	search();
	var dialog = openDialog("courseDialog","dialogCourseDiv","新增推荐课程",1100,600,true,"确定",function(){
		if($("#yxzkcRec input[name='recCourseHid']").length > 0){
	        mask();
	        $("#addRecCourse-form").attr("action", basePath+"/cloudClass/courseRecommend/addCourseRecommend");
	        $("#addRecCourse-form").ajaxSubmit(function(data){
	            unmask();
	            if(data.success){
	                $("#courseDialog").dialog("close");
	                layer.msg(data.errorMessage);
	                freshTable(_courseRecTable);
	            }else{
	            	layer.msg(data.errorMessage);
	            }
	        });
		}else{
			$("#courseDialog").dialog("close");
			return ;
		}
	});

}

function search(){
//	searchButton(_courseTable);
	var searchs = new Array();
	$("#searchDivKclb .searchTr").each(function() {
		if (!isnull($(this).find('.propertyValue1').val())) {
			var propertyValue2 = $(this).find('.propertyValue2').val();
			if(!isnull(propertyValue2)){
				searchs.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
						+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()
						+',"propertyValue2":"'+propertyValue2+'"}');
			}else{
				searchs.push('{"tempMatchType":'+$(this).find('.tempMatchType').val()+',"propertyName":'+$(this).find('.propertyName').val()
						+',"propertyValue1":"'+$(this).find('.propertyValue1').val()+'","tempType":'+$(this).find('.tempType').val()+'}');
			}
		}
	});

//	searchs = encodeURI(searchs);
	var str = "[" + searchs.join(",") + "]";

	_courseTable.fnFilter(str);

};

//$('#search_menu').change(function(){
//
//var firstMenuNumber=$(this).children('option:selected').val();//这就是selected的值
// $.ajax({
//	          type:'post',
//	          url:basePath+'/cloudclass/course/getSecoundMenu',
//	          data:{firstMenuNumber:""},
////	          data:{firstMenuNumber:firstMenuNumber},
//	          dataType:'json',
//	          success:function(data){
//
//                        var optionstring = "";
//                        for (var j = 0; j < data.length; j++) {
//                            optionstring += "<option value=\"" + data[j].number + "\" >" + data[j].name + "</option>";
//                        }
//                        $("#search_scoreType").html("<option value=''>请选择</option> "+optionstring);
//	          }
//	      }) ;
//}) ;

$("#ydRecCourse").on("click",function(){
	if($("#yxzkcRec input[name='recCourseHid']").length>5){
		alert("最多只能推荐5门课程!");
		return false;
	}
	var ids = new Array();
	var names = new Array();
	var testLength = new Array();
	//获取到所有选中的节点
	$("#courseDiv .dataTable tbody input[type='checkbox']:checked").each(function(){
		ids.push($(this).val());
		names.push($(this).parent().parent().find("td").eq(4).text());
	});

	$("#yxzkcRec input[name='recCourseHid']").each(function(){
		for(var i = 0;i<ids.length;i++){
			if(ids[i]==$(this).val()){
				delete ids[i];
				delete names[i];
			}
		}
	});

	if($("#yxzkcRec input[name='recCourseHid']").length == 0)
	{
		if(ids.length>5){
			alert("最多只能推荐5门课程!");
			return false;
		}
	}else{
		for(var i=0;i<ids.length;i++){
			if(ids[i]!=undefined){
				testLength.push(ids[i]);
			}
		}

		if(($("#yxzkcRec input[name='recCourseHid']").length+testLength.length)>5){
			alert("最多只能推荐5门课程!");
			return false;
		}
	}

	for(var i=0 ;i<ids.length;i++){
		if(ids[i]==undefined){
			continue;
		}
		$("#yxzkcRec").append('<a href="javascript:void(0);" onclick="javascript:void(0);return false;" id="recCourse'+ids[i]+'" style="height:40px;line-height:40px;border:0px;" class="list-group-item">'
						 + "<span id=\"recCourseS"+ids[i]+"\" style=\"margin-top: -10px;width:170px;display: block;float:left;word-break:keep-all;white-space:nowrap; overflow:hidden; text-overflow:ellipsis;\" title=\""+names[i]+"\">"+names[i] +
				'</span><span class="badge badge-info" id="recCourseST'+ids[i]+'"><i class="glyphicon glyphicon-trash" onclick="delRecCourse(\''+ids[i]+'\')"></i></span></a>'+
				'<input type="hidden" id="recCourseHid'+ids[i]+'" name="recCourseHid" value="'+ids[i]+'">');
	}

});

//删除一个推荐课程
function delRecCourse(courseId){
	$("#recCourseHid"+courseId).remove();
	$("#recCourse"+courseId).remove();
}

$("#descriptionShow").on("click",function(){//点击添加UL
	var  descriptionShow = 0;
	if($("#descriptionShow").is(':checked')){//如果选中赋值1 否则0
		descriptionShow = 1;
	}else{
		descriptionShow = 0;
	}
	$.ajax({
         type:'post',
         url:basePath+'/cloudclass/course/descriptionShow',
         data:{descriptionShow:descriptionShow,id:$("#courseId").val()},
         dataType:'json',
         success:function(data){
        	 if(data.success){//如果成功了无所谓

        	 }else{//失败了就还原
        		 if($("#descriptionShow").is(':checked')){//如果选中赋值1 否则0
        			 $("#descriptionShow").prop("checked",false);
    			 }else{
    				 $("#descriptionShow").prop("checked",true);
    			 }
        	 }
         }
     }) ;
});

function getSecoundMenu(){

	var firstMenuNumber=$(this).children('option:selected').val();//这就是selected的值
	 $.ajax({
		          type:'post',
		          url:basePath+'/cloudclass/course/getSecoundMenu',
		          data:{firstMenuNumber:""},
//		          data:{firstMenuNumber:firstMenuNumber},
		          dataType:'json',
		          success:function(data){

	                        var optionstring = "";
	                        for (var j = 0; j < data.length; j++) {
	                            optionstring += "<option value=\"" + data[j].number + "\" >" + data[j].name + "</option>";
	                        }
	                        $("#search_scoreType").html("<option value=''>请选择</option> "+optionstring);
		          }
		      }) ;
}