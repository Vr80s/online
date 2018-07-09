var applyTable;//医师列表

$(function(){
	/** 师承列表begin */
    var searchCase_P = new Array();
    searchCase_P.push('{"tempMatchType":"9","propertyName":"search_service_type","propertyValue1":"0","tempType":"String"}');
	var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
		var objData = [{ "title": checkbox,"class":"center","width":"5%","sortable":false,"data": 'id' ,"mRender":function(data,display,row){
        return '<input type="checkbox" value='+data+' class="ace" /><span class="lbl"></span><span name="skfs" style=\'display:none\'>'+row.teachMethodName+'</span>';
    }},
    { "title": "姓名", "class":"center","width":"9%","sortable":false,"data": 'name' },
    { "title": "手机", "class":"center","width":"8%","sortable":false,"data": 'tel'},
	{ "title": "性别", "class":"center","width":"8%","sortable":false,"data": 'sex',"mRender":function (data) {
            if(data==1){
                return data="<span name='zt'>男</span>";
            }else if(data==0){
                return data="<span name='zt'>女</span>";
            }else {
                return data="<span name='zt'>未知</span>";
			}
        } },
    { "title": "年龄", "class":"center","width":"6%", "sortable":false,"data": 'age'},
    { "title": "学历", "class":"center","width":"6%", "sortable":false,"data": 'education',"mRender":function (data) {
            if(data==1){
                return data="<span name='zt'>小学</span>";
            }else if(data==2){
                return data="<span name='zt'>初中</span>";
            }else if(data==3){
                return data="<span name='zt'>高中</span>";
            }
            else if(data==4){
                return data="<span name='zt'>大专</span>";
            }
            else if(data==5){
                return data="<span name='zt'>本科</span>";
            }
            else if(data==6){
                return data="<span name='zt'>研究生</span>";
            }
            else if(data==7){
                return data="<span name='zt'>博士生</span>";
            }
            else if(data==8){
                return data="<span name='zt'>博士后</span>";
            }else {
                return data="<span name='zt'>无</span>";
            }
        } },
	{ "title": "籍贯", "class":"center","width":"6%", "sortable":false,"data": 'nativePlace'},
	{ "title": "报名时间", "class":"center","width":"8%","sortable":false,"data": 'deadline'},
	{ "title": "徒弟", "class":"center","width":"8%","sortable":false,"data": 'apprentice',"mRender":function (data) {
            if(data==1){
                return data="<span name='zt'>是</span>";
            }else {
                return data="<span name='zt'>否</span>";
            }
        } },
    { "sortable": false,"class": "center","width":"12%","title":"操作","mRender":function (data, display, row) {
		return '<div class="hidden-sm hidden-xs action-buttons">'+
			'<a class="blue" href="javascript:void(-1);" title="查看" onclick="viewDetails(this)"><i class="ace-icon fa fa-search bigger-130"></i></a>'+
			'<a class="blue" href="javascript:void(-1);" title="编辑" onclick="updateIsApprentice(this)"><i class="ace-icon fa fa-pencil bigger-130"></i></a></div>'

	    }
	}];

    applyTable = initTables("applyTable",basePath+"/apprentice/entryInformationList",objData,true,true,0,null,searchCase_P,function(data){


	});
	/** 师承列表end */

	createDatetimePicker2($(".datetime-picker"),"yy-mm-dd","HH:mm:ss");
});

/**
 * 医师列表搜索
 */
function search_P(){
    var json = new Array();
    json.push('{"tempMatchType":"9","propertyName":"search_title","propertyValue1":"0","tempType":"int"}');
	searchButton(applyTable,json);
};

function getLocalTime(nS) {
    return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');
}

//查看
function  viewDetails(obj){
    var oo = $(obj).parent().parent().parent();
    var row = applyTable.fnGetData(oo); // get datarow
    window.location.href = basePath + '/home#apprentice/entryInformationDetail?id=' + row.id;

}
//修改页面
function toEdit(obj) {
    var oo = $(obj).parent().parent().parent();
    var row = applyTable.fnGetData(oo); // get datarow
    window.location.href = basePath + '/home#apprentice/entryInformationEdit?id=' + row.id;

}
function exportExcel() {
    var merId = $("#search_title").val();
    // $('#exportId').attr('href','/apprentice/export?merId='+merId);
    debugger
    window.location.href='/apprentice/export?merId='+merId;
}

//是否为徒弟弹框
function updateIsApprentice(obj){
    var oo = $(obj).parent().parent().parent();
    var row = applyTable.fnGetData(oo); // get datarow
    $("#id").val(row.id);
    $("#apprentice").val(row.apprentice);
    var dialog = openDialog("EditDialog","dialogEditDiv","是否为徒弟",380,300,true,"确定",function(){
        if($("#update-form").valid()){
            mask();
            $("#update-form").attr("action", basePath+"/apprentice/updateIsApprentice");
            $("#update-form").ajaxSubmit(function(data){
                data = getJsonData(data);
                unmask();
                if(data.success){
                    $("#EditDialog").dialog("close");
                    layer.msg(data.resultObject);
                    freshTable(applyTable);
                }else{
                    layer.msg(data.errorMessage);
                }
            });
        }
    });
}