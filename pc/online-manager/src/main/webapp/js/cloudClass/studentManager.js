var studentManagerTable;
var searchCase;
var studentManagerForm;

$(function(){
	
	document.onkeydown=function(event){
		if(event.keyCode == 13) {
            return false;
        }
	}
	
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
	var objData = [{ "title": checkbox,"class":"center","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span>';
    }},
    { "title": "序号", "class": "center","width":"80px","sortable": false,"data":"id" },
    { "title": "课程名称", "class":"center","sortable":false,"data": 'courseName' },
    { "title": "状态", "class":"center","sortable":false,"data": 'status',"mRender":function (data, display, row) {
    	if(data==1){
    		return data="已启用";
    	}else{
    		return data="已禁用";
    	}
    } },
    { "sortable": false,"class": "center","title":"操作","mRender":function (data, display, row) {
				return '<a class="blue" href="javascript:void(-1);" title="管理学员" onclick="delDialog(this)"></a></div>';
	    
	    } 
	},
	{ "title": "学科id", "class": "center","width":"80px","sortable": false,"data":"menuId","visible":false },
    { "title": "课程类别id", "class": "center","width":"80px","sortable": false,"data":"courseTypeId","visible":false },
    { "title": "授课方式id", "class": "center","width":"80px","sortable": false,"data":"courseType","visible":false }];
});