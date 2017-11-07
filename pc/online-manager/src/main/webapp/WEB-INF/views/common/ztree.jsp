<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table style="">
	<tr>
		<td class="col-sm-3" style="vertical-align: top;">
			<div style="text-align: center">
				<label for="form-field-select-2">课程体系名称</label>
			</div>
			<div style="position:relative">
				<input  id="getKsystemBySearch" style="width:298px;margin-left:5px;outline:none;height:30px"/>
				<span class="glyphicon glyphicon-search" style="position:absolute;z-index:2;top: 9px;right: 17px;"></span>
			</div>
			<div class="dropdown dropdown-preview" style="overflow-y: auto; overflow-x: auto; height: 519px; width: 300px;">
				<ul class="dropdown-menu" style="text-align: left; min-width: 100%;" id="courseUl">
				</ul>
			</div>
		</td>
		<td style="vertical-align: top;">
			<div style="text-align: center">
				<label for="form-field-select-2">选择课程体系</label>
			</div>
			<div class="contrightbox" style="overflow-y: auto; overflow-x: auto; height: 519px; min-width: 240px;">
				<div class="zTreeDemoBackground" style="min-height: 298px;">
					<ul id="knowledge" class="ztree" style="font-size: 13px; font-weight: bold; width: 250px;"></ul>
				</div>
			</div>
		</td>
		<td style="vertical-align: top;">
			<div>
				<div style="text-align: center">
					<label for="form-field-select-2">已选择的课程体系</label>
				</div>
				<div>
					<div class="contrightbox" style="overflow-y: auto; overflow-x: auto; height: 519px; min-width: 240px;">
						<div class="zTreeDemoBackground" id="getTree" style="min-height: 517px;"></div>
					</div>
				</div>
			</div>
		</td>
	</tr>
</table>

<script type="text/javascript">
$(function(){
	$("#getKsystemBySearch").on('keyup',getKsystemBySearch);
	function getKsystemBySearch(){
		var value ="";
	    value = $(this).val();
		ajaxRequest(basePath+'/cloudclass/course/getCourseVoList',{search:value},function(data){
			var htmls = "";
			var url = basePath+"/cloudclass/videores/getTreeByCourse";
			for(var i=0;i<data.length;i++){
				var cid = data[i].id;
				var tree2 = $.fn.zTree.getZTreeObj("treeDemo_"+cid);
				if(tree2){
					tree2.destroy();
				}
				htmls += '<li><a href="javascript:void(-1)" id="liA_'+cid+'" onclick=loadTreeByCourse(this,"'+cid+'","'+data[i].courseName+'","'+$("#level").val()+'","'+url+'");>'+data[i].courseName
				+'</a></li><li class="divider"></li>';
			
			}
			$("#courseUl").html(htmls);
			$("#getTree").html("");
		});
	}
	
	initCourseList();
	
});

function initCourseList(){
	ajaxRequest(basePath+'/cloudclass/course/getCourseVoList',{search:""},function(data){
		var htmls = "";
		var url = basePath+"/cloudclass/videores/getTreeByCourse";
		for(var i=0;i<data.length;i++){
			var cid = data[i].id;
			var tree2 = $.fn.zTree.getZTreeObj("treeDemo_"+cid);
			if(tree2){
				tree2.destroy();
			}
			htmls += '<li><a href="javascript:void(-1)" id="liA_'+cid+'" onclick=loadTreeByCourse(this,"'+cid+'","'+data[i].courseName+'","'+$("#level").val()+'","'+url+'");>'+data[i].courseName
			+'</a></li><li class="divider"></li>';
		
		}
		$("#courseUl").html(htmls);
		$("#getTree").html("");
	});
}


function loadCourseAndTree(courseUrl,url,callback){
	ajaxRequest(courseUrl,{},function(data){
		var htmls = "";
		for(var i=0;i<data.length;i++){
			var cid = data[i].id;
			var tree2 = $.fn.zTree.getZTreeObj("treeDemo_"+cid);
			if(tree2){
				tree2.destroy();
			}
			htmls += '<li><a href="javascript:void(-1)" id="liA_'+cid+'" onclick=loadTreeByCourse(this,"'+cid+'","'+data[i].courseName+'","'+$("#level").val()+'","'+url+'");>'+data[i].courseName
			+'</a></li><li class="divider"></li>';
		
		}
		$("#courseUl").html(htmls);
		$("#getTree").html("");
		
		if(!isnull(callback)){
			callback(data);
		}
	});
}

var courseId;
var courseLists = new Array();
function loadTreeByCourse(obj,cId,courseName,level,url){
	//debugger;
	courseId = cId;
	/* var objId = "treeDemo_"+courseId;
	var treeUrl = $("#getTree").html();
	if(treeUrl.indexOf(objId)==-1){
		$("#getTree").append('<ul id="'+objId+'" class="ztree" style="font-size: 13px; font-weight: bold;width: 250px;"></ul>');
		loadTree(objId,null);
	} */
	$("li .courseLi").removeClass("courseLi");
	$(obj).addClass("courseLi");
	loadTreeAll("knowledge",url,{"courseId":cId,"courseName":courseName,"level":level});
}
function loadTreeAll(objId,url,data){
	mask();
	ajaxRequest(url,data,function(zNodes){
		Tree1 = loadTree(objId,zNodes);
		Tree2 = $.fn.zTree.getZTreeObj("treeDemo_"+courseId);
		if(!isnull(Tree2)){//让Tree1某些节点呈选中状态
			var nodes = getAllNodes(Tree2);
			for(var i=0;i<nodes.length;i++){
				var id = nodes[i].id;
				var oNode = Tree1.getNodeByParam("id", id, null);
				if(oNode!= null && !oNode.isParent){
					Tree1.checkNode(oNode, true, true);
				}
			}
		}
		unmask();
	});
}

function loadTreeAllByEdit(url,data,callback){
	var treeUrl = $("#getTree").html();
	ajaxRequest(url,data,function(courseVos){
		for(var i=0;i<courseVos.length;i++){
			var zNodes = courseVos[i].ztreeVos;
			var objId = "treeDemo_"+courseVos[i].id;
			if(treeUrl.indexOf(objId)==-1){
				$("#getTree").append('<ul id="'+objId+'" class="ztree" style="font-size: 13px; font-weight: bold;width: 250px;"></ul>');
			}
			courseLists.push(courseVos[i].id);
			loadTree(objId,zNodes);
		}
		if(!isnull(callback)){
			callback(courseVos);
		}
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

function loopCurrentNode(treeNode){
	var nodes = Tree1.getNodesByParam("pId", treeNode.id, null);
	for(var i=0;i<nodes.length;i++){
		var cNode = nodes[i];
		if(cNode.isParent){
			loopCurrentNode(cNode);
		}else{
			if(cNode.type=="4"){
				moveTreeNode(Tree1, Tree2);
	    		if(courseLists.indexOf(courseId)==-1){
	        		courseLists.push(courseId);
	        	}
			}
		}
	}
}

/**
 * 捕获左右两边树的check事件
 */
function zTreeOnCheck(event, treeId, treeNode) {
	var objId = "treeDemo_"+courseId;
	var treeUrl = $("#getTree").html();
	isUpdateClickForCourseSystem = true;
	//debugger;
    if(treeNode.checked){//选中
    	if(treeUrl.indexOf(objId)==-1){
    		$("#getTree").append('<ul id="'+objId+'" class="ztree" style="font-size: 13px; font-weight: bold;width: 250px;"></ul>');
    		Tree2 = loadTree(objId,null);
    	}else{
    		Tree2 = $.fn.zTree.getZTreeObj(objId);
    	}
    	if(treeNode.isParent){
    		mask();
    		setTimeout(function(){
    			//loopCurrentNode(treeNode);//递归地调用
    			moveTreeNode(Tree1, Tree2);
	    		if(courseLists.indexOf(courseId)==-1){
	        		courseLists.push(courseId);
	        	}
        		unmask();
    		},50);
    	}else if(treeNode.type=="4"){
    		moveTreeNode(Tree1, Tree2);
    		if(courseLists.indexOf(courseId)==-1){
        		courseLists.push(courseId);
        	}
    	}
    }else{//取消选中
    	if(treeId!="knowledge"){//点击右边树形的check
    		Tree2 = $.fn.zTree.getZTreeObj(treeId);
    		var pNode2 = Tree2.getNodeByParam("id", treeNode.pId, null);//获取当前节点的父节点
    		if(!isnull(pNode2)){
        		if(!pNode2.checked){//判断父节点是否取消
        			var selects2 = Tree2.getCheckedNodes(true);
        		    if(selects2.length==0){
        		    	deleteParentNode(Tree2,pNode2);//删除父节点
        		    }else{
        		    	if(pNode2.level==2){
        		    		var parentNode2 = Tree2.getNodeByParam("id", pNode2.pId, null);//获取父节点的父节点
        		    		if(!isnull(parentNode2) && !parentNode2.checked){//父父节点取消
        		    			deleteTreeNode(Tree2,parentNode2);//删除父父节点
        		    		}
        		    	}
        		    	deleteTreeNode(Tree2,pNode2);//删除当前节点的父节点
        		    }
            	}
        	}
    		$("#liA_"+courseId).click();
    	}else{//点击左边树形的check
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
    	}
    	deleteTreeNode(Tree2,treeNode);//删除当前节点
    	var tree2Select = Tree2.getCheckedNodes(true);
	    if(tree2Select.length==0){//移除所有节点时
	    	if(treeId != "knowledge"){//取消点击右边树时
	    		objId = treeId;
	    	}
	    	var ulObj = $("#getTree").find('ul[id="'+objId+'"]');
	    	if(!isnull(ulObj)){
	    		ulObj.remove();
	    	}
	    }
    }
}

function viewRightTree(callback){
	var arrays = new Array();
	for(var k=0;k<courseLists.length;k++){
		var cid = courseLists[k];
		Tree2 = $.fn.zTree.getZTreeObj("treeDemo_"+cid);
		if(!isnull(Tree2)){
			var nodes = getAllNodes(Tree2);
			if(nodes.length>0){
				for(var i=0;i<nodes.length;i++){
					var id = nodes[i].id;
					var name = nodes[i].name;
					if(!nodes[i].isParent){
						var object = {"courseId":cid,"id":id,"name":name};
						arrays.push(object);
					}
				}
			}
		}
	}
	if(!isnull(callback)){
		callback(arrays);
	}
}

/**
 * 清除选中的树
 */
function clearTree(){
	if(!isnull(Tree1)){
		Tree1.checkAllNodes(false);
		for(var k=0;k<courseLists.length;k++){
			Tree2 = $.fn.zTree.getZTreeObj("treeDemo_"+courseLists[k]);
			if(!isnull(Tree2)){
				var nodes = getAllNodes(Tree2);
				for (var i=0, l=nodes.length; i < l; i++) {
					Tree2.removeNode(nodes[i]);
				}
			}
		}
	}
}
</script>