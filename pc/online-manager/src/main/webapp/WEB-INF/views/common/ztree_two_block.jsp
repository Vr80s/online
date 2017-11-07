<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<table style="">
			<tr>
				<td style="vertical-align: top;">
					<div style="text-align: center"><label>选择资源</label></div>
					<div class="contrightbox" style="overflow-y: auto; overflow-x: auto;height: 380px;width:260px;">
						<div class="zTreeDemoBackground">
							<ul id="leftResource" class="ztree" style="font-size: 13px; font-weight: bold;">
							</ul>
						</div>
					</div>
				</td>
				<td style="vertical-align: top;">
					<div style="text-align: center"><label>已选择资源</label></div>
					<div class="contrightbox" style="overflow-y: auto; overflow-x: auto;height: 380px;width:260px;">
						<div class="zTreeDemoBackground">
							<ul id="rightResource" class="ztree" style="font-size: 13px; font-weight: bold;">
							</ul>
						</div>
					</div>
				</td>
			</tr>
</table>
<script type="text/javascript">
function loadCourseAndTree(courseUrl,url,callback){
	ajaxRequest(courseUrl,{},function(data){
		var htmls = "";
		var treeUrl = $("#getTree").html();
		for(var i=0;i<data.length;i++){
			var cid = data[i].id;
			htmls += '<li><a href="javascript:void(-1)" id="liA_'+i+'" onclick=loadTreeByCourse(this,"'+cid+'","'+url+'");>'+data[i].name
			+'</a></li><li class="divider"></li>';
			
			if(treeUrl.indexOf("treeDemo_"+cid)==-1){
				$("#getTree").append('<ul id="treeDemo_'+cid+'" class="ztree" style="font-size: 13px; font-weight: bold;width: 250px;"></ul>');
				loadTree("treeDemo_"+cid,null);
			}
		}
		$("#courseUl").html(htmls);
		
		if(!isnull(callback)){
			callback();
		}
	});
}

var courseId;
var courseLists = new Array();
function loadTreeByCourse(obj,cId,treeUrl){
	courseId = cId;
	$("li .courseLi").removeClass("courseLi");
	$(obj).addClass("courseLi");
	loadTreeAll("knowledge",treeUrl,{"courseId":cId});
}
function loadTreeAll(objId,url,data){
	ajaxRequest(url,data,function(zNodes){
		Tree1 = loadTree(objId,zNodes);
		Tree2 = $.fn.zTree.getZTreeObj("treeDemo_"+courseId);
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
	});
}

function loadTreeAllByEdit(url,data,callback){
	ajaxRequest(url,data,function(courseVos){
		for(var i=0;i<courseVos.length;i++){
			var zNodes = courseVos[i].ztreeVos;
			var objId = "treeDemo_"+courseVos[i].id;
			courseLists.push(courseVos[i].id);
			loadTree(objId,zNodes);
		}
		if(!isnull(callback)){
			callback();
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
	var cNode = Tree1.getNodeByParam("pId", treeNode.id, null);//获取当前节点下的子节点
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

function zTreeOnCheck(event, treeId, treeNode) {
	Tree2 = $.fn.zTree.getZTreeObj("treeDemo_"+courseId);
    if(treeNode.checked){
    	if(treeNode.isParent){
    		loopCurrentNode(treeNode);//递归地调用
    	}else if(treeNode.type=="4"){
    		moveTreeNode(Tree1, Tree2);
    		if(courseLists.indexOf(courseId)==-1){
        		courseLists.push(courseId);
        	}
    	}
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
};

function viewRightTree(callback){
	var arrays = new Array();
	if(!isnull(Tree2)){
		var nodes = getAllNodes(Tree2);
		if(nodes.length>0){
			for(var i=0;i<nodes.length;i++){
				var id = nodes[i].id;
				var name = nodes[i].name;
				var leftNode = Tree1.getNodeByParam("id", id, null);
				if(!nodes[i].isParent&&leftNode.contenttype==4){
					var object = {"id":id,"name":name};
					arrays.push(object);
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