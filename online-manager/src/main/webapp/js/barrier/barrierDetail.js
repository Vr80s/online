var barrierTable;
var barrierForm;
var searchJson = new Array();
$(function() {
	//初始化Ztree
	initZtree();

	loadBarrierList();

    //时间控件
	createDatePicker($("#startTime"));
	createDatePicker($("#stopTime"));

});

//列表展示
function loadBarrierList(){
    var dataFields = [
	      { "title": "关卡名称", "class":"center","width":"14%","data": 'name',"sortable":false},
	      { "title": "总分数", "class":"center","width":"6%","sortable":false,"data": 'totalScore'},
	      { "title": "时长/min", "class":"center","width":"10%","sortable":false,"data": 'limitTime' },
	      { "title": "通关标准(分数)", "class":"center","width":"10%","sortable":false,"data": 'passScorePercent'},
	      { "title": "创建日期", "class":"center","width":"10%","sortable":false,"data": 'createTime'},
	      {title:"操作","class": "center","width":"8%","data":"id","sortable": false,"mRender":function (data, display, row) {
                var buttons= '<div class="hidden-sm hidden-xs action-buttons">' +
                			 '<a class="blue" href="javascript:void(-1);" title="修改" onclick="editBarrier(\''+row.kpointId+'\',\''+data.trim()+'\')"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'+
		                	 '<a class="blue" href="javascript:void(-1);" title="删除" onclick="deletesBarrier(\''+data.trim()+'\')"><i class="ace-icon fa fa-trash-o bigger-130"></i></a>';
				buttons += "</div>";
                return buttons;
          }}
    ];
    searchJson.push('{"tempMatchType":"9","propertyName":"search_courseId","propertyValue1":"' + $("#search_courseId").val() + '","tempType":"String"}');
    barrierTable = initTables("barrierTable", basePath + "/barrier/barrier/findBarrierList", dataFields, true, true, 0,null,searchJson,function(data){
    });
    
    //新增根据一级菜单获取相应的二级菜单 暂时用不上，现在没有级联菜单
	$('#menuName').change(function(){
		
		  
		var firstMenuNumber=$(this).children('option:selected').val();//这就是selected的值
		 $.ajax({  
			          type:'post',      
			          url:basePath+'/cloudclass/course/getSecoundMenu',  
			          data:{firstMenuNumber:firstMenuNumber},  
			          dataType:'json',  
			          success:function(data){  
		                     
			        	  		var optionstring = ""; 
		                        for (var j = 0; j < data.length; j++) {  
		                            optionstring += "<option value=\"" + data[j].number + "\" >" + data[j].name + "</option>";  
		                        }  
			        	      
		                        $("#menuNameSecond").html("<option value=''>请选择</option> "+optionstring);  
			          }  
			      }) ; 
		
	
		}) ;
	
	//编辑根据一级菜单获取相应的二级菜单
	$('#edid_menuName').change(function(){
		
		var firstMenuNumber=$(this).children('option:selected').val();//这就是selected的值
		 $.ajax({  
			          type:'post',      
			          url:basePath+'/cloudclass/course/getSecoundMenu',  
			          data:{firstMenuNumber:firstMenuNumber},  
			          dataType:'json',  
			          success:function(data){  
		                     
		                        var optionstring = "";  
		                        for (var j = 0; j < data.length; j++) {  
		                            optionstring += "<option value=\"" + data[j].number + "\" >" + data[j].name + "</option>";  
		                        }  
		                        $("#edid_menuNameSecond").html("<option value=''>请选择</option> "+optionstring); 
			          }  
			      }) ; 
		 
		}) ;

	$('#search_menu').change(function(){
	
	var firstMenuNumber=$(this).children('option:selected').val();//这就是selected的值
	 $.ajax({
		          type:'post',      
		          url:basePath+'/cloudclass/course/getSecoundMenu',  
		          data:{firstMenuNumber:firstMenuNumber},  
		          dataType:'json',  
		          success:function(data){  
	                     
	                        var optionstring = "";  
	                        for (var j = 0; j < data.length; j++) {  
	                            optionstring += "<option value=\"" + data[j].number + "\" >" + data[j].name + "</option>";  
	                        }  
	                        $("#search_scoreType").html("<option value=''>请选择</option> "+optionstring); 
		          }  
		      }) ; 
	 
	}) ;
}

//条件搜索
function search(sortType){
     searchButton(barrierTable,searchJson);
}

function addBarierDetail(kpointId,kpointName){
	if(kpointId == null){
		alert("请选择一个知识点进行添加！");
		return;
	}
	window.location.href=basePath+"/home#/barrier/barrier/barrierDetailAdd?courseId="+$("#courseId").val()+"&courseName="+$("#courseName").val()+"&kpointId="+kpointId+"&kpointName="+kpointName;	
}

/**
 * 批量逻辑删除
 * 
 */
function deletesBarrier(id){
	showDelDialog(function(){
		ajaxRequest(basePath+"/barrier/barrier/deletes",{'ids':id},function(data){
			if(!data.success){
				alertInfo(data.errorMessage);
			}else{
				if(!isnull(barrierTable)){

					var tree = $.fn.zTree.getZTreeObj("ztree");
					var node1 = tree.getNodesByParam("id", id, null);
					if(node1 != null){
						tree.removeNode(node1[0]);
					}

					freshDelTable(barrierTable);
				}
				if(typeof(data.errorMessage) != "undefined"){
					//alertInfo(data.errorMessage);
					layer.msg(data.resultObject);
				}
			}
		});
	},"","","");
}

/**
 * 修改
 * 
 */
function editBarrier(kpointId,id){
	var tree = $.fn.zTree.getZTreeObj("ztree");
	var node1 = tree.getNodesByParam("id", kpointId, null);
	var kpointName = node1[0].actName;
	window.location.href=basePath+"/home#/barrier/barrier/barrierDetailEdit?courseId="+
	$("#courseId").val()+"&courseName="+$("#courseName").val()+"&kpointId="+kpointId+"&kpointName="+kpointName+"&id="+id;
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
	 * @param onClickTree  左键点击方法
	 * @param zTreeBeforeDrop 排序拖拽释放之后执行 
	 * @returns
	 */
	treeObj = loadTreeDynomicBarrier("ztree",basePath+"/cloudclass/videores/showTreeBarrier?courseId="+$("#courseId").val()+"&courseName="+$("#courseName").val(),function(){
		$("#"+localStorage.spnode).trigger("click");
		$("#"+localStorage.snode).trigger("click");
	},function(currentNode){//右键添加
		if(currentNode.contenttype == 5){//如果是 5 就是修改
			editBarrier(currentNode.getPreNode().id,currentNode.id);
		}else{
			if(currentNode.getNextNode() != null && currentNode.getNextNode().contenttype==5){//如果该节点的下一个节点 是关卡 那么就为修改 否则为新增
				editBarrier(currentNode.id,currentNode.getNextNode().id);
			}else{
				addBarierDetail(currentNode.id,currentNode.name);
			}
		}
	},null//右键修改
	,null//右键删除
	,null//右键复制
	,function(currentNode){//左键单击
		if(currentNode.level <=2 || currentNode.contenttype == 5){
			var ids=[];
			if(currentNode.contenttype == 5){//如果点击关卡 就查询他的知识点层级
				ids = getChildren(ids,currentNode.getPreNode());
			}else{
				ids = getChildren(ids,currentNode);
			}

			$("#search_chapterId").val(ids.join(","));
			$("#search_chapterLevel").val(currentNode.level+1);
			search();
		}
		
	});
	
//------------------------Ztree初始化结束-----------------------------------------------
	function getChildren(ids,treeNode){

		ids.push(treeNode.id);

		if (treeNode.isParent){

				for(var obj in treeNode.children){

					getChildren(ids,treeNode.children[obj]);

				}

		   }

		return ids;

	}
}

$('#returnbutton').on('click',function(){
	window.location.href=basePath+'/home#barrier/barrier/index';
});

