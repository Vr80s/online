$(function() {
	getBarriersSelect();
	addBarrierDetailform = $("#addBarrierDetail-form").validate({
        messages: {
        	name: {
				required:"请输入关卡名称！",
				maxlength:"关卡名称过长，应小于60个字符！"
            },
            parentId: {
				required:"请选择上一阶段关卡！"
			},
			limitTime: {
				required:"请输入时长！",
				range:"请输入1-500整数！",
				digits:"请输入整数！"
			},
			totalScore: {
				required:"请输入分数！",
				range:"请输入1-500整数！",
				digits:"请输入整数！"
			},
			passScorePercent: {
				required:"请输入通关标准！",
				range:"请输入1-500整数！",
				digits:"请输入整数！"
			},
			kpointIds: {
				required:"请选择知识点范围！",
			}
        }
    });

	$.validator.addMethod(
		"lessThanTotal", //验证方法名称
		function(value, element, param) {//验证规则
			
			if(Number(value)>Number($("#totalScore").val())){
				return false;
			}
			
			return true;
		}, 
		'通关标准不能大于总分！'//验证提示信息
	);

	$.validator.addMethod(
			"lessThanTotalQuestion", //验证方法名称
			function(value, element, param) {//验证规则
				if(Number(value) > Number($(element).parent().parent().find("span").eq(0).text())){
					return false;
				}
				return true;
			}, 
			'可用题不足！'//验证提示信息
	);
	
	$.validator.addMethod(
			"equal0", //验证方法名称
			function(value, element, param) {//验证规则
				if(Number($("#totalScoreSpan").text())==0)
				{
					return true;
				}
				if(Number(value) != 0 && Number(value) > 0){
					return false;
				}
				
				return true;
			}, 
			'还有分数未分配！'//验证提示信息
	);
	
	$.validator.addMethod(
			"equalTotalScore", //验证方法名称
			function(value, element, param) {//验证规则
				if(Number(value) < 0){
					return false;
				}
				
				return true;
			}, 
			'已超过总分数！'//验证提示信息
	);
	
	$.validator.addMethod(
			"syncRequired", //验证方法名称
			function(value, element, param) {//验证规则
				if(Number($(element).parent().parent().find("[name='strategyTotalScore']").eq(0).val()) > 0){
					if(Number(value) == 0 || value == ""){
						return false;
					}
				}
				return true;
			}, 
			'题数不能为0！'//验证提示信息
	);
	
	$("#name").on("keyup change",function(){
		var arr = $(this).val().split("");
		$("#wordCnt").text(arr.length);
	});
	
	$("#totalScore").on("keyup change",function(){
		if(isNaN($(this).val())){
			$(this).val(0);
		}
		if($(this).val()>0 && $(this).val()<=500){//在合法范围内生效
			$("#totalScoreSpan").text($(this).val());
			changeInfo($("#radioQuestionCnt"));//$("#radioQuestionCnt") 随便选了下面分配题的一个文本框 因为这个方法传哪个文本框都一样
		}
	});
	
	$("#passScorePercent").on("keyup change",function(){
		if(isNaN($(this).val())){
			$(this).val(0);
		}
	});
	
	$("#radioQuestionCnt,#radioQuestionCnt_totalScore,#multiselectQuestionCnt,#multiselectQuestionCnt_totalScore,#nonQuestionCnt,#nonQuestionCnt_totalScore").on("keyup change",function(event){
		if(isNaN($(this).val())){
			$(this).val(0);
		}
		var totalQuestion = Number($(this).parent().parent().find("[type='text']").eq(0).val());
		var totalScore = Number($(this).parent().parent().find("[type='text']").eq(1).val());
		//本级联动
		if(totalQuestion !=0 && totalScore != 0){
			$(this).parent().parent().find("[type='text']").eq(2).val(totalScore/totalQuestion);			
		}else if(totalQuestion==0 || totalScore==0){
			$(this).parent().parent().find("[type='text']").eq(2).val(0);
		}

		//负数时的处理
		if(totalQuestion < 0){
			$(this).parent().parent().find("[type='text']").eq(0).val(0);
			$(this).parent().parent().find("[type='text']").eq(2).val(0);
		}
		if(totalScore < 0){
			$(this).parent().parent().find("[type='text']").eq(1).val(0);
			$(this).parent().parent().find("[type='text']").eq(2).val(0);
		}
		
		changeInfo(this);
	});

	$(".save_bx").on("click",function(){
		if($("#addBarrierDetail-form").valid()){
			mask();
			$("#addBarrierDetail-form").attr("action", basePath+"/barrier/barrier/addBarrier");
	        $("#addBarrierDetail-form").ajaxSubmit(function(data){
	        	try{
	        		data = jQuery.parseJSON(jQuery(data).text());
	        	}catch(e) {
	        		data = data;
	        	}
	        	unmask();
	            if(data.success){
	                layer.msg(data.resultObject);
	                //跳转回上个页面
	                window.location.href=basePath+'/home#/barrier/barrier/barrierDetail?courseId='+$("#courseId").val()+'&courseName='+$("#courseName").val();
	            }else{
	            	layer.msg(data.errorMessage);
	            	
	            }
	        });
		}
	})

	$("#selKpoint,#selKpoint1").on("click",function(){
	  //重新根据已经选择的构建树
	  var arrNodes = $("#kpointIds").val().split(",");
	  for(var i = 0;i<arrNodes.length;i++){
		  var node1 = Tree1.getNodesByParam("id",arrNodes[i],null);
		  
		  if(node1.length > 0){
			  node1[0].checked = true;
			  Tree1.updateNode(node1[0]);
			  node1[0].getParentNode().checked = true;
			  Tree1.updateNode(node1[0].getParentNode());
			  node1[0].getParentNode().getParentNode().checked = true;
			  Tree1.updateNode(node1[0].getParentNode().getParentNode());
			  node1[0].getParentNode().getParentNode().getParentNode().checked = true;
			  Tree1.updateNode(node1[0].getParentNode().getParentNode().getParentNode());
		  }
	  }
	  
	  if(Tree2 == null){
		  $("#getTree").append('<ul id="treeDemo_'+$("#courseId").val()+'" class="ztree" style="font-size: 13px; font-weight: bold;width: 250px;"></ul>');
		  Tree2 = loadTree2("treeDemo_"+$("#courseId").val(),null);
	  }

	  moveTreeNode(Tree1, Tree2);
	  
	  var dialog = openDialog("kpointDialog","dialogKpointDiv","选择知识点",600,550,true,"确定",function(){
			Tree3 = $.fn.zTree.getZTreeObj("treeDemo_"+$("#courseId").val());
			var arrNames = new Array();
			var arrIds = new Array();
			if(!isnull(Tree3)){
				//获取到  getTree 所有 知识点层级的节点  把name显示到 selectionKpoints中
				//把ID 存放在  kpointIds中
				var nodes = Tree3.transformToArray(Tree3.getNodes());
				for(var i=0;i<nodes.length;i++){
					if(nodes[i].level==3){//如果是知识点层就保存
						arrNames.push(nodes[i].name);
						arrIds.push(nodes[i].id);
					}
				}
			}
			$("#selectionKpoints").html(arrNames.join(","));
			$("#selectionKpoints").attr("title",arrNames.join(","));
			$("#kpointIds").val(arrIds.join(","));
			//然后根据已经选择的知识点 从后台查询出对应的题型及数量   回头写 
			ajaxRequest(basePath + "/barrier/barrier/getQuestions",{"kpointIds":$("#kpointIds").val()},function(data){
				//造假数据
				$("#radioQuestionCnt_span").text(0);
				$("#multiselectQuestionCnt_span").text(0);
				$("#nonQuestionCnt_span").text(0);
				if(data.success){
					if(data.resultObject != null){
						var results = data.resultObject;
						for(var i=0;i<results.length;i++){
							//0单选、1多选、2判断、
							if(results[i].questionType == 0){
								$("#radioQuestionCnt_span").text(results[i].cnt);
								$("#radioQuestionCnt").val(0);
							}else if(results[i].questionType == 1){
								$("#multiselectQuestionCnt_span").text(results[i].cnt);
								$("#multiselectQuestionCnt").val(0);
							}else if(results[i].questionType == 2){
								$("#nonQuestionCnt_span").text(results[i].cnt);
								$("#nonQuestionCnt").val(0);
							}
						}
					}
				}else{
					alert("加载可用题数量失败，请重新加载！");
				}

			});
			
			dialog.dialog( "close" );
			
			//如果已经选择了 就把错误提示隐藏掉
			if($("#kpointIds").val() != null && $("#kpointIds").val() != ''){
				$("#kpointIds").parent().find("label").hide();
			}
			showSelKpointsBtn();
		});
	})
	
	$("#getTree").append('<ul id="treeDemo_'+$("#courseId").val()+'" class="ztree" style="font-size: 13px; font-weight: bold;width: 250px;"></ul>');
	Tree2 = loadTree2("treeDemo_"+$("#courseId").val(),null);
	
	//加载选择资源的树节点
	loadTreeAll("knowledge",basePath+"/cloudclass/videores/showTreeBarrierAdd",{"courseId":$("#courseId").val(),"courseName":$("#courseName").val()});
	
});
	
	function loadTreeAll(objId,url,data){
		ajaxRequest(url,data,function(zNodes){
			Tree1 = loadTree(objId,zNodes);//初始化选择知识点
			var arrKpoint = $("#kpointIds").val().split(",");
			
			for(var i=0;i<arrKpoint.length;i++){
				var oNode = Tree1.getNodeByParam("id", arrKpoint[i], null);
				if(oNode!= null && !oNode.isParent){
//					Tree1.setChkDisabled(oNode, false);
					if(oNode.chkDisabled){//如果该节点已经被选  那么就清空在可选知识点范围
						 $("#kpointIds").val("");
						 $("#selectionKpoints").text("");
					}

					Tree1.checkNode(oNode, true, true);
					zTreeOnCheck(null,objId,oNode);

				}
			}

			//遍历所有的0、1、2 级节点 如果不可以用 全部禁用
			var nodesAll = Tree1.transformToArray(Tree1.getNodes());

			for(var i=0;i<nodesAll.length;i++){
				var tempNode = nodesAll[i];
				if(tempNode.level == 2){
					//查询出该父节点下是否有 不为禁用的节点 如果有 就可选 否则就不可选
					var tempNodes = Tree1.getNodesByParam("chkDisabled",false,tempNode);
					if(tempNodes.length == 0){
						Tree1.setChkDisabled(nodesAll[i],true);
					}
				}
			}
			
			nodesAll = Tree1.transformToArray(Tree1.getNodes());
			for(var i=0;i<nodesAll.length;i++){
				var tempNode = nodesAll[i];
				if(tempNode.level == 1){
					//查询出该父节点下是否有 不为禁用的节点 如果有 就可选 否则就不可选
					var tempNodes = Tree1.getNodesByParam("chkDisabled",false,tempNode);
					if(tempNodes.length == 0){
						Tree1.setChkDisabled(nodesAll[i],true);
					}
				}
			}
			
			nodesAll = Tree1.transformToArray(Tree1.getNodes());
			for(var i=0;i<nodesAll.length;i++){
				var tempNode = nodesAll[i];
				if(tempNode.level == 0){
					//查询出该父节点下是否有 不为禁用的节点 如果有 就可选 否则就不可选
					var tempNodes = Tree1.getNodesByParam("chkDisabled",false,tempNode);
					if(tempNodes.length == 0){
						Tree1.setChkDisabled(nodesAll[i],true);
					}
				}
			}

			//然后根据已经选择的知识点 从后台查询出对应的题型及数量   回头写 
			ajaxRequest(basePath + "/barrier/barrier/getQuestions",{"kpointIds":$("#kpointIds").val()},function(data){
				//造假数据
				$("#radioQuestionCnt_span").text(0);
				$("#multiselectQuestionCnt_span").text(0);
				$("#nonQuestionCnt_span").text(0);
				if(data.success){
					if(data.resultObject != null){
						var results = data.resultObject;
						for(var i=0;i<results.length;i++){
							//0单选、1多选、2判断、
							if(results[i].questionType == 0){
								$("#radioQuestionCnt_span").text(results[i].cnt);
								$("#radioQuestionCnt").val(0);
							}else if(results[i].questionType == 1){
								$("#multiselectQuestionCnt_span").text(results[i].cnt);
								$("#multiselectQuestionCnt").val(0);
							}else if(results[i].questionType == 2){
								$("#nonQuestionCnt_span").text(results[i].cnt);
								$("#nonQuestionCnt").val(0);
							}
						}
					}
				}else{
					alert("加载可用题数量失败，请重新加载！");
				}
			});
			showSelKpointsBtn();
		});
	}

	function loadTree(objId,zNodes){
		var setting = {
				view: {
					selectedMulti: true,
					expandSpeed: "normal"
				},
				check: {
					enable: true,
					chkStyle: "checkbox",
					chkboxType: { "Y": "ps", "N": "ps" }
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onCheck: zTreeOnCheck
				}
			};
		return $.fn.zTree.init($("#"+objId), setting, zNodes);
	}

	function loadTree2(objId,zNodes){
		var setting = {
				view: {
					selectedMulti: false,
					expandSpeed: "normal"
				},
				check: {
					enable: false,
					chkStyle: "checkbox",
					chkboxType: { "Y": "ps", "N": "ps" }
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onCheck: zTreeOnCheck
				}
		};
		return $.fn.zTree.init($("#"+objId), setting, zNodes);
	}
	
	/**
	 * 捕获左右两边树的check事件
	 */
	function zTreeOnCheck(event, treeId, treeNode) {
		var objId = "treeDemo_"+$("#courseId").val();
		var treeUrl = $("#getTree").html();
		isUpdateClickForCourseSystem = true;
		//debugger;
	    if(treeNode.checked){//选中
	    	if(treeUrl.indexOf(objId)==-1){
	    		$("#getTree").append('<ul id="'+objId+'" class="ztree" style="font-size: 13px; font-weight: bold;width: 250px;"></ul>');
	    		Tree2 = loadTree2(objId,null);
	    	}else{
	    		Tree2 = $.fn.zTree.getZTreeObj(objId);
	    	}
	    	if(treeNode.isParent){
	    		mask();
	    		setTimeout(function(){
	    			moveTreeNode(Tree1, Tree2);
	        		unmask();
	    		},50);
	    	}else if(treeNode.type=="4"){
	    		moveTreeNode(Tree1, Tree2);
	    	}
	    }else{//取消选中
    		var pNode = Tree1.getNodeByParam("id", treeNode.pId, null);//获取当前节点的父节点
        	if(!isnull(pNode)){
        		if(!pNode.checked){//判断父节点是否取消
        			var selects = Tree1.getCheckedNodes(true);
        		    if(selects.length==0){
        		    	deleteParentNode(Tree1,pNode);//删除父节点
        		    }else{
        		    	if(pNode.type=="3"){
        		    		var parentNode = Tree1.getNodeByParam("id", pNode.pId, null);//获取父节点的父节点
        		    		if(!isnull(parentNode) && !parentNode.checked){//父父节点取消
        		    			deleteTreeNode(Tree2,parentNode);//删除父父节点
        		    		}
        		    	}
        		    	deleteTreeNode(Tree2,pNode);//删除当前节点的父节点
        		    }
            	}
        	}
	    	deleteTreeNode(Tree2,treeNode);//删除当前节点
	    }
	}
	
	function changeInfo(obj){
		//处理完后 获取三组总分的得分 计算已经分配的分数
		var totalScore1 = Number($(obj).parent().parent().parent().find("[name='strategyTotalScore']").eq(0).val());
		var totalScore4 = Number($(obj).parent().parent().parent().find("[name='strategyTotalScore']").eq(1).val());
		var totalScore7 = Number($(obj).parent().parent().parent().find("[name='strategyTotalScore']").eq(2).val());
		var totalScore = Number($("#totalScore").val());
		if(isNaN(totalScore1)){
			totalScore1 = 0;
		}
		
		if(isNaN(totalScore4)){
			totalScore4 = 0;
		}
		
		if(isNaN(totalScore7)){
			totalScore7 = 0;
		}
		
		if(isNaN(totalScore)){
			totalScore = 0;
		}
		
		//得分
		$("#distributionScore").text(totalScore1 + totalScore4 + totalScore7);
		$("#unDistributionScore").text(totalScore - (totalScore1 + totalScore4 + totalScore7));
		$("#unDistributionScore_hid").val(totalScore - (totalScore1 + totalScore4 + totalScore7));
	}

	//获取到下拉框
	function getBarriersSelect(){
		ajaxRequest(basePath+"/barrier/barrier/getBarriersSelect",{"courseId":$("#courseId").val()},function(data){
			if(data.success){
				//获取到结果添加到下拉框中
				var results = data.resultObject;
				for(var i = 0;i<results.length;i++){
					$("#parentId").append( "<option value=\""+results[i].id+"\">"+results[i].name+"</option>" );
				}
			}else{
				alert("加载上一阶段关卡失败！");
			}
		});
	}
	
	$('#returnButton').on('click',function(){
		window.location.href=basePath+'/home#/barrier/barrier/barrierDetail?courseId='+$("#courseId").val()+'&courseName='+$("#courseName").val();
	});
	
	function showSelKpointsBtn(){
		if($("#selectionKpoints").text() == "" || $("#selectionKpoints").text() == null){
			$("#selKpoint1").show();
			$("#selKpoint").parent().hide();
		}else{
			$("#selKpoint1").hide();
			$("#selKpoint").parent().show();
		}
		$("#selectionKpoints").show();
	}