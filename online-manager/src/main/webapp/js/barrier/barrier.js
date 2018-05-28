var barrierTable;
var barrierForm;
var searchJson = new Array();
$(function() {
    loadBarrierList();
});

//列表展示
function loadBarrierList(){
    var dataFields = [
          { "title": '序号', "class": "center", "width": "5%","data": 'id',datafield: 'xuhao', "sortable": false},
          { "title": "课程名称", "class":"center","sortable":false,"data": 'courseName' },
	      { "title": "所属学科", "class":"center","width":"10%","sortable":false,"data": 'xMenuName' },
	      { "title": "业务类别", "class":"center","width":"8%","sortable":false,"data": 'courseTypeId' ,"mRender":function (data, display, row) {
		      	if(data==0){
		      		return data="职业课";
		      	}else{
		      		return data="微课";
		      	}
		  }},
	      { "title": "课程类别", "class":"center","width":"8%","sortable":false,"data": 'scoreTypeName' },
	      { "title": "授课方式", "class":"center","width":"8%","sortable":false,"data": 'teachMethodName',"mRender":function (data, display, row) {
	      		return "<span name='skfs'>"+data+"</span>";
	      }},
	      { "title": "关卡数", "class":"center","width":"8%","sortable":false,"data": 'barrierNum'},
	      { "title": "课程状态", "class":"center","width":"8%","sortable":false,"data": 'status',"mRender":function (data, display, row) {
	      	if(data==1){
	      		return data="<span name='zt'>启用</span>";
	      	}else{
	      		return data="<span name='zt'>禁用</span>";
	      	}
	      }},
	      { "title": "关卡状态", "class":"center","width":"8%","sortable":false,"data": 'barrierStatus',"mRender":function (data, display, row) {
	      	if(data==1){
	      		return "<span>启用</span>";
	      	}else{
	      		return "<span>禁用</span>";
	      	}
	      }},
	      {title:"操作","class": "center","width":"11%","data":"id","sortable": false,"mRender":function (data, display, row) {
              var buttons= 
            	'<div class="hidden-sm hidden-xs action-buttons">'+
            	'<a class="blue" href="javascript:void(-1);" title="关卡管理" onclick="showBarrierDetail(\''+row.id+'\',\''+row.courseName+'\')">关卡管理</a>'+
            	  (row.barrierStatus == 0 && row.barrierNum > 0 ?
            	  '<a class="blue" href="javascript:void(-1);" title="启用关卡" onclick="useBarrier(\''+row.id+'\')">启用关卡</a>'
            	  : '') +
				'</div>';
              return buttons;
         }}
    ];

    barrierTable = initTables("barrierTable", basePath + "/barrier/barrier/findCourseList", dataFields, true, true, 1,null,searchJson,function(data){
    });

}

 //条件搜索
 function search(){
     searchButton(barrierTable,searchJson);
}
//查看详情
 function showBarrierDetail(courseId,courseName){
  	 window.location.href=basePath+"/home#/barrier/barrier/barrierDetail?courseId="+courseId+"&courseName="+courseName;
 }
 
 function useBarrier(courseId){
	 confirmDialog(function(){
		 $.ajax({
			 url:basePath + '/barrier/barrier/useBarrier',
			 data:{courseId:courseId},
			 type:'post',
			 dataType:'json',
			 success:function(data){
				 if(data.success){
					 layer.msg('操作成功！');
					 freshTable(barrierTable);
				 } else {
					 alertInfo(data.errorMessage);
				 }
			 }
		 });
	 },'提示','启用关卡后将不能：禁用、新增、删除、修改上级，确定要启用吗？','确定启用');
 }