
var currentNode;

/**
 * 简单的选择树
 * @param objId 对象的ID
 * @param url 请求的后台地址
 * @param callback 成功后的回调方法
 * @param onClickTree 左键点击方法
 * @returns
 */
function loadSimpleTree(objId,url,callback,onClickTree){
	var setting = {
		view: {
			selectedMulti: true,
			expandSpeed: "normal"
		},
		check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType: { "Y": "ps", "N": "s" }
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		async: {
			enable: true,
			url:url,
			autoParam:["id"]
		},
		callback: {
			onClick: function(event, treeId, treeNode) {
				if(!isnull(onClickTree)){
					onClickTree(treeNode);
				}
			}
		}
	};
	return $.fn.zTree.init($("#"+objId), setting);
}
/**
 * 普通树显示只有 左键点击方法
 * @param objId  对象的ID
 * @param url    请求的后台地址
 * @param callback  成功后的回调方法
 * @param onClickTree  左键点击方法
 * @returns
 */
function loadTreeGenerally(objId,url,callback,onClickTree){
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
			async: {
				enable: true,
				url:url,
				autoParam:["id","type"]
			},
			callback: {
				onAsyncSuccess: function(event, treeId, treeNode, msg){
					layer.closeAll();
					if(!isnull(callback)){
						callback(treeNode);
					}
				},
				onClick: function(event, treeId, treeNode) {
				    if(!isnull(onClickTree)){
				    	onClickTree(treeNode);
				    }
				}
			}
		};
	return $.fn.zTree.init($("#"+objId), setting);
}



/**
 * 
 * @param objId  对象的ID
 * @param url    请求的后台地址
 * @param callback  成功后的回调方法
 * @param addTree   右键添加方法
 * @param updateTree  右键修改方法
 * @param deleteTree  右键删除方法
 * @param onClickTree  左键点击方法
 * @param zTreeBeforeDrop 排序拖拽释放之后执行 
 * @returns
 */
function loadTreeDynomic(objId,url,callback,addTree,updateTree,deleteTree,copyTree,onClickTree,zTreeBeforeDrop){
	var setting = {
			dragId: '',//当前拖拽父id 
			view: {
				selectedMulti: true,
				expandSpeed: "normal"
			},
			edit: {
				drag : {
					isCopy : false,
					isMove : true,
					prev : true,
					next : true,
					inner : false,
				},
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false
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
			async: {
				enable: true,
				url:url,
				autoParam:["id","type"]
			},
			callback: {
				//拖拽前执行 
				beforeDrag: function(treeId, treeNodes) {
					for (var i=0,l=treeNodes.length; i<l; i++) {
						setting.dragId = treeNodes[i].pId;  
						if (treeNodes[i].drag === false) {
							return false;
						}
					}
					return true;
				},
				//拖拽释放之后执行 
				beforeDrop: function(treeId, treeNodes, targetNode, moveType) {
					if(treeNodes[0].level == targetNode.level&&treeNodes[0].pId == targetNode.pId){
						   var data = {id:treeNodes[0].id,targetId:targetNode.id,targetPId:targetNode.pId,moveType:moveType};  
						   if(!isnull(zTreeBeforeDrop)){
							   return zTreeBeforeDrop(data);
							}
							return false;
					 }else{  
						 alert('亲，只能进行同级排序！');  
						 return false;  
					}  	
				},
				onAsyncSuccess: function(event, treeId, treeNode, msg){
					//debugger;
					layer.closeAll();
					if(!isnull(callback)){
						callback(treeNode);
					}
				},
				onRightClick: function(event, treeId, treeNode){
					currentNode = treeNode;
					var mouseX = event.screenX+10;
					var mouseY = event.clientY;
					var myMenu1 = $('#myMenu1');
					myMenu1.show();
					myMenu1.css("left", mouseX);
					myMenu1.css("top", mouseY);
					if(treeNode.type=="1"||treeNode.contenttype=="1"){
						$("#addTree").show();
						$("#mAdd").html("添加章");
						$("#copyTree").show();
						$("#mCopy").html("拷贝章");
						$("#updateTree").hide();
						$("#deleteTree").hide();
					}else if(treeNode.type=="2"||treeNode.contenttype=="2"){
						$("#addTree").show();
						$("#mAdd").html("添加节");
						$("#copyTree").show();
						$("#mCopy").html("拷贝节");
						$("#updateTree").show();
						$("#mEdit").html("修改章");
						$("#deleteTree").show();
						$("#mDel").html("删除章");
					}else if(treeNode.type=="3"||treeNode.contenttype=="3"){
						$("#addTree").show();
						$("#mAdd").html("添加知识点");
						$("#copyTree").show();
						$("#mCopy").html("拷贝知识点");
						$("#updateTree").show();
						$("#mEdit").html("修改节");
						$("#deleteTree").show();
						$("#mDel").html("删除节");
					}else if(treeNode.type=="4"||treeNode.contenttype=="4"){
						$("#addTree").hide();
						$("#copyTree").hide();
						$("#updateTree").show();
						$("#mEdit").html("修改知识点");
						$("#deleteTree").show();
						$("#mDel").html("删除知识点");
					}else{
						myMenu1.hide();
					}
					$('#addTree').unbind("click");
					$("#addTree").click(function(){
						if(!isnull(addTree)){
							addTree(currentNode);
						}
					});
					$('#updateTree').unbind("click");
					$("#updateTree").click(function(){
						if(!isnull(updateTree)){
							updateTree(currentNode);
						}
					});
					$('#deleteTree').unbind("click");
					$("#deleteTree").click(function(){
						if(!isnull(deleteTree)){
							deleteTree(currentNode);
						}
					});
					$('#copyTree').unbind("click");
					$("#copyTree").click(function(){
						if(!isnull(copyTree)){
							copyTree(currentNode);
						}
					});
				},
				onClick: function(event, treeId, treeNode) {
				    if(!isnull(onClickTree)){
				    	onClickTree(treeNode);
				    }
				}
			}
		};
	return $.fn.zTree.init($("#"+objId), setting);
}



/**
 * 
 * @param objId  对象的ID
 * @param url    请求的后台地址
 * @param callback  成功后的回调方法
 * @param addTree   右键添加方法
 * @param updateTree  右键修改方法
 * @param deleteTree  右键删除方法
 * @param onClickTree  左键点击方法
 * @param zTreeBeforeDrop 排序拖拽释放之后执行 
 * @returns
 */
function loadTreeDynomicBarrier(objId,url,callback,addTree,updateTree,deleteTree,copyTree,onClickTree,zTreeBeforeDrop){
	function setFontCss(treeId, treeNode) {
		return treeNode.contenttype == 5 ? {"background-color": "#2c6aa0","color":"white"} : {};
	};

	var setting = {
			dragId: '',//当前拖拽父id 
			view: {
				selectedMulti: true,
				expandSpeed: "normal",
				fontCss:setFontCss
			},
			edit: {
				drag : {
					isCopy : false,
					isMove : true,
					prev : true,
					next : true,
					inner : false,
				},
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false
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
			async: {
				enable: true,
				url:url,
				autoParam:["id","type"]
			},
			callback: {
				//拖拽前执行 
				beforeDrag: function(treeId, treeNodes) {
					for (var i=0,l=treeNodes.length; i<l; i++) {
						setting.dragId = treeNodes[i].pId;  
						if (treeNodes[i].drag === false) {
							return false;
						}
					}
					return true;
				},
				//拖拽释放之后执行 
				beforeDrop: function(treeId, treeNodes, targetNode, moveType) {
					if(treeNodes[0].level == targetNode.level){
						   var data = {id:treeNodes[0].id,targetId:targetNode.id,targetPId:targetNode.pId,moveType:moveType};  
						   if(!isnull(zTreeBeforeDrop)){
							   return zTreeBeforeDrop(data);
							}
							return false;
					 }else{  
						 alert('亲，只能进行同级排序！');  
						 return false;  
					}  	
				},
				onAsyncSuccess: function(event, treeId, treeNode, msg){
					//debugger;
					layer.closeAll();
					if(!isnull(callback)){
						callback(treeNode);
					}
				},
				onRightClick: function(event, treeId, treeNode){
					currentNode = treeNode;
					var mouseX = event.screenX+10;
					var mouseY = event.clientY;
					var myMenu1 = $('#myMenu1');
					myMenu1.show();
					myMenu1.css("left", mouseX);
					myMenu1.css("top", mouseY);
					if(treeNode.type=="1"||treeNode.contenttype=="1"){
//						$("#addTree").show();
//						$("#mAdd").html("添加章");
//						$("#copyTree").show();
//						$("#mCopy").html("拷贝章");
//						$("#updateTree").hide();
//						$("#deleteTree").hide();
						myMenu1.hide();
					}else if(treeNode.type=="2"||treeNode.contenttype=="2"){
//						$("#addTree").show();
//						$("#mAdd").html("添加节");
//						$("#copyTree").show();
//						$("#mCopy").html("拷贝节");
//						$("#updateTree").show();
//						$("#mEdit").html("修改章");
//						$("#deleteTree").show();
//						$("#mDel").html("删除章");
						myMenu1.hide();
					}else if(treeNode.type=="3"||treeNode.contenttype=="3"){
//						$("#addTree").show();
//						$("#mAdd").html("添加知识点");
//						$("#copyTree").show();
//						$("#mCopy").html("拷贝知识点");
//						$("#updateTree").show();
//						$("#mEdit").html("修改节");
//						$("#deleteTree").show();
//						$("#mDel").html("删除节");
						myMenu1.hide();
					}else if(treeNode.type=="4"||treeNode.contenttype=="4"){
						
						$("#addTree").show();
						$("#copyTree").hide();
						$("#updateTree").hide();
						if(treeNode.contenttype == 5||(treeNode.getNextNode() != null && treeNode.getNextNode().contenttype == 5)){
							$("#mAdd").html("修改闯关关卡");
						}else{
							$("#mAdd").html("设置闯关关卡");
						}
						$("#deleteTree").hide();
						$("#mDel").html("删除知识点");
					}else{
						myMenu1.hide();
					}
					$('#addTree').unbind("click");
					$("#addTree").click(function(){
						if(!isnull(addTree)){
							addTree(currentNode);
						}
					});
				},
				onClick: function(event, treeId, treeNode) {
				    if(!isnull(onClickTree)){
				    	onClickTree(treeNode);
				    }
				}
			}
		};
	return $.fn.zTree.init($("#"+objId), setting);
}


$(function(){
	$(document).bind("click", function(e) {
		$('#myMenu1').hide();
	});
});


var Tree1,Tree2;
function deleteParentNode(treeObj,pNode){
	if(!isnull(pNode)){//父节点是否为空
		deleteTreeNode(Tree2,pNode);//删除父节点
		var node = treeObj.getNodeByParam("id", pNode.pId, null);//获取父节点
		deleteParentNode(treeObj,node);
	}
}

function deleteTreeNode(zTree,node){
	var oNode = zTree.getNodeByParam("id", node.id, null);
	if(!isnull(oNode)){
		loopChildNode(zTree,oNode);
	}
}

function loopChildNode(zTree,node){
	var strs = {};
	var scode;
	if(node.isParent){
		var nodes = zTree.getNodesByParam("pId", node.id, null);
		var oNode;
		for(var i=0;i<nodes.length;i++){
			oNode = nodes[i];
			if(oNode.isParent){
				loopChildNode(zTree,oNode);
			}else{
				strs={};
				strs.id =oNode.id;
				strs.name=oNode.name;
				strs.pId = oNode.pId;
				strs.nodes = new Array();
				scode = strs.id;
				zTreeDataDelete(oNode,scode,zTree);
			}
		}
		strs={};
		strs.id =node.id;
		strs.name=node.name;
		strs.pId = node.pId;
		strs.nodes = new Array();
		scode = strs.id;
		zTreeDataDelete(node,scode,zTree);//移除父节点
	}else{
		strs.id =node.id;
		strs.name=node.name;
		strs.pId = node.pId;
		strs.nodes = new Array();
		scode = strs.id;
		zTreeDataDelete(node,scode,zTree);//只有一个子节点的情况
	}
}

function moveTreeNode(zTree1, zTree2){
	var nodes = zTree1.getCheckedNodes();	//获取选中需要移动的数据
	//console.log(nodes);
	//console.log(nodes.length);
	//debugger;
	for(var i=0;i<nodes.length;i++){		//把选中的数据从根开始一条一条往右添加
		var node = nodes[i];
		/*if(!node.isParent && node.type!="4"){
			continue;
		}else if(node.isParent && node.type=="2"){
			var cNode = Tree1.getNodeByParam("pId", node.id, null);//获取当前节点下的子节点
			console.log("当前节点:"+node+"----当前节点下的子节点:"+cNode);
			if(!isnull(cNode) && !cNode.isParent){
				continue;
			}
		}*/
		var strs={};			//新建一个JSON 格式数据,表示为一个节点,可以是根也可以是叶
		strs.id =node.id;
		strs.name=node.name;
		strs.pId = node.pId;
		strs.nodes = new Array();	//树节点里面有个 nodes 集合,用来存储父子之间的包涵关系
		//strs.nocheck = true;
		
		zTreeDataAddNode(strs,zTree2);
	}
		zTree2.checkAllNodes(true);
//		zTree1.checkAllNodes(false);
		
		//刷新
		zTree2.refresh();
		zTree1.refresh();
}

function moveTreeNode2(zTree1, zTree2){
	var nodes = zTree1.getCheckedNodes();	//获取选中需要移动的数据
	for(var i=0;i<nodes.length;i++){		//把选中的数据从根开始一条一条往右添加
		var node = nodes[i];
		var strs={};			//新建一个JSON 格式数据,表示为一个节点,可以是根也可以是叶
		strs.id =node.id;
		strs.name=node.name;
		strs.pId = node.pId;
		strs.nodes = new Array();	//树节点里面有个 nodes 集合,用来存储父子之间的包涵关系
		strs.nocheck = true;
		
		zTreeDataAddNode(strs,zTree2);
	}
		//刷新
		zTree2.refresh();
		zTree1.refresh();
}

//树数据移动方法
function zTreeDataAddNode(strs,zTree2){
	var nodes = zTree2.transformToArray(zTree2.getNodes());	//获取需要添加数据的树下面所有节点数据
	//如果有多个数据需要遍历,找到strs 属于那个父亲节点下面的元素.然后把自己添加进去
	if(nodes.length > 0){
		//这个循环判断是否已经存在,true表示不存在可以添加,false存在不能添加
		var isadd=true;
		for(var j=0;j<nodes.length;j++){
			if(strs.id==nodes[j].id){
				isadd=false;
				break;
			}
		}
		
		//找到父亲节点
		var scode = strs.pId;
		var i=0;
		var flag =false;
		for(i ;i<nodes.length;i++){
			if(scode ==nodes[i].id){
				flag = true;
				break;
			}
		}
		//同时满足两个条件就加进去,就是加到父亲节点下面去
		if(flag && isadd){
			var treeNode1=nodes[i];
			zTree2.addNodes(treeNode1,strs);
				
		//如果zTree2 里面找不到,也找不到父亲节点.就把自己作为一个根add进去
		}else if(isadd){
			zTree2.addNodes(null,strs);
		}
	}else{
		//树没任何数据时,第一个被加进来的元素
		zTree2.addNodes(null,strs);
	}
}

//数据移除
function zTreeDataDelete(node,scode,zTree1){
	if(node.isParent){	//判断是不是一个根节点,如果是一个根几点从叶子开始遍历寻找
		if(!isnull(zTree1)){
			var fnodes = zTree1.getNodesByParam("pId", node.id, null);
			//如果是个根就检测nodes里面是否有数据
			if(fnodes.length > 0){
				for(var x = 0; x<fnodes.length; x++){
					//不是根节点.并且code 相当就是需要移除的元素
					if(!(fnodes[x].isParent) && fnodes[x].id==scode){
						//调用ztree 的移除方法,参数是一个节点json格式数据
						zTree1.removeNode(fnodes[x]);
						//如果当前这个节点又是个根节点.开始递归
					}else if(fnodes[x].isParent){
						zTreeDataDelete(fnodes[x],scode);
					}
				}
			}else{
				//如果是个根,但是下面的元素没有了.就把这个根移除掉
				zTree1.removeNode(node);
			}
		}
	}else{
		//不是就直接移除
		zTree1.removeNode(node);
	}
}

function getAllNodes(treeObj){
	var list = new Array();
	var nodes = treeObj.getNodes();
	loopNodes(nodes,list,treeObj);
	
	return list;
}
function loopNodes(nodes,list,treeObj){
	for(var i=0;i<nodes.length;i++){
		list.push(nodes[i]);
		var nodes2 = treeObj.getNodesByParam("pId", nodes[i].id, null);
		loopNodes(nodes2,list,treeObj);
	}
	return list;
}

/**
 * 
 * @param objId   左边ID
 * @param objId2  右边ID
 * @param url
 * @param data
 * @param biaoshi 加载左边、右边树的标识
 * @param callback
 */
function initCommonZtrees(objId,objId2,url,data,biaoshi,callback){
	if(biaoshi=="left"){//生成左边树
		if(isnull(Tree2)){
			Tree2 = loadCommonTree(objId2,null);
		}
		ajaxRequest(url,data,function(zNodes){
			Tree1 = loadCommonTree(objId,zNodes);
			if(!isnull(Tree2)){//让Tree1某些节点呈选中状态
				var nodes = getAllNodes(Tree2);
				for(var i=0;i<nodes.length;i++){
					var id = nodes[i].id;
					var oNode = Tree1.getNodeByParam("id", id, null);
					if(!oNode.isParent){
						Tree1.checkNode(oNode, true, true);
					}
				}
			}
			if(!isnull(callback)){
				callback();
			}
		});
	}else if(biaoshi=="right"){//生成右边树
		ajaxRequest(url,data,function(zNodes){
			Tree2 = loadCommonTree2(objId2,zNodes);
			var allNodes = getAllNodes(Tree2);
			/*//去掉选框
			if(allNodes.length>0){
			    for(var i=0;i<allNodes.length;i++){
			       allNodes[i].nocheck=true;//nocheck为true表示没有选择框
			    }
			}*/
			if(!isnull(callback)){
				callback();
			}
		});
	}
}

function loadCommonTree(objId,zNodes){
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
				onCheck: commonCheck
			}
		};
	return $.fn.zTree.init($("#"+objId), setting, zNodes);
}

function loadCommonTree2(objId,zNodes){
	var setting = {
			view: {
				selectedMulti: true,
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
				onCheck: commonCheck
			}
		};
	return $.fn.zTree.init($("#"+objId), setting, zNodes);
}

function commonCheck(event, treeId, treeNode) {
    if(treeNode.checked){
    	moveTreeNode2(Tree1, Tree2);
    }else{
    	var pNode = Tree1.getNodeByParam("id", treeNode.pId, null);//获取当前节点的父节点
    	if(!isnull(pNode)){
    		if(!pNode.checked){//判断父节点是否取消
    			var selects = Tree1.getCheckedNodes(true);
    		    if(selects.length==0){
    		    	deleteParentNode(Tree1,pNode);//删除父节点
    		    }else{
    		    	deleteTreeNode(Tree2,pNode);//删除当前节点的父节点
    		    }
        	}
    	}
    	deleteTreeNode(Tree2,treeNode);//删除当前节点
    }
}


