var searchCase = new Array();
var searchCasePPT = new Array();
var searchCaseCase = new Array();
var searchCaseQues = new Array();
var treeObj = null;
var videoTable;
var pptTable;
var caseTable;
var quesTable;
var validateForm;
var validateUpdateForm;
var validatePPTForm;
var validateUpdatePPTForm;
var validateCaseForm; 
var validateUpdateCaseForm;
var OptionIndex = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K'];
$(function(){

	var objData = [
	            { "title": createAllCheckBox2(),"class":"center","sortable":false,"data": 'id',"width":"5%","mRender":function(data,display,row){
	            	if(row.isUse){
	            		return '<label class="position-relative"><input type="checkbox" disabled="disabled" value='+data+' class="ace" /><span class="lbl"></span></label>';
	            	}else{ 
	            		return createCheckBox(data);
	            	}
                }},
	           /* { "title": "序号", "class":"center","width":"5%","sortable":false,"data": 'options' },*/
	            { "title": "视频名称", "class":"center","width":"8%","sortable":false,"data": 'name' },
	            { "title": "视频ID", "class":"center","width":"8%","sortable":false,"data": 'videoId' },
	            //{ "title": "视频时长", "class":"center","sortable":false,"data": 'videoTime' },
	            { "title": "版本", "class":"center","width":"5%","sortable":false,"data":'videoVersion'},
		        { "title": "视频大小", "class":"center","width":"6%","sortable":false,"data": 'videoSize' },
		        { "title": "状态", "class":"center","width":"5%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
					return row.status=="1"?"已启用":"已禁用";
					}
				},
				 { "title": "是否试学", "class":"center","width":"6%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
						return row.isTryLearn=="1"?"试学":"非试学";
						}
					},
		        { "title": "创建人", "class":"center","width":"7%","sortable":false,"data": 'createPerson'},
		        { "title": "创建时间", "class":"center","width":"11%","sortable":false,"data": 'createTime' },
		        {"sortable": false,"class": "center","width":"9%","title":"排序","mRender":function (data, display, row) {
		        	return '<div class="hidden-sm hidden-xs action-buttons">'+
		    		'<a class="blue" href="javascript:void(-1);" title="上移" onclick="upMove(this)"><i class="glyphicon glyphicon-arrow-up bigger-130"></i></a>'+
		        	'<a class="blue" href="javascript:void(-1);" title="下移" onclick="downMove(this)"><i class="glyphicon glyphicon-arrow-down bigger-130"></i></a></div>';
		    		}},
		        { "sortable": false,"data":"id","class": "center","width":"7%","title":"操作","mRender":function (data, display, row) {
				   
		   	     	var opt = '<div class="hidden-sm hidden-xs action-buttons">';
				   	 	if(row.status==1) {
				   	 	    opt+='<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> ';
						}else{
							opt+='<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> ';
						};
						opt = opt + '<a class="blue" href="javascript:void(-1);" title="修改" onclick="editDialog(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
						opt = opt + '<a class="red" href="javascript:void(-1);" title="删除" onclick="delOne(\''+row.id+'\')"><i class="ace-icon fa fa-trash-o bigger-130"></i></a>';
					    opt = opt + '</div>';
					return opt;
		        	
				}}                                                                    
		    ];
	searchCase.push('{"tempMatchType":"7","propertyName":"courseId","propertyValue1":"' + $("#courseId").val() + '","tempType":"String"}');
	videoTable = initTables("videoTable",basePath+"/cloudclass/videores/findVideoList",objData,true,true,0,null,searchCase,function(data){
		var iDisplayStart = data._iDisplayStart;
		var countNum = data._iRecordsTotal;//总条数
		pageSize = data._iDisplayLength;//每页显示条数
		currentPage = iDisplayStart / pageSize +1;//页码
		if(currentPage == 1){//第一页的第一行隐藏向上箭头
			$("#videoTable tbody tr:first td").eq(9).find('a').eq(0).css("pointer-events","none").removeClass("blue").addClass("gray");
		}
		if(countNum/pageSize < 1 || countNum/pageSize == 1){//数据不足一页隐藏下移箭头
			$("#videoTable tbody tr:last td").eq(9).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
		}
		var countPage;
		if(countNum%pageSize == 0){
			countPage = parseInt(countNum/pageSize);
		}else{
			countPage = parseInt(countNum/pageSize) + 1;
		}
		if(countPage == currentPage){//隐藏最后一条数据下移
//			$("#cloudClassTable tbody tr:last td:last a:eq(5)").hide();
			$("#videoTable tbody tr:last td").eq(9).find('a').eq(1).css("pointer-events","none").removeClass("blue").addClass("gray");
		}
		if(!$(".add_bx").is(":visible")){//隐藏课程、章、节下的排序功能
			$("#videoTable tr").each(function(){
	    		 $(this).find("th").eq(9).hide();
	    		 $(this).find("td").eq(9).hide();
	    	});
			$(".dataTables_empty").attr("colspan", 10);
		}else{
			$("#videoTable tr").each(function(){
	    		 $(this).find("th").eq(9).show();
	    		 $(this).find("td").eq(9).show();
	    	});
		}
		$("[name='videoTable_length']").change(function(){//处理滚动条
			$("html").eq(0).css("overflow","scroll");
		});
		
		if((!$("#currentNodeLevel").val() || $("#currentNodeLevel").val()==1)&&$(".videoli").hasClass("active")){
			$(".async_video_button").show();
			$(".async_category_button").show();
		}else{
			$(".async_video_button").hide();
			$(".async_category_button").hide();
		}
		
	});
	//------------------------------------------------------------------------PPT------------------------------------------------------------------
	var objDataPPT = [
			{"title" : createAllCheckBox2(),"class" : "center","sortable" : false,"data" : 'id',"width" : "5%","mRender" : function(data, display, row) {
					if (row.isUse) {
						return '<label class="position-relative"><input type="checkbox" disabled="disabled" value='+ data+ ' class="ace" /><span class="lbl"></span></label>';
					} else {
						return createCheckBox(data);
					}
				}
			},
			{ "title": "序号", "class":"center","width":"5%","sortable":false,"data": 'options' },
			{ "title": "文件路径", "class": "center","sortable": false,"data":"fileUrl","visible":false },
			{ "title": "后缀", "class": "center","sortable": false,"data":"suffix","visible":false },
			{ "title": "类型", "class": "center","sortable": false,"data":"type","visible":false },
			{ "title": "名称", "class":"center","width":"15%","sortable":false,"data": 'name' },
			{ "title": "版本", "class":"center","width":"8%","sortable":false,"data": 'version' },
			{ "title": "状态", "class":"center","width":"8%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
				return row.status=="1"?"已启用":"已禁用";
				}
			},
			{ "title": "创建人", "class":"center","width":"7%","sortable":false,"data": 'createPerson'},
			{ "title": "创建日期", "class":"center","width":"11%","sortable":false,"data": 'createTime',"mRender":function (data, display, row){
	        	var d = new Date(data);
	        	var createDate = FormatDate(d);
	        	
	        	return createDate != "" ? createDate: "";
	        }  },
			{ "sortable": false,"data":"id","class": "center","width":"7%","title":"操作","mRender":function (data, display, row) {
				   
	   	     	var opt = '<div class="hidden-sm hidden-xs action-buttons">';
	   	     		if(row.fileUrl){
	   	     			row.fileUrl = row.fileUrl.replace('11310','11311');
	   	     		}
	   	     		opt = opt + '<a class="blue"  target="_blank" href="'+row.fileUrl+'" title="预览"><i class="ace-icon fa fa-search bigger-130"></i></a>';
			   	 	if(row.status==1) {
			   	 	    opt+='<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updatePPTStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> ';
					}else{
						opt+='<a class="blue" href="javascript:void(-1);" title="启用" onclick="updatePPTStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> ';
					};
					opt = opt + '<a class="blue" href="javascript:void(-1);" title="修改" onclick="editPPTDialog(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
					opt = opt + '<a class="red" href="javascript:void(-1);" title="删除" onclick="delOnePPT(\''+row.id+'\')"><i class="ace-icon fa fa-trash-o bigger-130"></i></a>';
				    opt = opt + '</div>';
				return opt;
	        	
			}}     
			];//cloudclass/ppt
	
	
	searchCasePPT.push('{"tempMatchType":"7","propertyName":"courseId","propertyValue1":"' + $("#courseId").val() + '","tempType":"String"}');
	pptTable = initTables("pptTable",basePath+"/cloudclass/ppt/findpptList",objDataPPT,true,true,2,null,searchCasePPT,function(data){
		
	});
	
	
	
	//--------------------------------------------------------------------PPT结束----------------------------------------------------------------------------
	validateForm = $("#addVideo-form").validate({
		messages:{
			
			name:{
                required:"视频名称不能为空"
            }
			
		}
	});
	
	validateUpdateForm = $("#editVideo-form").validate({
		messages:{
			
			name:{
                required:"视频名称不能为空"
            }
			
		}
	});
	
	validatePPTForm = $("#case-form").validate({
		messages:{
			
			name:{
                required:"教案名称不能为空"
            }
			
		}
	});
	validateUpdatePPTForm = $("#pptEdit-form").validate({
		messages:{
			
			name:{
                required:"PPT名称不能为空"
            }
			
		}
	});
	
	
	validateCaseForm = $("#caseEdit-form").validate({
		messages:{
			
			name:{
                required:"教案名称不能为空"
            }
			
		}
	});
	validateUpdateCaseForm = $("#ppt-form").validate({
		messages:{
			
			name:{
                required:"PPT名称不能为空"
            }
			
		}
	});
	//------------------------------------------------------------------------教案------------------------------------------------------------------
	
	var objDataCase = [
	      			{"title" : createAllCheckBox2(),"class" : "center","sortable" : false,"data" : 'id',"width" : "5%","mRender" : function(data, display, row) {
	      					if (row.isUse) {
	      						return '<label class="position-relative"><input type="checkbox" disabled="disabled" value='+ data+ ' class="ace" /><span class="lbl"></span></label>';
	      					} else {
	      						return createCheckBox(data);
	      					}
	      				}
	      			},
	      			{ "title": "序号", "class":"center","width":"5%","sortable":false,"data": 'options' },
	      			{ "title": "文件路径", "class": "center","sortable": false,"data":"fileUrl","visible":false },
	      			{ "title": "类型", "class": "center","sortable": false,"data":"type","visible":false },
	      			{ "title": "名称", "class":"center","width":"15%","sortable":false,"data": 'name' },
	      			{ "title": "版本", "class":"center","width":"8%","sortable":false,"data": 'version' },
	      			{ "title": "后缀", "class": "center","width":"5%","sortable": false,"data":"suffix" },
	      			{ "title": "状态", "class":"center","width":"8%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
	      				return row.status=="1"?"已启用":"已禁用";
	      				}
	      			},
	      			{ "title": "创建人", "class":"center","width":"7%","sortable":false,"data": 'createPerson'},
	      			{ "title": "创建日期", "class":"center","width":"11%","sortable":false,"data": 'createTime',"mRender":function (data, display, row){
	      	        	var d = new Date(data);
	      	        	var createDate = FormatDate(d);
	      	        	
	      	        	return createDate != "" ? createDate: "";
	      	        }  },
	      			{ "sortable": false,"data":"id","class": "center","width":"7%","title":"操作","mRender":function (data, display, row) {
	      				   
	      	   	     	var opt = '<div class="hidden-sm hidden-xs action-buttons">';
			      	   	   if(row.fileUrl){
			      	   		   row.fileUrl = row.fileUrl.replace('11310','11311');
			   	     		}
	      	   	     		opt = opt + '<a class="blue"  target="_blank" href="'+row.fileUrl+'" title="预览"><i class="ace-icon fa fa-search bigger-130"></i></a>';
	      			   	 	if(row.status==1) {
	      			   	 	    opt+='<a class="blue" href="javascript:void(-1);" title="禁用" onclick="updateCaseStatus(this);"><i class="ace-icon fa fa-ban bigger-130"></i></a> ';
	      					}else{
	      						opt+='<a class="blue" href="javascript:void(-1);" title="启用" onclick="updateCaseStatus(this);"><i class="ace-icon fa fa-check-square-o bigger-130"></i></a> ';
	      					};
	      					opt = opt + '<a class="blue" href="javascript:void(-1);" title="修改" onclick="editCaseDialog(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
	      					opt = opt + '<a class="red" href="javascript:void(-1);" title="删除" onclick="delOneCase(\''+row.id+'\')"><i class="ace-icon fa fa-trash-o bigger-130"></i></a>';
	      				    opt = opt + '</div>';
	      				return opt;
	      	        	
	      			}}     
	      			];
	
	searchCaseCase.push('{"tempMatchType":"7","propertyName":"courseId","propertyValue1":"' + $("#courseId").val() + '","tempType":"String"}');
	caseTable = initTables("caseTable",basePath+"/cloudclass/ppt/findcaseList",objDataCase,true,true,2,null,searchCaseCase,function(data){
		
	});
	
	//-----------------------------------------------------------------------教案结束----------------------------------------------------------------------------
	
	//------------------------------------------------------------------------问题开始---------------------------------------------------------------------------
	var objDataQues = [
		      			{"title" : createAllCheckBox2(),"class" : "center","sortable" : false,"data" : 'id',"width" : "5%","mRender" : function(data, display, row) {
		      					if (row.isUse) {
		      						return '<label class="position-relative"><input type="checkbox" disabled="disabled" value='+ data+ ' class="ace" /><span class="lbl"></span></label>';
		      					} else {
		      						return createCheckBox(data);
		      					}
		      				}
		      			},
		      			{ "title": "序号", "class":"center","width":"5%","sortable":false,"data": 'options' },
		      			{ "title": "题型", "class": "center","sortable": false,"data":"questionType", "mRender":function (data, display, row) {
			            	if("0" == data){return "单选题";}
			            	if("1" == data){return "多选题";}
			            	if("2" == data){return "判断题";}
			            	if("3" == data){return "填空题";}
			            	if("4" == data){return "简答题";}
			            	if("5" == data){return "代码题";}
			            	if("6" == data){return "实操题";}
			            } },
		      			{ "title": "题干", "class": "center","sortable": false,"data":"questionHeadText" },
		      			{ "title": "知识点数量", "class":"center","width":"15%","sortable":false,"data": 'chapterCount' },
		      			{ "title": "知识点名称", "class":"center","width":"8%","sortable":false,"data": 'chapterName' },
		      			{ "title": "状态", "class":"center","width":"8%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
		      				return row.status=="1"?"已启用":"已禁用";
		      				}
		      			},
		      			{ "title": "难度", "class": "center","width":"5%","sortable": false,"data":"difficulty", "mRender":function (data, display, row) {
			            	if("A" == data){return "简单";}
			            	if("B" == data){return "一般";}
			            	if("C" == data){return "困难";}
			            	if("D" == data){return "非常困难";}
			            	if("E" == data){return "变态困难";}
			            	if("F" == data){return "异常变态困难";}
			            	if("G" == data){return "地狱式困难";}
			            }},
		      			
		      			{ "sortable": false,"data":"id","class": "center","width":"7%","title":"操作","mRender":function (data, display, row) {
		      				   
		      	   	     	var opt = '<div class="hidden-sm hidden-xs action-buttons">';
				      	   	   if(row.fileUrl){
				      	   		   row.fileUrl = row.fileUrl.replace('11310','11311');
				   	     		}
		      	   	     		opt = opt + '<a class="blue" href="javascript:void(-1);" title="查看" onclick="previewDialog(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>';
		      					opt = opt + '<a class="blue" href="javascript:void(-1);" title="编辑" onclick="toEditQues(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a>';
		      					opt = opt + '<a class="red" href="javascript:void(-1);" title="删除" onclick="delOneQues(this)"><i class="ace-icon fa fa-trash-o bigger-130"></i></a>';
		      				    opt = opt + '</div>';
		      				return opt;
		      	        	
		      			}}     
		      			];
		
		searchCaseQues.push('{"tempMatchType":"7","propertyName":"courseId","propertyValue1":"' + $("#courseId").val() + '","tempType":"String"}');
		quesTable = initTables("quesTable",basePath+"/question/findQuesList",objDataQues,true,true,2,null,searchCaseQues,function(data){
			
		});
	
	//-----------------------------------------------------------------------问题结束----------------------------------------------------------------------------
	
	//初始化Ztree
	initZtree();	
	 
	/*  删除*/
	$(".dele_bx").click(function(){
		  deleteAll(basePath+'/cloudclass/videores/deletes',videoTable,null,'确定批量删除选中项?',null);
		  initZtree();
	});
	
	/*  启用*/
	$("#video_enable").click(function(){
		  deleteAll(basePath+'/cloudclass/videores/enbaleStatus',videoTable,null,'确定批量启用选中项？',null);
		
	});
	/*  禁用*/
	$("#video_disable").click(function(){
		  deleteAll(basePath+'/cloudclass/videores/disableStatus',videoTable,null,'确定批量禁用选中项？',null);
		
	});
	
	/*  启用*/
	$("#ques_enable").click(function(){
		  deleteAll(basePath+'/question/enbaleStatus',quesTable,null,'确定批量启用选中项？',null);
		
	});
	/*  禁用*/
	$("#ques_disable").click(function(){
		  deleteAll(basePath+'/question/disableStatus',quesTable,null,'确定批量禁用选中项？',null);
		
	});
	
	/*  删除PPT*/
	$(".dele_bx_ppt").click(function(){
		  deleteAll(basePath+'/cloudclass/ppt/deletes',pptTable);
	});
	
	/*  删除Case*/
	$(".dele_bx_case").click(function(){
		  deleteAll(basePath+'/cloudclass/ppt/deletes',caseTable);
	});
	/*  删除ques*/
	$(".dele_bx_ques").click(function(){
		var url ="question/deletes";
		var ids = new Array();
		$("#quesTable tbody input[type='checkbox']:checked").each(function(){
			ids.push($(this).val());
		});
		if(ids.length>0){
			showDelDialog(function(){
				ajaxRequest(url,{'ids':ids.join(","),"chapterIds":$("#search_chapterIds").val()},function(data){
					if(!data.success){
						//alertInfo(data.errorMessage);
						layer.msg(data.errorMessage);
					}else{
						freshDelTable(quesTable);
						if(typeof(data.errorMessage) != "undefined"){
							//alertInfo(data.errorMessage);
							layer.msg(data.errorMessage);
						}
					}
				});
			},'信息','确定要删除该条数据吗?',"确认");
		}else{
			showDelDialog("",title,"请选择删除对象！",btnName);
		}
		//  deleteAll(basePath+'/question/deletes',quesTable);
	});

	//新增框
	$(".add_video_button").click(function(){
		validateForm.resetForm();
		var dialog = openDialog("addVideoDialog","dialogAddVideoDiv","新增视频",580,500,true,"确定",function(){
			if($("#addVideo-form").valid()){
				var videoname = $('#videofile').val();
				if(videoname){
					
					var superCategory = $('#category').val();
					var subCategory = $('#sub-category').val();
					if(subCategory == '795EA34530C8D570' || superCategory=='FD2F60709F8EC562'){
						alert('请选择CC视频分类');
						return;
					}
					
					var nodes = treeObj.getSelectedNodes();
					if(nodes && nodes.length>0){
						var jie = nodes[0].getParentNode();
						if(jie){
							var zhang = jie.getParentNode();
							if (zhang){
								var categoryName = $('#sub-category').find("option:selected").text();
								if(categoryName != zhang.actName){
									alert('CC视频的二级分类与所选章的名称不一致！');
									return;
								}
							}
						}
					}
					addvideoinfo();
				} else {
					submitAddForm();
				}
				$("html").eq(0).css("overflow","scroll");
			}
		});
		//设置CC视频分类，二级联动
		setVideoCategory();
	});
	
	//新增框PPT
	$(".add_ppt_button").click(function(){
		validatePPTForm.resetForm();
		$('#ppt-form')[0].reset(); 
		openDialog("userDialog","dialogUserDiv","新增PPT",525,500,true,"确定",function(){
			if($("#ppt-form").valid()){
				var ret = checkVersion();
				if(!ret){
					return;
				}
				mask();
				var filePath = $("#pptFile").val();
				if (typeof(filePath) != "undefined") { 
					if(!fileUpload()){
						unmask();
						return;
					}
					
					ajaxFileUpload('pptFile', basePath+"/attachmentCenter/upload?projectName=online&fileType=2", function(data){
						console.log(data);
						var att = data;
						 $("#path").val(att.url);
						 var param = $("#ppt-form").serialize();
					 		ajaxRequest(basePath+"/cloudclass/ppt/add",param,function(data){
			    				unmask();
			    				if(data.success){
			    					$("#userDialog").dialog("close");
			    					 $("#path").val("");
			    					 freshTable(pptTable);
			    				}
			    				layer.msg(data.errorMessage);
			    				//$("#userDialog").dialog("close");
			    				//alertInfo(data.errorMessage);
			    			});	 
						
					});
				}else{
					var param = $("#ppt-form").serialize();
			 		ajaxRequest(basePath+"/resource/ppt/add",param,function(data){
	    				unmask();
	    				if(data.success){
	    					$("#userDialog").dialog("close");
	    				}
	    				//alertInfo(data.errorMessage);
	    				layer.msg(data.errorMessage);
	    				$("#userDialog").dialog("close");
	    				freshTable(pptTable);

	    			});
				}

			}
		});
	});
	
	//新增框教案
	$(".add_case_button").click(function(){
		$('#case-form')[0].reset(); 
		validateCaseForm.resetForm();
		openDialog("userCaseDialog","dialogUserCaseDiv","新增教案",525,500,true,"确定",function(){
			if($("#case-form").valid()){
				var ret = checkCaseVersion();
				if(!ret){
					return;
				}
				mask();
				var filePath = $("#caseFile").val();
				if (typeof(filePath) != "undefined") { 
					if(!fileUploadCase()){
						unmask();
						return;
					}
					
					ajaxFileUpload('caseFile', basePath+"/attachmentCenter/upload?projectName=online&fileType=2", function(data){
						var att = data;
						 $("#casepath").val(att.url);
						 var param = $("#case-form").serialize();
					 		ajaxRequest(basePath+"/cloudclass/ppt/add",param,function(data){
			    				unmask();
			    				if(data.success){
			    					$("#userCaseDialog").dialog("close");
			    					 $("#casepath").val("");
			    					 freshTable(caseTable);
			    				}
			    				layer.msg(data.errorMessage);
			    				//$("#userDialog").dialog("close");
			    				//alertInfo(data.errorMessage);
			    			});	 
						
					});
				}else{
					var param = $("#ppt-form").serialize();
			 		ajaxRequest(basePath+"/resource/ppt/add",param,function(data){
	    				unmask();
	    				if(data.success){
	    					$("#userDialog").dialog("close");
	    				}
	    				//alertInfo(data.errorMessage);
	    				layer.msg(data.errorMessage);
	    				$("#userDialog").dialog("close");
	    				freshTable(pptTable);

	    			});
				}

			}
		});
	});
	//同步视频
	$(".async_video_button").click(function(){
		mask();
		ajaxRequest(basePath+"/cloudclass/course/updateCourseVideoInfo","id="+$('#addcourseName').val(),function(data){
			unmask();
			if(data.success){
				if(data.resultObject == 'ok'){
					layer.msg("操作成功！");
				} else {
					alertInfo(data.resultObject);
				}
			} else {
				alertInfo(data.errorMessage);
			}
			freshTable(videoTable);
		});
	});
	

	
//----------------------------------------------------------------题目开始-------------------------------------------------------------------
	$("#questionOptionValue").html(multipleOne);
	$("#questionContentValue").html(contentTypeNoFilling);
	$("#questionSolutionValue").html(solution);
	createEdit($('.wysiwyg-editor'));//创建编辑器
	/**
	 * 题目类型切换动态地修改该类型需要的题干，答案 begin
	 */
	$("#qustion_type :radio").click(function(){
		$("#questionSolutionValue").empty();
		$("#questionSolutionValue").html(solution);
		var radioValue = $("input[type='radio']:checked").val();
		if (radioValue == 0) {//单选
			$("input[type='radio']").eq(0).attr("checked", true);
			$("#questionOptionValue").empty();
			$("#questionContentValue").empty();
			$("#questionAnswerValue").empty();
			$("#questionOptionValue").html(multipleOne);
			$("#questionContentValue").html(contentTypeNoFilling);
			createEdit($('.wysiwyg-editor'));
		} else if (radioValue == 1) {//多选
			$("input[type='radio']").eq(1).attr("checked", true);
			$("#questionOptionValue").empty();
			$("#questionContentValue").empty();
			$("#questionAnswerValue").empty();
	        $("#questionOptionValue").html(multipleMany);
	        $("#questionContentValue").html(contentTypeNoFilling);
	        createEdit($('.wysiwyg-editor'));
		} else if (radioValue == 2) {//判断
			$("input[type='radio']").eq(2).attr("checked", true);
			$("#questionOptionValue").empty();
			$("#questionContentValue").empty();
			$("#questionAnswerValue").empty();
			$("#questionContentValue").html(contentTypeNoFilling);
			$("#questionAnswerValue").html(answerJudge);
			createEdit($('.wysiwyg-editor'));
		} else if (radioValue == 3) {//填空
			$("input[type='radio']").eq(3).attr("checked", true);
			$("#questionOptionValue").empty();
			$("#questionContentValue").empty();
			$("#questionAnswerValue").empty();
			$("#questionContentValue").html(contentTypeFilling);
			createEdit($('.wysiwyg-editor'));
		} else if (radioValue == 4) {//解答
			$("input[type='radio']").eq(4).attr("checked", true);
			$("#questionOptionValue").empty();
			$("#questionContentValue").empty();
			$("#questionAnswerValue").empty();
			$("#questionContentValue").html(contentTypeNoFilling);
			$("#questionAnswerValue").html(answerNomal);
			createEdit($('.wysiwyg-editor'));
		} else if (radioValue == 5) {//代码
			$("input[type='radio']").eq(5).attr("checked", true);
			$("#questionOptionValue").empty();
			$("#questionContentValue").empty();
			$("#questionAnswerValue").empty();
			$("#questionContentValue").html(contentTypeNoFilling);
			$("#questionAnswerValue").html(answerNomal);
			createEdit($('.wysiwyg-editor'));
		}
		/**
		 * 题目类型切换动态地修改该类型需要的题干，答案 end
		 */
	});
	
	/* 添加题目 */
    $(".add_ques_button").click(function(){
    	var courseId=$("#courseId").val();
    	var courseName=$("#courseName").val();
    	var pId=$("#tId").val();
    	var currentNodeId =$("#currentNodeId").val();
    	var currentNodeName =$("#currentNodeName").val();
    	var courseName=$("#courseName").val();
    	turnPage(basePath+'/home#question/toAdd?courseId='+courseId+"&courseName="+courseName+"&pId="+pId+"&currentNodeId="+currentNodeId+"&currentNodeName="+currentNodeName);
    });
	

	//同步分类
	$(".async_category_button").click(function(){
		mask();
		ajaxRequest(basePath+"/cloudclass/course/updateCategoryInfo","courseId="+$('#addcourseName').val(),function(data){
			unmask();
			if(data.success){
				layer.msg("操作成功！");
			} else {
				alertInfo(data.errorMessage);
			}
		});
	});
	

	$("#pptTable_wrapper").hide();
	$("#caseTable_wrapper").hide();
	$("#quesTable_wrapper").hide();
	$(".dele_bx_ppt").hide();
	$(".dele_bx_case").hide();
	$(".dele_bx_ques").hide();
	$("#searchTable").hide();
	
	if($("#active").val()){
		$(".questionli a").click()
	}
});

//修改
function editDialog(obj){
	validateUpdateForm.resetForm();
	var oo = $(obj).parent().parent().parent();
	var row = videoTable.fnGetData(oo); // get datarow
	console.info(row);
	$("#editId").val(row.id);
	$("#editname").val(row.name);
	$("#editvideoId").val(row.videoId);
	//$("#editVideoTime").val(row.videoTime);
	$("#editVideoSize").val(row.videoSize);
	$("#editvideoVersion").val(row.videoVersion);
	if(row.isTryLearn){
		$("#editisTryLearn").attr("checked",true);
	}else {
		$("#editisTryLearn").attr("checked",false);
		
	}
	
	var dialog = openDialog("editVideoDialog","dialogEditVideoDiv","修改视频",580,500,true,"确定",function(){

		if($("#editVideo-form").valid()){
			mask();
			$("#editVideo-form").attr("action", basePath+"/cloudclass/videores/editVideo");
			 $("#editVideo-form").ajaxSubmit(function(data){
	                unmask();
	                if(data.success){
	                    $("#editVideoDialog").dialog("close");
	                    layer.msg(data.errorMessage);
	                    freshTable(videoTable);
	                    initZtree();
	                }else{
	                	layer.msg(data.errorMessage);
	                }
	            });
		}
	});
	
};
	
//修改PPT
function editPPTDialog(obj){
	$('#pptEdit-form')[0].reset(); 
	validateUpdatePPTForm.resetForm();
	var oo = $(obj).parent().parent().parent();
	var row = pptTable.fnGetData(oo); // get datarow
	$("#editPPTId").val(row.id);
	$("#editsuffix").val(row.suffix);
	$("#editpath").val(row.fileUrl);
	$("#editPPTname").val(row.name);
	$("#editversion").val(row.version);
	$("#editdescription").val(row.description);
	
	openDialog("userEditDialog","dialogUserEditDiv","修改PPT",525,500,true,"确定",function(){
		if($("#pptEdit-form").valid()){
			var ret = checkVersion_edit();
			if(!ret){
				return;
			}
			mask();
			var filePath = $("#editpptFile").val();
			if (typeof(filePath) != "undefined"&&""!=filePath) { 
				if(!fileUpload_edit()){
					unmask();
					return;
				}
				
				ajaxFileUpload('editpptFile', basePath+"/attachmentCenter/upload?projectName=online&fileType=2", function(data){
					var att = data;
					//console.log(att.url);
					 $("#editpath").val(att.url);
					 var param = $("#pptEdit-form").serialize();
				 		ajaxRequest(basePath+"/cloudclass/ppt/updatePPT",param,function(data){
		    				unmask();
		    				if(data.success){
		    					$("#userEditDialog").dialog("close");
		    					 $("#editpath").val("");
		    					 freshTable(pptTable);
		    				}
		    				layer.msg(data.errorMessage);
		    				//$("#userDialog").dialog("close");
		    				//alertInfo(data.errorMessage);
		    			});	 
					
				});
			}else{
				var param = $("#pptEdit-form").serialize();
		 		ajaxRequest(basePath+"/cloudclass/ppt/updatePPT",param,function(data){
    				unmask();
    				if(data.success){
    					$("#userEditDialog").dialog("close");
    				}
    				//alertInfo(data.errorMessage);
    				layer.msg(data.errorMessage);
    				$("#userEditDialog").dialog("close");
    				freshTable(pptTable);

    			});
			}

		}
	});
};

//修改案例
function editCaseDialog(obj){
	validateUpdateCaseForm.resetForm();
	$('#caseEdit-form')[0].reset(); 
	var oo = $(obj).parent().parent().parent();
	var row = caseTable.fnGetData(oo); // get datarow
	$("#editCaseId").val(row.id);
	$("#suffixCaseEdit").val(row.suffix);
	$("#casepathEdit").val(row.fileUrl);
	$("#casenameEdit").val(row.name);
	$("#caseversionEdit").val(row.version);
	$("#casedescriptionEdit").val(row.description);
	
	openDialog("userCaseEditDialog","dialogUserEditCaseDiv","修改教案",525,500,true,"确定",function(){
		if($("#caseEdit-form").valid()){
			var ret = checkCaseVersion_edit();
			if(!ret){
				return;
			}
			mask();
			var filePath = $("#caseFileEdit").val();
			if (typeof(filePath) != "undefined"&&""!=filePath) { 
				if(!fileUploadCaseEdit()){
					unmask();
					return;
				}
				
				ajaxFileUpload('caseFileEdit', basePath+"/attachmentCenter/upload?projectName=online&fileType=2", function(data){
					var att = data;
					//console.log(att.url);
					 $("#casepathEdit").val(att.url);
					 $("#isuploadFile").val('1');
					 var param = $("#caseEdit-form").serialize();
				 		ajaxRequest(basePath+"/cloudclass/ppt/updatePPT",param,function(data){
		    				unmask();
		    				if(data.success){
		    					$("#userCaseEditDialog").dialog("close");
		    					 $("#casepathEdit").val("");
		    					 freshTable(caseTable);
		    				}
		    				layer.msg(data.errorMessage);
		    				//$("#userDialog").dialog("close");
		    				//alertInfo(data.errorMessage);
		    			});	 
					
				});
			}else{
				$("#isuploadFile").val('0');
				var param = $("#caseEdit-form").serialize();
		 		ajaxRequest(basePath+"/cloudclass/ppt/updatePPT",param,function(data){
    				unmask();
    				if(data.success){
    					$("#userCaseEditDialog").dialog("close");
    				}
    				//alertInfo(data.errorMessage);
    				layer.msg(data.errorMessage);
    				$("#userCaseEditDialog").dialog("close");
    				freshTable(caseTable);

    			});
			}

		}
	});
	
	
};

//删除
function delOne(id){
	showDelDialog(function(){
		mask();
		var url = basePath+"/cloudclass/videores/deleteVideoById";
		ajaxRequest(url,{'id':id},function(data){
			unmask();
			if(!data.success){
	             layer.msg(data.errorMessage);
			}else{
				layer.msg(data.errorMessage);
				freshTable(videoTable);
				 initZtree();
			}
		});
	});
};

//删除PPT
function delOnePPT(id){
	showDelDialog(function(){
		mask();
		var url = basePath+"/cloudclass/ppt/deletePPTById";
		ajaxRequest(url,{'id':id},function(data){
			unmask();
			if(!data.success){
	             layer.msg(data.errorMessage);
			}else{
				layer.msg(data.errorMessage);
				freshTable(pptTable);
			}
		});
	});
};

//删除教案
function delOneCase(id){
	showDelDialog(function(){
		mask();
		var url = basePath+"/cloudclass/ppt/deletePPTById";
		ajaxRequest(url,{'id':id},function(data){
			unmask();
			if(!data.success){
	             layer.msg(data.errorMessage);
			}else{
				layer.msg(data.errorMessage);
				freshTable(caseTable);
			}
		});
	});
};

/**
 * 上移
 * @param obj
 */
function upMove(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = videoTable.fnGetData(oo);
	ajaxRequest(basePath+'/cloudclass/videores/upMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(videoTable);
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
	var aData = videoTable.fnGetData(oo);
	ajaxRequest(basePath+'/cloudclass/videores/downMove',{"id":aData.id},function(res){
		if(res.success){
			freshTable(videoTable);
		}else{
			layer.msg(res.errorMessage);
		}
	});
};


function search(){
	searchButton(videoTable);
};
function searchPPT(){
	searchButton(pptTable);
};
function searchCase(){
	searchButton(caseTable);
};
function searchques(){
	searchCaseQues.push('{"tempMatchType":"7","propertyName":"courseId","propertyValue1":"' + $("#courseId").val() + '","tempType":"String"}');
	searchCaseQues.push('{"tempMatchType":"8","propertyName":"chapterId","propertyValue1":"' + $("#search_chapterIds").val() + '","tempType":"String"}');
	searchButton(quesTable,searchCaseQues);
	searchCaseQues.pop();
	searchCaseQues.pop();
};


function getChildren(ids,treeNode){

	ids.push(treeNode.id);

	if (treeNode.isParent){

			for(var obj in treeNode.children){

				getChildren(ids,treeNode.children[obj]);

			}

	   }

	return ids;

}


function goback(page){
	window.location.href=basePath+'/home#cloudclass/course/index?page='+page;
}
/**
 * 设置视频分类，二级联动
 */
function setVideoCategory(){
	$('#category').html('');
	$.ajax({
		url:basePath+'/cloudclass/course/getVideoCategory',
		dataType:'json',
		success:function(data){
			
			var kename=$('#courseName').val();
			var zhangname;
			
			var nodes = treeObj.getSelectedNodes();
			if(nodes && nodes.length>0){
				var jie = nodes[0].getParentNode();
				if(jie){
					var zhang = jie.getParentNode();
					if (zhang){
						zhangname = zhang.actName;
					}
				}
			}
			
			//设置一级分类
			var str = '';
			var index0_id = null;
			var arry = data.video.category;
			for(var i=0;i<arry.length;i++){
				if(arry[i].name == kename){
					str+=('<option value="'+arry[i].id+'" selected = "selected">'+arry[i].name+'</option>');
				} else {
					str+=('<option value="'+arry[i].id+'">'+arry[i].name+'</option>');
				}
				if(kename && zhangname && arry[i].name == kename){
					index0_id = data.video.category[i].id;
				} else {
					if(i == 0){
						index0_id = data.video.category[i].id;
					}
				}
			}
			
			$('#category').html(str);
			
			setSubCategory(index0_id,arry,zhangname);
			
			//设置二级分类
			$('#category').bind('change',function(e){
				var id = $(this).val();
				setSubCategory(id,arry,null);
			});
		}
	});
}
/**
 * 设置二级分类
 * @param id 被选中的一级分类的id
 * @param arry 一级分类数组
 */
function setSubCategory(id,arry,zhangname){
	$('#sub-category').html('');
	if(arry && arry.length > 0){
		for(i in arry){
			if(id == arry[i].id){
				var sub = arry[i]['sub-category'];
				if(sub && sub.length > 0){
					var substr = '';
					for(a in sub){
						if(zhangname && sub[a].name == zhangname){
							substr+=('<option value="'+sub[a].id+'" selected = "selected">'+sub[a].name+'</option>');
						} else {
							substr+=('<option value="'+sub[a].id+'">'+sub[a].name+'</option>');
						}
					}
					$('#sub-category').html(substr);
				}
				break;
			}
		}
	}
}
/**
 * 当选择了视频，把视频名称写入input框
 * @param filename
 */
function on_spark_selected_file(filename,file_size) {
	$('#videofile').val(filename);
	$('#addVideoSize').val(Math.round(file_size/1024/1024)+'M');
	$('#addname').val(filename.substring(0,filename.lastIndexOf('.')));
}
/**
 * 视频上传的校验
 * @param status
 * @param videoid
 */
function on_spark_upload_validated(status, videoid) {
	if (status == "OK") {
		$('#addvideoId').val(videoid);
	} else if (status == "NETWORK_ERROR") {
		alert("网络错误");
	} else {
		alert("api错误码：" + status);
	}
}
/**
 * 上传进度
 * @param progress
 */
function on_spark_upload_progress(progress) {
	var uploadProgress = $('#uploadProgress');
	var str='';
	if(progress != -1){
		for(var i=0;i<progress;i++){
			str+='|';
		}
	}
	
	if (progress == -1) {
		uploadProgress.val("上传出错：" + progress);
	} else if (progress == 100) {
		uploadProgress.val(str+progress+"%");
		//提交表单
		submitAddForm();
	} else {
		uploadProgress.val(str+progress+"%");
	}
}

function addvideoinfo(){
	mask();
	var url = "";
	$.ajax({
		url:basePath+'/cloudclass/course/getUploadUrl',
		async: false,
		data:{title:$('#addname').val(),categoryid:$('#sub-category').val()},
		success:function(data){
			url = data;
		}
	});
	url = url + "&categoryid="+$('#sub-category').val();
	document.getElementById("uploadswf").start_upload(url); //	调用flash上传函数
}
/**
 * 状态修改
 * @param obj
 */
function updateStatus(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = videoTable.fnGetData(oo);
	ajaxRequest(basePath+"/cloudclass/videores/updateStatus",{"id":aData.id,"status":aData.status},function(){
		freshTable(videoTable);
	});
}

/**
 * 状态PPT修改
 * @param obj
 */
function updatePPTStatus(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = pptTable.fnGetData(oo);
	ajaxRequest(basePath+"/cloudclass/ppt/updateStatus",{"id":aData.id,"status":aData.status},function(data){
		if(data.success){
			freshTable(pptTable);
		}else{
				layer.msg("只能启用1条PPT数据！");
		}
		
	});
}


/**
 * 状态教案修改
 * @param obj
 */
function updateCaseStatus(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = caseTable.fnGetData(oo);
	ajaxRequest(basePath+"/cloudclass/ppt/updateStatus",{"id":aData.id,"status":aData.status},function(data){
		if(data.success){
			freshTable(caseTable);
		}else{
				layer.msg("只能启用1条教案数据！");
		}
		
	});
}

function submitAddForm(){
	//form表单提交
	$("#addVideo-form").attr("action", basePath+"/cloudclass/videores/addVideo");
	$("#addVideo-form").ajaxSubmit(function(data){
		unmask();
		if(data.success){
			$("#addVideoDialog").dialog("close");
			layer.msg(data.errorMessage);
			freshTable(videoTable);
			//初始化Ztree
			initZtree();	
		}else{
			layer.msg(data.errorMessage);
		}
	});
}

function initZtree(){
	var position = $(".contrightbox").offset();
	layer.load(1, {
		  shade: [0.1,'#fff'] ,//0.1透明度的白色背景
		  offset: [(position.top + 158) + 'px', (position.left + 128) + 'px']
		});
	//------------------------Ztree初始化开始-----------------------------------------------
	/**
	 * 
	 * @param objId  对象的ID
	 * @param url    请求的后台地址
	 * @param callback  成功后的回调方法
	 * @param addTree   右键添加方法
	 * @param updateTree  右键修改方法
	 * @param deleteTree  右键删除方法
	 * @returns
	 */
	treeObj = loadTreeDynomic("ztree",basePath+"/cloudclass/videores/showTree?courseId="+$("#courseId").val()+"&courseName="+$("#courseName").val()+"&currentNodePid="+$("#tId").val(),function(){
		$("#"+localStorage.spnode).trigger("click");
		$("#"+localStorage.snode).trigger("click");
	},
	function(currentNode){
		if(currentNode.contenttype ==4) {
			alertInfo("不能添加！！");
  			 return ;
      	  }
		if(currentNode.contenttype ==1) {
			var fileName = window.prompt("请输入章名称");
     	  }
		 if(currentNode.contenttype ==2) {
			 var fileName = window.prompt("请输入节名称");
    	  }
		 if(currentNode.contenttype ==3) {
			 var fileName = window.prompt("请输入知识点名称");
    	  }
		 fileName=$.trim(fileName);
		if (fileName) {// 该名字不能为空
      	  if(fileName.length>50||fileName.length==0) {
      		 alertInfo("请输入1~50位字符！");
  			 return ;
      	  }
     	  //var containSpecial = RegExp(/[~#^$@%&!*]/);   
     	 /* var containSpecial = /^[\u4e00-\u9fa5a-zA-Z0-9_()]{0,}$/;
     	 // alert(containSpecial.test(fileName));
     	  if(!containSpecial.test(fileName)) {
      		 alertInfo("只能输入中文、英文、数字、下划线、英文 小括号！");
  			 return ;
      	  }     */
		
     	ajaxRequest(basePath+ "/cloudclass/videores/checkname", {
      		 ksystemId : $("#ksystemId").val(),
            name: fileName
        }, function(data){
        	if(!data){
        		if(currentNode.contenttype ==1) {
        			 alertInfo("章名称不能重复！！");
          			 return ;
              	  }
        		if(currentNode.contenttype ==2) {
       			 alertInfo("节名称不能重复！！");
         			 return ;
             	  }
       		 }
        	 $.post(basePath+ "/cloudclass/videores/addNode", {
        		 pId: currentNode.id,
                 name: fileName,
                 courseId : $("#courseId").val(),
                 contenttype: currentNode.contenttype,
             }, function(data){
                 /**
                  * 新建一个新的文件节点,并且把文件节点追加到父节点上
                  */ 
             	var newNode = {
                 		 id: data.id,
                 		 name: fileName,
                          contenttype:data.contenttype,
                          nocheck:true
                     };
             	treeObj.addNodes(currentNode, newNode);
             });
        	 
        });
		
         }
		
	},function(currentNode){
		if(currentNode.contenttype ==1) {
			 alertInfo("不能修改！！");
 			 return ;
     	  }
		 if(currentNode.contenttype ==2) {
			 if(currentNode.actName==null||currentNode.actName==undefined){
				 var fileName = window.prompt("请输入要修改章的名字",currentNode.name);
			 }else{
				 var fileName = window.prompt("请输入要修改章的名字",currentNode.actName);
			 }
      	  }
		 if(currentNode.contenttype ==3) {
			 if(currentNode.actName==null||currentNode.actName==undefined){
				 var fileName = window.prompt("请输入要修改节的名字",currentNode.name);
			 }else{
				 var fileName = window.prompt("请输入要修改节的名字",currentNode.actName);
			 }
     	  }
		 if(currentNode.contenttype ==4) {
			 if(currentNode.actName==null||currentNode.actName==undefined){
				 var fileName = window.prompt("请输入要修知识点的名字",currentNode.name);
			 }else{
				 var fileName = window.prompt("请输入要修知识点的名字",currentNode.actName);
			 }
	     }
		 fileName=$.trim(fileName);
		 if (fileName) {// 该名字不能为空
	      	  if(fileName.length>50||fileName.length==0) {
	      		 alertInfo("请输入1~50位中文、英文、数字、下划线！");
       			 return ;
           	  }
        	
        	 //var containSpecial = RegExp(/[~#^$@%&!*]/);   
        	 /* var containSpecial = /^[\u4e00-\u9fa5a-zA-Z0-9_]{0,}$/;
        	  if(!containSpecial.test(fileName)) {
        		 alertInfo("只能输入中文、英文、数字、下划线！");
     			 return ;
         	  }    */
        	 
        	$.post(basePath+ "/cloudclass/videores/checkname", {
        		 ksystemId : $("#ksystemId").val(),
        		id: currentNode.id,
                name: fileName
            }, function(data){
            	 if(!data){
            		 if(currentNode.contenttype ==2) {
            			 alertInfo("章名称不能重复！！");
              			 return ;
                  	  }
            		if(currentNode.contenttype ==3) {
           			 alertInfo("节名称不能重复！！");
             			 return ;
                 	  }
           		 }
            	 $.post(basePath+"/cloudclass/videores/updateNode", {
                     id: currentNode.id,
                     name: fileName,
                 }, function(data){
                	 currentNode.name = fileName;
                	 currentNode.actName = fileName;
                	 treeObj.updateNode(currentNode);
                 });
            	 
            });
        	
        }
	},function(currentNode){
		if(currentNode.contenttype ==1) {
			
			 alertInfo("不能删除！！");
			 return ;
    	  }
		
			 var ids=[];
		   	 var idArr
			 ids = getChildren(ids,currentNode);
			 for(var i =0 ;i<ids.length;i++){
	                if(i!=0){
	                	idArr=idArr+","+ids[i];
	                }else{
	                	idArr=ids[i];
	                }
			 }
			 ajaxRequest(basePath+"/cloudclass/videores/checkVideoUse", {
             	id: idArr,
             	courseId : $("#courseId").val(),
             }, function(data){
            	 
        	
            	 var text="确定要删除该条数据吗?";
            	 if(data){
            		
            		 showDelDialog(function(){
 	 					ajaxRequest(basePath+"/cloudclass/videores/deleteNode",{
 	             	 		id: idArr,
 	             	 		contenttype: currentNode.contenttype,
 	             		},function(res){
 	             			 if(currentNode.isParent){
 	                   	       treeObj.removeChildNodes(currentNode);
 	                   		}
 	                   		treeObj.removeNode(currentNode);
 	                   		/*defLoad();*/
 	                   	 $(".add_bx").hide();
 						});
 	 				 },null,text);
            	 }else{
            		 if(currentNode.contenttype ==2){
                		 alertInfo("章下资源被引用，不能删除!");
                    	 return ;
                	 }
                	 if(currentNode.contenttype==3){
                		 alertInfo("节下资源被引用，不能删除!");
                    	 return ;
                	 }
                	 if(currentNode.contenttype==4){
                		 alertInfo("知识点下资源被引用，不能删除!");
                    	 return ;
                	 } 
            	 }
             });
	    
	},function(currentNode){
		
		$("#knowledge").html("");
		$("#getKsystemBySearch").val("");
		$("#level").val(currentNode.contenttype);
		$("#treeId").val(currentNode.id);
		clearTree();
		dialog = openDialog("knowledgeDialog","dialogCourseSystemDiv","关联课程资源",950,650,true,"保存",saveKnowledge,null,null,function(){
			$("#getKsystemBySearch").val("");
			if(isUpdateClickForCourseSystem){
				confirmInfos("是否确定取消此操作？",function(){
					dialogFlagForCourseSystem = true;
					isUpdateClickForCourseSystem = false;
					dialog.dialog( "close" ); 
				});
				return dialogFlagForCourseSystem;
			}
			return true;
		});
		//console.log($("#courseUl li").eq(1));
		$("#courseUl li a").eq(0).click();
		
	},function(node){
		 var ids=[];
		 ids = getChildren(ids,node)
		 
		 $("#search_chapterIds").val(ids);
		 $(".add_bx").hide();
		 $(".add_ppt_button").hide();
		 $(".add_case_button").hide();
		 
		searchCase.push('{"tempMatchType":"7","propertyName":"courseId","propertyValue1":"' + $("#courseId").val() + '","tempType":"String"}');
		searchCase.push('{"tempMatchType":"8","propertyName":"chapterId","propertyValue1":"' + ids + '","tempType":"String"}');
		$("#currentNodeLevel").val(node.contenttype);
		/*if(node.contenttype!=1&&$(".videoli").hasClass("active")){
			$(".async_video_button").hide();
			$(".async_category_button").hide();
		}*/
		if(node.contenttype==4){
			$("#currentNodeLevel").val(4);
			$("#addchapterId").val(node.id);
			$("#addPPTchapterId").val(node.id);
			$("#addCasechapterId").val(node.id);
			$("#currentNodeId").val(node.id);
			$("#currentNodeName").val(node.actName);
			if($(".videoli").hasClass("active")){
				$(".add_bx").show();
			}
			if($(".pptli").hasClass("active")){
				$(".add_ppt_button").show();
				$(".async_video_button").hide();
				$(".async_category_button").hide();
			}
			if($(".caseli").hasClass("active")){
				$(".add_case_button").show();
				$(".async_video_button").hide();
				$(".async_video_button").hide();
				$(".async_category_button").hide();
			}
			if($(".questionli").hasClass("active")){
				$(".add_ques_button").show();
				$(".async_video_button").hide();
				$(".async_video_button").hide();
				$(".async_category_button").hide();
			}
			/*$(".tab-pills li").each(function(){
				if($(this).hasClass("videoli")){
					$(".add_bx").show();
				}
			})*/
			
		}
		//console.log(node);
		$("#tId").val(node.pId);
		
		for(i=0;i<$("#search_questionType option").length;i++){
			if($("#search_questionType option").eq(i).text()=="全部"){
				$("#search_questionType option").eq(i).attr("select","selected"); 
				$("#search_questionType").val($("#search_questionType option").eq(i).val());
			}
		}
		$("#search_questionHeadText").val("");
		
		searchButton(videoTable,searchCase);
		searchButton(pptTable,searchCase);
		searchButton(caseTable,searchCase);
		searchButton(quesTable,searchCase);
		
		
		searchCase.pop();
		searchCase.pop();
		
		},function(data){
		
		 //排序后台逻辑 alertInfo(data)
		 var confirmVal = false;  
		 $.ajax({  
			  async: false,  
			  type: "post",  
			  data:data,  
			  url:basePath+"/cloudclass/videores/updateNodeSort",  
			  success: function(json){  
					if(json=="1" ){  
						 confirmVal = true;  
					} else{  
						alertInfo('亲，操作失败');  
					}  
			  },  
			  error: function(){  
					alertInfo('亲，网络有点不给力呀！');  
			  }  
		 });  
		 return confirmVal; 
	});
//------------------------Ztree初始化结束-----------------------------------------------
}

function saveKnowledge(){
	viewRightTree(function(arr){
		//console.log(arr);
		var tmp = new Array();
		var kpo = new Array();
		$(arr).each(function(i,o){
			tmp.push(o.courseId + "-"+o.id);
			kpo.push(o.id);
		});
		var kpoint = tmp.unique2();
		var kpos = kpo.unique2();
		
		var level=$("#level").val();
		var courentTreeId=$("#treeId").val();
		var courseId=$("#courseId_copy").val();
		
				mask();
				ajaxRequest(basePath+"/cloudclass/videores/saveCopyTree",
						{level:level,courentTreeId:courentTreeId,courseId:courseId, kpos:kpos.join(",")},
				function(resp){
					isUpdateClickForCourseSystem = false;
					unmask();
					if(resp.success){
						//初始化Ztree
						initZtree();
						dialog.dialog("close");
						
					}
					//ajaxCallback(resp);
				});
		
	});
}


/**
* 日期格式化yyyy-MM-dd HH:mm:ss
*/
function FormatDate (strTime) {
	var year = strTime.getFullYear();
	var month = getFormat(strTime.getMonth()+1);
	var day = getFormat(strTime.getDate());
	var hours = getFormatHMS(strTime.getHours());
	var minutes = getFormatHMS(strTime.getMinutes());
	var seconds = getFormatHMS(strTime.getSeconds());
    return year+"-"+month+"-"+day +" "+hours+":"+minutes+":"+seconds; 
}

function checkVersion(){
	var ver = $("#version").val();
	if(ver == ""){
		alertInfo("请输入版本号如:v1.0");
		return false;
	
	}
	if(ver.substr(0,1) != "v"){
		alertInfo("版本号应以v开头，如:v1.0");
		return false;
	
	}
	var v_num = ver.substr(1,ver.lastIndexOf(".")-1);
	
	if(!IsNum(v_num)){
		alertInfo("版本号应为整数，如:v1.0");
		return false;
		
	}
	var v_num2 = ver.substr(ver.lastIndexOf(".")+1);
	
	if(!IsNum(v_num2)){
		alertInfo("小版本号应为整数，如:v1.0");
		return false;
	}
	return true;
}

function checkCaseVersion(){
	var ver = $("#caseversion").val();
	if(ver == ""){
		alertInfo("请输入版本号如:v1.0");
		return false;
	
	}
	if(ver.substr(0,1) != "v"){
		alertInfo("版本号应以v开头，如:v1.0");
		return false;
	
	}
	var v_num = ver.substr(1,ver.lastIndexOf(".")-1);
	
	if(!IsNum(v_num)){
		alertInfo("版本号应为整数，如:v1.0");
		return false;
		
	}
	var v_num2 = ver.substr(ver.lastIndexOf(".")+1);
	
	if(!IsNum(v_num2)){
		alertInfo("小版本号应为整数，如:v1.0");
		return false;
	}
	return true;
}

function checkCaseVersion_edit(){
	var ver = $("#caseversionEdit").val();
	if(ver == ""){
		alertInfo("请输入版本号如:v1.0");
		return false;
	
	}
	if(ver.substr(0,1) != "v"){
		alertInfo("版本号应以v开头，如:v1.0");
		return false;
	
	}
	var v_num = ver.substr(1,ver.lastIndexOf(".")-1);
	
	if(!IsNum(v_num)){
		alertInfo("版本号应为整数，如:v1.0");
		return false;
		
	}
	var v_num2 = ver.substr(ver.lastIndexOf(".")+1);
	
	if(!IsNum(v_num2)){
		alertInfo("小版本号应为整数，如:v1.0");
		return false;
	}
	return true;
}


function checkVersion_edit(){
	var ver = $("#editversion").val();
	if(ver == ""){
		alertInfo("请输入版本号如:v1.0");
		return false;
	
	}
	if(ver.substr(0,1) != "v"){
		alertInfo("版本号应以v开头，如:v1.0");
		return false;
	
	}
	var v_num = ver.substr(1,ver.lastIndexOf(".")-1);
	
	if(!IsNum(v_num)){
		alertInfo("版本号应为整数，如:v1.0");
		return false;
		
	}
	var v_num2 = ver.substr(ver.lastIndexOf(".")+1);
	
	if(!IsNum(v_num2)){
		alertInfo("小版本号应为整数，如:v1.0");
		return false;
	}
	return true;
}

function IsNum(s)
{
    if(s!=null){
        var r,re;
        re = /\d*/i; //\d表示数字,*表示匹配多个数字
        r = s.match(re);
        return (r==s)?true:false;
    }
    return false;
}
function fileUpload() {
	//$("#name").val("");
	$("#path").val("");
	 var filePath = $("#pptFile").val();
	 if(filePath==null || ""==filePath){
		 alertInfo("上传PPT为空");
		 return false;
	 }
	 var str = filePath;
	 var n = str.lastIndexOf("\\") //获取斜杠最后一次出现的位置
	 var ppt_ = str.substring(n+1);
	 if(ppt_.length>60){
		 $("#pptFile").val('');
		 $("#name").val('');
		 alertInfo("PPT文件的名字过长，请修改长度小于60!");
		 return false;
	 }
	 
	 var tag = false;
	 if(filePath.lastIndexOf('.ppt')==-1&&filePath.lastIndexOf('.pptx')==-1){
		 
		 $("#pptFile").val('');
		 alertInfo('请上传格式为ppt、pptx的文件！');
		
		 return false ;
	 }
	
	 if(filePath.lastIndexOf('.ppt')!=-1){
		 $("#suffix").val('.ppt');
	 }
	 if(filePath.lastIndexOf('.pptx')!=-1){
		 $("#suffix").val('.pptx');
	 }
	 
	/* if(validateImgSize("pptFile",0)){
		 alertInfo("附件不能为0M,请重新选择.");
		 $("#pptFile").val('');
		return false;
	}
	 
	 if(!validateImgSize("pptFile",10)){
		 alertInfo("附件大于10M,请重新选择.");
		 $("#pptFile").val('');
		return false;
	}*/
//	var name1 = ppt_.substr(0,ppt_.lastIndexOf("."));
//	 ajaxRequest(basePath+"/resource/ppt/checkname",{name:name1,id:$("#knowledgeId").val()},function(data){
// 		 if(data){
// 			alertInfo("名称重复");
// 			$("#pptFile").val("");
// 			$("#path").val("");
// 			$("#name").val("");
// 			return false;
// 		 }else{
// 		 }
// 	});
	 $("#path").val(filePath);
	 var name = ppt_.substr(0,ppt_.lastIndexOf("."));
	 name = name.substr(0,180);
	// name =name.replace(/\ +/g,"_");
	$("#name").val(name);
	return true;
}

function fileUploadCase() {
	//$("#name").val("");
	$("#casepath").val("");
	 var filePath = $("#caseFile").val();
	 if(filePath==null || ""==filePath){
		 alertInfo("上传教案为空");
		 return false;
	 }
	 var str = filePath;
	 var n = str.lastIndexOf("\\") //获取斜杠最后一次出现的位置
	 var ppt_ = str.substring(n+1);
	 if(ppt_.length>60){
		 $("#caseFile").val('');
		 $("#casename").val('');
		 alertInfo("PPT文件的名字过长，请修改长度小于60!");
		 return false;
	 }
	 
	 var tag = false;
	 if(filePath.lastIndexOf('.doc')==-1&&filePath.lastIndexOf('.docx')==-1&&filePath.lastIndexOf('.txt')==-1&&filePath.lastIndexOf('.pdf')==-1
			 &&filePath.lastIndexOf('.md')==-1&&filePath.lastIndexOf('.MD')==-1){
		 
		 $("#caseFile").val('');
		 alertInfo('请上传格式为doc、docx、txt、pdf、md的文件！');
		
		 return false ;
	 }
	 
	 if(filePath.lastIndexOf('.doc')!=-1){
		 $("#suffixCase").val('.doc');
	 }
	 if(filePath.lastIndexOf('.docx')!=-1){
		 $("#suffixCase").val('.docx');
	 }
	 if(filePath.lastIndexOf('.txt')!=-1){
		 $("#suffixCase").val('.txt');
	 }
	 if(filePath.lastIndexOf('.pdf')!=-1){
		 $("#suffixCase").val('.pdf');
	 }
	 if(filePath.lastIndexOf('.md')!=-1){
		 $("#suffixCase").val('.md');
	 }
	 if(filePath.lastIndexOf('.MD')!=-1){
		 $("#suffixCase").val('.MD');
	 }
	/* if(validateImgSize("pptFile",0)){
		 alertInfo("附件不能为0M,请重新选择.");
		 $("#pptFile").val('');
		return false;
	}
	 
	 if(!validateImgSize("pptFile",10)){
		 alertInfo("附件大于10M,请重新选择.");
		 $("#pptFile").val('');
		return false;
	}*/
//	var name1 = ppt_.substr(0,ppt_.lastIndexOf("."));
//	 ajaxRequest(basePath+"/resource/ppt/checkname",{name:name1,id:$("#knowledgeId").val()},function(data){
// 		 if(data){
// 			alertInfo("名称重复");
// 			$("#pptFile").val("");
// 			$("#path").val("");
// 			$("#name").val("");
// 			return false;
// 		 }else{
// 		 }
// 	});
	 $("#casepath").val(filePath);
	// alert(ppt_)
	 var name = ppt_.substr(0,ppt_.lastIndexOf("."));
	 name = name.substr(0,180);
	// name =name.replace(/\ +/g,"_");
	$("#casename").val(name);
	return true;
}

function fileUploadCaseEdit(){
	//$("#name").val("");
	$("#casepathEdit").val("");
	 var filePath = $("#caseFileEdit").val();
	/* if(filePath==null || ""==filePath){
		 alertInfo("上传PPT为空");
		 return false;
	 }*/
	 var str = filePath;
	 var n = str.lastIndexOf("\\") //获取斜杠最后一次出现的位置
	 var ppt_ = str.substring(n+1);
	 if(ppt_.length>60){
		 $("#caseFileEdit").val('');
		 $("#casenameEdit").val('');
		 alertInfo("PPT文件的名字过长，请修改长度小于60!");
		 return false;
	 }
	 
	 var tag = false;
	 if(filePath.lastIndexOf('.doc')==-1&&filePath.lastIndexOf('.docx')==-1&&filePath.lastIndexOf('.txt')==-1&&filePath.lastIndexOf('.pdf')==-1
			 &&filePath.lastIndexOf('.md')==-1&&filePath.lastIndexOf('.MD')==-1){
		 
		 $("#caseFileEdit").val('');
		 alertInfo('请上传格式为doc、docx、txt、pdf、md的文件！');
		
		 return false ;
	 }
	 
	 if(filePath.lastIndexOf('.doc')!=-1){
		 $("#suffixCaseEdit").val('.doc');
	 }
	 if(filePath.lastIndexOf('.docx')!=-1){
		 $("#suffixCaseEdit").val('.docx');
	 }
	 if(filePath.lastIndexOf('.txt')!=-1){
		 $("#suffixCaseEdit").val('.txt');
	 }
	 if(filePath.lastIndexOf('.pdf')!=-1){
		 $("#suffixCaseEdit").val('.pdf');
	 }
	 if(filePath.lastIndexOf('.md')!=-1){
		 $("#suffixCaseEdit").val('.md');
	 }
	 if(filePath.lastIndexOf('.MD')!=-1){
		 $("#suffixCaseEdit").val('.MD');
	 }
	/* if(validateImgSize("pptFile",0)){
		 alertInfo("附件不能为0M,请重新选择.");
		 $("#pptFile").val('');
		return false;
	}
	 
	 if(!validateImgSize("pptFile",10)){
		 alertInfo("附件大于10M,请重新选择.");
		 $("#pptFile").val('');
		return false;
	}*/
//	var name1 = ppt_.substr(0,ppt_.lastIndexOf("."));
//	 ajaxRequest(basePath+"/resource/ppt/checkname",{name:name1,id:$("#knowledgeId").val()},function(data){
// 		 if(data){
// 			alertInfo("名称重复");
// 			$("#pptFile").val("");
// 			$("#path").val("");
// 			$("#name").val("");
// 			return false;
// 		 }else{
// 		 }
// 	});
	 $("#casepathEdit").val(filePath);
	 var name = ppt_.substr(0,ppt_.lastIndexOf("."));
	 name = name.substr(0,180);
	// alert(ppt_)
	// name =name.replace(/\ +/g,"_");
	$("#casenameEdit").val(name);
	return true;
}


function fileUpload_edit() {
	//$("#name").val("");
	$("#editpath").val("");
	 var filePath = $("#editpptFile").val();
	/* if(filePath==null || ""==filePath){
		 alertInfo("上传PPT为空");
		 return false;
	 }*/
	 var str = filePath;
	 var n = str.lastIndexOf("\\") //获取斜杠最后一次出现的位置
	 var ppt_ = str.substring(n+1);
	 if(ppt_.length>60){
		 $("#editpptFile").val('');
		 $("#editPPTname").val('');
		 alertInfo("PPT文件的名字过长，请修改长度小于60!");
		 return false;
	 }
	 
	 var tag = false;
	 if(filePath.lastIndexOf('.ppt')==-1&&filePath.lastIndexOf('.pptx')==-1){
		 
		 $("#editpptFile").val('');
		 alertInfo('请上传格式为ppt、pptx的文件！');
		
		 return false ;
	 }
	 
	 if(filePath.lastIndexOf('.ppt')!=-1){
		 $("#editsuffix").val('.ppt');
	 }
	 if(filePath.lastIndexOf('.pptx')!=-1){
		 $("#editsuffix").val('.pptx');
	 }
	
	/* if(validateImgSize("pptFile",0)){
		 alertInfo("附件不能为0M,请重新选择.");
		 $("#pptFile").val('');
		return false;
	}
	 
	 if(!validateImgSize("pptFile",10)){
		 alertInfo("附件大于10M,请重新选择.");
		 $("#pptFile").val('');
		return false;
	}*/
//	var name1 = ppt_.substr(0,ppt_.lastIndexOf("."));
//	 ajaxRequest(basePath+"/resource/ppt/checkname",{name:name1,id:$("#knowledgeId").val()},function(data){
// 		 if(data){
// 			alertInfo("名称重复");
// 			$("#pptFile").val("");
// 			$("#path").val("");
// 			$("#name").val("");
// 			return false;
// 		 }else{
// 		 }
// 	});
	 $("#editpath").val(filePath);
	 var name = ppt_.substr(0,ppt_.lastIndexOf("."));
	 name = name.substr(0,180);
	// name =name.replace(/\ +/g,"_");
	$("#editPPTname").val(name);
	return true;
}

function titlelength(node){
	var maxCount =node.attr("maxCount")||100;
	 var len = node.get(0).value.trim().length;
	node.next(".count").html(len+"/"+maxCount);
	if(node.get(0).value.length>=maxCount){
		 node.get(0).value = node.get(0).value.trim().substr(0,maxCount);
	}
}

function showVideo(){
	$("#videoTable_wrapper").show();
	$("#pptTable_wrapper").hide();
	$("#caseTable_wrapper").hide();
	$("#quesTable_wrapper").hide();
	
	if($("#currentNodeLevel").val()==4){
		$(".add_video_button").show();
	}else{
		$(".add_video_button").hide();
	}
	
	if(!$("#currentNodeLevel").val() || $("#currentNodeLevel").val()==1){
		$(".async_video_button").show();
		$(".async_category_button").show();
	}else{
		$(".async_video_button").hide();
		$(".async_category_button").hide();
	}
	$(".add_ques_button").hide();
	
	$(".add_ppt_button").hide();
	$(".add_case_button").hide();
	
	$(".dele_bx").show();
	$(".dele_bx_ppt").hide();
	$(".dele_bx_case").hide();
	$(".dele_bx_ques").hide();
	
	$(".daoru_bx").hide();
	$("#searchTable").hide();
	
	$("#video_enable").show();
	$("#video_disable").show();
	
	$("#ques_enable").hide();
	$("#ques_disable").hide();
}

function showPPT(){
	$("#videoTable_wrapper").hide();
	$("#pptTable_wrapper").show();
	$("#caseTable_wrapper").hide();
	$("#quesTable_wrapper").hide();
	
	$(".add_video_button").hide();
	if($("#currentNodeLevel").val()==4){
		$(".add_ppt_button").show();
	}else{
		$(".add_ppt_button").hide();
	}
	$(".add_case_button").hide();
	$(".add_ques_button").hide();
	
	$(".async_video_button").hide();
	$(".async_category_button").hide();
	$(".dele_bx").hide();
	$(".dele_bx_ppt").show();
	$(".dele_bx_case").hide();
	$(".dele_bx_ques").hide();
	
	$(".async_video_button").hide();
	$(".async_category_button").hide();
	
	$(".daoru_bx").hide();
	$("#searchTable").hide();
	
	$("#ques_enable").hide();
	$("#ques_disable").hide();

	$("#video_enable").hide();
	$("#video_disable").hide();
	
}
function showCase(){
	$("#videoTable_wrapper").hide();
	$("#pptTable_wrapper").hide();
	$("#caseTable_wrapper").show();
	$("#quesTable_wrapper").hide();
	
	$(".add_video_button").hide();
	$(".add_ppt_button").hide();
	if($("#currentNodeLevel").val()==4){
		$(".add_case_button").show();
	}else{
		$(".add_case_button").hide();
	}
	$(".add_ques_button").hide();
	
	$(".async_video_button").hide();
	$(".async_category_button").hide();
	$(".dele_bx").hide();
	$(".dele_bx_ppt").hide();
	$(".dele_bx_ques").hide();
	$(".dele_bx_case").show();
	
	$(".async_video_button").hide();
	$(".async_category_button").hide();
	
	$(".daoru_bx").hide();
	$("#searchTable").hide();
	
	$("#ques_enable").hide();
	$("#ques_disable").hide();

	$("#video_enable").hide();
	$("#video_disable").hide();
}

function showQuestion(){
	$("#videoTable_wrapper").hide();
	$("#pptTable_wrapper").hide();
	$("#caseTable_wrapper").hide();
	$("#quesTable_wrapper").show();
	
	$(".add_video_button").hide();
	$(".add_ppt_button").hide();
	$(".add_ques_button").show();
/*	if($("#currentNodeLevel").val()==4){
		$(".add_ques_button").show();
	}else{
		$(".add_ques_button").hide();
	}*/
	$(".add_case_button").hide();
	
	$(".async_video_button").hide();
	$(".async_category_button").hide();
	$(".dele_bx").hide();
	$(".dele_bx_ppt").hide();
	$(".dele_bx_case").hide();
	$(".dele_bx_ques").show();
	
	$(".async_video_button").hide();
	$(".async_category_button").hide();
	
	$(".daoru_bx").show();
	$("#searchTable").show();
	
	$("#video_enable").hide();
	$("#video_disable").hide();
	
	$("#ques_enable").show();
	$("#ques_disable").show();
}

function toEditQues(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = quesTable.fnGetData(oo);
	var id = aData.id;
	turnPage(basePath+'/home#question/toEdit?id='+id);
}

//题目预览
function previewDialog(obj){
	//获取对象
	var oo = $(obj).parent().parent().parent();
	var question = quesTable.fnGetData(oo);
/*	ajaxRequest("ksystem/findKSystemById",{"id":question.ksystemId},function(res){
		if(res != null){
			//课程名
			$("#ksystemName").text($("#courseName").val());
		}else{
			//如果没有值的时候就用 -- 表示
			$("#ksystem_name").text("--");
		}
		
	})*/;
	//debugger;
	//课程名
	$("#ksystemName").text($("#courseName").val());
	//知识点名称
	ajaxRequest("question/getKnowledgePointsName",{"id":question.id},function(res){
		var knowledgeNames = '';
		if (res != null) {
			for (var i=0; i < res.length; i++) {
				if (res[i] != null) {
					knowledgeNames += res[i] + ",";
				}
			}
			knowledgeNames = knowledgeNames.substring(0,knowledgeNames.length-1);
			$("#knowledgeName").text(knowledgeNames);
		}
	});
	
		
	var typeText = '';
	$("#questionDifficulty").text(question.difficulty);
	//题型
	if("A" == question.difficulty){
		$("#questionDifficulty").text('简单');
	}else if("B" == question.difficulty){
		$("#questionDifficulty").text('一般');
	}else if("C" == question.difficulty){
		$("#questionDifficulty").text('困难');
	}else if("D" == question.difficulty){
		$("#questionDifficulty").text('非常困难');
	}
	
	if (question.qKeyword != '' && question.qKeyword != null) {
		$("#qKeyword").text(question.qKeyword);
	} else {
		$("#qKeyword").text("--");
	}
	
	//题型
	if("0" == question.questionType){
		typeText = '单选题';
	}else if("1" == question.questionType){
		typeText = '多选题';
	}else if("2" == question.questionType){
		typeText = '判断题';
	}else if("3" == question.questionType){
		typeText = '填空题';
	}else if("4" == question.questionType){
		typeText = '简答题';
	}else if("5" == question.questionType){
		typeText = '代码题';
	}else if("6" == question.questionType){
		typeText = '实操题';
	}
	$("#previewDialog #questionType").text(typeText);
	var content1 = '', content2 = '', content3 = '';
	content1 += question.questionHead + '</br>';
	
	var options = eval(question.options);
	$(".panel-body").find('ul').find('li a:first').tab("show");
	if (question.questionType == '0') {
		var map=new Map();
		
		ajaxRequest("question/getQopList",{"questionId":question.id,"questionType":null,"optionName":null},function(res){
			if (res != null) {
				for (var k=0; k < res.length; k++) {
					map.put(res[k].optionName,res[k].attachmentId);
				}
				for (var i=0; i<options.length; i++) {
					content1 += OptionIndex[i] + ' ' + 
								$("<a></a>").text(options[i]).html() + 
								'</br>';
					if (map.get(OptionIndex[i]) != null) {
						content1 += '<img src="'+map.get(OptionIndex[i])+'" alt="..." >';
						content1 += '</br>';
					}
					if (i == eval(question.answer)) {
						content2 += OptionIndex[i];
					} 
				}
				//题干
				$("#home-pills").html(content1);
				//答案
				$("#home-pis").html(content2.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/[\r\n]/g,""));
			}
		});
	}
	
	if (question.questionType == '1') {
		var map=new Map();
		ajaxRequest("question/getQopList",{"questionId":question.id,"questionType":null,"optionName":null},function(res){
			if (res != null) {
				for (var k=0; k < res.length; k++) {
					map.put(res[k].optionName,res[k].attachmentId);
				}
				for (var i=0; i<options.length; i++) {
					content1 += OptionIndex[i] + ' ' + $("<a></a>").text(options[i]).html()  + '</br>';
					if (map.get(OptionIndex[i]) != null) {
						content1 += '<img src="'+map.get(OptionIndex[i])+'" alt="..." >';
						content1 += '</br>';
					}
					var answer = eval(question.answer);
					if (isContained(i,answer)) {
						content2 += OptionIndex[i] + ',';
					}
				}
				content2 = content2.substring(0,content2.length-1);
				//题干
				$("#home-pills").html(content1);
				//答案
				$("#home-pis").html(content2.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/[\r\n]/g,""));
			}
		});
	}
	if (question.questionType == '2' || question.questionType == '4') {
		content2 += question.answer;
	}

	if (question.questionType == '3') {
		//debugger
		content2 += eval(question.answerText.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/[\r\n]/g,""));
	}
	
	if (question.questionType == '5' || question.questionType == '6') {
		ajaxRequest("question/getAttachment",{"id":question.answer},function(res){
			
			if (res != null && res != '') {
				content2 += '<a href="question/questionCodeAttachmentDownload?id='+res.id+'&fileName='+res.orgFileName+'">'+res.orgFileName+'</a>';
			}
			$("#home-pis").html(content2);
		});
	}

	content3 = question.solution;
	
	//题干
	$("#home-pills").html(content1);
	//答案
	if ("6" == question.questionType) {
		$("#home-pis-head").html("素材");
		$("#home_solution_head").html("答案说明");
	} else {
		$("#home-pis-head").html("答案");
		$("#home_solution_head").html("答案说明");
	}
	
	$("#home-pis").html(content2.replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/[\r\n]/g,""));
	//解析
	if (content3 != null && content3 != '' && content3 != undefined) {
		$("#home_solution").html(content3);
	} else {
		$("#home_solution").html("");
	}
	
	//弹出层
	var dialog = openDialog("previewDialog","previewQuestionDiv","查看试题",730,460,false,"",function(){
		
	});
    dialog.find("#close").click(function(){
    	dialog.dialog("close");
	});
}
//单条题目删除
function delOneQues(obj){
	var oo = $(obj).parent().parent().parent();
	var aData = quesTable.fnGetData(oo);
	showDelDialog(function(){
		ajaxRequest("question/delete",{"id":aData.id,"chapterIds":$("#search_chapterIds").val()},function(res){
			alertMsg(res.errorMessage);
			freshDelTable(quesTable);
		});
	},"删除题目","确定删除该题目？");
}

function impStu(){
	$("#otherFile").html('<div style="display: none;"><label class="col-sm-3 control-label no-padding-right" style="margin-top:7px;"><span style="float:right">题附件(可选)：</span></label><div class="col-sm-9"><div id="ziprar-attachments"></div></div></div>');
	$("#ziprar-attachments").html("");
	$("#ziprar-attachments").html('<input type="file" name="zipraratt" id="zipraratt" />');
	
	openExcel(function(fileName){
		ajaxRequest(basePath+"/question/saveBatch",{"fileName":fileName,"courseName":$("#courseName").val()},function(res){
			$("#xlsuploading").dialog("close");
			$("#xlsupload").dialog("close");
			if(isnull(res)){
				//loadSearchData();
				freshTable(quesTable);
				alertMsg("导入成功！")
			}else{
				openError(res);
			}
		});
	},function(){
		//先上传附件   1、判断格式 2、上传
		var filepath = $("#zipraratt").val();
		if (isnull(filepath)) {
			return true;
		}
		filepath = filepath.substring(filepath.lastIndexOf(".") + 1,filepath.length);
		filepath = filepath.toLowerCase();
		if (filepath == "zip") {
			var fileSize=fileChangeGetSize(zipraratt)
			if(fileSize>100){
				alertMsg("附件大于100M,请重新选择.");
				return false;
			}else{
				return true;
			}
		} else {
			alertMsg("题附件只能上传后缀名为:zip的文件");
			return false;
		}
		
	},240);
}
